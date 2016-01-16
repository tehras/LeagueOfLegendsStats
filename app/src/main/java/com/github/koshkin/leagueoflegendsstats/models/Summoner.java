package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

    private String mSummonerName;
    private SummonerInfo mSummonerInfo;

    public SummonerInfo getSummonerInfo() {
        return mSummonerInfo;
    }

    public void setSummoner(SummonerInfo summonerInfo) {
        mSummonerInfo = summonerInfo;
    }

    @Override
    public Summoner parse(String body) {
        try {
            JSONObject object = new JSONObject(body);
            if (mSummonerName == null) {
                mSummonerInfo = new Gson().fromJson(body, SummonerInfo.class);
                mSummonerName = mSummonerInfo.getName();
            } else if (object.has(mSummonerName)) {
                JSONObject summonerInfoJson = object.getJSONObject(mSummonerName);

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
