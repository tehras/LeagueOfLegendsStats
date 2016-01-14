package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by tehras on 1/13/16.
 * <p/>
 * Spell Icons
 */
public class SpellIcons implements Request.ParserCallback<SpellIcons> {

    @SerializedName("data")
    private Map<String, Spell> mSpells;
    @SerializedName("version")
    private String mVersion;

    public Map<String, Spell> getSpells() {
        return mSpells;
    }

    public void setSpells(Map<String, Spell> spells) {
        mSpells = spells;
    }

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }

    @Override
    public SpellIcons parse(String body) {
        return new Gson().fromJson(body, SpellIcons.class);
    }

    public Spell getSpellIcon(String s) {
        for (Spell spell : mSpells.values()) {
            if (spell.getKey().equalsIgnoreCase(s))
                return spell;
        }

        return null;
    }
}
