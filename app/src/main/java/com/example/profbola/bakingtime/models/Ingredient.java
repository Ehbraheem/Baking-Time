package com.example.profbola.bakingtime.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.profbola.bakingtime.provider.RecipeContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prof.BOLA on 7/2/2017.
 */

public class Ingredient implements Parcelable {

    private static final String QUANTITY_KEY = "quantity";
    private static final String MEASURE_KEY = "measure";
    private static final String INGREDIENT_KEY = "ingredient";

    public final int quantity;
    public final String ingredient;
    public final String measure;

    public Ingredient(JSONObject object) throws JSONException {
        quantity = object.getInt(QUANTITY_KEY);
        ingredient = object.getString(INGREDIENT_KEY);
        measure = object.getString(MEASURE_KEY);
    }

    protected Ingredient(Parcel in) {
        quantity = in.readInt();
        ingredient = in.readString();
        measure = in.readString();
    }

    public Ingredient(Cursor cursor) {
        int quantityInd = cursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_QUANTITY);
        int ingredientInd = cursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_INGREDIENT);
        int measureInd = cursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_MEASURE);

        ingredient = cursor.getString(ingredientInd);
        quantity = cursor.getInt(quantityInd);
        measure = cursor.getString(measureInd);
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeString(ingredient);
        dest.writeString(measure);
    }

    public ContentValues toContentValues(int id) {
        ContentValues cv = new ContentValues();
        cv.put(RecipeContract.IngredientEntry.COLUMN_INGREDIENT, ingredient);
        cv.put(RecipeContract.IngredientEntry.COLUMN_MEASURE, measure);
        cv.put(RecipeContract.IngredientEntry.COLUMN_QUANTITY, quantity);
        cv.put(RecipeContract.IngredientEntry.COLUMN_RECIPE_ID, id);

        return cv;
    }
}
