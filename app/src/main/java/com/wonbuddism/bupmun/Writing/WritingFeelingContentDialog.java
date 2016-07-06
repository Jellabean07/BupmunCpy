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
import com.wonbuddism.bupmun.HttpConnection.HTTPconnFeelingDelete;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnFeelingModify;
import com.wonbuddism.bupmun.R;


public class WritingFeelingContentDialog extends Dialog implements View.OnClickListener{

    private Context context;
    private Activity activity;
    private String shortTitle;
    private String nowTime;
    private FeelingMemo item;
    private int state;

    private Button modifiy;
    private Button delete;
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

        setContentView(R.layout.dialog_writing_feeling_content);
        setLayout();
    }

    private void setLayout() {
        modifiy = (Button)findViewById(R.id.dialog_writing_feeling_content_modifiy_btn);
        delete = (Button)findViewById(R.id.dialog_writing_feeling_content_delete_btn);
        cancel = (Button)findViewById(R.id.dialog_writing_feeling_content_cancel_btn);
        modifiy.setOnClickListener(this);
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);

        title = (TextView)findViewById(R.id.dialog_writing_feeling_content_title_textview);

        date = (TextView)findViewById(R.id.dialog_writing_feeling_content_date_textview);
        content = (EditText)findViewById(R.id.dialog_writing_feeling_content_contnet_edittext);


        Log.e("수정모드", "수정모드");
        title.setText(item.getShort_title());
        date.setText(item.getRegist_date());
        content.setText(item.getMemo_contents());


    }

    public WritingFeelingContentDialog(Context context, Activity activity, FeelingMemo item) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.activity = activity;
        this.item = item;
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id) {
            case R.id.dialog_writing_feeling_content_modifiy_btn:
                new HTTPconnFeelingModify(activity,item.getBupmunindex(),
                        item.getMemo_seq(), content.getText().toString()).execute();
                dismiss();
                break;
            case R.id.dialog_writing_feeling_content_delete_btn:
                new HTTPconnFeelingDelete(activity,item.getBupmunindex(),item.getMemo_seq()).execute();
                dismiss();
                break;
            case R.id.dialog_writing_feeling_content_cancel_btn:
               // Toast.makeText(context,"취소",Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }
}
