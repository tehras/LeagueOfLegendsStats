package com.github.koshkin.leagueoflegendsstats.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;

/**
 * Created by tehras on 1/13/16.
 */
public abstract class BaseSimpleRecyclerViewFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranked_champons_layout, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        Utils.setUpRecyclerView(recyclerView);

        recyclerView.setAdapter(getAdapter());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = getActivity().getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
                }
            }
        });

        //noinspection deprecation
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int THRESHOLD = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if ((dy) > THRESHOLD) {
                    ((FloatingActionButton) getActivity().findViewById(R.id.fab)).hide();
                } else if ((dy) < -THRESHOLD) {
                    ((FloatingActionButton) getActivity().findViewById(R.id.fab)).show();
                }
            }
        });

        return rootView;
    }

    public abstract RecyclerView.Adapter getAdapter();
}
