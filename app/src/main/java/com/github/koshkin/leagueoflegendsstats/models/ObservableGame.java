package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.models.match.Participant;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tehras on 1/21/16.
 */
public class ObservableGame {

    @SerializedName("gameId")
    private long gameId;
    @SerializedName("mapId")
    private int mapId;
    @SerializedName("gameMode")
    private Game.GameMode gameMode;
    @SerializedName("gameType")
    private Game.GameType gameType;
    @SerializedName("platformId")
    private String platformId;
    @SerializedName("gameQueueConfigId")
    private int gameQueueConfigId;
    @SerializedName("gameStartTime")
    private long gameStartTime;
    @SerializedName("gameLength")
    private long gameLength;
    @SerializedName("bannedChampions")
    private ArrayList<Champion> bannedChampions;
    @SerializedName("participants")
    private ArrayList<Participant> participants;

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public Game.GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(Game.GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public Game.GameType getGameType() {
        return gameType;
    }

    public void setGameType(Game.GameType gameType) {
        this.gameType = gameType;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public int getGameQueueConfigId() {
        return gameQueueConfigId;
    }

    public void setGameQueueConfigId(int gameQueueConfigId) {
        this.gameQueueConfigId = gameQueueConfigId;
    }

    public long getGameStartTime() {
        return gameStartTime;
    }

    public long getGameLength() {
        return gameLength;
    }

    public ArrayList<Champion> getBannedChampions() {
        return bannedChampions;
    }

    public void setBannedChampions(ArrayList<Champion> bannedChampions) {
        this.bannedChampions = bannedChampions;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }
}
