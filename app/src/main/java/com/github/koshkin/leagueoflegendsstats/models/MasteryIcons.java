package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by tehras on 1/23/16.
 */
public class MasteryIcons implements Request.ParserCallback<MasteryIcons> {

    public static final String FEROCITY = "Ferocity";
    public static final String CUNNING = "Cunning";
    public static final String RESOLVE = "Resolve";

    @SerializedName("version")
    private String mVersion;
    @SerializedName("tree")
    private HashMap<String, ArrayList<ArrayList<MasteryTree>>> mTreeMap;
    @SerializedName("data")
    private HashMap<String, MasteryIcon> mMasterIcons;

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }

    public HashMap<String, ArrayList<ArrayList<MasteryTree>>> getTreeMap() {
        return mTreeMap;
    }

    public void setTreeMap(HashMap<String, ArrayList<ArrayList<MasteryTree>>> treeMap) {
        mTreeMap = treeMap;
    }

    public HashMap<String, MasteryIcon> getMasterIcons() {
        return mMasterIcons;
    }

    public void setMasterIcons(HashMap<String, MasteryIcon> masterIcons) {
        mMasterIcons = masterIcons;
    }

    @Override
    public MasteryIcons parse(String body) {
        try {
            JSONObject reader = new JSONObject(body);
            if (reader.has("version"))
                mVersion = reader.getString("version");
            if (reader.has("data")) {
                JSONObject data = reader.getJSONObject("data");
                Iterator<String> keys = data.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (mMasterIcons == null)
                        mMasterIcons = new HashMap<>();

                    mMasterIcons.put(key, new Gson().fromJson(data.get(key).toString(), MasteryIcon.class));
                }
            }
            if (reader.has("tree")) {
                JSONObject tree = reader.getJSONObject("tree");
                Iterator<String> keys = tree.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    JSONArray mastery = tree.getJSONArray(key);
                    ArrayList<ArrayList<MasteryTree>> masteryTrees = new ArrayList<>();

                    for (int i = 0; i < mastery.length(); i++) {
                        JSONArray thisMaster = mastery.getJSONArray(i);
                        ArrayList<MasteryTree> innerMasteryTree = new ArrayList<>();

                        for (int j = 0; j < thisMaster.length(); j++) {
                            if (thisMaster.isNull(j)) {
                                innerMasteryTree.add(new MasteryTree());
                                continue;
                            }

                            JSONObject jsonObject = thisMaster.getJSONObject(j);
                            innerMasteryTree.add(new Gson().fromJson(jsonObject.toString(), MasteryTree.class));
                        }

                        masteryTrees.add(innerMasteryTree);
                    }

                    if (mTreeMap == null)
                        mTreeMap = new HashMap<>();

                    mTreeMap.put(key, masteryTrees);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return this;
//        return new Gson().fromJson(body, this.getClass());

    }
}
