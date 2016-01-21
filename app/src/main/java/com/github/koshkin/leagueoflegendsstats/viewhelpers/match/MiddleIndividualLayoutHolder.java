package com.github.koshkin.leagueoflegendsstats.viewhelpers.match;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.TeamSide;
import com.github.koshkin.leagueoflegendsstats.models.match.FullMatch;
import com.github.koshkin.leagueoflegendsstats.models.match.Participant;
import com.github.koshkin.leagueoflegendsstats.models.match.ParticipantIdentity;
import com.github.koshkin.leagueoflegendsstats.models.match.Stats;
import com.github.koshkin.leagueoflegendsstats.utils.MatchUtils;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;
import com.github.koshkin.leagueoflegendsstats.views.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.github.koshkin.leagueoflegendsstats.utils.MatchUtils.getCurrentSideFromParticipantIdentity;
import static com.github.koshkin.leagueoflegendsstats.utils.MatchUtils.getIdentityFromSummonerId;
import static com.github.koshkin.leagueoflegendsstats.utils.MatchUtils.getParticipantFromParticipantIdentity;
import static com.github.koshkin.leagueoflegendsstats.utils.MatchUtils.getParticipantIdenityFromParticipant;
import static com.github.koshkin.leagueoflegendsstats.utils.MatchUtils.isFirstKill;
import static com.github.koshkin.leagueoflegendsstats.utils.MatchUtils.populateGameRole;

/**
 * Created by tehras on 1/17/16.
 * <p/>
 * Populates that pesky middle layout
 */
@SuppressWarnings("deprecation")
public class MiddleIndividualLayoutHolder {

    private ViewGroup mIndividualLayout1, mIndividualLayout2, mIndividualLayout3, mIndividualLayout4, mIndividualLayout5;

    public MiddleIndividualLayoutHolder(View view) {
        mIndividualLayout1 = (ViewGroup) view.findViewById(R.id.match_individual_champ_1);
        mIndividualLayout2 = (ViewGroup) view.findViewById(R.id.match_individual_champ_2);
        mIndividualLayout3 = (ViewGroup) view.findViewById(R.id.match_individual_champ_3);
        mIndividualLayout4 = (ViewGroup) view.findViewById(R.id.match_individual_champ_4);
        mIndividualLayout5 = (ViewGroup) view.findViewById(R.id.match_individual_champ_5);
    }

    public void populate(Activity activity, FullMatch fullMatch, String summonerId) {
        List<ParticipantIdentity> participantIdentities = fullMatch.getParticipantIdentities();
        List<Participant> participants = fullMatch.getParticipants();
        if (participantIdentities != null && participants != null) {
            ParticipantIdentity currentPlayer = getIdentityFromSummonerId((ArrayList<ParticipantIdentity>) participantIdentities, Long.parseLong(summonerId));
            Participant participant = getParticipantFromParticipantIdentity(participants, currentPlayer); //Main guy

            TeamSide leftSide = getCurrentSideFromParticipantIdentity(participant); //Defines Left Side
            TeamSide rightSide = leftSide == TeamSide.BLUE ? TeamSide.RED : TeamSide.BLUE; //Defines Right Side

            HashMap<Integer, RoleHelper> participantMap = fixParticipantsRoles(participants);
            //Inflate TOP
            populatePlayerView(activity, getCorrectParticipant(participantMap, leftSide, 1), getCorrectParticipant(participantMap, rightSide, 1), currentPlayer, participantIdentities, mIndividualLayout1, leftSide, rightSide);
            //Inflate Mid
            populatePlayerView(activity, getCorrectParticipant(participantMap, leftSide, 2), getCorrectParticipant(participantMap, rightSide, 2), currentPlayer, participantIdentities, mIndividualLayout2, leftSide, rightSide);
            //Inflate JG
            populatePlayerView(activity, getCorrectParticipant(participantMap, leftSide, 3), getCorrectParticipant(participantMap, rightSide, 3), currentPlayer, participantIdentities, mIndividualLayout3, leftSide, rightSide);
            //Inflate ADC
            populatePlayerView(activity, getCorrectParticipant(participantMap, leftSide, 4), getCorrectParticipant(participantMap, rightSide, 4), currentPlayer, participantIdentities, mIndividualLayout4, leftSide, rightSide);
            //Inflate SUPP
            populatePlayerView(activity, getCorrectParticipant(participantMap, leftSide, 5), getCorrectParticipant(participantMap, rightSide, 5), currentPlayer, participantIdentities, mIndividualLayout5, leftSide, rightSide);
        }

        //TODO show Error Message
    }

