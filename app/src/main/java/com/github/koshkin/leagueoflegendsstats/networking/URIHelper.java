package com.github.koshkin.leagueoflegendsstats.networking;

/**
 * Created by tehras on 1/10/16.
 */
public enum URIHelper {

    GET_SUMMONER(URIConstants.SUMMONER),
    GET_SUMMONER_SUMMARY(URIConstants.SUMMONER_SUMMARY),
    GET_SUMMONER_RANKED(URIConstants.SUMMONER_SUMMARY_RANKED);

    private String mOperationUrl;

    URIHelper(String operationUrl) {
        mOperationUrl = operationUrl;
    }

    public static Region sRegion = Region.NA;

    public String getUrl(String... params) {
        String url = URIConstants.NA_BASE_URI + mOperationUrl + URIConstants.API_KEY;

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
