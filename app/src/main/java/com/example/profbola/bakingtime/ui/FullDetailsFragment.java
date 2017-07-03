package com.example.profbola.bakingtime.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Ingredient;
import com.example.profbola.bakingtime.models.Step;

/**
 * Created by prof.BOLA on 7/2/2017.
 */

public class FullDetailsFragment extends Fragment {

    public FullDetailsFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_full_detail, container, false);
        Bundle bundle = getArguments();

        // TODO: 7/3/2017 Clean up by creating Key constants in all place used
        if (bundle != null && bundle.containsKey(RecipeDetailActivity.INGREDIENTS_KEY)) {
            Ingredient ingredient = bundle.getParcelable(RecipeDetailActivity.INGREDIENTS_KEY);
            setUpIngredient(ingredient, view);
        } else if (bundle != null && bundle.containsKey(RecipeDetailActivity.STEP_KEY)) {
            Step step = bundle.getParcelable(RecipeDetailActivity.STEP_KEY);
            setUpStep(step, view);
        }

        Toast.makeText(getContext(), "It Worked!!!", Toast.LENGTH_SHORT).show();

        return view;
    }

    private void setUpIngredient(Ingredient ingredient, View view) {
        Toast.makeText(getContext(), "It Worked!!!", Toast.LENGTH_SHORT).show();

        view.findViewById(R.id.player_view).setVisibility(View.GONE);
        view.findViewById(R.id.ingredient_view).setVisibility(View.VISIBLE);

        TextView quantity = (TextView) view.findViewById(R.id.quantity);
        TextView measure = (TextView) view.findViewById(R.id.measure);
        TextView name = (TextView) view.findViewById(R.id.ingredient);

        quantity.getText();
        quantity.setText(String.valueOf(ingredient.quantity));
        measure.setText(ingredient.measure);
        name.setText(ingredient.ingredient);
    }

    private void setUpStep(Step step, View view) {
        Toast.makeText(getContext(), "It Worked!!!", Toast.LENGTH_SHORT).show();

        view.findViewById(R.id.player_view).setVisibility(View.VISIBLE);
        view.findViewById(R.id.ingredient_view).setVisibility(View.GONE);
    }
}
