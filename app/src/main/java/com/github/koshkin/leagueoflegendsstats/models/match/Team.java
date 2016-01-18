package com.github.koshkin.leagueoflegendsstats.models.match;

import com.github.koshkin.leagueoflegendsstats.models.TeamSide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Team {

    @SerializedName("teamId")
    @Expose
    private TeamSide teamId;
    @SerializedName("winner")
    @Expose
    private boolean winner;
    @SerializedName("firstBlood")
    @Expose
    private boolean firstBlood;
    @SerializedName("firstTower")
    @Expose
    private boolean firstTower;
    @SerializedName("firstInhibitor")
    @Expose
    private boolean firstInhibitor;
    @SerializedName("firstBaron")
    @Expose
    private boolean firstBaron;
    @SerializedName("firstDragon")
    @Expose
    private boolean firstDragon;
    @SerializedName("firstRiftHerald")
    @Expose
    private boolean firstRiftHerald;
    @SerializedName("towerKills")
    @Expose
    private int towerKills;
    @SerializedName("inhibitorKills")
    @Expose
    private int inhibitorKills;
    @SerializedName("baronKills")
    @Expose
    private int baronKills;
    @SerializedName("dragonKills")
    @Expose
    private int dragonKills;
    @SerializedName("riftHeraldKills")
    @Expose
    private int riftHeraldKills;
    @SerializedName("vilemawKills")
    @Expose
    private int vilemawKills;
    @SerializedName("dominionVictoryScore")
    @Expose
    private int dominionVictoryScore;
    @SerializedName("bans")
    @Expose
    private List<Ban> bans = new ArrayList<Ban>();

    /**
     * @return The teamId
     */
    public TeamSide getTeamId() {
        return teamId;
    }

    /**
     * @param teamId The teamId
     */
    public void setTeamId(TeamSide teamId) {
        this.teamId = teamId;
    }

    /**
     * @return The winner
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     * @param winner The winner
     */
    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    /**
     * @return The firstBlood
     */
    public boolean isFirstBlood() {
        return firstBlood;
    }

    /**
     * @param firstBlood The firstBlood
     */
    public void setFirstBlood(boolean firstBlood) {
        this.firstBlood = firstBlood;
    }

    /**
     * @return The firstTower
     */
    public boolean isFirstTower() {
        return firstTower;
    }

    /**
     * @param firstTower The firstTower
     */
    public void setFirstTower(boolean firstTower) {
        this.firstTower = firstTower;
    }

    /**
     * @return The firstInhibitor
     */
    public boolean isFirstInhibitor() {
        return firstInhibitor;
    }

    /**
     * @param firstInhibitor The firstInhibitor
     */
    public void setFirstInhibitor(boolean firstInhibitor) {
        this.firstInhibitor = firstInhibitor;
    }

    /**
     * @return The firstBaron
     */
    public boolean isFirstBaron() {
        return firstBaron;
    }

    /**
     * @param firstBaron The firstBaron
     */
    public void setFirstBaron(boolean firstBaron) {
        this.firstBaron = firstBaron;
    }

    /**
     * @return The firstDragon
     */
    public boolean isFirstDragon() {
        return firstDragon;
    }

    /**
     * @param firstDragon The firstDragon
     */
    public void setFirstDragon(boolean firstDragon) {
        this.firstDragon = firstDragon;
    }

    /**
     * @return The firstRiftHerald
     */
    public boolean isFirstRiftHerald() {
        return firstRiftHerald;
    }

    /**
     * @param firstRiftHerald The firstRiftHerald
     */
    public void setFirstRiftHerald(boolean firstRiftHerald) {
        this.firstRiftHerald = firstRiftHerald;
    }

    /**
     * @return The towerKills
     */
    public int getTowerKills() {
        return towerKills;
    }

    /**
     * @param towerKills The towerKills
     */
    public void setTowerKills(int towerKills) {
        this.towerKills = towerKills;
    }

    /**
     * @return The inhibitorKills
     */
    public int getInhibitorKills() {
        return inhibitorKills;
    }

    /**
     * @param inhibitorKills The inhibitorKills
     */
    public void setInhibitorKills(int inhibitorKills) {
        this.inhibitorKills = inhibitorKills;
    }

    /**
     * @return The baronKills
     */
    public int getBaronKills() {
        return baronKills;
    }

    /**
     * @param baronKills The baronKills
     */
    public void setBaronKills(int baronKills) {
        this.baronKills = baronKills;
    }

    /**
     * @return The dragonKills
     */
    public int getDragonKills() {
        return dragonKills;
    }

    /**
     * @param dragonKills The dragonKills
     */
    public void setDragonKills(int dragonKills) {
        this.dragonKills = dragonKills;
    }

    /**
     * @return The riftHeraldKills
     */
    public int getRiftHeraldKills() {
        return riftHeraldKills;
    }

    /**
     * @param riftHeraldKills The riftHeraldKills
     */
    public void setRiftHeraldKills(int riftHeraldKills) {
        this.riftHeraldKills = riftHeraldKills;
    }

    /**
     * @return The vilemawKills
     */
    public int getVilemawKills() {
        return vilemawKills;
    }

    /**
     * @param vilemawKills The vilemawKills
     */
    public void setVilemawKills(int vilemawKills) {
        this.vilemawKills = vilemawKills;
    }

    /**
     * @return The dominionVictoryScore
     */
    public int getDominionVictoryScore() {
        return dominionVictoryScore;
    }

    /**
     * @param dominionVictoryScore The dominionVictoryScore
     */
    public void setDominionVictoryScore(int dominionVictoryScore) {
        this.dominionVictoryScore = dominionVictoryScore;
    }

    /**
     * @return The bans
     */
    public List<Ban> getBans() {
        return bans;
    }

    /**
     * @param bans The bans
     */
    public void setBans(List<Ban> bans) {
        this.bans = bans;
    }

}
