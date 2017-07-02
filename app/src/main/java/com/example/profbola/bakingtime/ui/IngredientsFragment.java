package com.example.profbola.bakingtime.ui;

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

/**
 * Created by prof.BOLA on 7/1/2017.
 */

public class IngredientsFragment extends Fragment {

    private RecyclerView mIngredientListing;
    private IngredientAdapter mAdapter;
    private Ingredient[] mIngredients;

    public IngredientsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients_listing, container, false);

        mIngredientListing = (RecyclerView) view.findViewById(R.id.ingredients_listing);
        setUpIngredientView();

        return view;
    }

    private void setUpIngredientView() {
        mAdapter = new IngredientAdapter(getContext(), mIngredients);
        mIngredientListing.setAdapter(mAdapter);
        mIngredientListing.setLayoutManager(new LinearLayoutManager(getContext()));
        mIngredientListing.setHasFixedSize(true);
//        mAdapter.swapIngredients(mIngredients);
    }

    public void addData(Ingredient[] ingredients) {
        mIngredients = ingredients;
    }
}