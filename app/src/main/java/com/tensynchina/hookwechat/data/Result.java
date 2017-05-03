package com.tensynchina.hookwechat.data;

/**
 * Created by llx on 2017/3/3.
 */

public class Result {
    private String data;
    private boolean hasData;

    public Result() {
        hasData = false;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }
}
