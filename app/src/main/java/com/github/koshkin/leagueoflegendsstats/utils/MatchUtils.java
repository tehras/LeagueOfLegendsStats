package com.github.koshkin.leagueoflegendsstats.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.models.TeamSide;
import com.github.koshkin.leagueoflegendsstats.models.match.Participant;
import com.github.koshkin.leagueoflegendsstats.models.match.ParticipantIdentity;
import com.github.koshkin.leagueoflegendsstats.models.match.Stats;
import com.github.koshkin.leagueoflegendsstats.models.match.Timeline;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.match.MiddleIndividualLayoutHolder;

import java.util.ArrayList;
import java.util.List;

import static com.github.koshkin.leagueoflegendsstats.utils.Utils.NOT_AVAILABLE;

/**
 * Created by tehras on 1/17/16.
 * <p/>
 * Match Utils to help with the view
 */
public class MatchUtils {

    public static ParticipantIdentity getIdentityFromSummonerId(ArrayList<ParticipantIdentity> participantIdentities, long summonerId) {
        if (participantIdentities != null)
            for (ParticipantIdentity participantIdentity : participantIdentities) {
                if (participantIdentity.getPlayer() != null && summonerId == participantIdentity.getPlayer().getSummonerId())
                    return participantIdentity;
            }

        return null;
    }

    public static Participant getParticipantFromParticipantIdentity(List<Participant> participants, ParticipantIdentity currentPlayer) {
        if (currentPlayer != null)
            for (Participant participant : participants) {
                if (participant.getParticipantId() == currentPlayer.getParticipantId())
                    return participant;
            }

        return participants.get(0);
    }

    public static TeamSide getCurrentSideFromParticipantIdentity(Participant currentPart) {
        if (currentPart != null)
            return currentPart.getTeamId();

        return TeamSide.BLUE;
    }

    public static MiddleIndividualLayoutHolder.Role getRoleFromParticipant(Participant participant) {
        if (participant != null && participant.getTimeline() != null) {
            Timeline.Role role = participant.getTimeline().getRole();
            Timeline.Lane lane = participant.getTimeline().getLane();

            switch (lane) {
                case TOP:
                    return MiddleIndividualLayoutHolder.Role.TOP;
                case MID:
                    return MiddleIndividualLayoutHolder.Role.MID;
                case MIDDLE:
                    return MiddleIndividualLayoutHolder.Role.MID;
                case JUNGLE:
                    return MiddleIndividualLayoutHolder.Role.JUNGLE;
                case BOT:
                    if (role == Timeline.Role.DUO_CARRY)
                        return MiddleIndividualLayoutHolder.Role.ADC;
                    else
                        return MiddleIndividualLayoutHolder.Role.SUPP;
                case BOTTOM:
                    if (role == Timeline.Role.DUO_CARRY)
                        return MiddleIndividualLayoutHolder.Role.ADC;
                    else if (role == Timeline.Role.DUO_SUPPORT)
                        return MiddleIndividualLayoutHolder.Role.SUPP;
                    else
                        return MiddleIndividualLayoutHolder.Role.BOT;
                default:
                    return MiddleIndividualLayoutHolder.Role.NONE;
            }
        }

        return MiddleIndividualLayoutHolder.Role.NONE;
    }

    public static ParticipantIdentity getParticipantIdenityFromParticipant(List<ParticipantIdentity> participantIdentities, Participant participant) {
        if (participant != null)
            for (ParticipantIdentity participantIdentity : participantIdentities) {
                if (participant.getParticipantId() == participantIdentity.getParticipantId())
                    return participantIdentity;
            }

        return null;
    }

    public static boolean isFirstKill(Participant leftPariticipant) {
        return leftPariticipant != null && leftPariticipant.getStats() != null && leftPariticipant.getStats().isFirstBloodKill();
    }

    public static void populateGameRole(View gameRoleLayout, TextView gameRoleText, MiddleIndividualLayoutHolder.Role role, Activity activity) {
        gameRoleText.setText(role.getName());
        //noinspection deprecation
        gameRoleLayout.setBackground(activity.getResources().getDrawable(role.getBackground()));
    }

    public static String getKills(Participant participant) {
        if (participant.getStats() == null)
            return Utils.NOT_AVAILABLE;

        return String.valueOf(participant.getStats().getKills());
    }

    public static String getDeaths(Participant participant) {
        if (participant.getStats() == null)
            return Utils.NOT_AVAILABLE;

        return String.valueOf(participant.getStats().getDeaths());
    }

