package com.github.koshkin.leagueoflegendsstats.models;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.github.koshkin.leagueoflegendsstats.FirstInitialize;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.utils.AssetReaderUtil;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;
import com.github.koshkin.leagueoflegendsstats.utils.SharedPrefsUtil;
import com.github.koshkin.leagueoflegendsstats.utils.Utils;

import java.io.File;
import java.util.HashMap;

/**
 * Created by tehras on 1/11/16.
 * will hold all static objects
 */
public class StaticDataHolder {

    private static StaticDataHolder sStaticDataHolder;
    private final Context mContext;

    private ChampionIcons mChampionIcons;
    private ProfileIcons mProfileIcons;
    private SpellIcons mSpellIcons;
    private ItemIcons mItemIcons;
    private RuneIcons mRuneIcons;
    private MasteryIcons mMasteryIcons;
    private boolean mNeedsRefresh;

    public void setItemIcons(ItemIcons itemIcons) {
        mItemIcons = itemIcons;
    }

    public void setChampionIcons(ChampionIcons championIcons) {
        mChampionIcons = championIcons;
    }

    public void setProfileIcons(ProfileIcons profileIcons) {
        mProfileIcons = profileIcons;
    }

    public void setSpellIcons(SpellIcons spellIcons) {
        mSpellIcons = spellIcons;
    }

    public void setRuneIcons(RuneIcons runeIcons) {
        mRuneIcons = runeIcons;
    }


    public void setMasteryIcons(MasteryIcons masteryIcons) {
        mMasteryIcons = masteryIcons;
    }

    public RuneIcons getRuneIcons() {
        return mRuneIcons;
    }

