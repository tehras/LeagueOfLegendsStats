package com.github.koshkin.leagueoflegendsstats.models;

/**
 * Created by tehras on 1/15/16.
 */
public enum Tier {

    CHALLENGER("Challenger"), MASTER("Master"), DIAMOND("Diamond"), PLATINUM("Platinum"), GOLD("Gold"), SILVER("Silver"), BRONZE("Bronze");

    private final String mName;

    Tier(String tierName) {
        mName = tierName;
    }

    public String getName() {
        return mName;
    }

    public static Tier fromName(String name) {
        for (Tier tier : values()) {
            if (tier.getName().equalsIgnoreCase(name))
                return tier;
        }

        return CHALLENGER;
    }
}
