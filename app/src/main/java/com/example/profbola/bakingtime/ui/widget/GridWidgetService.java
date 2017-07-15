package com.example.profbola.bakingtime.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.profbola.bakingtime.R;
import com.example.profbola.bakingtime.models.Recipe;
import com.example.profbola.bakingtime.provider.RecipeContract;
import com.example.profbola.bakingtime.ui.MainActivity;
import com.example.profbola.bakingtime.utils.RecipeUtils;

import static com.example.profbola.bakingtime.utils.RecipeConstants.RECIPE;

/**
 * Created by prof.BOLA on 7/6/2017.
 */

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewFactory(this.getApplicationContext());
    }
}

class GridRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;

    public GridRemoteViewFactory(Context appContext) {
        mContext = appContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                RecipeContract.RecipeEntry.CONTENT_URI,
                null,
                null,
                null,
                RecipeContract.RecipeEntry.COLUMN_WIDGET_LAST_DISPLAYED
        );
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;

        mCursor.moveToPosition(position);
        Recipe recipe = new Recipe(mCursor);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe);

        int imgRes = RecipeUtils.getResourceIdFromName(mContext, recipe.name);
        views.setTextViewText(R.id.recipe_title, recipe.name);
        views.setTextViewText(R.id.recipe_servings, RecipeUtils.formatServings(recipe.servings));
        views.setImageViewResource(R.id.recipe_image, imgRes);
//        views.setViewVisibility(R.id.widget_ingredients_listing, View.GONE);
//        views.setViewVisibility(R.id.widget_sync_recipes, View.GONE);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(RECIPE, recipe);

        views.setOnClickFillInIntent(R.id.recipe_image, fillInIntent);

        return views;
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
