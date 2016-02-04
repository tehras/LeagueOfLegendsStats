package com.github.koshkin.leagueoflegendsstats.fragments.summoner;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.github.koshkin.leagueoflegendsstats.holders.ChampionHolder;
import com.github.koshkin.leagueoflegendsstats.holders.GameHolder;
import com.github.koshkin.leagueoflegendsstats.holders.RankedDivisionHolder;
import com.github.koshkin.leagueoflegendsstats.models.Champion;
import com.github.koshkin.leagueoflegendsstats.models.FileHandler;
import com.github.koshkin.leagueoflegendsstats.models.Game;
import com.github.koshkin.leagueoflegendsstats.models.LeagueQueueType;
import com.github.koshkin.leagueoflegendsstats.models.LeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.PlayerRanked;
import com.github.koshkin.leagueoflegendsstats.models.RecentGames;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummoner;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Stats;
import com.github.koshkin.leagueoflegendsstats.models.SummonerLeagueStandings;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Collections;

import static com.github.koshkin.leagueoflegendsstats.utils.Utils.addToLayout;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.getKDAColor;

/**
 * Created by tehras on 1/10/16.
 * <p/>
 * This will show stats
 */
public class SummonerRankedStatsFragment extends BaseFragment implements Request.RequestCallback, SwipeRefreshLayout.OnRefreshListener {

    private String mWins;
    private String mLosses;
    private PlayerRanked mRankedStats;
    private String mSummonerName, mSummonerId;
    private int mSummonerIconId;
    private View mRankedHeader, mNoFoundLayout;
    private MaterialRippleLayout mViewAllChamps;
    private LinearLayout mTopChampsLayout;
    private MaterialRippleLayout mViewAllMatches;
    private LinearLayout mRecentMatchesLayout;
    private View mNoMatchesLayout;
    private RecentGames mRecentGames;
    private FileHandler mProfFileHandler;
    private int mSwipeToRefresh;
    private View mDivisionLayout;

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

    @Override
    protected String getToolbarTitle() {
        return getActivity().getResources().getString(R.string.fragment_title_ranked_stats);
    }

    @Override
    protected boolean showFab() {
        return true;
    }

    @Override
    public String getSummonerName() {
        return mSummonerName;
    }

    @Override
    public SimpleSummoner getFavorite() {
        return new SimpleSummoner(mRankedStats, mSummonerId, mSummonerName, mSummonerIconId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranked_layout, container, false);

        //Header Layout - Card1
        initializeHeaderLayout(rootView);
        //Ranked Layout - Card2
        initializeRankedLayout(rootView);
        //Champ Layout - Card3
        initializeTopChampsLayout(rootView);
        //History Layout - Card4
        initializeMatchHistoryLayout(rootView);

        if (mRankedStats == null)
            showLoading();
        else {
            populateRankedHeader();
            populateTopChampionsLayout();
        }

        if (mLeagueStandings != null)
            populateLeagueLayout();

        if (mRecentGames != null)
            populateMatchHistory();

        return rootView;
    }

    private LeagueStandings mLeagueStandings;

    /**
     * layout for the bottom match history layout
     *
     * @param rootView initial root view from onCreateView
     */
    private void initializeMatchHistoryLayout(View rootView) {
        mRecentMatchesLayout = (LinearLayout) rootView.findViewById(R.id.match_history_container);
        mNoMatchesLayout = rootView.findViewById(R.id.match_history_not_found);
        mViewAllMatches = (MaterialRippleLayout) rootView.findViewById(R.id.match_history_full_list);
    }

    /**
     * layout for the mid card - top 3 champs
     *
     * @param rootView initial root view from onCreateView
     */
    private void initializeTopChampsLayout(View rootView) {
        mTopChampsLayout = (LinearLayout) rootView.findViewById(R.id.champion_list_container);
        mNoFoundLayout = rootView.findViewById(R.id.champion_no_found);
        mViewAllChamps = (MaterialRippleLayout) rootView.findViewById(R.id.champion_view_full_list);
    }

