package com.wonbuddism.bupmun.Main;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wonbuddism.bupmun.Database.Typing.DbAdapter;
import com.wonbuddism.bupmun.Login.HTTPconnectionLogout;
import com.wonbuddism.bupmun.Login.LoginMainActivity;
import com.wonbuddism.bupmun.Progress.ProgressMainActivity;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.NavigationDrawerMenu;
import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;
import com.wonbuddism.bupmun.Writing.TypingProcess.TypingLogDbAdapter;
import com.wonbuddism.bupmun.Writing.WritingMainActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private TextView tv_login;
    private TextView tv_login_msg;
    private TextView tv_sync;
    private TextView tv_sync_date;
    private TextView tv_rating;

    private Button btn_progress;
    private Button btn_writing;

    private ProgressBar pb_ring;
    private long backKeyPressedTime = 0;
    private Toast toast;


    private DbAdapter bupmunDbAdapter;
    private TypingLogDbAdapter typingLogDbAdapter;
    private int dbParagraph;
    private int currentParaprah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLayout();

    }

    private void setLayout(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        tv_login = (TextView)findViewById(R.id.main_toolbar_login);
        tv_login_msg = (TextView)findViewById(R.id.include_main_msg_textview);
        tv_sync = (TextView)findViewById(R.id.include_main_sync_textview);
        tv_sync_date = (TextView)findViewById(R.id.include_main_sync_his_textview);
        tv_rating = (TextView)findViewById(R.id.include_main_progress_rate_textview);

        btn_progress = (Button)findViewById(R.id.include_main_progress_btn);
        btn_writing = (Button)findViewById(R.id.include_main_writing_btn);

        pb_ring = (ProgressBar) findViewById(R.id.include_main_progressbar);

        tv_login.setOnClickListener(this);
        tv_sync.setOnClickListener(this);
        btn_progress.setOnClickListener(this);
        btn_writing.setOnClickListener(this);

        LoginStateCheck();

        Log.e("progressValue", getProgress() + "");
        int progessValue = getProgress();
        pb_ring.setProgress(progessValue);
        tv_rating.setText(progessValue+"%");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_nav_view);
        if (navigationView != null) {
            new NavigationDrawerMenu(this,mDrawerLayout).setupDrawerContent(navigationView);
        }


     /*   Animation an = new RotateAnimation(0.0f, 90.0f, 250f, 273f);
        an.setFillAfter(true);
        pb.startAnimation(an);*/
    }

    private void getDbParagraph(){
        this.bupmunDbAdapter = new DbAdapter(MainActivity.this);
        bupmunDbAdapter.open();
        bupmunDbAdapter.beginTransaction();
        try{
            dbParagraph = bupmunDbAdapter.getBupmunParagraphCount();
            Log.e("dbParagraph",dbParagraph+"");
            bupmunDbAdapter.setTransaction();
        }catch (Exception e){
        }finally {
            bupmunDbAdapter.endTransaction();
        }
        bupmunDbAdapter.close();
    }

    private void getCurrentParagraph(){
        this.typingLogDbAdapter = new TypingLogDbAdapter(MainActivity.this);
        typingLogDbAdapter.open();
        typingLogDbAdapter.beginTransaction();
        try{
            currentParaprah = typingLogDbAdapter.getCount();
            Log.e("currentParaprah",currentParaprah+"");
            typingLogDbAdapter.setTransaction();
        }catch (Exception e){
        }finally {
            typingLogDbAdapter.endTransaction();
        }
        typingLogDbAdapter.close();
    }

    private int getProgress(){
        getDbParagraph();
        getCurrentParagraph();
        return currentParaprah/dbParagraph;
    }

    public void LoginStateCheck(){
        if(new PrefUserInfoManager(this).getLoginState()){
            tv_login.setText("로그아웃");
            tv_login_msg.setText(new PrefUserInfoManager(this).getUserInfo().getNAME()+"님 반갑습니다");
            //tv_login_msg.setText("admin님 반갑습니다");
        }else {
            tv_login.setText("로그인");
            tv_login_msg.setText("");
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.main_toolbar_login:
                if(!new PrefUserInfoManager(this).getLoginState()){
                    startActivityForResult(new Intent(MainActivity.this, LoginMainActivity.class),1000);
                }else{
                    /*finish();
                    new PrefUserInfoManager(this).LogOut();*/
                   /* Dialog dialog = new ProgressWaitDaialog(this);
                    dialog.show();*/
                    new HTTPconnectionLogout(this).execute();

                }

                break;
            case R.id.include_main_sync_textview:
                break;
            case R.id.include_main_progress_btn:
                startActivity(new Intent(MainActivity.this, ProgressMainActivity.class));
                break;
            case R.id.include_main_writing_btn:
                startActivity(new Intent(MainActivity.this, WritingMainActivity.class));
                break;
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        LoginStateCheck();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1000){
            finish();
        }
    }
}
