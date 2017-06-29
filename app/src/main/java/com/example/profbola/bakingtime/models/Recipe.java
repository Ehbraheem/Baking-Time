package com.example.profbola.bakingtime.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prof.BOLA on 6/29/2017.
 */

public class Recipe {

    public static final String SERVINGS_KEY = "servings";
    public static final String NAME_KEY = "name";
    public static final String IMAGE_KEY = "image";

    public final String name;
    public final String image;
    public final String servings;

    public Recipe(JSONObject object) throws JSONException {
        name = object.getString(NAME_KEY);
        image = object.get(IMAGE_KEY).equals("") ? name.toLowerCase() : object.getString(IMAGE_KEY);
        servings = object.getString(SERVINGS_KEY);
    }
}
