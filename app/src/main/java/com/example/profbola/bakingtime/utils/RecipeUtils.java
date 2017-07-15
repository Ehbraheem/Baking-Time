package com.example.profbola.bakingtime.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prof.BOLA on 7/6/2017.
 */

public class RecipeUtils {

    public static int getResourceIdFromName(Context context, String name) {
        String imageUrl = name.replaceAll("\\s", "_").toLowerCase();
        return context.getResources().getIdentifier(imageUrl, "drawable", context.getPackageName());
    }

    public static CharSequence formatServings(int servings) {
        return String.format("Served: %d", servings);
    }

    public interface IsPersistable<T> {

        List<T> consumeCusor(Cursor cursor);

    }
}
