package com.example.profbola.bakingtime.provider;

import android.database.sqlite.SQLiteDatabase;

import com.example.profbola.bakingtime.provider.RecipeContract.RecipeEntry;

/**
 * Created by prof.BOLA on 6/23/2017.
 */

public class RecipeDbHelper {

//    private static final String DATABASE_NAME = "bakingtime.db";

//    private static final int DATABASE_VERSION = 1;

//    public RecipeDbHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }

    public static void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_RECIPES_TABLE = "CREATE TABLE " + RecipeEntry.TABLE_NAME + " ( " +
                RecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "                         +
                RecipeEntry.COLUMN_NAME + " TEXT NOT NULL, "                                     +
                RecipeEntry.COLUMN_IMAGE + " STRING, "                                           +
                RecipeEntry.COLUMN_SERVINGS + " INTEGER NOT NULL, "                              +
                RecipeEntry.COLUMN_ID + " INTEGER NOT NULL, "                                    +
                RecipeEntry.COLUMN_WIDGET_LAST_DISPLAYED + " TIMESTAMP, "                        +
                "UNIQUE ( "  +  RecipeEntry.COLUMN_NAME  +  ", " + RecipeEntry.COLUMN_ID         +
                " ) ON CONFLICT REPLACE "                                                        +
                "); ";

        db.execSQL(SQL_CREATE_RECIPES_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RecipeEntry.TABLE_NAME);

        onCreate(db);
    }
}
