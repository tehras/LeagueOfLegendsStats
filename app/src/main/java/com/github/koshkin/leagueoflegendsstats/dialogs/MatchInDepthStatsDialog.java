package com.github.koshkin.leagueoflegendsstats.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.holders.MatchGameHolder;
import com.github.koshkin.leagueoflegendsstats.models.match.BaseTimeLineDelta;
import com.github.koshkin.leagueoflegendsstats.models.match.CreepsPerMinDeltas;
import com.github.koshkin.leagueoflegendsstats.models.match.CsDiffPerMinDeltas;
import com.github.koshkin.leagueoflegendsstats.models.match.Participant;
import com.github.koshkin.leagueoflegendsstats.models.match.ParticipantIdentity;
import com.github.koshkin.leagueoflegendsstats.models.match.XpDiffPerMinDeltas;
import com.github.koshkin.leagueoflegendsstats.utils.NumberUtils;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;
import com.github.koshkin.leagueoflegendsstats.views.MatchPerTenView;

/**
 * Created by tehras on 1/18/16.
 */
public class MatchInDepthStatsDialog extends DialogFragment {

    private Participant mParticipant;
    private ParticipantIdentity mParticipantIdentity;

    public static MatchInDepthStatsDialog getInstance(Participant participant, ParticipantIdentity participantIdentity) {
        MatchInDepthStatsDialog fragment = new MatchInDepthStatsDialog();
        fragment.setParticipant(participant);
        fragment.setParticipantIdentity(participantIdentity);

        return fragment;
    }

    public void setParticipantIdentity(ParticipantIdentity participantIdentity) {
        mParticipantIdentity = participantIdentity;
    }

    public void setParticipant(Participant participant) {
        mParticipant = participant;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_match_stats, container);

        initTopLayout(view);
        initBarCharts(view);

        fixWidth();

        return view;
    }

    private void initTopLayout(View view) {
        new MatchGameHolder(view).populate(mParticipant, mParticipantIdentity, getActivity());
    }

    private void fixWidth() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getDialog().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().show();
        getDialog().getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).allowScrollview();

        super.onDismiss(dialog);
    }

    private void initBarCharts(View view) {
        MatchPerTenView creepsPerTenView = (MatchPerTenView) view.findViewById(R.id.match_creep_per_ten);
        MatchPerTenView creepsPerTenDiffView = (MatchPerTenView) view.findViewById(R.id.match_cs_diff_per_ten);
        MatchPerTenView matchPerTenView = (MatchPerTenView) view.findViewById(R.id.match_xp_diff_per_ten);

        CreepsPerMinDeltas creepsPerTen = mParticipant.getTimeline().getCreepsPerMinDeltas();
        creepsPerTenView.setZeroToTen(getCorrectCreepsPerTen(creepsPerTen.getZeroToTen()), isPositive(creepsPerTen.getZeroToTen()))
                .setTenToTwenty(getCorrectCreepsPerTen(creepsPerTen.getTenToTwenty()), isPositive(creepsPerTen.getTenToTwenty()))
                .setTwentyToThirty(getCorrectCreepsPerTen(creepsPerTen.getTwentyToThirty()), isPositive(creepsPerTen.getTwentyToThirty()))
                .setThirtyPlus(getCorrectCreepsPerTen(creepsPerTen.getThirtyToEnd()), isPositive(creepsPerTen.getThirtyToEnd()));

        CsDiffPerMinDeltas csDiffPerMinDeltas = mParticipant.getTimeline().getCsDiffPerMinDeltas();
        creepsPerTenDiffView.setZeroToTen(getCorrectCreepsPerTen(csDiffPerMinDeltas.getZeroToTen()), isPositive(csDiffPerMinDeltas.getZeroToTen()))
                .setTenToTwenty(getCorrectCreepsPerTen(csDiffPerMinDeltas.getTenToTwenty()), isPositive(csDiffPerMinDeltas.getTenToTwenty()))
                .setTwentyToThirty(getCorrectCreepsPerTen(csDiffPerMinDeltas.getTwentyToThirty()), isPositive(csDiffPerMinDeltas.getTwentyToThirty()))
                .setThirtyPlus(getCorrectCreepsPerTen(csDiffPerMinDeltas.getThirtyToEnd()), isPositive(csDiffPerMinDeltas.getThirtyToEnd()));

        XpDiffPerMinDeltas xpDiffPerMinDeltas = mParticipant.getTimeline().getXpDiffPerMinDeltas();
        matchPerTenView.setZeroToTen(getCorrectCreepsPerTen(xpDiffPerMinDeltas.getZeroToTen()), isPositive(xpDiffPerMinDeltas.getZeroToTen()))
                .setTenToTwenty(getCorrectCreepsPerTen(xpDiffPerMinDeltas.getTenToTwenty()), isPositive(xpDiffPerMinDeltas.getTenToTwenty()))
                .setTwentyToThirty(getCorrectCreepsPerTen(xpDiffPerMinDeltas.getTwentyToThirty()), isPositive(xpDiffPerMinDeltas.getTwentyToThirty()))
                .setThirtyPlus(getCorrectCreepsPerTen(xpDiffPerMinDeltas.getThirtyToEnd()), isPositive(xpDiffPerMinDeltas.getThirtyToEnd()));
    }

    private CharSequence getCorrectCreepsPerTen(float zeroToTen) {
        if (zeroToTen == BaseTimeLineDelta.DEFAULT_VALUE)
            return Utils.NOT_AVAILABLE;

        return (NumberUtils.twoDecimals(zeroToTen)) + "/min";
    }


    private boolean isPositive(float zeroToTen) {
        return zeroToTen > 0f;
    }
}
