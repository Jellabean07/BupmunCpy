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
import com.wonbuddism.bupmun.Database.Typing.DbAdapter;
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
    private TextView info_content1;
    private TextView info_content2;
    private TextView info_content3;
    private ProgressTitleTopRankAdapter adapter;

    private List<Card> cards;
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
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItemPos = layoutManager.findLastVisibleItemPosition();
                Log.i("getChildCount", String.valueOf(visibleItemCount));
                Log.i("getItemCount", String.valueOf(totalItemCount));
                Log.i("lastVisibleItemPos", String.valueOf(lastVisibleItemPos));
                if ((visibleItemCount + lastVisibleItemPos) >= totalItemCount) {
                    Log.i("LOG", "Last Item Reached!");
                }
            }
        });

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
   /* private void setContentData(String key){
        cards = new ArrayList<>();
        if(key.equals("정전")){
            cards.add(new Card("개요",getResources().getString(R.string.info_content1_title1)));
            cards.add(new Card("결집과정",getResources().getString(R.string.info_content2_title1)));
          //  cards.add(new Card("구성내용",getResources().getString(R.string.info_content3_title1)));
          //  cards.add(new Card("의의",getResources().getString(R.string.info_content4_title1)));
        }else if(key.equals("대종경")){
            cards.add(new Card("개요",getResources().getString(R.string.info_content1_title2)));
        //    cards.add(new Card("내용과 특징",getResources().getString(R.string.info_content2_title2)));
            cards.add(new Card("대종경의 형성과정",getResources().getString(R.string.info_content3_title2)));
       //     cards.add(new Card("대종경 자료의 출처",getResources().getString(R.string.info_content4_title2)));
       //     cards.add(new Card("대종경의 의의",getResources().getString(R.string.info_content5_title2)));
        }else if(key.equals("불조요경")){
            cards.add(new Card("개요",getResources().getString(R.string.info_content1_title3)));
            cards.add(new Card("불조요경 결집과정",getResources().getString(R.string.info_content2_title3)));
        //    cards.add(new Card("원불교 교리와의 상관성",getResources().getString(R.string.info_content3_title3)));
         //   cards.add(new Card("볼조요경의 의의",getResources().getString(R.string.info_content4_title3)));

        }else if(key.equals("예전")){
            cards.add(new Card("개요",getResources().getString(R.string.info_content1_title4)));
        //    cards.add(new Card("편찬의 사상적 배경",getResources().getString(R.string.info_content2_title4)));
            cards.add(new Card("예전의 편수 과정",getResources().getString(R.string.info_content3_title4)));
        //    cards.add(new Card("예전의 사회적 성격",getResources().getString(R.string.info_content4_title4)));

        }else if(key.equals("정산종사법어")){
            cards.add(new Card("개요",getResources().getString(R.string.info_content1_title5)));
            cards.add(new Card("편찬과정",getResources().getString(R.string.info_content2_title5)));
   //         cards.add(new Card("세전의 구성",getResources().getString(R.string.info_content3_title5)));
   //         cards.add(new Card("법어의 구성",getResources().getString(R.string.info_content4_title5)));
        }else if(key.equals("대산종사법어")){
            cards.add(new Card("개요",getResources().getString(R.string.info_content1_title6)));
            cards.add(new Card("내용",getResources().getString(R.string.info_content2_title6)));
            cards.add(new Card("추가내용",getResources().getString(R.string.info_content3_title6)));
        }else if(key.equals("원불교교사")){
            cards.add(new Card("개요",getResources().getString(R.string.info_content1_title7)));
            cards.add(new Card("편찬과정",getResources().getString(R.string.info_content2_title7)));
    //        cards.add(new Card("내용구성",getResources().getString(R.string.info_content3_title7)));
   //         cards.add(new Card("편찬의의",getResources().getString(R.string.info_content4_title7)));
        }

        initializeAdapter();
    }*/


    private void setContentData(String key){
        dbAdapter=new DbAdapter(ProgressMainActivity.this);
        dbAdapter.open();

        contents = dbAdapter.getAllTitle2(key);
        Log.e("content!!!",contents.toString());
        dbAdapter.close();
        initializeAdapter(key);
    }

    private void initializeAdapter(String title){
       /* ProgressRecyclerViewAdapter adapter = new ProgressRecyclerViewAdapter(cards);
        rv.setAdapter(adapter);*/
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
