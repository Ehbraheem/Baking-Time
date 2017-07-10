package com.example.profbola.bakingtime.ui.widget;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Ingredient;
import com.example.profbola.bakingtime.provider.RecipeContract;

import static com.example.profbola.bakingtime.utils.RecipeConstants.RecipesWidgetProviderConstants.RECIPE_ID;


/**
 * Created by prof.BOLA on 7/9/2017.
 */

public class IngredientWidgetService extends RemoteViewsService{

    @Override public RemoteViewsFactory onGetViewFactory(Intent intent) {

        int recipeId = intent.getIntExtra(RECIPE_ID, 1);
        return new IngredientAdapterFactory(this.getApplicationContext(), recipeId);

    }
}

class IngredientAdapterFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;private int mRecipeId;private Cursor mIngredients;

    protected IngredientAdapterFactory(Context context, int recipeId) {

        mContext = context;

        mRecipeId = recipeId;

    }
    @Override
    public RemoteViews getViewAt(int position) {

        if (mIngredients == null || mIngredients.getCount() == 0) return null;

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient);

        mIngredients.moveToPosition(position);
        Ingredient ingredient = new Ingredient(mIngredients);
        views.setTextViewText(R.id.ingredient_name, ingredient.ingredient);
        views.setTextViewText(R.id.measure, ingredient.measure);
        views.setTextViewText(R.id.quantity, String.valueOf(ingredient.quantity));

        return views;
    }

    @Override public void onCreate() {

    }
    @Override
    public void onDataSetChanged() {

        if (mIngredients != null) mIngredients.close();

        mIngredients = mContext.getContentResolver().query(
                RecipeContract.IngredientEntry.CONTENT_URI(mRecipeId),
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDestroy() {

        if (mIngredients != null) mIngredients.close();

    }

    @Override
    public int getCount() {

        if (mIngredients == null) return 0;
        return mIngredients.getCount();

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}