package com.github.koshkin.leagueoflegendsstats.holders;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.Champion;
import com.github.koshkin.leagueoflegendsstats.models.PlayerRanked;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.utils.NumberUtils;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tehras on 1/24/16.
 */
public class MySummonerHolder {

    private final TextView mSummonerName, mWins, mLosses, mRankedKda, mRankedKills, mRankedDeaths, mRankedAssists;
    private final View mWinsLossesLayout, mRankedStatsLayout, mRankedErrorLayout;
    private final ImageView mSummonerIcon;

    public MySummonerHolder(View view) {
        mSummonerName = (TextView) view.findViewById(R.id.summoner_name);
        mLosses = (TextView) view.findViewById(R.id.overall_losses);
        mWins = (TextView) view.findViewById(R.id.overall_wins);
        mWinsLossesLayout = view.findViewById(R.id.wins_losses_layout);

        mRankedAssists = (TextView) view.findViewById(R.id.ranked_assists);
        mRankedKills = (TextView) view.findViewById(R.id.ranked_kills);
        mRankedDeaths = (TextView) view.findViewById(R.id.ranked_deaths);
        mRankedKda = (TextView) view.findViewById(R.id.ranked_kda);

        mRankedErrorLayout = view.findViewById(R.id.my_summoner_error_layout);
        mRankedStatsLayout = view.findViewById(R.id.my_summoner_ranked_stats_layout);

        mSummonerIcon = (ImageView) view.findViewById(R.id.summoner_icon);
    }

    public void populate(Summoner summoner, Activity activity) {
        mSummonerName.setText(summoner.getSummonerInfo().getName());

        populateIcon(summoner, activity);

        if (summoner.getPlayerRanked() != null && summoner.getPlayerRanked().getChampions() != null && summoner.getPlayerRanked().getChampions().size() > 0) {
            mRankedErrorLayout.setVisibility(View.GONE);
            mWinsLossesLayout.setVisibility(View.VISIBLE);
            mRankedStatsLayout.setVisibility(View.VISIBLE);

            mRankedKills.setText(NumberUtils.twoDecimals(summoner.getPlayerRanked().getKills()));
            mRankedDeaths.setText(NumberUtils.twoDecimals(summoner.getPlayerRanked().getDeaths()));
            mRankedAssists.setText(NumberUtils.twoDecimals(summoner.getPlayerRanked().getAssists()));
            mRankedKda.setText(getKdaText(mRankedKda, summoner.getPlayerRanked(), activity));

            mWins.setText(getWinsText(summoner));
            mLosses.setText(getLossesText(summoner));
        } else {
            mRankedErrorLayout.setVisibility(View.VISIBLE);
            mRankedStatsLayout.setVisibility(View.GONE);
            mWinsLossesLayout.setVisibility(View.GONE);
        }

    }

    private String getWinsText(Summoner summoner) {
        ArrayList<Champion> champs = summoner.getPlayerRanked().getChampions();
        Collections.sort(champs);

        //0th will be overall

        return String.valueOf(champs.get(0).getStats().getTotalSessionsWon()) + "W";
    }

    private String getLossesText(Summoner summoner) {
        ArrayList<Champion> champs = summoner.getPlayerRanked().getChampions();
        Collections.sort(champs);

        //0th will be overall

        return String.valueOf(champs.get(0).getStats().getTotalSessionsLost()) + "W";
    }

    private String getKdaText(TextView rankedKda, PlayerRanked playerRanked, Activity activity) {
        double kills = playerRanked.getKills();
        double deaths = playerRanked.getDeaths();
        double assists = playerRanked.getAssists();

        if (deaths == 0d) {
            rankedKda.setTextColor(Utils.getKDAColor(10d, activity));
            return "Perfect KDA";
        } else {
            double kda = ((kills + assists) / deaths);
            rankedKda.setTextColor(Utils.getKDAColor(kda, activity));
            return kda + ":1 KDA";
        }
    }

    private void populateIcon(final Summoner summoner, final Activity activity) {
        new LoaderHelper() {
            public Drawable mDrawable;

            @Override
            protected void postExecute() {
                if (mDrawable != null) {
                    mSummonerIcon.setImageDrawable(mDrawable);
                }
            }

            @Override
            protected void runInBackground() {
                mDrawable = StaticDataHolder.getInstance(activity).getProfileIcon(summoner.getSummonerInfo().getProfileIconId());
            }
        }.execute();
    }
}
