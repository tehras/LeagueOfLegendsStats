package com.github.koshkin.leagueoflegendsstats.models;

import android.util.Log;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by tehras on 1/15/16.
 */
public class LeagueStandings implements Request.ParserCallback<LeagueStandings> {

    @SerializedName("name")
    private String mName;
    @SerializedName("tier")
    private Tier mTier;
    @SerializedName("queue")
    private LeagueQueueType mQueueType;
    @SerializedName("entries")
    private ArrayList<RankedSummoner> mEntries;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Tier getTier() {
        return mTier;
    }

    public void setTier(Tier tier) {
        mTier = tier;
    }

    public LeagueQueueType getQueueType() {
        return mQueueType;
    }

    public void setQueueType(LeagueQueueType queueType) {
        mQueueType = queueType;
    }

    public ArrayList<RankedSummoner> getEntries() {
        return mEntries;
    }

    public void setEntries(ArrayList<RankedSummoner> entries) {
        mEntries = entries;
    }

    @Override
    public LeagueStandings parse(String body) {
        try {
            JSONObject reader = new JSONObject(body);
            if (!reader.has("name")) {
                //PARSE SUMMONERS
                Iterator<String> iterator = reader.keys();
                ArrayList<Summoner> summoners = new ArrayList<>();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    JSONObject object = reader.getJSONObject(key);
                    summoners.add(new Summoner().parse(object.toString()));
                }

                addToSummonerRanked(summoners);
            } else {
                return assignRanking(new Gson().fromJson(body, this.getClass()));
            }

            return this;
        } catch (JSONException e) {
            Log.e(getClass().getSimpleName(), "JsonException - ", e);
        }
        return assignRanking(new Gson().fromJson(body, this.getClass()));
    }

    private LeagueStandings assignRanking(LeagueStandings leagueStandings) {
        if (leagueStandings != null && leagueStandings.getEntries() != null && leagueStandings.getEntries().size() > 0) {
            ArrayList<RankedSummoner> entries = leagueStandings.getEntries();
            Collections.sort(entries);

            for (int i = 1; i < entries.size() + 1; i++) {
                RankedSummoner rankedSummoner = entries.get(i - 1);
                rankedSummoner.setRank(i);
            }
        }

        return leagueStandings;
    }

    /**
     * Searches through ranked summoners and adds new summoners to there
     *
     * @param summoners new retrieved summoners
     */
    private void addToSummonerRanked(ArrayList<Summoner> summoners) {
        for (RankedSummoner rankedSummoner : mEntries) {
            String playerId = rankedSummoner.getPlayerOrTeamId();
            Summoner returnedSummoner = null;

            for (Summoner summoner : summoners) {
                if (playerId.equalsIgnoreCase(summoner.getSummonerId())) {
                    returnedSummoner = summoner;
                    break;
                }
            }

            if (returnedSummoner != null) {
                summoners.remove(returnedSummoner);
                rankedSummoner.setSummoner(returnedSummoner);
            }

            if (summoners.size() == 0)
                break;
        }
    }
}
