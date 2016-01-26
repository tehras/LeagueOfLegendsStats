package com.github.koshkin.leagueoflegendsstats.networking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.github.koshkin.leagueoflegendsstats.models.FileHandler;
import com.github.koshkin.leagueoflegendsstats.models.SpriteHolder;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by tehras on 1/9/16.
 * <p/>
 * Manager for request / response
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class Manager {

    private static final String IMAGE_NAME_DEFAULT = "temp_image.png";
    private final Request mRequest;
    private final Context mContext;

    public Manager(Request request, Context context) {
        mRequest = request;
        mContext = context;
    }

    public com.github.koshkin.leagueoflegendsstats.networking.Response<Object> executeTask() {
        switch (mRequest.getRequestType()) {
            case GET:
                return get();
            case POST:
                return post();
            case GET_IMAGE:
                return getImage();
            default:
                return null;
        }
    }

    private com.github.koshkin.leagueoflegendsstats.networking.Response<Object> post() {
        return null;
    }

    public com.github.koshkin.leagueoflegendsstats.networking.Response<Object> getImage() {
        com.github.koshkin.leagueoflegendsstats.networking.Response<Object> response = new com.github.koshkin.leagueoflegendsstats.networking.Response<>();
        try {
            Map<String, String> map = mRequest.getExtraParams();

            String imageName = IMAGE_NAME_DEFAULT;
            if (map != null && map.containsKey(Request.KEY_IMAGE_NAME))
                imageName = map.get(Request.KEY_IMAGE_NAME);

            //gets file
            File file = new File(mContext.getCacheDir(), imageName);

            //if imageName is not default check if already exists
            boolean wasDeleted = true;
            if (!imageName.equalsIgnoreCase(IMAGE_NAME_DEFAULT) && !(response.getReturnedObject() instanceof SpriteHolder) && file.exists() && file.length() != 0) {
                response.setStatus(com.github.koshkin.leagueoflegendsstats.networking.Response.Status.SUCCESS);
                response.setReturnedObject(new FileHandler(imageName, file));
                return response;
            } else if (imageName.equalsIgnoreCase(IMAGE_NAME_DEFAULT) && file.exists()) {
                wasDeleted = file.delete();
            }

            Log.v(getClass().getSimpleName(), "URL to execute - " + mRequest.getUrl());

            boolean wasCreated = file.createNewFile();
            if (!wasCreated || !wasDeleted) {
                response.setStatus(com.github.koshkin.leagueoflegendsstats.networking.Response.Status.PARSE_EXCEPTION);
                return response;
            }

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(mRequest.getUrl())
                    .build();

            Response okResponse = getClient().newCall(request).execute();

            if (okResponse.isSuccessful()) {
                Bitmap bitmap = BitmapFactory.decodeStream(okResponse.body().byteStream());
                FileHandler fileHandler = new FileHandler(imageName, file, bitmap);
                if (response.getReturnedObject() instanceof SpriteHolder) {
                    fileHandler.setSpriteHolder((SpriteHolder) response.getReturnedObject());
                }

                response.setReturnedObject(fileHandler);
                response.setStatus(com.github.koshkin.leagueoflegendsstats.networking.Response.Status.SUCCESS);
            } else {
                int code = okResponse.code();
                Log.e(getClass().getSimpleName(), "code" + okResponse.code());
                response.setStatus(getStatusFromCode(code));

                file.delete();
            }
        } catch (IOException e) {
            response.setStatus(com.github.koshkin.leagueoflegendsstats.networking.Response.Status.FAILED);
        }
        return response;
    }

    private com.github.koshkin.leagueoflegendsstats.networking.Response<Object> get() {
        com.github.koshkin.leagueoflegendsstats.networking.Response<Object> response = new com.github.koshkin.leagueoflegendsstats.networking.Response<>();
        try {

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(mRequest.getUrl())
                    .build();

            Log.v(getClass().getSimpleName(), "URL to execute - " + mRequest.getUrl());

            Response okResponse = getClient().newCall(request).execute();

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

    public static OkHttpClient getClient() {
        if (sClient == null)
            sClient = new OkHttpClient();

        return sClient;
    }

    private static OkHttpClient sClient;
}
