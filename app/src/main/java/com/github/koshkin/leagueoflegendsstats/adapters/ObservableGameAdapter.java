package com.github.koshkin.leagueoflegendsstats.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.holders.FeaturedGameHolder;
import com.github.koshkin.leagueoflegendsstats.models.ObservableGame;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tehras on 1/23/16.
 * <p/>
 * Adapter for observable fragment
 */
public class ObservableGameAdapter extends RecyclerView.Adapter<FeaturedGameHolder> {

    private final Activity mActivity;
    private final ArrayList<ObservableGame> mObservableGames;

    public ObservableGameAdapter(ArrayList<ObservableGame> observableGames, Activity activity) {
        mObservableGames = observableGames;
        if (mObservableGames != null && mObservableGames.size() > 0)
            Collections.sort(mObservableGames);
        mActivity = activity;
    }

    @Override
    public FeaturedGameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        return new FeaturedGameHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_observable_game_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(FeaturedGameHolder holder, int position) {
        ObservableGame observableGame = mObservableGames.get(position);

        //populate the holder
        holder.populate(observableGame, mActivity, false);
    }

    @Override
    public int getItemCount() {
        if (mObservableGames == null || mObservableGames.size() == 0)
            return 0;

        return mObservableGames.size();
    }
}
