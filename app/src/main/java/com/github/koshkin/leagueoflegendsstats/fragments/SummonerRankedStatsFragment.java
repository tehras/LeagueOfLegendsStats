package com.github.koshkin.leagueoflegendsstats.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.Champion;
import com.github.koshkin.leagueoflegendsstats.models.PlayerRanked;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Stats;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Collections;

import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getCS;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getChampName;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getChampPlayed;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getKDA;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getKDADouble;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getKillsDeathsAssists;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getWinPercentage;
import static com.github.koshkin.leagueoflegendsstats.utils.ChampUtils.getWinPercentageDouble;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.NOT_AVAILABLE;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.addToLayout;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.assignWinPercentageColor;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.getKDAColor;

/**
 * Created by tehras on 1/10/16.
 * <p/>
 * This will show stats
 */
public class SummonerRankedStatsFragment extends BaseFragment implements Request.RequestCallback {

    private String mWins;
    private String mLosses;
    private PlayerRanked mRankedStats;
    private String mSummonerName;
    private int mSummonerIconId;
    private View mRankedHeader, mNoFoundLayout;
    private MaterialRippleLayout mViewAllChamps;
    private LinearLayout mTopChampsLayout;

    public static SummonerRankedStatsFragment getInstance(int summonerIconId, String summonerId, String summonerName, String wins, String losses) {
        Bundle args = new Bundle();
        args.putInt(ARG_SUMMONER_ICON_ID, summonerIconId);
        args.putString(ARG_SUMMONER_ID, summonerId);
        args.putString(ARG_SUMMONER_NAME, summonerName);
        args.putString(ARG_SUMMONER_WINS, wins);
        args.putString(ARG_SUMMONER_LOSSES, losses);

        SummonerRankedStatsFragment fragment = new SummonerRankedStatsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranked_layout, container, false);

        initializeHeaderLayout(rootView);
        initializeTopChampsLayout(rootView);

        if (mRankedStats == null)
            showLoading();
        else {
            populateRankedHeader();
            populateTopChampionsLayout();
        }

