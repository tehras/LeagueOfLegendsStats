package com.github.koshkin.leagueoflegendsstats.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.holders.GameHolder;
import com.github.koshkin.leagueoflegendsstats.models.Game;

import java.util.ArrayList;

/**
 * Created by tehras on 1/13/16.
 * <p/>
 * Adapter
 */
public class GameHistoryAdapter extends RecyclerView.Adapter<GameHolder> {

    private final ArrayList<Game> mGames;
    private final Activity mActivity;

    public GameHistoryAdapter(ArrayList<Game> games, Activity activity) {
        mGames = games;
        mActivity = activity;
    }

    @Override
    public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        return new GameHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_game_history_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(GameHolder h, int position) {
        Game game = mGames.get(position);
        h.populate(game, mActivity, true);
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

}
