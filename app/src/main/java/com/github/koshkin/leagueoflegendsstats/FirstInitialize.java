package com.github.koshkin.leagueoflegendsstats;

import android.content.Context;

import com.github.koshkin.leagueoflegendsstats.models.ChampionIcons;
import com.github.koshkin.leagueoflegendsstats.models.DataParser;
import com.github.koshkin.leagueoflegendsstats.models.ItemIcons;
import com.github.koshkin.leagueoflegendsstats.models.MasteryIcons;
import com.github.koshkin.leagueoflegendsstats.models.ProfileIcons;
import com.github.koshkin.leagueoflegendsstats.models.RuneIcons;
import com.github.koshkin.leagueoflegendsstats.models.SpellIcons;
import com.github.koshkin.leagueoflegendsstats.models.SpriteHolder;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.models.Type;
import com.github.koshkin.leagueoflegendsstats.models.VersionControl;
import com.github.koshkin.leagueoflegendsstats.networking.Executor;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.networking.URIConstants;
import com.github.koshkin.leagueoflegendsstats.networking.URIHelper;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;
import com.github.koshkin.leagueoflegendsstats.utils.SharedPrefsUtil;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by tehras on 1/14/16.
 * <p/>
 * First Initialize
 */
public class FirstInitialize implements Request.RequestCallback {

    private final Callback mCallback;
    private final MainActivity mMainActivity;

    public FirstInitialize(MainActivity mainActivity, Callback callback) {
        mMainActivity = mainActivity;
        mCallback = callback;
    }

    public static final String CHAMPION_JSON = "champion.json";
    public static final String SPELL_JSON = "summoner.json";
    public static final String ITEM_JSON = "item.json";
    public static final String PROFILE_JSON = "profileicon.json";
    public static final String RUNES_JSON = "rune.json";
    public static final String MASTERIES_JSON = "mastery.json";

