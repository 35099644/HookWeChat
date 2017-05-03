package com.tensynchina.hookwechat.data;

/**
 * Created by llx on 25/04/2017.
 */

public class SearchData {
    private String __msg_type;

    private String __event_id;

    private Params __params;

    public void set__msg_type(String __msg_type){
        this.__msg_type = __msg_type;
    }
    public String get__msg_type(){
        return this.__msg_type;
    }
    public void set__event_id(String __event_id){
        this.__event_id = __event_id;
    }
    public String get__event_id(){
        return this.__event_id;
    }
    public void set__params(Params __params){
        this.__params = __params;
    }
    public Params get__params(){
        return this.__params;
    }

    @Override
    public String toString() {
        return "SearchData{" +
                "__msg_type='" + __msg_type + '\'' +
                ", __event_id='" + __event_id + '\'' +
                ", __params=" + __params +
                '}';
    }
}
