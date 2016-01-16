package com.github.koshkin.leagueoflegendsstats;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.github.koshkin.leagueoflegendsstats.models.FileHandler;
import com.github.koshkin.leagueoflegendsstats.models.LeagueQueueType;
import com.github.koshkin.leagueoflegendsstats.models.LeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.MatchHistory;
import com.github.koshkin.leagueoflegendsstats.models.PlayerRanked;
import com.github.koshkin.leagueoflegendsstats.models.PlayerStatSummaries;
import com.github.koshkin.leagueoflegendsstats.models.RecentGames;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.networking.Executor;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.URIConstants;
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

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showFab();
        }
    }

    protected static final String ARG_SUMMONER_NAME = "summoner_name";
    protected static final String ARG_SUMMONER_ID = "summoner_id";
    protected static final String ARG_SUMMONER_ICON_ID = "summoner_icon_id";
    protected static final String ARG_SUMMONER_WINS = "ranked_wins";
    protected static final String ARG_SUMMONER_LOSSES = "ranked_losses";

    protected void executeGetSummoner(Request.RequestCallback requestCallback, String summonerName) {
        new Executor(new Request(Request.RequestType.GET, new Summoner(summonerName), requestCallback, URIHelper.GET_SUMMONER, summonerName), getActivity()).execute();
    }

    protected void executeGetStats(Request.RequestCallback requestCallback, Summoner summoner) {
        new Executor(new Request(Request.RequestType.GET, new PlayerStatSummaries(), requestCallback, URIHelper.GET_SUMMONER_SUMMARY, summoner.getSummonerId()), getActivity()).execute();
    }

    protected void executeGetRankedStats(Request.RequestCallback requestCallback, String summonerId) {
        new Executor(new Request(Request.RequestType.GET, new PlayerRanked(), requestCallback, URIHelper.GET_SUMMONER_RANKED, summonerId), getActivity()).execute();
    }

    protected void executeGetChallengerStandings(Request.RequestCallback requestCallback, LeagueQueueType type) {
        new Executor(new Request(Request.RequestType.GET, new LeagueStandings(), requestCallback, URIHelper.GET_CHALLENGER, type.name()), getActivity()).execute();
    }

    protected void executeGetRankedHistory5v5(Request.RequestCallback requestCallback, String summonerId) {
        new Executor(new Request(Request.RequestType.GET, new MatchHistory(), requestCallback, URIHelper.GET_SUMMONER_RANKED_HISTORY_5V5, summonerId, URIConstants.PARAM_RANKED_SOLO_5V5), getActivity()).execute();
    }

    protected void executeGetRankeGameHistory(Request.RequestCallback requestCallback, String summonerId) {
        new Executor(new Request(Request.RequestType.GET, new RecentGames(), requestCallback, URIHelper.GET_SUMMONER_RANKED_GAMES, summonerId), getActivity()).execute();
    }

    protected void executeGetProfileImage(Request.RequestCallback requestCallback, String imageName) {
        new Executor(new Request(Request.RequestType.GET_IMAGE, new RecentGames(), requestCallback, URIHelper.GET_PROFILE_URI, imageName).addExtraParam(Request.KEY_IMAGE_NAME, imageName), getActivity()).execute();
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

    protected void populateHeaderProfImage(ImageView summonerIcon, FileHandler fileHandler) {
        Drawable drawable = StaticDataHolder.getInstance(getActivity()).getProfileIcon(fileHandler.getBitmapFromFile());

        if (drawable != null)
            summonerIcon.setImageDrawable(drawable);
    }

    protected void populateHeaderProfImage(ImageView summonerIcon, FileHandler fileHandler, int summonerIconId) {
        if (fileHandler == null) {
            Drawable drawable = StaticDataHolder.getInstance(getActivity()).getProfileIcon(summonerIconId);
            if (drawable != null)
                summonerIcon.setImageDrawable(drawable);
        } else {
            populateHeaderProfImage(summonerIcon, fileHandler);
        }
    }


}
