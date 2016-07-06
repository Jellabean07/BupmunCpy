package com.wonbuddism.bupmun.Writing.TypingProcess;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.wonbuddism.bupmun.Database.BUPMUN_TYPING_INDEX;
import com.wonbuddism.bupmun.Database.DbAdapter;
import com.wonbuddism.bupmun.Database.TYPING_HIST;

import java.util.ArrayList;


public class TypingDbManager {
    private DbAdapter dbAdapter;
    private BUPMUN_TYPING_INDEX bupmunItem;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String bupmunindex;


    public TypingDbManager(Activity activity) {
        dbAdapter = new DbAdapter(activity);
        pref = activity.getSharedPreferences("BUPMUN_TYPING_INDEX", activity.MODE_PRIVATE);
        editor = pref.edit();
    }

    public TypingDbManager(Context context) {
        dbAdapter = new DbAdapter(context);
        pref = context.getSharedPreferences("BUPMUN_TYPING_INDEX", context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setTYPING_HIST_SyncDown(ArrayList<TYPING_HIST> typing_hists){
        dbAdapter.open();
        dbAdapter.deleteAll_Typing_hist();
        dbAdapter.bulk_insert_typing_hist(typing_hists);
        dbAdapter.close();
    }

    public BUPMUN_TYPING_INDEX getMoveContent(int NO){
        dbAdapter.open();
        int BUPMUN_MAX_SIZE =  dbAdapter.getBupmunCount();
        int BUPMUN_MIN_SIZE = 1;
        int BUPMUN_CURRENT_SIZE = NO;
        if(BUPMUN_CURRENT_SIZE < BUPMUN_MIN_SIZE){
            BUPMUN_CURRENT_SIZE = BUPMUN_MAX_SIZE;
        }else if(BUPMUN_CURRENT_SIZE>BUPMUN_MAX_SIZE){
            BUPMUN_CURRENT_SIZE = BUPMUN_MIN_SIZE;
        }
        bupmunItem = dbAdapter.getBupmunItem(BUPMUN_CURRENT_SIZE);
        dbAdapter.close();

        return bupmunItem;
    }


    public BUPMUN_TYPING_INDEX getReceiveBupmun(){
        bupmunindex = pref.getString("bupmunindex", "");
        dbAdapter.open();
        if(bupmunindex.equals("")){
            bupmunItem = dbAdapter.getBupmunItem("jungjun000100");
        }else {
            bupmunItem = dbAdapter.getBupmunItem(bupmunindex);
        }
        dbAdapter.close();

        editor.putString("bupmunindex", bupmunItem.getBUPMUNINDEX());
        editor.commit();

        return bupmunItem;
    }

    public void Export_TYPING_HIST(TYPING_HIST item){
        dbAdapter.open();
        dbAdapter.create_typing_hist(item);
        dbAdapter.close();
    }

    public void Export_TYPING_HIST_LOCAL(TYPING_HIST item){
        dbAdapter.open();
        dbAdapter.create_typing_hist_local(item);
        dbAdapter.close();
    }

    public ArrayList<TYPING_HIST> getTypingHistLocal(){
        dbAdapter.open();
        ArrayList<TYPING_HIST> typing_hists = dbAdapter.getAllLocal();
        dbAdapter.deleteAll_Typing_hist_local(); // 내용삭제
        dbAdapter.close();
        return typing_hists;
    }
/*
    public void Export_TYPING_HIST_LOCAL(TYPING_HIST_LOCAL item){
        dbAdapter.open();
        dbAdapter.create_typing_hist_local(item);
        dbAdapter.close();
    }

    public void Delete_TYPING_HIST_LOCAL(String bupmunindex, int paragraph_no){
        TempLogDbAdapter.open();
        TempLogDbAdapter.delete(bupmunindex,paragraph_no+"");
        TempLogDbAdapter.close();
    }*/


}
