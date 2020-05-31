package com.example.mymusic.utils;

import android.util.Log;

public class LogUtils {
    private static String basetag = "LogUtils";

    private static boolean isUsed = true;

    public static void init(String basetag, boolean isUsed){
        LogUtils.basetag = basetag;
        LogUtils.isUsed = isUsed;
    }

    public static void d(String tag, String message){
        if(isUsed){
            Log.d("[" + tag + "] -> ", message);
        }
    }

}
