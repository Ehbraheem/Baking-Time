package com.example.profbola.bakingtime.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by prof.BOLA on 6/29/2017.
 */

public class RecipeNetworkUtil {

    public static final String API_URL = "https://go.udacity.com/android-baking-app-json";

    private static URL makeApiUrlFromSring() {
        URL url = null;
        try {
            url = new URL(API_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static JSONArray getRecipeFromApi() {
        JSONArray recipes = null;
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) makeApiUrlFromSring().openConnection();
            InputStream stream = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder builder = new StringBuilder();
            String results;

            while ((results = reader.readLine()) != null) {
                builder.append(results);
            }

            recipes = new JSONArray(String.valueOf(builder));
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.disconnect();
        }
        return recipes;
    }

    public static boolean networkCheck (@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null;
    }
}