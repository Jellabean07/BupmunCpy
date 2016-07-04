package com.wonbuddism.bupmun.Database.DTO.Board;

import com.wonbuddism.bupmun.Database.DTO.SuperDTO;

import java.sql.Timestamp;

/**
 * Created by user on 2016-01-09.
 */
public class BoardDTO extends SuperDTO{

    private int index;
    private String title;
    private String id;
    private Timestamp date;
    private int hit;
    private int category;

    public BoardDTO() {
    }

    public BoardDTO(int index, String title, String id, Timestamp date, int hit, int category) {
        this.index = index;
        this.title = title;
        this.id = id;
        this.date = date;
        this.hit = hit;
        this.category = category;

        query = new String[1];
        query[0] = "select * from BOARD_BOARD";
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "BoardDTO{" +
                "index=" + index +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", date=" + date +
                ", hit=" + hit +
                ", category=" + category +
                '}';
    }

    @Override
    public String[] getQuery() {
        return query;
    }
}
