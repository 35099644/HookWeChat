package com.tensynchina.hookwechat;

import android.app.Application;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by llx on 02/05/2017.
 */

public class AppContext extends Application {

    private static AppContext appContext;

    public static AppContext getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        XposedBridge.log("AppContext onCreate");
    }
}
