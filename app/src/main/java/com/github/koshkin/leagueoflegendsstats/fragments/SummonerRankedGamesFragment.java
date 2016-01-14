package com.github.koshkin.leagueoflegendsstats.fragments;

import android.support.v7.widget.RecyclerView;

import com.github.koshkin.leagueoflegendsstats.adapters.GameHistoryAdapter;
import com.github.koshkin.leagueoflegendsstats.models.Game;

import java.util.ArrayList;

/**
 * Created by tehras on 1/11/16.
 * <p/>
 * List of ALL STATS
 */
public class SummonerRankedGamesFragment extends BaseSimpleRecyclerViewFragment {

    private ArrayList<Game> mGames;

    public static SummonerRankedGamesFragment getInstance(ArrayList<Game> games) {
        SummonerRankedGamesFragment fragment = new SummonerRankedGamesFragment();
        fragment.setGames(games);

        return fragment;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new GameHistoryAdapter(mGames, getActivity());
    }

    public void setGames(ArrayList<Game> games) {
        mGames = games;
    }
}
