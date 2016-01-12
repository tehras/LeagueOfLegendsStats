package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/11/16.
 */
public class ProfileIcon {

    @SerializedName("id")
    private String mId;
    @SerializedName("image")
    private Image mImage;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }
}
