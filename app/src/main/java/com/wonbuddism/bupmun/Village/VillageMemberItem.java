package com.wonbuddism.bupmun.Village;

/**
 * Created by user on 2016-01-25.
 */
public class VillageMemberItem {
    private String name;
    private String id;
    private String date;

    public VillageMemberItem() {
    }

    public VillageMemberItem(String name, String id, String date) {
        this.name = name;
        this.id = id;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