    private Participant getCorrectParticipant(HashMap<Integer, RoleHelper> participantMap, TeamSide side, int i) {
        if (side == TeamSide.RED)
            return participantMap.get(i).getRedParticipant();
        else
            return participantMap.get(i).getBlueParticipant();
    }

    private HashMap<Integer, RoleHelper> fixParticipantsRoles(List<Participant> participants) {
        HashMap<Integer, RoleHelper> map = new HashMap<>();

        populateMap((ArrayList<Participant>) participants, map);

        return map;
    }

    private void populateMap(ArrayList<Participant> participants, HashMap<Integer, RoleHelper> outMap) {
        ArrayList<Participant> leftovers = new ArrayList<>();

        for (Participant participant : participants) {
            Role role = MatchUtils.getRoleFromParticipant(participant);
            switch (role) {
                case TOP:
                    addToMap(outMap, 1, participant, leftovers);
                    break;
                case MID:
                    addToMap(outMap, 3, participant, leftovers);
                    break;
                case JUNGLE:
                    addToMap(outMap, 2, participant, leftovers);
                    break;
                case BOT:
                    leftovers.add(participant);
                    break;
                case ADC:
                    addToMap(outMap, 4, participant, leftovers);
                    break;
                case SUPP:
                    addToMap(outMap, 5, participant, leftovers);
                    break;
                default:
                    leftovers.add(participant);
                    break;
            }
        }

        if (leftovers.size() > 0) {
            for (Participant participant : leftovers) {
                addToNextAvailable(outMap, participant);
            }
        }
    }

    private void addToNextAvailable(HashMap<Integer, RoleHelper> outMap, Participant participant) {
        for (Integer i = 1; i < 6; i++) {
            if (!outMap.containsKey(i)) {
                RoleHelper roleHelper = new RoleHelper();
                if (participant.getTeamId() == TeamSide.RED) {
                    roleHelper.setRedParticipant(participant);
                } else {
                    roleHelper.setBlueParticipant(participant);
                }

                outMap.put(i, roleHelper);
                break;
            } else {
                RoleHelper helper = outMap.get(i);
                if (participant.getTeamId() == TeamSide.RED && helper.getRedParticipant() == null) {
                    helper.setRedParticipant(participant);
                    break;
                } else if (participant.getTeamId() == TeamSide.BLUE && helper.getBlueParticipant() == null) {
                    helper.setBlueParticipant(participant);
                    break;
                }
            }
        }
    }

    private void addToMap(HashMap<Integer, RoleHelper> map, Integer integer, Participant participant, ArrayList<Participant> leftovers) {
        if (map.containsKey(integer)) {
            RoleHelper roleHelper = map.get(integer);
            if (participant.getTeamId() == TeamSide.RED) {
                if (roleHelper.getRedParticipant() == null)
                    roleHelper.setRedParticipant(participant);
                else
                    leftovers.add(participant);
            } else {
                if (roleHelper.getBlueParticipant() == null)
                    roleHelper.setBlueParticipant(participant);
                else
                    leftovers.add(participant);
            }
        } else {
            RoleHelper roleHelper = new RoleHelper();
            if (participant.getTeamId() == TeamSide.RED)
                roleHelper.setRedParticipant(participant);
            else
                roleHelper.setBlueParticipant(participant);

            map.put(integer, roleHelper);
        }

    }

