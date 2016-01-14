package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/12/16.
 */
public class GameStats {

    @SerializedName("doubleKills")
    @Expose
    private int doubleKills;
    @SerializedName("tripleKills")
    @Expose
    private int tripleKills;
    @SerializedName("quadraKills")
    @Expose
    private int quadraKills;
    @SerializedName("pentaKills")
    @Expose
    private int pentaKills;
    @SerializedName("level")
    @Expose
    private int level;
    @SerializedName("goldEarned")
    @Expose
    private long goldEarned;
    @SerializedName("numDeaths")
    @Expose
    private int numDeaths;
    @SerializedName("minionsKilled")
    @Expose
    private int minionsKilled;
    @SerializedName("goldSpent")
    @Expose
    private long goldSpent;
    @SerializedName("totalDamageDealt")
    @Expose
    private long totalDamageDealt;
    @SerializedName("totalDamageTaken")
    @Expose
    private long totalDamageTaken;
    @SerializedName("team")
    @Expose
    private TeamSide team;
    @SerializedName("win")
    @Expose
    private boolean win;
    @SerializedName("neutralMinionsKilled")
    @Expose
    private int neutralMinionsKilled;
    @SerializedName("physicalDamageDealtPlayer")
    @Expose
    private long physicalDamageDealtPlayer;
    @SerializedName("magicDamageDealtPlayer")
    @Expose
    private long magicDamageDealtPlayer;
    @SerializedName("physicalDamageTaken")
    @Expose
    private long physicalDamageTaken;
    @SerializedName("magicDamageTaken")
    @Expose
    private long magicDamageTaken;
    @SerializedName("largestCriticalStrike")
    @Expose
    private long largestCriticalStrike;
    @SerializedName("timePlayed")
    @Expose
    private long timePlayed;
    @SerializedName("totalHeal")
    @Expose
    private long totalHeal;
    @SerializedName("totalUnitsHealed")
    @Expose
    private int totalUnitsHealed;
    @SerializedName("assists")
    @Expose
    private int assists;
    @SerializedName("item0")
    @Expose
    private int item0;
    @SerializedName("item1")
    @Expose
    private int item1;
    @SerializedName("item2")
    @Expose
    private int item2;
    @SerializedName("item3")
    @Expose
    private int item3;
    @SerializedName("item4")
    @Expose
    private int item4;
    @SerializedName("item5")
    @Expose
    private int item5;
    @SerializedName("item6")
    @Expose
    private int item6;
    @SerializedName("magicDamageDealtToChampions")
    @Expose
    private long magicDamageDealtToChampions;
    @SerializedName("physicalDamageDealtToChampions")
    @Expose
    private long physicalDamageDealtToChampions;
    @SerializedName("totalDamageDealtToChampions")
    @Expose
    private long totalDamageDealtToChampions;
    @SerializedName("trueDamageDealtPlayer")
    @Expose
    private long trueDamageDealtPlayer;
    @SerializedName("trueDamageDealtToChampions")
    @Expose
    private long trueDamageDealtToChampions;
    @SerializedName("trueDamageTaken")
    @Expose
    private long trueDamageTaken;
    @SerializedName("wardPlaced")
    @Expose
    private int wardPlaced;
    @SerializedName("neutralMinionsKilledYourJungle")
    @Expose
    private long neutralMinionsKilledYourJungle;
    @SerializedName("totalTimeCrowdControlDealt")
    @Expose
    private long totalTimeCrowdControlDealt;
    @SerializedName("playerRole")
    @Expose
    private int playerRole;
    @SerializedName("playerPosition")
    @Expose
    private int playerPosition;
    @SerializedName("championsKilled")
    @Expose
    private int championsKilled;

    public int getDoubleKills() {
        return doubleKills;
    }

    public void setDoubleKills(int doubleKills) {
        this.doubleKills = doubleKills;
    }

    public int getTripleKills() {
        return tripleKills;
    }

