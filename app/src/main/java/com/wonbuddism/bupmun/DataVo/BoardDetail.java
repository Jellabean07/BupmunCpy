package com.wonbuddism.bupmun.DataVo;

import java.io.Serializable;

/**
 * Created by csc-pc on 2016. 3. 4..
 */
public class BoardDetail implements Serializable{
    private String boardno; //게시판번호
    private String writeno; //글번호
    private String title; //제목
    private String readcnt; //조회수
    private String replydepth; //답글개수
    private String content; //내용
    private String userid; //작성자아이디
    private String writetime; //작성일시


    public BoardDetail() {
    }

    public BoardDetail(String boardno, String writeno, String title, String readcnt,
                       String replydepth, String content, String userid, String writetime) {
        this.boardno = boardno;
        this.writeno = writeno;
        this.title = title;
        this.readcnt = readcnt;
        this.replydepth = replydepth;
        this.content = content;
        this.userid = userid;
        this.writetime = writetime;
    }

    public String getBoardno() {
        return boardno;
    }

    public void setBoardno(String boardno) {
        this.boardno = boardno;
    }

    public String getWriteno() {
        return writeno;
    }

    public void setWriteno(String writeno) {
        this.writeno = writeno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReadcnt() {
        return readcnt;
    }

    public void setReadcnt(String readcnt) {
        this.readcnt = readcnt;
    }

    public String getReplydepth() {
        return replydepth;
    }

    public void setReplydepth(String replydepth) {
        this.replydepth = replydepth;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getWritetime() {
        return writetime;
    }

    public void setWritetime(String writetime) {
        this.writetime = writetime;
    }
}
