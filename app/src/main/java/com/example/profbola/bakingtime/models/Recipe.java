package com.example.profbola.bakingtime.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.profbola.bakingtime.provider.RecipeContract;

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
    public static final String ID_KEY = "id";

    public final String name;
    public final int id;
    public final String image;
    public final int servings;
    public Ingredient[] ingredients;
    public Step[] steps;

    public Recipe(JSONObject object) throws JSONException {
        name = object.getString(NAME_KEY);
        image = object.getString(IMAGE_KEY).equals("") ? name.replaceAll("\\s", "_").toLowerCase() : object.getString(IMAGE_KEY);
        servings = object.getInt(SERVINGS_KEY);
        id = object.getInt(ID_KEY);
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
        servings = in.readInt();
        id = in.readInt();
        ingredients = in.createTypedArray(Ingredient.CREATOR);
        steps = in.createTypedArray(Step.CREATOR);
    }

    public Recipe(Cursor cursor) {
        int nameInd = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME);
        int idInd = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_ID);
        int imageInd = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_IMAGE);
        int servingsInd = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_SERVINGS);

        id = cursor.getInt(idInd);
        name = cursor.getString(nameInd);
        image = cursor.getString(imageInd);
        servings = cursor.getInt(servingsInd);
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
        dest.writeInt(servings);
        dest.writeInt(id);
        dest.writeTypedArray(ingredients, flags);
        dest.writeTypedArray(steps, flags);
    }

    public ContentValues toContentValues() {
        ContentValues cv  = new ContentValues();
        cv.put(RecipeContract.RecipeEntry.COLUMN_ID, id);
        cv.put(RecipeContract.RecipeEntry.COLUMN_IMAGE, image);
        cv.put(RecipeContract.RecipeEntry.COLUMN_NAME, name);
        cv.put(RecipeContract.RecipeEntry.COLUMN_SERVINGS, servings);

        long displayedTime = System.currentTimeMillis();
        cv.put(RecipeContract.RecipeEntry.COLUMN_WIDGET_LAST_DISPLAYED, displayedTime);

        return cv;
    }
}
