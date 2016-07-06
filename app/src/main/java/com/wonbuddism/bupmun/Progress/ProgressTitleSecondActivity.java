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
import java.util.Map;

public class ProgressTitleSecondActivity extends AppCompatActivity {

    private ListView title2_view;
    private DbAdapter dbAdapter;
    private ArrayList<String> titles2;
    private ProgressTitleLowRankAdapter adapter;
    private TextView maintitle;
    private String title1;
    private String title2;
    private ArrayList<String> confirm;
    private Map<String,ArrayList<String>> titleState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_title_second);

        title1 = getIntent().getStringExtra("title1");

        dataProc();
        setLayout();

    }

    private void dataProc(){
        this.dbAdapter = new DbAdapter(ProgressTitleSecondActivity.this);
        dbAdapter.open();
        dbAdapter.beginTransaction();
        try{
            titles2= dbAdapter.getAllTitle2(title1);
            dbAdapter.setTransaction();
        }catch (Exception e){
        }finally {
            dbAdapter.endTransaction();
        }
        dbAdapter.close();
    }

    private void setLayout(){
        maintitle = (TextView)findViewById(R.id.title_second_maintitle_textview);
        maintitle.setText(title1);
        title2_view = (ListView)findViewById(R.id.title_second_content_listview);
        adapter = new ProgressTitleLowRankAdapter(ProgressTitleSecondActivity.this,titles2);

        title2_view.setAdapter(adapter);
        title2_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                title2 = (String) adapter.getItem(position);

                dbAdapter.open();
                ArrayList<String> list = dbAdapter.getAllTitle3(title1, title2);

                if (list.size() == 1) {
                    intent = new Intent(ProgressTitleSecondActivity.this, ProgressContentActivity.class);
                    BUPMUN_TYPING_INDEX bupmunItem = dbAdapter.getContent(title1, title2);
                    intent.putExtra("title1", title1);
                    intent.putExtra("title", title2);
                    intent.putExtra("bupmunItem", bupmunItem);
                } else {
                    intent = new Intent(ProgressTitleSecondActivity.this, ProgressTitleThridActivity.class);
                    intent.putExtra("title1", title1);
                    intent.putExtra("title2", title2);
                }
                dbAdapter.close();
                startActivityForResult(intent,1000);
            }
        });

        final Toolbar toolbar = (Toolbar) findViewById(R.id.title_second_toolbar);
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