    public void initialize() {
        StaticDataHolder.getInstance(mMainActivity).init();

        URIHelper.Region region = getRegion(mMainActivity);
        URIHelper.setCurrentRegion(region);

        Response response;
        try {
            response = new Executor(new Request(mMainActivity, Request.RequestType.GET, new VersionControl(), this, URIHelper.GET_REALM), mMainActivity).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            mCallback.completed();
            return;
        }
        if (Response.Status.SUCCESS == response.getStatus()) {
            VersionControl versionControl = (VersionControl) response.getReturnedObject();
            if (versionControl != null) {
                StaticDataHolder staticDataHolder = StaticDataHolder.getInstance(mMainActivity);
                String spellIconVersion = staticDataHolder.getSpellIcons().getVersion();
                String championIcons = staticDataHolder.getChampionIcons().getVersion();
                String profileIcons = staticDataHolder.getProfileIcons().getVersion();
                String itemIcons = staticDataHolder.getItemIcons().getVersion();
                String runeIcons = staticDataHolder.getRuneIcons().getVersion();
                String masteryIcons = staticDataHolder.getMasteryIcons().getVersion();

                URIConstants.NA_STATIC_URI = versionControl.getCdnUrl() + "/" + versionControl.getVersion() + "/img";
                URIConstants.NA_STATIC_DATA_URI = versionControl.getCdnUrl() + "/" + versionControl.getVersion() + "/data";

                if (!spellIconVersion.equalsIgnoreCase(getFromMap(versionControl, "summoner"))) {
                    mExecuteCounter++;
                    URIHelper.setVersion(getFromMap(versionControl, "summoner"));
                    new Executor(new Request(mMainActivity, Request.RequestType.GET, new DataParser(Type.SUMMONER, mMainActivity), this, URIHelper.GET_JSON, versionControl.getRegion(), SPELL_JSON), mMainActivity).execute();
                    for (int i = 0; i < 15; i++) {
                        mExecuteCounter++;
                        new Executor(new Request(mMainActivity, Request.RequestType.GET_IMAGE, new SpriteHolder(Type.SUMMONER), this, URIHelper.GET_SPRITES, "spell" + String.valueOf(i) + ".png"), mMainActivity).execute();
                    }
                }
                if (!itemIcons.equalsIgnoreCase(getFromMap(versionControl, "item"))) {
                    mExecuteCounter++;
                    URIHelper.setVersion(getFromMap(versionControl, "item"));
                    new Executor(new Request(mMainActivity, Request.RequestType.GET, new DataParser(Type.ITEM, mMainActivity), this, URIHelper.GET_JSON, versionControl.getRegion(), ITEM_JSON), mMainActivity).execute();
                    for (int i = 0; i < 4; i++) {
                        mExecuteCounter++;
                        new Executor(new Request(mMainActivity, Request.RequestType.GET_IMAGE, new SpriteHolder(Type.ITEM), this, URIHelper.GET_SPRITES, "item" + String.valueOf(i) + ".png"), mMainActivity).execute();
                    }
                }
                if (!championIcons.equalsIgnoreCase(getFromMap(versionControl, "champion"))) {
                    mExecuteCounter++;
                    URIHelper.setVersion(getFromMap(versionControl, "champion"));
                    new Executor(new Request(mMainActivity, Request.RequestType.GET, new DataParser(Type.CHAMPION, mMainActivity), this, URIHelper.GET_JSON, versionControl.getRegion(), CHAMPION_JSON), mMainActivity).execute();
                    for (int i = 0; i < 5; i++) {
                        mExecuteCounter++;
                        new Executor(new Request(mMainActivity, Request.RequestType.GET_IMAGE, new SpriteHolder(Type.CHAMPION), this, URIHelper.GET_SPRITES, "champion" + String.valueOf(i) + ".png"), mMainActivity).execute();
                    }
                }
                if (!profileIcons.equalsIgnoreCase(getFromMap(versionControl, "profileicon"))) {
                    mExecuteCounter++;
                    URIHelper.setVersion(getFromMap(versionControl, "profileicon"));
                    new Executor(new Request(mMainActivity, Request.RequestType.GET, new DataParser(Type.PROFILE, mMainActivity), this, URIHelper.GET_JSON, versionControl.getRegion(), PROFILE_JSON), mMainActivity).execute();
                    for (int i = 0; i < 1; i++) {
                        mExecuteCounter++;
                        new Executor(new Request(mMainActivity, Request.RequestType.GET_IMAGE, new SpriteHolder(Type.PROFILE), this, URIHelper.GET_SPRITES, "profileicon" + String.valueOf(i) + ".png"), mMainActivity).execute();
                    }
                }
                if (!runeIcons.equalsIgnoreCase(getFromMap(versionControl, "rune"))) {
                    mExecuteCounter++;
                    URIHelper.setVersion(getFromMap(versionControl, "rune"));
                    new Executor(new Request(mMainActivity, Request.RequestType.GET, new DataParser(Type.RUNES, mMainActivity), this, URIHelper.GET_JSON, versionControl.getRegion(), RUNES_JSON), mMainActivity).execute();
                    for (int i = 0; i < 1; i++) {
                        mExecuteCounter++;
                        new Executor(new Request(mMainActivity, Request.RequestType.GET_IMAGE, new SpriteHolder(Type.RUNES), this, URIHelper.GET_SPRITES, "rune" + String.valueOf(i) + ".png"), mMainActivity).execute();
                    }
                }
                if (!masteryIcons.equalsIgnoreCase(getFromMap(versionControl, "mastery"))) {
                    mExecuteCounter++;
                    URIHelper.setVersion(getFromMap(versionControl, "mastery"));
                    new Executor(new Request(mMainActivity, Request.RequestType.GET, new DataParser(Type.MASTERIES, mMainActivity), this, URIHelper.GET_JSON, versionControl.getRegion(), MASTERIES_JSON), mMainActivity).execute();
                    for (int i = 0; i < 1; i++) {
                        mExecuteCounter++;
                        new Executor(new Request(mMainActivity, Request.RequestType.GET_IMAGE, new SpriteHolder(Type.MASTERIES), this, URIHelper.GET_SPRITES, "mastery" + String.valueOf(i) + ".png"), mMainActivity).execute();
                    }
                }

                StaticDataHolder.getInstance(mMainActivity).setMySummoner(getMySummoner(mMainActivity));
            }
        }
        if (mExecuteCounter == 0) {
            mCallback.completed();
        }
    }

