package com.wonbuddism.bupmun.DataVo;



public class RankVillageInfo {
    private String no; // 순위
    private String vil_manager; //동네운영자이름
    private String vil_name; //동네이름
    private String vil_user_count; //동네회원수

    public RankVillageInfo() {
    }

    public RankVillageInfo(String no, String vil_manager, String vil_name, String vil_user_count) {
        this.no = no;
        this.vil_manager = vil_manager;
        this.vil_name = vil_name;
        this.vil_user_count = vil_user_count;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getVil_manager() {
        return vil_manager;
    }

    public void setVil_manager(String vil_manager) {
        this.vil_manager = vil_manager;
    }

    public String getVil_name() {
        return vil_name;
    }

    public void setVil_name(String vil_name) {
        this.vil_name = vil_name;
    }

    public String getVil_user_count() {
        return vil_user_count;
    }

    public void setVil_user_count(String vil_user_count) {
        this.vil_user_count = vil_user_count;
    }
}
