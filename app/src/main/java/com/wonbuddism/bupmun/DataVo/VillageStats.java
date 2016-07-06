package com.wonbuddism.bupmun.DataVo;

import java.io.Serializable;

public class VillageStats implements Serializable {
    private String vil_id; // 동네방네아이디
    private String vil_today_cnt; //오늘 단락수
    private String vil_month_cnt; //최근 30일 단락수
    private String vil_total_cnt; //누적 단락수
    private String vil_cnt; //동네 전체 개수
    private String vil_rank; //동네 전체순위
    private String member_today_cnt; //오늘 단락수
    private String member_month_cnt; //최근 30일 단락수
    private String member_total_cnt; //누적 단락수
    private String member_cnt; //동네 회원수
    private String member_rank; //동네에서 내 순위

    public VillageStats() {
    }

    public VillageStats(String vil_id,String vil_today_cnt, String vil_month_cnt, String vil_total_cnt,
                        String vil_cnt, String vil_rank, String member_today_cnt,
                        String member_month_cnt, String member_total_cnt, String member_cnt, String member_rank) {
        this.vil_id = vil_id;
        this.vil_today_cnt = vil_today_cnt;
        this.vil_month_cnt = vil_month_cnt;
        this.vil_total_cnt = vil_total_cnt;
        this.vil_cnt = vil_cnt;
        this.vil_rank = vil_rank;
        this.member_today_cnt = member_today_cnt;
        this.member_month_cnt = member_month_cnt;
        this.member_total_cnt = member_total_cnt;
        this.member_cnt = member_cnt;
        this.member_rank = member_rank;
    }

    public String getVil_id() {
        return vil_id;
    }

    public void setVil_id(String vil_id) {
        this.vil_id = vil_id;
    }

    public String getVil_today_cnt() {
        return vil_today_cnt;
    }

    public void setVil_today_cnt(String vil_today_cnt) {
        this.vil_today_cnt = vil_today_cnt;
    }

    public String getVil_month_cnt() {
        return vil_month_cnt;
    }

    public void setVil_month_cnt(String vil_month_cnt) {
        this.vil_month_cnt = vil_month_cnt;
    }

    public String getVil_total_cnt() {
        return vil_total_cnt;
    }

    public void setVil_total_cnt(String vil_total_cnt) {
        this.vil_total_cnt = vil_total_cnt;
    }

    public String getVil_cnt() {
        return vil_cnt;
    }

    public void setVil_cnt(String vil_cnt) {
        this.vil_cnt = vil_cnt;
    }

    public String getVil_rank() {
        return vil_rank;
    }

    public void setVil_rank(String vil_rank) {
        this.vil_rank = vil_rank;
    }

    public String getMember_today_cnt() {
        return member_today_cnt;
    }

    public void setMember_today_cnt(String member_today_cnt) {
        this.member_today_cnt = member_today_cnt;
    }

    public String getMember_month_cnt() {
        return member_month_cnt;
    }

    public void setMember_month_cnt(String member_month_cnt) {
        this.member_month_cnt = member_month_cnt;
    }

    public String getMember_total_cnt() {
        return member_total_cnt;
    }

    public void setMember_total_cnt(String member_total_cnt) {
        this.member_total_cnt = member_total_cnt;
    }

    public String getMember_cnt() {
        return member_cnt;
    }

    public void setMember_cnt(String member_cnt) {
        this.member_cnt = member_cnt;
    }

    public String getMember_rank() {
        return member_rank;
    }

    public void setMember_rank(String member_rank) {
        this.member_rank = member_rank;
    }
}
