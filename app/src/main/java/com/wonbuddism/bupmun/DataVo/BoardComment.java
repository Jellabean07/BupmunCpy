package com.wonbuddism.bupmun.DataVo;


public class BoardComment {
   private String seqno; // 댓글번호
    private String content; //댓글내용
    private String userid; // 작성자아이디
    private String username; //작성자이름
    private String writetime; //작성시간

    public BoardComment() {
    }

    public BoardComment(String seqno, String content, String userid, String username, String writetime) {
        this.seqno = seqno;
        this.content = content;
        this.userid = userid;
        this.username = username;
        this.writetime = writetime;
    }

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWritetime() {
        return writetime;
    }

    public void setWritetime(String writetime) {
        this.writetime = writetime;
    }

    @Override
    public String toString() {
        return "BoardComment{" +
                "seqno='" + seqno + '\'' +
                ", content='" + content + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", writetime='" + writetime + '\'' +
                '}';
    }
}
