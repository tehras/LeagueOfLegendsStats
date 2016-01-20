package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by tehras on 1/19/16.
 * <p/>
 * Favorites will contain the array of favorites as well as the comparators and other helpers
 */
public class Favorites {

    public Favorites() {
        mFavorites = new ArrayList<>();
    }

    @SerializedName("favorites")
    private ArrayList<Favorite> mFavorites;

    public void addFavorite(Favorite favorite) {
        if (mFavorites == null)
            mFavorites = new ArrayList<>();

        mFavorites.add(favorite);
    }

    public void removeFavorite(Favorite favorite) {
        if (mFavorites != null)
            mFavorites.remove(favorite);
    }

    public ArrayList<Favorite> getFavorites() {
        return mFavorites;
    }


    public static String toJson(Favorites favorites) {
        return new Gson().toJson(favorites);
    }

    public String toJson() {
        return toJson(this);
    }

    public static Favorites fromJson(String string) {
        return new Gson().fromJson(string, Favorites.class);
    }

    public class WinComparator implements Comparator<Favorite> {
        @Override
        public int compare(Favorite lhs, Favorite rhs) {
            return rhs.getWins() - lhs.getWins();
        }
    }

    public class LossComparator implements Comparator<Favorite> {
        @Override
        public int compare(Favorite lhs, Favorite rhs) {
            return rhs.getLosses() - lhs.getLosses();
        }
    }

    public class WinPComparator implements Comparator<Favorite> {
        @Override
        public int compare(Favorite lhs, Favorite rhs) {
            double lhsWinP = getWinP(lhs);
            double rhsWinP = getWinP(rhs);

            return Double.compare(rhsWinP, lhsWinP);
        }

        private double getWinP(Favorite lhs) {
            double wins = lhs.getWins();
            double losses = lhs.getLosses();

            return (wins * 100d) / (wins + losses);
        }
    }

    public class DateAddedComparator implements Comparator<Favorite> {
        @Override
        public int compare(Favorite lhs, Favorite rhs) {
            return Long.compare(rhs.getDate(), lhs.getDate());
        }
    }

    public class AlphabeticalComparator implements Comparator<Favorite> {
        @Override
        public int compare(Favorite lhs, Favorite rhs) {
            return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
        }
    }

    public enum SortBy {
        WINS, LOSSES, WIN_P, DATE_ADDED, ALPHABETICAL
    }
}
