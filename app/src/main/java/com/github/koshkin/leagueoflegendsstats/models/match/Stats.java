package com.github.koshkin.leagueoflegendsstats.models.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {

    @SerializedName("winner")
    @Expose
    private boolean winner;
    @SerializedName("champLevel")
    @Expose
    private int champLevel;
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
    @SerializedName("kills")
    @Expose
    private int kills;
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
    @SerializedName("unrealKills")
    @Expose
    private int unrealKills;
    @SerializedName("largestKillingSpree")
    @Expose
    private int largestKillingSpree;
    @SerializedName("deaths")
    @Expose
    private int deaths;
    @SerializedName("assists")
    @Expose
    private int assists;
    @SerializedName("totalDamageDealt")
    @Expose
    private int totalDamageDealt;
    @SerializedName("totalDamageDealtToChampions")
    @Expose
    private int totalDamageDealtToChampions;
    @SerializedName("totalDamageTaken")
    @Expose
    private int totalDamageTaken;
    @SerializedName("largestCriticalStrike")
    @Expose
    private int largestCriticalStrike;
    @SerializedName("totalHeal")
    @Expose
    private int totalHeal;
    @SerializedName("minionsKilled")
    @Expose
    private int minionsKilled;
    @SerializedName("neutralMinionsKilled")
    @Expose
    private int neutralMinionsKilled;
    @SerializedName("neutralMinionsKilledTeamJungle")
    @Expose
    private int neutralMinionsKilledTeamJungle;
    @SerializedName("neutralMinionsKilledEnemyJungle")
    @Expose
    private int neutralMinionsKilledEnemyJungle;
    @SerializedName("goldEarned")
    @Expose
    private int goldEarned;
    @SerializedName("goldSpent")
    @Expose
    private int goldSpent;
    @SerializedName("combatPlayerScore")
    @Expose
    private int combatPlayerScore;
    @SerializedName("objectivePlayerScore")
    @Expose
    private int objectivePlayerScore;
    @SerializedName("totalPlayerScore")
    @Expose
    private int totalPlayerScore;
    @SerializedName("totalScoreRank")
    @Expose
    private int totalScoreRank;
    @SerializedName("magicDamageDealtToChampions")
    @Expose
    private int magicDamageDealtToChampions;
    @SerializedName("physicalDamageDealtToChampions")
    @Expose
    private int physicalDamageDealtToChampions;
    @SerializedName("trueDamageDealtToChampions")
    @Expose
    private int trueDamageDealtToChampions;
    @SerializedName("visionWardsBoughtInGame")
    @Expose
    private int visionWardsBoughtInGame;
    @SerializedName("sightWardsBoughtInGame")
    @Expose
    private int sightWardsBoughtInGame;
    @SerializedName("magicDamageDealt")
    @Expose
    private int magicDamageDealt;
    @SerializedName("physicalDamageDealt")
    @Expose
    private int physicalDamageDealt;
    @SerializedName("trueDamageDealt")
    @Expose
    private int trueDamageDealt;
    @SerializedName("magicDamageTaken")
    @Expose
    private int magicDamageTaken;
    @SerializedName("physicalDamageTaken")
    @Expose
    private int physicalDamageTaken;
    @SerializedName("trueDamageTaken")
    @Expose
    private int trueDamageTaken;
    @SerializedName("firstBloodKill")
    @Expose
    private boolean firstBloodKill;
    @SerializedName("firstBloodAssist")
    @Expose
    private boolean firstBloodAssist;
    @SerializedName("firstTowerKill")
    @Expose
    private boolean firstTowerKill;
    @SerializedName("firstTowerAssist")
    @Expose
    private boolean firstTowerAssist;
    @SerializedName("firstInhibitorKill")
    @Expose
    private boolean firstInhibitorKill;
    @SerializedName("firstInhibitorAssist")
    @Expose
    private boolean firstInhibitorAssist;
    @SerializedName("inhibitorKills")
    @Expose
    private int inhibitorKills;
    @SerializedName("towerKills")
    @Expose
    private int towerKills;
    @SerializedName("wardsPlaced")
    @Expose
    private int wardsPlaced;
    @SerializedName("wardsKilled")
    @Expose
    private int wardsKilled;
    @SerializedName("largestMultiKill")
    @Expose
    private int largestMultiKill;
    @SerializedName("killingSprees")
    @Expose
    private int killingSprees;
    @SerializedName("totalUnitsHealed")
    @Expose
    private int totalUnitsHealed;
    @SerializedName("totalTimeCrowdControlDealt")
    @Expose
    private int totalTimeCrowdControlDealt;

    /**
     * @return The winner
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     * @param winner The winner
     */
    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    /**
     * @return The champLevel
     */
    public int getChampLevel() {
        return champLevel;
    }

    /**
     * @param champLevel The champLevel
     */
    public void setChampLevel(int champLevel) {
        this.champLevel = champLevel;
    }

    /**
     * @return The item0
     */
    public int getItem0() {
        return item0;
    }

    /**
     * @param item0 The item0
     */
    public void setItem0(int item0) {
        this.item0 = item0;
    }

    /**
     * @return The item1
     */
    public int getItem1() {
        return item1;
    }

    /**
     * @param item1 The item1
     */
    public void setItem1(int item1) {
        this.item1 = item1;
    }

    /**
     * @return The item2
     */
    public int getItem2() {
        return item2;
    }

    /**
     * @param item2 The item2
     */
    public void setItem2(int item2) {
        this.item2 = item2;
    }

    /**
     * @return The item3
     */
    public int getItem3() {
        return item3;
    }

    /**
     * @param item3 The item3
     */
    public void setItem3(int item3) {
        this.item3 = item3;
    }

    /**
     * @return The item4
     */
    public int getItem4() {
        return item4;
    }

    /**
     * @param item4 The item4
     */
    public void setItem4(int item4) {
        this.item4 = item4;
    }

    /**
     * @return The item5
     */
    public int getItem5() {
        return item5;
    }

    /**
     * @param item5 The item5
     */
    public void setItem5(int item5) {
        this.item5 = item5;
    }

    /**
     * @return The item6
     */
    public int getItem6() {
        return item6;
    }

    /**
     * @param item6 The item6
     */
    public void setItem6(int item6) {
        this.item6 = item6;
    }

    /**
     * @return The kills
     */
    public int getKills() {
        return kills;
    }

    /**
     * @param kills The kills
     */
    public void setKills(int kills) {
        this.kills = kills;
    }

    /**
     * @return The doubleKills
     */
    public int getDoubleKills() {
        return doubleKills;
    }

    /**
     * @param doubleKills The doubleKills
     */
    public void setDoubleKills(int doubleKills) {
        this.doubleKills = doubleKills;
    }

    /**
     * @return The tripleKills
     */
    public int getTripleKills() {
        return tripleKills;
    }

    /**
     * @param tripleKills The tripleKills
     */
    public void setTripleKills(int tripleKills) {
        this.tripleKills = tripleKills;
    }

    /**
     * @return The quadraKills
     */
    public int getQuadraKills() {
        return quadraKills;
    }

    /**
     * @param quadraKills The quadraKills
     */
    public void setQuadraKills(int quadraKills) {
        this.quadraKills = quadraKills;
    }

    /**
     * @return The pentaKills
     */
    public int getPentaKills() {
        return pentaKills;
    }

    /**
     * @param pentaKills The pentaKills
     */
    public void setPentaKills(int pentaKills) {
        this.pentaKills = pentaKills;
    }

    /**
     * @return The unrealKills
     */
    public int getUnrealKills() {
        return unrealKills;
    }

    /**
     * @param unrealKills The unrealKills
     */
    public void setUnrealKills(int unrealKills) {
        this.unrealKills = unrealKills;
    }

    /**
     * @return The largestKillingSpree
     */
    public int getLargestKillingSpree() {
        return largestKillingSpree;
    }

    /**
     * @param largestKillingSpree The largestKillingSpree
     */
    public void setLargestKillingSpree(int largestKillingSpree) {
        this.largestKillingSpree = largestKillingSpree;
    }

    /**
     * @return The deaths
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * @param deaths The deaths
     */
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    /**
     * @return The assists
     */
    public int getAssists() {
        return assists;
    }

    /**
     * @param assists The assists
     */
    public void setAssists(int assists) {
        this.assists = assists;
    }

    /**
     * @return The totalDamageDealt
     */
    public int getTotalDamageDealt() {
        return totalDamageDealt;
    }

    /**
     * @param totalDamageDealt The totalDamageDealt
     */
    public void setTotalDamageDealt(int totalDamageDealt) {
        this.totalDamageDealt = totalDamageDealt;
    }

    /**
     * @return The totalDamageDealtToChampions
     */
    public int getTotalDamageDealtToChampions() {
        return totalDamageDealtToChampions;
    }

    /**
     * @param totalDamageDealtToChampions The totalDamageDealtToChampions
     */
    public void setTotalDamageDealtToChampions(int totalDamageDealtToChampions) {
        this.totalDamageDealtToChampions = totalDamageDealtToChampions;
    }

    /**
     * @return The totalDamageTaken
     */
    public int getTotalDamageTaken() {
        return totalDamageTaken;
    }

    /**
     * @param totalDamageTaken The totalDamageTaken
     */
    public void setTotalDamageTaken(int totalDamageTaken) {
        this.totalDamageTaken = totalDamageTaken;
    }

    /**
     * @return The largestCriticalStrike
     */
    public int getLargestCriticalStrike() {
        return largestCriticalStrike;
    }

    /**
     * @param largestCriticalStrike The largestCriticalStrike
     */
    public void setLargestCriticalStrike(int largestCriticalStrike) {
        this.largestCriticalStrike = largestCriticalStrike;
    }

    /**
     * @return The totalHeal
     */
    public int getTotalHeal() {
        return totalHeal;
    }

    /**
     * @param totalHeal The totalHeal
     */
    public void setTotalHeal(int totalHeal) {
        this.totalHeal = totalHeal;
    }

    /**
     * @return The minionsKilled
     */
    public int getMinionsKilled() {
        return minionsKilled;
    }

    /**
     * @param minionsKilled The minionsKilled
     */
    public void setMinionsKilled(int minionsKilled) {
        this.minionsKilled = minionsKilled;
    }

    /**
     * @return The neutralMinionsKilled
     */
    public int getNeutralMinionsKilled() {
        return neutralMinionsKilled;
    }

    /**
     * @param neutralMinionsKilled The neutralMinionsKilled
     */
    public void setNeutralMinionsKilled(int neutralMinionsKilled) {
        this.neutralMinionsKilled = neutralMinionsKilled;
    }

    /**
     * @return The neutralMinionsKilledTeamJungle
     */
    public int getNeutralMinionsKilledTeamJungle() {
        return neutralMinionsKilledTeamJungle;
    }

    /**
     * @param neutralMinionsKilledTeamJungle The neutralMinionsKilledTeamJungle
     */
    public void setNeutralMinionsKilledTeamJungle(int neutralMinionsKilledTeamJungle) {
        this.neutralMinionsKilledTeamJungle = neutralMinionsKilledTeamJungle;
    }

    /**
     * @return The neutralMinionsKilledEnemyJungle
     */
    public int getNeutralMinionsKilledEnemyJungle() {
        return neutralMinionsKilledEnemyJungle;
    }

    /**
     * @param neutralMinionsKilledEnemyJungle The neutralMinionsKilledEnemyJungle
     */
    public void setNeutralMinionsKilledEnemyJungle(int neutralMinionsKilledEnemyJungle) {
        this.neutralMinionsKilledEnemyJungle = neutralMinionsKilledEnemyJungle;
    }

    /**
     * @return The goldEarned
     */
    public int getGoldEarned() {
        return goldEarned;
    }

    /**
     * @param goldEarned The goldEarned
     */
    public void setGoldEarned(int goldEarned) {
        this.goldEarned = goldEarned;
    }

    /**
     * @return The goldSpent
     */
    public int getGoldSpent() {
        return goldSpent;
    }

    /**
     * @param goldSpent The goldSpent
     */
    public void setGoldSpent(int goldSpent) {
        this.goldSpent = goldSpent;
    }

    /**
     * @return The combatPlayerScore
     */
    public int getCombatPlayerScore() {
        return combatPlayerScore;
    }

    /**
     * @param combatPlayerScore The combatPlayerScore
     */
    public void setCombatPlayerScore(int combatPlayerScore) {
        this.combatPlayerScore = combatPlayerScore;
    }

    /**
     * @return The objectivePlayerScore
     */
    public int getObjectivePlayerScore() {
        return objectivePlayerScore;
    }

    /**
     * @param objectivePlayerScore The objectivePlayerScore
     */
    public void setObjectivePlayerScore(int objectivePlayerScore) {
        this.objectivePlayerScore = objectivePlayerScore;
    }

    /**
     * @return The totalPlayerScore
     */
    public int getTotalPlayerScore() {
        return totalPlayerScore;
    }

    /**
     * @param totalPlayerScore The totalPlayerScore
     */
    public void setTotalPlayerScore(int totalPlayerScore) {
        this.totalPlayerScore = totalPlayerScore;
    }

    /**
     * @return The totalScoreRank
     */
    public int getTotalScoreRank() {
        return totalScoreRank;
    }

    /**
     * @param totalScoreRank The totalScoreRank
     */
    public void setTotalScoreRank(int totalScoreRank) {
        this.totalScoreRank = totalScoreRank;
    }

    /**
     * @return The magicDamageDealtToChampions
     */
    public int getMagicDamageDealtToChampions() {
        return magicDamageDealtToChampions;
    }

    /**
     * @param magicDamageDealtToChampions The magicDamageDealtToChampions
     */
    public void setMagicDamageDealtToChampions(int magicDamageDealtToChampions) {
        this.magicDamageDealtToChampions = magicDamageDealtToChampions;
    }

    /**
     * @return The physicalDamageDealtToChampions
     */
    public int getPhysicalDamageDealtToChampions() {
        return physicalDamageDealtToChampions;
    }

    /**
     * @param physicalDamageDealtToChampions The physicalDamageDealtToChampions
     */
    public void setPhysicalDamageDealtToChampions(int physicalDamageDealtToChampions) {
        this.physicalDamageDealtToChampions = physicalDamageDealtToChampions;
    }

    /**
     * @return The trueDamageDealtToChampions
     */
    public int getTrueDamageDealtToChampions() {
        return trueDamageDealtToChampions;
    }

    /**
     * @param trueDamageDealtToChampions The trueDamageDealtToChampions
     */
    public void setTrueDamageDealtToChampions(int trueDamageDealtToChampions) {
        this.trueDamageDealtToChampions = trueDamageDealtToChampions;
    }

    /**
     * @return The visionWardsBoughtInGame
     */
    public int getVisionWardsBoughtInGame() {
        return visionWardsBoughtInGame;
    }

    /**
     * @param visionWardsBoughtInGame The visionWardsBoughtInGame
     */
    public void setVisionWardsBoughtInGame(int visionWardsBoughtInGame) {
        this.visionWardsBoughtInGame = visionWardsBoughtInGame;
    }

    /**
     * @return The sightWardsBoughtInGame
     */
    public int getSightWardsBoughtInGame() {
        return sightWardsBoughtInGame;
    }

    /**
     * @param sightWardsBoughtInGame The sightWardsBoughtInGame
     */
    public void setSightWardsBoughtInGame(int sightWardsBoughtInGame) {
        this.sightWardsBoughtInGame = sightWardsBoughtInGame;
    }

    /**
     * @return The magicDamageDealt
     */
    public int getMagicDamageDealt() {
        return magicDamageDealt;
    }

    /**
     * @param magicDamageDealt The magicDamageDealt
     */
    public void setMagicDamageDealt(int magicDamageDealt) {
        this.magicDamageDealt = magicDamageDealt;
    }

    /**
     * @return The physicalDamageDealt
     */
    public int getPhysicalDamageDealt() {
        return physicalDamageDealt;
    }

    /**
     * @param physicalDamageDealt The physicalDamageDealt
     */
    public void setPhysicalDamageDealt(int physicalDamageDealt) {
        this.physicalDamageDealt = physicalDamageDealt;
    }

    /**
     * @return The trueDamageDealt
     */
    public int getTrueDamageDealt() {
        return trueDamageDealt;
    }

    /**
     * @param trueDamageDealt The trueDamageDealt
     */
    public void setTrueDamageDealt(int trueDamageDealt) {
        this.trueDamageDealt = trueDamageDealt;
    }

    /**
     * @return The magicDamageTaken
     */
    public int getMagicDamageTaken() {
        return magicDamageTaken;
    }

    /**
     * @param magicDamageTaken The magicDamageTaken
     */
    public void setMagicDamageTaken(int magicDamageTaken) {
        this.magicDamageTaken = magicDamageTaken;
    }

    /**
     * @return The physicalDamageTaken
     */
    public int getPhysicalDamageTaken() {
        return physicalDamageTaken;
    }

    /**
     * @param physicalDamageTaken The physicalDamageTaken
     */
    public void setPhysicalDamageTaken(int physicalDamageTaken) {
        this.physicalDamageTaken = physicalDamageTaken;
    }

    /**
     * @return The trueDamageTaken
     */
    public int getTrueDamageTaken() {
        return trueDamageTaken;
    }

    /**
     * @param trueDamageTaken The trueDamageTaken
     */
    public void setTrueDamageTaken(int trueDamageTaken) {
        this.trueDamageTaken = trueDamageTaken;
    }

    /**
     * @return The firstBloodKill
     */
    public boolean isFirstBloodKill() {
        return firstBloodKill;
    }

    /**
     * @param firstBloodKill The firstBloodKill
     */
    public void setFirstBloodKill(boolean firstBloodKill) {
        this.firstBloodKill = firstBloodKill;
    }

    /**
     * @return The firstBloodAssist
     */
    public boolean isFirstBloodAssist() {
        return firstBloodAssist;
    }

    /**
     * @param firstBloodAssist The firstBloodAssist
     */
    public void setFirstBloodAssist(boolean firstBloodAssist) {
        this.firstBloodAssist = firstBloodAssist;
    }

    /**
     * @return The firstTowerKill
     */
    public boolean isFirstTowerKill() {
        return firstTowerKill;
    }

    /**
     * @param firstTowerKill The firstTowerKill
     */
    public void setFirstTowerKill(boolean firstTowerKill) {
        this.firstTowerKill = firstTowerKill;
    }

    /**
     * @return The firstTowerAssist
     */
    public boolean isFirstTowerAssist() {
        return firstTowerAssist;
    }

    /**
     * @param firstTowerAssist The firstTowerAssist
     */
    public void setFirstTowerAssist(boolean firstTowerAssist) {
        this.firstTowerAssist = firstTowerAssist;
    }

    /**
     * @return The firstInhibitorKill
     */
    public boolean isFirstInhibitorKill() {
        return firstInhibitorKill;
    }

    /**
     * @param firstInhibitorKill The firstInhibitorKill
     */
    public void setFirstInhibitorKill(boolean firstInhibitorKill) {
        this.firstInhibitorKill = firstInhibitorKill;
    }

    /**
     * @return The firstInhibitorAssist
     */
    public boolean isFirstInhibitorAssist() {
        return firstInhibitorAssist;
    }

    /**
     * @param firstInhibitorAssist The firstInhibitorAssist
     */
    public void setFirstInhibitorAssist(boolean firstInhibitorAssist) {
        this.firstInhibitorAssist = firstInhibitorAssist;
    }

    /**
     * @return The inhibitorKills
     */
    public int getInhibitorKills() {
        return inhibitorKills;
    }

    /**
     * @param inhibitorKills The inhibitorKills
     */
    public void setInhibitorKills(int inhibitorKills) {
        this.inhibitorKills = inhibitorKills;
    }

    /**
     * @return The towerKills
     */
    public int getTowerKills() {
        return towerKills;
    }

    /**
     * @param towerKills The towerKills
     */
    public void setTowerKills(int towerKills) {
        this.towerKills = towerKills;
    }

    /**
     * @return The wardsPlaced
     */
    public int getWardsPlaced() {
        return wardsPlaced;
    }

    /**
     * @param wardsPlaced The wardsPlaced
     */
    public void setWardsPlaced(int wardsPlaced) {
        this.wardsPlaced = wardsPlaced;
    }

    /**
     * @return The wardsKilled
     */
    public int getWardsKilled() {
        return wardsKilled;
    }

    /**
     * @param wardsKilled The wardsKilled
     */
    public void setWardsKilled(int wardsKilled) {
        this.wardsKilled = wardsKilled;
    }

    /**
     * @return The largestMultiKill
     */
    public int getLargestMultiKill() {
        return largestMultiKill;
    }

    /**
     * @param largestMultiKill The largestMultiKill
     */
    public void setLargestMultiKill(int largestMultiKill) {
        this.largestMultiKill = largestMultiKill;
    }

    /**
     * @return The killingSprees
     */
    public int getKillingSprees() {
        return killingSprees;
    }

    /**
     * @param killingSprees The killingSprees
     */
    public void setKillingSprees(int killingSprees) {
        this.killingSprees = killingSprees;
    }

    /**
     * @return The totalUnitsHealed
     */
    public int getTotalUnitsHealed() {
        return totalUnitsHealed;
    }

    /**
     * @param totalUnitsHealed The totalUnitsHealed
     */
    public void setTotalUnitsHealed(int totalUnitsHealed) {
        this.totalUnitsHealed = totalUnitsHealed;
    }

    /**
     * @return The totalTimeCrowdControlDealt
     */
    public int getTotalTimeCrowdControlDealt() {
        return totalTimeCrowdControlDealt;
    }

    /**
     * @param totalTimeCrowdControlDealt The totalTimeCrowdControlDealt
     */
    public void setTotalTimeCrowdControlDealt(int totalTimeCrowdControlDealt) {
        this.totalTimeCrowdControlDealt = totalTimeCrowdControlDealt;
    }

}