    private void populatePlayerView(Activity activity, Participant leftParticipant, Participant rightParticipant, ParticipantIdentity mainParticipant, List<ParticipantIdentity> participantIdentities, ViewGroup view, TeamSide leftSide, TeamSide rightSide) {

        ParticipantIdentity leftParticipantIdentity = getParticipantIdenityFromParticipant(participantIdentities, leftParticipant);
        ParticipantIdentity rightParticipantIdentity = getParticipantIdenityFromParticipant(participantIdentities, rightParticipant);

        if (leftParticipant != null && rightParticipant != null && leftParticipantIdentity != null && rightParticipantIdentity != null) {
            //START ASSIGNING
            TextView leftSideName = (TextView) view.findViewById(R.id.left_match_individual_name);
            TextView rightSideName = (TextView) view.findViewById(R.id.right_match_individual_name);

            View leftView = view.findViewById(R.id.match_left_side_container);
            View rightView = view.findViewById(R.id.match_right_side_container);

            if (leftParticipant.getTeamId() == TeamSide.RED) {
                leftView.setBackground(activity.getResources().getDrawable(R.color.red_team_color));
                rightView.setBackground(activity.getResources().getDrawable(R.color.blue_team_color));
            } else {
                leftView.setBackground(activity.getResources().getDrawable(R.color.blue_team_color));
                rightView.setBackground(activity.getResources().getDrawable(R.color.red_team_color));
            }

            new LongHoldHelper(activity, leftView, leftParticipant, leftParticipantIdentity).init();
            new LongHoldHelper(activity, rightView, rightParticipant, rightParticipantIdentity).init();

            //POPULATES LEFT AND RIGHT SIDE
            leftSideName.setText(MatchUtils.getName(leftParticipantIdentity));
            if (mainParticipant != null && leftParticipantIdentity.getParticipantId() == mainParticipant.getParticipantId())
                leftSideName.setTextColor(activity.getResources().getColor(R.color.success_green));
            else
                leftSideName.setTextColor(activity.getResources().getColor(leftSide.getSideColor()));

            rightSideName.setText(MatchUtils.getName(rightParticipantIdentity));
            if (mainParticipant != null && rightParticipantIdentity.getParticipantId() == mainParticipant.getParticipantId())
                rightSideName.setTextColor(activity.getResources().getColor(R.color.success_green));
            else
                rightSideName.setTextColor(activity.getResources().getColor(rightSide.getSideColor()));

            View leftGameRoleLayout = view.findViewById(R.id.left_game_role_layout);
            TextView leftGameRoleText = (TextView) view.findViewById(R.id.left_game_role);
            populateGameRole(leftGameRoleLayout, leftGameRoleText, MatchUtils.getRoleFromParticipant(leftParticipant), activity);

            View rightGameRoleLayout = view.findViewById(R.id.right_game_role_layout);
            TextView rightGameRoleText = (TextView) view.findViewById(R.id.right_game_role);
            populateGameRole(rightGameRoleLayout, rightGameRoleText, MatchUtils.getRoleFromParticipant(rightParticipant), activity);

            //Populate First Kill Layout
            View leftSideFirstKill = view.findViewById(R.id.left_first_kill);
            View rightSideFirstKill = view.findViewById(R.id.right_first_kill);
            if (isFirstKill(leftParticipant))
                leftSideFirstKill.setVisibility(View.VISIBLE);
            else
                leftSideFirstKill.setVisibility(View.GONE);

            if (isFirstKill(rightParticipant))
                rightSideFirstKill.setVisibility(View.VISIBLE);
            else
                rightSideFirstKill.setVisibility(View.GONE);

            //Populate icons #background thread
            View leftIconLayout = view.findViewById(R.id.match_individual_left_icons);
            View rightIconLayout = view.findViewById(R.id.match_individual_right_icons);

            populateIconLayout(leftIconLayout, leftParticipant, activity);
            populateIconLayout(rightIconLayout, rightParticipant, activity);

            //Populate Middle Stats
            View leftMiddleLayout = view.findViewById(R.id.match_individual_left_stats);
            View rightMiddleLayout = view.findViewById(R.id.match_individual_right_stats);

            populateMiddelLayout(leftMiddleLayout, leftParticipant, activity);
            populateMiddelLayout(rightMiddleLayout, rightParticipant, activity);

            //Populate Spell Icons
            View leftSpellIcons = view.findViewById(R.id.match_individual_left_spells);
            View rightSpellIcons = view.findViewById(R.id.match_individual_right_spells);

            populateSpellsLayout(leftSpellIcons, leftParticipant, activity);
            populateSpellsLayout(rightSpellIcons, rightParticipant, activity);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void populateSpellsLayout(View view, final Participant participant, final Activity activity) {
        TextView gold = (TextView) view.findViewById(R.id.champion_gold);

        final ImageView spell0 = (ImageView) view.findViewById(R.id.icon0);
        spell0.setClickable(false);
        final ImageView spell1 = (ImageView) view.findViewById(R.id.icon1);
        spell1.setClickable(false);
        final ImageView spell2 = (ImageView) view.findViewById(R.id.icon2);
        spell2.setClickable(false);
        final ImageView spell3 = (ImageView) view.findViewById(R.id.icon3);
        spell3.setClickable(false);
        final ImageView spell4 = (ImageView) view.findViewById(R.id.icon4);
        spell4.setClickable(false);
        final ImageView spell5 = (ImageView) view.findViewById(R.id.icon5);
        spell5.setClickable(false);
        final ImageView spell6 = (ImageView) view.findViewById(R.id.icon6);
        spell6.setClickable(false);

        gold.setText(MatchUtils.getGold(participant));

        if (participant.getStats() != null)
            new LoaderHelper() {

                public Drawable mIcon1, mIcon2, mIcon3, mIcon4, mIcon5, mIcon6, mIcon0;

                @Override
                protected void postExecute() {
                    if (mIcon6 != null) {
                        spell0.setImageDrawable(mIcon6);
                    }
                    if (mIcon0 != null) {
                        spell1.setImageDrawable(mIcon0);
                    }
                    if (mIcon1 != null) {
                        spell2.setImageDrawable(mIcon1);
                    }
                    if (mIcon2 != null) {
                        spell3.setImageDrawable(mIcon2);
                    }
                    if (mIcon3 != null) {
                        spell4.setImageDrawable(mIcon3);
                    }
                    if (mIcon4 != null) {
                        spell5.setImageDrawable(mIcon4);
                    }
                    if (mIcon5 != null) {
                        spell6.setImageDrawable(mIcon5);
                    }
                }

                @Override
                protected void runInBackground() {
                    Stats stats = participant.getStats();
                    if (stats.getItem0() != 0)
                        mIcon0 = StaticDataHolder.getInstance(activity).getItemIcon(stats.getItem0());
                    if (stats.getItem1() != 0)
                        mIcon1 = StaticDataHolder.getInstance(activity).getItemIcon(stats.getItem1());
                    if (stats.getItem2() != 0)
                        mIcon2 = StaticDataHolder.getInstance(activity).getItemIcon(stats.getItem2());
                    if (stats.getItem3() != 0)
                        mIcon3 = StaticDataHolder.getInstance(activity).getItemIcon(stats.getItem3());
                    if (stats.getItem4() != 0)
                        mIcon4 = StaticDataHolder.getInstance(activity).getItemIcon(stats.getItem4());
                    if (stats.getItem5() != 0)
                        mIcon5 = StaticDataHolder.getInstance(activity).getItemIcon(stats.getItem5());
                    if (stats.getItem6() != 0)
                        mIcon6 = StaticDataHolder.getInstance(activity).getItemIcon(stats.getItem6());
                }
            }.execute();
    }

    private void populateMiddelLayout(View leftMiddleLayout, Participant participant, Activity activity) {
        TextView kills = (TextView) leftMiddleLayout.findViewById(R.id.champion_kills);
        TextView deaths = (TextView) leftMiddleLayout.findViewById(R.id.champion_deaths);
        TextView assists = (TextView) leftMiddleLayout.findViewById(R.id.champion_assists);
        TextView kda = (TextView) leftMiddleLayout.findViewById(R.id.champion_kda);

        TextView cs = (TextView) leftMiddleLayout.findViewById(R.id.champion_cs);
        TextView wards = (TextView) leftMiddleLayout.findViewById(R.id.champion_wards);
        TextView dmg = (TextView) leftMiddleLayout.findViewById(R.id.champion_dmg);

        ViewGroup achievementLayout = (ViewGroup) leftMiddleLayout.findViewById(R.id.game_achievement_layout);
        TextView achievementTextView = (TextView) leftMiddleLayout.findViewById(R.id.game_achievement);

        kills.setText(MatchUtils.getKills(participant));
        deaths.setText(MatchUtils.getDeaths(participant));
        assists.setText(MatchUtils.getAssists(participant));
        kda.setText(MatchUtils.getKDA(participant, kda, activity));

        cs.setText(MatchUtils.getCS(participant));
        dmg.setText(MatchUtils.getDmg(participant));
        wards.setText(MatchUtils.getWards(participant));

        MatchUtils.populateAchievement(achievementLayout, achievementTextView, participant);
    }

    private void populateIconLayout(View iconLayout, final Participant pariticipant, final Activity activity) {
        final CircleImageView championIcon = (CircleImageView) iconLayout.findViewById(R.id.champion_icon);
        final ImageView summonerIcon1 = (ImageView) iconLayout.findViewById(R.id.icon1);
        final ImageView summonerIcon2 = (ImageView) iconLayout.findViewById(R.id.icon2);

        TextView championName = (TextView) iconLayout.findViewById(R.id.champion_name);
        championName.setText(StaticDataHolder.getInstance(activity).getChampionName(pariticipant.getChampionId()));

        new LoaderHelper() {

            public Drawable mProfileIcon, mSummonerIcon1, mSummonerIcon2;

            @Override
            protected void postExecute() {
                if (mProfileIcon != null)
                    championIcon.setImageDrawable(mProfileIcon);
                if (mSummonerIcon1 != null)
                    summonerIcon1.setImageDrawable(mSummonerIcon1);
                if (mSummonerIcon2 != null)
                    summonerIcon2.setImageDrawable(mSummonerIcon2);
            }

            @Override
            protected void runInBackground() {
                mProfileIcon = StaticDataHolder.getInstance(activity).getChampionIcon(pariticipant.getChampionId());
                mSummonerIcon1 = StaticDataHolder.getInstance(activity).getSpellIcon(pariticipant.getSpell1Id());
                mSummonerIcon2 = StaticDataHolder.getInstance(activity).getSpellIcon(pariticipant.getSpell2Id());
            }
        }.execute();
    }

    public enum Role {
        TOP("Top", R.drawable.background_small_top), MID("Mid", R.drawable.background_small_mid), JUNGLE("Jungle", R.drawable.background_small_jg), ADC("ADC", R.drawable.background_small_adc), SUPP("Sup", R.drawable.background_small_supp), NONE("N/A", R.drawable.background_small_none), BOT("Bot", R.drawable.background_small_adc);

        private final int background;
        private final String name;

        Role(String name, int background) {
            this.name = name;
            this.background = background;
        }

        public int getBackground() {
            return background;
        }

        public String getName() {
            return name;
        }
    }

    public class RoleHelper {

        private Participant mRedParticipant;
        private Participant mBlueParticipant;

        public Participant getRedParticipant() {
            return mRedParticipant;
        }

        public void setRedParticipant(Participant redParticipant) {
            mRedParticipant = redParticipant;
        }

        public Participant getBlueParticipant() {
            return mBlueParticipant;
        }

        public void setBlueParticipant(Participant blueParticipant) {
            mBlueParticipant = blueParticipant;
        }

    }
}
