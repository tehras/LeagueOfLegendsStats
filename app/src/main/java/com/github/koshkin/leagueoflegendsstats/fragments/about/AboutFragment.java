package com.github.koshkin.leagueoflegendsstats.fragments.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.R;

/**
 * Created by tehras on 2/6/16.
 * <p/>
 * About fragment for licenses
 */
public class AboutFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        TextView t2 = (TextView) rootView.findViewById(R.id.lol_terms);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        
        return rootView;
    }
}
