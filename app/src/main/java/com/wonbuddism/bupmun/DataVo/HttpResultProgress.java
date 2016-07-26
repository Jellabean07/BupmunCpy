package com.wonbuddism.bupmun.DataVo;

import java.io.Serializable;

/**
 * Created by csc-pc on 2016. 7. 25..
 */
public class HttpResultProgress implements Serializable{

    private String title;
    private String ndepth;
    private String complete;

      /*      "TITLE": "개교표어",
            "NDEPTH": "F",
            "COMPLETE": "NONE"*/

    public HttpResultProgress() {
    }

    public HttpResultProgress(String title, String ndepth, String complete) {
        this.title = title;
        this.ndepth = ndepth;
        this.complete = complete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNdepth() {
        return ndepth;
    }

    public void setNdepth(String ndepth) {
        this.ndepth = ndepth;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }
}
