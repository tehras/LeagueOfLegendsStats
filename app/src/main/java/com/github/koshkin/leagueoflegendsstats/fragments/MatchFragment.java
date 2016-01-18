package com.github.koshkin.leagueoflegendsstats.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.match.FullMatch;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.match.MiddleIndividualLayoutHolder;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.match.TopLayoutViewHolder;

/**
 * Created by tehras on 1/17/16.
 */
public class MatchFragment extends BaseFragment implements Request.RequestCallback {

    private View mHeaderLayout, mMiddleLayout, mOverallLayout;

    public static MatchFragment getInstance(String summonerId, String matchId) {
        MatchFragment fragment = new MatchFragment();

        Bundle args = new Bundle();
        args.putString(ARG_SUMMONER_ID, summonerId);
        args.putString(ARG_MATCH_ID, matchId);

        fragment.setArguments(args);
        return fragment;
    }

    private String mSummonerId;
    private FullMatch mFullMatch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSummonerId = getArguments().getString(ARG_SUMMONER_ID);
        String matchId = getArguments().getString(ARG_MATCH_ID);

        if (mFullMatch == null) {
            showLoading();
            executeGetMatchData(this, matchId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match_record_layout, container, false);

        initializeHeaderAndMiddleLayout(rootView);

        if (mFullMatch != null)
            populateTopAndMiddleLayouts();

        return rootView;
    }

    private void initializeHeaderAndMiddleLayout(View rootView) {
        mHeaderLayout = rootView.findViewById(R.id.match_header_layout);
        mMiddleLayout = rootView.findViewById(R.id.match_middle_individual);
        mOverallLayout = rootView.findViewById(R.id.match_overall_layout);
    }

    private void populateTopAndMiddleLayouts() {
        hideErrorLayout();
        mOverallLayout.setVisibility(View.VISIBLE);

        TopLayoutViewHolder topLayoutViewHolder = new TopLayoutViewHolder(mHeaderLayout);
        MiddleIndividualLayoutHolder middleIndividualLayoutHolder = new MiddleIndividualLayoutHolder(mMiddleLayout);

        topLayoutViewHolder.populate(mFullMatch, getActivity(), mSummonerId);
        middleIndividualLayoutHolder.populate(getActivity(), mFullMatch, mSummonerId);
    }

    private void populateError() {
        initializeErrorLayout("Sorry..", "Unable to retrieve data");
        mOverallLayout.setVisibility(View.GONE);
    }

    @Override
    public void finished(Response response, Request request) {
        switch (request.getURIHelper()) {
            case GET_MATCH_STATS:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    mFullMatch = (FullMatch) response.getReturnedObject();
                    populateTopAndMiddleLayouts();
                } else {
                    populateError();
                }
                hideLoading();
                break;
        }
    }
}