    public void setTripleKills(int tripleKills) {
        this.tripleKills = tripleKills;
    }

    public int getQuadraKills() {
        return quadraKills;
    }

    public void setQuadraKills(int quadraKills) {
        this.quadraKills = quadraKills;
    }

    public int getPentaKills() {
        return pentaKills;
    }

    public void setPentaKills(int pentaKills) {
        this.pentaKills = pentaKills;
    }

    public int getChampionsKilled() {
        return championsKilled;
    }

    public void setChampionsKilled(int championsKilled) {
        this.championsKilled = championsKilled;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getGoldEarned() {
        return goldEarned;
    }

    public void setGoldEarned(long goldEarned) {
        this.goldEarned = goldEarned;
    }

    public int getNumDeaths() {
        return numDeaths;
    }

    public void setNumDeaths(int numDeaths) {
        this.numDeaths = numDeaths;
    }

    public int getMinionsKilled() {
        return minionsKilled;
    }

    public void setMinionsKilled(int minionsKilled) {
        this.minionsKilled = minionsKilled;
    }

    public long getGoldSpent() {
        return goldSpent;
    }

    public void setGoldSpent(long goldSpent) {
        this.goldSpent = goldSpent;
    }

    public long getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public void setTotalDamageDealt(long totalDamageDealt) {
        this.totalDamageDealt = totalDamageDealt;
    }

    public long getTotalDamageTaken() {
        return totalDamageTaken;
    }

    public void setTotalDamageTaken(long totalDamageTaken) {
        this.totalDamageTaken = totalDamageTaken;
    }

    public TeamSide getTeam() {
        return team;
    }

    public void setTeam(TeamSide team) {
        this.team = team;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int getNeutralMinionsKilled() {
        return neutralMinionsKilled;
    }

    public void setNeutralMinionsKilled(int neutralMinionsKilled) {
        this.neutralMinionsKilled = neutralMinionsKilled;
    }

    public long getPhysicalDamageDealtPlayer() {
        return physicalDamageDealtPlayer;
    }

    public void setPhysicalDamageDealtPlayer(long physicalDamageDealtPlayer) {
        this.physicalDamageDealtPlayer = physicalDamageDealtPlayer;
    }

    public long getMagicDamageDealtPlayer() {
        return magicDamageDealtPlayer;
    }

    public void setMagicDamageDealtPlayer(long magicDamageDealtPlayer) {
        this.magicDamageDealtPlayer = magicDamageDealtPlayer;
    }

    public long getPhysicalDamageTaken() {
        return physicalDamageTaken;
    }

    public void setPhysicalDamageTaken(long physicalDamageTaken) {
        this.physicalDamageTaken = physicalDamageTaken;
    }

    public long getMagicDamageTaken() {
        return magicDamageTaken;
    }

    public void setMagicDamageTaken(long magicDamageTaken) {
        this.magicDamageTaken = magicDamageTaken;
    }

    public long getLargestCriticalStrike() {
        return largestCriticalStrike;
    }

    public void setLargestCriticalStrike(long largestCriticalStrike) {
        this.largestCriticalStrike = largestCriticalStrike;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(long timePlayed) {
        this.timePlayed = timePlayed;
    }

    public long getTotalHeal() {
        return totalHeal;
    }

    public void setTotalHeal(long totalHeal) {
        this.totalHeal = totalHeal;
    }

    public int getTotalUnitsHealed() {
        return totalUnitsHealed;
    }

    public void setTotalUnitsHealed(int totalUnitsHealed) {
        this.totalUnitsHealed = totalUnitsHealed;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getItem0() {
        return item0;
    }

    public void setItem0(int item0) {
        this.item0 = item0;
    }

    public int getItem1() {
        return item1;
    }

    public void setItem1(int item1) {
        this.item1 = item1;
    }

    public int getItem2() {
        return item2;
    }

    public void setItem2(int item2) {
        this.item2 = item2;
    }

    public int getItem3() {
        return item3;
    }

    public void setItem3(int item3) {
        this.item3 = item3;
    }

    public int getItem4() {
        return item4;
    }

    public void setItem4(int item4) {
        this.item4 = item4;
    }

    public int getItem5() {
        return item5;
    }

    public void setItem5(int item5) {
        this.item5 = item5;
    }

    public int getItem6() {
        return item6;
    }

    public void setItem6(int item6) {
        this.item6 = item6;
    }

    public long getMagicDamageDealtToChampions() {
        return magicDamageDealtToChampions;
    }

    public void setMagicDamageDealtToChampions(long magicDamageDealtToChampions) {
        this.magicDamageDealtToChampions = magicDamageDealtToChampions;
    }

    public long getPhysicalDamageDealtToChampions() {
        return physicalDamageDealtToChampions;
    }

    public void setPhysicalDamageDealtToChampions(long physicalDamageDealtToChampions) {
        this.physicalDamageDealtToChampions = physicalDamageDealtToChampions;
    }

    public long getTotalDamageDealtToChampions() {
        return totalDamageDealtToChampions;
    }

    public void setTotalDamageDealtToChampions(long totalDamageDealtToChampions) {
        this.totalDamageDealtToChampions = totalDamageDealtToChampions;
    }

    public long getTrueDamageDealtPlayer() {
        return trueDamageDealtPlayer;
    }

    public void setTrueDamageDealtPlayer(long trueDamageDealtPlayer) {
        this.trueDamageDealtPlayer = trueDamageDealtPlayer;
    }

    public long getTrueDamageDealtToChampions() {
        return trueDamageDealtToChampions;
    }

    public void setTrueDamageDealtToChampions(long trueDamageDealtToChampions) {
        this.trueDamageDealtToChampions = trueDamageDealtToChampions;
    }

    public long getTrueDamageTaken() {
        return trueDamageTaken;
    }

    public void setTrueDamageTaken(long trueDamageTaken) {
        this.trueDamageTaken = trueDamageTaken;
    }

    public int getWardPlaced() {
        return wardPlaced;
    }

    public void setWardPlaced(int wardPlaced) {
        this.wardPlaced = wardPlaced;
    }

    public long getNeutralMinionsKilledYourJungle() {
        return neutralMinionsKilledYourJungle;
    }

    public void setNeutralMinionsKilledYourJungle(long neutralMinionsKilledYourJungle) {
        this.neutralMinionsKilledYourJungle = neutralMinionsKilledYourJungle;
    }

    public long getTotalTimeCrowdControlDealt() {
        return totalTimeCrowdControlDealt;
    }

    public void setTotalTimeCrowdControlDealt(long totalTimeCrowdControlDealt) {
        this.totalTimeCrowdControlDealt = totalTimeCrowdControlDealt;
    }

    public PlayerRole getPlayerRole() {
        return PlayerRole.get(playerRole);
    }

    public void setPlayerRole(int playerRole) {
        this.playerRole = playerRole;
    }

    public PlayerPosition getPlayerPosition() {
        return PlayerPosition.get(playerPosition);
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

    public enum PlayerRole {
        DUO(1), SUPPORT(2), CARRY(3), SOLO(4);

        private final int mRoleInt;

        PlayerRole(int i) {
            mRoleInt = i;
        }

        static PlayerRole get(int i) {
            for (PlayerRole position : values()) {
                if (position.mRoleInt == i)
                    return position;
            }

            return SOLO;
        }
    }

    public enum PlayerPosition {
        TOP(1), MIDDLE(2), JUNGLE(3), BOT(4);

        private final int mPosInt;

        PlayerPosition(int i) {
            mPosInt = i;
        }

        static PlayerPosition get(int i) {
            for (PlayerPosition position : values()) {
                if (position.mPosInt == i)
                    return position;
            }

            return TOP;
        }
    }
}
