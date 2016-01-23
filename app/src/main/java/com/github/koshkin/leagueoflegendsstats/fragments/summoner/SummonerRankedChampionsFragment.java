package com.github.koshkin.leagueoflegendsstats.fragments.summoner;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.adapters.ChampionAdapter;
import com.github.koshkin.leagueoflegendsstats.fragments.BaseSimpleRecyclerViewFragment;
import com.github.koshkin.leagueoflegendsstats.models.Champion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by tehras on 1/11/16.
 * <p/>
 * List of ALL STATS
 */
public class SummonerRankedChampionsFragment extends BaseSimpleRecyclerViewFragment {

    private ArrayList<Champion> mChampions;
    private Champion.SortType mSortBy;
    private ChampionAdapter mAdapter;

    public void setChampions(ArrayList<Champion> champions) {
        mChampions = champions;
    }

    public static SummonerRankedChampionsFragment getInstance(ArrayList<Champion> champions) {
        SummonerRankedChampionsFragment fragment = new SummonerRankedChampionsFragment();
        fragment.setChampions(champions);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mSortBy = Champion.SortType.GAMES_PLAYED;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new ChampionAdapter(mChampions, getActivity());
        return mAdapter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.champions_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Champion.SortType thisSortBy = mSortBy;
        switch (item.getItemId()) {
            case R.id.action_sort_by_games_played:
                thisSortBy = Champion.SortType.GAMES_PLAYED;
                break;
            case R.id.action_sort_win_p:
                thisSortBy = Champion.SortType.WINP;
                break;
            case R.id.action_sort_wins:
                thisSortBy = Champion.SortType.WINS;
                break;
            case R.id.action_sort_losses:
                thisSortBy = Champion.SortType.LOSSES;
                break;
        }

        if (thisSortBy != mSortBy) {
            mSortBy = thisSortBy;
            Collections.sort(mChampions, getCorrectSortBy(mSortBy));
            updateAdapter(mChampions);
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateAdapter(ArrayList<Champion> champions) {
        if (mAdapter != null)
            mAdapter.updateData(champions);
    }

    private Comparator<? super Champion> getCorrectSortBy(Champion.SortType sortBy) {
        switch (sortBy) {
            case GAMES_PLAYED:
                return new Champion().new Games();
            case WINP:
                return new Champion().new WinP();
            case WINS:
                return new Champion().new Wins();
            case LOSSES:
                return new Champion().new Losses();
        }

        return new Champion().new Games();
    }
}