    private TextView mRankedKills, mRankedDeaths, mRankedAssists, mRankedKDA, mRankedSummonerName, mRankedWins, mRankedLosses;
    private ImageView mRankedIcon;

    private void initializeHeaderLayout(View rootView) {
        mRankedKills = (TextView) rootView.findViewById(R.id.ranked_kills);
        mRankedDeaths = (TextView) rootView.findViewById(R.id.ranked_deaths);
        mRankedAssists = (TextView) rootView.findViewById(R.id.ranked_assists);
        mRankedKDA = (TextView) rootView.findViewById(R.id.ranked_kda);
        mRankedSummonerName = (TextView) rootView.findViewById(R.id.ranked_summoner_name);

        mRankedIcon = (ImageView) rootView.findViewById(R.id.ranked_logo);

        mRankedHeader = rootView.findViewById(R.id.ranked_header);
    }

    private void initializeRankedLayout(View rootView) {
        mDivisionLayout = rootView.findViewById(R.id.ranked_division_layout);
        mDivisionLayout.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSummonerId = getArguments().getString(ARG_SUMMONER_ID);
        mSummonerName = getArguments().getString(ARG_SUMMONER_NAME);
        mWins = getArguments().getString(ARG_SUMMONER_WINS);
        mLosses = getArguments().getString(ARG_SUMMONER_LOSSES);
        mSummonerIconId = getArguments().getInt(ARG_SUMMONER_ICON_ID);

        String name = StaticDataHolder.getInstance(getActivity()).getProfileIconName(mSummonerIconId);
        if (name != null)
            executeGetProfileImage(this, name);

        executeRemainingCalls();
    }

    private void executeRemainingCalls() {
        executeGetRankedStats(this, mSummonerId);
        executeGetRankeGameHistory(this, mSummonerId);
        executeGetLeagueBySummonerIds(this, mSummonerId);
    }

    private int resultsReturned = 0;

    @Override
    public void finished(Response response, Request request) {
        switch (request.getURIHelper()) {
            case GET_PROFILE_URI:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mProfFileHandler = (FileHandler) response.getReturnedObject();

                    populateHeaderProfImage(mRankedIcon, mProfFileHandler);
                }
                break;
            case GET_SUMMONER_RANKED:
                resultsReturned++;
                mSwipeToRefresh--;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    PlayerRanked rankedStats = (PlayerRanked) response.getReturnedObject();

                    if (rankedStats != null) {
                        mRankedStats = rankedStats;

                        addOnSwipeToRefreshListener(this);

                        populateRankedHeader();
                        populateTopChampionsLayout();
                    } else {
                        generalException();
                    }
                } else {
                    generalException();
                }
                break;
            case GET_SUMMONER_RANKED_GAMES:
                resultsReturned++;
                mSwipeToRefresh--;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    RecentGames recentGames = (RecentGames) response.getReturnedObject();

