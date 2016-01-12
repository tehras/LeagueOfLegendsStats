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
import com.github.koshkin.leagueoflegendsstats.adapters.ChampionAdapter;
import com.github.koshkin.leagueoflegendsstats.models.Champion;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;

import java.util.ArrayList;

/**
 * Created by tehras on 1/11/16.
 * <p/>
 * List of ALL STATS
 */
public class SummonerRankedChampionsFragment extends BaseFragment {

    private ArrayList<Champion> mChampions;

    public void setChampions(ArrayList<Champion> champions) {
        mChampions = champions;
    }

    public static SummonerRankedChampionsFragment getInstance(ArrayList<Champion> champions) {
        SummonerRankedChampionsFragment fragment = new SummonerRankedChampionsFragment();
        fragment.setChampions(champions);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranked_champons_layout, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        Utils.setUpRecyclerView(recyclerView);

        recyclerView.setAdapter(new ChampionAdapter(mChampions, getActivity()));
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
}
