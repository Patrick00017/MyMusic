package com.example.mymusic.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.example.mymusic.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

public class BaseApplication extends Application {

    private static Handler sHandler = null;
    private static Context sContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        CommonRequest mXimalaya = CommonRequest.getInstanse();
        if(DTransferConstants.isRelease) {
            String mAppSecret = "8646d66d6abe2efd14f2891f9fd1c8af";
            mXimalaya.setAppkey("9f9ef8f10bebeaa83e71e62f935bede8");
            mXimalaya.setPackid("com.app.test.android");
            mXimalaya.init(this ,mAppSecret);
        } else {
            String mAppSecret = "0a09d7093bff3d4947a5c4da0125972e";
            mXimalaya.setAppkey("f4d8f65918d9878e1702d49a8cdf0183");
            mXimalaya.setPackid("com.ximalaya.qunfeng");
            mXimalaya.init(this ,mAppSecret);
        }

        sContext = getBaseContext();
        sHandler = new Handler();
        LogUtils.init("Himalaya", true);
        //初始化音乐播放器
        XmPlayerManager.getInstance(this).init();
    }

    public static Handler getHandler(){
        return sHandler;
    }

    public static Context getAppContext(){
        return sContext;
    }
}
