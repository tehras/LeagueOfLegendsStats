package com.github.koshkin.leagueoflegendsstats.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.github.koshkin.leagueoflegendsstats.models.FileHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by tehras on 1/11/16.
 */
public class AssetReaderUtil {

    private static final String TAG = "AssetReader";

    public static Bitmap readPng(String assetName, Context context) {
        try {
            InputStream ims = context.getAssets().open(assetName);

            return BitmapFactory.decodeStream(ims);
        } catch (IOException e) {
            Log.e(TAG, "Exception - ", e);
        }

        return null;
    }

    public static Bitmap readPng(String assetName, File file) {
        return new FileHandler(assetName, file).getBitmapFromFile();
    }

    public static final String CONSTANT_CHAMPION = "champion_key";
    public static final String CONSTANT_PROFILE = "item_key";
    public static final String CONSTANT_ITEM = "real_item_key";
    public static final String CONSTANT_SUMMONER = "summoner_key";

    public static String read(String assetName, Context context) {
        File file = new File(context.getCacheDir(), assetName);
        if (file.exists() && file.length() > 0) {

        }

        BufferedReader reader = null;
        String output = "";

        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(assetName), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                output = output + mLine;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "Exception - ", e);
                }
            }
        }
        return output;
    }
}
