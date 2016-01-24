package com.github.koshkin.leagueoflegendsstats.holders.observable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.LeagueQueueType;
import com.github.koshkin.leagueoflegendsstats.models.LeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.MasteryIcons;
import com.github.koshkin.leagueoflegendsstats.models.MasteryTree;
import com.github.koshkin.leagueoflegendsstats.models.ObservableGame;
import com.github.koshkin.leagueoflegendsstats.models.RankedSummoner;
import com.github.koshkin.leagueoflegendsstats.models.RuneIcon;
import com.github.koshkin.leagueoflegendsstats.models.RuneIcons;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.SummonerLeagueStandings;
import com.github.koshkin.leagueoflegendsstats.models.TeamSide;
import com.github.koshkin.leagueoflegendsstats.models.Tier;
import com.github.koshkin.leagueoflegendsstats.models.match.Mastery;
import com.github.koshkin.leagueoflegendsstats.models.match.Participant;
import com.github.koshkin.leagueoflegendsstats.models.match.Rune;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;
import com.github.koshkin.leagueoflegendsstats.utils.NumberUtils;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;
import com.github.koshkin.leagueoflegendsstats.views.RoundedImageView;

import java.util.ArrayList;

/**
 * Created by tehras on 1/23/16.
 */
@SuppressWarnings("deprecation")
public class MainLayoutViewHolder {

    private final View playerLayout;

    public MainLayoutViewHolder(View view) {
        playerLayout = view.findViewById(R.id.observable_player_layout);
    }

    @SuppressLint("CutPasteId")
    public void populate(ObservableGame observableGame, SummonerLeagueStandings summonerLeagueStandings, Activity activity) {

        ArrayList<Participant> participants = observableGame.getParticipants();

        if (participants != null) {
            View redLayout1 = playerLayout.findViewById(R.id.red_player_1);
            View redLayout2 = playerLayout.findViewById(R.id.red_player_2);
            View redLayout3 = playerLayout.findViewById(R.id.red_player_3);
            View redLayout4 = playerLayout.findViewById(R.id.red_player_4);
            View redLayout5 = playerLayout.findViewById(R.id.red_player_5);
            View blueLayout1 = playerLayout.findViewById(R.id.blue_player_1);
            View blueLayout2 = playerLayout.findViewById(R.id.blue_player_2);
            View blueLayout3 = playerLayout.findViewById(R.id.blue_player_3);
            View blueLayout4 = playerLayout.findViewById(R.id.blue_player_4);
            View blueLayout5 = playerLayout.findViewById(R.id.blue_player_5);

            ArrayList<Participant> blueParticipants = getParticipants(TeamSide.BLUE, participants);
            ArrayList<Participant> redParticipants = getParticipants(TeamSide.RED, participants);

            populateView(blueParticipants.get(0), summonerLeagueStandings, blueLayout1, activity);
            populateView(blueParticipants.get(1), summonerLeagueStandings, blueLayout2, activity);
            populateView(blueParticipants.get(2), summonerLeagueStandings, blueLayout3, activity);
            populateView(blueParticipants.get(3), summonerLeagueStandings, blueLayout4, activity);
            populateView(blueParticipants.get(4), summonerLeagueStandings, blueLayout5, activity);

            populateView(redParticipants.get(0), summonerLeagueStandings, redLayout1, activity);
            populateView(redParticipants.get(1), summonerLeagueStandings, redLayout2, activity);
            populateView(redParticipants.get(2), summonerLeagueStandings, redLayout3, activity);
            populateView(redParticipants.get(3), summonerLeagueStandings, redLayout4, activity);
            populateView(redParticipants.get(4), summonerLeagueStandings, redLayout5, activity);
        } else {
            //todo show error
        }
    }

