package com.github.koshkin.leagueoflegendsstats.fragments.summoner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.holders.FeaturedGameHolder;
import com.github.koshkin.leagueoflegendsstats.models.FileHandler;
import com.github.koshkin.leagueoflegendsstats.models.ObservableGame;
import com.github.koshkin.leagueoflegendsstats.models.PlayerStatSummaries;
import com.github.koshkin.leagueoflegendsstats.models.PlayerSummary;
import com.github.koshkin.leagueoflegendsstats.models.RecentSummoner;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummoner;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.models.SummonerAggregateObject;
import com.github.koshkin.leagueoflegendsstats.models.SummonerInfo;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.sqlite.RecentSearchSqlLiteHelper;
import com.github.koshkin.leagueoflegendsstats.utils.NumberUtils;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;
import com.github.koshkin.leagueoflegendsstats.views.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;

import static com.github.koshkin.leagueoflegendsstats.utils.Utils.NOT_AVAILABLE;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.addToLayout;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.getRankedStats;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.getTextSafely;


/**
 * Created by tehras on 1/10/16.
 * <p/>
 * This fragment will show the stats
 */
public class SummonerStatsFragment extends BaseFragment implements Request.RequestCallback, SwipeRefreshLayout.OnRefreshListener {

    private SummonerAggregateObject mSummonerAggregateObject;
    private LinearLayout mSelectContainer;
    private View mSummaryLayout;

    private String mSummonerName;
    private FileHandler mProfFileHandler;
    private Summoner mSummoner;
    private String mSummonerId;

    private ObservableGame mObservableGame;
    private View mObservableClickToRetry, mObservableCheckLayout;
    private ViewGroup mObservableGameContainer;
    private ContentLoadingProgressBar mObservableTryLoader, mObservableRetryLoader;
    private int mToExecute;
    private int mSwipeToRefresh;

    public SummonerStatsFragment setSummoner(Summoner summoner) {
        mSummoner = summoner;
        return this;
    }

    public static SummonerStatsFragment getInstance(String summonerName, String summonerId) {
        Bundle args = new Bundle();
        args.putString(ARG_SUMMONER_NAME, summonerName);
        args.putString(ARG_SUMMONER_ID, summonerId);

        SummonerStatsFragment fragment = new SummonerStatsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected String getToolbarTitle() {
        return getActivity().getResources().getString(R.string.fragment_title_summoner_stats);
    }

    @Override
    protected boolean showFab() {
        return true;
    }

    protected String getSummonerName() {
        return mSummonerName;
    }

    protected String getSummonerId() {
        return mSummonerId;
    }

    @Override
    public SimpleSummoner getFavorite() {
        if (mSummonerAggregateObject == null || mSummonerAggregateObject.getSummoner() == null || mSummonerAggregateObject.getPlayerStatSummaries() == null)
            return null;
        return new SimpleSummoner(mSummonerAggregateObject);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_summoner_layout, container, false);

        initializeSummaryElements(rootView);
        initializeSelectLayout(rootView);
        initializeObservableLayout(rootView);

        //to check if some object is true
        populateObservableGameLayout();

        if (mSummonerAggregateObject != null) {
            if (mSummonerAggregateObject.getStatus() == Response.Status.SUCCESS) {
                populateSelectLayout();
                populateSummaryLayout();
            } else if (mSummonerAggregateObject.getStatus() == Response.Status.NOT_FOUND) {
                userNotFound();
            } else {
                generalException();
            }

            hideLoading();
        } else {
            showLoading();
        }
        return rootView;
    }

    private boolean mObservableGameChecked = false;

    private void populateObservableGameLayout() {
        hideLoaders();

        if (mObservableGame != null) {
            mObservableCheckLayout.setVisibility(View.GONE);
            mObservableClickToRetry.setVisibility(View.GONE);
            mObservableGameContainer.setVisibility(View.VISIBLE);

            populateObservableGame(mObservableGameContainer, mObservableGame);
        } else if (mObservableGameChecked) {
            mObservableClickToRetry.setVisibility(View.VISIBLE);
            mObservableCheckLayout.setVisibility(View.GONE);
            mObservableGameContainer.setVisibility(View.GONE);

            mObservableClickToRetry.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
            MaterialButton button = (MaterialButton) mObservableClickToRetry.findViewById(R.id.observable_click_to_retry_button);
            button.setOnButtonClickListener(callToGetObservableGame());
        } else {
            mObservableClickToRetry.setVisibility(View.GONE);
            mObservableCheckLayout.setVisibility(View.VISIBLE);
            mObservableGameContainer.setVisibility(View.GONE);

            mObservableCheckLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
            MaterialButton button = (MaterialButton) mObservableCheckLayout.findViewById(R.id.observable_check_for_game_button);
            button.setOnButtonClickListener(callToGetObservableGame());
        }

    }

