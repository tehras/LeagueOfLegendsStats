package com.github.koshkin.leagueoflegendsstats.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tehras on 1/14/16.
 */
public class SharedPrefsUtil {

    public static void saveSharedPrefs(String name, String response, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        if (sharedPref != null) {
            sharedPref.edit().putString(name, response).apply();
        }
    }

    public static String getSharedPrefs(String name, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        if (sharedPref != null) {
            return sharedPref.getString(name, null);
        }
        return null;
    }
}