    private void populateView(Participant participant, SummonerLeagueStandings summonerLeagueStandings, View view, Activity activity) {
        if (participant != null) {
            if (participant.getTeamId() == TeamSide.RED)
                view.setBackground(activity.getResources().getDrawable(R.color.red_team_color));
            else
                view.setBackground(activity.getResources().getDrawable(R.color.blue_team_color));

            TextView summonerName = (TextView) view.findViewById(R.id.summoner_name);
            TextView championName = (TextView) view.findViewById(R.id.champion_name);
            summonerName.setText(participant.getSummonerName());
            championName.setText(StaticDataHolder.getInstance(activity).getChampionName(participant.getChampionId()));

            populateChampionAndSummonerIcons(participant, activity, view);
            populateRunes(participant, activity, view);
            populateMasteries(participant, activity, view);
            populateDivision(participant, summonerLeagueStandings, activity, view);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void populateDivision(Participant participant, SummonerLeagueStandings summonerLeagueStandings, final Activity activity, View view) {
        String id = String.valueOf(participant.getSummonerId());

        final ImageView divisionIcon = (ImageView) view.findViewById(R.id.division_icon);
        TextView seasonRankings = (TextView) view.findViewById(R.id.season_ranking);
        TextView seasonWins = (TextView) view.findViewById(R.id.season_wins);
        TextView seasonLosses = (TextView) view.findViewById(R.id.season_losses);

        if (summonerLeagueStandings != null && summonerLeagueStandings.getLeagueStandingsHashMap() != null && summonerLeagueStandings.getLeagueStandingsHashMap().containsKey(id)) {
            ArrayList<LeagueStandings> leagueStandings = summonerLeagueStandings.getLeagueStandingsHashMap().get(id);

            LeagueStandings leagueStandings1 = getLeagueStanding(leagueStandings);
            if (leagueStandings1 != null) {
                RankedSummoner rankedSummoner = findIdInLeagueStandings(leagueStandings1, id);

                if (rankedSummoner != null) {
                    long wins = rankedSummoner.getWins();
                    long losses = rankedSummoner.getLosses();
                    long leaguePoints = rankedSummoner.getLeaguePoints();
                    String division = rankedSummoner.getDivision();
                    final Tier tier = leagueStandings1.getTier();

                    if (tier == Tier.CHALLENGER || tier == Tier.MASTER)
                        division = "";

                    seasonWins.setText(String.valueOf(wins) + "W");
                    seasonLosses.setText(String.valueOf(losses) + "L");
                    seasonRankings.setText(getRanking(division, tier, leaguePoints));
                    final String finalDivision = division;
                    new LoaderHelper() {

                        private Drawable mDrawable;

                        @Override
                        protected void postExecute() {
                            if (mDrawable != null) {
                                divisionIcon.setImageDrawable(mDrawable);
                            } else {
                                divisionIcon.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        protected void runInBackground() {
                            mDrawable = StaticDataHolder.getInstance(activity).getRankTier(tier, finalDivision);
                        }
                    }.execute();

                    return;
                }
            }
        }
        seasonWins.setVisibility(View.GONE);
        seasonLosses.setVisibility(View.GONE);
        divisionIcon.setVisibility(View.GONE);
        seasonRankings.setText(NO_RANK);

    }

    private LeagueStandings getLeagueStanding(ArrayList<LeagueStandings> leagueStandings) {
        if (leagueStandings != null && leagueStandings.size() > 0) {
            for (LeagueStandings leagueStandings1 : leagueStandings) {
                if (leagueStandings1.getQueueType() == LeagueQueueType.RANKED_SOLO_5x5)
                    return leagueStandings1;
            }
        }

        return null;
    }

    private String getRanking(String divison, Tier tier, long leaguePoints) {
        return tier.getName() + (!NullChecker.isNullOrEmpty(divison) ? " " : "") + divison + " (" + String.valueOf(leaguePoints) + "LP)";
    }

    private RankedSummoner findIdInLeagueStandings(LeagueStandings leagueStandings, String id) {
        if (leagueStandings != null) {
            for (RankedSummoner rankedSummoner : leagueStandings.getEntries()) {
                if (rankedSummoner.getPlayerOrTeamId().equalsIgnoreCase(id))
                    return rankedSummoner;
            }
        }
        return null;
    }

    private static final String NO_RANK = "Rank is Unavailable";

    @SuppressLint("SetTextI18n")
    private void populateMasteries(Participant participant, Activity activity, View view) {
        TextView masteries = (TextView) view.findViewById(R.id.masteries);
        View masteriesBg = view.findViewById(R.id.masteries_background);

        TextView ferocity = (TextView) view.findViewById(R.id.feroctiy_text);
        TextView cunning = (TextView) view.findViewById(R.id.cunning_text);
        TextView resolve = (TextView) view.findViewById(R.id.resolve_text);

        int fer = getMastery(participant, activity, MasteryIcons.FEROCITY);
        int cun = getMastery(participant, activity, MasteryIcons.CUNNING);
        int res = getMastery(participant, activity, MasteryIcons.RESOLVE);

        ferocity.setText(Html.fromHtml("<b>" + String.valueOf(fer) + "</b> " + MasteryIcons.FEROCITY));
        cunning.setText(Html.fromHtml("<b>" + String.valueOf(cun) + "</b> " + MasteryIcons.CUNNING));
        resolve.setText(Html.fromHtml("<b>" + String.valueOf(res) + "</b> " + MasteryIcons.RESOLVE));

        if (fer >= cun && fer >= res) {
            masteriesBg.setBackground(activity.getResources().getDrawable(R.drawable.background_rounded_rectangle_ferocity));
        } else if (cun >= fer && cun >= res) {
            masteriesBg.setBackground(activity.getResources().getDrawable(R.drawable.background_rounded_rectangle_cunning));
        } else {
            masteriesBg.setBackground(activity.getResources().getDrawable(R.drawable.background_rounded_rectangle_resolve));
        }
        masteries.setText(String.valueOf(fer) + "/" + String.valueOf(cun) + "/" + String.valueOf(res));
    }

    private int getMastery(Participant participant, Activity ac, String tree) {
        MasteryIcons masteryIcons = StaticDataHolder.getInstance(ac).getMasteryIcons();
        ArrayList<ArrayList<MasteryTree>> treeIcons = masteryIcons.getTreeMap().get(tree);

        int masteryIconsInt = 0;
        if (treeIcons != null) {
            for (Mastery mastery : participant.getMasteries()) {
                boolean wasUpdated = false;
                for (ArrayList<MasteryTree> masteryTrees : treeIcons) {
                    for (MasteryTree masteryTree : masteryTrees) {
                        if (mastery.getMasteryId() == masteryTree.getMasteryId()) {
                            masteryIconsInt = masteryIconsInt + mastery.getRank();
                            wasUpdated = true;
                            break;
                        }
                    }
                    if (wasUpdated)
                        break;
                }
            }
        }

        return masteryIconsInt;
    }

    private void populateRunes(Participant participant, Activity activity, View view) {
        ViewGroup runeContent = (ViewGroup) view.findViewById(R.id.runes_content);

        ArrayList<CharSequence> runes = getRunes(participant, activity);

        for (CharSequence rune : runes) {
            runeContent.addView(getRuneView(rune, activity));
        }
    }

    private View getRuneView(CharSequence rune, Activity activity) {
        @SuppressLint("InflateParams") View view = activity.getLayoutInflater().inflate(R.layout.partial_run_view, null);
        ((TextView) view.findViewById(R.id.text)).setText(rune);

        return view;
    }

    private ArrayList<CharSequence> getRunes(Participant participant, Activity activity) {
        ArrayList<Rune> runes = (ArrayList<Rune>) participant.getRunes();
        ArrayList<CharSequence> output = new ArrayList<>();
        RuneIcons runeIcons = StaticDataHolder.getInstance(activity).getRuneIcons();
        for (Rune rune : runes) {
            RuneIcon runeIcon = runeIcons.getRuneIcons().get(String.valueOf(rune.getRuneId()));
            if (runeIcon != null) {
                for (String string : runeIcon.getStats().keySet()) {
                    String convertedKey = getConvertedKey(string);

                    double amount = runeIcon.getStats().get(string);
                    if (convertedKey.contains("%"))
                        amount = amount * 100d;
                    String value = NumberUtils.twoDecimals(amount);

                    output.add(Html.fromHtml(rune.getCount() + "x<b>" + value + (convertedKey.contains("%") ? "" : " ") + convertedKey + "</b>"));
                }
            }
        }

        return output;
    }

    private String getConvertedKey(String string) {
        if (string.startsWith("r")) {
            string = string.substring(1);
        }

        string = string.replaceAll("Mod", "");
        string = string.replaceAll("Level", "Lvl");
        string = string.replaceAll("Percent", "%");
        string = string.replaceAll("Per", "/");

        String output = "";
        for (int i = 0; i < string.length(); i++) {
            Character c = string.charAt(i);

            if (Character.isUpperCase(c) && i != 0)
                output = output + " " + c;
            else
                output = output + c;
        }

        return output;
    }

    private void populateChampionAndSummonerIcons(final Participant participant, final Activity activity, View view) {
        final RoundedImageView championIcon = (RoundedImageView) view.findViewById(R.id.champion_icon);
        final ImageView item1 = (ImageView) view.findViewById(R.id.item_1);
        final ImageView item2 = (ImageView) view.findViewById(R.id.item_2);

        new LoaderHelper() {

            Drawable championIconD, item1D, item2D;

            @Override
            protected void postExecute() {
                if (championIconD != null)
                    championIcon.setImageDrawable(championIconD);
                if (item1D != null)
                    item1.setImageDrawable(item1D);
                if (item2D != null)
                    item2.setImageDrawable(item2D);
            }

            @Override
            protected void runInBackground() {
                championIconD = StaticDataHolder.getInstance(activity).getChampionIcon(participant.getChampionId());
                item1D = StaticDataHolder.getInstance(activity).getSpellIcon(participant.getSpell1Id());
                item2D = StaticDataHolder.getInstance(activity).getSpellIcon(participant.getSpell2Id());
            }
        }.execute();
    }

    private ArrayList<Participant> getParticipants(TeamSide side, ArrayList<Participant> participants) {
        ArrayList<Participant> participants1 = new ArrayList<>();

        for (Participant participant : participants) {
            if (participant.getTeamId() == side)
                participants1.add(participant);
        }

        return participants1;
    }
}
