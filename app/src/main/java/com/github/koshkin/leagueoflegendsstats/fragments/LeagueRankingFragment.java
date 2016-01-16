package com.github.koshkin.leagueoflegendsstats.fragments;

import android.support.v7.widget.RecyclerView;

import com.github.koshkin.leagueoflegendsstats.adapters.LeagueRankingAdapter;
import com.github.koshkin.leagueoflegendsstats.models.LeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.RankedSummoner;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;

import java.util.ArrayList;

/**
 * Created by tehras on 1/16/16.
 */
public class LeagueRankingFragment extends BaseSimpleRecyclerViewFragment implements Request.RequestCallback {

    private LeagueStandings mLeagueStandings;
    private LeagueRankingAdapter mAdapter;

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mAdapter == null)
            mAdapter = new LeagueRankingAdapter(mLeagueStandings, getActivity()) {
                @Override
                public void getMoreItems(ArrayList<RankedSummoner> arrayToExecute, boolean isFirst) {
                    if (isFirst)
                        showLoading();

                    executeGetSummonersById(LeagueRankingFragment.this, getStringArrayFromRankedSummoner(arrayToExecute), mLeagueStandings);
                }
            };

        return mAdapter;
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
        }
    }

    private void updateAdapter(LeagueStandings returnedObject) {
        if (mAdapter != null) {
            mAdapter.update(returnedObject.getEntries());
        }
    }
}
