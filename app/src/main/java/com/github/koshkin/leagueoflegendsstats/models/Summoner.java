package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by tehras on 1/10/16.
 * <p/>
 * Summoner object
 */
public class Summoner implements Request.ParserCallback<Summoner> {

    public Summoner(String name) {
        mSummonerName = name;
    }

    public Summoner() {
    }

    @SerializedName("summonerName")
    private String mSummonerName;
    @SerializedName("summonerInfo")
    private SummonerInfo mSummonerInfo;

    public SummonerInfo getSummonerInfo() {
        return mSummonerInfo;
    }

    public void setSummoner(SummonerInfo summonerInfo) {
        mSummonerInfo = summonerInfo;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Summoner fromJson(String json) {
        return new Gson().fromJson(json, Summoner.class);
    }

    @Override
    public Summoner parse(String body) {
        try {
            JSONObject object = new JSONObject(body);
            if (NullChecker.isNullOrEmpty(mSummonerName)) {
                mSummonerInfo = new Gson().fromJson(body, SummonerInfo.class);
                mSummonerName = mSummonerInfo.getName();
            } else {
                Iterator<String> keys = object.keys();

                JSONObject summonerInfoJson = null;
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (key.equalsIgnoreCase(mSummonerName) || key.equalsIgnoreCase(mSummonerName.replaceAll(" ", ""))) {
                        summonerInfoJson = object.getJSONObject(key);
                        break;
                    }
                    keys.next();
                }

                if (summonerInfoJson != null)
                    mSummonerInfo = new Gson().fromJson(summonerInfoJson.toString(), SummonerInfo.class);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return this;
    }

    public String getSummonerId() {
        SummonerInfo summonerInfo = getSummonerInfo();
        if (summonerInfo != null) {
            return summonerInfo.getId();
        }

        return "";
    }
}
