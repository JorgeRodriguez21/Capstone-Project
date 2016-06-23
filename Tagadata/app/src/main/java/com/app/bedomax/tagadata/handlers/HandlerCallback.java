package com.app.bedomax.tagadata.handlers;

/**
 * Created by Jorge on 22/06/16.
 */
public class HandlerCallback {

    private int status;
    public static final int OK = 1;
    public static final int FAIL = 2;

    public interface listener {
        void onResponse(int status);
    }
}
