package com.tensynchina.hookwechat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.AndServerBuild;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findMethodExact;

/**
 * Created by llx on 2017/2/27.
 */

public class XposedHookLoadPackage implements IXposedHookLoadPackage {

    private SearchKeyHandler mHook = new SearchKeyHandler();

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {


        if ("com.example.llx.test".equals(loadPackageParam.packageName)) {

            findAndHookMethod("com.example.llx.test.MainActivityFather", loadPackageParam.classLoader, "a", String.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    XposedBridge.log("hook method a from com.example.llx.test.MainActivityFather parmStr = " + param.args[0]);
                }
            });

        }

        if ("com.tencent.mm".equals(loadPackageParam.packageName)) {

            findAndHookMethod("com.tencent.mm.ui.LauncherUI", loadPackageParam.classLoader, "onResume", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("微信开启主页面...开始点击搜索按钮");
                    final Activity a = (Activity) param.thisObject;
                    View searchIcon = a.findViewById(1);
                    if (searchIcon != null) {
                        searchIcon.performClick();
                    } else {
                        final View content = a.findViewById(android.R.id.content);
                        content.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                View searchIcon = a.findViewById(1);
                                searchIcon.performClick();
                            }
                        },1000);
                    }
                }
            });

            /*findAndHookMethod("com.tencent.mm.plugin.search.ui.FTSMainUI", loadPackageParam.classLoader, "onResume", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("进入文章搜索页面");
                    Activity a = (Activity) param.thisObject;
                    View article = a.findViewById(2131757286);
                    article.performClick();
                }
            });*/

            findAndHookMethod("com.tencent.mm.plugin.search.ui.FTSMainUI", loadPackageParam.classLoader, "onResume", mHook);

            findAndHookMethod("com.tencent.mm.ui.tools.p", loadPackageParam.classLoader, "a", "android.support.v4.app.FragmentActivity", "android.view.Menu", mHook);

            findAndHookMethod("com.tencent.mm.plugin.webview.ui.tools.fts.FTSSearchTabWebViewUI", loadPackageParam.classLoader, "onResume", mHook);

            findAndHookMethod("com.tencent.mm.plugin.webview.ui.tools.fts.FTSSearchTabWebViewUI", loadPackageParam.classLoader, "onPause", mHook);

            findAndHookMethod("com.tencent.mm.plugin.webview.ui.tools.jsapi.i.a", loadPackageParam.classLoader, "a", String.class, Map.class, boolean.class, String.class, mHook);

            findAndHookConstructor("com.tencent.smtt.sdk.WebView", loadPackageParam.classLoader, Context.class, AttributeSet.class, int.class, Map.class, boolean.class, mHook);

        }
    }
}
