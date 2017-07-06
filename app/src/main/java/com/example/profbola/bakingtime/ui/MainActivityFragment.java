package com.example.profbola.bakingtime.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
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
import com.example.profbola.bakingtime.services.RecipeService;
import com.example.profbola.bakingtime.utils.RecipeAdapter;
import com.example.profbola.bakingtime.utils.RecipeNetworkUtil;
import com.example.profbola.bakingtime.utils.RecipeParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.example.profbola.bakingtime.provider.RecipeContract.RecipeEntry.CONTENT_URI;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecipeAdapter mRecipeAdapter;
    private RecyclerView mRecipeListView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private List<Recipe> mRecipes;

    private static final String RECIPES_KEY = "recipes_key";

    private static final int LOADER_ID = 456;

    private Context mContext;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getContext();
        startSync();
        /* This TextView is used to display errors and will be hidden if there are no errors */
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mErrorMessageDisplay = (TextView) view.findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) view.findViewById(R.id.pb_loading_indicator);

        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPES_KEY);
        } else {
            getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }

        setUpRecipeList(view);


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(RECIPES_KEY, (ArrayList<? extends Parcelable>) mRecipes);
    }

    private void setUpRecipeList(View view) {
        mRecipeListView = (RecyclerView) view.findViewById(R.id.recipe_list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mRecipeListView.setLayoutManager(layoutManager);
        mRecipeAdapter = new RecipeAdapter(mContext);
        mRecipeAdapter.swapRecipes(mRecipes);
        mRecipeListView.setAdapter(mRecipeAdapter);
        mRecipeListView.setHasFixedSize(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        /**
        return new AsyncTaskLoader<JSONArray>(mContext) {

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
        */
        return new CursorLoader(mContext, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (null != data) {
            mRecipes = consumeCursor(data);
            mRecipeAdapter.swapRecipes(mRecipes);
            showRecipeList();
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void showErrorMessage() {
        mRecipeListView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showRecipeList() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecipeListView.setVisibility(View.VISIBLE);
    }

    private void startSync() {
        RecipeService.startActionSyncRecipes(mContext);
    }

    private List<Recipe> consumeCursor(Cursor cursor) {
        List<Recipe> recipes = new ArrayList<>();
        while (cursor.moveToNext()) {
            recipes.add(new Recipe(cursor));
            cursor.moveToNext();
        }

        return recipes;
    }
}
