package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;

/**
 * Created by tehras on 1/14/16.
 */
public class SpriteHolder implements Request.ParserCallback {
    private final Type mType;

    public SpriteHolder(Type type) {
        mType = type;
    }

    public Type getType() {
        return mType;
    }

    @Override
    public Object parse(String body) {
        return null;
    }
}
