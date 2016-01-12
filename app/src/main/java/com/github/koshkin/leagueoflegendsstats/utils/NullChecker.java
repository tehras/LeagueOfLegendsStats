package com.github.koshkin.leagueoflegendsstats.utils;

/**
 * Created by tehras on 1/9/16.
 */
public class NullChecker {

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }
}