    public MasteryIcons getMasteryIcons() {
        return mMasteryIcons;
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

    public ItemIcons getItemIcons() {
        return mItemIcons;
    }

    private static final String FILE_NAME_CHAMPIONS = "champion_icons.json";
    private static final String FILE_NAME_PROFILE = "profile_icons.json";
    private static final String FILE_NAME_SUMMONER = "summoner_icons.json";
    private static final String FILE_NAME_ITEM = "item_icons.json";
    private static final String FILE_NAME_RUNES = "runes_icons.json";
    private static final String FILE_NAME_MASTERIES = "mastery_icons.json";

    private StaticDataHolder(Context context) {
        mContext = context;
    }

    public void init() {
        if (mChampionIcons == null && mProfileIcons == null && mSpellIcons == null && mRuneIcons == null) {
            String champResponse = SharedPrefsUtil.getSharedPrefs(AssetReaderUtil.CONSTANT_CHAMPION, mContext);
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
            String itemResponse = SharedPrefsUtil.getSharedPrefs(AssetReaderUtil.CONSTANT_ITEM, mContext);
            if (NullChecker.isNullOrEmpty(itemResponse)) {
                itemResponse = AssetReaderUtil.read(FILE_NAME_ITEM, mContext);
            }
            mItemIcons = new ItemIcons().parse(itemResponse);
            String runesResponse = SharedPrefsUtil.getSharedPrefs(AssetReaderUtil.CONSTANT_ITEM, mContext);
            if (NullChecker.isNullOrEmpty(runesResponse)) {
                runesResponse = AssetReaderUtil.read(FILE_NAME_RUNES, mContext);
            }
            mRuneIcons = new RuneIcons().parse(runesResponse);
            String masteryResponse = SharedPrefsUtil.getSharedPrefs(AssetReaderUtil.CONSTANT_MASTERIES, mContext);
            if (NullChecker.isNullOrEmpty(masteryResponse)) {
                masteryResponse = AssetReaderUtil.read(FILE_NAME_MASTERIES, mContext);
            }
            mMasteryIcons = new MasteryIcons().parse(masteryResponse);
        }
    }

    public Drawable getSpellIcon(int icon) {
        return getSpellIcon(mSpellIcons.getSpellIcon(String.valueOf(icon)));
    }

    public Drawable getProfileIcon(int icon) {
        return getProfileIcon(mProfileIcons.getProfileIcons().get(String.valueOf(icon)), false);
    }

    public Drawable getProfileIconSmall(int icon) {
        return getProfileIcon(mProfileIcons.getProfileIcons().get(String.valueOf(icon)), true);
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

    public Drawable getItemIcon(int iconId) {
        return getItemIcon(mItemIcons.getItemIcon(String.valueOf(iconId)));
    }

    public Drawable getItemIcon(ItemIcon itemIcon) {
        if (itemIcon == null || itemIcon.getImage() == null)
            return null;

        int size = mContext.getResources().getDimensionPixelSize(R.dimen.item_icon_height);
        return loadFromAssets(itemIcon.getImage(), size, size);
    }


    public Drawable getSpellIcon(Spell spell) {
        if (spell == null || spell.getImage() == null)
            return null;

        int size = mContext.getResources().getDimensionPixelSize(R.dimen.item_icon_height);
        return loadFromAssets(spell.getImage(), size, size);
    }

    public Drawable getRankTier(Tier tier, String division) {
        if (tier == null || NullChecker.isNullOrEmpty(division))
            return null;

        int size = mContext.getResources().getDimensionPixelSize(R.dimen.profile_icon_size_xsmall);
        return loadFromAssets(tier.getName().toLowerCase() + "_" + division.toLowerCase() + ".png", size, size);
    }

    public Drawable getProfileIcon(ProfileIcon profileIcon, boolean smallSize) {
        if (profileIcon == null || profileIcon.getImage() == null)
            return null;

        int size;
        if (smallSize)
            size = mContext.getResources().getDimensionPixelSize(R.dimen.profile_icon_size_small);
        else
            size = mContext.getResources().getDimensionPixelSize(R.dimen.profile_icon_size);
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

    private HashMap<String, Drawable> mLoadedImages;

    public Drawable loadFromAssets(String key, int width, int height) {

        if (mLoadedImages != null && mLoadedImages.containsKey(key)) {
            Drawable drawable = mLoadedImages.get(key);
            if (drawable != null)
                return drawable;
        }

        File file = new File(mContext.getCacheDir(), key);
        Bitmap bm;
        if (file.exists() && file.length() > 0) {
            bm = AssetReaderUtil.readPng(key, file);
        } else {
            bm = AssetReaderUtil.readPng(key, mContext);
        }
        Drawable drawable = null;
        if (bm != null) {
            Bitmap scaled = Bitmap.createScaledBitmap(bm, width, height, false);
            drawable = new BitmapDrawable(mContext.getResources(), scaled);

            bm.recycle();
        }

        if (mLoadedImages == null)
            mLoadedImages = new HashMap<>();
        mLoadedImages.put(key, drawable);

        return drawable;
    }

    public Drawable loadFromAssets(Image image, int width, int height) {
        String key = image.getFull();

        if (mLoadedImages != null && mLoadedImages.containsKey(key)) {
            Drawable drawable = mLoadedImages.get(key);
            if (drawable != null)
                return drawable;
        }

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

        if (mLoadedImages == null)
            mLoadedImages = new HashMap<>();
        mLoadedImages.put(key, drawable);

        return drawable;
    }

    public static StaticDataHolder getInstance(Context context) {
        if (sStaticDataHolder == null) {
            sStaticDataHolder = new StaticDataHolder(context);
        }

        return sStaticDataHolder;
    }

    private Summoner mMySummoner;

    public Summoner getMySummoner() {
        return mMySummoner;
    }

    public void setMySummoner(Summoner summoner) {
        mMySummoner = summoner;
    }

    public void setMySummoner(Summoner summoner, Activity activity) {
        SharedPrefsUtil.saveSharedPrefs(FirstInitialize.SHARED_MY_SUMMONER, summoner.toJson(), activity);
        mMySummoner = summoner;
    }

    public boolean needsRefresh() {
        return mNeedsRefresh;
    }

    public void needsRefresh(boolean needsRefresh) {
        mNeedsRefresh = needsRefresh;
    }
}
