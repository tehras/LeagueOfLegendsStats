package com.github.koshkin.leagueoflegendsstats.sqlite;

import android.database.sqlite.SQLiteException;

import com.github.koshkin.leagueoflegendsstats.models.RecentSummoner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tehras on 1/20/16.
 * <p/>
 * Recent Search SQL Helper
 */
public class RecentSearchSqlLiteHelper {

    //Get SimpleSummonerComparator
    public static List<RecentSummoner> getAllRecent() {
        try {
            Iterator<RecentSummoner> summoners = RecentSummoner.findAll(RecentSummoner.class);
            ArrayList<RecentSummoner> allSimpleSummoners = null;
            while (summoners.hasNext()) {
                if (allSimpleSummoners == null)
                    allSimpleSummoners = new ArrayList<>();

                allSimpleSummoners.add(summoners.next());
            }

            return allSimpleSummoners;
        } catch (SQLiteException e) {
            return new ArrayList<>();
        }
    }

    //Get SimpleSummoner By Id
    public static RecentSummoner getRecent(String name) {
        List<RecentSummoner> simpleSummoners = RecentSummoner.find(RecentSummoner.class, "name = ?", name);
        if (simpleSummoners != null && simpleSummoners.size() > 0) {
            return simpleSummoners.get(0);
        }

        return null;
    }

    //Put SimpleSummoner
    public static RecentSummoner addRecent(RecentSummoner simpleSummoner) {
        List<RecentSummoner> simpleSummoners = RecentSummoner.find(RecentSummoner.class, "name = ?", simpleSummoner.getName());
        if (simpleSummoners != null && simpleSummoners.size() > 0) {
            updateRecent(simpleSummoner);
        } else
            simpleSummoner.save();

        return simpleSummoner;
    }

    //Update SimpleSummoner
    public static RecentSummoner updateRecent(RecentSummoner simpleSummoner) {
        if (simpleSummoner == null)
            return null;

        RecentSummoner fav = getRecent(simpleSummoner.getName());

        if (fav != null) {
            fav.updateFavorite(simpleSummoner);
            fav.save();
        }

        return fav;
    }

}
