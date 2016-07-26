package com.wonbuddism.bupmun.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wonbuddism.bupmun.Common.PrefUserInfoManager;
import com.wonbuddism.bupmun.Database.ExcelToDb;
import com.wonbuddism.bupmun.Login.LoginMainActivity;
import com.wonbuddism.bupmun.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 1000); // 3초 후에 hd Handler 실행

    }

    private class splashhandler implements Runnable{
        public void run() {
            if(new PrefUserInfoManager(SplashActivity.this).getLoginState()){
                startActivity(new Intent(getApplication(), MainActivity.class)); // 로딩이 끝난후 이동할 Activity
            }else {
                startActivity(new Intent(getApplication(), LoginMainActivity.class)); // 로딩이 끝난후 이동할 Activity

            }
            SplashActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
        }
    }

    @Override
    public void onBackPressed() {

    }

}
