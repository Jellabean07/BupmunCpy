package com.wonbuddism.bupmun.Database.Typing;

import java.io.Serializable;

/**
 * Created by csc-pc on 2016. 3. 25..
 */
public class BUPMUN_TYPING_INDEX implements Serializable{
    private int NO;
    private String BUPMUNINDEX; //법문인덱스키	VARCHAR
    private String TITLE1; //	제목1	VARCHAR
    private String TITLE2; //	제목2	VARCHAR
    private String TITLE3; //	제목3	VARCHAR
    private String TITLE4; //	제목4	VARCHAR
    private String BUPMUNSORT; //	법문정렬	VARCHAR
    private String CONTENTS; //	법문내용	CLOB
    private String SHORTTITLE; //	법문타이틀	VARCHAR
    private int PARAGRAPH_CNT; //	단락수	SMALLINT
    private String CHINESE_HELP; //	한자도우미	CLOB
    private String CONTENTS_KOR; //	법문내용(한글)	CLOB


    public BUPMUN_TYPING_INDEX() {
    }

    public BUPMUN_TYPING_INDEX(String BUPMUNINDEX, String TITLE1, String TITLE2,
                      String TITLE3, String TITLE4, String BUPMUNSORT,
                      String CONTENTS, String SHORTTITLE, int PARAGRAPH_CNT,
                      String CHINESE_HELP, String CONTENTS_KOR) {

        this.BUPMUNINDEX = BUPMUNINDEX;
        this.TITLE1 = TITLE1;
        this.TITLE2 = TITLE2;
        this.TITLE3 = TITLE3;
        this.TITLE4 = TITLE4;
        this.BUPMUNSORT = BUPMUNSORT;
        this.CONTENTS = CONTENTS;
        this.SHORTTITLE = SHORTTITLE;
        this.PARAGRAPH_CNT = PARAGRAPH_CNT;
        this.CHINESE_HELP = CHINESE_HELP;
        this.CONTENTS_KOR = CONTENTS_KOR;
    }

    public BUPMUN_TYPING_INDEX(int NO, String BUPMUNINDEX, String TITLE1, String TITLE2,
                      String TITLE3, String TITLE4, String BUPMUNSORT, String CONTENTS,
                      String SHORTTITLE, int PARAGRAPH_CNT, String CHINESE_HELP, String CONTENTS_KOR) {

        this.NO = NO;
        this.BUPMUNINDEX = BUPMUNINDEX;
        this.TITLE1 = TITLE1;
        this.TITLE2 = TITLE2;
        this.TITLE3 = TITLE3;
        this.TITLE4 = TITLE4;
        this.BUPMUNSORT = BUPMUNSORT;
        this.CONTENTS = CONTENTS;
        this.SHORTTITLE = SHORTTITLE;
        this.PARAGRAPH_CNT = PARAGRAPH_CNT;
        this.CHINESE_HELP = CHINESE_HELP;
        this.CONTENTS_KOR = CONTENTS_KOR;
    }

    public int getNO() {
        return NO;
    }

    public void setNO(int NO) {
        this.NO = NO;
    }

    public String getBUPMUNINDEX() {
        return BUPMUNINDEX;
    }

    public void setBUPMUNINDEX(String BUPMUNINDEX) {
        this.BUPMUNINDEX = BUPMUNINDEX;
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

    public String getCHINESE_HELP() {
        return CHINESE_HELP;
    }

    public void setCHINESE_HELP(String CHINESE_HELP) {
        this.CHINESE_HELP = CHINESE_HELP;
    }

    public String getCONTENTS_KOR() {
        return CONTENTS_KOR;
    }

    public void setCONTENTS_KOR(String CONTENTS_KOR) {
        this.CONTENTS_KOR = CONTENTS_KOR;
    }

    @Override
    public String toString() {
        return "BUPMUN_TYPING_INDEX{" +
                "NO=" + NO +
                ", BUPMUNINDEX='" + BUPMUNINDEX + '\'' +
                ", TITLE1='" + TITLE1 + '\'' +
                ", TITLE2='" + TITLE2 + '\'' +
                ", TITLE3='" + TITLE3 + '\'' +
                ", TITLE4='" + TITLE4 + '\'' +
                ", BUPMUNSORT='" + BUPMUNSORT + '\'' +
                ", CONTENTS='" + CONTENTS + '\'' +
                ", SHORTTITLE='" + SHORTTITLE + '\'' +
                ", PARAGRAPH_CNT=" + PARAGRAPH_CNT +
                ", CHINESE_HELP='" + CHINESE_HELP + '\'' +
                ", CONTENTS_KOR='" + CONTENTS_KOR + '\'' +
                '}';
    }
}
