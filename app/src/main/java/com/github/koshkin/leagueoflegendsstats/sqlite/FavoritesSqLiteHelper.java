package com.github.koshkin.leagueoflegendsstats.sqlite;

import android.database.sqlite.SQLiteException;

import com.github.koshkin.leagueoflegendsstats.models.SimpleSummoner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tehras on 1/20/16.
 */
public class FavoritesSqLiteHelper {

    //Get SimpleSummonerComparator
    public static List<SimpleSummoner> getAllFavorites() {
        try {
            Iterator<SimpleSummoner> favorites = SimpleSummoner.findAll(SimpleSummoner.class);
            ArrayList<SimpleSummoner> allSimpleSummoners = null;
            while (favorites.hasNext()) {
                if (allSimpleSummoners == null)
                    allSimpleSummoners = new ArrayList<>();

                SimpleSummoner simpleSummoner = favorites.next();

                if (allSimpleSummoners.contains(simpleSummoner))
                    continue;

                allSimpleSummoners.add(simpleSummoner);
            }

            return allSimpleSummoners;
        } catch (SQLiteException e) {
            return new ArrayList<>();
        }
    }

    //Get SimpleSummoner By Id
    public static SimpleSummoner getFavorite(String name) {
        List<SimpleSummoner> simpleSummoners = SimpleSummoner.find(SimpleSummoner.class, "name = ?", name);
        if (simpleSummoners != null && simpleSummoners.size() > 0) {
            return simpleSummoners.get(0);
        }

        return null;
    }

    //Delete SimpleSummoner
    public static boolean deleteFavorite(SimpleSummoner simpleSummoner) {
        if (simpleSummoner == null)
            return false;

        boolean hasDeleted = false;

        List<SimpleSummoner> simpleSummoners = SimpleSummoner.find(SimpleSummoner.class, "name = ?", simpleSummoner.getName());
        if (simpleSummoners != null && simpleSummoners.size() > 0) {
            for (SimpleSummoner fav : simpleSummoners)
                SimpleSummoner.delete(fav);

            hasDeleted = true;
        }

        return hasDeleted;
    }

    //Put SimpleSummoner
    public static SimpleSummoner addFavorite(SimpleSummoner simpleSummoner) {
        deleteFavorite(simpleSummoner);
        simpleSummoner.save();

        return simpleSummoner;
    }

    //Update favorites if exists already
    public static void updateFavoriteIfAlreadyExists(SimpleSummoner simpleSummoner) {
        if (simpleSummoner == null)
            return;

        SimpleSummoner fav = getFavorite(simpleSummoner.getName());
        if (fav != null) {
            fav.updateFavorite(simpleSummoner);
            fav.save();
        }
    }

    //Update SimpleSummoner
    public static SimpleSummoner updateFavorite(SimpleSummoner simpleSummoner) {
        if (simpleSummoner == null)
            return null;

        SimpleSummoner fav = getFavorite(simpleSummoner.getName());

        if (fav != null) {
            fav.updateFavorite(simpleSummoner);
            fav.save();
        }

        return fav;
    }

}
