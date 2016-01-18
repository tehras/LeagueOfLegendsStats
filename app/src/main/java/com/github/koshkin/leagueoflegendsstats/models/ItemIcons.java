package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by tehras on 1/17/16.
 */
public class ItemIcons implements Request.ParserCallback<ItemIcons> {

    @SerializedName("data")
    private HashMap<String, ItemIcon> mItemIcons;
    @SerializedName("version")
    private String mVersion;

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }

    public HashMap<String, ItemIcon> getItemIcons() {
        return mItemIcons;
    }

    public void setItemIcons(HashMap<String, ItemIcon> itemIcons) {
        mItemIcons = itemIcons;
    }

    @Override
    public ItemIcons parse(String body) {
        return new Gson().fromJson(body, this.getClass());
    }

    public ItemIcon getItemIcon(String itemId) {
        if (mItemIcons.containsKey(itemId))
            return mItemIcons.get(itemId);

        return null;
    }
}
