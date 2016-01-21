package com.github.koshkin.leagueoflegendsstats.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.holders.LeagueChampionHolder;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummoner;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummonerComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by tehras on 1/19/16.
 * <p/>
 * SimpleSummonerComparator adapter
 */
public class FavoritesAdapter extends RecyclerView.Adapter<LeagueChampionHolder> {
    private final Activity mContext;
    private ArrayList<SimpleSummoner> mSimpleSummoners;

    public FavoritesAdapter(ArrayList<SimpleSummoner> simpleSummoners, Activity activity) {
        if (simpleSummoners != null) {
            mSimpleSummoners = simpleSummoners;
            sortBy(SimpleSummonerComparator.SortBy.DATE_ADDED);
        }
        mContext = activity;
    }

    private void sortBy(SimpleSummonerComparator.SortBy sortBy) {
        if (mSimpleSummoners != null && mSimpleSummoners.size() > 0) {
            Comparator<SimpleSummoner> comparator = null;

            switch (sortBy) {
                case WIN_P:
                    comparator = new SimpleSummonerComparator().new WinPComparator();
                    break;
                case WINS:
                    comparator = new SimpleSummonerComparator().new WinComparator();
                    break;
                case LOSSES:
                    comparator = new SimpleSummonerComparator().new LossComparator();
                    break;
                case ALPHABETICAL:
                    comparator = new SimpleSummonerComparator().new AlphabeticalComparator();
                    break;
                case DATE_ADDED:
                    comparator = new SimpleSummonerComparator().new DateAddedComparator();
                    break;
            }

            Collections.sort(mSimpleSummoners, comparator);
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
        holder.populate(mSimpleSummoners.get(position), (MainActivity) mContext, true);
    }

    @Override
    public int getItemCount() {
        if (mSimpleSummoners == null)
            return 0;
        else return mSimpleSummoners.size();
    }

    public void updateSort(SimpleSummonerComparator.SortBy sortBy) {
        sortBy(sortBy);
        notifyDataSetChanged();
    }
}
