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

//    public static void removeFromSharedPrefs(SimpleSummoner favorite, String prefName, Context context) {
//        context = fixContext(context);
//
//        if (favorite == null || favorite.getSummonerId() == null)
//            return;
//
//        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
//
//        if (prefs != null) {
//            String string = prefs.getString(prefName, null);
//
//            string = removeFavoriteFromSet(favorite, string);
//            if (string != null) {
//                prefs.edit().putString(prefName, string).apply();
//            }
//        }
//    }

//    private static String removeFavoriteFromSet(SimpleSummoner favorite, String set) {
//        if (!NullChecker.isNullOrEmpty(set)) {
//            SimpleSummonerComparator favorites = SimpleSummonerComparator.fromJson(set);
//            if (favorites != null && favorites.getFavorites() != null) {
//                SimpleSummoner favoriteToRemove = getFavorite(favorite, favorites.getFavorites());
//
//                if (favoriteToRemove != null) {
//                    favorites.getFavorites().remove(favoriteToRemove);
//                    return favorites.toJson();
//                }
//            }
//        }
//
//        return null;
//    }

//    public static void addToSharedPrefs(SimpleSummoner favorite, String prefName, Context context) {
//        context = fixContext(context);
//
//        if (favorite == null || favorite.getSummonerId() == null)
//            return;
//
//        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
//
//        if (prefs != null) {
//            String set = prefs.getString(prefName, null);
//
//            SimpleSummonerComparator favorites = null;
//            if (!NullChecker.isNullOrEmpty(set)) {
//                favorites = SimpleSummonerComparator.fromJson(set);
//            }
//            if (favorites == null || favorites.getFavorites() == null) {
//                favorites = new SimpleSummonerComparator();
//            }
//
//            addToSet(favorite, favorites);
//
//            prefs.edit().putString(prefName, favorites.toJson()).apply();
//        }
//    }

//    private static void addToSet(SimpleSummoner addFavorite, SimpleSummonerComparator favorites) {
//        SimpleSummoner favorite = getFavorite(addFavorite, favorites.getFavorites());
//
//        if (favorite != null) {
//            favorites.getFavorites().remove(favorite);
//        }
//
//        favorites.addFavorite(addFavorite);
//    }

//    private static SimpleSummoner getFavorite(SimpleSummoner addFavorite, ArrayList<SimpleSummoner> favorites) {
//        if (addFavorite != null && favorites != null && favorites.size() > 0)
//            for (SimpleSummoner favorite : favorites) {
//                if (favorite != null && favorite.getSummonerId().equalsIgnoreCase(addFavorite.getSummonerId())) {
//                    return favorite;
//                }
//            }
//
//        return null;
//    }

//    private static SimpleSummonerComparator addIfAlreadyContains(SimpleSummoner addFavorite, String set, SharedPreferences.Editor edit, String key) {
//        if (!NullChecker.isNullOrEmpty(set)) {
//            SimpleSummonerComparator favorites = SimpleSummonerComparator.fromJson(set);
//            if (addFavorite != null && favorites != null && favorites.getFavorites() != null) {
//                SimpleSummoner favorite = getFavorite(addFavorite, favorites.getFavorites());
//                if (favorite != null) {
//                    favorites.removeFavorite(favorite);
//                    favorites.addFavorite(addFavorite);
//
//                    edit.putString(key, favorites.toJson()).apply();
//                }
//            }
//            return favorites;
//        }
//
//        return null;
//    }
//
//    public static SimpleSummonerComparator getFromSharedPrefs(SimpleSummoner favorite, String prefName, Context context) {
//        context = fixContext(context);
//        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
//
//        if (prefs != null) {
//            String set = prefs.getString(prefName, null);
//            SharedPreferences.Editor edit = prefs.edit();
//
//            SimpleSummonerComparator favorites = addIfAlreadyContains(favorite, set, edit, prefName);
//            edit.apply();
//
//            return favorites;
//        }
//        return null;
//    }

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
