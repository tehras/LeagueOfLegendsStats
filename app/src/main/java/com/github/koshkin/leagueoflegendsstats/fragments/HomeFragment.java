package com.github.koshkin.leagueoflegendsstats.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.holders.LeagueChampionHolder;
import com.github.koshkin.leagueoflegendsstats.models.LeagueQueueType;
import com.github.koshkin.leagueoflegendsstats.models.LeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.RankedSummoner;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.views.CustomCardView;

import java.util.Collections;


/**
 * Created by tehras on 1/9/16.
 * <p/>
 * Home layout to pop up
 */
public class HomeFragment extends BaseFragment implements Request.RequestCallback {

    private CustomCardView mChallengerLayout;
    private LeagueStandings mLeagueStandings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //EXECUTE
        executeGetChallengerStandings(this, LeagueQueueType.RANKED_SOLO_5x5);
        showLoading();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);

        initChallengerLayout(view);

        if (mChallengerLayout != null)
            populateChallengerLayout();
        else
            challengerLayoutError();

        return view;
    }

    private void initChallengerLayout(View view) {
        mChallengerLayout = (CustomCardView) view.findViewById(R.id.challenger_layout);
        mChallengerLayout.showError().setViewAllOnClickListener(viewAllChallengersListener());
    }

    private View.OnClickListener viewAllChallengersListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeFragment.this.getActivity(), "Not Yet Implemented", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void finished(Response response, Request request) {
        switch (request.getURIHelper()) {
            case GET_CHALLENGER:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mLeagueStandings = (LeagueStandings) response.getReturnedObject();
                    populateChallengerLayout();
                } else {
                    challengerLayoutError();
                }
                hideLoading();
                break;
        }
    }

    private void populateChallengerLayout() {
        if (mLeagueStandings != null && mLeagueStandings.getEntries() != null && mLeagueStandings.getEntries().size() > 0) {
            int i = 0;

            Collections.sort(mLeagueStandings.getEntries());

            for (RankedSummoner summoner : mLeagueStandings.getEntries()) {
                if (i > 2)
                    break;
                mChallengerLayout.addViewToHolder(getChallengerView(summoner, i + 1));
                i++;
            }
        } else {
            mChallengerLayout.showError();
        }
    }

    private View getChallengerView(RankedSummoner summoner, int rank) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.partial_summoner, null);

        new LeagueChampionHolder(view).populate(summoner, (MainActivity) getActivity(), rank, false);

        return view;
    }

    private void challengerLayoutError() {
        mChallengerLayout.showError();
    }
}
