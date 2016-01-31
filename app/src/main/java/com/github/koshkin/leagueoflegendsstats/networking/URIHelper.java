package com.github.koshkin.leagueoflegendsstats.networking;

import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.API_KEY;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.FEATURED_MATCHES;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.LEAGUE_BY_SUMMONERS;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.LEAGUE_CHALLENGER;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.LEAGUE_MASTER;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.MATCH_STATS;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.NA_BASE_URI;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.NA_STATIC_DATA_URI;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.NA_STATIC_URI;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.OBSERVALBE_GAME;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.PROFILE_ICON;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.REALM;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.STATIC_DATA;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.STATIC_SPRITE;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.SUMMONER;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.SUMMONER_BY_ID;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.SUMMONER_RECENT_RANKED;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.SUMMONER_SUMMARY;
import static com.github.koshkin.leagueoflegendsstats.networking.URIConstants.SUMMONER_SUMMARY_RANKED;

/**
 * Created by tehras on 1/10/16.
 */
public enum URIHelper {

    GET_SUMMONER(SUMMONER, NA_BASE_URI, true, true),
    GET_SUMMONER_SUMMARY(SUMMONER_SUMMARY, NA_BASE_URI, true, true),
    GET_SUMMONER_RANKED(SUMMONER_SUMMARY_RANKED, NA_BASE_URI, true, true),
    GET_SUMMONER_RANKED_GAMES(SUMMONER_RECENT_RANKED, NA_BASE_URI, true, true),
    GET_PROFILE_URI(PROFILE_ICON, NA_STATIC_URI, false, false),
    GET_JSON(STATIC_DATA, NA_STATIC_DATA_URI, false, false),
    GET_SPRITES(STATIC_SPRITE, NA_STATIC_URI, false, false),
    GET_CHALLENGER(LEAGUE_CHALLENGER, NA_BASE_URI, true, true),
    GET_MASTER(LEAGUE_MASTER, NA_BASE_URI, true, true),
    GET_LEAGUE_BY_SUMMONERS(LEAGUE_BY_SUMMONERS, NA_BASE_URI, true, true),
    GET_MATCH_STATS(MATCH_STATS, NA_BASE_URI, true, true),
    GET_SUMMONER_BY_IDS(SUMMONER_BY_ID, NA_BASE_URI, true, true),
    GET_FEATURED_MATCHES(FEATURED_MATCHES, NA_BASE_URI, true, true),
    GET_OBSERVABLE_GAME(OBSERVALBE_GAME, NA_BASE_URI, false, true),
    GET_REALM(REALM, NA_BASE_URI, true, true);

    private final String mBaseUri;
    private final boolean mIsNonStaticContent;
    private String mOperationUrl;
    private final boolean mRequiresApiString;

    URIHelper(String operationUrl, String baseUri, boolean b, boolean b1) {
        mOperationUrl = operationUrl;
        mBaseUri = baseUri;
        mIsNonStaticContent = b;
        mRequiresApiString = b1;
    }

    public static final String VERSION_FILL = "<version>";

    public static String sVersion = "6.1.1";

    public static void setVersion(String version) {
        sVersion = version;
    }

    public static Region sRegion = Region.NA;

    public static Region getCurrentRegion() {
        return sRegion;
    }

    public static void setCurrentRegion(Region region) {
        sRegion = region;
    }

    public String getUrl(String... params) {
        String url;

        if (mBaseUri.equalsIgnoreCase(NA_BASE_URI)) {
            url = String.format(mBaseUri, getCurrentRegion().getRegionName().toLowerCase());
        } else {
            url = mBaseUri.replaceAll(VERSION_FILL, sVersion);
        }

        url = url + mOperationUrl;

        String apiKey = API_KEY;
        if (url.contains("?"))
            apiKey = apiKey.replace("?", "&");
        url = url + (mRequiresApiString ? apiKey : "");

        if (mIsNonStaticContent)
            url = url.replaceFirst("%s", sRegion.getRegionName());
        if (params != null) {
            for (String param : params) {
                url = url.replaceFirst("%s", param);
            }
        }

        return url;
    }

    public enum Region {
        NA("na", "North America"), BR("br", "Brazil"), EUNE("eune", "Europe North East"), EUW("euw", "Europe West"), LAN("lan", "South America North")/*latin america north*/, LAS("las", "Latin America South")/*latin america south*/, OCE("oce", "Oceana"), PBE("pbe", "PBE"), RU("ru", "Russia"), TR("tr", "Turkey");

        private final String mRegionDisplayName;
        private String mRegionName;

        public String getRegionDisplayName() {
            return mRegionDisplayName;
        }

        Region(String regionName, String s) {
            mRegionName = regionName;
            mRegionDisplayName = s;
        }

        public String getRegionName() {
            return mRegionName;
        }

        public Region getFromString(Region region) {
            for (Region v : values()) {
                if (v.equals(region))
                    return v;
            }

            return NA;
        }

        public static Region fromDisplayName(String text) {
            for (Region v : values()) {
                if (v.getRegionDisplayName().equalsIgnoreCase(text))
                    return v;
            }

            return NA;
        }
    }

    public enum PlatformId {
        NA1(Region.NA), BR1(Region.BR), LA1(Region.LAN), LA2(Region.LAS), OC1(Region.OCE), EUN1(Region.EUNE), TR1(Region.TR), RU(Region.RU), EUW1(Region.EUW);

        private Region mRegion;

        PlatformId(Region region) {
            mRegion = region;
        }

        public static PlatformId getByRegion(Region region) {
            for (PlatformId platformId : values()) {
                if (platformId.mRegion == region)
                    return platformId;
            }

            return NA1;
        }
    }
}
