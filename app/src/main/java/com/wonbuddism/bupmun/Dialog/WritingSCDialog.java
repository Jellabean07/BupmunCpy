package com.wonbuddism.bupmun.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonbuddism.bupmun.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by csc-pc on 2016. 7. 26..
 */
public class WritingSCDialog extends Dialog implements View.OnClickListener {

    @Bind(R.id.dialog_sc_dot_linearlayout) LinearLayout btn_dot;
    @Bind(R.id.dialog_sc_1_linearlayout) LinearLayout btn_1;
    @Bind(R.id.dialog_sc_2_linearlayout) LinearLayout btn_2;
    @Bind(R.id.dialog_sc_3_linearlayout) LinearLayout btn_3;
    @Bind(R.id.dialog_sc_4_linearlayout) LinearLayout btn_4;
    @Bind(R.id.dialog_sc_5_linearlayout) LinearLayout btn_5;
    @Bind(R.id.dialog_sc_6_linearlayout) LinearLayout btn_6;
    @Bind(R.id.dialog_sc_7_linearlayout) LinearLayout btn_7;
    @Bind(R.id.dialog_sc_8_linearlayout) LinearLayout btn_8;
    @Bind(R.id.dialog_sc_9_linearlayout) LinearLayout btn_9;
    @Bind(R.id.dialog_sc_10_linearlayout) LinearLayout btn_10;
    @Bind(R.id.dialog_sc_11_linearlayout) LinearLayout btn_11;
    @Bind(R.id.dialog_sc_12_linearlayout) LinearLayout btn_12;
    @Bind(R.id.dialog_writing_sc_close_textview) TextView btn_close;


    private Activity activity;
    private OnDismissListener listener;
    private String sch = "";

    public WritingSCDialog(Activity activity) {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        this.activity = activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.dialog_writing_sc);

        setLayout();
    }

    private void setLayout() {
        ButterKnife.bind(this);


        btn_dot.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_10.setOnClickListener(this);
        btn_11.setOnClickListener(this);
        btn_12.setOnClickListener(this);
        btn_close.setOnClickListener(this);
    }

    public String getString(){
        return sch;
    }

    public void setOnDissmissListener(OnDismissListener listener){
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dialog_sc_dot_linearlayout:
                sch = "·";
                break;
            case R.id.dialog_sc_1_linearlayout:
                sch = "①";
                break;
            case R.id.dialog_sc_2_linearlayout:
                sch = "②";
                break;
            case R.id.dialog_sc_3_linearlayout:
                sch = "③";
                break;
            case R.id.dialog_sc_4_linearlayout:
                sch = "④";
                break;
            case R.id.dialog_sc_5_linearlayout:
                sch = "⑤";
                break;
            case R.id.dialog_sc_6_linearlayout:
                sch = "⑥";
                break;
            case R.id.dialog_sc_7_linearlayout:
                sch = "⑦";
                break;
            case R.id.dialog_sc_8_linearlayout:
                sch = "⑧";
                break;
            case R.id.dialog_sc_9_linearlayout:
                sch = "⑨";
                break;
            case R.id.dialog_sc_10_linearlayout:
                sch = "⑩";
                break;
            case R.id.dialog_sc_11_linearlayout:
                sch = "⑪";
                break;
            case R.id.dialog_sc_12_linearlayout:
                sch = "⑫";
                break;
            case R.id.dialog_writing_sc_close_textview:
                break;

        }
        if( listener == null ) {} else {
            listener.onDismiss( WritingSCDialog.this ) ;
        }
        dismiss() ;
    }
}
