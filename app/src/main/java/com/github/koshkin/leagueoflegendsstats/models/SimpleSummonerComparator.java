package com.github.koshkin.leagueoflegendsstats.models;

import java.util.Comparator;

/**
 * Created by tehras on 1/19/16.
 * <p/>
 * SimpleSummonerComparator will contain the array of favorites as well as the comparators and other helpers
 */
public class SimpleSummonerComparator {

    public class WinComparator implements Comparator<SimpleSummoner> {
        @Override
        public int compare(SimpleSummoner lhs, SimpleSummoner rhs) {
            return rhs.getWins() - lhs.getWins();
        }
    }

    public class LossComparator implements Comparator<SimpleSummoner> {
        @Override
        public int compare(SimpleSummoner lhs, SimpleSummoner rhs) {
            return rhs.getLosses() - lhs.getLosses();
        }
    }

    public class WinPComparator implements Comparator<SimpleSummoner> {
        @Override
        public int compare(SimpleSummoner lhs, SimpleSummoner rhs) {
            double lhsWinP = getWinP(lhs);
            double rhsWinP = getWinP(rhs);

            return Double.compare(rhsWinP, lhsWinP);
        }

        private double getWinP(SimpleSummoner lhs) {
            double wins = lhs.getWins();
            double losses = lhs.getLosses();

            return (wins * 100d) / (wins + losses);
        }
    }

    public class DateAddedComparator implements Comparator<SimpleSummoner> {
        @Override
        public int compare(SimpleSummoner lhs, SimpleSummoner rhs) {
            return Long.compare(rhs.getDate(), lhs.getDate());
        }
    }

    public class AlphabeticalComparator implements Comparator<SimpleSummoner> {
        @Override
        public int compare(SimpleSummoner lhs, SimpleSummoner rhs) {
            return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
        }
    }

    public enum SortBy {
        WINS, LOSSES, WIN_P, DATE_ADDED, ALPHABETICAL
    }
}
