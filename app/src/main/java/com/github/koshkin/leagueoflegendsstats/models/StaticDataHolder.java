package com.github.koshkin.leagueoflegendsstats.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.utils.AssetReaderUtil;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;
import com.github.koshkin.leagueoflegendsstats.utils.SharedPrefsUtil;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;

import java.io.File;

/**
 * Created by tehras on 1/11/16.
 */
public class StaticDataHolder {

    private static StaticDataHolder sStaticDataHolder;
    private final Context mContext;

    private ChampionIcons mChampionIcons;
    private ProfileIcons mProfileIcons;
    private SpellIcons mSpellIcons;

    public void setChampionIcons(ChampionIcons championIcons) {
        mChampionIcons = championIcons;
    }

    public void setProfileIcons(ProfileIcons profileIcons) {
        mProfileIcons = profileIcons;
    }

    public void setSpellIcons(SpellIcons spellIcons) {
        mSpellIcons = spellIcons;
    }

    public SpellIcons getSpellIcons() {
        return mSpellIcons;
    }

    public ProfileIcons getProfileIcons() {
        return mProfileIcons;
    }

    public ChampionIcons getChampionIcons() {
        return mChampionIcons;
    }

    private static final String FILE_NAME_CHAMPIONS = "champion_icons.json";
    private static final String FILE_NAME_PROFILE = "profile_icons.json";
    private static final String FILE_NAME_SUMMONER = "summoner_icons.json";

    private StaticDataHolder(Context context) {
        mContext = context;
    }

    public void init() {
        if (mChampionIcons == null && mProfileIcons == null && mSpellIcons == null) {
            String champResponse = SharedPrefsUtil.getSharedPrefs(AssetReaderUtil.CONSTANT_CHAMPION, mContext);
            Log.e(getClass().getSimpleName(), "response - " + champResponse);
            if (NullChecker.isNullOrEmpty(champResponse)) {
                champResponse = AssetReaderUtil.read(FILE_NAME_CHAMPIONS, mContext);
            }
            mChampionIcons = new ChampionIcons().parse(champResponse);
            String profileRsponse = SharedPrefsUtil.getSharedPrefs(AssetReaderUtil.CONSTANT_PROFILE, mContext);
            if (NullChecker.isNullOrEmpty(profileRsponse)) {
                profileRsponse = AssetReaderUtil.read(FILE_NAME_PROFILE, mContext);
            }
            mProfileIcons = new ProfileIcons().parse(profileRsponse);
            String spellResponse = SharedPrefsUtil.getSharedPrefs(AssetReaderUtil.CONSTANT_SUMMONER, mContext);
            if (NullChecker.isNullOrEmpty(spellResponse)) {
                spellResponse = AssetReaderUtil.read(FILE_NAME_SUMMONER, mContext);
            }
            mSpellIcons = new SpellIcons().parse(spellResponse);
        }
    }

    public Drawable getSpellIcon(int icon) {
        return getSpellIcon(mSpellIcons.getSpellIcon(String.valueOf(icon)));
    }

    public Drawable getProfileIcon(int icon) {
        return getProfileIcon(mProfileIcons.getProfileIcons().get(String.valueOf(icon)));
    }

    public Drawable getChampionIcon(int id) {
        return getChampionIcon(mChampionIcons.getChampionIcon(String.valueOf(id)));
    }

    public String getChampionName(int id) {
        ChampionIcon championIcon = mChampionIcons.getChampionIcon(String.valueOf(id));
        if (championIcon == null)
            return Utils.NOT_AVAILABLE;

        return championIcon.getName();
    }

    public String getProfileIconName(int id) {
        ProfileIcon profileIcon = mProfileIcons.getProfileIcon(String.valueOf(id));
        if (profileIcon == null)
            return null;

        return profileIcon.getImage().getFull();
    }

    public Drawable getSpellIcon(Spell spell) {
        if (spell == null || spell.getImage() == null)
            return null;

        int size = mContext.getResources().getDimensionPixelSize(R.dimen.small_icon_height);
        return loadFromAssets(spell.getImage(), size, size);
    }

    public Drawable getProfileIcon(ProfileIcon profileIcon) {
        if (profileIcon == null || profileIcon.getImage() == null)
            return null;

        int size = mContext.getResources().getDimensionPixelSize(R.dimen.profile_icon_size);
        return loadFromAssets(profileIcon.getImage(), size, size);
    }

    public Drawable getProfileIcon(Bitmap bitmap) {
        if (bitmap == null)
            return null;

        int size = mContext.getResources().getDimensionPixelSize(R.dimen.profile_icon_size);
        bitmap = Bitmap.createScaledBitmap(bitmap, size, size, false);

        return new BitmapDrawable(mContext.getResources(), bitmap);
    }

    public Drawable getChampionIcon(ChampionIcon championIcon) {
        if (championIcon == null || championIcon.getImage() == null)
            return null;

        int size = mContext.getResources().getDimensionPixelSize(R.dimen.champion_icon_size);
        return loadFromAssets(championIcon.getImage(), size, size);
    }

    public Drawable loadFromAssets(Image image, int width, int height) {
        File file = new File(mContext.getCacheDir(), image.getSprite());
        Bitmap bm;
        if (file.exists() && file.length() > 0) {
            bm = AssetReaderUtil.readPng(image.getSprite(), file);
        } else {
            bm = AssetReaderUtil.readPng(image.getSprite(), mContext);
        }
        Drawable drawable = null;
        if (bm != null) {
            Bitmap sprite = Bitmap.createBitmap(bm, image.getX(), image.getY(), image.getW(), image.getH());
            sprite = Bitmap.createScaledBitmap(sprite, width, height, false);

            drawable = new BitmapDrawable(mContext.getResources(), sprite);
            bm.recycle();
        }

        return drawable;
    }

    public static StaticDataHolder getInstance(Context context) {
        if (sStaticDataHolder == null) {
            sStaticDataHolder = new StaticDataHolder(context);
        }

        return sStaticDataHolder;
    }

}
