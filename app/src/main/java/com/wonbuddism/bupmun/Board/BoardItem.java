package com.wonbuddism.bupmun.Board;

import java.io.Serializable;

/**
 * Created by user on 2015-12-30.
 */
public class BoardItem implements Serializable{
    private int no;
    private String category;
    private String id;
    private String name;
    private String title;
    private String content;
    private String date;
    private int hit;
    private int coment_count;


    public BoardItem() {
    }

    public BoardItem(String category, String id, String name, String title, String content, String date, int hit, int coment_count) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.date = date;
        this.hit = hit;
        this.coment_count = coment_count;
    }

    public BoardItem(int no, String category, String id, String name, String title, String content, String date, int hit, int coment_count) {
        this.no = no;
        this.category = category;
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.date = date;
        this.hit = hit;
        this.coment_count = coment_count;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getComent_count() {
        return coment_count;
    }

    public void setComent_count(int coment_count) {
        this.coment_count = coment_count;
    }

    public void increaseHit(){
        hit++;
    }
}
