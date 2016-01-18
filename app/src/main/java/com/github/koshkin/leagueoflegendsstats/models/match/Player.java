package com.github.koshkin.leagueoflegendsstats.models.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player {

    @SerializedName("summonerId")
    @Expose
    private int summonerId;
    @SerializedName("summonerName")
    @Expose
    private String summonerName;
    @SerializedName("matchHistoryUri")
    @Expose
    private String matchHistoryUri;
    @SerializedName("profileIcon")
    @Expose
    private int profileIcon;

    /**
     * @return The summonerId
     */
    public int getSummonerId() {
        return summonerId;
    }

    /**
     * @param summonerId The summonerId
     */
    public void setSummonerId(int summonerId) {
        this.summonerId = summonerId;
    }

    /**
     * @return The summonerName
     */
    public String getSummonerName() {
        return summonerName;
    }

    /**
     * @param summonerName The summonerName
     */
    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    /**
     * @return The matchHistoryUri
     */
    public String getMatchHistoryUri() {
        return matchHistoryUri;
    }

    /**
     * @param matchHistoryUri The matchHistoryUri
     */
    public void setMatchHistoryUri(String matchHistoryUri) {
        this.matchHistoryUri = matchHistoryUri;
    }

    /**
     * @return The profileIcon
     */
    public int getProfileIcon() {
        return profileIcon;
    }

    /**
     * @param profileIcon The profileIcon
     */
    public void setProfileIcon(int profileIcon) {
        this.profileIcon = profileIcon;
    }

}
