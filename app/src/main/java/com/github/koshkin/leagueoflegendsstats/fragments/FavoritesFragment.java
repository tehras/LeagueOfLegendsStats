package com.github.koshkin.leagueoflegendsstats.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.adapters.FavoritesAdapter;
import com.github.koshkin.leagueoflegendsstats.models.Favorites;

/**
 * Created by tehras on 1/19/16.
 *
 * Favorites Fragment
 */
public class FavoritesFragment extends BaseSimpleRecyclerViewFragment {

    private Favorites mFavorites;
    private Favorites.SortBy mSortBy;

    public static FavoritesFragment getInstance(Favorites favorites) {
        return new FavoritesFragment().setFavorites(favorites);
    }

    public FavoritesFragment setFavorites(Favorites favorites) {
        mFavorites = favorites;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mSortBy = Favorites.SortBy.DATE_ADDED;
    }

    private FavoritesAdapter mAdapter;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.favorites_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Favorites.SortBy thisSortBy = mSortBy;

        switch (item.getItemId()) {
            case R.id.action_sort_alpha:
                thisSortBy = Favorites.SortBy.ALPHABETICAL;
                break;
            case R.id.action_sort_date_added:
                thisSortBy = Favorites.SortBy.DATE_ADDED;
                break;
            case R.id.action_sort_win_p:
                thisSortBy = Favorites.SortBy.WIN_P;
                break;
            case R.id.action_sort_wins:
                thisSortBy = Favorites.SortBy.WINS;
                break;
            case R.id.action_sort_losses:
                thisSortBy = Favorites.SortBy.LOSSES;
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
            mAdapter = new FavoritesAdapter(mFavorites, getActivity());

        return mAdapter;
    }
}
