package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by tehras on 1/11/16.
 */
public class ChampionIcons implements Request.ParserCallback<ChampionIcons> {

    @SerializedName("version")
    private String mVersion;
    @SerializedName("data")
    private Map<String, ChampionIcon> mChampionIcons;

    public ChampionIcon getChampionIcon(String id) {
        for (ChampionIcon championIcon : mChampionIcons.values()) {
            if (id.equalsIgnoreCase(championIcon.getKey()))
                return championIcon;
        }

        return null;
    }

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }

    public Map<String, ChampionIcon> getChampionIcons() {
        return mChampionIcons;
    }

    public void setChampionIcons(Map<String, ChampionIcon> championIcons) {
        mChampionIcons = championIcons;
    }

    @Override
    public ChampionIcons parse(String body) {
        return new Gson().fromJson(body, ChampionIcons.class);
    }
}
