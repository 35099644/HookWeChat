package com.tensynchina.hookwechat.data;

import java.util.List;

/**
 * Created by llx on 25/04/2017.
 */

public class Data {
    private int count;

    private List<Items> items ;

    private int resultType;

    private String title;

    private int totalCount;

    private int type;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setItems(List<Items> items){
        this.items = items;
    }
    public List<Items> getItems(){
        return this.items;
    }
    public void setResultType(int resultType){
        this.resultType = resultType;
    }
    public int getResultType(){
        return this.resultType;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
    }
    public int getTotalCount(){
        return this.totalCount;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
}
