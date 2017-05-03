package com.tensynchina.hookwechat.data;

/**
 * Created by llx on 25/04/2017.
 */

public class Params {
    private boolean newQuery;

    private String json;

    public void setNewQuery(boolean newQuery){
        this.newQuery = newQuery;
    }
    public boolean getNewQuery(){
        return this.newQuery;
    }
    public void setJson(String json){
        this.json = json;
    }
    public String getJson(){
        return this.json;
    }

    @Override
    public String toString() {
        return "Params{" +
                "newQuery=" + newQuery +
                ", json='" + json + '\'' +
                '}';
    }
}
