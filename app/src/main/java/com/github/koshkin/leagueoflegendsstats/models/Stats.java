package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/10/16.
 */
public class Stats {
    @SerializedName("totalSessionsPlayed")
    private Integer totalSessionsPlayed;
    @SerializedName("totalSessionsLost")
    private Integer totalSessionsLost;
    @SerializedName("totalSessionsWon")
    private Integer totalSessionsWon;
    @SerializedName("totalChampionKills")
    private Integer totalChampionKills;
    @SerializedName("killingSpree")
    private Integer killingSpree;
    @SerializedName("totalDamageDealt")
    private Integer totalDamageDealt;
    @SerializedName("totalDamageTaken")
    private Integer totalDamageTaken;
    @SerializedName("mostChampionKillsPerSession")
    private Integer mostChampionKillsPerSession;
    @SerializedName("totalMinionKills")
    private Integer totalMinionKills;
    @SerializedName("totalDoubleKills")
    private Integer totalDoubleKills;
    @SerializedName("totalTripleKills")
    private Integer totalTripleKills;
    @SerializedName("totalQuadraKills")
    private Integer totalQuadraKills;
    @SerializedName("totalPentaKills")
    private Integer totalPentaKills;
    @SerializedName("totalUnrealKills")
    private Integer totalUnrealKills;
    @SerializedName("totalDeathsPerSession")
    private Integer totalDeathsPerSession;
    @SerializedName("totalGoldEarned")
    private Integer totalGoldEarned;
    @SerializedName("mostSpellsCast")
    private Integer mostSpellsCast;
    @SerializedName("totalTurretsKilled")
    private Integer totalTurretsKilled;
    @SerializedName("totalPhysicalDamageDealt")
    private Integer totalPhysicalDamageDealt;
    @SerializedName("totalMagicDamageDealt")
    private Integer totalMagicDamageDealt;
    @SerializedName("totalNeutralMinionsKilled")
    private Integer totalNeutralMinionsKilled;
    @SerializedName("totalFirstBlood")
    private Integer totalFirstBlood;
    @SerializedName("totalAssists")
    private Integer totalAssists;
    @SerializedName("totalHeal")
    private Integer totalHeal;
    @SerializedName("maxLargestKillingSpree")
    private Integer maxLargestKillingSpree;
    @SerializedName("maxLargestCriticalStrike")
    private Integer maxLargestCriticalStrike;
    @SerializedName("maxChampionsKilled")
    private Integer maxChampionsKilled;
    @SerializedName("maxNumDeaths")
    private Integer maxNumDeaths;
    @SerializedName("maxTimePlayed")
    private Integer maxTimePlayed;
    @SerializedName("maxTimeSpentLiving")
    private Integer maxTimeSpentLiving;
    @SerializedName("normalGamesPlayed")
    private Integer normalGamesPlayed;
    @SerializedName("rankedSoloGamesPlayed")
    private Integer rankedSoloGamesPlayed;
    @SerializedName("rankedPremadeGamesPlayed")
    private Integer rankedPremadeGamesPlayed;
    @SerializedName("botGamesPlayed")
    private Integer botGamesPlayed;

    public Integer getTotalSessionsPlayed() {
        return totalSessionsPlayed;
    }

    public void setTotalSessionsPlayed(Integer totalSessionsPlayed) {
        this.totalSessionsPlayed = totalSessionsPlayed;
    }

    public Integer getTotalSessionsLost() {
        return totalSessionsLost;
    }

    public void setTotalSessionsLost(Integer totalSessionsLost) {
        this.totalSessionsLost = totalSessionsLost;
    }

    public Integer getTotalSessionsWon() {
        return totalSessionsWon;
    }

    public void setTotalSessionsWon(Integer totalSessionsWon) {
        this.totalSessionsWon = totalSessionsWon;
    }

    public Integer getTotalChampionKills() {
        return totalChampionKills;
    }

    public void setTotalChampionKills(Integer totalChampionKills) {
        this.totalChampionKills = totalChampionKills;
    }

    public Integer getKillingSpree() {
        return killingSpree;
    }

    public void setKillingSpree(Integer killingSpree) {
        this.killingSpree = killingSpree;
    }

    public Integer getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public void setTotalDamageDealt(Integer totalDamageDealt) {
        this.totalDamageDealt = totalDamageDealt;
    }

    public Integer getTotalDamageTaken() {
        return totalDamageTaken;
    }

    public void setTotalDamageTaken(Integer totalDamageTaken) {
        this.totalDamageTaken = totalDamageTaken;
    }

    public Integer getMostChampionKillsPerSession() {
        return mostChampionKillsPerSession;
    }

    public void setMostChampionKillsPerSession(Integer mostChampionKillsPerSession) {
        this.mostChampionKillsPerSession = mostChampionKillsPerSession;
    }

    public Integer getTotalMinionKills() {
        return totalMinionKills;
    }

    public void setTotalMinionKills(Integer totalMinionKills) {
        this.totalMinionKills = totalMinionKills;
    }

    public Integer getTotalDoubleKills() {
        return totalDoubleKills;
    }

    public void setTotalDoubleKills(Integer totalDoubleKills) {
        this.totalDoubleKills = totalDoubleKills;
    }

    public Integer getTotalTripleKills() {
        return totalTripleKills;
    }

    public void setTotalTripleKills(Integer totalTripleKills) {
        this.totalTripleKills = totalTripleKills;
    }

