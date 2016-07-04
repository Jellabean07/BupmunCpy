package com.wonbuddism.bupmun.Progress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wonbuddism.bupmun.Database.Typing.BUPMUN_TYPING_INDEX;
import com.wonbuddism.bupmun.Database.Typing.DbAdapter;
import com.wonbuddism.bupmun.R;

import java.util.ArrayList;

public class ProgressTitleThridActivity extends AppCompatActivity {


    private ListView title3_view;
    private DbAdapter dbAdapter;
    private ArrayList<String> titles3;
    private ProgressTitleLowRankAdapter adapter;
    private TextView maintitle;
    private String title1;
    private String title2;
    private String title3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_title_thrid);

        title1 = getIntent().getStringExtra("title1");
        title2 = getIntent().getStringExtra("title2");

        dataProc();
        initView();
    }

    private void dataProc(){
        this.dbAdapter = new DbAdapter(ProgressTitleThridActivity.this);
        dbAdapter.open();
        dbAdapter.beginTransaction();
        try{
            titles3 = dbAdapter.getAllTitle3(title1, title2);
            dbAdapter.setTransaction();
        }catch (Exception e){
        }finally {
            dbAdapter.endTransaction();
        }
        dbAdapter.close();
    }

    private void initView() {
        maintitle = (TextView)findViewById(R.id.title_third_maintitle_textview);
        maintitle.setText(title2);
        title3_view = (ListView)findViewById(R.id.title_third_content_listview);
        adapter = new ProgressTitleLowRankAdapter(ProgressTitleThridActivity.this, titles3);
        title3_view.setAdapter(adapter);
        title3_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                title3 = (String) adapter.getItem(position);

                Intent intent;
                dbAdapter.open();
                ArrayList<String> list = dbAdapter.getAllTitle4(title1, title2, title3);
                if (list.size() == 1) {
                    intent = new Intent(ProgressTitleThridActivity.this, ProgressContentActivity.class);
                    BUPMUN_TYPING_INDEX bupmunItem = dbAdapter.getContent(title1, title2, title3);
                    intent.putExtra("title", title3);
                    intent.putExtra("bupmunItem", bupmunItem);
                } else {
                    intent = new Intent(ProgressTitleThridActivity.this, ProgressTitleFourthActivity.class);
                    intent.putExtra("title1", title1);
                    intent.putExtra("title2", title2);
                    intent.putExtra("title3", title3);
                }
                dbAdapter.close();

                startActivityForResult(intent,1000);
            }
        });
        final Toolbar toolbar = (Toolbar) findViewById(R.id.title_third_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000){
            setResult(1000);
            finish();
        }
    }
}
