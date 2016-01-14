package com.github.koshkin.leagueoflegendsstats.holders;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.Champion;

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
 * Created by tehras on 1/13/16.
 * <p/>
 * <p/>
 * will reuse in multiple spots
 */
public abstract class ChampionHolder extends RecyclerView.ViewHolder {

    public TextView champName, champCs, champKda, champKillsDeaths, champWinPercentage, champPlayed;
    public ImageView champIcon;
    public View divider;

    public ChampionHolder(View view) {
        super(view);

        champIcon = (ImageView) view.findViewById(R.id.champion_icon);

        champName = (TextView) view.findViewById(R.id.champion_name);
        champCs = (TextView) view.findViewById(R.id.champion_cs);
        champKda = (TextView) view.findViewById(R.id.champion_kda);
        champKillsDeaths = (TextView) view.findViewById(R.id.champion_kills_deaths_assists);
        champWinPercentage = (TextView) view.findViewById(R.id.champion_win_percentage);
        champPlayed = (TextView) view.findViewById(R.id.champion_played);

        divider = view.findViewById(R.id.divider);
    }

    public void populate(final Champion champ, final Activity activity, boolean t) {
        final ChampionHolder h = this;
        h.champName.setText(getChampName(champ, activity));
        h.champCs.setText(getCS(champ));

        String kda = getKDA(champ);
        h.champKda.setText(kda);
        if (!kda.equalsIgnoreCase(NOT_AVAILABLE))
            h.champKda.setTextColor(getKDAColor(getKDADouble(champ), activity));

        h.champKillsDeaths.setText(getKillsDeathsAssists(champ));
        h.champWinPercentage.setText(getWinPercentage(champ));
        assignWinPercentageColor(h.champWinPercentage, getWinPercentageDouble(champ), activity);
        h.champPlayed.setText(getChampPlayed(champ));

        new Runnable() {
            @Override
            public void run() {
                Drawable drawable = getIcon(champ);
                if (drawable != null)
                    h.champIcon.setImageDrawable(drawable);
                else
                    h.champIcon.setImageDrawable(activity.getDrawable(R.drawable.ic_wallpaper_black_48dp));
            }
        }.run();

        if (!t)
            divider.setVisibility(View.GONE);

    }

    public abstract Drawable getIcon(Champion champ);
}
