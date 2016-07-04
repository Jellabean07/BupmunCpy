package com.wonbuddism.bupmun.Database.Typing;


public class TYPING_HIST_LOCAL {
    private int TYPING_CNT; //	사경횟수	SMALLINT
    private String TYPING_ID; //	사경아이디	CHARACTER
    private String BUPMUNINDEX; //	법문인덱스키	VARCHAR
    private int PARAGRAPH_NO; //	문단번호	SMALLINT
    private String CHNS_YN; //	한자포함여부	CHARACTER
    private int TASU; //	타수	SMALLINT
    private String REGIST_DATE; //	사경일	CHARACTER
    private String REGIST_TIME; //	사경시간	CHARACTER


    public TYPING_HIST_LOCAL() {
    }

    public TYPING_HIST_LOCAL(int TYPING_CNT, String TYPING_ID, String BUPMUNINDEX,
                             int PARAGRAPH_NO, String CHNS_YN, int TASU,
                             String REGIST_DATE, String REGIST_TIME) {
        this.TYPING_CNT = TYPING_CNT;
        this.TYPING_ID = TYPING_ID;
        this.BUPMUNINDEX = BUPMUNINDEX;
        this.PARAGRAPH_NO = PARAGRAPH_NO;
        this.CHNS_YN = CHNS_YN;
        this.TASU = TASU;
        this.REGIST_DATE = REGIST_DATE;
        this.REGIST_TIME = REGIST_TIME;
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

    public String getCHNS_YN() {
        return CHNS_YN;
    }

    public void setCHNS_YN(String CHNS_YN) {
        this.CHNS_YN = CHNS_YN;
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
}
