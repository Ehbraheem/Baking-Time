package com.example.profbola.bakingtime.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.profbola.bakingtime.utils.RecipeConstants.DbHelperConstants.*;

/**
 * Created by prof.BOLA on 7/7/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        RecipeDbHelper.onCreate(db);
        StepDbHelper.onCreate(db);
        IngredientDbHelper.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        RecipeDbHelper.onUpgrade(db, oldVersion, newVersion);
        StepDbHelper.onUpgrade(db, oldVersion, newVersion);
        IngredientDbHelper.onUpgrade(db, oldVersion, newVersion);
    }
}
