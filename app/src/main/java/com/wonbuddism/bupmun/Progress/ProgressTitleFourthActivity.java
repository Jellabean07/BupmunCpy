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

import com.wonbuddism.bupmun.Database.BUPMUN_TYPING_INDEX;
import com.wonbuddism.bupmun.Database.DbAdapter;
import com.wonbuddism.bupmun.R;

import java.util.ArrayList;

public class ProgressTitleFourthActivity extends AppCompatActivity {

    private ListView title4_view;
    private DbAdapter dbAdapter;
    private ArrayList<String> titles4;
    private ProgressTitleLowRankAdapter adapter;
    private TextView maintitle;
    private String title1;
    private String title2;
    private String title3;
    private String title4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_title_fourth);

        title1 = getIntent().getStringExtra("title1");
        title2 = getIntent().getStringExtra("title2");
        title3 = getIntent().getStringExtra("title3");

        dataProc();
        initView();
    }

    private void dataProc(){
        this.dbAdapter = new DbAdapter(ProgressTitleFourthActivity.this);
        dbAdapter.open();
        dbAdapter.beginTransaction();
        try{
            titles4= dbAdapter.getAllTitle4(title1, title2, title3);
            dbAdapter.setTransaction();
        }catch (Exception e){
        }finally {
            dbAdapter.endTransaction();
        }
        dbAdapter.close();
    }


    private void initView() {
        maintitle = (TextView)findViewById(R.id.title_fourth_maintitle_textview);
        maintitle.setText(title3);
        title4_view = (ListView)findViewById(R.id.title_fourth_content_listview);
        adapter = new ProgressTitleLowRankAdapter(ProgressTitleFourthActivity.this,titles4);
        title4_view.setAdapter(adapter);
        title4_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title4 = (String) adapter.getItem(position);
                dbAdapter.open();
                BUPMUN_TYPING_INDEX bupmunItem = dbAdapter.getContent(title1, title2, title3, title4);
                dbAdapter.close();
                Intent intent = new Intent(ProgressTitleFourthActivity.this, ProgressContentActivity.class);

                intent.putExtra("bupmunItem", bupmunItem);
                intent.putExtra("title1", title1);
                intent.putExtra("title2", title2);
                intent.putExtra("title3", title3);
                intent.putExtra("title", title4);
                startActivityForResult(intent,1000);
            }
        });
        final Toolbar toolbar = (Toolbar) findViewById(R.id.title_fourth_toolbar);
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
