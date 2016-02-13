package com.github.koshkin.leagueoflegendsstats.fragments.summoner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.PlayerStatSummaries;
import com.github.koshkin.leagueoflegendsstats.models.PlayerSummary;
import com.github.koshkin.leagueoflegendsstats.models.RecentSummoner;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.models.SummonerAggregateObject;
import com.github.koshkin.leagueoflegendsstats.models.SummonerInfo;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.sqlite.RecentSearchSqlLiteHelper;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

import static com.github.koshkin.leagueoflegendsstats.utils.Utils.addToLayout;


/**
 * Created by tehras on 1/10/16.
 * <p/>
 * This fragment will show the stats
 */
public class SummonerStatsFragment extends BaseFragment implements Request.RequestCallback, SwipeRefreshLayout.OnRefreshListener {

    private SummonerAggregateObject mSummonerAggregateObject;
    private LinearLayout mSelectContainer;

    private String mSummonerName;
    private String mSummonerId;

    private int mToExecute;
    private int mSwipeToRefresh;
    private Summoner mSummoner;

    private boolean mAlreadyAnimated;

    public SummonerStatsFragment setSummoner(Summoner summoner) {
        mSummoner = summoner;
        return this;
    }

    public static SummonerStatsFragment getInstance(String summonerName, String summonerId, Summoner summoner, SummonerAggregateObject summonerAggregateObject) {
        Bundle args = new Bundle();
        args.putString(ARG_SUMMONER_NAME, summonerName);
        args.putString(ARG_SUMMONER_ID, summonerId);

        SummonerStatsFragment fragment = new SummonerStatsFragment();
        fragment.setSummoner(summoner);
        fragment.setSummonerAggregateObject(summonerAggregateObject);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected boolean showFab() {
        return true;
    }

    protected String getSummonerName() {
        return mSummonerName;
    }

    protected String getSummonerId() {
        return mSummonerId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_summoner_layout, container, false);

        initializeSelectLayout(rootView);

        if (mSummonerAggregateObject != null) {
            populateSelectLayout();
            hideLoading();
        } else {
            showLoading();
        }
        return rootView;
    }

    private void initializeSelectLayout(View rootView) {
        mSelectContainer = (LinearLayout) rootView.findViewById(R.id.match_type_select_container);
    }

    private void populateSelectLayout() {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        if (mSummonerAggregateObject != null && mSummonerAggregateObject.getPlayerStatSummaries().getPlayerSummaries() != null) {
            ArrayList<PlayerSummary> playerSummaries = mSummonerAggregateObject.getPlayerStatSummaries().getPlayerSummaries();
            //Sorts the collection
            Collections.sort(playerSummaries);

            mSelectContainer.removeAllViews();//removes before adding
            for (PlayerSummary playerSummary : playerSummaries) {
                addToLayout(mSelectContainer, getSelectableView(inflater, playerSummary));
            }

            animateContainer(mSelectContainer);

        } else {
            noGameRecordsFound();
        }
    }

    private void animateContainer(LinearLayout selectContainer) {
        if (mAlreadyAnimated)
            return;

        mAlreadyAnimated = true;
        if (selectContainer.getChildCount() > 0) {
            for (int i = 0; i < selectContainer.getChildCount(); i++) {
                View child = selectContainer.getChildAt(i);

                Animation slideInAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
                slideInAnim.setDuration((long) (200 + (50 * i)));

                child.startAnimation(slideInAnim);
            }
        }
    }

    public boolean mFromSummonerId = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSummonerName = getArguments().getString(ARG_SUMMONER_NAME, null);
        mSummonerId = getArguments().getString(ARG_SUMMONER_ID, null);

