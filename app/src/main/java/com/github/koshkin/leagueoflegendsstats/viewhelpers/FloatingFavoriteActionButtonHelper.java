package com.github.koshkin.leagueoflegendsstats.viewhelpers;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.Favorite;
import com.github.koshkin.leagueoflegendsstats.utils.SharedPrefsUtil;

import java.util.Set;

/**
 * Created by tehras on 1/18/16.
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

    public boolean refresh(Favorite favorite, String summonerId) {
        Set<String> favorites = SharedPrefsUtil.getFromSharedPrefs(favorite, PREFS_KEY, mActivity);
        final boolean isAlreadyFav = containsInSet(favorites, summonerId);

        if (isAlreadyFav)
            mFab.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_fav_fab_disable));
        else
            mFab.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_fav_fab_enable));

        mFab.show();

        return isAlreadyFav;
    }

    private boolean containsInSet(Set<String> favorites, String summonerId) {
        if (summonerId == null || favorites == null)
            return false;

        for (String string : favorites) {
            if (Favorite.fromJson(string).getSummonerId().equalsIgnoreCase(summonerId))
                return true;
        }

        return false;
    }

    public void init() {
        mIsAlreadyFav = refresh(null, mSummonerId);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = mFavoriteCallback.getFavorite();

                String text;
                if (mIsAlreadyFav) {
                    text = "Removed from Favorites";
                    removeFromFavorites(favorite, mActivity);
                } else {
                    text = "Added to Favorites";
                    addToFavorites(mFavoriteCallback.getFavorite(), mActivity);
                }
                Snackbar.make(mFab, text, Snackbar.LENGTH_SHORT).show();
                mIsAlreadyFav = refresh(favorite, mSummonerId);
            }
        });
    }

    public static final String PREFS_KEY = "favorite_summoners_key";

    private void removeFromFavorites(Favorite favorite, Activity activity) {
        SharedPrefsUtil.removeFromSharedPrefs(favorite, PREFS_KEY, activity);
    }

    private void addToFavorites(Favorite favorite, Activity activity) {
        SharedPrefsUtil.addToSharedPrefs(favorite, PREFS_KEY, activity);
    }

    public interface FavoriteCallback {
        Favorite getFavorite();
    }
}
