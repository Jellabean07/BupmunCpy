package com.wonbuddism.bupmun.Utility;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;

public class LoadingProgressManager {
    private Activity activity;
    private Dialog dialog;
    private Handler mHandler;
    public LoadingProgressManager() {

    }

    public LoadingProgressManager(Activity activity) {
        this.activity = activity;
        this.mHandler = new Handler();
        this.dialog = new ProgressWaitDaialog(activity);
    }

    public void StartLoading(Runnable handler){
        dialog.show();
        mHandler.postDelayed(handler, 2000); // 2초 후에 hd Handler 실행
    }


}
