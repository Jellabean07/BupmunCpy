package com.wonbuddism.bupmun.DataVo;

public class BoardArticle {

    private String boardno; //게시판번호
    private String writeno; //글번호
    private String title; //제목
    private String readcnt; //조회수
    private String parentwriteno; //부모 글번호
    private String founderwriteno; //묶음 글번호
    private String replydepth; //답글순서
    private String replyorder; //답글순서
    private String userid; //작성자아이디
    private String writetime; //작성일시

    public BoardArticle() {
    }

    public BoardArticle(String boardno, String writeno, String title,
                        String readcnt, String parentwriteno, String founderwriteno,
                        String replydepth, String replyorder, String userid, String writetime) {
        this.boardno = boardno;
        this.writeno = writeno;
        this.title = title;
        this.readcnt = readcnt;
        this.parentwriteno = parentwriteno;
        this.founderwriteno = founderwriteno;
        this.replydepth = replydepth;
        this.replyorder = replyorder;
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

    public String getParentwriteno() {
        return parentwriteno;
    }

    public void setParentwriteno(String parentwriteno) {
        this.parentwriteno = parentwriteno;
    }

    public String getFounderwriteno() {
        return founderwriteno;
    }

    public void setFounderwriteno(String founderwriteno) {
        this.founderwriteno = founderwriteno;
    }

    public String getReplydepth() {
        return replydepth;
    }

    public void setReplydepth(String replydepth) {
        this.replydepth = replydepth;
    }


    public String getReplyorder() {
        return replyorder;
    }

    public void setReplyorder(String replyorder) {
        this.replyorder = replyorder;
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
