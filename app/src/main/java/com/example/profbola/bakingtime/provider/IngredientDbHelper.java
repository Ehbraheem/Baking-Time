package com.example.profbola.bakingtime.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.profbola.bakingtime.provider.RecipeContract.IngredientEntry;

/**
 * Created by prof.BOLA on 6/23/2017.
 */

public class IngredientDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bakingtime.db";

    private static final int DATABASE_VERSION = 1;

    public IngredientDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + IngredientEntry.TABLE_NAME + " ( "       +
                IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "                                   +
                IngredientEntry.COLUMN_INGREDIENT + " STRING NOT NULL, "                                       +
                IngredientEntry.COLUMN_MEASURE + " STRING NOT NULL, "                                          +
                IngredientEntry.COLUMN_QUANTITY + " REAL NOT NULL, "                                           +
                IngredientEntry.COLUMN_RECIPE_ID + " INTEGER, "                                                +
                " FOREIGN KEY ( " + IngredientEntry.COLUMN_RECIPE_ID + " ) REFRENCES "                         +
                RecipeContract.RecipeEntry.TABLE_NAME + " ( " + RecipeContract.RecipeEntry.COLUMN_ID + " ) "   +

                " UNIQUE ( " + IngredientEntry.COLUMN_INGREDIENT + " ) ON CONFLICT REPLACE "                   +
                ");";

        db.execSQL(SQL_CREATE_INGREDIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + IngredientEntry.TABLE_NAME);

        onCreate(db);
    }
}
