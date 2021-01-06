package com.abdullah.goldenapp.network;

/**
 * Created by jazib Shehraz on 11/4/2017.
 */

public abstract class WSResponse {
    private static String TAG = "WSResponse";

    abstract public void onSuccess(String responce);

    abstract public void onError(String error);


}