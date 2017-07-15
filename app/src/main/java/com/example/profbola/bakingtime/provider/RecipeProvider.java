package com.example.profbola.bakingtime.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.profbola.bakingtime.provider.RecipeContract.*;

import java.util.Arrays;

import static com.example.profbola.bakingtime.utils.RecipeConstants.RecipeProviderConstants.*;

/**
 * Created by prof.BOLA on 6/23/2017.
 */

public class RecipeProvider extends ContentProvider {

    private DbHelper dbHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();


    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RecipeContract.AUTHORITY;

        matcher.addURI(authority, RecipeContract.PATH_RECIPES, CODE_RECIPES);

        matcher.addURI(authority, RecipeContract.PATH_RECIPES + "/#", CODE_SINGLE_RECIPE);

        matcher.addURI(authority,
                RecipeContract.PATH_RECIPES + "/#/" + RecipeContract.PATH_INGREDIENTS,
                CODE_SINGLE_RECIPE_INGREDIENTS);

        matcher.addURI(authority,
                RecipeContract.PATH_RECIPES + "/#/" + RecipeContract.PATH_STEPS,
                CODE_SINGLE_RECIPE_STEPS);

        matcher.addURI(authority,
                RecipeContract.PATH_RECIPES + "/#/" + RecipeContract.PATH_STEPS + "/#",
                CODE_SINGLE_RECIPE_SINGLE_STEP);

        matcher.addURI(authority,
                RecipeContract.PATH_RECIPES + "/#/" + RecipeContract.PATH_INGREDIENTS + "/#",
                CODE_SINGLE_RECIPE_SINGLE_INGREDIENT);

        return matcher;
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        dbHelper = new DbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        int match = sUriMatcher.match(uri);
        Cursor retCusor;
        final SQLiteDatabase db;
        String recipeId;

