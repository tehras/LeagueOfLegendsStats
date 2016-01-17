package com.github.koshkin.leagueoflegendsstats.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
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

        if (mLeagueStandings != null) {
            populateChallengerLayout();
        } else
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
                if (getActivity() != null && getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).startFragment(LeagueRankingFragment.getInstance(mLeagueStandings));
            }
        };
    }

    @Override
    public void finished(Response response, Request request) {
        switch (request.getURIHelper()) {
            case GET_CHALLENGER:
                boolean hideLoading = false;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mLeagueStandings = (LeagueStandings) response.getReturnedObject();
                    ArrayList<String> summonerIds = populateChallengerLayout();
                    if (summonerIds != null)
                        executeGetSummonersById(this, summonerIds, mLeagueStandings);
                    else
                        hideLoading = true;
                } else {
                    hideLoading = true;
                    challengerLayoutError();
                }
                if (hideLoading)
                    hideLoading();
                break;
            case GET_SUMMONER_BY_IDS:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mLeagueStandings = (LeagueStandings) response.getReturnedObject();
                    updateChallengerLayout();
                }
                hideLoading();
                break;
        }
    }

    private void updateChallengerLayout() {
        int size = mChallengerLayout.getViewHolderCountSize();
        for (int i = 0; i < size; i++) {
            View view = mChallengerLayout.getViewHolderChildAt(i);
            new LeagueChampionHolder(view).updateImage(mLeagueStandings, getActivity());
        }

    }

    private ArrayList<String> populateChallengerLayout() {
        ArrayList<String> summonersToReturn = null;

        if (mLeagueStandings != null && mLeagueStandings.getEntries() != null && mLeagueStandings.getEntries().size() > 0) {
            int i = 0;

            Collections.sort(mLeagueStandings.getEntries());

            for (RankedSummoner summoner : mLeagueStandings.getEntries()) {
                if (i > 2)
                    break;
                mChallengerLayout.addViewToHolder(getChallengerView(summoner));
                if (summonersToReturn == null)
                    summonersToReturn = new ArrayList<>();

                summonersToReturn.add(summoner.getPlayerOrTeamId());
                i++;
            }
        } else {
            mChallengerLayout.showError();
        }

        return summonersToReturn;
    }

    private View getChallengerView(RankedSummoner summoner) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.partial_summoner, null);

        new LeagueChampionHolder(view).populate(summoner, (MainActivity) getActivity(), LeagueQueueType.RANKED_SOLO_5x5, false);

        return view;
    }

    private void challengerLayoutError() {
        mChallengerLayout.showError();
    }
}
