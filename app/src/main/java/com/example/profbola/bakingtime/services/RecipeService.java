package com.example.profbola.bakingtime.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.profbola.bakingtime.models.Recipe;
import com.example.profbola.bakingtime.utils.RecipeNetworkUtil;
import com.example.profbola.bakingtime.utils.RecipeParser;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by prof.BOLA on 7/5/2017.
 */

public class RecipeService extends IntentService {


    private static final String NAME = RecipeService.class.getName();

    public static final String ACTION_SYC_RECIPES
            = "com.example.profbola.bakingtime.services.action.sync_recipes";
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
            }
        }
    }

    private void handleActionSynRecipes() {
        RecipeTasks.writeRecipes(this);
    }

    public static void startActionSyncRecipes(Context context) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(ACTION_SYC_RECIPES);
        context.startService(intent);
    }
}
