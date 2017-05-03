package com.tensynchina.hookwechat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by llx on 02/05/2017.
 */

public class PSService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        XposedBridge.log("PSService has start");

        return super.onStartCommand(intent, flags, startId);
    }
}