    public Integer getTotalQuadraKills() {
        return totalQuadraKills;
    }

    public void setTotalQuadraKills(Integer totalQuadraKills) {
        this.totalQuadraKills = totalQuadraKills;
    }

    public Integer getTotalPentaKills() {
        return totalPentaKills;
    }

    public void setTotalPentaKills(Integer totalPentaKills) {
        this.totalPentaKills = totalPentaKills;
    }

    public Integer getTotalUnrealKills() {
        return totalUnrealKills;
    }

    public void setTotalUnrealKills(Integer totalUnrealKills) {
        this.totalUnrealKills = totalUnrealKills;
    }

    public Integer getTotalDeathsPerSession() {
        return totalDeathsPerSession;
    }

    public void setTotalDeathsPerSession(Integer totalDeathsPerSession) {
        this.totalDeathsPerSession = totalDeathsPerSession;
    }

    public Integer getTotalGoldEarned() {
        return totalGoldEarned;
    }

    public void setTotalGoldEarned(Integer totalGoldEarned) {
        this.totalGoldEarned = totalGoldEarned;
    }

    public Integer getMostSpellsCast() {
        return mostSpellsCast;
    }

    public void setMostSpellsCast(Integer mostSpellsCast) {
        this.mostSpellsCast = mostSpellsCast;
    }

    public Integer getTotalTurretsKilled() {
        return totalTurretsKilled;
    }

    public void setTotalTurretsKilled(Integer totalTurretsKilled) {
        this.totalTurretsKilled = totalTurretsKilled;
    }

    public Integer getTotalPhysicalDamageDealt() {
        return totalPhysicalDamageDealt;
    }

    public void setTotalPhysicalDamageDealt(Integer totalPhysicalDamageDealt) {
        this.totalPhysicalDamageDealt = totalPhysicalDamageDealt;
    }

    public Integer getTotalMagicDamageDealt() {
        return totalMagicDamageDealt;
    }

    public void setTotalMagicDamageDealt(Integer totalMagicDamageDealt) {
        this.totalMagicDamageDealt = totalMagicDamageDealt;
    }

    public Integer getTotalNeutralMinionsKilled() {
        return totalNeutralMinionsKilled;
    }

    public void setTotalNeutralMinionsKilled(Integer totalNeutralMinionsKilled) {
        this.totalNeutralMinionsKilled = totalNeutralMinionsKilled;
    }

    public Integer getTotalFirstBlood() {
        return totalFirstBlood;
    }

    public void setTotalFirstBlood(Integer totalFirstBlood) {
        this.totalFirstBlood = totalFirstBlood;
    }

    public Integer getTotalAssists() {
        return totalAssists;
    }

    public void setTotalAssists(Integer totalAssists) {
        this.totalAssists = totalAssists;
    }

    public Integer getTotalHeal() {
        return totalHeal;
    }

    public void setTotalHeal(Integer totalHeal) {
        this.totalHeal = totalHeal;
    }

    public Integer getMaxLargestKillingSpree() {
        return maxLargestKillingSpree;
    }

    public void setMaxLargestKillingSpree(Integer maxLargestKillingSpree) {
        this.maxLargestKillingSpree = maxLargestKillingSpree;
    }

    public Integer getMaxLargestCriticalStrike() {
        return maxLargestCriticalStrike;
    }

    public void setMaxLargestCriticalStrike(Integer maxLargestCriticalStrike) {
        this.maxLargestCriticalStrike = maxLargestCriticalStrike;
    }

    public Integer getMaxChampionsKilled() {
        return maxChampionsKilled;
    }

    public void setMaxChampionsKilled(Integer maxChampionsKilled) {
        this.maxChampionsKilled = maxChampionsKilled;
    }

    public Integer getMaxNumDeaths() {
        return maxNumDeaths;
    }

    public void setMaxNumDeaths(Integer maxNumDeaths) {
        this.maxNumDeaths = maxNumDeaths;
    }

    public Integer getMaxTimePlayed() {
        return maxTimePlayed;
    }

    public void setMaxTimePlayed(Integer maxTimePlayed) {
        this.maxTimePlayed = maxTimePlayed;
    }

    public Integer getMaxTimeSpentLiving() {
        return maxTimeSpentLiving;
    }

    public void setMaxTimeSpentLiving(Integer maxTimeSpentLiving) {
        this.maxTimeSpentLiving = maxTimeSpentLiving;
    }

    public Integer getNormalGamesPlayed() {
        return normalGamesPlayed;
    }

    public void setNormalGamesPlayed(Integer normalGamesPlayed) {
        this.normalGamesPlayed = normalGamesPlayed;
    }

    public Integer getRankedSoloGamesPlayed() {
        return rankedSoloGamesPlayed;
    }

    public void setRankedSoloGamesPlayed(Integer rankedSoloGamesPlayed) {
        this.rankedSoloGamesPlayed = rankedSoloGamesPlayed;
    }

    public Integer getRankedPremadeGamesPlayed() {
        return rankedPremadeGamesPlayed;
    }

    public void setRankedPremadeGamesPlayed(Integer rankedPremadeGamesPlayed) {
        this.rankedPremadeGamesPlayed = rankedPremadeGamesPlayed;
    }

    public Integer getBotGamesPlayed() {
        return botGamesPlayed;
    }

    public void setBotGamesPlayed(Integer botGamesPlayed) {
        this.botGamesPlayed = botGamesPlayed;
    }
}
