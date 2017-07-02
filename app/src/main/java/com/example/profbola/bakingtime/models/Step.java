package com.example.profbola.bakingtime.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prof.BOLA on 7/2/2017.
 */

public class Step implements Parcelable {

    private static final String ID_KEY = "id";
    private static final String SHORT_DESCRIPTION_KEY = "shortDescription";
    private static final String DESCRIPTION_KEY = "description";
    private static final String VIDEO_URL_KEY = "videoURL";
    private static final String THUMBNAIL_URL_KEY = "thumbnailURL";

    public final int id;
    public final String shortDescription;
    public final String description;
    public final String videoURL;
    public final String thumbnailURL;


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
}
