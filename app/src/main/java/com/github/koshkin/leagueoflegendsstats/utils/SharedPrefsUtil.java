package com.github.koshkin.leagueoflegendsstats.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.koshkin.leagueoflegendsstats.MainActivity;

/**
 * Created by tehras on 1/14/16.
 * <p/>
 * SharedPreferences helper
 */
public class SharedPrefsUtil {

    public static void saveSharedPrefs(String name, String response, Context context) {
        context = fixContext(context);

        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);

        if (sharedPref != null) {
            sharedPref.edit().putString(name, response).apply();
        }
    }

    private static Context fixContext(Context context) {
        if (context != null && context instanceof MainActivity)
            return context.getApplicationContext();

        return context;
    }

    public static String getSharedPrefs(String name, Context context) {
        context = fixContext(context);

        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);

        if (sharedPref != null) {
            return sharedPref.getString(name, null);
        }
        return null;
    }
}
