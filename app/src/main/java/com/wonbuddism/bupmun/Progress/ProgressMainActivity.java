package com.wonbuddism.bupmun.Progress;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wonbuddism.bupmun.DataVo.HttpParamProgress;
import com.wonbuddism.bupmun.DataVo.HttpResultProgress;
import com.wonbuddism.bupmun.Database.DbAdapter;
import com.wonbuddism.bupmun.HttpConnection.HttpConnProgressTitle;
import com.wonbuddism.bupmun.Listener.ProgressReponseListener;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Common.CustomLinearLayoutManager;
import com.wonbuddism.bupmun.Common.NavigationDrawerMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ProgressMainActivity extends AppCompatActivity implements ProgressReponseListener {

    private ViewPager paper;
    private List<Fragment> listData;
    private ImageView pageDot;
    private DrawerLayout mDrawerLayout;

    private ProgressRecyclerViewAdapter adapter;
    private ArrayList<HttpResultProgress> httpResults;
    private HttpParamProgress httpParam;

    private ArrayList<String> contents;
    private RecyclerView rv;
    private DbAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_main);

        listData = new ArrayList<>();
        listData.add(new ProgressItemFragment().newInstance("정전"));
        listData.add(new ProgressItemFragment().newInstance("대종경"));
        listData.add(new ProgressItemFragment().newInstance("불조요경"));
        listData.add(new ProgressItemFragment().newInstance("예전"));
        listData.add(new ProgressItemFragment().newInstance("정산종사법어"));
        listData.add(new ProgressItemFragment().newInstance("대산종사법어"));
        listData.add(new ProgressItemFragment().newInstance("원불교교사"));

        setLayout();

    }

    private void setupAdapter(){
        httpResults = new ArrayList<>();
        adapter = new ProgressRecyclerViewAdapter(this,httpResults);
        adapter.setHasStableIds(true);

    }

    private void setupRecyclerView(final RecyclerView recyclerView) {
        final CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        setupAdapter();
        recyclerView.setAdapter(adapter);
        setContentData("정전", "0");
        recyclerView.setNestedScrollingEnabled(false);

    }

    private void setContentData(String key, String code){
        String index = code;
        String title_no = "0";
        String depth = "T";

        httpParam = new HttpParamProgress(index,title_no,depth);

        HttpConnProgressTitle httpConnProgressTitle =  new HttpConnProgressTitle(this,httpParam);
        httpConnProgressTitle.setOnResponseListener(this);
        httpConnProgressTitle.execute();

    }

    public void setLayout(){
        ButterKnife.bind(this);


        rv=(RecyclerView)findViewById(R.id.progress_recycler_view);
        setupRecyclerView(rv);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.progress_drawer_layout);
        pageDot = (ImageView)findViewById(R.id.progress_viewpage_page1);
        pageDot.setImageResource(R.drawable.indicators_on);

        ProgressViewPagerAdapder adapder = new ProgressViewPagerAdapder(getSupportFragmentManager(), listData);
        paper = (ViewPager) findViewById(R.id.progress_viewpager);

        //final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        paper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        pageDot.setImageResource(R.drawable.indicators_off);
                        pageDot = (ImageView) findViewById(R.id.progress_viewpage_page1);
                        pageDot.setImageResource(R.drawable.indicators_on);
                        break;
                    case 1:
                        pageDot.setImageResource(R.drawable.indicators_off);
                        pageDot = (ImageView) findViewById(R.id.progress_viewpage_page2);
                        pageDot.setImageResource(R.drawable.indicators_on);
                        break;
                    case 2:
                        pageDot.setImageResource(R.drawable.indicators_off);
                        pageDot = (ImageView) findViewById(R.id.progress_viewpage_page3);
                        pageDot.setImageResource(R.drawable.indicators_on);
                        break;
                    case 3:
                        pageDot.setImageResource(R.drawable.indicators_off);
                        pageDot = (ImageView) findViewById(R.id.progress_viewpage_page4);
                        pageDot.setImageResource(R.drawable.indicators_on);
                        break;
                    case 4:
                        pageDot.setImageResource(R.drawable.indicators_off);
                        pageDot = (ImageView) findViewById(R.id.progress_viewpage_page5);
                        pageDot.setImageResource(R.drawable.indicators_on);
                        break;
                    case 5:
                        pageDot.setImageResource(R.drawable.indicators_off);
                        pageDot = (ImageView) findViewById(R.id.progress_viewpage_page6);
                        pageDot.setImageResource(R.drawable.indicators_on);
                        break;
                    case 6:
                        pageDot.setImageResource(R.drawable.indicators_off);
                        pageDot = (ImageView) findViewById(R.id.progress_viewpage_page7);
                        pageDot.setImageResource(R.drawable.indicators_on);
                        break;
                }
                infoChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        paper.setAdapter(adapder);


        //네비게이션
        NavigationView navigationView = (NavigationView) findViewById(R.id.progress_nav_view);

        if (navigationView != null) {
            new NavigationDrawerMenu(this,mDrawerLayout).setupDrawerContent(navigationView);
        }

        //액션바
        final Toolbar toolbar = (Toolbar) findViewById(R.id.progress_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.progress_collapsing_toolbar);
        //collapsingToolbar.setTitle(" ");
        collapsingToolbar.setTitleEnabled(false);


        loadBackdrop();
    }
    public void infoChange(int position){
        switch (position){
            case 0:
                setContentData("정전","0");
                break;
            case 1:
                setContentData("대종경","1");
                break;
            case 2:
                setContentData("불조요경","2");
                break;
            case 3:
                setContentData("예전","3");
                break;
            case 4:
                setContentData("정산종사법어","4");
                break;
            case 5:
                setContentData("대산종사법어","5");
                break;
            case 6:
                setContentData("원불교교사","6");
                break;
            default:
                setContentData("정전","0");
                break;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //빽(취소)키가 눌렸을때 종료여부를 묻는 다이얼로그 띄움
        if((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerLayout.closeDrawers();
                return false;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.progress_backdrop);
        Glide.with(this).load(R.drawable.bg).centerCrop().into(imageView);
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
        adapter.setParameter(httpParam);
        adapter.clear();
        for(HttpResultProgress s : httpResults){
            adapter.add(s);
        }
        adapter.notifyDataSetChanged();
    }
}
