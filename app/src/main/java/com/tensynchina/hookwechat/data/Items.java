package com.tensynchina.hookwechat.data;

import android.support.annotation.NonNull;

/**
 * Created by llx on 25/04/2017.
 */

public class Items implements Comparable<Items> {
    private long date;

    private String desc;

    private String docID;

    private String doc_url;

    private int news_type;

    private String source;

    private String thumbUrl;

    private String title;

    public void setDate(int date){
        this.date = date;
    }
    public long getDate(){
        return this.date;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
    public void setDocID(String docID){
        this.docID = docID;
    }
    public String getDocID(){
        return this.docID;
    }
    public void setDoc_url(String doc_url){
        this.doc_url = doc_url;
    }
    public String getDoc_url(){
        return this.doc_url;
    }
    public void setNews_type(int news_type){
        this.news_type = news_type;
    }
    public int getNews_type(){
        return this.news_type;
    }
    public void setSource(String source){
        this.source = source;
    }
    public String getSource(){
        return this.source;
    }
    public void setThumbUrl(String thumbUrl){
        this.thumbUrl = thumbUrl;
    }
    public String getThumbUrl(){
        return this.thumbUrl;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    @Override
    public String toString() {
        return "Items{" +
                "date=" + date +
                ", desc='" + desc + '\'' +
                ", docID='" + docID + '\'' +
                ", doc_url='" + doc_url + '\'' +
                ", news_type=" + news_type +
                ", source='" + source + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    // 与自然排序相反
    @Override
    public int compareTo(@NonNull Items o) {

        if (this.date < o.getDate()) {
            return 1;
        }

        if (this.date > o.getDate()) {
            return -1;
        }

        return 0;
    }
}
