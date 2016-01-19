package com.github.koshkin.leagueoflegendsstats;

import com.github.koshkin.leagueoflegendsstats.models.ChampionIcons;
import com.github.koshkin.leagueoflegendsstats.models.DataParser;
import com.github.koshkin.leagueoflegendsstats.models.ItemIcons;
import com.github.koshkin.leagueoflegendsstats.models.ProfileIcons;
import com.github.koshkin.leagueoflegendsstats.models.SpellIcons;
import com.github.koshkin.leagueoflegendsstats.models.SpriteHolder;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Type;
import com.github.koshkin.leagueoflegendsstats.models.VersionControl;
import com.github.koshkin.leagueoflegendsstats.networking.Executor;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.networking.URIConstants;
import com.github.koshkin.leagueoflegendsstats.networking.URIHelper;

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
    public static final String PROFILE_JSON = "profileicon.json";

    public void initialize() {
        StaticDataHolder.getInstance(mMainActivity).init();

        Response response;
        try {
            response = new Executor(new Request(Request.RequestType.GET, new VersionControl(), this, URIHelper.GET_REALM), mMainActivity).execute().get();
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

                URIConstants.NA_STATIC_URI = versionControl.getCdnUrl() + "/" + versionControl.getVersion() + "/img";
                URIConstants.NA_STATIC_DATA_URI = versionControl.getCdnUrl() + "/" + versionControl.getVersion() + "/data";

                if (!spellIconVersion.equalsIgnoreCase(getFromMap(versionControl, "summoner"))) {
                    mExecuteCounter++;
                    new Executor(new Request(Request.RequestType.GET, new DataParser(Type.SUMMONER, mMainActivity), this, URIHelper.GET_JSON, versionControl.getRegion(), SPELL_JSON), mMainActivity).execute();
                    for (int i = 0; i < 15; i++) {
                        mExecuteCounter++;
                        new Executor(new Request(Request.RequestType.GET_IMAGE, new SpriteHolder(Type.SUMMONER), this, URIHelper.GET_SPRITES, "spell" + String.valueOf(i) + ".png"), mMainActivity).execute();
                    }
                }
                if (!itemIcons.equalsIgnoreCase(getFromMap(versionControl, "profileicon"))) {
                    mExecuteCounter++;
                    new Executor(new Request(Request.RequestType.GET, new DataParser(Type.ITEM, mMainActivity), this, URIHelper.GET_JSON, versionControl.getRegion(), SPELL_JSON), mMainActivity).execute();
                    for (int i = 0; i < 4; i++) {
                        mExecuteCounter++;
                        new Executor(new Request(Request.RequestType.GET_IMAGE, new SpriteHolder(Type.ITEM), this, URIHelper.GET_SPRITES, "item" + String.valueOf(i) + ".png"), mMainActivity).execute();
                    }
                }
                if (!championIcons.equalsIgnoreCase(getFromMap(versionControl, "champion"))) {
                    mExecuteCounter++;
                    new Executor(new Request(Request.RequestType.GET, new DataParser(Type.CHAMPION, mMainActivity), this, URIHelper.GET_JSON, versionControl.getRegion(), CHAMPION_JSON), mMainActivity).execute();
                    for (int i = 0; i < 5; i++) {
                        mExecuteCounter++;
                        new Executor(new Request(Request.RequestType.GET_IMAGE, new SpriteHolder(Type.CHAMPION), this, URIHelper.GET_SPRITES, "champion" + String.valueOf(i) + ".png"), mMainActivity).execute();
                    }
                }
                if (!profileIcons.equalsIgnoreCase(getFromMap(versionControl, "item"))) {
                    mExecuteCounter++;
                    new Executor(new Request(Request.RequestType.GET, new DataParser(Type.PROFILE, mMainActivity), this, URIHelper.GET_JSON, versionControl.getRegion(), PROFILE_JSON), mMainActivity).execute();
                    for (int i = 0; i < 1; i++) {
                        mExecuteCounter++;
                        new Executor(new Request(Request.RequestType.GET_IMAGE, new SpriteHolder(Type.PROFILE), this, URIHelper.GET_SPRITES, "profileicon" + String.valueOf(i) + ".png"), mMainActivity).execute();
                    }
                }
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
                    }
                break;
        }


        if (mExecuteCounter == 0) {
            mCallback.completed();
        }
    }

    public interface Callback {
        void completed();
    }
}
