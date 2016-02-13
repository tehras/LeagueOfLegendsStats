package com.github.koshkin.leagueoflegendsstats.fragments.favorite;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.adapters.FavoritesAdapter;
import com.github.koshkin.leagueoflegendsstats.fragments.BaseSimpleRecyclerViewFragment;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummoner;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummonerComparator;

import java.util.ArrayList;

/**
 * Created by tehras on 1/19/16.
 * <p/>
 * SimpleSummonerComparator Fragment
 */
public class FavoritesFragment extends BaseSimpleRecyclerViewFragment {

    private ArrayList<SimpleSummoner> mSimpleSummoners;
    private SimpleSummonerComparator.SortBy mSortBy;

    public static FavoritesFragment getInstance(ArrayList<SimpleSummoner> simpleSummoners) {
        return new FavoritesFragment().setSimpleSummoners(simpleSummoners);
    }

    public FavoritesFragment setSimpleSummoners(ArrayList<SimpleSummoner> simpleSummoners) {
        mSimpleSummoners = simpleSummoners;
        return this;
    }

    @Override
    public String getToolbarTitle() {
        return getActivity().getResources().getString(R.string.fragment_title_favorites);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mSortBy = SimpleSummonerComparator.SortBy.DATE_ADDED;
    }

    private FavoritesAdapter mAdapter;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.favorites_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SimpleSummonerComparator.SortBy thisSortBy = mSortBy;

        switch (item.getItemId()) {
            case R.id.action_sort_alpha:
                thisSortBy = SimpleSummonerComparator.SortBy.ALPHABETICAL;
                break;
            case R.id.action_sort_date_added:
                thisSortBy = SimpleSummonerComparator.SortBy.DATE_ADDED;
                break;
            case R.id.action_sort_win_p:
                thisSortBy = SimpleSummonerComparator.SortBy.WIN_P;
                break;
            case R.id.action_sort_wins:
                thisSortBy = SimpleSummonerComparator.SortBy.WINS;
                break;
            case R.id.action_sort_losses:
                thisSortBy = SimpleSummonerComparator.SortBy.LOSSES;
                break;
        }

        if (thisSortBy != mSortBy) {
            mSortBy = thisSortBy;
            updateAdapter();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateAdapter() {
        if (mAdapter != null)
            mAdapter.updateSort(mSortBy);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (mAdapter == null)
            mAdapter = new FavoritesAdapter(mSimpleSummoners, getActivity());

        return mAdapter;
    }
}
