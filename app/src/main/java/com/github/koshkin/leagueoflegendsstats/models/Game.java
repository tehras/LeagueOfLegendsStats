package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tehras on 1/12/16.
 */
public class Game {

    @SerializedName("gameId")
    private long mGameId;
    @SerializedName("invalid")
    private boolean mInvalid;
    @SerializedName("gameMode")
    private GameMode mGameMode;
    @SerializedName("gameType")
    private GameType mGameType;
    @SerializedName("subType")
    private SubType mSubType;
    @SerializedName("mapId")
    private long mMapId;
    @SerializedName("teamId")
    private TeamSide mTeamSide;
    @SerializedName("championId")
    private int mChampionId;
    @SerializedName("spell1")
    private int mSpell1;
    @SerializedName("spell2")
    private int mSpell2;
    @SerializedName("ipEarned")
    private int mIpEarned;
    @SerializedName("createDate")
    private long mCreateDate;
    @SerializedName("fellowPlayers")
    private ArrayList<FellowPlayers> mFellowPlayers;
    @SerializedName("stats")
    private GameStats mGameStats;

    public GameStats getGameStats() {
        return mGameStats;
    }

    public void setGameStats(GameStats gameStats) {
        mGameStats = gameStats;
    }

    public long getGameId() {
        return mGameId;
    }

    public void setGameId(long gameId) {
        mGameId = gameId;
    }

    public boolean isInvalid() {
        return mInvalid;
    }

    public void setInvalid(boolean invalid) {
        mInvalid = invalid;
    }

    public GameMode getGameMode() {
        return mGameMode;
    }

    public void setGameMode(GameMode gameMode) {
        mGameMode = gameMode;
    }

    public GameType getGameType() {
        return mGameType;
    }

    public void setGameType(GameType gameType) {
        mGameType = gameType;
    }

    public SubType getSubType() {
        return mSubType;
    }

    public void setSubType(SubType subType) {
        mSubType = subType;
    }

    public long getMapId() {
        return mMapId;
    }

    public void setMapId(long mapId) {
        mMapId = mapId;
    }

    public TeamSide getTeamSide() {
        return mTeamSide;
    }

    public void setTeamSide(TeamSide teamSide) {
        mTeamSide = teamSide;
    }

    public int getChampionId() {
        return mChampionId;
    }

    public void setChampionId(int championId) {
        mChampionId = championId;
    }

    public int getSpell1() {
        return mSpell1;
    }

    public void setSpell1(int spell1) {
        mSpell1 = spell1;
    }

    public int getSpell2() {
        return mSpell2;
    }

    public void setSpell2(int spell2) {
        mSpell2 = spell2;
    }

    public int getIpEarned() {
        return mIpEarned;
    }

    public void setIpEarned(int ipEarned) {
        mIpEarned = ipEarned;
    }

    public long getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(long createDate) {
        mCreateDate = createDate;
    }

    public ArrayList<FellowPlayers> getFellowPlayers() {
        return mFellowPlayers;
    }

    public void setFellowPlayers(ArrayList<FellowPlayers> fellowPlayers) {
        mFellowPlayers = fellowPlayers;
    }

    public enum GameType {
        CUSTOM_GAME, MATCHED_GAME, TUTORIAL_GAME
    }

    public enum GameMode {
        CLASSIC, ODIN, ARAM, TUTORIAL, ONEFORALL, ASCENSION, FIRSTBLOOD, KINGPORO
    }

    public enum SubType {
        NONE, NORMAL, BOT, RANKED_SOLO_5x5, RANKED_PREMADE_3x3, RANKED_PREMADE_5x5, ODIN_UNRANKED, RANKED_TEAM_3x3, RANKED_TEAM_5x5, NORMAL_3x3, BOT_3x3, CAP_5x5, ARAM_UNRANKED_5x5, ONEFORALL_5x5, FIRSTBLOOD_1x1, FIRSTBLOOD_2x2, SR_6x6, URF, URF_BOT, NIGHTMARE_BOT, ASCENSION, HEXAKILL, KING_PORO, COUNTER_PICK, BILGEWATER
    }
}
