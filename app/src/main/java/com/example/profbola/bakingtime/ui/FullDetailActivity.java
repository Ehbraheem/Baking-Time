package com.example.profbola.bakingtime.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Ingredient;
import com.example.profbola.bakingtime.models.Step;

import java.util.List;

import static com.example.profbola.bakingtime.utils.RecipeConstants.FullDetailsConstants.NEXT_BUTTON_REQUEST;
import static com.example.profbola.bakingtime.utils.RecipeConstants.RecipeDetailsConstants.INGREDIENTS_KEY;
import static com.example.profbola.bakingtime.utils.RecipeConstants.RecipeDetailsConstants.INGREDIENT_INDEX;
import static com.example.profbola.bakingtime.utils.RecipeConstants.RecipeDetailsConstants.STEP_INDEX;
import static com.example.profbola.bakingtime.utils.RecipeConstants.RecipeDetailsConstants.STEP_KEY;

public class FullDetailActivity extends AppCompatActivity
        implements FullDetailsFragment.NextStepButtonClicked {
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail);

        if (null == savedInstanceState) {

            // FIXME: 7/13/2017 Add NULL Guard
            Intent intent = getIntent();
            if (intent != null && intent.getExtras() != null) {

                Bundle bundle = extractExtras(intent);
                setUpFragment(bundle);
            }
        }
    }

    private void respondToNextButton(Step step) {
        int position = RecipeDetailActivity.provideNextInLine(step, mSteps);
        Step nextStep = mSteps.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(STEP_KEY, nextStep);
        setUpFragment(bundle);
    }

    private Bundle extractExtras(Intent intent) {
        Bundle bundle = new Bundle();
        if (intent.hasExtra(INGREDIENTS_KEY)) {
            mIngredients = intent.getParcelableArrayListExtra(INGREDIENTS_KEY);
            int mPosition = intent.getIntExtra(INGREDIENT_INDEX, 0);
            Parcelable object = mIngredients.get(mPosition);
            bundle.putParcelable(INGREDIENTS_KEY, object);
        } else {
            mSteps = intent.getParcelableArrayListExtra(STEP_KEY);
            int mPosition = intent.getIntExtra(STEP_INDEX, 0);
            Parcelable object = mSteps.get(mPosition);
            bundle.putParcelable(STEP_KEY, object);
        }
        return bundle;
    }

    @Override
    public void openNextStep(Step step) {
        respondToNextButton(step);
    }

    private void setUpFragment(Bundle bundle) {
        FullDetailsFragment fullDetailsFragment = new FullDetailsFragment();
        fullDetailsFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.full_details, fullDetailsFragment)
                .commit();
    }

}
