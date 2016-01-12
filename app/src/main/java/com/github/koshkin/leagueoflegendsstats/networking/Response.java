package com.github.koshkin.leagueoflegendsstats.networking;

/**
 * Created by tehras on 1/9/16.
 */
public class Response<T> {

    private Status mStatus;
    private T mReturnedObject;

    public T getReturnedObject() {
        return mReturnedObject;
    }

    public void setReturnedObject(T returnedObject) {
        mReturnedObject = returnedObject;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public enum Status {
        SUCCESS, FAILED, PARSE_EXCEPTION, NOT_FOUND, NOT_AUTHORIZED
    }
}
