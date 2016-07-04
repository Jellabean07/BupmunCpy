package com.wonbuddism.bupmun.Village.HTTPconnection;

public class VillageComments {
    private String comment_no; //글번호
    private String userid; //사경아이디
    private String comment_name; //글쓴이 이름
    private String comment_contents; //내용
    private String comment_date; //글쓴날짜

    public VillageComments() {
    }

    public VillageComments(String comment_no, String typing_id, String comment_name, String comment_contents, String comment_date) {
        this.comment_no = comment_no;
        this.userid = typing_id;
        this.comment_name = comment_name;
        this.comment_contents = comment_contents;
        this.comment_date = comment_date;
    }

    public String getComment_no() {
        return comment_no;
    }

    public void setComment_no(String comment_no) {
        this.comment_no = comment_no;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getComment_name() {
        return comment_name;
    }

    public void setComment_name(String comment_name) {
        this.comment_name = comment_name;
    }

    public String getComment_contents() {
        return comment_contents;
    }

    public void setComment_contents(String comment_contents) {
        this.comment_contents = comment_contents;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }
}
