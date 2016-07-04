package com.wonbuddism.bupmun.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wonbuddism.bupmun.Database.ExcelToDb;
import com.wonbuddism.bupmun.Login.LoginMainActivity;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Database.ExcelToDatabase;

public class SplashActivity extends AppCompatActivity {


    ProgressDialog dialog;
    ExcelToDb toDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 에니메이션을 구동하기 위한 이미지뷰를 가져와 애니메이션을 세팅합니다.
    /*    splashAnimation = (ImageView) findViewById(R.id.splash);
        splashAnimation.setBackgroundResource(R.drawable.splash_animation);
        aniFrame = (AnimationDrawable)splashAnimation.getBackground();
        aniFrame.start();*/

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 1000); // 3초 후에 hd Handler 실행

    }

    private class splashhandler implements Runnable{
        public void run() {
            SplashActivity.this.toDatabase = new ExcelToDb(SplashActivity.this);

            SharedPreferences pref = getSharedPreferences("Db", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            //db 존재유무확인하고 존재하면 불러오지않는다
            if(pref.getBoolean("key",true)){
                editor.putBoolean("key", false);
                editor.commit();

                toDatabase.copyExcelDataToDatabase();

            }

            startActivity(new Intent(getApplication(), LoginMainActivity.class)); // 로딩이 끝난후 이동할 Activity
            SplashActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
        }
    }

    @Override
    public void onBackPressed() {

    }

}
