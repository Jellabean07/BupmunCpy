package com.wonbuddism.bupmun.DataVo;

import java.io.Serializable;

/**
 * Created by user on 2016-01-13.
 */
public class RankUserInfo implements Serializable{
    private String no;
    private String name;
    private String id;


    public RankUserInfo(String no, String name, String id) {
        this.no = no;
        this.name = name;
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
