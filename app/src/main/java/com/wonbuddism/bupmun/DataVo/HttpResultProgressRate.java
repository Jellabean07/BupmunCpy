package com.wonbuddism.bupmun.DataVo;

/**
 * Created by csc-pc on 2016. 7. 27..
 */
public class HttpResultProgressRate {
    private int exp;
    private int totalexp;

    public HttpResultProgressRate() {
    }

    public HttpResultProgressRate(int exp, int totalexp) {
        this.exp = exp;
        this.totalexp = totalexp;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getTotalexp() {
        return totalexp;
    }

    public void setTotalexp(int totalexp) {
        this.totalexp = totalexp;
    }
}
