package com.github.koshkin.leagueoflegendsstats.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.utils.AssetReaderUtil;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;

/**
 * Created by tehras on 1/11/16.
 */
public class StaticDataHolder {

    private static StaticDataHolder sStaticDataHolder;
    private final Context mContext;

    private ChampionIcons mChampionIcons;
    private ProfileIcons mProfileIcons;
    private SpellIcons mSpellIcons;

    private static final String FILE_NAME_CHAMPIONS = "champion_icons.json";
    private static final String FILE_NAME_PROFILE = "profile_icons.json";
    private static final String FILE_NAME_SUMMONER = "summoner_icons.json";

    private StaticDataHolder(Context context) {
        mContext = context;
    }

    public void init() {
        if (mChampionIcons == null && mProfileIcons == null) {
            mChampionIcons = new ChampionIcons().parse(AssetReaderUtil.read(FILE_NAME_CHAMPIONS, mContext));
            mProfileIcons = new ProfileIcons().parse(AssetReaderUtil.read(FILE_NAME_PROFILE, mContext));
            mSpellIcons = new SpellIcons().parse(AssetReaderUtil.read(FILE_NAME_SUMMONER, mContext));
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
        Bitmap bm = AssetReaderUtil.readPng(image.getSprite(), mContext);
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
