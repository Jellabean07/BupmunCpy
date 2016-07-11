package com.wonbuddism.bupmun.Writing.HTTPconnection;

/**
 * Created by csc-pc on 2016. 7. 9..
 */
public class HttpParamBupmun {
    private int PARAGRAPH_NO; //	문단번호	SMALLINT
    private String bupmunindex ;
    private int TASU; //	타수	SMALLIN

    public HttpParamBupmun() {
    }

    public HttpParamBupmun(int PARAGRAPH_NO, String bupmunindex, int TASU) {
        this.PARAGRAPH_NO = PARAGRAPH_NO;
        this.bupmunindex = bupmunindex;
        this.TASU = TASU;
    }

    public int getPARAGRAPH_NO() {
        return PARAGRAPH_NO;
    }

    public void setPARAGRAPH_NO(int PARAGRAPH_NO) {
        this.PARAGRAPH_NO = PARAGRAPH_NO;
    }

    public String getBupmunindex() {
        return bupmunindex;
    }

    public void setBupmunindex(String bupmunindex) {
        this.bupmunindex = bupmunindex;
    }

    public int getTASU() {
        return TASU;
    }

    public void setTASU(int TASU) {
        this.TASU = TASU;
    }
}
