package com.github.koshkin.leagueoflegendsstats.fragments.summoner;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.adapters.GameHistoryAdapter;
import com.github.koshkin.leagueoflegendsstats.fragments.BaseSimpleRecyclerViewFragment;
import com.github.koshkin.leagueoflegendsstats.models.Game;

import java.util.ArrayList;

/**
 * Created by tehras on 1/11/16.
 * <p/>
 * List of ALL STATS
 */
public class SummonerRankedGamesFragment extends BaseSimpleRecyclerViewFragment {

    private ArrayList<Game> mGames;
    private String mSummonerId;

    public static SummonerRankedGamesFragment getInstance(ArrayList<Game> games, String summonerId) {
        SummonerRankedGamesFragment fragment = new SummonerRankedGamesFragment();
        fragment.setGames(games);

        Bundle args = new Bundle();
        args.putString(ARG_SUMMONER_ID, summonerId);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected String getToolbarTitle() {
        return getActivity().getResources().getString(R.string.fragment_title_match_history);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSummonerId = getArguments().getString(ARG_SUMMONER_ID);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new GameHistoryAdapter(mGames, getActivity(), mSummonerId);
    }

    public void setGames(ArrayList<Game> games) {
        mGames = games;
    }
}
