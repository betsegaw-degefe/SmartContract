package com.gebeya.framework.utils;

import android.content.Context;
import android.util.Log;

public class Util {
    /**
     * Used as the global TAG throughout the application to log and filter out
     * the output only with the provided TAG.
     */
    private static final String TAG = "TAG-SMART-CONTRACT";

    /**
     * Send a simple debug message to the log using the Debug level.
     * @param source Object source of where the message is coming from.
     * @param message The message to show in the log.
     */
    public static void d(Object source, String message) {
        log(source, message, false);
    }

    /**
     * Send a simple debug message to the log using the Error level.
     * @param source Object source of where the message is coming from.
     * @param message The message to show in the log.
     */
    public static void e(Object source, String message) {
        log(source, message, true);
    }

    private static void log(Object source, String message, boolean error) {
        String name = source.getClass().getSimpleName();
        String output = name + " : " + message;
        if (error) {
            Log.e(TAG, output);
        } else {
            Log.d(TAG, output);
        }
    }

    /*public static boolean connected(Context context) {
        Net`
    }*/
}
