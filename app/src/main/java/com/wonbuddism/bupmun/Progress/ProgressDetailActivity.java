package com.wonbuddism.bupmun.Progress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.wonbuddism.bupmun.DataVo.HttpParamProgress;
import com.wonbuddism.bupmun.DataVo.HttpResultProgress;
import com.wonbuddism.bupmun.HttpConnection.HttpConnProgressTitle;
import com.wonbuddism.bupmun.Listener.ProgressNextListener;
import com.wonbuddism.bupmun.Listener.ProgressReponseListener;
import com.wonbuddism.bupmun.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProgressDetailActivity extends AppCompatActivity implements ProgressReponseListener, ProgressNextListener {

    @Bind(R.id.progress_detail_toolbar) Toolbar toolbar;
    @Bind(R.id.progress_detail_content_listview) ListView listView;
    @Bind(R.id.progress_detail_maintitle_textview) TextView maintitle;


    private ProgressListViewAdapter adapter;
    private ArrayList<HttpResultProgress> httpResults;
    private HttpParamProgress httpParam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_detail);
        setLayout();
        dataProc();
    }

    private void dataProc(){
        httpParam = (HttpParamProgress)getIntent().getSerializableExtra("httpParam");
        dataLoad();

    }

    private void dataLoad(){
        HttpConnProgressTitle httpConnProgressTitle =  new HttpConnProgressTitle(this,httpParam);
        httpConnProgressTitle.setOnResponseListener(this);
        httpConnProgressTitle.execute();
    }

    private void setLayout(){
        ButterKnife.bind(this);


        httpResults = new ArrayList<>();
        adapter = new ProgressListViewAdapter(ProgressDetailActivity.this,httpResults);
        adapter.setNextProgressListenter(this);
        listView.setAdapter(adapter);


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

    @Override
    public void HttpResponse(ArrayList<HttpResultProgress> httpResults) {
        maintitle.setText(httpParam.getTitle().get(Integer.parseInt(httpParam.getTitle_no())-1));
        adapter.setParameter(httpParam);
        adapter.clear();
        for(HttpResultProgress s : httpResults){
            adapter.add(s);
        }
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }


    @Override
    public void nextProgress(HttpParamProgress httpParam) {
        this.httpParam = httpParam;
        dataLoad();
    }
}
