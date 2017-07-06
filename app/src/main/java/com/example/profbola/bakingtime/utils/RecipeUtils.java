package com.example.profbola.bakingtime.utils;

import android.content.Context;

/**
 * Created by prof.BOLA on 7/6/2017.
 */

public class RecipeUtils {

    public static int getResourceIdFromName(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static CharSequence formatServings(int servings) {
        return String.format("Served: %d", servings);
    }
}
