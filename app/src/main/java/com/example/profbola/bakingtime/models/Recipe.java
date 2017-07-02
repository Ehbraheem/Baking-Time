package com.example.profbola.bakingtime.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prof.BOLA on 6/29/2017.
 */

public class Recipe implements Parcelable {

    private static final String SERVINGS_KEY = "servings";
    private static final String NAME_KEY = "name";
    private static final String IMAGE_KEY = "image";
    public static final String INGREDIENTS_KEY = "ingredients";
    public static final String STEPS_KEY = "steps";

    public final String name;
    public final String image;
    public final String servings;
    public final Ingredient[] ingredients;
    public final Step[] steps;

    public Recipe(JSONObject object) throws JSONException {
        name = object.getString(NAME_KEY);
        image = object.getString(IMAGE_KEY).equals("") ? name.replaceAll("\\s", "_").toLowerCase() : object.getString(IMAGE_KEY);
        servings = object.getString(SERVINGS_KEY);
        JSONArray stepsJson = object.getJSONArray(STEPS_KEY);
        JSONArray ingredientsJson = object.getJSONArray(INGREDIENTS_KEY);

        // FIXME: 7/2/2017 Extract method and DRY constructor
        // TODO: 7/2/2017 Found out its a bad practice to call methods in constructor
        steps = new Step[stepsJson.length()];
        for (int i = 0; i < stepsJson.length(); i++) {
            steps[i] = new Step(stepsJson.getJSONObject(i));
        }

        ingredients = new Ingredient[ingredientsJson.length()];
        for (int i = 0; i < ingredientsJson.length(); i++) {
            ingredients[i] = new Ingredient(ingredientsJson.getJSONObject(i));
        }
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        image = in.readString();
        servings = in.readString();
        ingredients = in.createTypedArray(Ingredient.CREATOR);
        steps = in.createTypedArray(Step.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(servings);
        dest.writeTypedArray(ingredients, flags);
        dest.writeTypedArray(steps, flags);
    }

//    protected Recipe(Parcel source) {
//        String[] data = new String[3];
//        Ingredient[] ingredientsParcel = new Ingredient[0];
//        Step[] stepsParcel;
//
//        source.readStringArray(data);
//
//
//        this.name = data[0];
//        this.image = data[1];
//        this.servings = data[2];
//        this.ingredients = source.readTypedArray(ingredientsParcel, Ingredient.CREATOR);
//        this.steps = source.readTypedArray(stepsParcel, Step.CREATOR);
//    }
//
//    public static final Parcelable.Creator<Recipe> CREATOR
//            = new Creator<Recipe>() {
//        @Override
//        public Recipe createFromParcel(Parcel source) {
//            return new Recipe(source);
//        }
//
//        @Override
//        public Recipe[] newArray(int size) {
//            return new Recipe[0];
//        }
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeString(image);
//        dest.writeString(servings);
//        dest.writeTypedArray(ingredients, PARCELABLE_WRITE_RETURN_VALUE);
//        dest.writeTypedArray(steps, PARCELABLE_WRITE_RETURN_VALUE);
//    }
}
