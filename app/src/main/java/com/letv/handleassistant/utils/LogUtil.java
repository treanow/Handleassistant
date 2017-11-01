package com.letv.handleassistant.utils;
import android.util.Log;

public class LogUtil {
    private static final boolean isLog = true;
    private static final String DEFAULT_TAG = "callback";

    public static void log(String tag, int level, String msg, Throwable tr) {
        if (isLog) {
            switch (level) {
                case Log.VERBOSE:
                    if (tr == null) {
                        Log.v(tag, msg);
                    } else {
                        Log.v(tag, msg, tr);
                    }
                    break;
                case Log.INFO:
                    if (tr == null) {
                        Log.i(tag, msg);
                    } else {
                        Log.i(tag, msg, tr);
                    }
                    break;
                case Log.DEBUG:
                    if (tr == null) {
                        Log.d(tag, msg);
                    } else {
                        Log.d(tag, msg, tr);
                    }
                    break;
                case Log.WARN:
                    if (tr == null) {
                        Log.w(tag, msg);
                    } else {
                        Log.w(tag, msg, tr);
                    }
                    break;
                case Log.ERROR:
                    if (tr == null) {
                        Log.e(tag, msg, tr);
                    } else {
                        Log.e(tag, msg, tr);
                    }

                    break;
            }
        }

    }

    public static void log(String tag, int level, String msg) {
        if (isLog) {
            log(tag, level, msg, null);
        }

    }
    
    public static void log(String msg) {
        if (isLog) {
            log(DEFAULT_TAG, Log.INFO, msg, null);
        }
    }
    
    
}
