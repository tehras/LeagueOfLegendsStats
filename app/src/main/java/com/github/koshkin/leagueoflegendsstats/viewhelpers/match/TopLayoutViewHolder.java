package com.github.koshkin.leagueoflegendsstats.viewhelpers.match;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.TeamSide;
import com.github.koshkin.leagueoflegendsstats.models.match.FullMatch;
import com.github.koshkin.leagueoflegendsstats.models.match.Participant;
import com.github.koshkin.leagueoflegendsstats.models.match.ParticipantIdentity;
import com.github.koshkin.leagueoflegendsstats.models.match.Team;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;

import java.util.ArrayList;

import static com.github.koshkin.leagueoflegendsstats.utils.MatchUtils.getCurrentSideFromParticipantIdentity;
import static com.github.koshkin.leagueoflegendsstats.utils.MatchUtils.getIdentityFromSummonerId;
import static com.github.koshkin.leagueoflegendsstats.utils.MatchUtils.getParticipantFromParticipantIdentity;
import static com.github.koshkin.leagueoflegendsstats.utils.MatchUtils.populateKDAandGold;

/**
 * Created by tehras on 1/17/16.
 * <p/>
 * Top View layout
 */
@SuppressWarnings("deprecation")
public class TopLayoutViewHolder {

    private final TextView mLeftTeamTitle, mLeftKDA, mLefGold, mLeftTowers, mLeftDragons, mLeftBarons;
    private final TextView mRightTeamTitle, mRightKDA, mRightGold, mRightTowers, mRightDragons, mRightBarons, mGameTime, mGameLength;
    private final ImageView mLeftWin, mRightWin;

    public TopLayoutViewHolder(View view) {
        mLeftTeamTitle = (TextView) view.findViewById(R.id.left_team_title);
        mLeftKDA = (TextView) view.findViewById(R.id.left_team_kda);
        mLefGold = (TextView) view.findViewById(R.id.left_team_gold);
        mLeftTowers = (TextView) view.findViewById(R.id.left_team_towers);
        mLeftDragons = (TextView) view.findViewById(R.id.left_team_dragons);
        mLeftBarons = (TextView) view.findViewById(R.id.left_team_barons);
        mLeftWin = (ImageView) view.findViewById(R.id.left_team_check);

        mGameTime = (TextView) view.findViewById(R.id.game_time);
        mGameLength = (TextView) view.findViewById(R.id.game_length);

        mRightTeamTitle = (TextView) view.findViewById(R.id.right_team_title);
        mRightKDA = (TextView) view.findViewById(R.id.right_team_kda);
        mRightGold = (TextView) view.findViewById(R.id.right_team_gold);
        mRightTowers = (TextView) view.findViewById(R.id.right_team_towers);
        mRightDragons = (TextView) view.findViewById(R.id.right_team_dragons);
        mRightBarons = (TextView) view.findViewById(R.id.right_team_barons);
        mRightWin = (ImageView) view.findViewById(R.id.right_team_check);
    }

    @SuppressLint("SetTextI18n")
    public void populate(FullMatch fullMatch, Activity activity, String summonerId, String matchTime, String matchLength) {
        if (fullMatch.getTeams() != null && fullMatch.getParticipants() != null && fullMatch.getParticipantIdentities() != null && summonerId != null) {
            ArrayList<Team> teams = (ArrayList<Team>) fullMatch.getTeams();
            ArrayList<Participant> participants = (ArrayList<Participant>) fullMatch.getParticipants();

            ParticipantIdentity currentPlayer = getIdentityFromSummonerId((ArrayList<ParticipantIdentity>) fullMatch.getParticipantIdentities(), Long.parseLong(summonerId));
            Participant participant = getParticipantFromParticipantIdentity(participants, currentPlayer); //Main guy

            TeamSide leftSide = getCurrentSideFromParticipantIdentity(participant); //Defines Left Side

            populateKDAandGold(mLefGold, mRightGold, mLeftKDA, mRightKDA, participants, leftSide);
            Team leftTeam = null, rightTeam = null;
            for (Team team : teams) {
                if (team.getTeamId() == leftSide)
                    leftTeam = team;
                else if (team.getTeamId() != leftSide)
                    rightTeam = team;
            }

            populateTeamStats(leftTeam, mLeftTowers, mLeftBarons, mLeftDragons, rightTeam);
            populateTeamStats(rightTeam, mRightTowers, mRightBarons, mRightDragons, leftTeam);
            mLeftWin.setVisibility(View.GONE);
            mRightWin.setVisibility(View.GONE);

            if (leftTeam != null) {
                if (leftTeam.isWinner()) {
                    mLeftWin.setVisibility(View.VISIBLE);
                    mLeftWin.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_in_left));
                } else {
                    mRightWin.setVisibility(View.VISIBLE);
                    mRightWin.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_in_right));
                }
            }

            if (leftSide == TeamSide.BLUE) {
                mLeftTeamTitle.setText("Blue Team");
                mRightTeamTitle.setText("Red Team");
                mLeftTeamTitle.setTextColor(activity.getResources().getColor(TeamSide.BLUE.getSideColor()));
                mRightTeamTitle.setTextColor(activity.getResources().getColor(TeamSide.RED.getSideColor()));
            } else {
                mLeftTeamTitle.setText("Red Team");
                mRightTeamTitle.setText("Blue Team");
                mLeftTeamTitle.setTextColor(activity.getResources().getColor(TeamSide.RED.getSideColor()));
                mRightTeamTitle.setTextColor(activity.getResources().getColor(TeamSide.BLUE.getSideColor()));
            }

            mGameLength.setText("Game Length - " + matchLength);
            mGameTime.setText("Played " + matchTime);
        } else {
            //TODO error layout
        }
    }

    private void populateTeamStats(Team team, TextView towers, TextView barons, TextView dragons, Team otherTeam) {
        if (team != null) {
            if (otherTeam != null) {
                if (team.getTowerKills() < otherTeam.getTowerKills())
                    towers.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                if (team.getDragonKills() < otherTeam.getDragonKills())
                    dragons.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                if (team.getBaronKills() < otherTeam.getBaronKills())
                    barons.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
            towers.setText(String.valueOf(team.getTowerKills() + " Tower" + (team.getTowerKills() > 1 ? "s" : "")));
            barons.setText(String.valueOf(team.getBaronKills()) + " Baron" + (team.getBaronKills() > 1 ? "s" : ""));
            dragons.setText(String.valueOf(team.getDragonKills()) + " Dragon" + (team.getDragonKills() > 1 ? "s" : ""));
        } else {
            towers.setText(Utils.NOT_AVAILABLE);
            barons.setText(Utils.NOT_AVAILABLE);
            dragons.setText(Utils.NOT_AVAILABLE);
        }
    }

}
