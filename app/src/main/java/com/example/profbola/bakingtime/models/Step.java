package com.example.profbola.bakingtime.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.profbola.bakingtime.provider.RecipeContract;
import com.example.profbola.bakingtime.utils.RecipeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.profbola.bakingtime.utils.RecipeConstants.StepsConstants.*;

/**
 * Created by prof.BOLA on 7/2/2017.
 */

public class Step implements Parcelable, RecipeUtils.IsPersistable<Step> {

    public int id;
    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnailURL;

    public Step(){}

    public Step(JSONObject object) throws JSONException {
        shortDescription = object.getString(SHORT_DESCRIPTION_KEY);
        id = object.getInt(ID_KEY);
        description = object.getString(DESCRIPTION_KEY);
        videoURL = object.getString(VIDEO_URL_KEY);
        thumbnailURL = object.getString(THUMBNAIL_URL_KEY);
    }

    protected Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public Step(Cursor cursor) {
        int idInd = cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_ID);
        int videoUrlInd = cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_VIDEO_URL);
        int thumbnailUrlInd = cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_THUMBNAIL_URL);
        int descInd = cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_DESCRIPTION);
        int shortDescInd = cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_SHORT_DESCRIPTION);

        id = cursor.getInt(idInd);
        videoURL = cursor.getString(videoUrlInd);
        thumbnailURL = cursor.getString(thumbnailUrlInd);
        description = cursor.getString(descInd);
        shortDescription = cursor.getString(shortDescInd);
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    public ContentValues toContentValues(int recipeId) {
        ContentValues cv = new ContentValues();
        cv.put(RecipeContract.StepEntry.COLUMN_DESCRIPTION, description);
        cv.put(RecipeContract.StepEntry.COLUMN_RECIPE_ID, recipeId);
        cv.put(RecipeContract.StepEntry.COLUMN_SHORT_DESCRIPTION, shortDescription);
        cv.put(RecipeContract.StepEntry.COLUMN_THUMBNAIL_URL, thumbnailURL);
        cv.put(RecipeContract.StepEntry.COLUMN_VIDEO_URL, videoURL);
        cv.put(RecipeContract.StepEntry.COLUMN_ID, id);

        return cv;
    }

    @Override
    public List<Step> consumeCusor(Cursor cursor) {
        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            steps.add(new Step(cursor));
        }
        cursor.close();
        return steps;
    }

    public static List<Step> convertCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) return null;
        return new Step().consumeCusor(cursor);
    }
}
