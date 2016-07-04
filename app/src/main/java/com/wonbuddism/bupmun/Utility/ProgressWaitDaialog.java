package com.wonbuddism.bupmun.Utility;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wonbuddism.bupmun.R;

public class ProgressWaitDaialog extends Dialog{

    private ImageView waitAnimation;
    private AnimationDrawable aniFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.5f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.dialog_wait);
        setProgressWait();
    }

    public void setProgressWait(){
        waitAnimation = (ImageView) findViewById(R.id.dialog_progress_wait_imageview);
        waitAnimation.setBackgroundResource(R.drawable.animainton_progress_wait);
        aniFrame = (AnimationDrawable) waitAnimation.getBackground();
        aniFrame.start();
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }



    public ProgressWaitDaialog(Activity activity) {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
    }



}
