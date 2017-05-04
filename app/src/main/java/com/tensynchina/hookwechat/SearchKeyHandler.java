package com.tensynchina.hookwechat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tensynchina.hookwechat.data.Items;
import com.tensynchina.hookwechat.data.Result;
import com.tensynchina.push.IPushServiceAidlInterface;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;


import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * Created by llx on 2017/3/3.
 */

public class SearchKeyHandler extends XC_MethodHook implements Runnable{

    private Activity activity;
    private final Result mResult = new Result();
    private final Object mLock = new Object();
    private boolean mContinueTag = false;
    private Object mWebView;

    private LinkedBlockingQueue<Message> mKeyQueue = new LinkedBlockingQueue<>();

    private Thread mThread;

    private Random mRandom = new Random(300);

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.tensynchina.push.MESSAGE_ACTION")) {
                String uuid = intent.getStringExtra("uuid");
                String key = intent.getStringExtra("key");
                String end_time = intent.getStringExtra("end_time");
                Message msg = new Message();
                msg.uuid = uuid;
                msg.key = key;
                msg.end_time = end_time;
                // 这里面队列满就会抛出异常
                mKeyQueue.add(msg);
            }
        }
    };

    private Result newQuery(final String key) throws UnsupportedEncodingException {

        final EditText et = (EditText) activity.findViewById(2131755283);
        XposedBridge.log("获得 EditText 对象");
        et.postDelayed(new Runnable() {
            @Override
            public void run() {
                XposedBridge.log("进入微信主线程");
                et.setText(key);
                XposedBridge.log("设置key成功");
                try {
                    Class<?> etClass = et.getClass().getSuperclass();
                    XposedBridge.log(etClass.getName());
                    Field mEditorField = etClass.getDeclaredField("mEditor");
                    mEditorField.setAccessible(true);
                    Object mEditor = mEditorField.get(et);
                    //Class<?> editorClass = mEditor.getClass().getSuperclass();
                    Class<?> editorClass = mEditor.getClass();
                    Field inputContentTypeField = editorClass.getDeclaredField("mInputContentType");
                    inputContentTypeField.setAccessible(true);
                    Object inputContentType = inputContentTypeField.get(mEditor);
                    Class<?> inputContentTypeClass = inputContentType.getClass();
                    Field onEditorActionListenerField = inputContentTypeClass.getDeclaredField("onEditorActionListener");
                    onEditorActionListenerField.setAccessible(true);
                    Object onEditorActionListener = onEditorActionListenerField.get(inputContentType);
                    Class<?> onEditorActionListenerClass = onEditorActionListener.getClass();
                    Method onEditorActionMethod = onEditorActionListenerClass.getMethod("onEditorAction", TextView.class, int.class, KeyEvent.class);
                    onEditorActionMethod.invoke(onEditorActionListener,et, EditorInfo.IME_ACTION_SEARCH,null);
                    synchronized (mLock) {
                        mContinueTag = true;
                        mLock.notifyAll();
                    }
                }catch (Exception e) {
                    XposedBridge.log(e);
                    synchronized (mLock) {
                        mContinueTag = true;
                        mLock.notifyAll();
                    }
                }
            }
        },50);
        synchronized (mLock) {
            while (!mContinueTag) {
                try {
                    mLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        mResult.setData("");
        mResult.setHasData(false);
        XposedBridge.log("等待获取数据");
        synchronized (mResult) {
            while (!mResult.isHasData()) {
                try {
                    mResult.wait(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    mResult.setData("异常中断");

                }
            }
        }

        if (!mResult.isHasData()) {
            mResult.setData("超时");
        }

        return mResult;
    }

    private void handleResult(String data,long endTime,String uuid,String key) {

        if (TextUtils.isEmpty(data)) {
            XposedBridge.log("data is null");
            sendMsg(uuid,key,String.valueOf(endTime),data);
            return;
        }
        //目前只查3页
        XposedBridge.log("data: " + data);
        JSONArray items;
        JSONArray dataArray  = JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseObject(data).
                getString("__params")).getString("json")).getJSONArray("data");
        if (dataArray == null) {
            sendMsg(uuid,key,String.valueOf(endTime),data);
            return;
        }
        items =  dataArray.getJSONObject(0).getJSONArray("items");

        List<Items> itemsList = JSONObject.parseArray(items.toJSONString(),Items.class);
        // 对items用时间进行一次排序
        Collections.sort(itemsList);
        // 判断是否到了截止时间
        long lastTime = itemsList.get(itemsList.size() - 1).getDate() * 1000;
        if (lastTime > endTime) {
            XposedBridge.log("lastTime > endTime " + lastTime + " > " + endTime);
            for (int i = 0; i < 2; i++) {
                // 第一页没有查完，继续查下一页
                String nextPageStr = nextPage();
                XposedBridge.log("获得下一页 " + nextPageStr.substring(0,50));
                if (TextUtils.isEmpty(nextPageStr)) {
                    break;
                }
                // 将list转换成json
                JSONArray itemsNext = JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseObject(nextPageStr).
                        getString("__params")).getString("json")).
                        getJSONArray("data").getJSONObject(0).getJSONArray("items");
                List<Items> itemsListNext = JSONObject.parseArray(itemsNext.toJSONString(),Items.class);
                Collections.sort(itemsListNext);
                itemsList.addAll(itemsListNext);
                long lastTimeNext = itemsListNext.get(itemsListNext.size() - 1).getDate() * 1000;
                if (lastTimeNext < endTime) {
                    break;
                }
            }
        } else {
            XposedBridge.log("lastTime < endTime " + lastTime + " < " + endTime);
        }

        Collections.sort(itemsList);
        XposedBridge.log(JSONObject.toJSONString(itemsList));
        sendMsg(uuid,key,String.valueOf(endTime),JSONObject.toJSONString(itemsList));
    }

    private String nextPage() {
        if (mWebView == null) {
            return null;
        }

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        try {
            final Class<?> mWebViewClass = mWebView.getClass().getSuperclass();
            mResult.setData("");
            mResult.setHasData(false);


            ((View)mWebView).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Method pageDown = null;
                    try {
                        pageDown = mWebViewClass.getMethod("pageDown", boolean.class);
                        Object pageDownRet = pageDown.invoke(mWebView, true);
                        XposedBridge.log("pageDownRet = " + pageDownRet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            },50);

            synchronized (mResult) {
                while (!mResult.isHasData()) {
                    mResult.wait(3000);
                }
            }

            if (!mResult.isHasData()) {
                mResult.setData("");
            }

        } catch (Exception e) {
            XposedBridge.log(e);
            mResult.setData("");
        }

        return mResult.getData();
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        if (param.thisObject != null && "com.tencent.mm.plugin.webview.ui.tools.fts.FTSSearchTabWebViewUI".equals(param.thisObject.getClass().getName())) {
            if ("onResume".equals(param.method.getName())) {
                XposedBridge.log("进入关键字搜索页面");
                activity = (Activity) param.thisObject;
                XposedBridge.log("activity package name = " + activity.getPackageName());
                // 注册接收广播
                XposedBridge.log("开启广播");
                registerReceiver(activity);
                // 开启消费者线程
                XposedBridge.log("开启消费者线程");
                mThread = new Thread(this);
                mThread.start();

            } else if ("onPause".equals(param.method.getName())) {
                // 关闭广播
                XposedBridge.log("关闭广播");
                unRegisterReceiver(activity);
                // 关闭消费者线程
                if (mThread!= null) {
                    XposedBridge.log("关闭消费者线程");
                    mThread.interrupt();
                    mThread = null;
                }
            }
        } else if (param.thisObject == null) {
            if ("a".equals(param.method.getName())) {
                if ("onSearchDataReady".equals(param.args[0])) {
                    String data = (String) param.getResult();
                    synchronized (mResult) {
                        mResult.setData(data);
                        mResult.setHasData(true);
                        mResult.notify();
                    }
                }
            }
        } else if (param.thisObject != null && "com.tencent.mm.ui.widget.MMWebView".equals(param.thisObject.getClass().getName())) {
            mWebView = param.thisObject;
        } else if (param.thisObject != null && "com.tencent.mm.plugin.search.ui.FTSMainUI".equals(param.thisObject.getClass().getName())) {
            XposedBridge.log("进入文章搜索页面");
            Activity a = (Activity) param.thisObject;
            View article = a.findViewById(2131757286);
            article.performClick();

        }
    }

    private void registerReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter("com.tensynchina.push.MESSAGE_ACTION");
        context.registerReceiver(mReceiver,intentFilter);
    }

    private void unRegisterReceiver(Context context) {
        context.unregisterReceiver(mReceiver);
    }

    @Override
    public void run() {
        while (true) {
            try {

                Message msg = mKeyQueue.take();

                String uuid = msg.uuid;
                String key = msg.key;
                String endTime = msg.end_time;

                // 开始搜索
                try {
                    XposedBridge.log(msg.toString());

                    Result result = newQuery(key);
                    // 对结果进行处理，以确定是否需要进一步的查询
                    handleResult(result.getData(),Long.parseLong(endTime),uuid,key);
                } catch (Exception e) {
                    XposedBridge.log(e);
                    //mPSReceiver.sendmsg(1,21503,uid,"key:{\"error\":\"error\"}");
                    sendMsg(uuid,key,endTime,"{\"error\":\"error\"}");
                }
                Thread.sleep((mRandom.nextInt(20*1000) + 10*1000));
            } catch (InterruptedException e) {
                XposedBridge.log(e);
                break;
            }
        }
    }

    private void sendMsg(final String uuid,final String key,final String endTime ,final String data) {
        // 绑定远程服务发送数据
        Intent intent = new Intent();
        intent.setAction("com.tensynchina.push.msg");
        intent.setPackage("com.tensynchina.push");
        activity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IPushServiceAidlInterface pushService = IPushServiceAidlInterface.Stub.asInterface(service);
                try {
                    pushService.sendMessage(uuid,key,endTime,data);
                } catch (RemoteException e) {
                    XposedBridge.log("远程服务发送数据失败");
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                XposedBridge.log("远程服务连接失败");
            }
        },Context.BIND_AUTO_CREATE);

    }

    private class Message {
        private String uuid;
        private String key;
        private String end_time;

        @Override
        public String toString() {
            return "Message{" +
                    "uuid='" + uuid + '\'' +
                    ", key='" + key + '\'' +
                    ", end_time='" + end_time + '\'' +
                    '}';
        }
    }
}
