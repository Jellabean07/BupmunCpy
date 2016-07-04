package com.wonbuddism.bupmun.Writing.TypingProcess;

public class TypingTempLog {

    private int TYPING_CNT; //	사경횟수	SMALLINT
    private String TYPING_ID; //	사경아이디	CHARACTER
    private String BUPMUNINDEX; //	법문인덱스키	VARCHAR
    private int PARAGRAPH_NO; //	문단번호	SMALLINT
    private String CHNS_YN; //	한자포함여부	CHARACTER
    private String REGIST_TIME; //	사경시간	CHARACTER
    private String RMRK;

    public TypingTempLog() {
    }

    public TypingTempLog(int TYPING_CNT, String TYPING_ID, String BUPMUNINDEX, int PARAGRAPH_NO, String CHNS_YN, String REGIST_TIME, String RMRK) {
        this.TYPING_CNT = TYPING_CNT;
        this.TYPING_ID = TYPING_ID;
        this.BUPMUNINDEX = BUPMUNINDEX;
        this.PARAGRAPH_NO = PARAGRAPH_NO;
        this.CHNS_YN = CHNS_YN;
        this.REGIST_TIME = REGIST_TIME;
        this.RMRK = RMRK;
    }

    public String getTYPING_ID() {
        return TYPING_ID;
    }

    public void setTYPING_ID(String TYPING_ID) {
        this.TYPING_ID = TYPING_ID;
    }

    public int getTYPING_CNT() {
        return TYPING_CNT;
    }

    public void setTYPING_CNT(int TYPING_CNT) {
        this.TYPING_CNT = TYPING_CNT;
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

    public String getREGIST_TIME() {
        return REGIST_TIME;
    }

    public void setREGIST_TIME(String REGIST_TIME) {
        this.REGIST_TIME = REGIST_TIME;
    }

    public String getRMRK() {
        return RMRK;
    }

    public void setRMRK(String RMRK) {
        this.RMRK = RMRK;
    }
}
