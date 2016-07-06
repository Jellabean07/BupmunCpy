package com.wonbuddism.bupmun.Writing;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wonbuddism.bupmun.DataVo.FeelingMemo;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnFeelingRegist;
import com.wonbuddism.bupmun.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WritingFeelingRegistDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private Activity activity;
    private String nowTime;
    private String shorttitle;
    private String bupmunindex;

    private FeelingMemo item;

    private Button regist;
    private Button cancel;

    private TextView title;
    private TextView date;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_writing_feeling_regist);
        setLayout();

    }

    private void setLayout() {
        regist = (Button)findViewById(R.id.dialog_writing_feeling_regist_regist_btn);
        cancel = (Button)findViewById(R.id.dialog_writing_feeling_regist_cancel_btn);
        regist.setOnClickListener(this);
        cancel.setOnClickListener(this);

        title = (TextView)findViewById(R.id.dialog_writing_feeling_regist_title_textview);
        date = (TextView)findViewById(R.id.dialog_writing_feeling_regist_date_textview);
        content = (EditText)findViewById(R.id.dialog_writing_feeling_regist_content_edittext);


        Log.e("쓰기모드", "쓰기모드");
        title.setText(shorttitle);
        nowTime = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        date.setText(nowTime.substring(0, 10));

    }


    public WritingFeelingRegistDialog(Context context,Activity activity, String shorttitle, String bupmunindex) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.activity = activity;
        this.shorttitle = shorttitle;
        this.bupmunindex = bupmunindex;
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id) {
            case R.id.dialog_writing_feeling_regist_regist_btn:
                new HTTPconnFeelingRegist(activity,bupmunindex,content.getText().toString()).execute();
                dismiss();
                break;
            case R.id.dialog_writing_feeling_regist_cancel_btn:
                //Toast.makeText(context,"취소",Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }
}
