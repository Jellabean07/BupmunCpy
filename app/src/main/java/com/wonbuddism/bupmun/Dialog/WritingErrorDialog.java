package com.wonbuddism.bupmun.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnWritingReport;

import org.angmarch.views.NiceSpinner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 2016-01-21.
 */
public class WritingErrorDialog extends Dialog implements View.OnClickListener{

    private Context context;
    private Activity activity;
    private Button btn_left;
    private Button btn_right;
    private NiceSpinner category;
    private EditText content;
    private TextView date;
    private String bupmunindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_writing_error);

        setLayout();

    }



    private void setLayout() {
        // 다이얼로그 외부 화면 흐리게 표현
        content = (EditText)findViewById(R.id.dialog_writing_error_content_edittext);
        date = (TextView)findViewById(R.id.dialog_writing_error_date_textview);
        date.setText( new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
        btn_left = (Button)findViewById(R.id.dialog_writing_error_go_button);
        btn_right = (Button)findViewById(R.id.dialog_writing_error_cancel_button);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);

        category = (NiceSpinner)findViewById(R.id.dialog_writing_error_category_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("철자오류", "프로그램 오류", "기타"));
        category.attachDataSource(dataset);
    }

    private String getCategoryData(int index){
        switch (index){
            case 0:
                return "00";
            case 1:
                return "01";
            case 2:
                return "02";
            default:
                return "02";
        }
    }

    public WritingErrorDialog(Context context, Activity activity,String bupmunindex) {
        super(context,android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.activity = activity;
        this.bupmunindex = bupmunindex;
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case R.id.dialog_writing_error_go_button:
                new HTTPconnWritingReport(activity,bupmunindex,getCategoryData(category.getSelectedIndex()),content.getText().toString()).execute();
                dismiss();
                break;
            case R.id.dialog_writing_error_cancel_button:
                dismiss();
                break;
        }
    }
}
