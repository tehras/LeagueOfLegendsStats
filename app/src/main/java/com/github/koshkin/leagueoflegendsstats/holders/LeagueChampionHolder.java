package com.github.koshkin.leagueoflegendsstats.holders;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.RankedSummoner;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;

/**
 * Created by tehras on 1/15/16.
 * <p/>
 * League Champion Holder
 */
public class LeagueChampionHolder extends RecyclerView.ViewHolder {
    public final TextView mLosses, mWins, mWinP, mName, mPoints;
    public final TextView mRank;
    public final View mDivider, mFreshBlood, mNewPlayer, mVeteran;

    public LeagueChampionHolder(View itemView) {
        super(itemView);

        mWins = (TextView) itemView.findViewById(R.id.ranked_wins);
        mLosses = (TextView) itemView.findViewById(R.id.ranked_losses);
        mWinP = (TextView) itemView.findViewById(R.id.ranked_win_percentage);
        mName = (TextView) itemView.findViewById(R.id.summoner_name);
        mPoints = (TextView) itemView.findViewById(R.id.ranked_points);

        mRank = (TextView) itemView.findViewById(R.id.ranked_rank);

        mDivider = itemView.findViewById(R.id.divider);

        mFreshBlood = itemView.findViewById(R.id.ranked_hot_streak);
        mNewPlayer = itemView.findViewById(R.id.ranked_new_player);
        mVeteran = itemView.findViewById(R.id.ranked_veteran);
    }

    @SuppressLint("SetTextI18n")
    public void populate(RankedSummoner summoner, MainActivity activity, int rank, boolean t) {
        mWins.setText(String.valueOf(summoner.getWins()));
        mLosses.setText(String.valueOf(summoner.getLosses()));

        double winPercentage = ((double) summoner.getWins() * 100) / ((double) summoner.getLosses());
        Utils.assignWinPercentageColor(mWinP, winPercentage, activity);

        mPoints.setText(String.valueOf(summoner.getLeaguePoints()) + " Points");
        mName.setText(summoner.getPlayerOrTeamName());
        mName.setSelected(true);

        mRank.setText(Html.fromHtml("Rank <b>" + String.valueOf(rank) + "</b>"));

        if (summoner.isFreshBlood())
            mNewPlayer.setVisibility(View.VISIBLE);
        else
            mNewPlayer.setVisibility(View.GONE);

        if (summoner.isVeteran())
            mVeteran.setVisibility(View.VISIBLE);
        else
            mVeteran.setVisibility(View.GONE);

        if (summoner.isHotStreak())
            mFreshBlood.setVisibility(View.VISIBLE);
        else
            mFreshBlood.setVisibility(View.GONE);


        if (t)
            mDivider.setVisibility(View.GONE);
    }
}
