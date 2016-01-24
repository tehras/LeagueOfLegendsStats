package com.github.koshkin.leagueoflegendsstats.holders.observable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.Champion;
import com.github.koshkin.leagueoflegendsstats.models.ObservableGame;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.TeamSide;
import com.github.koshkin.leagueoflegendsstats.models.match.Participant;
import com.github.koshkin.leagueoflegendsstats.utils.ObservableUtils;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Top layout for the Observable games
 * <p/>
 * Created by tehras on 1/23/16.
 */
public class TopLayoutViewHolder {

    private final View mLeftPickView, mRightPickView, mLeftBanView, mRightbanView;
    private final TextView mBansText, mMatchMode, mMatchType, mMatchGameStarted;
    private Timer mTimer;

    public TopLayoutViewHolder(View view) {
        mLeftPickView = view.findViewById(R.id.left_pick_ban);
        mRightPickView = view.findViewById(R.id.right_pick_ban);
        mLeftBanView = view.findViewById(R.id.left_ban);
        mRightbanView = view.findViewById(R.id.right_ban);

        mBansText = (TextView) view.findViewById(R.id.observable_game_bans);
        mMatchType = (TextView) view.findViewById(R.id.observable_game_type);
        mMatchMode = (TextView) view.findViewById(R.id.observable_game_mode);
        mMatchGameStarted = (TextView) view.findViewById(R.id.observable_game_start);
    }

    public void populate(ObservableGame observableGame, Activity activity) {
        populatePicks(mLeftPickView, observableGame, TeamSide.BLUE, activity);
        populatePicks(mRightPickView, observableGame, TeamSide.RED, activity);

        populateBans(mLeftBanView, observableGame, TeamSide.BLUE, activity);
        populateBans(mRightbanView, observableGame, TeamSide.RED, activity);

        mMatchType.setText(getMatchType(observableGame));
        mMatchMode.setText(getMatchMode(observableGame));
        mMatchGameStarted.setText(ObservableUtils.getStartedText(Calendar.getInstance().getTimeInMillis(), observableGame.getGameStartTime()));

        clickingGameTime(observableGame, activity);
    }

    public void stopTimer() {
        if (mTimer != null)
            mTimer.cancel();
    }

    private void clickingGameTime(ObservableGame observableGame, Activity activity) {
        mTimer = new Timer();
        UpdateTimerTask timerTask = new UpdateTimerTask(observableGame, activity);
        mTimer.schedule(timerTask, 0, 1000);
    }

    private class UpdateTimerTask extends TimerTask {
        private final ObservableGame mObservableGame;
        private final Activity mActivity;

        public UpdateTimerTask(ObservableGame observableGame, Activity activity) {
            mObservableGame = observableGame;
            mActivity = activity;
        }

