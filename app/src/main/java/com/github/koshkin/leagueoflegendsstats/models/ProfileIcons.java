package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by tehras on 1/11/16.
 */
public class ProfileIcons implements Request.ParserCallback<ProfileIcons> {

    @SerializedName("version")
    private String mVersion;
    @SerializedName("data")
    private Map<String, ProfileIcon> mProfileIcons;

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }

    public Map<String, ProfileIcon> getProfileIcons() {
        return mProfileIcons;
    }

    public void setProfileIcons(Map<String, ProfileIcon> profileIcons) {
        mProfileIcons = profileIcons;
    }

    @Override
    public ProfileIcons parse(String body) {
        return new Gson().fromJson(body, ProfileIcons.class);
    }

}
