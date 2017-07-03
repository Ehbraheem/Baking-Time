package com.example.profbola.bakingtime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Ingredient;
import com.example.profbola.bakingtime.models.Recipe;
import com.example.profbola.bakingtime.models.Step;
import com.example.profbola.bakingtime.utils.RecipePagerAdapter;

/**
 * Created by prof.BOLA on 7/1/2017.
 */

public class RecipeDetailActivity extends AppCompatActivity implements
        IngredientsFragment.OnIngredientSelected, StepsFragment.OnVideoPlayerSelected {

    private static final String INGREDIENT_TAB = "ingredients";
    private static final String STEP_TAB = "steps";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private IngredientsFragment mIngredientsFragment;
    private StepsFragment mStepsFragment;
    private FullDetailsFragment mFullDetailsFragment;

    private boolean mTwoPane;

    public static final String INGREDIENTS_KEY = "ingredient";
    public static final String STEP_KEY = "step";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);

        if (findViewById(R.id.recipe_full_view) != null) {
            mTwoPane = true;

            mFullDetailsFragment = new FullDetailsFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_full_view, mFullDetailsFragment)
                    .commit();
        } else {
            mTwoPane = false;
        }

        if (savedInstanceState == null) {
            Recipe recipe = (Recipe) getIntent().getExtras().get("NAME");
            mIngredientsFragment = new IngredientsFragment();
            mIngredientsFragment.addData(recipe.ingredients);

            mStepsFragment = new StepsFragment();
            mStepsFragment.addData(recipe.steps);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setUpViewPager(mIngredientsFragment, mStepsFragment);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager, true);

    }

    private void setUpViewPager(IngredientsFragment ingredientsFragment, StepsFragment stepsFragment) {
        RecipePagerAdapter adapter = new RecipePagerAdapter(getSupportFragmentManager());
        //TODO: Add the fragments

        adapter.addFragment(ingredientsFragment, INGREDIENT_TAB);
        adapter.addFragment(stepsFragment, STEP_TAB);

        viewPager.setAdapter(adapter);
    }

    @Override
    public void ingredientClicked(Ingredient ingredient) {
        routeToFragmentOrActivity(ingredient, INGREDIENTS_KEY);
    }

    @Override
    public void videoClicked(Step step) {
        routeToFragmentOrActivity(step, STEP_KEY);
    }

    private void routeToFragmentOrActivity(Parcelable object, String type) {
        if (mTwoPane) {
            FullDetailsFragment newFragment = new FullDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(type, object);
            newFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_full_view, newFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, FullDetailActivity.class);
            intent.putExtra(type, object);
            startActivity(intent);
        }
    }
}
