package com.github.koshkin.leagueoflegendsstats.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.PlayerSummary;

import java.util.ArrayList;

/**
 * Created by tehras on 1/10/16.
 * <p/>
 * Generic utils class for all the reusable elements
 */
@SuppressWarnings("deprecation")
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

    /**
     * get color(int) for based on KDA
     *
     * @param kda     0 - inf
     * @param context prefer Activity
     * @return color int
     */
    public static int getKDAColor(Double kda, Context context) {
        if (kda == null) {
            return context.getResources().getColor(R.color.bad);
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

        return context.getResources().getColor(colorInt);
    }

    /**
     * Assigns color based on win %
     *
     * @param textView      win% textView
     * @param winPercentage win% - 0% to 100%
     * @param context       prefer Activity
     */
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

    public static void setUpRecyclerView(RecyclerView recyclerView, Activity activity, final SwipeRefreshLayout refreshLayout) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutParams = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutParams);
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                refreshLayout.setEnabled(layoutParams.findFirstCompletelyVisibleItemPosition() == 0);
//            }
//        });
    }
}
