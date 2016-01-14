package com.github.koshkin.leagueoflegendsstats.networking;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tehras on 1/9/16.
 * <p/>
 * Request class to contain request elements
 */
public class Request {

    public static String KEY_IMAGE_NAME = "key_image_name";

    private final String[] mParams;
    private RequestType mRequestType;
    private ParserCallback mParserCallback;
    private RequestCallback mRequestCallback;
    private URIHelper mURIHelper;
    private Map<String, String> mExtraParams;

    public Request(RequestType requestType, ParserCallback parserCallback, RequestCallback requestCallback, URIHelper uriHelper, String... params) {
        mRequestType = requestType;
        mParserCallback = parserCallback;
        mRequestCallback = requestCallback;
        mURIHelper = uriHelper;
        mParams = params;
    }

    public Request addExtraParam(String key, String value) {
        if (mExtraParams == null)
            mExtraParams = new HashMap<>();

        mExtraParams.put(key, value);

        return this;
    }

    public URIHelper getURIHelper() {
        return mURIHelper;
    }

    public String getUrl() {
        return mURIHelper.getUrl(mParams);
    }

    public RequestType getRequestType() {
        return mRequestType;
    }

    public Object parse(String body) throws ParseException {
        if (mParserCallback != null)
            return mParserCallback.parse(body);

        return null;
    }

    public Map<String, String> getExtraParams() {
        return mExtraParams;
    }

    public void finished(Response response) {
        mRequestCallback.finished(response, this);
    }

    public enum RequestType {
        GET, POST, PUT, GET_IMAGE, DELETE
    }

    public interface ParserCallback<T> {
        T parse(String body);
    }

    public interface RequestCallback<T> {
        void finished(Response<T> response, Request request);
    }
}
