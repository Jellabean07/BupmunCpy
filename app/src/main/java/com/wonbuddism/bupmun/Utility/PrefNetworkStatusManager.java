package com.wonbuddism.bupmun.Utility;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class PrefNetworkStatusManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private Activity activity;

    public PrefNetworkStatusManager(Context context) {
        this.context = context;
        pref=this.context.getSharedPreferences("network",this.context.MODE_PRIVATE);
        editor = pref.edit();

    }

    public void networkStatusChange(String statusValue){
        boolean status;
        if(statusValue.equals("Not connected to Internet")){
            status = false;
        }else {
            status = true;
        }
        editor.putBoolean("status",status);
        editor.commit();
    }

    public boolean getNetworkStatus(){
        boolean status = pref.getBoolean("status",false);
        Log.e("통신상태", status+"");
        return status;
    }
}
