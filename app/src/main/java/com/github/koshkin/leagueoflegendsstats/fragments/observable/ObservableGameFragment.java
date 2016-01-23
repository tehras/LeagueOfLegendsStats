package com.github.koshkin.leagueoflegendsstats.fragments.observable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.holders.observable.TopLayoutViewHolder;
import com.github.koshkin.leagueoflegendsstats.models.ObservableGame;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;

/**
 * Created by tehras on 1/23/16.
 * <p/>
 * Observable Game Fragment
 */
public class ObservableGameFragment extends BaseFragment implements Request.RequestCallback {
    private View mTopLayout, mMiddleLayout;

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
    private String mSummonerName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSummonerId = getArguments().getString(ARG_SUMMONER_ID);
        mSummonerName = getArguments().getString(ARG_SUMMONER_NAME);

        if (!NullChecker.isNullOrEmpty(mSummonerId))
            executeObservableGame();
        else
            executeGetSummoner(this, mSummonerName);

        showLoading();
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

        if (mObservableGame != null) {
            populateObservableGameLayout();
        }

        return rootView;
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
                        showError();
                    }
                } else {
                    showError();
                }
                break;
            case GET_OBSERVABLE_GAME:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mObservableGame = (ObservableGame) response.getReturnedObject();
                    populateObservableGameLayout();
                } else {
                    showError();
                }

        }
    }

    private void populateObservableGameLayout() {
        hideLoading();
        new TopLayoutViewHolder(mTopLayout).populate(mObservableGame, getActivity());
    }

    private void showError() {
        hideLoading();
        initializeErrorLayout("Sorry", "Game data could not be retrieved");
    }
}
