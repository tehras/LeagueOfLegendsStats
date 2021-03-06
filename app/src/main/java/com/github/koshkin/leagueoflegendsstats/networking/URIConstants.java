package com.github.koshkin.leagueoflegendsstats.networking;

/**
 * Created by tehras on 1/10/16.
 * <p/>
 * CONSTANTS for all URI types
 */
public class URIConstants {

    //API KEY - PROD == 3000 req / 10s
    public static final String API_KEY = "?api_key=bb71ca67-9e00-4bc2-9442-9e835adf4a6c";

    //BASE URI, diff for each region
    public static final String NA_BASE_URI = "https://%s.api.pvp.net";
    public static String NA_STATIC_URI = "http://ddragon.leagueoflegends.com/cdn/" + URIHelper.VERSION_FILL + "/img";
    public static String NA_STATIC_DATA_URI = "http://ddragon.leagueoflegends.com/cdn/" + URIHelper.VERSION_FILL + "/data";

    //API STATIC
    public static final String PROFILE_ICON = "/profileicon/%s";

    //API OPERATIONS
    public static final String SUMMONER = "/api/lol/%s/v1.4/summoner/by-name/%s";
    public static final String SUMMONER_BY_ID = "/api/lol/%s/v1.4/summoner/%s";
    public static final String SUMMONER_SUMMARY = "/api/lol/%s/v1.3/stats/by-summoner/%s/summary";
    public static final String SUMMONER_SUMMARY_RANKED = "/api/lol/%s/v1.3/stats/by-summoner/%s/ranked";
    public static final String SUMMONER_RECENT_RANKED = "/api/lol/%s/v1.3/game/by-summoner/%s/recent";

    public static final String LEAGUE_CHALLENGER = "/api/lol/%s/v2.5/league/challenger?type=%s";
    public static final String LEAGUE_MASTER = "/api/lol/%s/v2.5/league/master?type=%s";
    public static final String LEAGUE_BY_SUMMONERS = "/api/lol/%s/v2.5/league/by-summoner/%s";

    public static final String MATCH_STATS = "/api/lol/%s/v2.2/match/%s";

    public static final String FEATURED_MATCHES = "/observer-mode/rest/featured";
    public static final String OBSERVALBE_GAME = "/observer-mode/rest/consumer/getSpectatorGameInfo/%s/%s";

    public static final String REALM = "/api/lol/static-data/%s/v1.2/realm";
    public static final String STATIC_DATA = "/%s/%s";
    public static final String STATIC_SPRITE = "/sprite/%s";

    //URL PARAMETERS
    public static final String URL_PARAM_SUMMONER_GAME_TYPE = "?rankedQueues=%s";

    //PARAMS
    public static final String PARAM_RANKED_SOLO_5V5 = "RANKED_SOLO_5x5";
}
