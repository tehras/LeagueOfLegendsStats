package com.github.koshkin.leagueoflegendsstats.models.match;

import com.github.koshkin.leagueoflegendsstats.models.TeamSide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Participant {

    @SerializedName("teamId")
    @Expose
    private TeamSide teamId;
    @SerializedName("spell1Id")
    @Expose
    private int spell1Id;
    @SerializedName("spell2Id")
    @Expose
    private int spell2Id;
    @SerializedName("championId")
    @Expose
    private int championId;
    @SerializedName("highestAchievedSeasonTier")
    @Expose
    private String highestAchievedSeasonTier;
    @SerializedName("timeline")
    @Expose
    private Timeline timeline;
    @SerializedName("profileIconId")
    @Expose
    private String profileIconId;
    @SerializedName("summonerName")
    @Expose
    private String summonerName;
    @SerializedName("bot")
    @Expose
    private boolean bot;
    @SerializedName("masteries")
    @Expose
    private List<Mastery> masteries = new ArrayList<Mastery>();
    @SerializedName("stats")
    @Expose
    private Stats stats;
    @SerializedName("participantId")
    @Expose
    private int participantId;
    @SerializedName("runes")
    @Expose
    private List<Rune> runes = new ArrayList<Rune>();

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
     * @return The spell1Id
     */
    public int getSpell1Id() {
        return spell1Id;
    }

    /**
     * @param spell1Id The spell1Id
     */
    public void setSpell1Id(int spell1Id) {
        this.spell1Id = spell1Id;
    }

    /**
     * @return The spell2Id
     */
    public int getSpell2Id() {
        return spell2Id;
    }

    /**
     * @param spell2Id The spell2Id
     */
    public void setSpell2Id(int spell2Id) {
        this.spell2Id = spell2Id;
    }

    /**
     * @return The championId
     */
    public int getChampionId() {
        return championId;
    }

    /**
     * @param championId The championId
     */
    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public String getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(String profileIconId) {
        this.profileIconId = profileIconId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    /**
     * @return The highestAchievedSeasonTier
     */
    public String getHighestAchievedSeasonTier() {
        return highestAchievedSeasonTier;
    }

    /**
     * @param highestAchievedSeasonTier The highestAchievedSeasonTier
     */
    public void setHighestAchievedSeasonTier(String highestAchievedSeasonTier) {
        this.highestAchievedSeasonTier = highestAchievedSeasonTier;
    }

    /**
     * @return The timeline
     */
    public Timeline getTimeline() {
        return timeline;
    }

    /**
     * @param timeline The timeline
     */
    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    /**
     * @return The masteries
     */
    public List<Mastery> getMasteries() {
        return masteries;
    }

    /**
     * @param masteries The masteries
     */
    public void setMasteries(List<Mastery> masteries) {
        this.masteries = masteries;
    }

    /**
     * @return The stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * @param stats The stats
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     * @return The participantId
     */
    public int getParticipantId() {
        return participantId;
    }

    /**
     * @param participantId The participantId
     */
    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    /**
     * @return The runes
     */
    public List<Rune> getRunes() {
        return runes;
    }

    /**
     * @param runes The runes
     */
    public void setRunes(List<Rune> runes) {
        this.runes = runes;
    }

}
