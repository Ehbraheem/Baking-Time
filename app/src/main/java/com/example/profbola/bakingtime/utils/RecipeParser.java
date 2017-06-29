package com.example.profbola.bakingtime.utils;

import com.example.profbola.bakingtime.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prof.BOLA on 6/29/2017.
 */

public class RecipeParser {

    public static List<Recipe> parse(JSONArray array) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);
                recipes.add(new Recipe(json));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
