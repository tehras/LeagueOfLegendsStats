package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by tehras on 1/23/16.
 */
public class RuneIcons implements Request.ParserCallback<RuneIcons> {

    @SerializedName("version")
    private String mVersion;
    @SerializedName("data")
    private Map<String, RuneIcon> mRuneIcons;

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }

    public Map<String, RuneIcon> getRuneIcons() {
        return mRuneIcons;
    }

    public void setRuneIcons(Map<String, RuneIcon> runeIcons) {
        mRuneIcons = runeIcons;
    }


    @Override
    public RuneIcons parse(String body) {
        return new Gson().fromJson(body, this.getClass());
    }
}