    private int mExecuteCounter;

    private String getFromMap(VersionControl versionControl, String key) {
        HashMap<String, String> map = versionControl.getVersionMap();
        if (map != null && map.containsKey(key)) {
            return map.get(key);
        } else {
            return versionControl.getVersion(); //AS A BACKUP
        }
    }

    @Override
    public void finished(Response response, Request request) {
        switch (request.getURIHelper()) {
            case GET_SPRITES:
                mExecuteCounter--;
                break;
            case GET_JSON:
                mExecuteCounter--;
                if (response.getStatus() == Response.Status.SUCCESS) {
                    DataParser parser = (DataParser) response.getReturnedObject();
                    if (parser.getResponseObject() != null)
                        switch (parser.getType()) {
                            case CHAMPION:
                                if (parser.getResponseObject() instanceof ChampionIcons)
                                    StaticDataHolder.getInstance(mMainActivity).setChampionIcons((ChampionIcons) parser.getResponseObject());
                                break;
                            case SUMMONER:
                                if (parser.getResponseObject() instanceof SpellIcons)
                                    StaticDataHolder.getInstance(mMainActivity).setSpellIcons((SpellIcons) parser.getResponseObject());
                                break;
                            case PROFILE:
                                if (parser.getResponseObject() instanceof ProfileIcons)
                                    StaticDataHolder.getInstance(mMainActivity).setProfileIcons((ProfileIcons) parser.getResponseObject());
                                break;
                            case ITEM:
                                if (parser.getResponseObject() instanceof ItemIcons)
                                    StaticDataHolder.getInstance(mMainActivity).setItemIcons((ItemIcons) parser.getResponseObject());
                                break;
                            case RUNES:
                                if (parser.getResponseObject() instanceof RuneIcons)
                                    StaticDataHolder.getInstance(mMainActivity).setRuneIcons((RuneIcons) parser.getResponseObject());
                                break;
                            case MASTERIES:
                                if (parser.getResponseObject() instanceof MasteryIcons)
                                    StaticDataHolder.getInstance(mMainActivity).setMasteryIcons((MasteryIcons) parser.getResponseObject());
                                break;
                        }
                }
                break;
        }


        if (mExecuteCounter == 0) {
            mCallback.completed();
        }
    }

    public static final String SHARED_REGION = "region_shared_key";
    public static final String SHARED_MY_SUMMONER = "my_summoner_shared_key";

    public URIHelper.Region getRegion(Context context) {
        URIHelper.Region region = URIHelper.Region.NA;
        String regionString = SharedPrefsUtil.getSharedPrefs(SHARED_REGION, context);
        if (!NullChecker.isNullOrEmpty(regionString)) {
            return region.getFromString(region);
        } else {//for first time
            SharedPrefsUtil.saveSharedPrefs(SHARED_REGION, URIHelper.Region.NA.toString(), context);
        }

        return region;
    }

    public Summoner getMySummoner(Context context) {
        String mySummoner = SharedPrefsUtil.getSharedPrefs(SHARED_MY_SUMMONER, context);
        if (!NullChecker.isNullOrEmpty(mySummoner))
            return Summoner.fromJson(mySummoner);

        return null;
    }

    public interface Callback {
        void completed();
    }
}
