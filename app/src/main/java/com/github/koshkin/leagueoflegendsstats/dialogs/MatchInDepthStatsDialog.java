package com.github.koshkin.leagueoflegendsstats.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.match.BaseTimeLineDelta;
import com.github.koshkin.leagueoflegendsstats.models.match.CsDiffPerMinDeltas;
import com.github.koshkin.leagueoflegendsstats.models.match.Participant;
import com.github.koshkin.leagueoflegendsstats.models.match.ParticipantIdentity;
import com.github.koshkin.leagueoflegendsstats.utils.MatchUtils;
import com.github.koshkin.leagueoflegendsstats.utils.NumberUtils;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;
import com.github.koshkin.leagueoflegendsstats.views.HorizontalBarStatView;
import com.github.koshkin.leagueoflegendsstats.views.MatchPerTenView;

import java.util.ArrayList;
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
        if (mParticipantIdentity != null && mParticipantIdentity.getPlayer() != null)
            ((TextView) view.findViewById(R.id.summoner_name)).setText(mParticipantIdentity.getPlayer().getSummonerName());
        else
            ((TextView) view.findViewById(R.id.summoner_name)).setText("N/A");

        HorizontalBarStatView damageChart = (HorizontalBarStatView) view.findViewById(R.id.damage_chart);
        HorizontalBarStatView goldChart = (HorizontalBarStatView) view.findViewById(R.id.gold_chart);
        HorizontalBarStatView kdaChart = (HorizontalBarStatView) view.findViewById(R.id.kda_chart);

        ArrayList<Float> damages = new ArrayList<>();
        ArrayList<Float> gold = new ArrayList<>();
        ArrayList<Float> kdas = new ArrayList<>();
        float playerDamage = 0;
        float playerGold = 0;
        float playerKda = 0;

        for (Participant participant : mParticipants) {
            if (participant == mParticipant) {
                playerDamage = participant.getStats().getTotalDamageDealtToChampions();
                playerGold = participant.getStats().getGoldEarned();
                playerKda = MatchUtils.getKDAFloat(participant);
            }

            kdas.add(MatchUtils.getKDAFloat(participant));
            damages.add((float) participant.getStats().getTotalDamageDealtToChampions());
            gold.add((float) participant.getStats().getGoldEarned());
        }

        kdaChart.setData(kdas, playerKda);
        damageChart.setData(damages, playerDamage);
        goldChart.setData(gold, playerGold);
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
        MatchPerTenView creepsPerTenDiffView = (MatchPerTenView) view.findViewById(R.id.match_cs_diff_per_ten);

        CsDiffPerMinDeltas csDiffPerMinDeltas = mParticipant.getTimeline().getCsDiffPerMinDeltas();
        if (csDiffPerMinDeltas != null)
            creepsPerTenDiffView.setZeroToTen(getCorrectCreepsPerTen(csDiffPerMinDeltas.getZeroToTen()), matchColocsDiffPerMinDeltas(csDiffPerMinDeltas.getZeroToTen(), 0))
                    .setTenToTwenty(getCorrectCreepsPerTen(csDiffPerMinDeltas.getTenToTwenty()), matchColocsDiffPerMinDeltas(csDiffPerMinDeltas.getTenToTwenty(), 1))
                    .setTwentyToThirty(getCorrectCreepsPerTen(csDiffPerMinDeltas.getTwentyToThirty()), matchColocsDiffPerMinDeltas(csDiffPerMinDeltas.getTwentyToThirty(), 2))
                    .setThirtyPlus(getCorrectCreepsPerTen(csDiffPerMinDeltas.getThirtyToEnd()), matchColocsDiffPerMinDeltas(csDiffPerMinDeltas.getThirtyToEnd(), 3));
        else
            creepsPerTenDiffView.setVisibility(View.GONE);
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

        return MatchUtils.getColor(howManybetter, mParticipants.size());
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

        return MatchUtils.getColor(howManybetter, mParticipants.size());
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

        return MatchUtils.getColor(howManybetter, mParticipants.size());
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
