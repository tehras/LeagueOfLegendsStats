package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by tehras on 1/23/16.
 * <p/>
 * Main part of the observe game layout
 */
public class SummonerLeagueStandings implements Request.ParserCallback<SummonerLeagueStandings> {


    private HashMap<String, ArrayList<LeagueStandings>> mLeagueStandingsHashMap;

    public HashMap<String, ArrayList<LeagueStandings>> getLeagueStandingsHashMap() {
        return mLeagueStandingsHashMap;
    }

    @Override
    public SummonerLeagueStandings parse(String body) {
        try {
            JSONObject object = new JSONObject(body);

            Iterator<String> keys = object.keys();
            while (keys.hasNext()) {
                String key = keys.next();

                JSONArray array = object.getJSONArray(key);
                ArrayList<LeagueStandings> leagueStandings = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    leagueStandings.add(new Gson().fromJson(obj.toString(), LeagueStandings.class));
                }

                if (mLeagueStandingsHashMap == null)
                    mLeagueStandingsHashMap = new HashMap<>();

                mLeagueStandingsHashMap.put(key, leagueStandings);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return this;
    }
}
