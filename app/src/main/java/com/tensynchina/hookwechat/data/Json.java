package com.tensynchina.hookwechat.data;

import java.util.List;

/**
 * Created by llx on 25/04/2017.
 */

public class Json {
    private int continueFlag;

    private List<Data> data ;

    private int direction;

    private int exposeMs;

    private int isDivide;

    private int isExpose;

    private int isHomePage;

    private String lang;

    private int monitorMs;

    private int offset;

    private String query;

    private int resultType;

    private int ret;

    private String searchID;

    public void setContinueFlag(int continueFlag){
        this.continueFlag = continueFlag;
    }
    public int getContinueFlag(){
        return this.continueFlag;
    }
    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
        return this.data;
    }
    public void setDirection(int direction){
        this.direction = direction;
    }
    public int getDirection(){
        return this.direction;
    }
    public void setExposeMs(int exposeMs){
        this.exposeMs = exposeMs;
    }
    public int getExposeMs(){
        return this.exposeMs;
    }
    public void setIsDivide(int isDivide){
        this.isDivide = isDivide;
    }
    public int getIsDivide(){
        return this.isDivide;
    }
    public void setIsExpose(int isExpose){
        this.isExpose = isExpose;
    }
    public int getIsExpose(){
        return this.isExpose;
    }
    public void setIsHomePage(int isHomePage){
        this.isHomePage = isHomePage;
    }
    public int getIsHomePage(){
        return this.isHomePage;
    }
    public void setLang(String lang){
        this.lang = lang;
    }
    public String getLang(){
        return this.lang;
    }
    public void setMonitorMs(int monitorMs){
        this.monitorMs = monitorMs;
    }
    public int getMonitorMs(){
        return this.monitorMs;
    }
    public void setOffset(int offset){
        this.offset = offset;
    }
    public int getOffset(){
        return this.offset;
    }
    public void setQuery(String query){
        this.query = query;
    }
    public String getQuery(){
        return this.query;
    }
    public void setResultType(int resultType){
        this.resultType = resultType;
    }
    public int getResultType(){
        return this.resultType;
    }
    public void setRet(int ret){
        this.ret = ret;
    }
    public int getRet(){
        return this.ret;
    }
    public void setSearchID(String searchID){
        this.searchID = searchID;
    }
    public String getSearchID(){
        return this.searchID;
    }
}
