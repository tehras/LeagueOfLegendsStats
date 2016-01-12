package com.github.koshkin.leagueoflegendsstats.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.PlayerSummary;

import java.util.ArrayList;

/**
 * Created by tehras on 1/10/16.
 */
public class Utils {

    public static final String NOT_AVAILABLE = "N/A";

    public static PlayerSummary getRankedStats(ArrayList<PlayerSummary> playerSummaries) {
        if (playerSummaries != null)
            for (PlayerSummary playerSummary : playerSummaries) {
                if (playerSummary.getSummaryType() == PlayerSummary.SummaryType.RANKED_SOLO_5X)
                    return playerSummary;
            }

        return null;
    }

    public static String getTextSafely(int wins) {
        if (wins != -1)
            return String.valueOf(wins);
        else
            return NOT_AVAILABLE;
    }

    public static String getTextSafely(Double wins) {
        if (wins != null)
            return String.valueOf(wins);
        else
            return NOT_AVAILABLE;
    }

    public static void addToLayout(ViewGroup linearLayout, View selectableView) {
        if (selectableView == null || linearLayout == null)
            return;

        linearLayout.addView(selectableView);
    }

    public static int getKDAColor(Double kda, Context context) {
        if (kda == null) {
            return context.getColor(R.color.bad);
        }

        int colorInt;
        if (kda < 2.0) {
            colorInt = R.color.bad;
        } else if (kda <= 3.0) {
            colorInt = R.color.average;
        } else if (kda <= 4.5) {
            colorInt = R.color.good;
        } else {
            colorInt = R.color.great;
        }

        return context.getColor(colorInt);
    }

    public static void assignWinPercentageColor(TextView textView, Double winPercentage, Context context) {
        if (winPercentage == null)
            return;

        int colorId;
        if (winPercentage > 70d) {
            colorId = R.color.great;
        } else if (winPercentage > 55d) {
            colorId = R.color.good;
        } else if (winPercentage > 45d) {
            colorId = R.color.average;
        } else {
            colorId = R.color.bad;
        }

        textView.setTextColor(context.getResources().getColor(colorId));
    }

    public static void setUpRecyclerView(RecyclerView recyclerView) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }
}
