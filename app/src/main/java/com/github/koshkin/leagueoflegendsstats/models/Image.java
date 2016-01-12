package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/11/16.
 */
public class Image {

    @SerializedName("full")
    private String mFull;
    @SerializedName("sprite")
    private String mSprite;
    @SerializedName("group")
    private String mGroup;
    @SerializedName("x")
    private int x;
    @SerializedName("y")
    private int y;
    @SerializedName("w")
    private int w;
    @SerializedName("h")
    private int h;

    public String getFull() {
        return mFull;
    }

    public void setFull(String full) {
        mFull = full;
    }

    public String getSprite() {
        return mSprite;
    }

    public void setSprite(String sprite) {
        mSprite = sprite;
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
