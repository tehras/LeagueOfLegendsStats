package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.R;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/12/16.
 */
public enum TeamSide {

    @SerializedName("100")
    RED(R.color.red_team_color),
    @SerializedName("200")
    BLUE(R.color.blue_team_color);


    private final int mSideColor;

    TeamSide(int color) {
        mSideColor = color;
    }

    public int getSideColor() {
        return mSideColor;
    }
}
