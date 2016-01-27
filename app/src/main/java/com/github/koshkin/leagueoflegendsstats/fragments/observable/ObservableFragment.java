package com.github.koshkin.leagueoflegendsstats.fragments.observable;

import android.support.v7.widget.RecyclerView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.adapters.ObservableGameAdapter;
import com.github.koshkin.leagueoflegendsstats.fragments.BaseSimpleRecyclerViewFragment;
import com.github.koshkin.leagueoflegendsstats.models.ObservableGame;

import java.util.ArrayList;

/**
 * Created by tehras on 1/23/16.
 * <p/>
 * Observable game scrollable view
 */
public class ObservableFragment extends BaseSimpleRecyclerViewFragment {
    private ArrayList<ObservableGame> mObservableGames;

    public static ObservableFragment getInstance(ArrayList<ObservableGame> observableGames) {
        return new ObservableFragment().setObservableGames(observableGames);
    }

    public ObservableFragment setObservableGames(ArrayList<ObservableGame> observableGames) {
        mObservableGames = observableGames;
        return this;
    }

    @Override
    protected String getToolbarTitle() {
        return getActivity().getResources().getString(R.string.fragment_title_featured_games);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new ObservableGameAdapter(mObservableGames, getActivity());
    }
}
