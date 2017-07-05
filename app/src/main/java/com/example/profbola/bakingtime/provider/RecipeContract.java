package com.example.profbola.bakingtime.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by prof.BOLA on 6/22/2017.
 */

public class RecipeContract {

    public static final String AUTHORITY = "com.example.profbola.bakingapp.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_RECIPES = "recipes";

    public static final String PATH_INGREDIENTS = "ingredients";

    public static final String PATH_STEPS = "steps";

    public static final class RecipeEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(PATH_RECIPES)
                .build();

        public static final String TABLE_NAME = "recipes";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_SERVINGS = "servings";

        public static final String COLUMN_IMAGE = "image";

//        public static final String COLUMN_OVERVIEW = "overview";

        public static final String COLUMN_ID = "id";

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.ANY_CURSOR_ITEM_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;
    }

    public static final class IngredientEntry implements BaseColumns {

        public static final Uri CONTENT_URI(int recipeId) {
            return RecipeEntry.CONTENT_URI
                    .buildUpon()
                    .appendPath(String.valueOf(recipeId))
                    .appendPath(PATH_INGREDIENTS)
                    .build();
        }

        public static final String TABLE_NAME = "ingredients";

        public static final String COLUMN_QUANTITY = "quantity";

        public static final String COLUMN_RECIPE_ID = "recipe_id";

        public static final String COLUMN_MEASURE = "measure";

        public static final String COLUMN_INGREDIENT = "ingredient";

        public static Uri buildIgredientWithId(long id, int recipeId) {
            return buildUriWithObjectId(id, CONTENT_URI(recipeId));
        }

    }

    public static final class StepEntry implements BaseColumns {

        public static final Uri CONTENT_URI(int recipeId) {
            return RecipeEntry.CONTENT_URI
                    .buildUpon()
                    .appendPath(String.valueOf(recipeId))
                    .appendPath(PATH_STEPS)
                    .build();
        }

        public static final String TABLE_NAME = "steps";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_RECIPE_ID = "recipe_id";

        public static final String COLUMN_SHORT_DESCRIPTION = "short_description";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_VIDEO_URL = "video_url";

        public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";

        public static Uri buildStepWithId(long id, int recipeId) {
            return buildUriWithObjectId(id, CONTENT_URI(recipeId));
        }
    }

    public static Uri buildUriWithObjectId(long id, Uri uri) {
        return ContentUris.withAppendedId(uri, id);
    }
}
