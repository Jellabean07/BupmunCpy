package com.wonbuddism.bupmun.DataVo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by csc-pc on 2016. 7. 25..
 */
public class HttpParamProgress implements Serializable{
    private String index;
    private String title_no;
    private String ndepth;
    private ArrayList<String> title;


    public HttpParamProgress() {
    }

    public HttpParamProgress(String index, String title_no, String ndepth) {
        this.index = index;
        this.title_no = title_no;
        this.ndepth = ndepth;
        this.title = new ArrayList<>();
    }


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle_no() {
        return title_no;
    }

    public void setTitle_no(String title_no) {
        this.title_no = title_no;
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    public String getNdepth() {
        return ndepth;
    }

    public void setNdepth(String ndepth) {
        this.ndepth = ndepth;
    }

    @Override
    public String toString() {
        return "HttpParamProgress{" +
                "index='" + index + '\'' +
                ", title_no='" + title_no + '\'' +
                ", ndepth='" + ndepth + '\'' +
                ", title=" + title +
                '}';
    }
}
