package com.github.koshkin.leagueoflegendsstats.holders;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.match.Participant;
import com.github.koshkin.leagueoflegendsstats.models.match.ParticipantIdentity;
import com.github.koshkin.leagueoflegendsstats.utils.MatchUtils;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;

/**
 * Created by tehras on 1/13/16.
 * <p/>
 * Common holder to create Game History view
 */
public class MatchGameHolder extends RecyclerView.ViewHolder {

    public TextView champNameTv, kdaTv, killsDeathsAssistsTv, achievementText, levelTv, csTv, gameWardsTv, killPartTv, mSummonerName;
    public ImageView champImage;
    public ImageView icon1;
    public ImageView icon2;
    public View achievementLayout;

    public MatchGameHolder(View view) {
        super(view);

        champImage = (ImageView) view.findViewById(R.id.champion_icon);
        icon1 = (ImageView) view.findViewById(R.id.icon1); //TODO icon
        icon2 = (ImageView) view.findViewById(R.id.icon2); //TODO icon

        champNameTv = (TextView) view.findViewById(R.id.champion_name);

        kdaTv = (TextView) view.findViewById(R.id.champion_kda);
        killsDeathsAssistsTv = (TextView) view.findViewById(R.id.champion_kills_deaths_assists);
        achievementLayout = view.findViewById(R.id.game_achievement_layout);
        achievementText = (TextView) view.findViewById(R.id.game_achievement);

        levelTv = (TextView) view.findViewById(R.id.game_champ_level);
        csTv = (TextView) view.findViewById(R.id.game_cs);
        gameWardsTv = (TextView) view.findViewById(R.id.game_wards);
        killPartTv = (TextView) view.findViewById(R.id.game_kill_part);

        mSummonerName = (TextView) view.findViewById(R.id.summoner_name);
    }

    public void populate(final Participant participant, ParticipantIdentity participantIdentity, final Activity activity) {
        new LoaderHelper() {

            Drawable mDrawable, mIcon1Drawable, mIcon2Drawable;

            @Override
            protected void postExecute() {
                if (mDrawable != null)
                    MatchGameHolder.this.champImage.setImageDrawable(mDrawable);
                if (mIcon1Drawable != null)
                    MatchGameHolder.this.icon1.setImageDrawable(mIcon1Drawable);
                if (mIcon2Drawable != null)
                    MatchGameHolder.this.icon2.setImageDrawable(mIcon2Drawable);
            }

            @Override
            protected void runInBackground() {
                mDrawable = StaticDataHolder.getInstance(activity).getChampionIcon(participant.getChampionId());
                mIcon1Drawable = StaticDataHolder.getInstance(activity).getSpellIcon(participant.getSpell1Id());
                mIcon2Drawable = StaticDataHolder.getInstance(activity).getSpellIcon(participant.getSpell2Id());
            }
        }.execute();

        this.champNameTv.setText(StaticDataHolder.getInstance(activity).getChampionName(participant.getChampionId()));

        //KDA game stats and achievement
        this.kdaTv.setText(MatchUtils.kdaCalculated(participant, kdaTv, activity));
        this.killsDeathsAssistsTv.setText(MatchUtils.getKDA(participant));
        MatchUtils.populateAchievement((ViewGroup) this.achievementLayout, this.achievementText, participant);

        //Other Stats
        this.levelTv.setText(MatchUtils.getLevel(participant));
        this.csTv.setText(MatchUtils.getCS(participant));
        this.gameWardsTv.setText(MatchUtils.gameWards(participant));
        this.killPartTv.setText(MatchUtils.dmgDealt(participant));

        this.champNameTv.setText(MatchUtils.getName(participantIdentity));
    }
}
