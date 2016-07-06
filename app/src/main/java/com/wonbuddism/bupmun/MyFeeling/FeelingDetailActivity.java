package com.wonbuddism.bupmun.MyFeeling;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.FeelingMemo;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnFeelingDelete;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnFeelingModify;
import com.wonbuddism.bupmun.R;

public class FeelingDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private NestedScrollView nsv;
    public static final String EXTRA_NAME = "feelingMemo";
    private TextView title;
    private TextView short_title;
    private TextView date;
    private TextView content;
    private EditText contentModifiy;
    private Button btnModifiy;
    private FeelingMemo feelingMemo;

    private TextView delete;
    private TextView modify;
    private LinearLayout managerLine;

    private int modifyStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_detail);
        feelingMemo = (FeelingMemo)getIntent().getSerializableExtra(EXTRA_NAME);
        Log.e("intentValue", feelingMemo.toString());
        setLayout();
        setData();
    }
    private void setData(){
        modifyStep = 0;
        title.setText(feelingMemo.getTitle());
        short_title.setText(feelingMemo.getShort_title());
        date.setText(feelingMemo.getRegist_date());
        content.setText(feelingMemo.getMemo_contents());
    }

    private void setLayout() {
        delete = (TextView)findViewById(R.id.feeling_detail_middle_manage_delete_textview);
        modify = (TextView)findViewById(R.id.feeling_detail_middle_manage_modify_textview);
        managerLine = (LinearLayout)findViewById(R.id.feeling_detail_middle_manage);

        delete.setOnClickListener(this);
        modify.setOnClickListener(this);

        title = (TextView)findViewById(R.id.feeling_detail_top_category_textview);
        short_title = (TextView)findViewById(R.id.feeling_detail_top_title_textview);
        date = (TextView)findViewById(R.id.feeling_detail_top_date_textview);
        content = (TextView)findViewById(R.id.feeling_detail_bottom_content_textview);
        contentModifiy = (EditText)findViewById(R.id.feeling_detail_bottom_content_edittext);
        btnModifiy = (Button)findViewById(R.id.feeling_detail_bottom_modifiy_btn);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.feeling_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nsv = (NestedScrollView)findViewById(R.id.feeling_detail_nestedscrollview);
        nsv.smoothScrollTo(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_feeling_modify:
                content.setVisibility(View.GONE);
                contentModifiy.setVisibility(View.VISIBLE);
                contentModifiy.setText(feelingMemo.getMemo_contents());
                contentModifiy.setFocusableInTouchMode(true);
                btnModifiy.setVisibility(View.VISIBLE);
                return true;
            case R.id.menu_feeling_delete:
                Toast.makeText(this,"삭제",Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feeling_detail, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.feeling_detail_middle_manage_delete_textview:
                new HTTPconnFeelingDelete(this,feelingMemo.getBupmunindex(),feelingMemo.getMemo_seq()).execute();
                setResult(1000);
                finish();
                break;
            case R.id.feeling_detail_middle_manage_modify_textview:
                Log.e("modyfiy","step0");
               switch (modifyStep){
                   case 0:
                       Log.e("modyfiy","step1");
                       content.setVisibility(View.GONE);
                       contentModifiy.setVisibility(View.VISIBLE);
                       contentModifiy.setText(feelingMemo.getMemo_contents());
                       contentModifiy.setFocusableInTouchMode(true);
                       modify.setText("완료");
                       modifyStep=1;
                       Toast.makeText(this,"감각감상 수정후 완료를 눌러주세요",Toast.LENGTH_SHORT).show();

                       break;
                   case 1:
                       Log.e("modyfiy","step2");
                       new HTTPconnFeelingModify(this,feelingMemo.getBupmunindex(),
                               feelingMemo.getMemo_seq(), contentModifiy.getText().toString()).execute();
                       setResult(1000);
                       modifyStep = 0;
                       finish();
                       break;
               }
                break;
        }
    }
}
