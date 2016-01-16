package com.github.koshkin.leagueoflegendsstats.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.holders.LeagueChampionHolder;
import com.github.koshkin.leagueoflegendsstats.models.LeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.RankedSummoner;

import java.util.ArrayList;

/**
 * Created by tehras on 1/16/16.
 * <p/>
 * Adapter to get all the stuff
 */
public abstract class LeagueRankingAdapter extends RecyclerView.Adapter<LeagueChampionHolder> {

    private ArrayList<RankedSummoner> mEntries;
    private final Activity mContext;
    private ArrayList<RankedSummoner> mArrayToExecute;

    public LeagueRankingAdapter(LeagueStandings leagueStandings, Activity activity) {
        mEntries = leagueStandings.getEntries();
        mContext = activity;

        getItemsToExecute(mEntries, true);
    }

    private void getItemsToExecute(ArrayList<RankedSummoner> entries, boolean isFirst) {
        int itemsToExecute = 0;

        ArrayList<RankedSummoner> arrayToExecute = null;
        for (RankedSummoner summoner : entries) {
            int MAX_TO_EXECUTE = 40;
            if (itemsToExecute >= MAX_TO_EXECUTE)
                break;

            if (mArrayToExecute != null) {
                if (mArrayToExecute.contains(summoner))
                    continue;
            }
            if (summoner.getSummoner() == null) {
                if (arrayToExecute == null)
                    arrayToExecute = new ArrayList<>();
                arrayToExecute.add(summoner);

                itemsToExecute++;
            }
        }

        if (arrayToExecute != null) {
            mArrayToExecute = arrayToExecute;
            getMoreItems(arrayToExecute, isFirst);
        }
    }

    public abstract void getMoreItems(ArrayList<RankedSummoner> arrayToExecute, boolean isFirst);

    @Override
    public LeagueChampionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LeagueChampionHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_league_rankings_adapter, parent, false));
    }

    int mHighestPos = 0;

    @Override
    public void onBindViewHolder(LeagueChampionHolder holder, int position) {
        RankedSummoner summoner = mEntries.get(position);
        if (mHighestPos < position) {
            mHighestPos = position;

            checkIfNeedToExecuteMore(position);
        }
        holder.populate(summoner, (MainActivity) mContext, position + 1, true);
    }

    private void checkIfNeedToExecuteMore(int position) {
        //CHECK 20 ahead
        int PREFETCH_AHEAD = 20;
        if (mEntries.size() > position + PREFETCH_AHEAD) {
            RankedSummoner summoner = mEntries.get(position + PREFETCH_AHEAD);

            if (summoner.getSummoner() == null && (mArrayToExecute == null || !mArrayToExecute.contains(summoner))) {
                getItemsToExecute(mEntries, false);
            }
        } else {
            getItemsToExecute(mEntries, false);
        }
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    public void update(ArrayList<RankedSummoner> updatedEntries) {
        for (RankedSummoner summoner : updatedEntries) {
            int index = mEntries.indexOf(summoner);
            if (index != -1) {
                mEntries.get(index).setSummoner(summoner.getSummoner());
                if (index < mHighestPos)
                    notifyItemChanged(index);
            }
            if (mArrayToExecute != null) {
                int executeIndex = mArrayToExecute.indexOf(summoner);
                if (executeIndex != -1) {
                    mArrayToExecute.remove(executeIndex);
                }
            }
        }
    }
}
