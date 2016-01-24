package com.github.koshkin.leagueoflegendsstats.models;

import java.util.HashMap;

/**
 * Created by tehras on 1/23/16.
 */
public class RuneStats {

    private HashMap<String, Double> mMap;

    public HashMap<String, Double> getMap() {
        return mMap;
    }

    public void setMap(HashMap<String, Double> map) {
        mMap = map;
    }
}
