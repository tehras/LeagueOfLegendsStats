package com.github.koshkin.leagueoflegendsstats.networking;

import android.os.AsyncTask;

/**
 * Created by tehras on 1/9/16.
 * <p/>
 * Execute is a background task that will handle the network calls
 */
public class Executor extends AsyncTask<String, Void, Response> {

    public Request mRequest;

    public Executor(Request request) {
        mRequest = request;
    }

    @Override
    protected Response doInBackground(String... params) {
        return new Manager(mRequest).executeTask();
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);

        mRequest.finished(response);
    }
}
