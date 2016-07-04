package com.wonbuddism.bupmun.Writing;

/**
 * Created by user on 2016-01-25.
 */
public class WritingImportItem {
    private String title;
    private String shorttitle;
    private int count;
    private int para;

    public WritingImportItem() {
    }

    public WritingImportItem(String title, String shorttitle, int count, int para) {
        this.title = title;
        this.shorttitle = shorttitle;
        this.count = count;
        this.para = para;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShorttitle() {
        return shorttitle;
    }

    public void setShorttitle(String shorttitle) {
        this.shorttitle = shorttitle;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPara() {
        return para;
    }

    public void setPara(int para) {
        this.para = para;
    }
}
