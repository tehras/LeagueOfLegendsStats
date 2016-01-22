package com.github.koshkin.leagueoflegendsstats.utils;

import android.app.Activity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.Game;
import com.github.koshkin.leagueoflegendsstats.models.GameStats;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.github.koshkin.leagueoflegendsstats.utils.NullChecker.isNullOrEmpty;
import static com.github.koshkin.leagueoflegendsstats.utils.Utils.NOT_AVAILABLE;

/**
 * Created by tehras on 1/12/16.
 * <p/>
 * For game
 */
public class GameUtils {
    public static String generateGameType(Game game) {
        String gameType = "";
        if (game.getSubType() != Game.SubType.NONE) {
            String subType = game.getSubType().toString();

            String[] words = subType.split("_");
            for (String word : words) {
                word = capitalizeFirstLetter(word.toLowerCase()).replace("x", "v");
                gameType = gameType + (isNullOrEmpty(gameType) ? "" : " ") + word;
            }
        }

        return gameType;
    }

    public static String capitalizeFirstLetter(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static String gameStartedDate(Game game) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(game.getCreateDate());

        if (isWithinAnHour(c)) {
            return capitalizeFirstLetter(minutesBeforeRightNow(c) + " minutes ago");
        } else if (isWithinADay(c))
            return capitalizeFirstLetter(hoursBeforeRightNow(c)) + " hours ago";
        else if (isYesterday(c))
            return "Yesterday";

        return daysBeforeRightNow(c) + " days ago";
    }

    private static String daysBeforeRightNow(Calendar c) {
        Calendar rightNow = Calendar.getInstance();

        long diff = rightNow.getTimeInMillis() - c.getTimeInMillis();

        return String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }

    private static boolean isYesterday(Calendar c) {
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -2);

