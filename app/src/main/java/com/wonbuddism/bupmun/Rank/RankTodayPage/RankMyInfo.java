package com.wonbuddism.bupmun.Rank.RankTodayPage;

import java.io.Serializable;


public class RankMyInfo implements Serializable {
    private String name;
    private String today_rank ;
    private String today_total_cnt;

    public RankMyInfo() {
    }

    public RankMyInfo(String name, String today_rank, String today_total_cnt) {
        this.name = name;
        this.today_rank = today_rank;
        this.today_total_cnt = today_total_cnt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToday_rank() {
        return today_rank;
    }

    public void setToday_rank(String today_rank) {
        this.today_rank = today_rank;
    }

    public String getToday_total_cnt() {
        return today_total_cnt;
    }

    public void setToday_total_cnt(String today_total_cnt) {
        this.today_total_cnt = today_total_cnt;
    }
}
