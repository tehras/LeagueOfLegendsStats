package com.github.koshkin.leagueoflegendsstats;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.github.koshkin.leagueoflegendsstats.models.FeaturedGames;
import com.github.koshkin.leagueoflegendsstats.models.FileHandler;
import com.github.koshkin.leagueoflegendsstats.models.LeagueQueueType;
import com.github.koshkin.leagueoflegendsstats.models.LeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.ObservableGame;
import com.github.koshkin.leagueoflegendsstats.models.PlayerRanked;
import com.github.koshkin.leagueoflegendsstats.models.PlayerStatSummaries;
import com.github.koshkin.leagueoflegendsstats.models.RecentGames;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummoner;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.models.SummonerLeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.match.FullMatch;
import com.github.koshkin.leagueoflegendsstats.networking.Executor;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.URIHelper;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.FloatingFavoriteActionButtonHelper;

import java.util.ArrayList;

/**
 * Created by tehras on 1/9/16.
 * <p/>
 * Class is to create general
 */
public class BaseFragment extends android.support.v4.app.Fragment implements FloatingFavoriteActionButtonHelper.FavoriteCallback {

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //leave blank by defualt
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideErrorLayout();
    }

    /**
     * Decided to wait till onViewCreated because onCreate way TOO early
     *
     * @param view               view
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null && getActivity() instanceof MainActivity) {
            Toolbar toolbar = ((MainActivity) getActivity()).getToolbar();
            setToolbar(toolbar);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //hide errors and show/fab
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showFab();
            ((MainActivity) getActivity()).hideError();
            if (showFab() && isNotMySummoner())
                ((MainActivity) getActivity()).showFaveFab(this, getSummonerName());
            else
                ((MainActivity) getActivity()).hideFaveFab();
        }

        if (mSwipeListener != null) //this is to make sure add on swipe listener gets added back
            addOnSwipeToRefreshListener(mSwipeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).scrollToTop();
            if (getParentFragment() == null || !(getParentFragment() instanceof BaseFragment))
                ((MainActivity) getActivity()).removeSwipeToRefreshListener();
        }
    }

    private SwipeRefreshLayout.OnRefreshListener mSwipeListener = null;

    /**
     * put it in onResume or else it won't work correctly
     *
     * @param swipeRefreshLayout OnRefreshListener
     */
    public void addOnSwipeToRefreshListener(SwipeRefreshLayout.OnRefreshListener swipeRefreshLayout) {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).addSwipeToRefreshListener(swipeRefreshLayout);
            mSwipeListener = swipeRefreshLayout;
        }
    }

    public void stopRefreshing() {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).stopRefreshing();
        }
    }

    protected String getSummonerName() {
        if (getParentFragment() != null && getParentFragment() instanceof BaseFragment)
            return ((BaseFragment) getParentFragment()).getSummonerName();
        return null;
    }

    protected boolean showFab() {
        return getParentFragment() != null && getParentFragment() instanceof BaseFragment && ((BaseFragment) getParentFragment()).showFab();
    }

    protected static final String ARG_SUMMONER_NAME = "summoner_name";
    protected static final String ARG_SUMMONER_ID = "summoner_id";
    protected static final String ARG_MATCH_ID = "match_id";
    protected static final String ARG_MATCH_TIME = "match_time";
    protected static final String ARG_MATCH_LENGTH = "match_length";
    protected static final String ARG_SUMMONER_ICON_ID = "summoner_icon_id";

    protected void executeGetSummoner(Request.RequestCallback requestCallback, String summonerName) {
        new Executor(new Request(getActivity(), Request.RequestType.GET, new Summoner(summonerName.toLowerCase()), requestCallback, URIHelper.GET_SUMMONER, summonerName), getActivity()).execute();
    }

    protected void executeGetSummonersById(Request.RequestCallback requestCallback, ArrayList<String> summonerIds, LeagueStandings leagueStandings) {
        new Executor(new Request(getActivity(), Request.RequestType.GET, leagueStandings, requestCallback, URIHelper.GET_SUMMONER_BY_IDS, commaSeparatedSummonerIds(summonerIds)), getActivity()).execute();
    }

    private String commaSeparatedSummonerIds(ArrayList<String> summonerIds) {
        String s = "" + summonerIds.get(0);
        if (summonerIds.size() == 1)
            return s;

        for (int i = 1; i < summonerIds.size(); i++) {
            s = s + "," + summonerIds.get(i);
        }

        return s;
    }

    protected void executeGetStats(Request.RequestCallback requestCallback, String summonerId) {
        new Executor(new Request(getActivity(), Request.RequestType.GET, new PlayerStatSummaries(), requestCallback, URIHelper.GET_SUMMONER_SUMMARY, summonerId), getActivity()).execute();
    }

    protected void executeGetRankedStats(Request.RequestCallback requestCallback, String summonerId) {
        new Executor(new Request(getActivity(), Request.RequestType.GET, new PlayerRanked(), requestCallback, URIHelper.GET_SUMMONER_RANKED, summonerId), getActivity()).execute();
    }

    protected void executeGetChallengerStandings(Request.RequestCallback requestCallback, LeagueQueueType type) {
        new Executor(new Request(getActivity(), Request.RequestType.GET, new LeagueStandings(), requestCallback, URIHelper.GET_CHALLENGER, type.name()), getActivity()).execute();
    }

    protected void executeGetMasterStandings(Request.RequestCallback requestCallback, LeagueQueueType type) {
        new Executor(new Request(getActivity(), Request.RequestType.GET, new LeagueStandings(), requestCallback, URIHelper.GET_MASTER, type.name()), getActivity()).execute();
    }

    protected void executeGetRankeGameHistory(Request.RequestCallback requestCallback, String summonerId) {
        new Executor(new Request(getActivity(), Request.RequestType.GET, new RecentGames(), requestCallback, URIHelper.GET_SUMMONER_RANKED_GAMES, summonerId), getActivity()).execute();
    }

    protected void executeGetProfileImage(Request.RequestCallback requestCallback, String imageName) {
        new Executor(new Request(getActivity(), Request.RequestType.GET_IMAGE, new RecentGames(), requestCallback, URIHelper.GET_PROFILE_URI, imageName).addExtraParam(Request.KEY_IMAGE_NAME, imageName), getActivity()).execute();
    }

    protected void executeGetMatchData(Request.RequestCallback requestCallback, String matchId) {
        new Executor(new Request(getActivity(), Request.RequestType.GET, new FullMatch(), requestCallback, URIHelper.GET_MATCH_STATS, matchId), getActivity()).execute();
    }

    protected void executeGetFeaturedGames(Request.RequestCallback requestCallback) {
        new Executor(new Request(getActivity(), Request.RequestType.GET, new FeaturedGames(), requestCallback, URIHelper.GET_FEATURED_MATCHES), getActivity()).execute();
    }

    protected void executeGetObservableGame(Request.RequestCallback requestCallback, String summonerId) {
        new Executor(new Request(getActivity(), Request.RequestType.GET, new ObservableGame(), requestCallback, URIHelper.GET_OBSERVABLE_GAME, URIHelper.PlatformId.getByRegion(URIHelper.getCurrentRegion()).toString(), summonerId), getActivity()).execute();
    }

    protected void executeGetLeagueBySummonerIds(Request.RequestCallback requestCallback, String summonerIds) {
        new Executor(new Request(getActivity(), Request.RequestType.GET, new SummonerLeagueStandings(), requestCallback, URIHelper.GET_LEAGUE_BY_SUMMONERS, summonerIds), getActivity()).execute();
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


    @Override
    public SimpleSummoner getFavorite() {
        if (getParentFragment() != null && getParentFragment() instanceof BaseFragment)
            return ((BaseFragment) getParentFragment()).getFavorite();
        return null;
    }

    public boolean isNotMySummoner() {
        Summoner mySummoner = StaticDataHolder.getInstance(getActivity()).getMySummoner();

        return mySummoner == null || !getSummonerName().equalsIgnoreCase(mySummoner.getSummonerInfo().getName());
    }

    protected void showKeyboard() {
        if (this.getActivity() == null)
            return;

        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    protected void hideKeyboard() {
        if (this.getActivity() == null)
            return;

        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void setToolbar(Toolbar toolbar) {
        toolbar.setTitle(getToolbarTitle());
    }

    /**
     * Override this method in order to change the toolbar name.
     * <p/>
     * By default it'll be the app name
     *
     * @return toolbar name
     */
    public String getToolbarTitle() {
        if (getParentFragment() != null && getParentFragment() instanceof BaseFragment)
            return ((BaseFragment) getParentFragment()).getToolbarTitle();
        else
            return getActivity().getResources().getString(R.string.app_name);
    }
}