    private void hideLoaders() {
        ((View) mObservableRetryLoader.getParent()).setVisibility(View.GONE);
        ((View) mObservableTryLoader.getParent()).setVisibility(View.GONE);
        mObservableRetryLoader.hide();
        mObservableTryLoader.hide();
    }

    private void showTryLoader() {
        ((View) mObservableTryLoader.getParent()).setVisibility(View.VISIBLE);
        mObservableTryLoader.show();
    }

    private void showReTryLoader() {
        ((View) mObservableRetryLoader.getParent()).setVisibility(View.VISIBLE);
        mObservableRetryLoader.show();
    }

    private void populateObservableGame(ViewGroup observableGameContainer, ObservableGame observableGame) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.partial_featured_game, null, false);
        new FeaturedGameHolder(view).setSummonerId(mSummonerAggregateObject.getSummoner().getSummonerInfo().getId()).populate(observableGame, getActivity(), true);

        //remove all views
        observableGameContainer.removeAllViews();
        //add this one and lets animate
        view.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
        observableGameContainer.addView(view);
    }

    private View.OnClickListener callToGetObservableGame() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mObservableGameChecked)
                    showReTryLoader();
                else
                    showTryLoader();
                executeGetObservableGame(SummonerStatsFragment.this, mSummonerAggregateObject.getSummoner().getSummonerId());
            }
        };
    }

    private void initializeObservableLayout(View rootView) {
        mObservableCheckLayout = rootView.findViewById(R.id.observable_click_to_check);
        mObservableClickToRetry = rootView.findViewById(R.id.observable_click_to_retry);
        mObservableGameContainer = (ViewGroup) rootView.findViewById(R.id.observable_game_container);

        mObservableTryLoader = (ContentLoadingProgressBar) rootView.findViewById(R.id.observable_try_loaded);
        mObservableRetryLoader = (ContentLoadingProgressBar) rootView.findViewById(R.id.observable_retry_loaded);
    }

    private void initializeSelectLayout(View rootView) {
        mSelectContainer = (LinearLayout) rootView.findViewById(R.id.match_type_select_container);
    }

    private void populateSelectLayout() {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        if (mSummonerAggregateObject != null && mSummonerAggregateObject.getPlayerStatSummaries().getPlayerSummaries() != null) {
            ArrayList<PlayerSummary> playerSummeries = mSummonerAggregateObject.getPlayerStatSummaries().getPlayerSummaries();
            //Sorts the collection
            Collections.sort(playerSummeries);

            mSelectContainer.removeAllViews();//removes before adding
            for (PlayerSummary playerSummary : playerSummeries) {
                addToLayout(mSelectContainer, getSelectableView(inflater, playerSummary));
            }

            animateContainer(mSelectContainer);

        } else {
            noGameRecordsFound();
        }
    }

    private void animateContainer(LinearLayout selectContainer) {
        if (selectContainer.getChildCount() > 0) {
            for (int i = 0; i < selectContainer.getChildCount(); i++) {
                View child = selectContainer.getChildAt(i);

                Animation slideInAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
                slideInAnim.setDuration((long) (200 + (50 * i)));

                child.startAnimation(slideInAnim);
            }
        }
    }

    private TextView mSummaryName, mSummaryWins, mSummaryLosses, mSummaryWinPercentage;
    private ImageView mSummaryIcon;

    private void initializeSummaryElements(View rootView) {
        mSummaryName = (TextView) rootView.findViewById(R.id.summoner_name);
        mSummaryWins = (TextView) rootView.findViewById(R.id.summoner_wins);
        mSummaryLosses = (TextView) rootView.findViewById(R.id.summoner_losses);
        mSummaryWinPercentage = (TextView) rootView.findViewById(R.id.summoner_win_percentage);
        mSummaryIcon = (ImageView) rootView.findViewById(R.id.summoner_icon);

        mSummaryLayout = rootView.findViewById(R.id.summoner_header_layout);
    }

    public boolean mFromSummonerId = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSummonerName = getArguments().getString(ARG_SUMMONER_NAME, null);
        mSummonerId = getArguments().getString(ARG_SUMMONER_ID, null);

        if (mSummonerAggregateObject == null) {
            if (mSummonerId == null || mSummoner == null)
                executeGetSummoner(this, mSummonerName);
            else {
                addOnSwipeToRefreshListener(this);
                if (mSummoner.getSummonerInfo() != null) {
                    getProfileImage(mSummoner.getSummonerInfo());
                }
                mFromSummonerId = true;
                executeGetStats(this, mSummonerId);
            }
        }
    }

    @Override
    public void finished(@NonNull Response response, @NonNull Request request) {
        if (mSummonerAggregateObject == null)
            mSummonerAggregateObject = new SummonerAggregateObject();
        mSummonerAggregateObject.setStatus(response.getStatus());

        switch (request.getURIHelper()) {
            case GET_PROFILE_URI:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mProfFileHandler = (FileHandler) response.getReturnedObject();

                    populateHeaderProfImage(mSummaryIcon, mProfFileHandler);
                }
                break;
            case GET_SUMMONER:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    Summoner summoner = (Summoner) response.getReturnedObject();

                    if (summoner != null) {
                        mSummonerAggregateObject.setSummoner(summoner);

                        if (summoner.getSummonerInfo() != null) {
                            getProfileImage(summoner.getSummonerInfo());
                        }

                        //enable refresh
                        addOnSwipeToRefreshListener(this);
                        //execute remaining calls
                        executeRemainingCalls(summoner.getSummonerId());
                    } else {
                        generalException();
                    }
                } else if (response.getStatus() == Response.Status.NOT_FOUND) {
                    userNotFound();
                } else {
                    generalException();
                }
                break;
            case GET_OBSERVABLE_GAME:
                mSwipeToRefresh--;
                mToExecute--;
                mObservableGameChecked = true;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mObservableGame = (ObservableGame) response.getReturnedObject();
                }
                populateObservableGameLayout();
                if (mToExecute <= 0)
                    hideLoading();
                break;
            case GET_SUMMONER_SUMMARY:
                mSwipeToRefresh--;
                mToExecute--;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    PlayerStatSummaries playerStatSummaries = (PlayerStatSummaries) response.getReturnedObject();
                    if (playerStatSummaries != null) {
                        if (mFromSummonerId) {
                            mSummonerAggregateObject = new SummonerAggregateObject(mSummoner);
                            mSummonerAggregateObject.setStatus(Response.Status.SUCCESS);
                        }
                        mSummonerAggregateObject.setPlayerStatSummaries(playerStatSummaries);
                        populateSummaryLayout();
                        populateSelectLayout();
                    } else {
                        generalException();
                    }
                } else if (response.getStatus() == Response.Status.NOT_FOUND) {
                    userNotFound();
                } else {
                    generalException();
                }
                if (mToExecute <= 0)
                    hideLoading();
                break;
        }
        addToRecent(mSummonerAggregateObject);
        if (mSwipeToRefresh <= 0)
            stopRefreshing();
    }

    private void executeRemainingCalls(String summonerId) {
        mToExecute = 2;
        executeGetObservableGame(this, summonerId);
        executeGetStats(this, summonerId);
    }

    private void addToRecent(SummonerAggregateObject summoner) {
        if (summoner.getPlayerStatSummaries() != null && summoner.getSummoner() != null) {
            RecentSummoner recentSummoner = new RecentSummoner(summoner);
            RecentSearchSqlLiteHelper.addRecent(recentSummoner);
        }
    }

    private void getProfileImage(SummonerInfo summonerInfo) {
        String profileIconName = StaticDataHolder.getInstance(getActivity()).getProfileIconName(summonerInfo.getProfileIconId());
        executeGetProfileImage(this, profileIconName);
    }

    private void generalException() {
        hideLoading();
        hideHeaderLayout();
        if (getActivity() != null && getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).hideFaveFab();

        initializeErrorLayout(getResources().getString(R.string.general_error_title), getResources().getString(R.string.general_error_body));
    }

    private void noGameRecordsFound() {
        hideLoading();
        hideHeaderLayout();

//        initializeErrorLayout(getResources().getString(R.string.no_record_found_title), getResources().getString(R.string.no_record_found_body));
    }

    private void userNotFound() {
        hideLoading();
        hideHeaderLayout();

//        initializeErrorLayout(getActivity().getResources().getText(R.string.no_user_found_title).toString(), Html.fromHtml(String.format(getActivity().getResources().getText(R.string.no_user_found_message).toString(), mSummonerName)));
    }

    private void populateSummaryLayout() {
        mSummaryName.setText(getSummonerNameFromAggregate());
        if (mSummonerAggregateObject != null && mSummonerAggregateObject.getSummoner() != null && mSummonerAggregateObject.getPlayerStatSummaries() != null) {
            PlayerSummary summary = getRankedStats(mSummonerAggregateObject.getPlayerStatSummaries().getPlayerSummaries());
            if (summary != null) {
                mSummaryWins.setText(getTextSafely(summary.getWins()));
                mSummaryLosses.setText(getTextSafely(summary.getLosses()));
                mSummaryWinPercentage.setText(getWinPercentage(summary));

                if (mSummonerAggregateObject.getSummoner() != null && mSummonerAggregateObject.getSummoner().getSummonerInfo() != null) {
                    populateHeaderProfImage(mSummaryIcon, mProfFileHandler, mSummonerAggregateObject.getSummoner().getSummonerInfo().getProfileIconId());
                }

                return;
            }
        }
        mSummaryWins.setText(NOT_AVAILABLE);
        mSummaryLosses.setText(NOT_AVAILABLE);
        mSummaryWinPercentage.setText(NOT_AVAILABLE);

        mSummaryLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_top));
    }

    private String getWinPercentage(@NonNull PlayerSummary summary) {
        int wins = summary.getWins();
        int losses = summary.getLosses();

        if (wins != -1 && losses != -1 && wins != 0 && losses != 0) {
            return NumberUtils.twoDecimals((double) (wins * 100) / (wins + losses)) + "%";
        }
        return NOT_AVAILABLE;
    }

    public String getSummonerNameFromAggregate() {
        if (mSummonerAggregateObject == null)
            return "";

        Summoner playerSummary = mSummonerAggregateObject.getSummoner();
        if (playerSummary != null && playerSummary.getSummonerInfo() != null)
            return playerSummary.getSummonerInfo().getName();

        return "";
    }

    @SuppressLint("SetTextI18n")
    public View getSelectableView(LayoutInflater inflater, final PlayerSummary playerSummary) {
        if (playerSummary.getSummaryType() == null || (playerSummary.getWins() == 0 && playerSummary.getLosses() == 0))
            return null;

        @SuppressLint("InflateParams") View rootView = inflater.inflate(R.layout.partial_select_game_type_layout, null);

        TextView title = (TextView) rootView.findViewById(R.id.select_game_type_title);
        title.setText(playerSummary.getSummaryType().getName());

        TextView wins = (TextView) rootView.findViewById(R.id.select_wins);
        TextView hyphen = (TextView) rootView.findViewById(R.id.select_hyphen);
        TextView losses = (TextView) rootView.findViewById(R.id.select_losses);

        if (playerSummary.getLosses() == -1) {
            hyphen.setVisibility(View.GONE);
            wins.setText(Utils.getTextSafely(playerSummary.getWins()) + " Wins");
            losses.setVisibility(View.GONE);
        } else {
            wins.setText(Utils.getTextSafely(playerSummary.getWins()) + " Wins");
            losses.setText(Utils.getTextSafely(playerSummary.getLosses()) + " Losses");
        }

        ImageView carat = (ImageView) rootView.findViewById(R.id.select_game_right_icon);
        View clickableView = rootView.findViewById(R.id.select_clickable);

        switch (playerSummary.getSummaryType()) {
            case RANKED_SOLO_5X:
                carat.setVisibility(View.VISIBLE);
                clickableView.setOnClickListener(getOnClickListener(playerSummary.getWins(), playerSummary.getLosses()));
                break;
            default:
                carat.setVisibility(View.GONE);
                break;
        }

        return rootView;
    }

    private void hideHeaderLayout() {
        mSummaryLayout.setVisibility(View.GONE);
    }

    public View.OnClickListener getOnClickListener(final int wins, final int losses) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSummonerAggregateObject != null && mSummonerAggregateObject.getSummoner() != null && mSummonerAggregateObject.getSummoner().getSummonerInfo() != null) {
                    String summonerId = mSummonerAggregateObject.getSummoner().getSummonerId();
                    int summonerIconId;
                    summonerIconId = mSummonerAggregateObject.getSummoner().getSummonerInfo().getProfileIconId();
                    String summonerName = mSummonerAggregateObject.getSummoner().getSummonerInfo().getName();

                    ((MainActivity) getActivity()).startFragment(SummonerRankedStatsFragment.getInstance(summonerIconId, summonerId, summonerName, Utils.getTextSafely(wins), Utils.getTextSafely(losses)));
                }
            }
        };
    }

    @Override
    public void onRefresh() {
        mSwipeToRefresh = 2;
        executeRemainingCalls(mSummonerAggregateObject.getSummoner().getSummonerId());
    }
}
