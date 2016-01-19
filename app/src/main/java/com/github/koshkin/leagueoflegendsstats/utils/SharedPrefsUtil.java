package com.github.koshkin.leagueoflegendsstats.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.models.Favorite;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tehras on 1/14/16.
 */
public class SharedPrefsUtil {

    public static void saveSharedPrefs(String name, String response, Context context) {
        context = fixContext(context);

        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);

        if (sharedPref != null) {
            sharedPref.edit().putString(name, response).apply();
        }
    }

    public static void removeFromSharedPrefs(Favorite favorite, String prefName, Context context) {
        context = fixContext(context);

        if (favorite == null || favorite.getSummonerId() == null)
            return;

        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);

        if (prefs != null) {
            Set<String> set = prefs.getStringSet(prefName, null);

            if (set != null && removeFavoriteFromSet(favorite, set)) {
                prefs.edit().putStringSet(prefName, set).apply();
            }
        }
    }

    private static boolean removeFavoriteFromSet(Favorite favorite, Set<String> set) {
        String stringToRemove = getFavorite(favorite, set);

        if (stringToRemove != null) {
            set.remove(stringToRemove);
            return true;
        }

        return false;
    }

    private static final String TAG = "SharedPrefsUtils";

    public static void addToSharedPrefs(Favorite favorite, String prefName, Context context) {
        context = fixContext(context);

        if (favorite == null || favorite.getSummonerId() == null)
            return;

        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);

        if (prefs != null) {
            Set<String> set = prefs.getStringSet(prefName, null);

            if (set == null)
                set = new HashSet<>();

            addToSet(favorite, set);

            Log.e(TAG, "" + set.size());
            prefs.edit().putStringSet(prefName, set).apply();
        }
    }

    private static void addToSet(Favorite addFavorite, Set<String> set) {
        String stringToRemove = getFavorite(addFavorite, set);

        if (stringToRemove != null) {
            set.remove(stringToRemove);
        }

        set.add(addFavorite.toJson());
    }

    private static String getFavorite(Favorite addFavorite, Set<String> set) {
        for (String string : set) {
            Favorite favorite = Favorite.fromJson(string);
            if (favorite.getSummonerId().equalsIgnoreCase(addFavorite.getSummonerId())) {
                return string;
            }
        }
        return null;
    }

    private static void addIfAlreadyContains(Favorite addFavorite, Set<String> set) {
        if (addFavorite == null)
            return;

        String stringToRemove = getFavorite(addFavorite, set);

        if (stringToRemove != null) {
            set.remove(stringToRemove);
            set.add(addFavorite.toJson());
        }
    }

    public static Set<String> getFromSharedPrefs(Favorite favorite, String prefName, Context context) {
        context = fixContext(context);
        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);

        if (prefs != null) {
            Set<String> set = prefs.getStringSet(prefName, null);
            addIfAlreadyContains(favorite, set);

            Log.e(TAG, "" + (set != null ? set.size() : 0));
            return set;
        }
        return null;
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
