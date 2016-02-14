package com.github.koshkin.leagueoflegendsstats.viewhelpers.match;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.dialogs.MatchInDepthStatsDialog;
import com.github.koshkin.leagueoflegendsstats.models.match.Participant;
import com.github.koshkin.leagueoflegendsstats.models.match.ParticipantIdentity;

import java.util.List;

/**
 * Created by tehras on 1/18/16.
 * <p/>
 * 3D touch sort of interaction
 */
public class LongHoldHelper implements View.OnTouchListener {

    private static final long LONG_TAP_THRESHOLD = 200L;
    private final View mViewGroup;
    private final Activity mActivity;
    private final Participant mParticipant;
    private final ParticipantIdentity mParticipantIdentity;
    private final List<Participant> mParticipants;
    private MatchInDepthStatsDialog mEditDialog;

    public LongHoldHelper(Activity activity, View viewGroup, Participant participant, ParticipantIdentity participantIdentity, List<Participant> participants) {
        mActivity = activity;
        mViewGroup = viewGroup;
        mParticipant = participant;
        mParticipantIdentity = participantIdentity;
        mParticipants = participants;
    }

    public void init() {
        mViewGroup.setOnTouchListener(this);
        mViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    boolean isInside = false;

    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        if (v instanceof NestedScrollView) {
            switch (ev.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    handler.removeCallbacks(mLongPressed);
                    hideDialog();
                    return true;
                case MotionEvent.ACTION_CANCEL:
                    handler.removeCallbacks(mLongPressed);
                    hideDialog();
                    return true;
            }
        } else {
            switch (ev.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    isInside = true;
                    handler.postDelayed(mLongPressed, LONG_TAP_THRESHOLD);
                    return true;
                case MotionEvent.ACTION_UP:
                    isInside = false;
                    handler.removeCallbacks(mLongPressed);
                    hideDialog();
                    return true;
                case MotionEvent.ACTION_CANCEL:
                    isInside = false;
                    if (!isShowing)
                        handler.removeCallbacks(mLongPressed);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    return isInside;
            }

            isInside = false;
        }
        return false;
    }

    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            showDialog();
        }
    };

    private boolean isShowing = false;

    private void showDialog() {
        isShowing = true;
        if (mActivity instanceof MainActivity)
            ((MainActivity) mActivity).blockScrollView(this);

        mViewGroup.setHapticFeedbackEnabled(true);
        mViewGroup.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

        FragmentManager fm = mActivity.getFragmentManager();
        mEditDialog = MatchInDepthStatsDialog.getInstance(mParticipant, mParticipantIdentity, mParticipants);
        mEditDialog.show(fm, "fragment_edit_name");
    }

    private void hideDialog() {
        isShowing = false;
        if (mEditDialog != null)
            mEditDialog.dismiss();
        if (mActivity instanceof MainActivity)
            ((MainActivity) mActivity).allowScrollview();
    }

}
