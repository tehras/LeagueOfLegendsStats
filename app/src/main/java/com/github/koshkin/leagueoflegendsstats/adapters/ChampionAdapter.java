package com.github.koshkin.leagueoflegendsstats.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.Champion;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;

import java.util.ArrayList;
import java.util.HashMap;

import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getCS;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getChampName;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getChampPlayed;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getKDA;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getKDADouble;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getKillsDeathsAssists;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getWinPercentage;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getWinPercentageDouble;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.NOT_AVAILABLE;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.assignWinPercentageColor;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.getKDAColor;

/**
 * Created by tehras on 1/11/16.
 */
public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.Holder> {

    private final ArrayList<Champion> mChampions;
    private final Activity mActivity;

    public ChampionAdapter(ArrayList<Champion> champions, Activity context) {
        mChampions = new ArrayList<>(champions);
        mChampions.remove(0);
        mActivity = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_champions_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder h, int position) {
        Champion champ = mChampions.get(position);

        h.champName.setText(getChampName(champ, mActivity));
        h.champCs.setText(getCS(champ));

        String kda = getKDA(champ);
        h.champKda.setText(kda);
        if (!kda.equalsIgnoreCase(NOT_AVAILABLE))
            h.champKda.setTextColor(getKDAColor(getKDADouble(champ), mActivity));

        h.champKillsDeaths.setText(getKillsDeathsAssists(champ));
        h.champWinPercentage.setText(getWinPercentage(champ));
        assignWinPercentageColor(h.champWinPercentage, getWinPercentageDouble(champ), mActivity);
        h.champPlayed.setText(getChampPlayed(champ));

        Drawable drawable = getIcon(champ);
        if (drawable != null)
            h.champIcon.setImageDrawable(drawable);
        else
            h.champIcon.setImageDrawable(mActivity.getDrawable(R.drawable.ic_wallpaper_black_48dp));
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

    public class Holder extends RecyclerView.ViewHolder {

        final TextView champName, champCs, champKda, champKillsDeaths, champWinPercentage, champPlayed;
        final ImageView champIcon;

        public Holder(View view) {
            super(view);

            champIcon = (ImageView) view.findViewById(R.id.champion_icon);

            champName = (TextView) view.findViewById(R.id.champion_name);
            champCs = (TextView) view.findViewById(R.id.champion_cs);
            champKda = (TextView) view.findViewById(R.id.champion_kda);
            champKillsDeaths = (TextView) view.findViewById(R.id.champion_kills_deaths_assists);
            champWinPercentage = (TextView) view.findViewById(R.id.champion_win_percentage);
            champPlayed = (TextView) view.findViewById(R.id.champion_played);

            view.findViewById(R.id.divider).setVisibility(View.GONE);
        }
    }
}