                    if (recentGames != null) {
                        mRecentGames = recentGames;
                        populateMatchHistory();
                    } else {
                        populateMatchHistoryError();
                    }
                } else {
                    populateMatchHistoryError();
                }
                break;
            case GET_LEAGUE_BY_SUMMONERS:
                resultsReturned++;
                mSwipeToRefresh--;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mLeagueStandings = getLeagueStanding((SummonerLeagueStandings) response.getReturnedObject());

                    if (mLeagueStandings != null)
                        populateLeagueLayout();
                }
                break;
        }
        if (resultsReturned == 2)
            hideLoading();
        if (mSwipeToRefresh <= 0)
            stopRefreshing();
    }

    private LeagueStandings getLeagueStanding(SummonerLeagueStandings leagueStandings) {
        if (leagueStandings == null || leagueStandings.getLeagueStandingsHashMap() == null || leagueStandings.getLeagueStandingsHashMap().size() == 0)
            return null;

        for (String key : leagueStandings.getLeagueStandingsHashMap().keySet()) {
            if (key.equalsIgnoreCase(mSummonerId)) {
                ArrayList<LeagueStandings> array = leagueStandings.getLeagueStandingsHashMap().get(key);
                for (LeagueStandings standings : array) {
                    if (standings.getQueueType() == LeagueQueueType.RANKED_SOLO_5x5)
                        return standings;
                }
                break;
            }
        }

        return null;
    }

    private void populateLeagueLayout() {
        mDivisionLayout.setVisibility(View.VISIBLE);

        new RankedDivisionHolder(mDivisionLayout).populate(getActivity(), mLeagueStandings, mSummonerId);
    }

    private void populateMatchHistory() {
        ArrayList<Game> games = mRecentGames.getGames();
        if (games != null && games.size() > 0) {

            mRecentMatchesLayout.removeAllViews(); //remove views before populating
            int matchesShown = 0; //counter - we only want to show top 5??
            for (Game game : games) {
                if (game.getChampionId() == 0)
                    continue;

                if (matchesShown > 2) //only show 3
                    break;

                addToLayout(mRecentMatchesLayout, getMatchLayout(game));
                matchesShown++;

                mViewAllMatches.setOnClickListener(getMatchesViewAllOnClickListener(games));
            }

            mNoMatchesLayout.setVisibility(View.GONE);
        } else {
            populateMatchHistoryError();
        }
    }

    /**
     * Shows "No Matches Found"
     */
    private void populateMatchHistoryError() {
        mNoMatchesLayout.setVisibility(View.VISIBLE);
        mRecentMatchesLayout.setVisibility(View.GONE);
        mViewAllMatches.setVisibility(View.GONE);
    }

    private void populateTopChampionsLayout() {
        ArrayList<Champion> champs = mRankedStats.getChampions();
        if (champs != null && champs.size() > 0) {
            Collections.sort(champs);

            mTopChampsLayout.removeAllViews();//remove views before populating
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

    private View getMatchLayout(Game game) {
        @SuppressLint("InflateParams") View view = getActivity().getLayoutInflater().inflate(R.layout.partial_match_layout, null, false);
        //TOP Layout

        GameHolder h = new GameHolder(view);
        h.populate(game, getActivity(), mSummonerId, false);

        return view;
    }

    private View getChampLayout(Champion champ) {
        @SuppressLint("InflateParams") View view = getActivity().getLayoutInflater().inflate(R.layout.partial_champion_layout, null, false);

        ChampionHolder championHolder = new ChampionHolder(view) {
            @Override
            public Drawable getIcon(Champion champ) {
                return StaticDataHolder.getInstance(getActivity()).getChampionIcon(champ.getId());
            }
        };
        championHolder.populate(champ, getActivity(), true);

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void populateRankedHeader() {
        populateKillsDeathsAssists();

        mRankedKills.setText(NumberUtils.twoDecimalsSafely(mRankedStats.getKills()));
        mRankedDeaths.setText(NumberUtils.twoDecimalsSafely(mRankedStats.getDeaths()));
        mRankedAssists.setText(NumberUtils.twoDecimalsSafely(mRankedStats.getAssists()));
        mRankedKDA.setText(NumberUtils.twoDecimalsSafely(mRankedStats.getKDA()));

        mRankedKDA.setTextColor(getKDAColor(mRankedStats.getKDA(), getActivity()));

        mRankedSummonerName.setText(mSummonerName);

        populateHeaderProfImage(mRankedIcon, mProfFileHandler, mSummonerIconId);

        if (resultsReturned == 2)

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

    public View.OnClickListener getMatchesViewAllOnClickListener(final ArrayList<Game> games) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null && getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).startFragment(SummonerRankedGamesFragment.getInstance(games, mSummonerId));
                }
            }
        };
    }

    @Override
    public void onRefresh() {
        mSwipeToRefresh = 3;
        executeRemainingCalls();
    }
}
