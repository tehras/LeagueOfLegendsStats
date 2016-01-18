package com.github.koshkin.leagueoflegendsstats.networking;

import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.API_KEY;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.LEAGUE_CHALLENGER;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.LEAGUE_MASTER;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.MATCH_STATS;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.NA_BASE_URI;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.NA_STATIC_DATA_URI;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.NA_STATIC_URI;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.PROFILE_ICON;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.REALM;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.STATIC_DATA;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.STATIC_SPRITE;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.SUMMONER;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.SUMMONER_BY_ID;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.SUMMONER_HISTORY_RANKED;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.SUMMONER_RECENT_RANKED;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.SUMMONER_SUMMARY;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.SUMMONER_SUMMARY_RANKED;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.URL_PARAM_SUMMONER_GAME_TYPE;

/**
 * Created by tehras on 1/10/16.
 */
public enum URIHelper {

    GET_SUMMONER(SUMMONER, NA_BASE_URI),
    GET_SUMMONER_SUMMARY(SUMMONER_SUMMARY, NA_BASE_URI),
    GET_SUMMONER_RANKED(SUMMONER_SUMMARY_RANKED, NA_BASE_URI),
    GET_SUMMONER_RANKED_GAMES(SUMMONER_RECENT_RANKED, NA_BASE_URI),
    GET_SUMMONER_RANKED_HISTORY_5V5(SUMMONER_HISTORY_RANKED + URL_PARAM_SUMMONER_GAME_TYPE, NA_BASE_URI),
    GET_PROFILE_URI(PROFILE_ICON, NA_STATIC_URI),
    GET_JSON(STATIC_DATA, NA_STATIC_DATA_URI),
    GET_SPRITES(STATIC_SPRITE, NA_STATIC_URI),
    GET_CHALLENGER(LEAGUE_CHALLENGER, NA_BASE_URI),
    GET_MASTER(LEAGUE_MASTER, NA_BASE_URI),
    GET_MATCH_STATS(MATCH_STATS, NA_BASE_URI),
    GET_SUMMONER_BY_IDS(SUMMONER_BY_ID, NA_BASE_URI),
    GET_REALM(REALM, NA_BASE_URI);

    private final String mBaseUri;
    private String mOperationUrl;

    URIHelper(String operationUrl, String baseUri) {
        mOperationUrl = operationUrl;
        mBaseUri = baseUri;
    }

    public static Region sRegion = Region.NA;

    public String getUrl(String... params) {
        boolean isNonStaticContent = mBaseUri.equalsIgnoreCase(NA_BASE_URI);

        String url = mBaseUri + mOperationUrl;

        String apiKey = API_KEY;
        if (url.contains("?"))
            apiKey = apiKey.replace("?", "&");
        url = url + (isNonStaticContent ? apiKey : "");

        if (isNonStaticContent)
            url = url.replaceFirst("%s", sRegion.getRegionName());
        if (params != null) {
            for (String param : params) {
                url = url.replaceFirst("%s", param);
            }
        }

        return url;
    }

    public enum Region {
        NA("na");

        private String mRegionName;

        Region(String regionName) {
            mRegionName = regionName;
        }

        public String getRegionName() {
            return mRegionName;
        }
    }
}
