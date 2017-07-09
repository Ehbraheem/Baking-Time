package com.example.profbola.bakingtime.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Recipe;
import com.example.profbola.bakingtime.utils.RecipeAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private RecipeAdapter mRecipeAdapter;
    private RecyclerView mRecipeListView;
    private TextView mErrorMessageDisplay;

    private List<Recipe> mRecipes;

    private static final String RECIPES_KEY = "recipes_key";

    private Context mContext;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPES_KEY);
        }

        mContext = getContext();
        /* This TextView is used to display errors and will be hidden if there are no errors */
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mErrorMessageDisplay = (TextView) view.findViewById(R.id.tv_error_message_display);
        setUpRecipeList(view);


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(RECIPES_KEY, (ArrayList<? extends Parcelable>) mRecipes);
    }

    private void setUpRecipeList(View view) {
        mRecipeListView = (RecyclerView) view.findViewById(R.id.recipe_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecipeListView.setLayoutManager(layoutManager);
        mRecipeAdapter = new RecipeAdapter(mContext);
        mRecipeAdapter.swapRecipes(mRecipes);
        mRecipeListView.setAdapter(mRecipeAdapter);
        mRecipeListView.setHasFixedSize(true);
    }


    public void changeData(Cursor data) {

        if (null != data) {
            int count = data.getCount();
            mRecipes = Recipe.convertCursor(data);
            mRecipeAdapter.swapRecipes(mRecipes);
        } else {
            showErrorMessage();
        }
    }

//    private List<Recipe> consumeCursor(Cursor cursor) {
//        List<Recipe> recipes = new ArrayList<>();
//        for (int i = 0; i < cursor.getCount(); i++) {
//            cursor.moveToPosition(i);
//            recipes.add(new Recipe(cursor));
//        }
//        cursor.close();
//
//        return recipes;
//    }

    private void showErrorMessage() {
        mRecipeListView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showRecipeList() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecipeListView.setVisibility(View.VISIBLE);
    }
}