    public static String getAssists(Participant participant) {
        if (participant.getStats() == null)
            return Utils.NOT_AVAILABLE;

        return String.valueOf(participant.getStats().getAssists());
    }

    public static String getKDA(Participant participant) {
        if (participant.getStats() == null)
            return Utils.NOT_AVAILABLE;
        int kills = participant.getStats().getKills();
        int deaths = participant.getStats().getDeaths();
        int assists = participant.getStats().getAssists();

        return String.valueOf(kills) + "/" + String.valueOf(deaths) + "/" + String.valueOf(assists);
    }

    public static String getKDA(Participant participant, TextView kdaView, Activity activity) {
        if (participant.getStats() == null) {
            kdaView.setTextColor(Utils.getKDAColor(0d, activity));
            return NOT_AVAILABLE;
        }

        Stats gameStats = participant.getStats();

        double kills = gameStats.getKills();
        double deaths = gameStats.getDeaths();
        double assists = gameStats.getAssists();

        if (deaths == 0d) {
            if (kills == 0d && assists == 0d) {
                kdaView.setTextColor(Utils.getKDAColor(3.5d, activity));
                return "No KDA";
            } else {
                kdaView.setTextColor(Utils.getKDAColor(10d, activity));
                return "Perfect KDA";
            }
        }

        double kda = ((kills + assists) / deaths);

        kdaView.setTextColor(Utils.getKDAColor(kda, activity));
        return NumberUtils.oneDecimalSafely(kda) + ":1 KDA";
    }

    public static Spanned getCS(Participant participant) {
        if (participant.getStats() == null)
            return Html.fromHtml(Utils.NOT_AVAILABLE);

        int minions = participant.getStats().getMinionsKilled() + participant.getStats().getNeutralMinionsKilled();

        return Html.fromHtml("<b>" + String.valueOf(minions) + "</b> CS");
    }

    public static Spanned getDmg(Participant participant) {
        if (participant.getStats() == null)
            return Html.fromHtml(Utils.NOT_AVAILABLE);

        double dmg = participant.getStats().getTotalDamageDealtToChampions();

        return Html.fromHtml("<b>" + NumberUtils.oneDecimalSafely(dmg / 1000) + "K</b> dmg dealt");
    }

    public static Spanned getWards(Participant participant) {
        if (participant.getStats() == null)
            return Html.fromHtml(Utils.NOT_AVAILABLE);

        int wards = participant.getStats().getWardsPlaced();
        String HTML_WARDS = "Placed <b>%s</b> Wards";
        if (wards == 0) {
            String NO_HTML_WARDS = "No Wards Placed";
            return Html.fromHtml(NO_HTML_WARDS);
        } else
            return Html.fromHtml(String.format(HTML_WARDS, wards));
    }

    public static void populateAchievement(ViewGroup achievementLayout, TextView achievementTextView, Participant participant) {
        if (participant.getStats() == null) {
            achievementLayout.setVisibility(View.GONE);
        }

        Stats stats = participant.getStats();

        achievementLayout.setVisibility(View.VISIBLE);
        if (stats.getPentaKills() > 0) {
            achievementTextView.setText(getAchievementText(stats.getPentaKills(), "Penta Kill"));
        } else if (stats.getQuadraKills() > 0) {
            achievementTextView.setText(getAchievementText(stats.getQuadraKills(), "Quadra Kill"));
        } else if (stats.getTripleKills() > 0) {
            achievementTextView.setText(getAchievementText(stats.getTripleKills(), "Triple Kill"));
        } else if (stats.getDoubleKills() > 0) {
            achievementTextView.setText(getAchievementText(stats.getDoubleKills(), "Double Kill"));
        } else {
            achievementLayout.setVisibility(View.GONE);
        }
    }

    private static String getAchievementText(int number, String restOfMessage) {
        if (number == 1)
            return restOfMessage;
        else {
            return String.valueOf(number) + " " + restOfMessage + "s";
        }
    }

    public static String getGold(Participant participant) {
        if (participant == null || participant.getStats() == null)
            return Utils.NOT_AVAILABLE;

        return NumberUtils.oneDecimalSafely(((double) participant.getStats().getGoldEarned()) / 1000) + "K Gold";

    }