        @Override
        public void run() {
            if (mActivity != null)
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMatchGameStarted.setText(ObservableUtils.getStartedText(Calendar.getInstance().getTimeInMillis(), mObservableGame.getGameStartTime()));
                    }
                });
        }
    }

    private void populateBans(View view, ObservableGame observableGame, TeamSide side, final Activity activity) {
        ArrayList<Champion> champion = observableGame.getBannedChampions();

        if (champion == null || champion.size() == 0) {
            mBansText.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            return;
        }

        final ArrayList<Champion> championArrayList = getChampions(champion, side);

        View ban1, ban2, ban3;
        if (side == TeamSide.BLUE) {
            ban1 = view.findViewById(R.id.pick_ban_3);
            ban2 = view.findViewById(R.id.pick_ban_2);
            ban3 = view.findViewById(R.id.pick_ban_1);
        } else {
            ban1 = view.findViewById(R.id.pick_ban_1);
            ban2 = view.findViewById(R.id.pick_ban_2);
            ban3 = view.findViewById(R.id.pick_ban_3);
        }

        final View ban1Final = ban1;
        final View ban2Final = ban2;
        final View ban3Final = ban3;
        new LoaderHelper() {

            @Override
            protected void postExecute() {
                populateThis(ban1, ban1Final);
                populateThis(ban2, ban2Final);
                populateThis(ban3, ban3Final);
            }

            private void populateThis(HashMap<Drawable, Boolean> ban, View banView) {

                Drawable drawable = Iterables.get(ban.keySet(), 0);
                boolean isBan = Iterables.get(ban.values(), 0);

                if (isBan) {
                    if (drawable != null)
                        ((ImageView) banView.findViewById(R.id.champion_image)).setImageDrawable(drawable);
                    banView.findViewById(R.id.champion_pick_order).setVisibility(View.GONE);
                } else {
                    banView.setVisibility(View.GONE);
                }
            }

            HashMap<Drawable, Boolean> ban1, ban2, ban3;

            @Override
            protected void runInBackground() {
                ban1 = getChampById(0);
                ban2 = getChampById(1);
                ban3 = getChampById(2);
            }

            private HashMap<Drawable, Boolean> getChampById(int i) {
                Champion champion = getChamp(i);
                boolean isBan;
                Drawable drawable;
                if (champion != null) {
                    isBan = true;
                    drawable = StaticDataHolder.getInstance(activity).getChampionIcon(champion.getChampionId());
                } else {
                    isBan = false;
                    drawable = null;
                }
                HashMap<Drawable, Boolean> map = new HashMap<>();
                map.put(drawable, isBan);
                return map;
            }

            private Champion getChamp(int i) {
                if (championArrayList.size() > i) {
                    return championArrayList.get(i);
                } else
                    return null;
            }
        }.execute();
    }

    private ArrayList<Champion> getChampions(ArrayList<Champion> champions, TeamSide teamSide) {
        ArrayList<Champion> championArrayList = new ArrayList<>();

        for (Champion champ : champions) {
            if (teamSide == champ.getTeamSide())
                championArrayList.add(champ);
        }

        return championArrayList;
    }

    @SuppressLint("CutPasteId")
    private void populatePicks(View view, ObservableGame observableGame, TeamSide side, final Activity activity) {
        ArrayList<Participant> participants = observableGame.getParticipants();

        final ArrayList<Integer> championIds = getChampionIds(participants, side);

        View pick1 = view.findViewById(R.id.pick_ban_1);
        View pick2 = view.findViewById(R.id.pick_ban_2);
        final View pick3 = view.findViewById(R.id.pick_ban_3);
        View pick4 = view.findViewById(R.id.pick_ban_4);
        View pick5 = view.findViewById(R.id.pick_ban_5);

        if (side == TeamSide.BLUE) {// left side is reversed order
            pick1 = view.findViewById(R.id.pick_ban_5);
            pick2 = view.findViewById(R.id.pick_ban_4);
            pick4 = view.findViewById(R.id.pick_ban_2);
            pick5 = view.findViewById(R.id.pick_ban_1);
        }

        final View finalPick1 = pick1;
        final View finalPick2 = pick2;
        final View finalPick3 = pick4;
        final View finalPick4 = pick5;
        new LoaderHelper() {
            @Override
            protected void postExecute() {
                populate(finalPick1, 0, champ1);
                populate(finalPick2, 0, champ2);
                populate(pick3, 0, champ3);
                populate(finalPick3, 0, champ4);
                populate(finalPick4, 0, champ5);
            }

            private void populate(View pick, int i, Drawable champ) {
                if (contains(championIds, i)) {
                    pick.setVisibility(View.VISIBLE);
                    if (champ != null)
                        ((ImageView) pick.findViewById(R.id.champion_image)).setImageDrawable(champ);
                    pick.findViewById(R.id.champion_pick_order).setVisibility(View.GONE);
                } else {
                    pick.setVisibility(View.GONE);
                }
            }

            private Drawable champ1, champ2, champ3, champ4, champ5;

            @Override
            protected void runInBackground() {
                champ1 = getChamp(0);
                champ2 = getChamp(1);
                champ3 = getChamp(2);
                champ4 = getChamp(3);
                champ5 = getChamp(4);
            }

            private Drawable getChamp(int i) {
                if (contains(championIds, i)) {
                    int champId = championIds.get(i);
                    return StaticDataHolder.getInstance(activity).getChampionIcon(champId);
                }
                return null;
            }
        }.execute();


    }

    private boolean contains(ArrayList<Integer> championIds, int i) {
        return championIds.size() >= i;
    }

    private ArrayList<Integer> getChampionIds(ArrayList<Participant> participants, TeamSide teamSide) {
        ArrayList<Integer> championIds = new ArrayList<>();

        for (Participant participant : participants) {
            if (participant.getTeamId() == teamSide)
                championIds.add(participant.getChampionId());
        }

        return championIds;
    }

    public String getMatchType(ObservableGame observableGame) {
        return ObservableUtils.getFromGameType(observableGame.getGameType());
    }

    public String getMatchMode(ObservableGame observableGame) {
        return ObservableUtils.getFromGameMode(observableGame.getGameMode());
    }
}
