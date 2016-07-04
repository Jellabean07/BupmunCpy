package com.wonbuddism.bupmun.Progress;

/**
 * Created by user on 2015-12-29.
 */
public class Card {
    private String title;
    private String content;

    public Card(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
