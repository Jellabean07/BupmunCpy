package com.wonbuddism.bupmun.Main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wonbuddism.bupmun.Board.BoardMainActivity;
import com.wonbuddism.bupmun.Database.DbAdapter2;
import com.wonbuddism.bupmun.Login.LoginMainActivity;
import com.wonbuddism.bupmun.MyFeeling.FeelingMainActivity;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Progress.ProgressMainActivity;
import com.wonbuddism.bupmun.Utility.NavigationDrawerMenu;
import com.wonbuddism.bupmun.Writing.WritingMainActivity;

import java.util.ArrayList;

public class BeginningActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout mDrawerLayout;
    private ImageView progress_title;
    private TextView notice_more;
    private TextView feeling_more;
    private Button writing;
    private ArrayList<String> notice;
    private ArrayList<String> feeling;
    private ListView listview_notice;
    private ListView listview_feeling;
    private ProgressBar progressBar;
    private TextView progressValue;
    private float progress_rating = 50; //임시 값

    private long backKeyPressedTime = 0;
    private Toast toast;

    private SharedPreferences pref;
    private TextView login;
    /*private final Handler mHandler  = new Handler();
    private boolean mFlag = false;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginning);

        LayoutView();
    }

    private void LayoutView(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        progress_title = (ImageView)findViewById(R.id.view_progress_imageview_title);
        progress_title.setOnClickListener(this);
        notice_more = (TextView)findViewById(R.id.view_list_notice_textview_more);
        notice_more.setOnClickListener(this);
        feeling_more = (TextView)findViewById(R.id.view_list_feeling_textview_more);
        feeling_more.setOnClickListener(this);
        writing = (Button)findViewById(R.id.beginning_button_writing);
        writing.setOnClickListener(this);
        listview_feeling = (ListView)findViewById(R.id.view_list_feeling_listview);
        listview_notice = (ListView)findViewById(R.id.view_list_notice_listview);
        progressValue = (TextView)findViewById(R.id.view_progress_textview_value);
        progressBar = (ProgressBar)findViewById(R.id.view_progress_progressbar);
        progress_rating = getProgressRating();
        progressValue.setText((int)progress_rating+"%");
        progressBar.setProgress((int)progress_rating);
        /*
        test 데이터

         */
        notice=new ArrayList<>();
        notice.add("사경진도표 및 이달의 사경로드 업데이트");
        notice.add("12월 3일 서버 정기점검 안내");
        notice.add("법문사경 출석 체크 정책 변경안내");
        notice.add("11월 업데이트 일정");
        notice.add("이달의 사경 랭킹");

        feeling = new ArrayList<>();
        feeling.add("감각감상 메모");
        feeling.add("감각감상 메모일까?");
        feeling.add("좋다 좋아");
        feeling.add("마음이 차분해진다");
        feeling.add("옆집 개가 짖고있네요");
        feeling.add("듣고 있자니 배가 고파오네요");
        /*
        test 데이터

         */

        listview_feeling.setAdapter(new BeginningListAdapter(BeginningActivity.this,feeling));
        listview_notice.setAdapter(new BeginningListAdapter(BeginningActivity.this,notice));

        //네비게이션
        NavigationView navigationView = (NavigationView) findViewById(R.id.beginning_nav_view);


        if (navigationView != null) {
            new NavigationDrawerMenu(this,mDrawerLayout).setupDrawerContent(navigationView);
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.beginning_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.beginning_collapsing_toolbar);
        collapsingToolbar.setTitleEnabled(false);
        loadBackdrop();

        pref=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.beginning_backdrop);
        Glide.with(this).load(R.drawable.beginning_back).centerCrop().into(imageView);
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
        switch (v.getId()){
            case R.id.view_progress_imageview_title:
                startActivity(new Intent(BeginningActivity.this, ProgressMainActivity.class));
                break;
            case R.id.beginning_button_writing:
                startActivity(new Intent(BeginningActivity.this, WritingMainActivity.class));
                break;
            default:
                if(pref.getBoolean("state",false)){
                    finish();
                    switch (v.getId()){
                        case R.id.view_list_feeling_textview_more:
                            startActivity(new Intent(BeginningActivity.this, FeelingMainActivity.class));
                            break;
                        case R.id.view_list_notice_textview_more:
                            startActivity(new Intent(BeginningActivity.this, BoardMainActivity.class));
                            break;
                    }
                }else{
                    getDialog();
                }
                break;
        }

    }

    public float getProgressRating(){
        DbAdapter2 dbAdapter = new DbAdapter2(this);
        dbAdapter.open();
        int allCount = dbAdapter.getCount();
        Log.e("allCount",allCount+"");
        int writingCount = dbAdapter.getWritingCount();
        //int writingCount = 200;
        Log.e("writingCount", writingCount + "");
        dbAdapter.close();

        return Float.parseFloat(String.format("%.1f", (writingCount/(float)allCount)*100));
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }else{
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                showGuide();
                return;
            }

            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                finish();
                toast.cancel();
            }
        }

        super.onBackPressed();
    }

    private void showGuide() {
        toast = Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void getDialog(){
        AlertDialog.Builder d = new AlertDialog.Builder(this);
        d.setTitle("로그인");
        d.setMessage("로그인이 필요한 서비스입니다\n로그인 하시겠습니까?");
        // d.setIcon(R.drawable.warnning_off);

        d.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
                startActivity(new Intent(BeginningActivity.this, LoginMainActivity.class));
            }
        });

        d.setNegativeButton("아니요",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        d.show();
    }
}
