package com.github.koshkin.leagueoflegendsstats.networking;

import java.text.ParseException;

/**
 * Created by tehras on 1/9/16.
 */
public class Request {

    private final String[] mParams;
    private RequestType mRequestType;
    private ParserCallback mParserCallback;
    private RequestCallback mRequestCallback;
    private URIHelper mURIHelper;

    public Request(RequestType requestType, ParserCallback parserCallback, RequestCallback requestCallback, URIHelper uriHelper, String... params) {
        mRequestType = requestType;
        mParserCallback = parserCallback;
        mRequestCallback = requestCallback;
        mURIHelper = uriHelper;
        mParams = params;
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

    public void finished(Response response) {
        mRequestCallback.finished(response, this);
    }

    public enum RequestType {
        GET, POST, PUT, DELETE
    }

    public interface ParserCallback<T> {
        T parse(String body);
    }

    public interface RequestCallback<T> {
        void finished(Response<T> response, Request request);
    }
}
