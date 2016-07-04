package com.wonbuddism.bupmun.MyFeeling;

import java.io.Serializable;

public class FeelingItem implements Serializable{
    private String no;
    private String content;
    private String category;
    private String shortTitle;
    private String date;

    public FeelingItem(String no, String content, String category, String shortTitle, String date) {
        this.no = no;
        this.content = content;
        this.category = category;
        this.shortTitle = shortTitle;
        this.date = date;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "FeelingItem{" +
                "no='" + no + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", shortTitle='" + shortTitle + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
