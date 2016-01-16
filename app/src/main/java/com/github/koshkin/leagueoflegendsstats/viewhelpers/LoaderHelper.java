package com.github.koshkin.leagueoflegendsstats.viewhelpers;

import android.os.AsyncTask;

/**
 * Created by tehras on 1/16/16.
 */
public abstract class LoaderHelper extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        runInBackground();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        postExecute();
    }

    protected abstract void postExecute();

    protected abstract void runInBackground();
}
