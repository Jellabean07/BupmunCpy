package com.wonbuddism.bupmun.DataVo;


import java.io.Serializable;

public class FeelingMemo implements Serializable {
    private String bupmunindex; //법문인덱스키,
    private String title; //법문타이틀,
    private String short_title; //법문
    private String memo_seq; //메모인덱스번호,
    private String memo_contents; //메모내용,
    private String regist_date; //메모등록일

    public FeelingMemo() {
    }

    public FeelingMemo(String bupmunindex, String title, String shorttitle, String memo_seq,
                       String memo_contents, String regist_date) {
        this.bupmunindex = bupmunindex;
        this.title = title;
        this.short_title = shorttitle;
        this.memo_seq = memo_seq;
        this.memo_contents = memo_contents;
        this.regist_date = regist_date;
    }

    public FeelingMemo(String bupmunindex, String bupmuntitle, String memo_seq,
                       String memo_contents, String regist_date) {
        this.bupmunindex = bupmunindex;
        this.title = bupmuntitle;
        this.memo_seq = memo_seq;
        this.memo_contents = memo_contents;
        this.regist_date = regist_date;
    }

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public String getBupmunindex() {
        return bupmunindex;
    }

    public void setBupmunindex(String bupmunindex) {
        this.bupmunindex = bupmunindex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo_seq() {
        return memo_seq;
    }

    public void setMemo_seq(String memo_seq) {
        this.memo_seq = memo_seq;
    }

    public String getMemo_contents() {
        return memo_contents;
    }

    public void setMemo_contents(String memo_contents) {
        this.memo_contents = memo_contents;
    }

    public String getRegist_date() {
        return regist_date;
    }

    public void setRegist_date(String regist_date) {
        this.regist_date = regist_date;
    }

    @Override
    public String toString() {
        return "FeelingMemo{" +
                "bupmunindex='" + bupmunindex + '\'' +
                ", title='" + title + '\'' +
                ", short_title='" + short_title + '\'' +
                ", memo_seq='" + memo_seq + '\'' +
                ", memo_contents='" + memo_contents + '\'' +
                ", regist_date='" + regist_date + '\'' +
                '}';
    }
}
