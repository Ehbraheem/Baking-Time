package com.example.profbola.bakingtime.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.profbola.bakingtime.provider.RecipeContract;
import com.example.profbola.bakingtime.utils.RecipeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.profbola.bakingtime.utils.RecipeConstants.RecipesConstants.*;

/**
 * Created by prof.BOLA on 6/29/2017.
 */

public class Recipe implements Parcelable, RecipeUtils.IsPersistable<Recipe> {


    public String name;
    public int id;
    public String image;
    public int servings;
    public Ingredient[] ingredients;
    public Step[] steps;

    public Recipe() {}

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

    @Override
    public List<Recipe> consumeCusor(Cursor cursor) {
        List<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            recipes.add(new Recipe(cursor));
        }
        cursor.close();
        return recipes;
    }

    public static List<Recipe> convertCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) return null;
        return new Recipe().consumeCusor(cursor);
    }
}
