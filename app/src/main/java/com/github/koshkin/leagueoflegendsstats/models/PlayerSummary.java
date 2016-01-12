package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/10/16.
 */
public class PlayerSummary implements Comparable<PlayerSummary> {
    @SerializedName("playerStatSummaryType")
    private SummaryType mSummaryType;
    @SerializedName("aggregatedStats")
    private AggregatedStats mAggregatedStats;
    @SerializedName("modifyDate")
    private double mModifyDate;
    @SerializedName("wins")
    private int mWins = -1;
    @SerializedName("losses")
    private int mLosses = -1;

    public SummaryType getSummaryType() {
        return mSummaryType;
    }

    public void setSummaryType(SummaryType summaryType) {
        mSummaryType = summaryType;
    }

    public AggregatedStats getAggregatedStats() {
        return mAggregatedStats;
    }

    public void setAggregatedStats(AggregatedStats aggregatedStats) {
        mAggregatedStats = aggregatedStats;
    }

    public double getModifyDate() {
        return mModifyDate;
    }

    public void setModifyDate(double modifyDate) {
        mModifyDate = modifyDate;
    }

    public int getWins() {
        return mWins;
    }

    public void setWins(int wins) {
        this.mWins = wins;
    }

    public int getLosses() {
        return mLosses;
    }

    public void setLosses(int losses) {
        mLosses = losses;
    }

    @Override
    public int compareTo(PlayerSummary another) {
        if (this.getSummaryType() == null || another.getSummaryType() == null) {
            if (this.getSummaryType() == null && another.getSummaryType() != null)
                return -1;
            else if (this.getSummaryType() != null && another.getSummaryType() == null)
                return 1;
            else
                return 0;
        }
        return this.getSummaryType().mOrder - another.getSummaryType().mOrder;
    }

    public enum SummaryType {
        @SerializedName("CoopVsAI")
        COOP_VS_AI(6, "5v5 - COOP vs AI"),
        @SerializedName("CoopVsAI3x3")
        COOP_VS_AI_3X(7, "3v3 - COOP vs AI"),
        @SerializedName("OdinUnranked")
        ODIN_UNRANKED(9, "ODIN Unranked"),
        @SerializedName("RankedTeam3x3")
        RANKED_TEAM_3X(2, "3v3 - Ranked Team"),
        @SerializedName("RankedTeam5x5")
        RANKED_TEAM_5X(3, "5v5 - Ranked Team"),
        @SerializedName("Unranked3x3")
        UNRANKED_3X(5, "3v3 - Unranked Team"),
        @SerializedName("AramUnranked5x5")
        ARAM_UNRANKED_5X(8, "5v5 - ARAM"),
        @SerializedName("Unranked")
        UNRANKED(4, "5v5 - Summoners Rift Unranked"),
        @SerializedName("RankedSolo5x5")
        RANKED_SOLO_5X(1, "5v5 - Summoners Rift Ranked");

        private int mOrder;
        private String mName;

        public String getName() {
            return mName;
        }

        SummaryType(int order, String name) {
            mOrder = order;
            mName = name;
        }
    }


    @Override
    public String toString() {
        return "PlayerSummary{" +
                "mSummaryType=" + mSummaryType +
                ", mAggregatedStats=" + mAggregatedStats +
                ", mModifyDate=" + mModifyDate +
                ", wins=" + mWins +
                ", losses=" + mLosses +
                '}';
    }
}
