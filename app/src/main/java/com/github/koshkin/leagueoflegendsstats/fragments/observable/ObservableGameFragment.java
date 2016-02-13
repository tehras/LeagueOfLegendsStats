package com.github.koshkin.leagueoflegendsstats.fragments.observable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.holders.observable.MainLayoutViewHolder;
import com.github.koshkin.leagueoflegendsstats.holders.observable.TopLayoutViewHolder;
import com.github.koshkin.leagueoflegendsstats.models.ObservableGame;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.models.SummonerLeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.match.Participant;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;

/**
 * Created by tehras on 1/23/16.
 * <p/>
 * Observable Game Fragment
 */
public class ObservableGameFragment extends BaseFragment implements Request.RequestCallback, SwipeRefreshLayout.OnRefreshListener {
    private View mTopLayout, mMiddleLayout;
    private SummonerLeagueStandings mSummonerLeagues;
    private TopLayoutViewHolder mTopLayoutHolder;
    private View mObservableCheckLayout;

    /**
     * You can pass either
     *
     * @param summonerId   summonerId to directly get the game
     * @param summonerName will have to make 2 calls, one to get the summonerId, then get the game
     * @return This Fragment
     */
    public static ObservableGameFragment getInstance(String summonerId, String summonerName) {
        Bundle args = new Bundle();
        args.putString(ARG_SUMMONER_ID, summonerId);
        args.putString(ARG_SUMMONER_NAME, summonerName);

        ObservableGameFragment fragment = new ObservableGameFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private ObservableGame mObservableGame;
    private String mSummonerId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSummonerId = getArguments().getString(ARG_SUMMONER_ID);
        String summonerName = getArguments().getString(ARG_SUMMONER_NAME);

        if (!NullChecker.isNullOrEmpty(mSummonerId))
            executeObservableGame();
        else
            executeGetSummoner(this, summonerName);
    }

    @Override
    public String getToolbarTitle() {
        if (getParentFragment() == null || !(getParentFragment() instanceof BaseFragment))
            return getActivity().getResources().getString(R.string.fragment_title_observable_game);
        return ((BaseFragment) getParentFragment()).getToolbarTitle();
    }

    private void executeObservableGame() {
        executeGetObservableGame(this, mSummonerId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_observable_game_layout, container, false);

        initializeTopView(rootView);
        initializeMainView(rootView);

        initializeErrorScenario(rootView);

        if (mObservableGame != null)
            populateObservableGameLayout();
        else {
            mTopLayout.setVisibility(View.GONE);
            mMiddleLayout.setVisibility(View.GONE);
            showNoGamesFound();
        }

        return rootView;
    }


    private void initializeErrorScenario(View rootView) {
        mObservableCheckLayout = rootView.findViewById(R.id.observable_check_for_game_button);
    }

    private void initializeMainView(View rootView) {
        mMiddleLayout = rootView.findViewById(R.id.observable_game_main_layout);
    }

    private void initializeTopView(View rootView) {
        mTopLayout = rootView.findViewById(R.id.observable_game_top_layout);
    }

    @Override
    public void finished(Response response, Request request) {
        switch (request.getURIHelper()) {
            case GET_SUMMONER:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    Summoner summoner = (Summoner) response.getReturnedObject();
                    if (!NullChecker.isNullOrEmpty(summoner.getSummonerId())) {
                        mSummonerId = summoner.getSummonerId();
                        executeObservableGame();
                    } else {
                        showNoGamesFound();
                    }
                } else {
                    showNoGamesFound();
                }
                break;
            case GET_OBSERVABLE_GAME:
                stopRefreshing();

                if (response.getStatus() == Response.Status.SUCCESS) {
                    mObservableGame = (ObservableGame) response.getReturnedObject();
                    String participantIds = getSummonerIds(mObservableGame);
                    if (!NullChecker.isNullOrEmpty(participantIds))
                        executeGetLeagueBySummonerIds(this, getSummonerIds(mObservableGame));
                    else
                        populateObservableGameLayout();
                } else {
                    showNoGamesFound();
                }
                break;
            case GET_LEAGUE_BY_SUMMONERS:
                if (response.getStatus() == Response.Status.SUCCESS)
                    mSummonerLeagues = (SummonerLeagueStandings) response.getReturnedObject();

                populateObservableGameLayout();
                break;
        }
    }

    private void showNoGamesFound() {
        hideLoading();
        mTopLayout.setVisibility(View.GONE);
        mMiddleLayout.setVisibility(View.GONE);

        mObservableCheckLayout.setVisibility(View.VISIBLE);
    }

    private String getSummonerIds(ObservableGame observableGame) {
        if (observableGame != null && observableGame.getParticipants() != null && observableGame.getParticipants().size() > 0) {
            String summonerIds = "";
            for (int i = 0; i < observableGame.getParticipants().size(); i++) {
                Participant participant = observableGame.getParticipants().get(i);

                summonerIds = summonerIds + (i != 0 ? "," : "") + participant.getSummonerId();
            }

            return summonerIds;
        } else {
            return null;
        }
    }

    private void populateObservableGameLayout() {
        hideLoading();
        if (mTopLayoutHolder == null)
            mTopLayoutHolder = new TopLayoutViewHolder();

        mObservableCheckLayout.setVisibility(View.GONE);
        mTopLayout.setVisibility(View.VISIBLE);
        mMiddleLayout.setVisibility(View.VISIBLE);

        mTopLayoutHolder.init(mTopLayout).populate(mObservableGame, getActivity());
        new MainLayoutViewHolder(mMiddleLayout).setSummonerId(mSummonerId).populate(mObservableGame, mSummonerLeagues, getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mTopLayoutHolder != null)
            mTopLayoutHolder.stopTimer();
    }

    @Override
    public void onRefresh() {
        executeGetObservableGame(ObservableGameFragment.this, mSummonerId);
    }
}
