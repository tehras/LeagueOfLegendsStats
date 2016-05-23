package com.github.koshkin.leagueoflegendsstats.fragments.match;

import android.view.LayoutInflater;
import android.view.View;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.fragments.BaseBottomSheetFragment;

/**
 * Created by tehras on 2/14/16.
 */
public class MatchBottomSheetFragment extends BaseBottomSheetFragment {

    @Override
    public View getDesiredView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_match_bottom_sheet, null, false);

        return view;
    }
}
