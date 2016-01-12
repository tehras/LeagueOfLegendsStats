package com.github.koshkin.leagueoflegendsstats.utils;

import android.content.Context;

import com.github.koshkin.leagueoflegendsstats.models.Champion;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Stats;

import static com.github.koshkin.leagueoflegendsstats.utils.NumberUtils.noDecimalSafely;
import static com.github.koshkin.leagueoflegendsstats.utils.NumberUtils.oneDecimalSafely;
import static com.github.koshkin.leagueoflegendsstats.utils.NumberUtils.twoDecimals;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.NOT_AVAILABLE;

/**
 * Created by tehras on 1/11/16.
 */
public class ChampUtils {

    public static String getChampName(Champion champion, Context context) {
        return StaticDataHolder.getInstance(context).getChampionName(champion.getId());
    }

    public static String getCS(Champion champ) {
        Stats stats = champ.getStats();

        double cs = 0.0d;
        if (stats != null && stats.getTotalMinionKills() != null && stats.getTotalSessionsPlayed() != null) {
            cs = ((double) stats.getTotalMinionKills()) / ((double) stats.getTotalSessionsPlayed());
        }

        return oneDecimalSafely(cs) + " CS";
    }

    public static String getKDA(Champion champ) {
        String kdaString = twoDecimals(getKDADouble(champ));
        if (kdaString.equalsIgnoreCase(NOT_AVAILABLE))
            return NOT_AVAILABLE;
        else
            return kdaString;
    }

    public static double getKDADouble(Champion champ) {
        Stats stats = champ.getStats();

        double kda = -1.0d;
        if (stats != null) {
            double kills = stats.getTotalChampionKills();
            double deaths = stats.getTotalDeathsPerSession();
            double assists = stats.getTotalAssists();

            kda = ((kills + assists) / deaths);
        }

        return kda;
    }

    public static String getKillsDeathsAssists(Champion champ) {
        Stats stats = champ.getStats();

        if (stats == null)
            return NOT_AVAILABLE;

        String kills = twoDecimals(((double) stats.getTotalChampionKills()) / ((double) stats.getTotalSessionsPlayed()));
        String deaths = twoDecimals(((double) stats.getTotalDeathsPerSession()) / ((double) stats.getTotalSessionsPlayed()));
        String assists = twoDecimals(((double) stats.getTotalAssists()) / ((double) stats.getTotalSessionsPlayed()));

        return String.format(STRING_KILLS_DEATHS_ASSISTS, kills, deaths, assists);
    }

    private static final String STRING_KILLS_DEATHS_ASSISTS = "%s / %s / %s";

    public static String getWinPercentage(Champion champ) {
        Double winPerc = getWinPercentageDouble(champ);
        if (winPerc == null)
            return NOT_AVAILABLE;

        String winPercentage = noDecimalSafely(winPerc);
        if (winPercentage.equalsIgnoreCase(NOT_AVAILABLE))
            return NOT_AVAILABLE;

        return winPercentage + " %";
    }

    public static Double getWinPercentageDouble(Champion champ) {
        Stats stats = champ.getStats();

        if (stats == null)
            return null;

        double wins = stats.getTotalSessionsWon();
        double sessions = stats.getTotalSessionsPlayed();

        return wins * 100 / sessions;
    }

    public static String getChampPlayed(Champion champ) {
        Stats stats = champ.getStats();

        if (stats == null)
            return NOT_AVAILABLE;

        return stats.getTotalSessionsPlayed() + " played";
    }
}