        if (mSummonerAggregateObject == null) {
            if (mSummonerId == null)
                executeGetSummoner(this, mSummonerName);
            else {
                if (mSummoner != null && mSummoner.getSummonerInfo() != null) {
                    getProfileImage(mSummoner.getSummonerInfo());
                }
                mFromSummonerId = true;
                executeGetStats(this, mSummonerId);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void finished(@NonNull Response response, @NonNull Request request) {
        if (mSummonerAggregateObject == null)
            mSummonerAggregateObject = new SummonerAggregateObject();
        mSummonerAggregateObject.setStatus(response.getStatus());

        switch (request.getURIHelper()) {
            case GET_SUMMONER_SUMMARY:
                mSwipeToRefresh--;
                mToExecute--;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    PlayerStatSummaries playerStatSummaries = (PlayerStatSummaries) response.getReturnedObject();
                    if (playerStatSummaries != null) {
                        if (mFromSummonerId) {
                            mSummonerAggregateObject = new SummonerAggregateObject(mSummoner);
                            mSummonerAggregateObject.setStatus(Response.Status.SUCCESS);
                        }
                        mSummonerAggregateObject.setPlayerStatSummaries(playerStatSummaries);
                        populateSelectLayout();
                    } else {
                        generalException();
                    }
                } else if (response.getStatus() == Response.Status.NOT_FOUND) {
                    userNotFound();
                } else {
                    generalException();
                }
                if (mToExecute <= 0)
                    hideLoading();
                break;
        }
        addToRecent(mSummonerAggregateObject);
        if (mSwipeToRefresh <= 0)
            stopRefreshing();
    }

    private void executeRemainingCalls(String summonerId) {
        mToExecute = 1;
        executeGetStats(this, summonerId);
    }

    private void addToRecent(SummonerAggregateObject summoner) {
        if (summoner.getPlayerStatSummaries() != null && summoner.getSummoner() != null) {
            RecentSummoner recentSummoner = new RecentSummoner(summoner);
            RecentSearchSqlLiteHelper.addRecent(recentSummoner);
        }
    }

    private void getProfileImage(SummonerInfo summonerInfo) {
        String profileIconName = StaticDataHolder.getInstance(getActivity()).getProfileIconName(summonerInfo.getProfileIconId());
        executeGetProfileImage(this, profileIconName);
    }

    private void generalException() {
        hideLoading();
        if (getActivity() != null && getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).hideFaveFab();

        initializeErrorLayout(getResources().getString(R.string.general_error_title), getResources().getString(R.string.general_error_body));
    }

    private void noGameRecordsFound() {
        hideLoading();

//        initializeErrorLayout(getResources().getString(R.string.no_record_found_title), getResources().getString(R.string.no_record_found_body));
    }

    private void userNotFound() {
        hideLoading();

//        initializeErrorLayout(getActivity().getResources().getText(R.string.no_user_found_title).toString(), Html.fromHtml(String.format(getActivity().getResources().getText(R.string.no_user_found_message).toString(), mSummonerName)));
    }

    @SuppressLint("SetTextI18n")
    public View getSelectableView(LayoutInflater inflater, final PlayerSummary playerSummary) {
        if (playerSummary.getSummaryType() == null || (playerSummary.getWins() == 0 && playerSummary.getLosses() == 0))
            return null;

        @SuppressLint("InflateParams") View rootView = inflater.inflate(R.layout.partial_select_game_type_layout, null);

        TextView title = (TextView) rootView.findViewById(R.id.select_game_type_title);
        title.setText(playerSummary.getSummaryType().getName());

        TextView wins = (TextView) rootView.findViewById(R.id.select_wins);
        TextView hyphen = (TextView) rootView.findViewById(R.id.select_hyphen);
        TextView losses = (TextView) rootView.findViewById(R.id.select_losses);

        if (playerSummary.getLosses() == -1) {
            hyphen.setVisibility(View.GONE);
            wins.setText(Utils.getTextSafely(playerSummary.getWins()) + " Wins");
            losses.setVisibility(View.GONE);
        } else {
            wins.setText(Utils.getTextSafely(playerSummary.getWins()) + " Wins");
            losses.setText(Utils.getTextSafely(playerSummary.getLosses()) + " Losses");
        }

        ImageView carat = (ImageView) rootView.findViewById(R.id.select_game_right_icon);
        carat.setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public void onRefresh() {
        mSwipeToRefresh = 1;
        executeRemainingCalls(mSummonerAggregateObject.getSummoner().getSummonerId());
    }

    public void setSummonerAggregateObject(SummonerAggregateObject summonerAggregateObject) {
        mSummonerAggregateObject = summonerAggregateObject;
    }
}
