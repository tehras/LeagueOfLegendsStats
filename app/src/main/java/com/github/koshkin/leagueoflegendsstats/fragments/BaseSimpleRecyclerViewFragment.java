package com.github.koshkin.leagueoflegendsstats.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;

/**
 * Created by tehras on 1/13/16.
 * <p/>
 * Simple Recycler View base
 */
@SuppressWarnings("deprecation")
public abstract class BaseSimpleRecyclerViewFragment extends BaseFragment {

    private LinearLayout mExtraLayout;
    private SwipeRefreshLayout mRefreshLayout;

    protected void toggleLayout() {
        if (mExtraLayout.getVisibility() == View.VISIBLE)
            hideExtraLayout();
        else
            showExtraLayout();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mRefreshLayout != null)
            mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addOnSwipeToRefreshListener(SwipeRefreshLayout.OnRefreshListener swipeRefreshLayout) {
        mRefreshLayout.setEnabled(true);
        mRefreshLayout.setColorSchemeColors(this.getResources().getColor(R.color.role_color_adc), this.getResources().getColor(R.color.role_color_jg), this.getResources().getColor(R.color.role_color_mid), this.getResources().getColor(R.color.role_color_top), this.getResources().getColor(R.color.role_color_supp));
        mRefreshLayout.setOnRefreshListener(swipeRefreshLayout);
    }

    @Override
    public void stopRefreshing() {
        if (mRefreshLayout != null)
            mRefreshLayout.setRefreshing(false);
    }

    protected void showExtraLayout() {
        mExtraLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_from_top));
        mExtraLayout.setVisibility(View.VISIBLE);
    }

    protected void hideExtraLayout() {
        mExtraLayout.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranked_champons_layout, container, false);

        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        Utils.setUpRecyclerView(recyclerView, getActivity(), mRefreshLayout);

        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_to_refresh);
        mRefreshLayout.setEnabled(false);

        recyclerView.setAdapter(getAdapter());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = getActivity().getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
                }
            }
        });

        mExtraLayout = (LinearLayout) rootView.findViewById(R.id.extra_layout);
        final View view = populateExtraLayout(inflater);
        if (view != null) {
            mExtraLayout.addView(view);
        }

        mExtraLayout.setVisibility(View.GONE);

        //noinspection deprecation
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int THRESHOLD = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                hideExtraLayout();
                if ((dy) > THRESHOLD) {
                    ((FloatingActionButton) getActivity().findViewById(R.id.fab)).hide();
                } else if ((dy) < -THRESHOLD) {
                    ((FloatingActionButton) getActivity().findViewById(R.id.fab)).show();
                }
            }
        });

        return rootView;
    }

    protected View populateExtraLayout(LayoutInflater inflater) {
        return null;
    }

    public abstract RecyclerView.Adapter getAdapter();
}
