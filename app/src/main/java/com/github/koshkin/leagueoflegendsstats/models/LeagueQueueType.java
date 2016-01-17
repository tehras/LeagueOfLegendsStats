package com.github.koshkin.leagueoflegendsstats.models;

/**
 * Created by tehras on 1/15/16.
 */
public enum LeagueQueueType {
    RANKED_SOLO_5x5("Solo 5v5"), RANKED_TEAM_3x3("Team 3v3"), RANKED_TEAM_5x5("Team 5v5");

    private final String mName;

    LeagueQueueType(String s) {
        mName = s;
    }

    public String getName() {
        return mName;
    }

    public static LeagueQueueType fromName(String name) {
        for (LeagueQueueType tier : values()) {
            if (tier.getName().equalsIgnoreCase(name))
                return tier;
        }

        return RANKED_SOLO_5x5;
    }
}
