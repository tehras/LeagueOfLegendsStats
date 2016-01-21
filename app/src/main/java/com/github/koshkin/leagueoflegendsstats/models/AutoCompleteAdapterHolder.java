package com.github.koshkin.leagueoflegendsstats.models;

/**
 * Created by tehras on 1/20/16.
 */
public class AutoCompleteAdapterHolder {

    private String mName;
    private AutoCompleteType mAutoCompleteType;

    public AutoCompleteAdapterHolder(String name, AutoCompleteType autoCompleteType) {
        mName = name;
        mAutoCompleteType = autoCompleteType;
    }

    public String getName() {
        return mName;
    }

    public AutoCompleteType getAutoCompleteType() {
        return mAutoCompleteType;
    }

    public enum AutoCompleteType {
        FAVORITES, RECENT
    }
}
