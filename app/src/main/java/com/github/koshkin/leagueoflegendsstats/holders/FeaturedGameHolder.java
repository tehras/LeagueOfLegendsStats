package com.github.koshkin.leagueoflegendsstats.holders;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.Game;
import com.github.koshkin.leagueoflegendsstats.models.ObservableGame;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.TeamSide;
import com.github.koshkin.leagueoflegendsstats.models.match.Participant;
import com.github.koshkin.leagueoflegendsstats.utils.GameUtils;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;
import com.github.koshkin.leagueoflegendsstats.views.RoundedImageView;

import java.util.ArrayList;

import static com.github.koshkin.leagueoflegendsstats.utils.NullChecker.isNullOrEmpty;

/**
 * Created by tehras on 1/21/16.
 * <p/>
 * Holder for observable games
 */
public class FeaturedGameHolder {

    private final TextView mGameType, mStartedText;
    private final View mDivider;
    private View mFirstLayout, mSecondLayout, mThirdLayout, mFourthLayout, mFifthLayout;

    public FeaturedGameHolder(View view) {
        mFirstLayout = view.findViewById(R.id.observable_game_first);
        mSecondLayout = view.findViewById(R.id.observable_game_second);
        mThirdLayout = view.findViewById(R.id.observable_game_third);
        mFourthLayout = view.findViewById(R.id.observable_game_fourth);
        mFifthLayout = view.findViewById(R.id.observable_game_fifth);

        mGameType = (TextView) view.findViewById(R.id.observable_game_type);
        mStartedText = (TextView) view.findViewById(R.id.observable_game_start);

        mDivider = view.findViewById(R.id.divider);
    }

    public void populate(ObservableGame observableGame, Activity activity, boolean showDivider) {
        ArrayList<Participant> participants = observableGame.getParticipants();

        populateView(mFirstLayout, participants, activity, 1);
        populateView(mSecondLayout, participants, activity, 2);
        populateView(mThirdLayout, participants, activity, 3);
        populateView(mFourthLayout, participants, activity, 4);
        populateView(mFifthLayout, participants, activity, 5);

        mGameType.setText(getFromGameType(observableGame.getGameType()));
        mStartedText.setText(getStartedText(observableGame.getGameLength()));

        if (showDivider)
            mDivider.setVisibility(View.VISIBLE);
        else
            mDivider.setVisibility(View.GONE);
    }

    private CharSequence getStartedText(long gameLength) {
        long min = (int) (gameLength / 60); //get minutes
        long seconds = (int) (gameLength - (min * 60)); //get seconds

        return Html.fromHtml("Game Started <b>" + (min > 0 ? String.valueOf(min) + "m " : "") + (seconds > 0 ? String.valueOf(seconds) + "s" : "") + "</b> ago");
    }

    private String getFromGameType(Game.GameType gameType) {
        String gameTypeString = "";

        String[] words = gameType.toString().split("_");
        for (String word : words) {
            word = GameUtils.capitalizeFirstLetter(word.toLowerCase()).replace("x", "v");
            gameTypeString = gameTypeString + (isNullOrEmpty(gameTypeString) ? "" : " ") + word;
        }

        return gameTypeString;
    }

    private void populateView(View layout, ArrayList<Participant> participants, Activity activity, int i) {
        View leftView = layout.findViewById(R.id.observable_game_left_view);
        View rightView = layout.findViewById(R.id.observable_game_right_view);

        Participant leftParticipant = getParticipant(participants, TeamSide.RED, i);
        Participant rightParticipant = getParticipant(participants, TeamSide.BLUE, i);

        if (leftParticipant != null) {
            layout.setVisibility(View.VISIBLE);
            populateRightOrLeftView(leftView, leftParticipant, activity);
        } else
            layout.setVisibility(View.GONE);
        if (rightParticipant != null) {
            layout.setVisibility(View.VISIBLE);
            populateRightOrLeftView(rightView, rightParticipant, activity);
        } else
            layout.setVisibility(View.GONE);
    }

    private Participant getParticipant(ArrayList<Participant> participants, TeamSide side, int i) {
        int j = 1;
        for (Participant participant : participants) {
            if (participant.getTeamId() == side) {
                if (j == i) {
                    return participant;
                } else {
                    j++;
                }
            }
        }

        return null;
    }

    private void populateRightOrLeftView(View view, final Participant participant, final Activity activity) {
        TextView summonerName = (TextView) view.findViewById(R.id.observable_summoner_name);
        TextView championName = (TextView) view.findViewById(R.id.observable_summoner_champion);

        final RoundedImageView championIcon = (RoundedImageView) view.findViewById(R.id.observable_profile_icon);
        final RoundedImageView itemIcon1View = (RoundedImageView) view.findViewById(R.id.observable_icon_1);
        final RoundedImageView itemIcon2View = (RoundedImageView) view.findViewById(R.id.observable_icon_2);

        summonerName.setText(participant.getSummonerName());
        championName.setText(StaticDataHolder.getInstance(activity).getChampionName(participant.getChampionId()));


        new LoaderHelper() {
            private Drawable championDrawable, itemIcon1, itemIcon2;

            @Override
            protected void postExecute() {
                if (championDrawable != null)
                    championIcon.setImageDrawable(championDrawable);
                else
                    championIcon.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_wallpaper_black_48dp, null));
                if (itemIcon1 != null) {
                    itemIcon1View.setImageDrawable(itemIcon1);
                } else
                    itemIcon1View.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_wallpaper_black_48dp, null));
                if (itemIcon2 != null) {
                    itemIcon2View.setImageDrawable(itemIcon2);
                } else
                    itemIcon2View.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_wallpaper_black_48dp, null));
            }

            @Override
            protected void runInBackground() {
                championDrawable = StaticDataHolder.getInstance(activity).getChampionIcon(participant.getChampionId());
                itemIcon1 = StaticDataHolder.getInstance(activity).getSpellIcon(participant.getSpell1Id());
                itemIcon2 = StaticDataHolder.getInstance(activity).getSpellIcon(participant.getSpell2Id());
            }
        }.execute();

    }

}
