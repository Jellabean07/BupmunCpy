package com.wonbuddism.bupmun.Utility;

public class PrefUserInfo {
    private String USERID;	//아이디
    private String TYPING_ID;	 //사경아이디
    private String BDNM;	//법명
    private String NAME;	//속명
    private String USER_LEVEL; //	사용자 등급

/*
    private String ORG_NAME; //	교당명
    private String HIGH_NAME; //	상위기관명
    private String FINISH_TYPING_CNT; //	완료수
     private String NOTE_OPEN; //	쪽지사용여부
    private String VIRTUAL_ID; //	가상식별번호
    private String CHNS_YN; //	한자사용여부 x
    private String SOUND_YN; //	효과음사용여부 x
    private String FONT_SIZE; //	글자크기 x
    private String CHK_TRANS_KKYE; //	자동고침(께) x
    private String CHK_TRANS_KKYA; //	자동고침(깨) x
    private String CHK_TRANS_TTYA; //	자동고침(때) x
    private String CHK_TRANS_HALZZI; //	자동고침(할지) x
    private String L_BG_MUSIC; //	듣는법문배경음악 x
    private String L_PLAYER_SKIN; //	듣는법문스킨 x
    private String L_FONT_SIZE; //	듣는법문글자크기 x
    private String L_AUTO_PAGE; //	듣는법문자동넘김여부 x
    private String SIMGO_YN; //	심고알림사용여부 x*/


    public PrefUserInfo() {
    }

    public PrefUserInfo(String USERID, String TYPING_ID, String BDNM, String NAME, String USER_LEVEL) {
        this.USERID = USERID;
        this.TYPING_ID = TYPING_ID;
        this.BDNM = BDNM;
        this.NAME = NAME;
        this.USER_LEVEL = USER_LEVEL;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getTYPING_ID() {
        return TYPING_ID;
    }

    public void setTYPING_ID(String TYPING_ID) {
        this.TYPING_ID = TYPING_ID;
    }

    public String getBDNM() {
        return BDNM;
    }

    public void setBDNM(String BDNM) {
        this.BDNM = BDNM;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getUSER_LEVEL() {
        return USER_LEVEL;
    }

    public void setUSER_LEVEL(String USER_LEVEL) {
        this.USER_LEVEL = USER_LEVEL;
    }

    @Override
    public String toString() {
        return "PrefUserInfo{" +
                "USERID='" + USERID + '\'' +
                ", TYPING_ID='" + TYPING_ID + '\'' +
                ", BDNM='" + BDNM + '\'' +
                ", NAME='" + NAME + '\'' +
                ", USER_LEVEL='" + USER_LEVEL + '\'' +
                '}';
    }
}
