package com.example.profbola.bakingtime.provider;

import android.database.sqlite.SQLiteDatabase;

import com.example.profbola.bakingtime.provider.RecipeContract.StepEntry;
/**
 * Created by prof.BOLA on 6/23/2017.
 */

public class StepDbHelper {

//    private static final String DATABASE_NAME = "bakingtime.db";
//
//    private static final int DATABASE_VERSION = 1;

//    public StepDbHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }

    public static void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_STEPS_TABLE = "CREATE TABLE " + StepEntry.TABLE_NAME + " ( "                    +
                StepEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "                                          +
                StepEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "                                               +
                StepEntry.COLUMN_SHORT_DESCRIPTION + " TEXT NOT NULL, "                                         +
                StepEntry.COLUMN_THUMBNAIL_URL + " STRING, "                                                    +
                StepEntry.COLUMN_VIDEO_URL + " STRING, "                                                        +
                StepEntry.COLUMN_RECIPE_ID + " INTEGER, "                                                       +
                StepEntry.COLUMN_ID + " INTEGER NOT NULL, "                                                     +
                " FOREIGN KEY ( " + StepEntry.COLUMN_RECIPE_ID + " ) REFERENCES "                               +
                RecipeContract.RecipeEntry.TABLE_NAME + " ( " + RecipeContract.RecipeEntry.COLUMN_ID + " ) "    +
                " UNIQUE ( " + StepEntry.COLUMN_ID + " ) ON CONFLICT REPLACE "                                  +
                ");";

        db.execSQL(SQL_CREATE_STEPS_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + StepEntry.TABLE_NAME);

        onCreate(db);
    }
}
