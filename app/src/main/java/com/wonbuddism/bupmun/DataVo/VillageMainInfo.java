package com.wonbuddism.bupmun.DataVo;


import java.io.Serializable;

public class VillageMainInfo implements Serializable {
    private String vil_manager; //	   동네운영자이름
    private String vil_name; //	   동네이름
    private String vil_intro; //	   동네소개
    private String vil_open_date; //	   동네개설일
    private String vil_join_date; //	   동네가입일
    private String vil_user_count; //   동네회원수
    private String vil_rank; //	   동네전체순위
    private String vil_id; // 동네방네아이디

    public VillageMainInfo() {
    }

    public VillageMainInfo(String vil_manager, String vil_name,
                           String vil_intro, String vil_open_date, String vil_join_date,
                           String vil_user_count, String vil_rank, String vil_id) {
        this.vil_manager = vil_manager;
        this.vil_name = vil_name;
        this.vil_intro = vil_intro;
        this.vil_open_date = vil_open_date;
        this.vil_join_date = vil_join_date;
        this.vil_user_count = vil_user_count;
        this.vil_rank = vil_rank;
        this.vil_id = vil_id;
    }

    public String getVil_manager() {
        return vil_manager;
    }

    public void setVil_manager(String vil_manager) {
        this.vil_manager = vil_manager;
    }

    public String getVil_name() {
        return vil_name;
    }

    public void setVil_name(String vil_name) {
        this.vil_name = vil_name;
    }

    public String getVil_intro() {
        return vil_intro;
    }

    public void setVil_intro(String vil_intro) {
        this.vil_intro = vil_intro;
    }

    public String getVil_open_date() {
        return vil_open_date;
    }

    public void setVil_open_date(String vil_open_date) {
        this.vil_open_date = vil_open_date;
    }

    public String getVil_join_date() {
        return vil_join_date;
    }

    public void setVil_join_date(String vil_join_date) {
        this.vil_join_date = vil_join_date;
    }

    public String getVil_user_count() {
        return vil_user_count;
    }

    public void setVil_user_count(String vil_user_count) {
        this.vil_user_count = vil_user_count;
    }

    public String getVil_rank() {
        return vil_rank;
    }

    public void setVil_rank(String vil_rank) {
        this.vil_rank = vil_rank;
    }

    public String getVil_id() {
        return vil_id;
    }

    public void setVil_id(String vil_id) {
        this.vil_id = vil_id;
    }
}
