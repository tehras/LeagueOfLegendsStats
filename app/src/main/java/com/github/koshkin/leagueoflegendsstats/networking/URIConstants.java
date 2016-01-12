package com.github.koshkin.leagueoflegendsstats.networking;

/**
 * Created by tehras on 1/10/16.
 */
public class URIConstants {

    //API KEY - DEV == 10 req / 10s
    public static final String API_KEY = "?api_key=47746efd-ea3c-4440-9c80-0f96a6797014";

    //BASE URI, diff for each region
    public static final String NA_BASE_URI = "https://na.api.pvp.net";

    //API OPERATIONS
    public static final String SUMMONER = "/api/lol/%s/v1.4/summoner/by-name/%s";
    public static final String SUMMONER_SUMMARY = "/api/lol/%s/v1.3/stats/by-summoner/%s/summary";
    public static final String SUMMONER_SUMMARY_RANKED = "/api/lol/%s/v1.3/stats/by-summoner/%s/ranked";
}
