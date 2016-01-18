package com.github.koshkin.leagueoflegendsstats.holders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.fragments.SummonerStatsFragment;
import com.github.koshkin.leagueoflegendsstats.models.LeagueQueueType;
import com.github.koshkin.leagueoflegendsstats.models.LeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.RankedSummoner;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.utils.NumberUtils;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;

/**
 * Created by tehras on 1/15/16.
 * <p/>
 * League Champion Holder
 */
public class LeagueChampionHolder extends RecyclerView.ViewHolder {
    public final TextView mLosses, mWins, mWinP, mName, mPoints;
    public final TextView mRank;
    public final View mDivider, mFreshBlood, mNewPlayer, mVeteran;
    public final ImageView mIcon;
    public final View mTotalView;

    public LeagueChampionHolder(View itemView) {
        super(itemView);

        mTotalView = itemView;

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

        mIcon = (ImageView) itemView.findViewById(R.id.summoner_icon);
    }

    @SuppressLint("SetTextI18n")
    public void populate(final RankedSummoner summoner, final MainActivity activity, LeagueQueueType queueType, boolean t) {
        mWins.setText(String.valueOf(summoner.getWins()));
        mLosses.setText(String.valueOf(summoner.getLosses()));

        double winPercentage = ((double) summoner.getWins() * 100) / (((double) summoner.getLosses() + (double) summoner.getWins()));
        Utils.assignWinPercentageColor(mWinP, winPercentage, activity);
        mWinP.setText(NumberUtils.oneDecimalSafely(winPercentage) + "%");

        mPoints.setText(String.valueOf(summoner.getLeaguePoints()) + " Points");
        mName.setText(summoner.getPlayerOrTeamName());
        mName.setSelected(true);

        mRank.setText(Html.fromHtml("Rank <b>" + String.valueOf(summoner.getRank()) + "</b>"));

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

        if (summoner.getSummoner() != null) {
            populateImage(summoner.getSummoner(), activity);
        } else if (queueType == LeagueQueueType.RANKED_SOLO_5x5) {
            mIcon.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_account_circle_black_48dp, null));
        } else {
            mIcon.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_social_group, null));
        }

        mTotalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSummonerLayout(summoner, activity);
            }
        });
    }

    private void startSummonerLayout(RankedSummoner summoner, MainActivity activity) {
        SummonerStatsFragment summonerStatsFragment = SummonerStatsFragment.getInstance(summoner.getPlayerOrTeamName(), summoner.getPlayerOrTeamId());
        summonerStatsFragment.setSummoner(summoner.getSummoner());
        activity.startFragment(summonerStatsFragment);
    }

    public void updateImage(LeagueStandings leagueStandings, Context context) {
        String name = mName.getText().toString();

        Summoner summoner = getSummonerFromStandings(leagueStandings, name);
        populateImage(summoner, context);
    }

    private void populateImage(final Summoner summoner, final Context context) {
        if (summoner != null && summoner.getSummonerInfo() != null) {
            new LoaderHelper() {
                Drawable mImage;

                @Override
                protected void postExecute() {
                    if (mImage != null) {
                        mIcon.setImageDrawable(mImage);
                    } else {
                        mIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_account_circle_black_48dp, null));
                    }
                }

                @Override
                protected void runInBackground() {
                    mImage = StaticDataHolder.getInstance(context).getProfileIconSmall(summoner.getSummonerInfo().getProfileIconId());
                }
            }.execute();
        }
    }

    private Summoner getSummonerFromStandings(LeagueStandings leagueStandings, String name) {
        if (leagueStandings.getEntries() == null || leagueStandings.getEntries().size() == 0) {
            return null;
        }

        for (RankedSummoner rankedSummoner : leagueStandings.getEntries()) {
            if (rankedSummoner.getPlayerOrTeamName().equalsIgnoreCase(name)) {
                return rankedSummoner.getSummoner();
            }
        }

        return null;
    }
}