        return rootView;
    }

    private void initializeTopChampsLayout(View rootView) {
        mTopChampsLayout = (LinearLayout) rootView.findViewById(R.id.champion_list_container);
        mNoFoundLayout = rootView.findViewById(R.id.champion_no_found);
        mViewAllChamps = (MaterialRippleLayout) rootView.findViewById(R.id.champion_view_full_list);
    }

    private TextView mRankedKills, mRankedDeaths, mRankedAssists, mRankedKDA, mRankedSummonerName, mRankedWins, mRankedLosses;
    private ImageView mRankedIcon; //TODO later

    private void initializeHeaderLayout(View rootView) {
        mRankedKills = (TextView) rootView.findViewById(R.id.ranked_kills);
        mRankedDeaths = (TextView) rootView.findViewById(R.id.ranked_deaths);
        mRankedAssists = (TextView) rootView.findViewById(R.id.ranked_assists);
        mRankedKDA = (TextView) rootView.findViewById(R.id.ranked_kda);
        mRankedWins = (TextView) rootView.findViewById(R.id.ranked_wins);
        mRankedLosses = (TextView) rootView.findViewById(R.id.ranked_losses);
        mRankedSummonerName = (TextView) rootView.findViewById(R.id.ranked_summoner_name);

        mRankedIcon = (ImageView) rootView.findViewById(R.id.ranked_logo);

        mRankedHeader = rootView.findViewById(R.id.ranked_header);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String summonerId = getArguments().getString(ARG_SUMMONER_ID);
        mSummonerName = getArguments().getString(ARG_SUMMONER_NAME);
        mWins = getArguments().getString(ARG_SUMMONER_WINS);
        mLosses = getArguments().getString(ARG_SUMMONER_LOSSES);
        mSummonerIconId = getArguments().getInt(ARG_SUMMONER_ICON_ID);

        executeGetRankedStats(this, summonerId);
    }

    @Override
    public void finished(Response response, Request request) {
        switch (request.getURIHelper()) {
            case GET_SUMMONER_RANKED:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    PlayerRanked rankedStats = (PlayerRanked) response.getReturnedObject();

                    if (rankedStats != null) {
                        mRankedStats = rankedStats;
                        populateRankedHeader();
                        populateTopChampionsLayout();
                    } else {
                        generalException();
                    }
                } else {
                    generalException();
                }
        }
    }

    private void populateTopChampionsLayout() {
        ArrayList<Champion> champs = mRankedStats.getChampions();
        if (champs != null && champs.size() > 0) {
            Collections.sort(champs);
            int champInt = 0;
            for (Champion champion : champs) {
                if (champion.getId() == 0)
                    continue;

                if (champInt > 2) //only show top 3
                    break;

                addToLayout(mTopChampsLayout, getChampLayout(champion));
                champInt++;
            }

            mViewAllChamps.setOnClickListener(getViewAllOnClickListener(champs));

            mViewAllChamps.setVisibility(View.VISIBLE);
            mNoFoundLayout.setVisibility(View.GONE);
            mTopChampsLayout.setVisibility(View.VISIBLE);
        } else {
            mViewAllChamps.setVisibility(View.GONE);
            mNoFoundLayout.setVisibility(View.VISIBLE);
            mTopChampsLayout.setVisibility(View.GONE);
        }
    }

    private View getChampLayout(Champion champ) {
        @SuppressLint("InflateParams") View view = getActivity().getLayoutInflater().inflate(R.layout.partial_champion_layout, null, false);

        ImageView champImage = (ImageView) view.findViewById(R.id.champion_icon);
        Drawable drawable = StaticDataHolder.getInstance(getActivity()).getChampionIcon(champ.getId());
        if (drawable != null)
            champImage.setImageDrawable(drawable);

        TextView champName = (TextView) view.findViewById(R.id.champion_name);
        TextView champCs = (TextView) view.findViewById(R.id.champion_cs);
        TextView champKda = (TextView) view.findViewById(R.id.champion_kda);
        TextView champKillsDeaths = (TextView) view.findViewById(R.id.champion_kills_deaths_assists);
        TextView champWinPercentage = (TextView) view.findViewById(R.id.champion_win_percentage);
        TextView champPlayed = (TextView) view.findViewById(R.id.champion_played);

        champName.setText(getChampName(champ, getActivity()));
        champCs.setText(getCS(champ));

        String kda = getKDA(champ);
        champKda.setText(kda);
        if (!kda.equalsIgnoreCase(NOT_AVAILABLE))
            champKda.setTextColor(getKDAColor(getKDADouble(champ), getActivity()));

        champKillsDeaths.setText(getKillsDeathsAssists(champ));
        champWinPercentage.setText(getWinPercentage(champ));
        assignWinPercentageColor(champWinPercentage, getWinPercentageDouble(champ), this.getActivity());
        champPlayed.setText(getChampPlayed(champ));

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void populateRankedHeader() {
        populateKillsDeathsAssists();

        mRankedKills.setText(NumberUtils.twoDecimalsSafely(mRankedStats.getKills()));
        mRankedDeaths.setText(NumberUtils.twoDecimalsSafely(mRankedStats.getDeaths()));
        mRankedAssists.setText(NumberUtils.twoDecimalsSafely(mRankedStats.getAssists()));
        mRankedKDA.setText(NumberUtils.twoDecimalsSafely(mRankedStats.getKDA()));

        mRankedWins.setText(mWins + "W");
        mRankedLosses.setText(mLosses + "L");

        mRankedKDA.setTextColor(getKDAColor(mRankedStats.getKDA(), getActivity()));

        mRankedSummonerName.setText(mSummonerName);

        Drawable drawable = StaticDataHolder.getInstance(getActivity()).getProfileIcon(mSummonerIconId);
        if (drawable != null)
            mRankedIcon.setImageDrawable(drawable);

        hideLoading();
    }

    private void generalException() {
        hideLoading();
        hideHeaderLayout();

        initializeErrorLayout(getResources().getString(R.string.general_error_title), getResources().getString(R.string.general_error_body));
    }

    private void hideHeaderLayout() {
        mRankedHeader.setVisibility(View.GONE);
    }

    private void populateKillsDeathsAssists() {
        if (mRankedStats != null) {
            if (mRankedStats.getChampions() != null) {
                double kills = 0d, deaths = 0d, assists = 0d, sessions = 0;
                for (Champion champion : mRankedStats.getChampions()) {
                    if (champion.getStats() != null) {
                        Stats stats = champion.getStats();
                        kills = kills + stats.getTotalChampionKills();
                        deaths = deaths + stats.getTotalDeathsPerSession();
                        assists = assists + stats.getTotalAssists();
                        sessions = sessions + stats.getTotalSessionsPlayed();
                    }
                }

                mRankedStats.setKills(kills / sessions);
                mRankedStats.setDeaths(deaths / sessions);
                mRankedStats.setAssists(assists / sessions);

                if (deaths != 0) {
                    double kda = (((kills + assists)) / (deaths));
                    mRankedStats.setKDA(kda);
                }
            }
        }
    }

    public View.OnClickListener getViewAllOnClickListener(final ArrayList<Champion> champions) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null && getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).startFragment(SummonerRankedChampionsFragment.getInstance(champions));
                }
            }
        };
    }
}
