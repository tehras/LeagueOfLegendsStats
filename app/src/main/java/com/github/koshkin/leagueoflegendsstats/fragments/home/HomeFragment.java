package com.github.koshkin.leagueoflegendsstats.fragments.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.fragments.favorite.FavoritesFragment;
import com.github.koshkin.leagueoflegendsstats.fragments.league.LeagueRankingFragment;
import com.github.koshkin.leagueoflegendsstats.fragments.observable.ObservableFragment;
import com.github.koshkin.leagueoflegendsstats.fragments.settings.SettingsFragment;
import com.github.koshkin.leagueoflegendsstats.holders.FeaturedGameHolder;
import com.github.koshkin.leagueoflegendsstats.holders.LeagueChampionHolder;
import com.github.koshkin.leagueoflegendsstats.holders.MySummonerHolder;
import com.github.koshkin.leagueoflegendsstats.models.FeaturedGames;
import com.github.koshkin.leagueoflegendsstats.models.LeagueQueueType;
import com.github.koshkin.leagueoflegendsstats.models.LeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.ObservableGame;
import com.github.koshkin.leagueoflegendsstats.models.PlayerRanked;
import com.github.koshkin.leagueoflegendsstats.models.RankedSummoner;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummoner;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummonerComparator;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.sqlite.FavoritesSqLiteHelper;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;
import com.github.koshkin.leagueoflegendsstats.views.CustomCardView;
import com.github.koshkin.leagueoflegendsstats.views.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tehras on 1/9/16.
 * <p/>
 * Home layout to pop up
 */
public class HomeFragment extends BaseFragment implements Request.RequestCallback, SwipeRefreshLayout.OnRefreshListener {

    private CustomCardView mChallengerLayout, mFavoriteLayout, mFeaturedGamesLayout;
    private LeagueStandings mLeagueStandings;
    private boolean mFirstLoad = true;
    private FeaturedGames mFeaturedGames;
    private int mCallsToExecute;
    private Summoner mMySummoner;

    private CustomCardView mMySummonerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EXECUTE
        mCallsToExecute = 0;
        if (mLeagueStandings == null) {
            mCallsToExecute++;
            executeGetChallengerStandings(this, LeagueQueueType.RANKED_SOLO_5x5);
        }
        if (mMySummoner == null) {
            mMySummoner = StaticDataHolder.getInstance(getActivity()).getMySummoner();
            if (mMySummoner != null) {
                mCallsToExecute++;
                executeGetRankedStats(this, mMySummoner.getSummonerId());
            }
        }

