package com.example.profbola.bakingtime.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Ingredient;
import com.example.profbola.bakingtime.utils.IngredientAdapter;

import java.util.ArrayList;

import static com.example.profbola.bakingtime.utils.RecipeConstants.IngredientFragmentConstants.INGREDIENT_KEY;

/**
 * Created by prof.BOLA on 7/1/2017.
 */

public class IngredientsFragment extends Fragment {

    private RecyclerView mIngredientListing;
    private IngredientAdapter mAdapter;
    private ArrayList<Ingredient> mIngredients;

    OnIngredientSelected mCallback;

    public interface OnIngredientSelected {
        void ingredientClicked(int position);
    }

    public IngredientsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mIngredients = savedInstanceState.getParcelableArrayList(INGREDIENT_KEY);
        }
        View view = inflater.inflate(R.layout.fragment_ingredients_listing, container, false);

        mIngredientListing = (RecyclerView) view.findViewById(R.id.ingredients_listing);
        setUpIngredientView();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(INGREDIENT_KEY, mIngredients);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnIngredientSelected) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnIngredientSelected");
        }
    }



    private void setUpIngredientView() {
        mAdapter = new IngredientAdapter(getContext(), (OnIngredientSelected) getContext());
        mIngredientListing.setAdapter(mAdapter);
        mIngredientListing.setLayoutManager(new LinearLayoutManager(getContext()));
        mIngredientListing.setHasFixedSize(true);
        mAdapter.swapIngredients(mIngredients);
    }

    public void addData(ArrayList<Ingredient> ingredients) {
        mIngredients = ingredients;
        mAdapter.swapIngredients(mIngredients);
    }
}
