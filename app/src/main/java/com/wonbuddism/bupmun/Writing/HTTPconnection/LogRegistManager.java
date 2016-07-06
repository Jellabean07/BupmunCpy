package com.wonbuddism.bupmun.Writing.HTTPconnection;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.wonbuddism.bupmun.Database.BUPMUN_TYPING_INDEX;
import com.wonbuddism.bupmun.Database.TYPING_HIST;
import com.wonbuddism.bupmun.Utility.PrefNetworkStatusManager;
import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;
import com.wonbuddism.bupmun.Writing.TypingProcess.TypingDbManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LogRegistManager {

    private Activity activity;
    private Context context;
    private TypingDbManager typingDbManager;
    private TYPING_HIST item;

    public LogRegistManager(Activity activity) {
        this.activity = activity;
        this.typingDbManager = new TypingDbManager(activity);
    }

    public LogRegistManager(Context context) {
        this.context = context;
        this.typingDbManager = new TypingDbManager(context);
    }

    public void LocalLogUpload(){
        ArrayList<TYPING_HIST> typing_hists = typingDbManager.getTypingHistLocal();
        new HTTPconnSyncTempUp(context,typing_hists).execute();

    }

    public void LogRegist(BUPMUN_TYPING_INDEX bupmunitem, int paragraph, ArrayList<String> content){
        item = getLog(bupmunitem, paragraph, content);
        typingDbManager.Export_TYPING_HIST(item);
        if(new PrefNetworkStatusManager(activity).getNetworkStatus()){ //통신
            new HTTPconnSyncUp(activity,item).execute();
            Log.e("통신","통신중");
        }else{ // 비통신
            typingDbManager.Export_TYPING_HIST_LOCAL(item);
            Log.e("통신", "비통신중");
       }
    }

    private TYPING_HIST getLog(BUPMUN_TYPING_INDEX bupmunitem, int paragraph, ArrayList<String> content){
        int TYPING_CNT = 1; //	사경횟수	SMALLINT
        String TYPING_ID = new PrefUserInfoManager(activity).getUserInfo().getTYPING_ID(); //	사경아이디	CHARACTER
        //String TYPING_ID = "00000000"; //	사경아이디	CHARACTER
        String BUPMUNINDEX = bupmunitem.getBUPMUNINDEX(); //	법문인덱스키	VARCHAR
        int PARAGRAPH_NO = paragraph; //	문단번호	SMALLINT
        String CHNS_YN = "M"; //	한자포함여부	CHARACTER
        int TASU = content.get(paragraph).length(); //	타수	SMALLINT
        String REGIST_DATE =  new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis())); //	사경일	CHARACTER
        String REGIST_TIME =  new SimpleDateFormat("HHmmss").format(new Date(System.currentTimeMillis())) ; //	사경시간	CHARACTER
        TYPING_HIST item = new TYPING_HIST(TYPING_CNT,TYPING_ID,BUPMUNINDEX,PARAGRAPH_NO,CHNS_YN,TASU,REGIST_DATE,REGIST_TIME);
        return item;
    }

}
