package com.wonbuddism.bupmun.Village;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.NavigationDrawerMenu;
import com.wonbuddism.bupmun.Utility.RecyclerViewScrollListener;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnVillComment;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnVillCommentDelete;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnVillCommentRegist;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnVillStats;
import com.wonbuddism.bupmun.DataVo.VillageComments;
import com.wonbuddism.bupmun.DataVo.VillageMainInfo;

import java.util.ArrayList;

public class VillageMainActivity extends AppCompatActivity implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener, VillageDeleteListener{

    private DrawerLayout mDrawerLayout;
    private TextView title;
    private TextView master;
    private TextView member;
    private TextView create_date;
    private TextView join_date;
    private TextView rank;
    private TextView toobarTitle;

    private TextView intro;
    private EditText comment_content;
    private Button comment_btn;
    private RecyclerView comment_list;
    private VillageRecycleViewAdapter comment_adapter;
    private LinearLayoutManager mLayoutManager;
    private ImageView mImageparallax;


    private InputMethodManager imm;
    private FrameLayout mFrameParallax;
    private LinearLayout main_title_view;
    private LinearLayout member_next;
    private LinearLayout stats_next;
    private AppBarLayout mAppBarLayout;

    private NestedScrollView nv;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;
    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private VillageMainInfo villageMainInfo;

