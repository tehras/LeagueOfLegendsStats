package com.github.koshkin.leagueoflegendsstats;

import android.app.Fragment;
import android.os.Bundle;

import com.github.koshkin.leagueoflegendsstats.models.PlayerRanked;
import com.github.koshkin.leagueoflegendsstats.models.PlayerStatSummaries;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.networking.Executor;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.URIHelper;

/**
 * Created by tehras on 1/9/16.
 * <p/>
 * Class is to create general
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideErrorLayout();
    }

    protected static final String ARG_SUMMONER_NAME = "summoner_name";
    protected static final String ARG_SUMMONER_ID = "summoner_id";
    protected static final String ARG_SUMMONER_ICON_ID = "summoner_icon_id";
    protected static final String ARG_SUMMONER_WINS = "ranked_wins";
    protected static final String ARG_SUMMONER_LOSSES = "ranked_losses";

    protected void executeGetSummoner(Request.RequestCallback requestCallback, String summonerName) {
        new Executor(new Request(Request.RequestType.GET, new Summoner(summonerName), requestCallback, URIHelper.GET_SUMMONER, summonerName)).execute();
    }

    protected void executeGetStats(Request.RequestCallback requestCallback, Summoner summoner) {
        new Executor(new Request(Request.RequestType.GET, new PlayerStatSummaries(), requestCallback, URIHelper.GET_SUMMONER_SUMMARY, summoner.getSummonerId())).execute();
    }

    protected void executeGetRankedStats(Request.RequestCallback requestCallback, String summonerId) {
        new Executor(new Request(Request.RequestType.GET, new PlayerRanked(), requestCallback, URIHelper.GET_SUMMONER_RANKED, summonerId)).execute();
    }

    protected void initializeErrorLayout(CharSequence title, CharSequence body) {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).initializeError(title, body);
        }
    }

    protected void hideErrorLayout() {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideError();
        }
    }

    protected void showLoading() {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showLoading();
        }
    }

    protected void hideLoading() {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideLoading();
        }
    }

}
