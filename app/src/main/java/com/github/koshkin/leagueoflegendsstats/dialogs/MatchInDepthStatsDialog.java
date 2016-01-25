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

import java.util.List;

/**
 * Created by tehras on 1/18/16.
 * <p/>
 * In Depth Dialog
 */
public class MatchInDepthStatsDialog extends DialogFragment {

    private Participant mParticipant;
    private ParticipantIdentity mParticipantIdentity;
    private List<Participant> mParticipants;

    public static MatchInDepthStatsDialog getInstance(Participant participant, ParticipantIdentity participantIdentity, List<Participant> participants) {
        MatchInDepthStatsDialog fragment = new MatchInDepthStatsDialog();
        fragment.setParticipant(participant);
        fragment.setParticipantIdentity(participantIdentity);
        fragment.setParticipants(participants);

        return fragment;
    }

    public void setParticipantIdentity(ParticipantIdentity participantIdentity) {
        mParticipantIdentity = participantIdentity;
    }

    public void setParticipant(Participant participant) {
        mParticipant = participant;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() != null && getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).showFroth();
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
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).allowScrollview();
            ((MainActivity) getActivity()).hideFroth();
        }
        super.onDismiss(dialog);
    }

    private void initBarCharts(View view) {
        MatchPerTenView creepsPerTenView = (MatchPerTenView) view.findViewById(R.id.match_creep_per_ten);
        MatchPerTenView creepsPerTenDiffView = (MatchPerTenView) view.findViewById(R.id.match_cs_diff_per_ten);
        MatchPerTenView matchPerTenView = (MatchPerTenView) view.findViewById(R.id.match_xp_diff_per_ten);

        CreepsPerMinDeltas creepsPerTen = mParticipant.getTimeline().getCreepsPerMinDeltas();
        if (creepsPerTen != null)
            creepsPerTenView.setZeroToTen(getCorrectCreepsPerTen(creepsPerTen.getZeroToTen()), matchColoCreepsPerMin(creepsPerTen.getZeroToTen(), 0))
                    .setTenToTwenty(getCorrectCreepsPerTen(creepsPerTen.getTenToTwenty()), matchColoCreepsPerMin(creepsPerTen.getTenToTwenty(), 1))
                    .setTwentyToThirty(getCorrectCreepsPerTen(creepsPerTen.getTwentyToThirty()), matchColoCreepsPerMin(creepsPerTen.getTwentyToThirty(), 2))
                    .setThirtyPlus(getCorrectCreepsPerTen(creepsPerTen.getThirtyToEnd()), matchColoCreepsPerMin(creepsPerTen.getThirtyToEnd(), 3));
        else
            creepsPerTenView.setVisibility(View.GONE);

        CsDiffPerMinDeltas csDiffPerMinDeltas = mParticipant.getTimeline().getCsDiffPerMinDeltas();
        creepsPerTenDiffView.setZeroToTen(getCorrectCreepsPerTen(csDiffPerMinDeltas.getZeroToTen()), matchColocsDiffPerMinDeltas(csDiffPerMinDeltas.getZeroToTen(), 0))
                .setTenToTwenty(getCorrectCreepsPerTen(csDiffPerMinDeltas.getTenToTwenty()), matchColocsDiffPerMinDeltas(csDiffPerMinDeltas.getTenToTwenty(), 1))
                .setTwentyToThirty(getCorrectCreepsPerTen(csDiffPerMinDeltas.getTwentyToThirty()), matchColocsDiffPerMinDeltas(csDiffPerMinDeltas.getTwentyToThirty(), 2))
                .setThirtyPlus(getCorrectCreepsPerTen(csDiffPerMinDeltas.getThirtyToEnd()), matchColocsDiffPerMinDeltas(csDiffPerMinDeltas.getThirtyToEnd(), 3));

        XpDiffPerMinDeltas xpDiffPerMinDeltas = mParticipant.getTimeline().getXpDiffPerMinDeltas();
        matchPerTenView.setZeroToTen(getCorrectCreepsPerTen(xpDiffPerMinDeltas.getZeroToTen()), matchColocsXpDiffPerMinDeltas(xpDiffPerMinDeltas.getZeroToTen(), 0))
                .setTenToTwenty(getCorrectCreepsPerTen(xpDiffPerMinDeltas.getTenToTwenty()), matchColocsXpDiffPerMinDeltas(xpDiffPerMinDeltas.getTenToTwenty(), 1))
                .setTwentyToThirty(getCorrectCreepsPerTen(xpDiffPerMinDeltas.getTwentyToThirty()), matchColocsXpDiffPerMinDeltas(xpDiffPerMinDeltas.getTwentyToThirty(), 2))
                .setThirtyPlus(getCorrectCreepsPerTen(xpDiffPerMinDeltas.getThirtyToEnd()), matchColocsXpDiffPerMinDeltas(xpDiffPerMinDeltas.getThirtyToEnd(), 3));
    }

    //0-10 == 0, 10-20 == 1, 20-30 == 2, 30+ == 3
    private MatchPerTenView.MatchColor matchColoCreepsPerMin(float f, int i) {

        int howManybetter = 0;
        for (Participant participant : mParticipants) {
            if (participant.getTimeline() == null || participant.getTimeline().getCreepsPerMinDeltas() == null)
                continue;
            float fromPart;
            if (i == 0) {
                fromPart = participant.getTimeline().getCreepsPerMinDeltas().getZeroToTen();
            } else if (i == 1) {
                fromPart = participant.getTimeline().getCreepsPerMinDeltas().getTenToTwenty();
            } else if (i == 2) {
                fromPart = participant.getTimeline().getCreepsPerMinDeltas().getTwentyToThirty();
            } else {
                fromPart = participant.getTimeline().getCreepsPerMinDeltas().getThirtyToEnd();
            }
            if (fromPart > f)
                howManybetter++;
        }

        return getColor(howManybetter);
    }

    //0-10 == 0, 10-20 == 1, 20-30 == 2, 30+ == 3
    private MatchPerTenView.MatchColor matchColocsDiffPerMinDeltas(float f, int i) {

        int howManybetter = 0;
        for (Participant participant : mParticipants) {
            if (participant.getTimeline() == null || participant.getTimeline().getCsDiffPerMinDeltas() == null)
                continue;
            float fromPart;
            if (i == 0) {
                fromPart = participant.getTimeline().getCsDiffPerMinDeltas().getZeroToTen();
            } else if (i == 1) {
                fromPart = participant.getTimeline().getCsDiffPerMinDeltas().getTenToTwenty();
            } else if (i == 2) {
                fromPart = participant.getTimeline().getCsDiffPerMinDeltas().getTwentyToThirty();
            } else {
                fromPart = participant.getTimeline().getCsDiffPerMinDeltas().getThirtyToEnd();
            }
            if (fromPart > f)
                howManybetter++;
        }

        return getColor(howManybetter);
    }

    //0-10 == 0, 10-20 == 1, 20-30 == 2, 30+ == 3
    private MatchPerTenView.MatchColor matchColocsXpDiffPerMinDeltas(float f, int i) {

        int howManybetter = 0;
        for (Participant participant : mParticipants) {
            if (participant.getTimeline() == null || participant.getTimeline().getXpDiffPerMinDeltas() == null)
                continue;
            float fromPart;
            if (i == 0) {
                fromPart = participant.getTimeline().getXpDiffPerMinDeltas().getZeroToTen();
            } else if (i == 1) {
                fromPart = participant.getTimeline().getXpDiffPerMinDeltas().getTenToTwenty();
            } else if (i == 2) {
                fromPart = participant.getTimeline().getXpDiffPerMinDeltas().getTwentyToThirty();
            } else {
                fromPart = participant.getTimeline().getXpDiffPerMinDeltas().getThirtyToEnd();
            }
            if (fromPart > f)
                howManybetter++;
        }

        return getColor(howManybetter);
    }

    private MatchPerTenView.MatchColor getColor(int howManyBetter) {
        if (howManyBetter == 0 || mParticipants.size() / howManyBetter == 3) {
            return MatchPerTenView.MatchColor.GREAT;
        } else if (mParticipants.size() / howManyBetter == 2) {
            return MatchPerTenView.MatchColor.GOOD;
        } else if (mParticipants.size() == howManyBetter + 1) {
            return MatchPerTenView.MatchColor.BAD;
        } else {
            return MatchPerTenView.MatchColor.NEUTRAL;
        }

    }

    private CharSequence getCorrectCreepsPerTen(float zeroToTen) {
        if (zeroToTen == BaseTimeLineDelta.DEFAULT_VALUE)
            return Utils.NOT_AVAILABLE;

        return (NumberUtils.twoDecimals(zeroToTen)) + "/min";
    }

    public void setParticipants(List<Participant> participants) {
        mParticipants = participants;
    }
}
