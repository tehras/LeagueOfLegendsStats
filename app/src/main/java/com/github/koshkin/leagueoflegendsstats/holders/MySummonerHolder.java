package com.github.koshkin.leagueoflegendsstats.holders;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.fragments.stats.StatsTabbedFragment;
import com.github.koshkin.leagueoflegendsstats.models.Champion;
import com.github.koshkin.leagueoflegendsstats.models.PlayerRanked;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Stats;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;
import com.github.koshkin.leagueoflegendsstats.utils.NumberUtils;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tehras on 1/24/16.
 * <p/>
 * For my summoner view
 */
public class MySummonerHolder {

    private final TextView mSummonerName, mWins, mLosses, mRankedKda, mRankedKills, mRankedDeaths, mRankedAssists;
    private final View mWinsLossesLayout, mRankedStatsLayout, mRankedErrorLayout;
    private final ImageView mSummonerIcon;
    private final ImageView mChampionIcon1, mChampionIcon2, mChampionIcon3;
    private final TextView mChampionKDA1, mChampionKDA2, mChampionKDA3;
    private final View mClickableView;

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

        mChampionIcon1 = (ImageView) view.findViewById(R.id.champion_1_icon);
        mChampionIcon2 = (ImageView) view.findViewById(R.id.champion_2_icon);
        mChampionIcon3 = (ImageView) view.findViewById(R.id.champion_3_icon);

        mChampionKDA1 = (TextView) view.findViewById(R.id.champion_1_kda);
        mChampionKDA2 = (TextView) view.findViewById(R.id.champion_2_kda);
        mChampionKDA3 = (TextView) view.findViewById(R.id.champion_3_kda);

        mClickableView = view;
    }

    public void populate(final Summoner summoner, final Activity activity) {
        mSummonerName.setText(summoner.getSummonerInfo().getName());

        populateIcon(summoner, activity);

        if (summoner.getPlayerRanked() != null && summoner.getPlayerRanked().getChampions() != null && summoner.getPlayerRanked().getChampions().size() > 0) {
            mRankedErrorLayout.setVisibility(View.GONE);
            mWinsLossesLayout.setVisibility(View.VISIBLE);
            mRankedStatsLayout.setVisibility(View.VISIBLE);

            Collections.sort(summoner.getPlayerRanked().getChampions());
            mRankedKills.setText(getKills(summoner.getPlayerRanked()));
            mRankedDeaths.setText(getDeaths(summoner.getPlayerRanked()));
            mRankedAssists.setText(getAssists(summoner.getPlayerRanked()));
            mRankedKda.setText(getKdaText(mRankedKda, summoner.getPlayerRanked(), activity));

            mWins.setText(getWinsText(summoner));
            mLosses.setText(getLossesText(summoner));

            populateChampions(summoner, activity);

        } else {
            mRankedErrorLayout.setVisibility(View.VISIBLE);
            mRankedStatsLayout.setVisibility(View.GONE);
            mWinsLossesLayout.setVisibility(View.GONE);
        }

        if (!NullChecker.isNullOrEmpty(summoner.getSummonerInfo().getName()))
            mClickableView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) activity).startFragment(StatsTabbedFragment.getInstance(summoner.getSummonerInfo().getName(), summoner.getSummonerInfo().getId()).setSummoner(summoner));
                }
            });

    }

    private String getKills(PlayerRanked playerRanked) {
        return NumberUtils.twoDecimals(((double) playerRanked.getChampions().get(0).getStats().getTotalChampionKills()) / ((double) playerRanked.getChampions().get(0).getStats().getTotalSessionsPlayed()));
    }

    private String getDeaths(PlayerRanked playerRanked) {
        return NumberUtils.twoDecimals(((double) playerRanked.getChampions().get(0).getStats().getTotalDeathsPerSession()) / ((double) playerRanked.getChampions().get(0).getStats().getTotalSessionsPlayed()));
    }

    private String getAssists(PlayerRanked playerRanked) {
        return NumberUtils.twoDecimals(((double) playerRanked.getChampions().get(0).getStats().getTotalAssists()) / ((double) playerRanked.getChampions().get(0).getStats().getTotalSessionsPlayed()));
    }

    private void populateChampions(Summoner summoner, final Activity activity) {
        final ArrayList<Champion> champions = summoner.getPlayerRanked().getChampions();
        Collections.sort(champions);

        ((View) mChampionIcon1.getParent()).setVisibility(View.GONE);
        ((View) mChampionIcon2.getParent()).setVisibility(View.GONE);
        ((View) mChampionIcon3.getParent()).setVisibility(View.GONE);
        if (champions.size() > 1) {
            populateChampion(mChampionIcon1, mChampionKDA1, champions.get(1), activity);
        }
        if (champions.size() > 2) {
            populateChampion(mChampionIcon2, mChampionKDA2, champions.get(2), activity);
        }
        if (champions.size() > 3) {
            populateChampion(mChampionIcon3, mChampionKDA3, champions.get(3), activity);
        }

    }

    private void populateChampion(final ImageView championIcon1, TextView championKda, final Champion champion, final Activity activity) {
        ((View) championIcon1.getParent()).setVisibility(View.VISIBLE);

        Stats stats = champion.getStats();
        championKda.setText(getKdaFromChamps(stats, championKda, activity));

        new LoaderHelper() {
            Drawable mDrawable;

            @Override
            protected void postExecute() {
                if (mDrawable != null)
                    championIcon1.setImageDrawable(mDrawable);
            }

            @Override
            protected void runInBackground() {
                mDrawable = StaticDataHolder.getInstance(activity).getChampionIcon(champion.getId());
            }
        }.execute();
    }

    private String getKdaFromChamps(Stats stats, TextView textView, Activity activity) {
        double kills = stats.getTotalChampionKills();
        double deaths = stats.getTotalDeathsPerSession();
        double assists = stats.getTotalAssists();

        double kda = ((kills + assists) / deaths);

        textView.setTextColor(Utils.getKDAColor(kda, activity));

        return NumberUtils.twoDecimalsSafely(kda) + "KDA";
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

        return String.valueOf(champs.get(0).getStats().getTotalSessionsLost()) + "L";
    }

    private String getKdaText(TextView rankedKda, PlayerRanked playerRanked, Activity activity) {
        double kills = playerRanked.getChampions().get(0).getStats().getTotalChampionKills();
        double deaths = playerRanked.getChampions().get(0).getStats().getTotalDeathsPerSession();
        double assists = playerRanked.getChampions().get(0).getStats().getTotalAssists();

        if (deaths == 0d) {
            rankedKda.setTextColor(Utils.getKDAColor(10d, activity));
            return "Perfect KDA";
        } else {
            double kda = ((kills + assists) / deaths);
            rankedKda.setTextColor(Utils.getKDAColor(kda, activity));
            return NumberUtils.twoDecimalsSafely(kda) + ":1 KDA";
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