        if (mFeaturedGamesLayout == null) {
            mCallsToExecute++;
            executeGetFeaturedGames(this);
        }
        if (mCallsToExecute > 0)
            showLoading();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!mFirstLoad) {
            populateFavoriteLayout();
        }
        mFirstLoad = false;

        addOnSwipeToRefreshListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);

        initChallengerLayout(view);
        initFavoriteLayout(view);
        initFeaturedGames(view);
        initMySummoner(view);

        populateFavoriteLayout();
        if (mLeagueStandings != null) {
            populateChallengerLayout();
        } else
            challengerLayoutError();

        if (mMySummoner != null)
            populateMySummonerLayout();
        else
            populateErrorMySummonerLayout();

        if (mFeaturedGames != null) {
            populateObservableGames();
        }

        return view;
    }

    private void initMySummoner(View view) {
        mMySummonerLayout = (CustomCardView) view.findViewById(R.id.my_summoner_layout);
        mMySummonerLayout.hideViewAllView();
    }

    private void initChallengerLayout(View view) {
        mChallengerLayout = (CustomCardView) view.findViewById(R.id.challenger_layout);
        mChallengerLayout.showError().setViewAllOnClickListener(viewAllChallengersListener());
    }

    private void initFeaturedGames(View view) {
        mFeaturedGamesLayout = (CustomCardView) view.findViewById(R.id.featured_layout);
        mFeaturedGamesLayout.showError().hideViewAllView().setViewAllOnClickListener(viewAllObservableListener());
    }

    private View.OnClickListener viewAllObservableListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startFragment(ObservableFragment.getInstance(mFeaturedGames.getObservableGames()));
            }
        };
    }

    private void initFavoriteLayout(View view) {
        mFavoriteLayout = (CustomCardView) view.findViewById(R.id.favorite_layout);
    }

    private View.OnClickListener viewAllChallengersListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null && getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).startFragment(LeagueRankingFragment.getInstance(mLeagueStandings));
            }
        };
    }

    @Override
    public void finished(Response response, Request request) {
        switch (request.getURIHelper()) {
            case GET_CHALLENGER:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mLeagueStandings = (LeagueStandings) response.getReturnedObject();
                    ArrayList<String> summonerIds = populateChallengerLayout();
                    if (summonerIds != null) {
                        mCallsToExecute++;
                        executeGetSummonersById(this, summonerIds, mLeagueStandings);
                    }
                } else {
                    challengerLayoutError();
                }
                mCallsToExecute--;
                mViewsRefreshed--;
                break;
            case GET_SUMMONER_RANKED:
                mCallsToExecute--;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    PlayerRanked playerRanked = (PlayerRanked) response.getReturnedObject();
                    mMySummoner.setPlayerRanked(playerRanked);
                    populateMySummonerLayout();
                } else {
                    populateErrorMySummonerLayout();
                }
                break;
            case GET_SUMMONER_BY_IDS:
                mCallsToExecute--;
                mViewsRefreshed--;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mLeagueStandings = (LeagueStandings) response.getReturnedObject();
                    updateChallengerLayout();
                }
                break;
            case GET_FEATURED_MATCHES:
                mCallsToExecute--;
                mViewsRefreshed--;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mFeaturedGames = (FeaturedGames) response.getReturnedObject();
                    populateObservableGames();
                }
                break;
        }
        if (mCallsToExecute <= 0)
            new Timer().schedule(new MyTimerTask(), 500);
        if (mViewsRefreshed <= 0)
            stopRefreshing();
    }

    private void populateErrorMySummonerLayout() {
        mMySummonerLayout.clearViewsFromHolder();
        mMySummonerLayout.addViewToHolder(getAddNewSummonerLayout());
    }

    private int mViewsRefreshed = 0;

    @Override
    public void onRefresh() {
        mViewsRefreshed = 2;
        executeGetChallengerStandings(this, LeagueQueueType.RANKED_SOLO_5x5);
        executeGetFeaturedGames(this);
    }

    public View getAddNewSummonerLayout() {
        @SuppressLint("InflateParams") View view = getActivity().getLayoutInflater().inflate(R.layout.partial_add_new_summoner, null);

        MaterialButton materialButton = (MaterialButton) view.findViewById(R.id.go_to_settings);
        materialButton.setOnButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startFragment(SettingsFragment.class);
            }
        });
        return view;
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (HomeFragment.this.getActivity() != null)
                HomeFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
        }
    }

    private void updateChallengerLayout() {
        int size = mChallengerLayout.getViewHolderCountSize();
        for (int i = 0; i < size; i++) {
            View view = mChallengerLayout.getViewHolderChildAt(i);
            new LeagueChampionHolder(view).updateImage(mLeagueStandings, getActivity());
        }
    }

    private void populateObservableGames() {
        if (mFeaturedGames != null && mFeaturedGames.getObservableGames() != null && mFeaturedGames.getObservableGames().size() > 0) {
            ArrayList<ObservableGame> featuredGames = mFeaturedGames.getObservableGames();
            int i = 0;
            Collections.sort(featuredGames);

            mFeaturedGamesLayout.clearViewsFromHolder();
            for (ObservableGame game : featuredGames) {
                if (i == 1)
                    break;

                LayoutInflater inflater = getActivity().getLayoutInflater();
                @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.partial_featured_game, null, false);
                new FeaturedGameHolder(view).populate(game, getActivity(), true);

                mFeaturedGamesLayout.addViewToHolder(view);

                i++;
            }
            mFeaturedGamesLayout.hideError();
        } else {
            mFeaturedGamesLayout.showError();
            mFeaturedGamesLayout.hideViewAllView();
        }
    }

    private void populateMySummonerLayout() {
        if (mMySummoner != null) {
            mMySummonerLayout.clearViewsFromHolder();
            mMySummonerLayout.addViewToHolder(viewMySummonerLayout());

            mMySummonerLayout.hideViewAllView();
        } else {
            populateErrorMySummonerLayout();
        }
    }

    private View viewMySummonerLayout() {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getActivity()).inflate(R.layout.partial_my_summoner_layout, null, false);
        new MySummonerHolder(view).populate(mMySummoner, getActivity());
        return view;
    }

    private void populateFavoriteLayout() {
        new LoaderHelper() {
            public ArrayList<SimpleSummoner> mSimpleSummoners;

            @Override
            protected void postExecute() {
                mFavoriteLayout.clearViewsFromHolder();

                if (mSimpleSummoners != null && mSimpleSummoners.size() > 0) {
                    int i = 0;

                    Collections.sort(mSimpleSummoners, new SimpleSummonerComparator().new DateAddedComparator());

                    for (SimpleSummoner simpleSummoner : mSimpleSummoners) {
                        if (i > 2)
                            break;

                        if (simpleSummoner == null)
                            continue;

                        mFavoriteLayout.addViewToHolder(getFavoritesView(simpleSummoner));
                        i++;
                    }

                    mFavoriteLayout.setViewAllOnClickListener(viewAllFavoritesListener(mSimpleSummoners));
                } else {
                    mFavoriteLayout.showError();
                }
            }

            @Override
            protected void runInBackground() {
                mSimpleSummoners = new ArrayList<>();
                mSimpleSummoners = (ArrayList<SimpleSummoner>) FavoritesSqLiteHelper.getAllFavorites();
            }
        }.execute();
    }

    private View.OnClickListener viewAllFavoritesListener(final ArrayList<SimpleSummoner> simpleSummoners) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null && getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).startFragment(FavoritesFragment.getInstance(simpleSummoners));
            }
        };
    }

    private ArrayList<String> populateChallengerLayout() {
        ArrayList<String> summonersToReturn = null;

        if (mLeagueStandings != null && mLeagueStandings.getEntries() != null && mLeagueStandings.getEntries().size() > 0) {
            int i = 0;

            Collections.sort(mLeagueStandings.getEntries());

            mChallengerLayout.clearViewsFromHolder();
            for (RankedSummoner summoner : mLeagueStandings.getEntries()) {
                if (i > 2)
                    break;
                mChallengerLayout.addViewToHolder(getChallengerView(summoner));
                if (summonersToReturn == null)
                    summonersToReturn = new ArrayList<>();

                summonersToReturn.add(summoner.getPlayerOrTeamId());
                i++;
            }
        } else {
            mChallengerLayout.showError();
        }

        return summonersToReturn;
    }

    private View getChallengerView(RankedSummoner summoner) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.partial_summoner, null);
        new LeagueChampionHolder(view).populate(summoner, (MainActivity) getActivity(), LeagueQueueType.RANKED_SOLO_5x5, false);

        return view;
    }

    private View getFavoritesView(SimpleSummoner simpleSummoner) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.partial_summoner, null);
        new LeagueChampionHolder(view).populate(simpleSummoner, (MainActivity) getActivity(), false);

        return view;
    }

    private void challengerLayoutError() {
        mChallengerLayout.showError();
    }
}
