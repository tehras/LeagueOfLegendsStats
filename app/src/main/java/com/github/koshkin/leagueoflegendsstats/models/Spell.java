package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tehras on 1/13/16.
 */
public class Spell {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("tooltip")
    private String tooltip;
    @SerializedName("maxrank")
    private int maxrank;
    @SerializedName("cooldown")
    private List<Integer> cooldown;
    @SerializedName("cooldownBurn")
    private String cooldownBurn;
    @SerializedName("cost")
    private List<Integer> cost;
    @SerializedName("costBurn")
    private String costBurn;
    @SerializedName("key")
    private String key;
    @SerializedName("summonerLevel")
    private int summonerLevel;
    @SerializedName("modes")
    private List<String> modes;
    @SerializedName("costType")
    private String costType;
    //    @SerializedName("range")
//    private String range;
    @SerializedName("rangeBurn")
    private String rangeBurn;
    @SerializedName("image")
    private Image image;
    @SerializedName("resource")
    private String resource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public int getMaxrank() {
        return maxrank;
    }

    public void setMaxrank(int maxrank) {
        this.maxrank = maxrank;
    }

    public List<Integer> getCooldown() {
        return cooldown;
    }

    public void setCooldown(List<Integer> cooldown) {
        this.cooldown = cooldown;
    }

    public String getCooldownBurn() {
        return cooldownBurn;
    }

    public void setCooldownBurn(String cooldownBurn) {
        this.cooldownBurn = cooldownBurn;
    }

    public List<Integer> getCost() {
        return cost;
    }

    public void setCost(List<Integer> cost) {
        this.cost = cost;
    }

    public String getCostBurn() {
        return costBurn;
    }

    public void setCostBurn(String costBurn) {
        this.costBurn = costBurn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public List<String> getModes() {
        return modes;
    }

    public void setModes(List<String> modes) {
        this.modes = modes;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

//    public String getRange() {
//        return range;
//    }
//
//    public void setRange(String range) {
//        this.range = range;
//    }

    public String getRangeBurn() {
        return rangeBurn;
    }

    public void setRangeBurn(String rangeBurn) {
        this.rangeBurn = rangeBurn;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Spell parse(String body) {
        return new Gson().fromJson(body, Spell.class);
    }
}
