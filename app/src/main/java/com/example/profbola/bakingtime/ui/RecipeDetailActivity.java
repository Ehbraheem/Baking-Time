package com.example.profbola.bakingtime.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Recipe;
import com.example.profbola.bakingtime.utils.RecipePagerAdapter;

/**
 * Created by prof.BOLA on 7/1/2017.
 */

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String INGREDIENT_TAB = "ingredients";
    private static final String STEP_TAB = "steps";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        Recipe recipe = (Recipe) getIntent().getExtras().get("NAME");
        setUpViewPager(recipe);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager, true);

    }

    private void setUpViewPager(Recipe recipe) {
        RecipePagerAdapter adapter = new RecipePagerAdapter(getSupportFragmentManager());
        //TODO: Add the fragments
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.addData(recipe.ingredients);

        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.addData(recipe.steps);

        adapter.addFragment(ingredientsFragment, INGREDIENT_TAB);
        adapter.addFragment(stepsFragment, STEP_TAB);

        viewPager.setAdapter(adapter);
    }

}
