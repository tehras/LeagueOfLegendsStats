package com.github.koshkin.leagueoflegendsstats;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by tehras on 1/20/16.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        SugarContext.init(getApplicationContext());
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
    
}
