package com.github.koshkin.leagueoflegendsstats.networking;

import android.util.Log;

import java.io.IOException;
import java.text.ParseException;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by tehras on 1/9/16.
 * <p/>
 * Manager for request / response
 */
public class Manager {

    private final Request mRequest;

    public Manager(Request request) {
        mRequest = request;
    }

    public com.github.koshkin.leagueoflegendsstats.networking.Response<Object> executeTask() {
        switch (mRequest.getRequestType()) {
            case GET:
                return get();
            case POST:
                return post();
            default:
                return null;
        }
    }

    private com.github.koshkin.leagueoflegendsstats.networking.Response<Object> post() {
        return null; //todo implement
    }

    private com.github.koshkin.leagueoflegendsstats.networking.Response<Object> get() {
        com.github.koshkin.leagueoflegendsstats.networking.Response<Object> response = new com.github.koshkin.leagueoflegendsstats.networking.Response<Object>();
        try {

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(mRequest.getUrl())
                    .build();

            Log.v(getClass().getSimpleName(), "URL to execute - " + mRequest.getUrl());

            Response okResponse = mClient.newCall(request).execute();

            if (okResponse.isSuccessful()) {
                String body = okResponse.body().string();
                Log.e(getClass().getSimpleName(), "body - " + body);
                response.setReturnedObject(mRequest.parse(body));

                response.setStatus(com.github.koshkin.leagueoflegendsstats.networking.Response.Status.SUCCESS);
            } else {
                int code = okResponse.code();
                Log.e(getClass().getSimpleName(), "code" + okResponse.code());
                response.setStatus(getStatusFromCode(code));
            }

            okResponse.body().close();
        } catch (IOException e) {
            response.setStatus(com.github.koshkin.leagueoflegendsstats.networking.Response.Status.FAILED);
        } catch (ParseException e) {
            response.setStatus(com.github.koshkin.leagueoflegendsstats.networking.Response.Status.PARSE_EXCEPTION);
        }

        return response;
    }

    private com.github.koshkin.leagueoflegendsstats.networking.Response.Status getStatusFromCode(int code) {
        if (code >= 200 && code < 300) {
            return com.github.koshkin.leagueoflegendsstats.networking.Response.Status.SUCCESS;
        } else if (code == 404) {
            return com.github.koshkin.leagueoflegendsstats.networking.Response.Status.NOT_FOUND;
        } else if (code == 401) {
            return com.github.koshkin.leagueoflegendsstats.networking.Response.Status.NOT_AUTHORIZED;
        } else {
            return com.github.koshkin.leagueoflegendsstats.networking.Response.Status.FAILED;
        }
    }

    private final OkHttpClient mClient = new OkHttpClient();
}
