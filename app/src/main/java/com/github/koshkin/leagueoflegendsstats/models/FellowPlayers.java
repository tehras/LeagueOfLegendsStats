package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/12/16.
 */
public class FellowPlayers {

    @SerializedName("summonerId")
    private long summonerId;

    @SerializedName("teamId")
    private TeamSide mTeamSide;

    @SerializedName("championId")
    private int mChampionId;

}
