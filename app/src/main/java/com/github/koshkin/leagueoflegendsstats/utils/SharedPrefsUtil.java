package com.github.koshkin.leagueoflegendsstats.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.models.Favorite;
import com.github.koshkin.leagueoflegendsstats.models.Favorites;

import java.util.ArrayList;

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

    public static void removeFromSharedPrefs(Favorite favorite, String prefName, Context context) {
        context = fixContext(context);

        if (favorite == null || favorite.getSummonerId() == null)
            return;

        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);

        if (prefs != null) {
            String string = prefs.getString(prefName, null);

            string = removeFavoriteFromSet(favorite, string);
            if (string != null) {
                prefs.edit().putString(prefName, string).apply();
            }
        }
    }

    private static String removeFavoriteFromSet(Favorite favorite, String set) {
        if (!NullChecker.isNullOrEmpty(set)) {
            Favorites favorites = Favorites.fromJson(set);
            if (favorites != null && favorites.getFavorites() != null) {
                Favorite favoriteToRemove = getFavorite(favorite, favorites.getFavorites());

                if (favoriteToRemove != null) {
                    favorites.getFavorites().remove(favoriteToRemove);
                    return favorites.toJson();
                }
            }
        }

        return null;
    }

    public static void addToSharedPrefs(Favorite favorite, String prefName, Context context) {
        context = fixContext(context);

        if (favorite == null || favorite.getSummonerId() == null)
            return;

        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);

        if (prefs != null) {
            String set = prefs.getString(prefName, null);

            Favorites favorites = null;
            if (!NullChecker.isNullOrEmpty(set)) {
                favorites = Favorites.fromJson(set);
            }
            if (favorites == null || favorites.getFavorites() == null) {
                favorites = new Favorites();
            }

            addToSet(favorite, favorites);

            prefs.edit().putString(prefName, favorites.toJson()).apply();
        }
    }

    private static void addToSet(Favorite addFavorite, Favorites favorites) {
        Favorite favorite = getFavorite(addFavorite, favorites.getFavorites());

        if (favorite != null) {
            favorites.getFavorites().remove(favorite);
        }

        favorites.addFavorite(addFavorite);
    }

    private static Favorite getFavorite(Favorite addFavorite, ArrayList<Favorite> favorites) {
        if (addFavorite != null && favorites != null && favorites.size() > 0)
            for (Favorite favorite : favorites) {
                if (favorite != null && favorite.getSummonerId().equalsIgnoreCase(addFavorite.getSummonerId())) {
                    return favorite;
                }
            }

        return null;
    }

    private static Favorites addIfAlreadyContains(Favorite addFavorite, String set, SharedPreferences.Editor edit, String key) {
        if (!NullChecker.isNullOrEmpty(set)) {
            Favorites favorites = Favorites.fromJson(set);
            if (addFavorite != null && favorites != null && favorites.getFavorites() != null) {
                Favorite favorite = getFavorite(addFavorite, favorites.getFavorites());
                if (favorite != null) {
                    favorites.removeFavorite(favorite);
                    favorites.addFavorite(addFavorite);

                    edit.putString(key, favorites.toJson()).apply();
                }
            }
            return favorites;
        }

        return null;
    }

    public static Favorites getFromSharedPrefs(Favorite favorite, String prefName, Context context) {
        context = fixContext(context);
        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);

        if (prefs != null) {
            String set = prefs.getString(prefName, null);
            SharedPreferences.Editor edit = prefs.edit();

            Favorites favorites = addIfAlreadyContains(favorite, set, edit, prefName);
            edit.apply();

            return favorites;
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
