package com.github.koshkin.leagueoflegendsstats.holders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.fragments.league.LeagueRankingFragment;
import com.github.koshkin.leagueoflegendsstats.models.LeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.RankedSummoner;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;

import java.util.Collections;

/**
 * Created by tehras on 2/3/16.
 * <p/>
 * Ranked Division Holder
 */
public class RankedDivisionHolder {

    private final View mLayout;
    private TextView mDivision, mTier, mRankedDivisionWins, mRankedDivisionLosses, mRankedPoints, mRankedRankFinal;
    private ImageView mDivisionIcon;

    public RankedDivisionHolder(View rootView) {
        mDivision = (TextView) rootView.findViewById(R.id.ranked_division_text);
        mTier = (TextView) rootView.findViewById(R.id.ranked_division_tier);
        mRankedDivisionWins = (TextView) rootView.findViewById(R.id.ranked_division_wins);
        mRankedDivisionLosses = (TextView) rootView.findViewById(R.id.ranked_division_losses);
        mRankedRankFinal = (TextView) rootView.findViewById(R.id.ranked_rank_final);
        mRankedPoints = (TextView) rootView.findViewById(R.id.ranked_points);

        mDivisionIcon = (ImageView) rootView.findViewById(R.id.ranked_division_logo);

        mLayout = rootView;
    }

    @SuppressLint("SetTextI18n")
    public void populate(final Activity activity, final LeagueStandings leagueStandings, final String summonerId) {
        mDivision.setText(leagueStandings.getName());
        final RankedSummoner summoner = getRankedSummoner(leagueStandings, summonerId);

        mTier.setText(getTierName(summoner, leagueStandings));

        if (summoner != null) {
            mRankedDivisionWins.setText(String.valueOf(summoner.getWins()));
            mRankedDivisionLosses.setText(String.valueOf(summoner.getLosses()));
            mRankedPoints.setText(String.valueOf(summoner.getLeaguePoints()));
            mRankedRankFinal.setText(String.valueOf(summoner.getRank()) + "/" + String.valueOf(leagueStandings.getEntries().size()));
        } else {
            mRankedDivisionWins.setText(Utils.NOT_AVAILABLE);
            mRankedDivisionLosses.setText(Utils.NOT_AVAILABLE);
            mRankedPoints.setText(Utils.NOT_AVAILABLE);
            mRankedRankFinal.setText(Utils.NOT_AVAILABLE);
        }

        populateImage(activity, summoner, leagueStandings, mDivisionIcon);

        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity != null && activity instanceof MainActivity) {
                    ((MainActivity) activity).startFragment(LeagueRankingFragment.getInstance(leagueStandings, summonerId));
                }
            }
        });
    }

    private void populateImage(final Activity activity, final RankedSummoner summoner, final LeagueStandings leagueStandings, final ImageView divisionIcon) {
        new LoaderHelper() {

            Drawable mDrawable;

            @Override
            protected void postExecute() {
                if (mDrawable != null)
                    divisionIcon.setImageDrawable(mDrawable);
            }

            @Override
            protected void runInBackground() {
                mDrawable = StaticDataHolder.getInstance(activity).getRankTier(leagueStandings.getTier(), (summoner != null ? summoner.getDivision() : "I"));
            }
        }.execute();
    }

    private RankedSummoner getRankedSummoner(LeagueStandings leagueStandings, String summonerId) {
        RankedSummoner returnSummoner = null;

        if (leagueStandings != null && leagueStandings.getEntries() != null) {
            Collections.sort(leagueStandings.getEntries());
            for (int i = 0; i < leagueStandings.getEntries().size(); i++) {
                RankedSummoner summoner = leagueStandings.getEntries().get(i);
                if (summoner.getPlayerOrTeamId().equalsIgnoreCase(summonerId))
                    returnSummoner = summoner;

                summoner.setRank(i + 1);
            }
        }

        return returnSummoner;
    }

    private String getTierName(RankedSummoner rankedSummoner, LeagueStandings leagueStandings) {
        String tier = leagueStandings.getTier().getName();

        String division = null;

        if (rankedSummoner != null)
            division = rankedSummoner.getDivision();

        return tier + (!NullChecker.isNullOrEmpty(division) ? " " + division : "");

    }
}