    @SuppressLint("SetTextI18n")
    public static void populateKDAandGold(TextView lefGold, TextView rightGold, TextView leftKda, TextView rightKDA, ArrayList<Participant> participants, TeamSide leftSide) {
        int leftKills = 0, rightKills = 0, leftAssists = 0, rightAssists = 0, leftDeaths = 0, rightDeaths = 0;
        long leftGoldTotal = 0, rightGoldTotal = 0;

        for (Participant participant : participants) {
            if (participant.getStats() != null) {
                Stats stats = participant.getStats();
                int kills = stats.getKills();
                int deaths = stats.getDeaths();
                int assists = stats.getAssists();
                long gold = stats.getGoldEarned();

                if (participant.getTeamId() == leftSide) {
                    leftKills = leftKills + kills;
                    leftDeaths = leftDeaths + deaths;
                    leftAssists = leftAssists + assists;
                    leftGoldTotal = leftGoldTotal + gold;
                } else {
                    rightKills = rightKills + kills;
                    rightDeaths = rightDeaths + deaths;
                    rightAssists = rightAssists + assists;
                    rightGoldTotal = rightGoldTotal + gold;
                }
            }
        }

        double leftGoldDouble = ((double) leftGoldTotal) / 1000d;
        double rightGoldDouble = ((double) rightGoldTotal) / 1000d;

        lefGold.setText(NumberUtils.oneDecimalSafely(leftGoldDouble) + "K Gold");
        rightGold.setText(NumberUtils.oneDecimalSafely(rightGoldDouble) + "K Gold");
        if (leftGoldDouble < rightGoldDouble)
            lefGold.setTypeface(Typeface.DEFAULT);
        else if (leftGoldDouble > rightGoldDouble)
            rightGold.setTypeface(Typeface.DEFAULT);

        leftKda.setText(String.valueOf(leftKills) + "/" + String.valueOf(leftDeaths) + "/" + String.valueOf(leftAssists));
        rightKDA.setText(String.valueOf(rightKills) + "/" + String.valueOf(rightDeaths) + "/" + String.valueOf(rightAssists));
        if (leftKills < rightKills)
            leftKda.setTypeface(Typeface.DEFAULT);
        else if (leftKills > rightKills)
            rightKDA.setTypeface(Typeface.DEFAULT);

    }

    public static String getName(ParticipantIdentity leftParticipantIdentity) {
        if (leftParticipantIdentity.getPlayer() == null)
            return Utils.NOT_AVAILABLE;

        return leftParticipantIdentity.getPlayer().getSummonerName();
    }

    public static CharSequence getLevel(Participant participant) {
        return Html.fromHtml("Level " + "<b>" + String.valueOf(participant.getStats().getChampLevel()) + "</b>");
    }

    public static String kdaCalculated(Participant participant, TextView kdaView, Activity activity) {
        if (participant.getStats() == null) {
            kdaView.setTextColor(Utils.getKDAColor(0d, activity));
            return NOT_AVAILABLE;
        }

        Stats gameStats = participant.getStats();

        double kills = gameStats.getKills();
        double deaths = gameStats.getDeaths();
        double assists = gameStats.getAssists();

        if (deaths == 0d) {
            if (kills == 0d && assists == 0d) {
                kdaView.setTextColor(Utils.getKDAColor(3.5d, activity));
                return "No KDA";
            } else {
                kdaView.setTextColor(Utils.getKDAColor(10d, activity));
                return "Perfect KDA";
            }
        }

        double kda = ((kills + assists) / deaths);

        kdaView.setTextColor(Utils.getKDAColor(kda, activity));
        return NumberUtils.oneDecimalSafely(kda) + ":1 KDA";
    }

    public static CharSequence gameWards(Participant participant) {
        if (participant.getStats() == null)
            return new SpannableString("");

        int wards = participant.getStats().getWardsPlaced();
        String HTML_WARDS = "Placed <b>%s</b> Wards";
        if (wards == 0) {
            String NO_HTML_WARDS = "No Wards Placed";
            return Html.fromHtml(NO_HTML_WARDS);
        } else
            return Html.fromHtml(String.format(HTML_WARDS, wards));
    }

    public static Spanned dmgDealt(Participant participant) {
        if (participant.getStats() == null)
            return new SpannableString(NOT_AVAILABLE);

        Stats gameStats = participant.getStats();
        double damage = gameStats.getTotalDamageDealtToChampions();
        String output;
        if (damage > 1000d) {
            output = "<b>" + NumberUtils.oneDecimalSafely(damage / 1000d) + "</b>" + "K dmg dealt";
        } else {
            output = "<b>" + String.valueOf((int) damage) + "</b>" + " dmg dealt";
        }

        return Html.fromHtml(output);
    }
}
