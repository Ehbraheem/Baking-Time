package com.example.profbola.bakingtime.services;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.profbola.bakingtime.models.Ingredient;
import com.example.profbola.bakingtime.models.Recipe;
import com.example.profbola.bakingtime.models.Step;
import com.example.profbola.bakingtime.provider.RecipeContract;
import com.example.profbola.bakingtime.utils.RecipeNetworkUtil;
import com.example.profbola.bakingtime.utils.RecipeParser;

import org.json.JSONArray;

import java.util.List;

import static com.example.profbola.bakingtime.provider.RecipeContract.RecipeEntry.CONTENT_URI;

/**
 * Created by prof.BOLA on 7/5/2017.
 */

public class RecipeTasks {

    synchronized static void writeRecipes(@NonNull Context context) {

        JSONArray jsonArray = RecipeNetworkUtil.getRecipeFromApi();
        List<Recipe> recipeParser = RecipeParser.parse(jsonArray);

        for (Recipe recipe: recipeParser) {
            insertRecipe(context, recipe);
            insertIngredients(context, recipe);
            insertSteps(context, recipe);
        }

    }

    private static void insertSteps(Context context, Recipe recipe) {
        int id = recipe.id;
        for (Step step: recipe.steps) {
            context.getContentResolver().insert(
                    RecipeContract.StepEntry.CONTENT_URI(id),
                    step.toContentValues(id)
            );
        }
    }

    private static void insertIngredients(Context context, Recipe recipe) {
        int id = recipe.id;
        for (Ingredient ingredient: recipe.ingredients) {
            context.getContentResolver().insert(
                    RecipeContract.IngredientEntry.CONTENT_URI(id),
                    ingredient.toContentValues(id)
            );
        }
    }

    private static void insertRecipe(Context context, Recipe recipe) {
        context.getContentResolver().insert(
                CONTENT_URI,
                recipe.toContentValues()
        );
    }
}
