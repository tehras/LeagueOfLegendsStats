package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by tehras on 1/14/16.
 */
public class VersionControl implements Request.ParserCallback<VersionControl> {

    @SerializedName("v")
    private String mVersion;
    @SerializedName("cdn")
    private String mCdnUrl;
    @SerializedName("n")
    private HashMap<String, String> mVersionMap;
    @SerializedName("l")
    private String mRegion;

    public String getRegion() {
        return mRegion;
    }

    public String getVersion() {
        return mVersion;
    }

    public String getCdnUrl() {
        return mCdnUrl;
    }

    public HashMap<String, String> getVersionMap() {
        return mVersionMap;
    }

    @Override
    public VersionControl parse(String body) {
        return new Gson().fromJson(body, this.getClass());
    }
}