    private LayoutInflater mInflater;
    private ArrayList<VillageComments> commentses;
    private int page_no;
    private boolean mLockListView;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_main);
        setData();
        setLayout();
    }

    private void setData() {
        Intent intent = getIntent();
        villageMainInfo = (VillageMainInfo)intent.getSerializableExtra("villageMainInfo");
        commentses = new ArrayList<>();
        this.mLockListView = false;
        mHandler = new Handler();
        page_no = 0;


    }

    private void setLayout() {
        title = (TextView)findViewById(R.id.include_village_main_title_textview); // 동네이름
        master = (TextView)findViewById(R.id.include_village_main_master_textview); // 운영자 이름
        member = (TextView)findViewById(R.id.include_village_main_member_count_textview); // 회원수
        create_date = (TextView)findViewById(R.id.include_village_main_creative_date_textview); //개설일
        join_date = (TextView)findViewById(R.id.include_village_main_join_date_textview); //가입일
        rank = (TextView)findViewById(R.id.include_village_main_rank_textview); // 동내랭크
        intro = (TextView)findViewById(R.id.include_village_main_introduce_textview); // 동네소개
        toobarTitle = (TextView)findViewById(R.id.include_village_main_toolbar_title_textview);

        title.setText(villageMainInfo.getVil_name());
        master.setText(villageMainInfo.getVil_manager());
        member.setText(villageMainInfo.getVil_user_count());
        create_date.setText(villageMainInfo.getVil_open_date());
        join_date.setText(villageMainInfo.getVil_join_date());
        rank.setText(villageMainInfo.getVil_rank());
        intro.setText(villageMainInfo.getVil_intro());
        toobarTitle.setText(villageMainInfo.getVil_name());
        //toobarTitle.setText("우리동네 만가대");

        comment_content = (EditText)findViewById(R.id.include_village_main_comment_edittext); // 한마디 작성내용
        comment_content.setOnFocusChangeListener(new View.OnFocusChangeListener() {

             @Override
             public void onFocusChange(View v, boolean hasFocus) {

                 if (hasFocus == true) {
                     nv.postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             nv.smoothScrollBy(0, 800);
                         }
                     }, 100);
                 }

             }
         });


            comment_btn = (Button)findViewById(R.id.include_village_main_comment_btn);
       // comment_list = (RecyclerView)findViewById(R.id.include_village_main_comment_recycleview); // 댓글리스트

        mImageparallax  = (ImageView) findViewById(R.id.vilage_backdrop);
        mFrameParallax  = (FrameLayout) findViewById(R.id.include_village_main_frame_view);

        main_title_view = (LinearLayout)findViewById(R.id.include_village_main_view);
        member_next = (LinearLayout)findViewById(R.id.include_village_main_member_next_view);
        stats_next = (LinearLayout)findViewById(R.id.include_village_main_stats_view);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.village_appbar);
        nv = (NestedScrollView)findViewById(R.id.include_village_main_nestedscrollview);


        comment_btn.setOnClickListener(this);
        member_next.setOnClickListener(this);
        stats_next.setOnClickListener(this);

        comment_list = (RecyclerView)findViewById(R.id.include_village_main_comment_recycleview);

        setupRecyclerView(comment_list);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.village_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.village_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.village_nav_view);
        if (navigationView != null) {
            new NavigationDrawerMenu(this,mDrawerLayout).setupDrawerContent(navigationView);
        }

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.village_collapsing_toolbar);
        collapsingToolbar.setTitleEnabled(false);


        mAppBarLayout.addOnOffsetChangedListener(this);


        startAlphaAnimation(toobarTitle, 0, View.INVISIBLE);
        initParallaxValues();

        imm = (InputMethodManager)getSystemService(VillageMainActivity.this.INPUT_METHOD_SERVICE); //키보드 컨트롤
    }

    private void setupRecyclerView(final RecyclerView recyclerView) {
        /*final CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapter();
        recyclerView.setAdapter(comment_adapter);
        loadMoreData();
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onLoadMore() {
                comment_adapter.showLoading(true);
                loadMoreData();
            }
        });

       // mLayoutManager = new LinearLayoutManager(this);


    }
    private void setupAdapter() {
        commentses = new ArrayList<>();
        comment_adapter = new VillageRecycleViewAdapter(VillageMainActivity.this,this, commentses);
        comment_adapter.setHasStableIds(true);
        //rankUserInfos = getSortList();
        comment_adapter.setItems(commentses);
        comment_adapter.setOnDeleteListener(this);
    }
    // adding 10 object creating dymically to arraylist and updating recyclerview when ever we reached last item

    private void loadMoreData() {

        comment_adapter.showLoading(true);
        comment_adapter.notifyDataSetChanged();

        // Load data after delay
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new HTTPconnVillComment(VillageMainActivity.this, comment_adapter,
                        villageMainInfo.getVil_id(), (page_no++) + "").execute();
            }
        }, 1500);

    }

    @Override
    public void DeleteItem(VillageComments comments) {
        deleteComment(comments);
    }



    private void addComment() {
        String content = comment_content.getText().toString().trim();
        comment_content.setText("");
        page_no = 0;
        new HTTPconnVillCommentRegist(this,villageMainInfo.getVil_id(),content).execute();
        commentses.clear();
        comment_adapter.setItems(commentses);
        comment_adapter.notifyDataSetChanged();

        imm.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
        scrollToEnd();
        loadMoreData();
    }
    private void deleteComment(VillageComments comments){
        new HTTPconnVillCommentDelete(this,villageMainInfo.getVil_id(),comments.getComment_no()).execute();
        commentses.clear();
        comment_adapter.setItems(commentses);
        comment_adapter.notifyDataSetChanged();

        imm.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
        scrollToEnd();
        loadMoreData();
    }


    public void scrollToEnd(){
        nv.post(new Runnable() {
            @Override
            public void run() {
                nv.fullScroll(View.FOCUS_DOWN);
            }

        });

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
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.include_village_main_comment_btn:
                if(comment_content.getText().toString().trim().equals("")){
                    Toast.makeText(this, "덧글 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(comment_content.getText().toString().trim().length()>=100){
                    Toast.makeText(this, "글자수는 100자를 넘어갈 수 없습니다", Toast.LENGTH_SHORT).show();
                }else{
                    addComment();
                }
                break;
            case R.id.include_village_main_member_next_view:
                //new HTTPconnVillMember(VillageMainActivity.this,villageMainInfo.getVil_id(),"0").execute();
                Dialog dialog = new VillageMemberDialog(this,villageMainInfo.getVil_id());
                dialog.show();
                break;
            case R.id.include_village_main_stats_view:
                new HTTPconnVillStats(this,villageMainInfo.getVil_id()).execute();
                //startActivity(new Intent(VillageMainActivity.this,VillageStatsActivity.class));
                break;
        }

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(toobarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(toobarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(main_title_view, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(main_title_view, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) mImageparallax.getLayoutParams();

        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) mFrameParallax.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);

        mImageparallax.setLayoutParams(petDetailsLp);
        mFrameParallax.setLayoutParams(petBackgroundLp);
    }
}
