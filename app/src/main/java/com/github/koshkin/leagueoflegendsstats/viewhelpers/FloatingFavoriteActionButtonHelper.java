package com.github.koshkin.leagueoflegendsstats.viewhelpers;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummoner;
import com.github.koshkin.leagueoflegendsstats.sqlite.FavoritesSqLiteHelper;

/**
 * Created by tehras on 1/18/16.
 * <p/>
 * FloatingActionButtonHelper
 */
@SuppressWarnings("deprecation")
public class FloatingFavoriteActionButtonHelper {

    private final FloatingActionButton mFab;
    private final Activity mActivity;
    private final String mSummonerId;
    private boolean mIsAlreadyFav;
    private FavoriteCallback mFavoriteCallback;

    public FloatingFavoriteActionButtonHelper(FloatingActionButton fab, Activity activity, FavoriteCallback favorite, String summonerId) {
        mFab = fab;
        mActivity = activity;
        mFavoriteCallback = favorite;
        mSummonerId = summonerId;
    }

    public boolean refresh(SimpleSummoner simpleSummoner, String summonerName) {
        SimpleSummoner favorites = FavoritesSqLiteHelper.getFavorite(summonerName);

        boolean isAlreadyFav = favorites != null;

        if (isAlreadyFav)
            FavoritesSqLiteHelper.updateFavorite(simpleSummoner);

        if (isAlreadyFav)
            mFab.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_fav_fab_disable));
        else
            mFab.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_fav_fab_enable));

        mFab.show();

        return isAlreadyFav;
    }

    public void init() {
        mIsAlreadyFav = refresh(null, mSummonerId);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleSummoner simpleSummoner = mFavoriteCallback.getFavorite();

                String text;
                if (mIsAlreadyFav) {
                    text = "Removed from Favorites";
                    removeFromFavorites(simpleSummoner);
                } else {
                    text = "Added to Favorites";
                    addToFavorites(mFavoriteCallback.getFavorite());
                }
                Snackbar.make(mFab, text, Snackbar.LENGTH_SHORT).show();
                mIsAlreadyFav = refresh(simpleSummoner, mSummonerId);
            }
        });
    }

    private void removeFromFavorites(SimpleSummoner simpleSummoner) {
        FavoritesSqLiteHelper.deleteFavorite(simpleSummoner);
    }

    private void addToFavorites(SimpleSummoner simpleSummoner) {
        FavoritesSqLiteHelper.addFavorite(simpleSummoner);
    }

    public interface FavoriteCallback {
        SimpleSummoner getFavorite();
    }
}
