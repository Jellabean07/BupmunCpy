package com.wonbuddism.bupmun.Database;

import java.io.Serializable;

public class ScriptureDTO implements Serializable{
    private String IDX;
    private String BUPMUNINDEX;
    private String TITLE1;
    private String TITLE2;
    private String TITLE3;
    private String TITLE4;
    private String BUPMUNSORT;
    private String CONTENTS;
    private String SHORTTITLE;
    private String PARAGRAPH_CNT;
    private String CHINESE_HELP;
    private String TYPING_CONTENT;
    private String BUPMUN_CONFIRM;

    public ScriptureDTO() {
    }

    public ScriptureDTO(String BUPMUNINDEX, String TITLE1, String TITLE2, String TITLE3,
                        String TITLE4, String BUPMUNSORT, String CONTENTS, String SHORTTITLE,
                        String PARAGRAPH_CNT, String CHINESE_HELP) {
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
    }

    public ScriptureDTO(String IDX, String BUPMUNINDEX, String TITLE1, String TITLE2, String TITLE3, String TITLE4, String BUPMUNSORT, String CONTENTS, String SHORTTITLE, String PARAGRAPH_CNT, String CHINESE_HELP, String TYPING_CONTENT, String BUPMUN_CONFIRM) {
        this.IDX = IDX;
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
        this.TYPING_CONTENT = TYPING_CONTENT;
        this.BUPMUN_CONFIRM = BUPMUN_CONFIRM;
    }


    public String getIDX() {
        return IDX;
    }

    public void setIDX(String IDX) {
        this.IDX = IDX;
    }

    public String getTYPING_CONTENT() {
        return TYPING_CONTENT;
    }

    public void setTYPING_CONTENT(String TYPING_CONTENT) {
        this.TYPING_CONTENT = TYPING_CONTENT;
    }

    public String getBUPMUN_CONFIRM() {
        return BUPMUN_CONFIRM;
    }

    public void setBUPMUN_CONFIRM(String BUPMUN_CONFIRM) {
        this.BUPMUN_CONFIRM = BUPMUN_CONFIRM;
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

    public String getPARAGRAPH_CNT() {
        return PARAGRAPH_CNT;
    }

    public void setPARAGRAPH_CNT(String PARAGRAPH_CNT) {
        this.PARAGRAPH_CNT = PARAGRAPH_CNT;
    }

    public String getCHINESE_HELP() {
        return CHINESE_HELP;
    }

    public void setCHINESE_HELP(String CHINESE_HELP) {
        this.CHINESE_HELP = CHINESE_HELP;
    }

    @Override
    public String toString() {
        return "ScriptureDTO{" +
                "BUPMUNINDEX='" + BUPMUNINDEX + '\'' +
                ", TITLE1='" + TITLE1 + '\'' +
                ", TITLE2='" + TITLE2 + '\'' +
                ", TITLE3='" + TITLE3 + '\'' +
                ", TITLE4='" + TITLE4 + '\'' +
                ", BUPMUNSORT='" + BUPMUNSORT + '\'' +
                ", CONTENTS='" + CONTENTS + '\'' +
                ", SHORTTITLE='" + SHORTTITLE + '\'' +
                ", PARAGRAPH_CNT='" + PARAGRAPH_CNT + '\'' +
                ", CHINESE_HELP='" + CHINESE_HELP + '\'' +
                '}';
    }
}
