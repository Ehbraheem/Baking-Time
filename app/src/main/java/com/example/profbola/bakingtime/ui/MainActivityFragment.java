package com.example.profbola.bakingtime.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Recipe;
import com.example.profbola.bakingtime.utils.RecipeAdapter;
import com.example.profbola.bakingtime.utils.RecipeNetworkUtil;
import com.example.profbola.bakingtime.utils.RecipeParser;

import org.json.JSONArray;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<JSONArray> {

    private RecipeAdapter mRecipeAdapter;
    private RecyclerView mRecipeListView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private static final int LOADER_ID = 456;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* This TextView is used to display errors and will be hidden if there are no errors */
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mErrorMessageDisplay = (TextView) view.findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) view.findViewById(R.id.pb_loading_indicator);

        setUpRecipeList(view);

        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        return view;
    }

    private void setUpRecipeList(View view) {
        Context context = getContext();
        mRecipeListView = (RecyclerView) view.findViewById(R.id.recipe_list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        mRecipeListView.setLayoutManager(layoutManager);
        mRecipeAdapter = new RecipeAdapter(context);
        mRecipeListView.setAdapter(mRecipeAdapter);
        mRecipeListView.setHasFixedSize(true);
    }

    @Override
    public Loader<JSONArray> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<JSONArray>(getContext()) {

            JSONArray recipeData = null;

            @Override
            protected void onStartLoading() {
                if (recipeData != null) {
                    deliverResult(recipeData);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public JSONArray loadInBackground() {
                return RecipeNetworkUtil.getRecipeFromApi();
            }

            @Override
            public void deliverResult(JSONArray data) {
                recipeData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<JSONArray> loader, JSONArray data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (null != data) {
            List<Recipe> recipes = RecipeParser.parse(data);
            mRecipeAdapter.swapRecipes(recipes);
            showRecipeList();
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<JSONArray> loader) {

    }

    private void showErrorMessage() {
        mRecipeListView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showRecipeList() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecipeListView.setVisibility(View.VISIBLE);
    }
}
