package com.github.koshkin.leagueoflegendsstats.fragments.league;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.adapters.LeagueRankingAdapter;
import com.github.koshkin.leagueoflegendsstats.fragments.BaseSimpleRecyclerViewFragment;
import com.github.koshkin.leagueoflegendsstats.models.LeagueQueueType;
import com.github.koshkin.leagueoflegendsstats.models.LeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.RankedSummoner;
import com.github.koshkin.leagueoflegendsstats.models.Tier;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.views.CustomSpinner;
import com.github.koshkin.leagueoflegendsstats.views.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.github.koshkin.leagueoflegendsstats.models.LeagueQueueType.RANKED_SOLO_5x5;
import static com.github.koshkin.leagueoflegendsstats.models.LeagueQueueType.RANKED_TEAM_3x3;
import static com.github.koshkin.leagueoflegendsstats.models.LeagueQueueType.RANKED_TEAM_5x5;

/**
 * Created by tehras on 1/16/16.
 * <p/>
 * Ranked play
 */
public class LeagueRankingFragment extends BaseSimpleRecyclerViewFragment implements Request.RequestCallback, SwipeRefreshLayout.OnRefreshListener {

    private LeagueStandings mLeagueStandings;
    private LeagueRankingAdapter mAdapter;
    private LeagueQueueType mSelectedQueue;
    private Tier mSelectedTier;

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mAdapter == null)
            mAdapter = new LeagueRankingAdapter(mLeagueStandings, getActivity(), mSelectedQueue) {
                @Override
                public void getMoreItems(ArrayList<RankedSummoner> arrayToExecute, boolean isFirst) {
                    if (isFirst)
                        showLoading();

                    executeGetSummonersById(LeagueRankingFragment.this, getStringArrayFromRankedSummoner(arrayToExecute), mLeagueStandings);
                }
            };

        return mAdapter;
    }

    @Override
    public void onResume() {
        super.onResume();
        addOnSwipeToRefreshListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mSelectedQueue = LeagueQueueType.RANKED_SOLO_5x5;
        mSelectedTier = Tier.CHALLENGER;
        mSortBy = RankedSummoner.SortBy.POINTS;
    }

    @Override
    protected String getToolbarTitle() {
        return getActivity().getResources().getString(R.string.fragment_title_highest_ranked);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.league_standings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private RankedSummoner.SortBy mSortBy;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        RankedSummoner.SortBy thisSortBy = mSortBy;
        switch (item.getItemId()) {
            case R.id.action_filter:
                toggleLayout();
                break;
            case R.id.action_sort:
                break;
            case R.id.action_sort_points:
                thisSortBy = RankedSummoner.SortBy.POINTS;
                break;
            case R.id.action_sort_win_p:
                thisSortBy = RankedSummoner.SortBy.WINP;
                break;
            case R.id.action_sort_wins:
                thisSortBy = RankedSummoner.SortBy.WINS;
                break;
            case R.id.action_sort_losses:
                thisSortBy = RankedSummoner.SortBy.LOSSES;
                break;
        }

        if (thisSortBy != mSortBy) {
            mSortBy = thisSortBy;
            updateAdapterAllNew(mLeagueStandings);
        }

        return super.onOptionsItemSelected(item);
    }

    private Comparator<? super RankedSummoner> getCorrectSortBy(RankedSummoner.SortBy sortBy) {
        switch (sortBy) {
            case WINS:
                return new RankedSummoner().new WinComparator();
            case WINP:
                return new RankedSummoner().new WinPercentageComparator();
            case LOSSES:
                return new RankedSummoner().new LossComparator();
            default:
                return new RankedSummoner().new PointsComparator();
        }
    }

    @Override
    protected View populateExtraLayout(LayoutInflater inflater) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.partial_ranked_upper_layout, null, false);

        final String originalQueue = mSelectedQueue.getName();
        final String originalTier = mSelectedTier.getName();

        final MaterialButton button = (MaterialButton) view.findViewById(R.id.button);

        final CustomSpinner leagueChooser = (CustomSpinner) view.findViewById(R.id.league_spinner);
        leagueChooser.setChoices(new String[]{Tier.CHALLENGER.getName(), Tier.MASTER.getName()}).setSelectedOption(mSelectedTier.getName()).setSpinnerListener(new CustomSpinner.CustomSpinnerListener() {
            @Override
            public void spinnerClicked(String text) {
                mSelectedTier = Tier.fromName(text);
                showOrHideSubmitButton(originalQueue, originalTier, mSelectedQueue, mSelectedTier, button);
            }
        });

        final CustomSpinner modeChooser = (CustomSpinner) view.findViewById(R.id.mode_spinner);
        modeChooser.setChoices(new String[]{RANKED_SOLO_5x5.getName(), RANKED_TEAM_5x5.getName(), RANKED_TEAM_3x3.getName()}).setSelectedOption(mSelectedQueue.getName()).setSpinnerListener(new CustomSpinner.CustomSpinnerListener() {
            @Override
            public void spinnerClicked(String text) {
                mSelectedQueue = LeagueQueueType.fromName(text);
                showOrHideSubmitButton(originalQueue, originalTier, mSelectedQueue, mSelectedTier, button);
            }
        });

        button.setOnButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAllowed = showOrHideSubmitButton(originalQueue, originalTier, mSelectedQueue, mSelectedTier, button);
                if (isAllowed) {
                    showLoading();
                    executeCall();
                }
            }
        });
        showOrHideSubmitButton(originalQueue, originalTier, mSelectedQueue, mSelectedTier, button);

        return view;
    }

    private void executeCall() {
        if (mSelectedTier == Tier.MASTER) {
            executeGetMasterStandings(LeagueRankingFragment.this, mSelectedQueue);
        } else {
            executeGetChallengerStandings(LeagueRankingFragment.this, mSelectedQueue);
        }
    }

    private boolean showOrHideSubmitButton(String originalQueue, String originalTier, LeagueQueueType selectedQueue, Tier selectedTier, MaterialButton button) {
        if (!originalQueue.equalsIgnoreCase(selectedQueue.getName()) || !originalTier.equalsIgnoreCase(selectedTier.getName())) {
            button.setTextColor(R.color.colorAccent);
            return true;
        } else {
            button.setTextColor(R.color.text_color_grey);
            return false;
        }
    }

    private ArrayList<String> getStringArrayFromRankedSummoner(ArrayList<RankedSummoner> arrayToExecute) {
        ArrayList<String> strings = new ArrayList<>();
        for (RankedSummoner summoner : arrayToExecute) {
            strings.add(summoner.getPlayerOrTeamId());
        }

        return strings;
    }

    public static LeagueRankingFragment getInstance(LeagueStandings leagueStandings) {
        LeagueRankingFragment fragment = new LeagueRankingFragment();
        fragment.setLeagueStandings(leagueStandings);

        return fragment;
    }

    private void setLeagueStandings(LeagueStandings leagueStandings) {
        mLeagueStandings = leagueStandings;
    }

    @Override
    public void finished(Response response, Request request) {
        switch (request.getURIHelper()) {
            case GET_SUMMONER_BY_IDS:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    updateAdapter((LeagueStandings) response.getReturnedObject());
                }
                hideLoading();
                break;
            case GET_CHALLENGER:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mLeagueStandings = (LeagueStandings) response.getReturnedObject();
                    updateAdapterAllNew(mLeagueStandings);
                } else {
                    initializeErrorLayout("Error", "Failed retrieving the data");
                }
                hideLoading();
                break;
            case GET_MASTER:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mLeagueStandings = (LeagueStandings) response.getReturnedObject();
                    updateAdapterAllNew(mLeagueStandings);
                } else {
                    updateAdapterError();
                    initializeErrorLayout("Error", "Failed retrieving the data");
                }
                break;
        }
        mOnRefreshListener--;
        if (mOnRefreshListener <= 0)
            stopRefreshing();

    }

    private void updateAdapter(LeagueStandings returnedObject) {
        if (mAdapter != null) {
            mAdapter.update(returnedObject.getEntries());
        }
    }

    private void updateAdapterAllNew(LeagueStandings leagueStandings) {
        if (mAdapter != null) {
            Collections.sort(mLeagueStandings.getEntries(), getCorrectSortBy(mSortBy));
            mAdapter.updateAllNew(leagueStandings);
        }
    }

    private void updateAdapterError() {
        if (mAdapter != null) {
            mAdapter.updateError();
        }
    }

    private int mOnRefreshListener;

    @Override
    public void onRefresh() {
        mOnRefreshListener = 1;
        executeCall();
    }
}
