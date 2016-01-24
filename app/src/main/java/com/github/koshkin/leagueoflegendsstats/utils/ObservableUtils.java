package com.github.koshkin.leagueoflegendsstats.utils;

import android.text.Html;

import com.github.koshkin.leagueoflegendsstats.models.Game;

import static com.github.koshkin.leagueoflegendsstats.utils.NullChecker.isNullOrEmpty;

/**
 * Created by tehras on 1/23/16.
 * <p/>
 * Utils for observable views
 */
public class ObservableUtils {

    public static String getFromGameType(Game.GameType gameType) {
        String gameTypeString = "";

        String[] words = gameType.toString().split("_");
        for (String word : words) {
            word = GameUtils.capitalizeFirstLetter(word.toLowerCase()).replace("x", "v");
            gameTypeString = gameTypeString + (isNullOrEmpty(gameTypeString) ? "" : " ") + word;
        }


        return gameTypeString;
    }

    public static String getFromGameMode(Game.GameMode gameMode) {
        return GameUtils.capitalizeFirstLetter(gameMode.toString().toLowerCase());
    }

    public static CharSequence getStartedText(long gameLength) {
        long min = (int) (gameLength / 60); //get minutes
        long seconds = (int) (gameLength - (min * 60)); //get seconds

        return Html.fromHtml("Game Started <b>" + (min > 0 ? String.valueOf(min) + "m " : "") + (seconds > 0 ? String.valueOf(seconds) + "s" : "") + "</b> ago");
    }

    public static CharSequence getStartedText(long currentTime, long timeStarted) {

        long totalTime = currentTime - timeStarted; //millies
        totalTime = totalTime / 1000; //Seconds;

        return getStartedText(totalTime);
    }
}
