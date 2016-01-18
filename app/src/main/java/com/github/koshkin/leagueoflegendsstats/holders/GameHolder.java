package com.github.koshkin.leagueoflegendsstats.holders;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.fragments.MatchFragment;
import com.github.koshkin.leagueoflegendsstats.models.Game;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.utils.GameUtils;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.LoaderHelper;

import static com.github.koshkin.leagueoflegendsstats.utils.GameUtils.gameLength;
import static com.github.koshkin.leagueoflegendsstats.utils.GameUtils.gameStartedDate;
import static com.github.koshkin.leagueoflegendsstats.utils.GameUtils.generateGameType;

/**
 * Created by tehras on 1/13/16.
 * <p/>
 * Common holder to create Game History view
 */
public class GameHolder extends RecyclerView.ViewHolder {

    private final View divider;
    private final View mItemView;
    public TextView gameTypeTv, role, gameStartedTv, gameLengthTv, gameResult, champNameTv, kdaTv, killsDeathsAssistsTv, achievementText, levelTv, csTv, gameWardsTv, killPartTv;
    public ImageView champImage, icon1, icon2, ribbon;
    public View achievementLayout;

    public GameHolder(View view) {
        super(view);

        mItemView = view.findViewById(R.id.material_button_layout);

        ribbon = (ImageView) view.findViewById(R.id.ribbon);

        gameTypeTv = (TextView) view.findViewById(R.id.game_type);
        gameStartedTv = (TextView) view.findViewById(R.id.game_started);
        gameLengthTv = (TextView) view.findViewById(R.id.game_length);

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

        gameResult = (TextView) view.findViewById(R.id.game_result);

        role = (TextView) view.findViewById(R.id.game_role);

        divider = view.findViewById(R.id.divider);
    }

    public void populate(final Game game, final Activity activity, final String summonerId, boolean t) {
        //TOP Layout
        this.gameTypeTv.setText(generateGameType(game));
        this.gameStartedTv.setText(gameStartedDate(game));
        this.gameLengthTv.setText(gameLength(game));

        new LoaderHelper() {

            Drawable mDrawable, mIcon1Drawable, mIcon2Drawable;

            @Override
            protected void postExecute() {
                if (mDrawable != null)
                    GameHolder.this.champImage.setImageDrawable(mDrawable);
                if (mIcon1Drawable != null)
                    GameHolder.this.icon1.setImageDrawable(mIcon1Drawable);
                if (mIcon2Drawable != null)
                    GameHolder.this.icon2.setImageDrawable(mIcon2Drawable);

                mDrawable = StaticDataHolder.getInstance(activity).getChampionIcon(game.getChampionId());

                mIcon1Drawable = StaticDataHolder.getInstance(activity).getSpellIcon(game.getSpell1());
                mIcon2Drawable = StaticDataHolder.getInstance(activity).getSpellIcon(game.getSpell2());
            }

            @Override
            protected void runInBackground() {
                mDrawable = StaticDataHolder.getInstance(activity).getChampionIcon(game.getChampionId());
                mIcon1Drawable = StaticDataHolder.getInstance(activity).getSpellIcon(game.getSpell1());
                mIcon2Drawable = StaticDataHolder.getInstance(activity).getSpellIcon(game.getSpell2());
            }
        }.execute();

        this.champNameTv.setText(StaticDataHolder.getInstance(activity).getChampionName(game.getChampionId()));

        //KDA game stats and achievement
        this.kdaTv.setText(GameUtils.kda(game, this.kdaTv, activity));
        this.killsDeathsAssistsTv.setText(GameUtils.killsDeath(game));
        GameUtils.setUpAchievementLayout(this.achievementLayout, this.achievementText, game);

        //Other Stats
        this.levelTv.setText(GameUtils.levelText(game));
        this.csTv.setText(GameUtils.creepScore(game));
        this.gameWardsTv.setText(GameUtils.gameWards(game));
        this.killPartTv.setText(GameUtils.killPart(game));

        //Game result
        this.gameResult.setText(GameUtils.isAWin(this.gameResult, game, ribbon, activity));

        //Role
        GameUtils.gameRole(this.role, game, activity);

        if (t)
            divider.setVisibility(View.GONE);

        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).startFragment(MatchFragment.getInstance(summonerId, String.valueOf(game.getGameId())));
            }
        });
    }
}
