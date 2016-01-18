package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/17/16.
 */
public class ItemIcon {

    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("plaintext")
    private String mPlainText;
    @SerializedName("image")
    private Image mImage;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPlainText() {
        return mPlainText;
    }

    public void setPlainText(String plainText) {
        mPlainText = plainText;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }
}
