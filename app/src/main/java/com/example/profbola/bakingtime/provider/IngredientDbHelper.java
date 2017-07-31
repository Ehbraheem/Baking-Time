package com.example.profbola.bakingtime.provider;

import android.database.sqlite.SQLiteDatabase;

import com.example.profbola.bakingtime.provider.RecipeContract.IngredientEntry;

import static com.example.profbola.bakingtime.utils.RecipeConstants.IngredientDbHelperConstants.INGREDIENT_RECIPE_ID_IDX;

/**
 * Created by prof.BOLA on 6/23/2017.
 */

public class IngredientDbHelper {

//    private static final String DATABASE_NAME = "bakingtime.db";

//    private static final int DATABASE_VERSION = 1;

//    public IngredientDbHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }

    public static void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + IngredientEntry.TABLE_NAME + " ( "       +
                IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "                                   +
                IngredientEntry.COLUMN_INGREDIENT + " STRING NOT NULL, "                                       +
                IngredientEntry.COLUMN_MEASURE + " STRING NOT NULL, "                                          +
                IngredientEntry.COLUMN_QUANTITY + " REAL NOT NULL, "                                           +
                IngredientEntry.COLUMN_RECIPE_ID + " INTEGER, "                                                +
                " FOREIGN KEY ( " + IngredientEntry.COLUMN_RECIPE_ID + " ) REFERENCES "                         +
                RecipeContract.RecipeEntry.TABLE_NAME + " ( " + RecipeContract.RecipeEntry.COLUMN_ID + " ) "   +

                " UNIQUE ( " + IngredientEntry.COLUMN_INGREDIENT + " , " + IngredientEntry.COLUMN_RECIPE_ID +  " ) ON CONFLICT REPLACE "                   +
                ");";

        final String SQL_CREATE_INDEX
                = "CREATE INDEX " + INGREDIENT_RECIPE_ID_IDX + " ON " + IngredientEntry.TABLE_NAME + " ( " + IngredientEntry.COLUMN_RECIPE_ID + " );";

        db.execSQL(SQL_CREATE_INGREDIENTS_TABLE);
        db.execSQL(SQL_CREATE_INDEX);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + IngredientEntry.TABLE_NAME);

        onCreate(db);
    }
}
