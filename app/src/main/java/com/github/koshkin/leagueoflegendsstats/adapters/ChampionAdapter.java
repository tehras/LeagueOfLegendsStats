package com.github.koshkin.leagueoflegendsstats.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.holders.ChampionHolder;
import com.github.koshkin.leagueoflegendsstats.models.Champion;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tehras on 1/11/16.
 * <p/>
 * Champion Adapter . Generates all Game history for adapter
 */
public class ChampionAdapter extends RecyclerView.Adapter<ChampionHolder> {

    private final ArrayList<Champion> mChampions;
    private final Activity mActivity;

    public ChampionAdapter(ArrayList<Champion> champions, Activity context) {
        mChampions = new ArrayList<>(champions);
        mChampions.remove(0);
        mActivity = context;
    }

    @Override
    public ChampionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        return new ChampionHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_champions_layout, parent, false)) {
            @Override
            public Drawable getIcon(Champion champ) {
                return ChampionAdapter.this.getIcon(champ);
            }
        };
    }

    @Override
    public void onBindViewHolder(ChampionHolder h, int position) {
        Champion champ = mChampions.get(position);
        h.populate(champ, mActivity, false);
    }

    private HashMap<Integer, Drawable> mIconMap = new HashMap<>();

    private Drawable getIcon(Champion champ) {
        int iconInt = champ.getId();

        Drawable icon;
        if (mIconMap.containsKey(iconInt))
            icon = mIconMap.get(iconInt);
        else {
            icon = StaticDataHolder.getInstance(mActivity).getChampionIcon(iconInt);
            mIconMap.put(iconInt, icon);
        }

        return icon;
    }

    @Override
    public int getItemCount() {
        return mChampions.size();
    }

}
