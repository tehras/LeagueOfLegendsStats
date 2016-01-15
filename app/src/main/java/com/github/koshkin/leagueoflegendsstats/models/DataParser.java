package com.github.koshkin.leagueoflegendsstats.models;

import android.content.Context;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.utils.SharedPrefsUtil;

import static com.github.koshkin.leagueoflegendsstats.utils.AssetReaderUtil.CONSTANT_CHAMPION;
import static com.github.koshkin.leagueoflegendsstats.utils.AssetReaderUtil.CONSTANT_PROFILE;
import static com.github.koshkin.leagueoflegendsstats.utils.AssetReaderUtil.CONSTANT_SUMMONER;

/**
 * Created by tehras on 1/14/16.
 */
public class DataParser implements Request.ParserCallback<DataParser> {

    private final Context mContext;

    private final Type mType;
    private Object mResponseObject;

    public Object getResponseObject() {
        return mResponseObject;
    }

    public DataParser(Type type, Context context) {
        mType = type;
        mContext = context;
    }

    @Override
    public DataParser parse(String body) {
        switch (mType) {
            case CHAMPION:
                SharedPrefsUtil.saveSharedPrefs(CONSTANT_CHAMPION, body, mContext);
                mResponseObject = new ChampionIcons().parse(body);
                break;
            case SUMMONER:
                SharedPrefsUtil.saveSharedPrefs(CONSTANT_SUMMONER, body, mContext);
                mResponseObject = new SpellIcons().parse(body);
                break;
            case PROFILE:
                SharedPrefsUtil.saveSharedPrefs(CONSTANT_PROFILE, body, mContext);
                mResponseObject = new ProfileIcons().parse(body);
                break;
        }
        return this;
    }

    public Type getType() {
        return mType;
    }
}
