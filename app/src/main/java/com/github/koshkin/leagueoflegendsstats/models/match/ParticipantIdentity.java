package com.github.koshkin.leagueoflegendsstats.models.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParticipantIdentity {

    @SerializedName("participantId")
    @Expose
    private long participantId;
    @SerializedName("player")
    @Expose
    private Player player;

    /**
     * @return The participantId
     */
    public long getParticipantId() {
        return participantId;
    }

    /**
     * @param participantId The participantId
     */
    public void setParticipantId(long participantId) {
        this.participantId = participantId;
    }

    /**
     * @return The player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player The player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

}
