package com.barackbao.aicalligraphy.network;

import android.util.Log;

public class HttpLogger {
    private static final String TAG = "Gancall_okhttp";

    public static void e(String msg) {
        Log.e(TAG, msg);
    }
}
