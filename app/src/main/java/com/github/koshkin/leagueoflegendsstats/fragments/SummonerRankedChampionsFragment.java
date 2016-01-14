package com.github.koshkin.leagueoflegendsstats.fragments;

import android.support.v7.widget.RecyclerView;

import com.github.koshkin.leagueoflegendsstats.adapters.ChampionAdapter;
import com.github.koshkin.leagueoflegendsstats.models.Champion;

import java.util.ArrayList;

/**
 * Created by tehras on 1/11/16.
 * <p/>
 * List of ALL STATS
 */
public class SummonerRankedChampionsFragment extends BaseSimpleRecyclerViewFragment {

    private ArrayList<Champion> mChampions;

    public void setChampions(ArrayList<Champion> champions) {
        mChampions = champions;
    }

    public static SummonerRankedChampionsFragment getInstance(ArrayList<Champion> champions) {
        SummonerRankedChampionsFragment fragment = new SummonerRankedChampionsFragment();
        fragment.setChampions(champions);

        return fragment;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new ChampionAdapter(mChampions, getActivity());
    }
}
