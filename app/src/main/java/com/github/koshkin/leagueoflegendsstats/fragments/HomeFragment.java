package com.github.koshkin.leagueoflegendsstats.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.holders.FeaturedGameHolder;
import com.github.koshkin.leagueoflegendsstats.holders.LeagueChampionHolder;
import com.github.koshkin.leagueoflegendsstats.models.FeaturedGames;
import com.github.koshkin.leagueoflegendsstats.models.LeagueQueueType;
import com.github.koshkin.leagueoflegendsstats.models.LeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.ObservableGame;
import com.github.koshkin.leagueoflegendsstats.models.RankedSummoner;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummoner;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummonerComparator;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.sqlite.FavoritesSqLiteHelper;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;
import com.github.koshkin.leagueoflegendsstats.views.CustomCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by tehras on 1/9/16.
 * <p/>
 * Home layout to pop up
 */
public class HomeFragment extends BaseFragment implements Request.RequestCallback {

    private CustomCardView mChallengerLayout, mFavoriteLayout, mFeaturedGamesLayout;
    private LeagueStandings mLeagueStandings;
    private boolean mFirstLoad = true;
    private FeaturedGames mFeaturedGames;
    private int mCallsToExecute;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EXECUTE
        mCallsToExecute = 2;
        executeGetChallengerStandings(this, LeagueQueueType.RANKED_SOLO_5x5);
        executeGetFeaturedGames(this);
        showLoading();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!mFirstLoad) {
            populateFavoriteLayout();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        mFirstLoad = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);

        initChallengerLayout(view);
        initFavoriteLayout(view);
        initFeaturedGames(view);

        populateFavoriteLayout();
        if (mLeagueStandings != null) {
            populateChallengerLayout();
        } else
            challengerLayoutError();

        if (mFeaturedGames != null) {
            populateObservableGames();
        }

        return view;
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
        return null;
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
                break;
            case GET_SUMMONER_BY_IDS:
                mCallsToExecute--;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mLeagueStandings = (LeagueStandings) response.getReturnedObject();
                    updateChallengerLayout();
                }
                break;
            case GET_FEATURED_MATCHES:
                mCallsToExecute--;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mFeaturedGames = (FeaturedGames) response.getReturnedObject();
                    populateObservableGames();
                }
                break;
        }
        if (mCallsToExecute == 0)
            new Timer().schedule(new MyTimerTask(), 500);
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
            for (ObservableGame game : featuredGames) {
                if (i == 2)
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
