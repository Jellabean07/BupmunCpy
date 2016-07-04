package com.wonbuddism.bupmun.Village.HTTPconnection;


public class VillageMember {
    private String userid; //아이디
    private String name; //속명
    private String vil_join_date; //동네가입일

    public VillageMember() {
    }

    public VillageMember(String userid, String name, String vil_join_date) {
        this.userid = userid;
        this.name = name;
        this.vil_join_date = vil_join_date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVil_join_date() {
        return vil_join_date;
    }

    public void setVil_join_date(String vil_join_date) {
        this.vil_join_date = vil_join_date;
    }
}
