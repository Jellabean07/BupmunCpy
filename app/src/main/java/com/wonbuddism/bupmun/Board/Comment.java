package com.wonbuddism.bupmun.Board;

/**
 * Created by user on 2016-01-08.
 */
public class Comment {
    private int no;
    private String id;
    private String name;
    private String content;
    private String date;
    private int index;
    //12/12/3 12:22
    public Comment() {
    }

    public Comment(int no, String id, String name, String content, String date) {
        this.no = no;
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
}
