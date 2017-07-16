package com.example.profbola.bakingtime.provider;

import android.database.sqlite.SQLiteDatabase;

import com.example.profbola.bakingtime.provider.RecipeContract.StepEntry;

import static com.example.profbola.bakingtime.utils.RecipeConstants.StepDbHelperConstants.STEP_RECIPE_ID_IDX;

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
                " UNIQUE ( " + StepEntry.COLUMN_DESCRIPTION + " ) ON CONFLICT REPLACE "                           +
                ");";

        final String SQL_CREATE_INDEX
                = "CREATE INDEX " + STEP_RECIPE_ID_IDX + " ON " + StepEntry.TABLE_NAME + " ( " + StepEntry.COLUMN_RECIPE_ID + " );";

        db.execSQL(SQL_CREATE_STEPS_TABLE);
        db.execSQL(SQL_CREATE_INDEX);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + StepEntry.TABLE_NAME);

        onCreate(db);
    }
}
