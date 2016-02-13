package com.github.koshkin.leagueoflegendsstats.fragments.stats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.fragments.observable.ObservableGameFragment;
import com.github.koshkin.leagueoflegendsstats.fragments.summoner.SummonerRankedStatsFragment;
import com.github.koshkin.leagueoflegendsstats.fragments.summoner.SummonerStatsFragment;
import com.github.koshkin.leagueoflegendsstats.models.PlayerStatSummaries;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummoner;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.models.SummonerAggregateObject;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.sqlite.FavoritesSqLiteHelper;

/**
 * Created by tehras on 2/13/16.
 * <p/>
 * Tabbed layout
 */
public class StatsTabbedFragment extends BaseFragment implements Request.RequestCallback, SwipeRefreshLayout.OnRefreshListener {

    private String mSummonerName, mSummonerId;
    private Summoner mSummoner;
    private ViewPager mViewPager;
    private TabLayout mTabStrip;
    private SummonerAggregateObject mSummonerAggregateObject;
    private SummonerStatsFragment mStatsFragment;
    private ObservableGameFragment mLiveMatchFragment;
    private SummonerRankedStatsFragment mRankedFragment;

    @Override
    public String getToolbarTitle() {
        return "Summoner - " + mSummonerName;
    }

    @Override
    public SimpleSummoner getFavorite() {
        if (mSummonerAggregateObject == null || mSummonerAggregateObject.getSummoner() == null || mSummonerAggregateObject.getPlayerStatSummaries() == null)
            return null;
        return new SimpleSummoner(mSummonerAggregateObject);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSummonerName = getArguments().getString(ARG_SUMMONER_NAME, null);
        mSummonerId = getArguments().getString(ARG_SUMMONER_ID, null);

        executeGetSummoner(this, mSummonerName);
    }

    public static StatsTabbedFragment getInstance(String summonerName, String summonerId) {
        Bundle args = new Bundle();
        args.putString(ARG_SUMMONER_NAME, summonerName);
        args.putString(ARG_SUMMONER_ID, summonerId);

        StatsTabbedFragment fragment = new StatsTabbedFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getSummonerName() {
        return mSummonerName;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed_layout, container, false);

        initializeAndSetupTabbedLayoutAndViewPager(rootView);

        if (mSummonerAggregateObject != null)
            setUpTabLayout();

        return rootView;
    }

    @Override
    protected boolean showFab() {
        return true;
    }

    private void initializeAndSetupTabbedLayoutAndViewPager(View rootView) {
        mViewPager = (ViewPager) rootView.findViewById(R.id.stats_view_pager);
        mTabStrip = (TabLayout) rootView.findViewById(R.id.stats_tabbed_layout);
    }

    private void setUpTabLayout() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            private String tabTitles[] = new String[]{"General", "Ranked", "Live Match"};

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return mStatsFragment = SummonerStatsFragment.getInstance(mSummonerName, mSummonerId, mSummoner, mSummonerAggregateObject);
                    case 1:
                        return mRankedFragment = SummonerRankedStatsFragment.getInstance(mSummoner.getSummonerInfo().getProfileIconId(), mSummoner.getSummonerId(), mSummonerName);
                    default:
                        return mLiveMatchFragment = ObservableGameFragment.getInstance(mSummonerId, mSummonerName);
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }
        });
        mTabStrip.setupWithViewPager(mViewPager);

        //adds refresh
        addOnSwipeToRefreshListener(this);
        mTabStrip.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                stopRefreshing();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public StatsTabbedFragment setSummoner(Summoner summoner) {
        mSummoner = summoner;
        return this;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).setToolbarShadow(false);
    }

    @Override
    public void onPause() {
        super.onPause();

        ((MainActivity) getActivity()).setToolbarShadow(true);
    }

    @Override
    public void finished(Response response, Request request) {
        switch (request.getURIHelper()) {
            case GET_SUMMONER:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mSummoner = (Summoner) response.getReturnedObject();
                    mSummonerId = mSummoner.getSummonerId();
                    executeGetStats(this, mSummoner.getSummonerId());
                } else if (response.getStatus() == Response.Status.NOT_FOUND) {
                    userNotFound();
                } else {
                    generalException();
                }
                break;
            case GET_SUMMONER_SUMMARY:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    PlayerStatSummaries playerStatSummaries = (PlayerStatSummaries) response.getReturnedObject();
                    if (playerStatSummaries != null) {
                        mSummonerAggregateObject = new SummonerAggregateObject(mSummoner);
                        mSummonerAggregateObject.setPlayerStatSummaries(playerStatSummaries);

                        updateFavorites();
                        setUpTabLayout();
                    } else {
                        generalException();
                    }
                } else if (response.getStatus() == Response.Status.NOT_FOUND) {
                    userNotFound();
                } else {
                    generalException();
                }
                break;
        }
    }

    /**
     * This method will update the favorites if already exists
     */
    private void updateFavorites() {
        FavoritesSqLiteHelper.updateFavoriteIfAlreadyExists(new SimpleSummoner(mSummonerAggregateObject));
    }

    private void userNotFound() {
        hideLoading();
        hideHeaderLayout();

        initializeErrorLayout(getActivity().getResources().getText(R.string.no_user_found_title).toString(), Html.fromHtml(String.format(getActivity().getResources().getText(R.string.no_user_found_message).toString(), mSummonerName)));
    }

    private void hideHeaderLayout() {
        mTabStrip.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
    }

    private void generalException() {
        hideLoading();
        hideHeaderLayout();
        if (getActivity() != null && getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).hideFaveFab();

        initializeErrorLayout(getResources().getString(R.string.general_error_title), getResources().getString(R.string.general_error_body));
    }

    @Override
    public void onRefresh() {
        switch (mTabStrip.getSelectedTabPosition()) {
            case 0:
                if (mStatsFragment != null) mStatsFragment.onRefresh();
                break;
            case 1:
                if (mRankedFragment != null) mRankedFragment.onRefresh();
                break;
            case 2:
                if (mLiveMatchFragment != null) mLiveMatchFragment.onRefresh();
                break;
        }
    }
}
