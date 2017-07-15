package com.example.profbola.bakingtime.ui.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Recipe;
import com.example.profbola.bakingtime.services.RecipeService;
import com.example.profbola.bakingtime.ui.MainActivity;
import com.example.profbola.bakingtime.ui.RecipeDetailActivity;
import com.example.profbola.bakingtime.utils.RecipeUtils;

import static com.example.profbola.bakingtime.utils.RecipeConstants.RECIPE;
import static com.example.profbola.bakingtime.utils.RecipeConstants.RecipeServiceConstants.ACTION_SYC_RECIPES;
import static com.example.profbola.bakingtime.utils.RecipeConstants.RecipesWidgetProviderConstants.RECIPE_ID;

/**
 * Created by prof.BOLA on 7/4/2017.
 */

public class RecipesWidgetProvider extends AppWidgetProvider {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews rv;

        if (width < 300) {
            rv = getSingleRecipeRemoteViews(context, recipe);
        } else {
            rv = getBakersLogicGridRemoteViews(context);
        }

        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private static RemoteViews getBakersLogicGridRemoteViews(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget_grid_view);

        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        Intent appIntent = new Intent(context, RecipeDetailActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        views.setEmptyView(R.id.widget_grid_view, R.id.widget_empty_view);

        return views;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

        RecipeService.startActionUpdateRecipeWidget(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    private static RemoteViews getSingleRecipeRemoteViews(Context context, Recipe recipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.recipes_widget_provider);

        int id = RecipeUtils.getResourceIdFromName(context, recipe.image);
        // TODO: 7/12/2017 Undo after review
//        views.setImageViewResource(R.id.widget_recipe_image, id);
        views.setTextViewText(R.id.recipe_widget_ingredinets_title,
                context.getString(R.string.recipe_widget_ingredients, recipe.name));

//        views.setTextViewText(R.id.widget_recipe_servings, "served: " + String.valueOf(recipe.servings));
//        views.setViewVisibility(R.id.widget_recipe_image, View.GONE);
//        views.setViewVisibility(R.id.widget_recipe_name, View.GONE);
//        views.setViewVisibility(R.id.widget_recipe_servings, View.GONE);

//        Intent intent = new Intent(context, RecipeDetailActivity.class);
//        intent.putExtra(RECIPE, recipe);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        views.setOnClickPendingIntent(R.id.widget_recipe_image, pendingIntent);

        Intent ingredientIntent = new Intent(context, IngredientWidgetService.class);
        ingredientIntent.putExtra(RECIPE_ID, recipe.id);
        views.setRemoteAdapter(R.id.widget_ingredients_listing, ingredientIntent);

//        Intent syncIntent = new Intent(context, RecipeService.class);
//        syncIntent.setAction(ACTION_SYC_RECIPES);
//        PendingIntent syncPendingIntent = PendingIntent.getService(
//                context,
//                0,
//                syncIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );


//        views.setOnClickPendingIntent(R.id.widget_sync_recipes, syncPendingIntent);

        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeService.startActionUpdateRecipeWidget(context);
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                            int[] widgetIds, Recipe recipe) {

        for (int widgetId: widgetIds) {
            updateAppWidget(context, appWidgetManager, widgetId, recipe);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}