        return yesterday.before(c);
    }

    private static String minutesBeforeRightNow(Calendar c) {
        Calendar rightNow = Calendar.getInstance();
        long diff = rightNow.getTimeInMillis() - c.getTimeInMillis();

        return String.valueOf(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS));
    }

    private static String hoursBeforeRightNow(Calendar c) {
        Calendar rightNow = Calendar.getInstance();
        long diff = rightNow.getTimeInMillis() - c.getTimeInMillis();

        return String.valueOf(TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS));
    }

    private static boolean isWithinADay(Calendar c) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.HOUR, -24);
        return today.before(c);
    }

    private static boolean isWithinAnHour(Calendar c) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.HOUR, -1);
        return today.before(c);
    }

    public static String gameLength(Game game) {
        if (game.getGameStats() == null)
            return "";

        long gameTime = game.getGameStats().getTimePlayed();

        long minutes = gameTime / (60);
        long seconds = gameTime - (minutes * 60);

        return String.valueOf(minutes) + "m " + String.valueOf(seconds) + "s";
    }

    public static String kda(Game game, TextView kdaView, Activity activity) {
        if (game.getGameStats() == null) {
            kdaView.setTextColor(Utils.getKDAColor(0d, activity));
            return NOT_AVAILABLE;
        }

        GameStats gameStats = game.getGameStats();

        double kills = gameStats.getChampionsKilled();
        double deaths = gameStats.getNumDeaths();
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

    public static String killsDeath(Game game) {
        if (game.getGameStats() == null)
            return NOT_AVAILABLE;

        GameStats gameStats = game.getGameStats();

        double kills = gameStats.getChampionsKilled();
        double deaths = gameStats.getNumDeaths();
        double assists = gameStats.getAssists();

        return String.valueOf(kills) + " / " + deaths + " / " + assists;

    }

    public static void setUpAchievementLayout(View achievementLayout, TextView achievementText, Game game) {
        if (game.getGameStats() != null) {
            GameStats gameStats = game.getGameStats();

            String achievement = "";
            if (gameStats.getPentaKills() > 0) {
                achievement = getNum(gameStats.getPentaKills()) + "Penta Kill" + moreThanOne(gameStats.getPentaKills());
            } else if (gameStats.getQuadraKills() > 0) {
                achievement = getNum(gameStats.getQuadraKills()) + "Quadra Kill" + moreThanOne(gameStats.getPentaKills());
            } else if (gameStats.getTripleKills() > 0) {
                achievement = getNum(gameStats.getTripleKills()) + "Triple Kill" + moreThanOne(gameStats.getTripleKills());
            } else if (gameStats.getDoubleKills() > 0) {
                achievement = getNum(gameStats.getDoubleKills()) + "Double Kill" + moreThanOne(gameStats.getDoubleKills());
            }

            if (isNullOrEmpty(achievement)) {
                achievementLayout.setVisibility(View.GONE);
            } else {
                achievementLayout.setVisibility(View.VISIBLE);
                achievementText.setText(achievement);
            }
        } else {
            achievementLayout.setVisibility(View.GONE);
        }
    }

    private static String moreThanOne(int pentaKills) {
        if (pentaKills == 1) {
            return "";
        } else {
            return "s";
        }
    }

    private static String getNum(int pentaKills) {
        if (pentaKills == 1)
            return "";
        else
            return String.valueOf(pentaKills) + " ";
    }

    public static String levelText(Game game) {
        if (game.getGameStats() == null)
            return "";

        return "Level " + String.valueOf(game.getGameStats().getLevel());
    }

    public static String creepScore(Game game) {
        if (game.getGameStats() == null)
            return "";

        int minions = game.getGameStats().getMinionsKilled();
        int neutralMinions = game.getGameStats().getNeutralMinionsKilled();

        return String.valueOf(minions + neutralMinions) + " CS";
    }

    public static Spanned gameWards(Game game) {
        if (game.getGameStats() == null)
            return new SpannableString("");

        int wards = game.getGameStats().getWardPlaced();
        String HTML_WARDS = "Placed <b>%s</b> Wards";
        if (wards == 0) {
            String NO_HTML_WARDS = "No Wards Placed";
            return Html.fromHtml(NO_HTML_WARDS);
        } else
            return Html.fromHtml(String.format(HTML_WARDS, wards));
    }

    public static Spanned killPart(Game game) {
        if (game.getGameStats() == null)
            return new SpannableString(NOT_AVAILABLE);

        GameStats gameStats = game.getGameStats();
        double damage = gameStats.getTotalDamageDealtToChampions();
        String output;
        if (damage > 1000d) {
            output = "<b>" + NumberUtils.oneDecimalSafely(damage / 1000d) + "</b>" + "K dmg dealt";
        } else {
            output = "<b>" + String.valueOf((int) damage) + "</b>" + " dmg dealt";
        }

        return Html.fromHtml(output);
    }

    @SuppressWarnings("deprecation")
    public static String isAWin(TextView textView, Game game, ImageView ribbon, Activity activity) {
        if (game.getGameStats() == null)
            return "Win";

        GameStats gameStats = game.getGameStats();

        if (gameStats.isWin()) {
            textView.setTextColor(activity.getResources().getColor(R.color.success_green));
            ribbon.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_action_bookmark_green));
            return "Win";
        } else {
            textView.setTextColor(activity.getResources().getColor(R.color.failed_red));
            ribbon.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_action_bookmark));
            return "Loss";
        }
    }

    public static void gameRole(TextView role, Game game, Activity activity) {
        if (game.getGameStats() == null) {
            ((ViewGroup) role.getParent()).setVisibility(View.GONE);
        }

        GameStats.PlayerRole playerRole = game.getGameStats().getPlayerRole();
        GameStats.PlayerPosition playerPosition = game.getGameStats().getPlayerPosition();

        String roleString;
        int drawable;

        switch (playerPosition) {
            case MIDDLE:
                roleString = "Middle";
                drawable = R.drawable.background_mid;
                break;
            case TOP:
                roleString = "TOP";
                drawable = R.drawable.background_top;
                break;
            case JUNGLE:
                roleString = "Jungle";
                drawable = R.drawable.background_jg;
                break;
            default:
                if (playerRole == GameStats.PlayerRole.SUPPORT) {
                    roleString = "Support";
                    drawable = R.drawable.background_supp;
                } else {
                    roleString = "ADC";
                    drawable = R.drawable.background_adc;
                }
                break;
        }

        role.setText(roleString);
        ((ViewGroup) role.getParent()).setBackground(activity.getResources().getDrawable(drawable, null));
    }
}
