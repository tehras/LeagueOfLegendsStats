package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/11/16.
 */
public class ChampionIcon {

    @SerializedName("name")
    private String mName;
    @SerializedName("image")
    private Image mImage;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("key")
    private String mKey;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }
}
