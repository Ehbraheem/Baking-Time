package com.example.profbola.bakingtime.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Ingredient;
import com.example.profbola.bakingtime.models.Recipe;
import com.example.profbola.bakingtime.models.Step;
import com.example.profbola.bakingtime.provider.RecipeContract;
import com.example.profbola.bakingtime.ui.RecipeDetailActivity;
import com.example.profbola.bakingtime.ui.widget.IngredientWidgetService;
import com.example.profbola.bakingtime.ui.widget.RecipesWidgetProvider;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.example.profbola.bakingtime.provider.RecipeContract.RecipeEntry.CONTENT_URI;
import static com.example.profbola.bakingtime.utils.RecipeConstants.RECIPE;
import static com.example.profbola.bakingtime.utils.RecipeConstants.RecipeServiceConstants.*;

/**
 * Created by prof.BOLA on 7/5/2017.
 */

public class RecipeService extends IntentService {

    private static final String NAME = RecipeService.class.getName();

    /**
     * Creates an IntentService.  Invoked by the superclass's constructor.
     */
    public RecipeService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SYC_RECIPES.equals(action)) {
                handleActionSynRecipes();
            } else if (ACTION_GET_ASSOCIATED_DATA.equals(action)) {
                Recipe recipe = intent.getParcelableExtra(ACTION_GET_ASSOCIATED_DATA);
                handleActionGetAssociatedData(recipe);
            } else if (UPDATE_RECIPE_WIDGET.equals(action)) {
                handleUpdateRecipeWidget();
            } else if (LAST_VIEWED_RECIPE_INGREDIENTS.equals(action)) {
                if (intent.hasExtra(RECIPE)) {
                    Recipe recipe = intent.getParcelableExtra(RECIPE);
                    handleLastViewedRecipeIngredients(recipe);
                }
            }
        }
    }

    private void handleLastViewedRecipeIngredients(Recipe recipe) {
        changeCurrentWidgetRecipeLastSeen(recipe);
        startActionUpdateRecipeWidget(this);

    }

    private void handleUpdateRecipeWidget() {
        Recipe recipe = null;
        Cursor cursor = getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                RecipeContract.RecipeEntry.COLUMN_WIDGET_LAST_DISPLAYED + " DESC"
        );

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            recipe = new Recipe(cursor);
            cursor.close();
        }

        if (recipe != null) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipesWidgetProvider.class));

            IngredientWidgetService.setRecipeId(recipe.id);
            appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.widget_ingredients_listing);
            appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.widget_grid_view);
            RecipesWidgetProvider.updateRecipeWidgets(this, appWidgetManager, widgetIds, recipe);
        }
    }

    private void handleActionGetAssociatedData(Recipe recipe) {
        Uri STEP_URI = RecipeContract.StepEntry.CONTENT_URI(recipe.id);
        Uri INGREDIENT_URI = RecipeContract.IngredientEntry.CONTENT_URI(recipe.id);

        Cursor stepCursor = getContentResolver().query(
                STEP_URI,
                null,
                null,
                null,
                RecipeContract.StepEntry.COLUMN_ID + " ASC"
        );

        Cursor ingCursor = getContentResolver().query(
                INGREDIENT_URI,
                null,
                null,
                null,
                null
        );

        if (checkCursors(ingCursor, stepCursor)) {
            List<Step> steps = Step.convertCursor(stepCursor);
            List<Ingredient> ingredients = Ingredient.convertCursor(ingCursor);
            recipe.steps = steps.toArray(new Step[steps.size()]);
            recipe.ingredients = ingredients.toArray(new Ingredient[ingredients.size()]);
        }
    }

    private void handleActionSynRecipes() {
        RecipeTasks.writeRecipes(this);
    }

    public static void startActionLastViewedRecipeIngredients(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(LAST_VIEWED_RECIPE_INGREDIENTS);
        intent.putExtra(RECIPE, recipe);
        context.startService(intent);
    }

    public static void startActionSyncRecipes(Context context) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(ACTION_SYC_RECIPES);
        context.startService(intent);
    }

    public static void startActionUpdateRecipeWidget(Context context) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(UPDATE_RECIPE_WIDGET);
        context.startService(intent);
    }

    public static void startGetAssociatedData(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(ACTION_GET_ASSOCIATED_DATA);
        intent.putExtra(ACTION_GET_ASSOCIATED_DATA,recipe);
        context.startService(intent);
    }


    // FIXME: 7/5/2017 F**k Java does not support Truthy values
    private boolean checkCursors(Cursor ingCursor, Cursor stepCursor) {
        return (ingCursor != null && ingCursor.getCount() > 0) &&
                (stepCursor != null && stepCursor.getCount() > 0);
    }

    private void changeCurrentWidgetRecipeLastSeen(Recipe recipe) {
        if (recipe != null) {
            Uri RECIPE_URI = CONTENT_URI.buildUpon()
                    .appendPath(String.valueOf(recipe.id))
                    .build();

            getContentResolver()
                    .update(RECIPE_URI,
                            recipe.toContentValues(),
                            null,
                            null);
        }
    }

    // FIXME: 7/15/2017 Test ContentProvider delete
    public static boolean deleteIngredient(Context context) {
        Uri uri = RecipeContract.IngredientEntry.CONTENT_URI(1)
                .buildUpon().appendPath(String.valueOf(1)).build();
        int numDeleted = context.getContentResolver()
                .delete(uri, null, null);

        return numDeleted > 0;
    }
}
