package com.github.koshkin.leagueoflegendsstats.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.holders.LeagueChampionHolder;
import com.github.koshkin.leagueoflegendsstats.models.Favorite;
import com.github.koshkin.leagueoflegendsstats.models.Favorites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by tehras on 1/19/16.
 * <p/>
 * Favorites adapter
 */
public class FavoritesAdapter extends RecyclerView.Adapter<LeagueChampionHolder> {
    private final Activity mContext;
    private ArrayList<Favorite> mFavorites;

    public FavoritesAdapter(Favorites favorites, Activity activity) {
        if (favorites != null) {
            mFavorites = favorites.getFavorites();
            sortBy(Favorites.SortBy.DATE_ADDED);
        }
        mContext = activity;
    }

    private void sortBy(Favorites.SortBy sortBy) {
        if (mFavorites != null && mFavorites.size() > 0) {
            Comparator<Favorite> comparator = null;

            switch (sortBy) {
                case WIN_P:
                    comparator = new Favorites().new WinPComparator();
                    break;
                case WINS:
                    comparator = new Favorites().new WinComparator();
                    break;
                case LOSSES:
                    comparator = new Favorites().new LossComparator();
                    break;
                case ALPHABETICAL:
                    comparator = new Favorites().new AlphabeticalComparator();
                    break;
                case DATE_ADDED:
                    comparator = new Favorites().new DateAddedComparator();
                    break;
            }

            Collections.sort(mFavorites, comparator);
        }
    }

    @Override
    public LeagueChampionHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //initial layout
        return new LeagueChampionHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_league_rankings_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(LeagueChampionHolder holder, int position) {
        holder.populate(mFavorites.get(position), (MainActivity) mContext, true);
    }

    @Override
    public int getItemCount() {
        if (mFavorites == null)
            return 0;
        else return mFavorites.size();
    }

    public void updateSort(Favorites.SortBy sortBy) {
        sortBy(sortBy);
        notifyDataSetChanged();
    }
}
