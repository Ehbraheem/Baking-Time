package com.example.profbola.bakingtime.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.os.ResultReceiver;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Ingredient;
import com.example.profbola.bakingtime.models.Recipe;
import com.example.profbola.bakingtime.models.Step;
import com.example.profbola.bakingtime.provider.RecipeContract;
import com.example.profbola.bakingtime.services.RecipeService;
import com.example.profbola.bakingtime.utils.RecipePagerAdapter;

import java.util.List;

/**
 * Created by prof.BOLA on 7/1/2017.
 */

public class RecipeDetailActivity extends AppCompatActivity implements
        IngredientsFragment.OnIngredientSelected,
        StepsFragment.OnVideoPlayerSelected,
        LoaderManager.LoaderCallbacks<Cursor> {

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

    public static final int STEP_LOADER_ID = 223;
    public static final int INGREDIENT_LOADER_ID = 445;

    private Recipe mRecipe;
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;

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
            Recipe recipe = (Recipe) getIntent().getExtras().get(MainActivity.RECIPE);
            // FIXME: 7/8/2017 Register Loaders
            mRecipe = recipe;

            mIngredientsFragment = new IngredientsFragment();

            mStepsFragment = new StepsFragment();

            setUpLoaders();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(mRecipe.name);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        int recipeId = mRecipe.id;
        switch (id) {

            case STEP_LOADER_ID:
                Uri STEP_URI = RecipeContract.StepEntry.CONTENT_URI(recipeId);
                String sortOrder = RecipeContract.StepEntry.COLUMN_ID + " ASC";

                return createQueryCursor(STEP_URI, sortOrder);

            case INGREDIENT_LOADER_ID:
                Uri INGREDIENT_URI = RecipeContract.IngredientEntry.CONTENT_URI(recipeId);

                return createQueryCursor(INGREDIENT_URI, null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch (loader.getId()) {
            case INGREDIENT_LOADER_ID:
                mIngredientsFragment = new IngredientsFragment();
                mIngredients = Ingredient.convertCursor(data);
                
                break;
            
            case STEP_LOADER_ID:
                mStepsFragment = new StepsFragment();
                mSteps = Step.convertCursor(data);
                
                break;
            
            default:
                throw new RuntimeException("Loader Not Implemented: " + loader.getId());
        }

        // FIXME: 7/8/2017 SetUp Fragments
        if (mSteps != null && mIngredients != null) {
            deliverDataToFragments(mSteps, mIngredients);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private CursorLoader createQueryCursor(Uri contentUri, String sortOrder) {
        return new CursorLoader(this,
                                contentUri,
                                null,
                                null,
                                null,
                                sortOrder);
    }

    private void setUpLoaders() {
        getSupportLoaderManager().restartLoader(STEP_LOADER_ID, null, this);
        getSupportLoaderManager().restartLoader(INGREDIENT_LOADER_ID, null, this);
    }

    private void deliverDataToFragments(List<Step> steps, List<Ingredient> ingredients) {

        mIngredientsFragment = (IngredientsFragment) viewPager.getAdapter().instantiateItem(viewPager, 0);
        mIngredientsFragment.addData(ingredients.toArray(new Ingredient[ingredients.size()]));
        mStepsFragment = (StepsFragment) viewPager.getAdapter().instantiateItem(viewPager, 1);
        mStepsFragment.addData(steps.toArray(new Step[steps.size()]));

        viewPager.getAdapter().notifyDataSetChanged();
    }
}
