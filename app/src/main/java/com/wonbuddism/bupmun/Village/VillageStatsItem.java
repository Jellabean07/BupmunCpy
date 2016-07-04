package com.wonbuddism.bupmun.Village;

/**
 * Created by user on 2016-01-23.
 */
public class VillageStatsItem {

    private int no;
    private String name;
    private String id;
    private int today;
    private int month;
    private int cumulation;

    public VillageStatsItem() {
    }

    public VillageStatsItem(int no, String name, String id, int today, int month, int cumulation) {
        this.no = no;
        this.name = name;
        this.id = id;
        this.today = today;
        this.month = month;
        this.cumulation = cumulation;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getCumulation() {
        return cumulation;
    }

    public void setCumulation(int cumulation) {
        this.cumulation = cumulation;
    }
}