        switch (match) {
            case CODE_RECIPES:
                db = dbHelper.getReadableDatabase();

                retCusor = db.query(RecipeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_SINGLE_RECIPE:

                db = dbHelper.getReadableDatabase();
                recipeId = uri.getLastPathSegment();
                String recipeSelection = makeSelection(RecipeEntry._ID);
                String[] recipeSelectionArgs = makeSelectionArgs(recipeId);

                retCusor = db.query(RecipeEntry.TABLE_NAME,
                        projection,
                        recipeSelection,
                        recipeSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_SINGLE_RECIPE_INGREDIENTS:

                db = dbHelper.getReadableDatabase();
                recipeId = uri.getPathSegments().get(1);
                String ingredientSelection = makeSelection(IngredientEntry.COLUMN_RECIPE_ID);
                String[] ingredientSelectionArgs = makeSelectionArgs(recipeId);

                retCusor = db.query(IngredientEntry.TABLE_NAME,
                        projection,
                        ingredientSelection,
                        ingredientSelectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case CODE_SINGLE_RECIPE_STEPS:

                db = dbHelper.getReadableDatabase();
                recipeId = uri.getPathSegments().get(1);
                String stepSelection = makeSelection(StepEntry.COLUMN_RECIPE_ID);
                String[] stepArgs = makeSelectionArgs(recipeId);

                retCusor = db.query(StepEntry.TABLE_NAME,
                        projection,
                        stepSelection,
                        stepArgs,
                        null,
                        null,
                        sortOrder
                );

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCusor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCusor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db;

        int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        String recipeId;
        long id;

        switch (match) {

            case CODE_RECIPES:

                db = dbHelper.getWritableDatabase();
                id = db.insert(RecipeEntry.TABLE_NAME,
                        null,
                        values);
                if (id > 0) {
                    returnUri = uri;
                }
                break;

            case CODE_SINGLE_RECIPE_INGREDIENTS:

                db = dbHelper.getWritableDatabase();
                recipeId = uri.getPathSegments().get(1);

                id = db.insert(IngredientEntry.TABLE_NAME,
                        null,
                        values);
                if (id > 0) {
                    returnUri = IngredientEntry.buildIgredientWithId(id, Integer.parseInt(recipeId));
                }
                break;

            case CODE_SINGLE_RECIPE_STEPS:

                db = dbHelper.getWritableDatabase();
                recipeId = uri.getPathSegments().get(1);

                id = db.insert(StepEntry.TABLE_NAME,
                        null,
                        values);
                if (id > 0) {
                    returnUri = StepEntry.buildStepWithId(id, Integer.parseInt(recipeId));
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db;
        int match = sUriMatcher.match(uri);
        int insertedRecords = 0;
        String recipeId;

        switch (match) {

            case CODE_RECIPES:
                db = dbHelper.getWritableDatabase();
                db.beginTransaction();

                try {
                    for (ContentValues value: values) {
                        long _id = db.insert(RecipeEntry.TABLE_NAME, null, value);
                        if (_id > 0) insertedRecords++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                return insertedRecords;

            case CODE_SINGLE_RECIPE_INGREDIENTS:
                db = dbHelper.getWritableDatabase();
                recipeId = uri.getPathSegments().get(1);
                db.beginTransaction();

                try {
                    for (ContentValues value: values) {
                        value.put(IngredientEntry.COLUMN_RECIPE_ID, recipeId);
                        long _id = db.insert(IngredientEntry.TABLE_NAME, null, value);
                        if (_id > 0) insertedRecords++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                return insertedRecords;

            case CODE_SINGLE_RECIPE_STEPS:
                db = dbHelper.getWritableDatabase();
                recipeId = uri.getPathSegments().get(1);
                db.beginTransaction();

                try {
                    for (ContentValues value: values) {
                        value.put(StepEntry.COLUMN_RECIPE_ID, recipeId);
                        long _id = db.insert(StepEntry.TABLE_NAME, null, value);
                        if (_id > 0) insertedRecords++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                return insertedRecords;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int numDeleted;
        final SQLiteDatabase db;
        int match = sUriMatcher.match(uri);
        String recipeId;

        switch (match) {

            case CODE_SINGLE_RECIPE:

                db = dbHelper.getWritableDatabase();
                String id = uri.getLastPathSegment();
                String recipeSelection = makeSelection(RecipeEntry.COLUMN_ID);
                String[] recipeSelectionArgs = makeSelectionArgs(id);
                numDeleted = db.delete(RecipeEntry.TABLE_NAME,
                        recipeSelection,
                        recipeSelectionArgs
                );
                break;

            case CODE_SINGLE_RECIPE_SINGLE_INGREDIENT:

                db = dbHelper.getWritableDatabase();
                recipeId = uri.getPathSegments().get(1);
                String ingredientId = uri.getLastPathSegment();
                String ingredientSelection = makeSelection(IngredientEntry.COLUMN_RECIPE_ID, IngredientEntry._ID);
                String[] ingredientSelectionArgs = makeSelectionArgs(recipeId, ingredientId);
                numDeleted = db.delete(IngredientEntry.TABLE_NAME,
                        ingredientSelection,
                        ingredientSelectionArgs
                );
                break;

            case CODE_SINGLE_RECIPE_SINGLE_STEP:

                db = dbHelper.getWritableDatabase();
                recipeId = uri.getPathSegments().get(1);
                String stepId = uri.getLastPathSegment();
                String stepSelection = makeSelection(StepEntry.COLUMN_RECIPE_ID, StepEntry.COLUMN_ID);
                String[] stepArgs = makeSelectionArgs(recipeId, stepId);
                numDeleted = db.delete(StepEntry.TABLE_NAME,
                        stepSelection,
                        stepArgs
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (numDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int numUpdated;
        final SQLiteDatabase db;
        int match = sUriMatcher.match(uri);
        String recipeId;

        switch (match) {

            case CODE_SINGLE_RECIPE:

                db = dbHelper.getWritableDatabase();
                String id = uri.getLastPathSegment();
                String recipeSelection = makeSelection(RecipeEntry.COLUMN_ID);
                String[] recipeSelectionArgs = makeSelectionArgs(id);
                numUpdated = db.update(RecipeEntry.TABLE_NAME,
                        values,
                        recipeSelection,
                        recipeSelectionArgs
                );
                break;

            case CODE_SINGLE_RECIPE_SINGLE_INGREDIENT:

                db = dbHelper.getWritableDatabase();
                recipeId = uri.getPathSegments().get(1);
                String ingredientId = uri.getLastPathSegment();
                String ingredientSelection = makeSelection(IngredientEntry.COLUMN_RECIPE_ID, IngredientEntry._ID);
                String[] ingredientSelectionArgs = makeSelectionArgs(recipeId, ingredientId);
                numUpdated = db.update(IngredientEntry.TABLE_NAME,
                        values,
                        ingredientSelection,
                        ingredientSelectionArgs
                );
                break;

            case CODE_SINGLE_RECIPE_SINGLE_STEP:

                db = dbHelper.getWritableDatabase();
                recipeId = uri.getPathSegments().get(1);
                String stepId = uri.getLastPathSegment();
                String stepSelection = makeSelection(StepEntry.COLUMN_RECIPE_ID, StepEntry.COLUMN_ID);
                String[] stepArgs = makeSelectionArgs(recipeId, stepId);
                numUpdated = db.update(StepEntry.TABLE_NAME,
                        values,
                        stepSelection,
                        stepArgs
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numUpdated;
    }

    private String[] makeSelectionArgs(@NonNull Object... args) {
        String[] result = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            result[i] = String.valueOf(args[i]);
        }

        return result;
    }

    private String makeSelection(@NonNull String... args) {
        StringBuilder selectionBuilder = new StringBuilder();
//        String selection = TextUtils.join(", ", args);
        String ques = " = ?";

        int lenght = args.length;
        if (lenght == 1) {
            selectionBuilder.append(args[0]).append(ques);
        } else {
            for (String select : args) {
                selectionBuilder = selectLogic(selectionBuilder, select, ques);
            }
        }


        return selectionBuilder.toString();
    }

    private StringBuilder selectLogic(StringBuilder builder, String select, String ques) {
        return builder.toString().endsWith(" AND ") ? builder.append(select).append(ques) : builder.append(select).append(ques).append(" AND ");
    }
}
