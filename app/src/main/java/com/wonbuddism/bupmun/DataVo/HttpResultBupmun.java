package com.wonbuddism.bupmun.DataVo;

import java.io.Serializable;

/**
 * Created by csc-pc on 2016. 7. 9..
 */
public class HttpResultBupmun implements Serializable{
    private int TYPING_CNT;  // int
    private String TYPING_ID; //0000014246
    private String BUPMUNINDEX; //jungjun020102
    private int PARAGRAPH_NO; // int
    private int TASU; // int
    private String REGIST_DATE; //20160707
    private String REGIST_TIME; //032021
    private String TITLE1; //정전(正典)
    private String TITLE2; //제2 교의편(敎義編)
    private String TITLE3; //제1장 일원상(一圓相)
    private String TITLE4; //제2절 일원상의 신앙(一圓相-信仰)
    private String BUPMUNSORT; //01020102
    private String CONTENTS;
    private String SHORTTITLE; //일원상의 신앙
    private int PARAGRAPH_CNT; //int
    private String CONTENTS_KOR;

    public HttpResultBupmun() {
    }

    public HttpResultBupmun(int TYPING_CNT, String TYPING_ID, String BUPMUNINDEX,
                            int PARAGRAPH_NO, int TASU, String REGIST_DATE, String REGIST_TIME,
                            String TITLE1, String TITLE2, String TITLE3, String TITLE4,
                            String BUPMUNSORT, String CONTENTS, String SHORTTITLE,
                            int PARAGRAPH_CNT, String CONTENTS_KOR) {
        this.TYPING_CNT = TYPING_CNT;
        this.TYPING_ID = TYPING_ID;
        this.BUPMUNINDEX = BUPMUNINDEX;
        this.PARAGRAPH_NO = PARAGRAPH_NO;
        this.TASU = TASU;
        this.REGIST_DATE = REGIST_DATE;
        this.REGIST_TIME = REGIST_TIME;
        this.TITLE1 = TITLE1;
        this.TITLE2 = TITLE2;
        this.TITLE3 = TITLE3;
        this.TITLE4 = TITLE4;
        this.BUPMUNSORT = BUPMUNSORT;
        this.CONTENTS = CONTENTS;
        this.SHORTTITLE = SHORTTITLE;
        this.PARAGRAPH_CNT = PARAGRAPH_CNT;
        this.CONTENTS_KOR = CONTENTS_KOR;
    }

    public int getTYPING_CNT() {
        return TYPING_CNT;
    }

    public void setTYPING_CNT(int TYPING_CNT) {
        this.TYPING_CNT = TYPING_CNT;
    }

    public String getTYPING_ID() {
        return TYPING_ID;
    }

    public void setTYPING_ID(String TYPING_ID) {
        this.TYPING_ID = TYPING_ID;
    }

    public String getBUPMUNINDEX() {
        return BUPMUNINDEX;
    }

    public void setBUPMUNINDEX(String BUPMUNINDEX) {
        this.BUPMUNINDEX = BUPMUNINDEX;
    }

    public int getPARAGRAPH_NO() {
        return PARAGRAPH_NO;
    }

    public void setPARAGRAPH_NO(int PARAGRAPH_NO) {
        this.PARAGRAPH_NO = PARAGRAPH_NO;
    }

    public int getTASU() {
        return TASU;
    }

    public void setTASU(int TASU) {
        this.TASU = TASU;
    }

    public String getREGIST_DATE() {
        return REGIST_DATE;
    }

    public void setREGIST_DATE(String REGIST_DATE) {
        this.REGIST_DATE = REGIST_DATE;
    }

    public String getREGIST_TIME() {
        return REGIST_TIME;
    }

    public void setREGIST_TIME(String REGIST_TIME) {
        this.REGIST_TIME = REGIST_TIME;
    }

    public String getTITLE1() {
        return TITLE1;
    }

    public void setTITLE1(String TITLE1) {
        this.TITLE1 = TITLE1;
    }

    public String getTITLE2() {
        return TITLE2;
    }

    public void setTITLE2(String TITLE2) {
        this.TITLE2 = TITLE2;
    }

    public String getTITLE3() {
        return TITLE3;
    }

    public void setTITLE3(String TITLE3) {
        this.TITLE3 = TITLE3;
    }

    public String getTITLE4() {
        return TITLE4;
    }

    public void setTITLE4(String TITLE4) {
        this.TITLE4 = TITLE4;
    }

    public String getBUPMUNSORT() {
        return BUPMUNSORT;
    }

    public void setBUPMUNSORT(String BUPMUNSORT) {
        this.BUPMUNSORT = BUPMUNSORT;
    }

    public String getCONTENTS() {
        return CONTENTS;
    }

    public void setCONTENTS(String CONTENTS) {
        this.CONTENTS = CONTENTS;
    }

    public String getSHORTTITLE() {
        return SHORTTITLE;
    }

    public void setSHORTTITLE(String SHORTTITLE) {
        this.SHORTTITLE = SHORTTITLE;
    }

    public int getPARAGRAPH_CNT() {
        return PARAGRAPH_CNT;
    }

    public void setPARAGRAPH_CNT(int PARAGRAPH_CNT) {
        this.PARAGRAPH_CNT = PARAGRAPH_CNT;
    }

    public String getCONTENTS_KOR() {
        return CONTENTS_KOR;
    }

    public void setCONTENTS_KOR(String CONTENTS_KOR) {
        this.CONTENTS_KOR = CONTENTS_KOR;
    }

    @Override
    public String toString() {
        return "HttpResultBupmun{" +
                "TYPING_CNT=" + TYPING_CNT +
                ", TYPING_ID='" + TYPING_ID + '\'' +
                ", BUPMUNINDEX='" + BUPMUNINDEX + '\'' +
                ", PARAGRAPH_NO=" + PARAGRAPH_NO +
                ", TASU=" + TASU +
                ", REGIST_DATE='" + REGIST_DATE + '\'' +
                ", REGIST_TIME='" + REGIST_TIME + '\'' +
                ", TITLE1='" + TITLE1 + '\'' +
                ", TITLE2='" + TITLE2 + '\'' +
                ", TITLE3='" + TITLE3 + '\'' +
                ", TITLE4='" + TITLE4 + '\'' +
                ", BUPMUNSORT='" + BUPMUNSORT + '\'' +
                ", CONTENTS='" + CONTENTS + '\'' +
                ", SHORTTITLE='" + SHORTTITLE + '\'' +
                ", PARAGRAPH_CNT=" + PARAGRAPH_CNT +
                ", CONTENTS_KOR='" + CONTENTS_KOR + '\'' +
                '}';
    }
}
