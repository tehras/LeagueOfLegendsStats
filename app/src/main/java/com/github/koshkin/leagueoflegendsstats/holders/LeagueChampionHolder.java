package com.github.koshkin.leagueoflegendsstats.holders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
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
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummoner;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.utils.NumberUtils;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by tehras on 1/15/16.
 * <p/>
 * League Champion Holder
 */
public class LeagueChampionHolder extends RecyclerView.ViewHolder {
    public final TextView mLosses, mWins, mWinP, mName, mPoints;
    public final TextView mRank, mSmallDateText;
    public final View mDivider, mFreshBlood, mNewPlayer, mVeteran;
    public final ImageView mIcon;
    public final View mTotalView;
    private final View mPointsLayout, mStreaksLayout;

    public LeagueChampionHolder(View itemView) {
        super(itemView);

        mTotalView = itemView.findViewById(R.id.material_clickable_layout);

        mSmallDateText = (TextView) mTotalView.findViewById(R.id.small_date_layout);
        mWins = (TextView) itemView.findViewById(R.id.ranked_wins);
        mLosses = (TextView) itemView.findViewById(R.id.ranked_losses);
        mWinP = (TextView) itemView.findViewById(R.id.ranked_win_percentage);
        mName = (TextView) itemView.findViewById(R.id.summoner_name);
        mPoints = (TextView) itemView.findViewById(R.id.ranked_points);

        mRank = (TextView) itemView.findViewById(R.id.ranked_rank);

        mDivider = itemView.findViewById(R.id.divider);

        mStreaksLayout = itemView.findViewById(R.id.streaks);
        mPointsLayout = itemView.findViewById(R.id.points_layout);
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

    @SuppressLint("SetTextI18n")
    public void populate(final SimpleSummoner simpleSummoner, final MainActivity activity, boolean t) {
        mStreaksLayout.setVisibility(View.GONE);
        mPointsLayout.setVisibility(View.GONE);

        if (simpleSummoner.getWins() > 0)
            mWins.setText(String.valueOf(simpleSummoner.getWins()));
        else
            mWinP.setText(Utils.NOT_AVAILABLE);
        if (simpleSummoner.getLosses() > 0)
            mLosses.setText(String.valueOf(simpleSummoner.getLosses()));
        else
            mLosses.setText(Utils.NOT_AVAILABLE);


        double winPercentage = -1d;
        if (simpleSummoner.getWins() > 0 && simpleSummoner.getLosses() > 0)
            winPercentage = ((double) simpleSummoner.getWins() * 100) / (((double) simpleSummoner.getLosses() + (double) simpleSummoner.getWins()));

        Utils.assignWinPercentageColor(mWinP, winPercentage, activity);
        if (winPercentage != -1d)
            mWinP.setText(NumberUtils.oneDecimalSafely(winPercentage) + "%");
        else
            mWinP.setText(Utils.NOT_AVAILABLE);

        mPoints.setVisibility(View.GONE);
        mName.setText(simpleSummoner.getName());
        mName.setSelected(true);

        mRank.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        mSmallDateText.setVisibility(View.VISIBLE);
        mSmallDateText.setText(getDate(simpleSummoner));
        mSmallDateText.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        mRank.setVisibility(View.GONE);

        mNewPlayer.setVisibility(View.GONE);
        mVeteran.setVisibility(View.GONE);
        mFreshBlood.setVisibility(View.GONE);

        if (t)
            mDivider.setVisibility(View.GONE);

        if (simpleSummoner.getIconId() != -1) {
            populateImage(simpleSummoner.getIconId(), activity, mIcon);
        } else
            mIcon.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_account_circle_black_48dp, null));


        mTotalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSummonerLayout(simpleSummoner, activity);
            }
        });
    }

    private String getDate(SimpleSummoner simpleSummoner) {
        long date = simpleSummoner.getDate();
        Calendar c = Calendar.getInstance();
        if (date > 0)
            c.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();
        if (today.get(Calendar.DATE) == c.get(Calendar.DATE)) {
            String hour = String.valueOf(c.get(Calendar.HOUR));
            String minute = String.valueOf(c.get(Calendar.MINUTE));
            if (minute.length() == 1)
                minute = "0" + minute;
            int amPm = (c.get(Calendar.AM_PM));

            return "Last Updated : " + hour + ":" + minute + " " + (amPm == 0 ? "AM" : "PM");
        }
        today.add(Calendar.HOUR, -24); //yesterady
        if (today.before(c)) {
            return "Last Updated Yesterday";
        }

        return "Last Updated On :" + getDateOther(c);
    }

    private String getDateOther(Calendar c) {
        String month = c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String year = String.valueOf(c.get(Calendar.YEAR));

        return month + " " + day + ", " + year;
    }

    private void startSummonerLayout(RankedSummoner summoner, MainActivity activity) {
        SummonerStatsFragment summonerStatsFragment = SummonerStatsFragment.getInstance(summoner.getPlayerOrTeamName(), summoner.getPlayerOrTeamId());
        summonerStatsFragment.setSummoner(summoner.getSummoner());
        activity.startFragment(summonerStatsFragment);
    }

    private void startSummonerLayout(SimpleSummoner simpleSummoner, MainActivity activity) {
        activity.startFragment(SummonerStatsFragment.getInstance(simpleSummoner.getName(), simpleSummoner.getSummonerId()));
    }

    public void updateImage(LeagueStandings leagueStandings, Context context) {
        String name = mName.getText().toString();

        Summoner summoner = getSummonerFromStandings(leagueStandings, name);
        populateImage(summoner, context);
    }

    private void populateImage(final Summoner summoner, final Context context) {
        if (summoner != null && summoner.getSummonerInfo() != null) {
            populateImage(summoner.getSummonerInfo().getProfileIconId(), context, mIcon);
        }
    }

    private void populateImage(final int profileIconId, final Context context, final ImageView icon) {
        new LoaderHelper() {
            Drawable mImage;

            @Override
            protected void postExecute() {
                if (mImage != null) {
                    icon.setImageDrawable(mImage);
                } else {
                    icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_account_circle_black_48dp, null));
                }
            }

            @Override
            protected void runInBackground() {
                mImage = StaticDataHolder.getInstance(context).getProfileIconSmall(profileIconId);
            }
        }.execute();
    }

    private Summoner getSummonerFromStandings(LeagueStandings leagueStandings, String name) {
        if (leagueStandings.getEntries() == null || leagueStandings.getEntries().size() == 0) {
            return null;
        }

        for (RankedSummoner rankedSummoner : leagueStandings.getEntries()) {
            if (rankedSummoner.getPlayerOrTeamName().equalsIgnoreCase(name) || (rankedSummoner.getSummoner() != null && rankedSummoner.getSummoner() != null && rankedSummoner.getSummoner().getSummonerInfo().getName().equalsIgnoreCase(name))) {
                return rankedSummoner.getSummoner();
            }
        }

        return null;
    }
}
