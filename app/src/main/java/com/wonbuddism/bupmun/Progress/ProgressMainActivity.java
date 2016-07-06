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
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wonbuddism.bupmun.Database.DbAdapter;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.CustomLinearLayoutManager;
import com.wonbuddism.bupmun.Utility.NavigationDrawerMenu;

import java.util.ArrayList;
import java.util.List;

public class ProgressMainActivity extends AppCompatActivity {

    private ViewPager paper;
    private List<Fragment> listData;
    private ImageView pageDot;
    private DrawerLayout mDrawerLayout;

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

    public void setLayout(){
        final CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv=(RecyclerView)findViewById(R.id.progress_recycler_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        rv.setNestedScrollingEnabled(false);
        setContentData("정전");

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
                setContentData("정전");
                break;
            case 1:
                setContentData("대종경");
                break;
            case 2:
                setContentData("불조요경");
                break;
            case 3:
                setContentData("예전");
                break;
            case 4:
                setContentData("정산종사법어");
                break;
            case 5:
                setContentData("대산종사법어");
                break;
            case 6:
                setContentData("원불교교사");
                break;
            default:
                setContentData("정전");
                break;
        }

    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.progress_backdrop);
        Glide.with(this).load(R.drawable.bg).centerCrop().into(imageView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.progress_actionbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.progress_info:
                Toast.makeText(this,"원을 클릭 하세요",Toast.LENGTH_SHORT).show();
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

    private void setContentData(String key){
        dbAdapter=new DbAdapter(ProgressMainActivity.this);
        dbAdapter.open();

        contents = dbAdapter.getAllTitle2(key);
        Log.e("content!!!",contents.toString());
        dbAdapter.close();
        initializeAdapter(key);
    }

    private void initializeAdapter(String title){
        ProgressContentRecyclerViewAdapter recyclerViewAdapter = new ProgressContentRecyclerViewAdapter(ProgressMainActivity.this, title, contents);
        rv.setAdapter(recyclerViewAdapter);

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
