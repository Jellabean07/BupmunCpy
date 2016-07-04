package com.wonbuddism.bupmun.Village;

/**
 * Created by user on 2016-01-22.
 */
public class VillageItem {
    private int no;
    private String name;
    private String id;
    private String content;
    private String date;
    private int index;

    public VillageItem() {
    }

    public VillageItem(int no, String name, String id, String content, String date) {
        this.no = no;
        this.name = name;
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
