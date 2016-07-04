package com.wonbuddism.bupmun.Village.HTTPconnection;

public class VillageStatsMember {
    private String no; //동네안 순위
    private String userid; //유저 아이디
    private String name; //유저 이름
    private String today_cnt; // 오늘 단락수
    private String month_cnt; //최근 30일 단락수
    private String total_cnt; //누적 단락수

    public VillageStatsMember() {
    }

    public VillageStatsMember(String no, String userid, String name, String today_cnt, String month_cnt, String total_cnt) {
        this.no = no;
        this.userid = userid;
        this.name = name;
        this.today_cnt = today_cnt;
        this.month_cnt = month_cnt;
        this.total_cnt = total_cnt;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToday_cnt() {
        return today_cnt;
    }

    public void setToday_cnt(String today_cnt) {
        this.today_cnt = today_cnt;
    }

    public String getMonth_cnt() {
        return month_cnt;
    }

    public void setMonth_cnt(String month_cnt) {
        this.month_cnt = month_cnt;
    }

    public String getTotal_cnt() {
        return total_cnt;
    }

    public void setTotal_cnt(String total_cnt) {
        this.total_cnt = total_cnt;
    }
}
