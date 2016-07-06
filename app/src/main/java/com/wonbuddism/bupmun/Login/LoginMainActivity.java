package com.wonbuddism.bupmun.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wonbuddism.bupmun.HttpConnection.HTTPconnectionLogin;
import com.wonbuddism.bupmun.R;

public class LoginMainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText id;
    private EditText pass;
    private Button submit;
    private Button nonmember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);


        setLayout();
    }

    private void setLayout() {
        id = (EditText)findViewById(R.id.login_main_id_edittext);
        pass = (EditText)findViewById(R.id.login_main_pass_edittext);
        submit = (Button)findViewById(R.id.login_main_submit_btn);
        submit.setOnClickListener(this);
       // nonmember = (Button)findViewById(R.id.login_main_nomember_btn);

        id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    id.setBackgroundResource(R.drawable.view_login_edittext_active);
                }else{
                    id.setBackgroundResource(R.drawable.view_login_edittext);
                }
            }
        });

        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pass.setBackgroundResource(R.drawable.view_login_edittext_active);
                } else {
                    pass.setBackgroundResource(R.drawable.view_login_edittext);
                }
            }
        });

        id.requestFocus();
        id.setFocusable(true);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loginProc(){
        String id_value = id.getText().toString();
        String pass_value = pass.getText().toString();

       /* PrefUserInfo userInfo = new PrefUserInfo(id_value,"0000000","문유진","문유진","00");
        new PrefUserInfoManager(this).setUserInfo(userInfo);
        new PrefUserInfoManager(this).setOTP("$2a$08$K23vpQMI91kawc.nmYX3ne1U/o0nH4v3rYsygE9xEzK8Fn595O27C");
        new PrefUserInfoManager(this).setModeManager(true);
        new PrefUserInfoManager(this).setLoginState(true);

        startActivity(new Intent(LoginMainActivity.this, MainActivity.class)); // 로딩이 끝난후 이동할 Activity
        finish(); // 로딩페이지 Activity Stack에서 제거
        Toast.makeText(LoginMainActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();*/
        /*Dialog dialog = new ProgressWaitDaialog(this);
        dialog.show();*/


        new HTTPconnectionLogin(LoginMainActivity.this,id_value,pass_value).execute();
        //new HTTPconnSyncDown(this,"0").execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_main_submit_btn:
                loginProc();
                break;
         /*   case R.id.login_main_nomember_btn:
                finish();
                new PrefUserInfoManager(this).setLoginState(false);
                startActivity(new Intent(LoginMainActivity.this, BeginningActivity.class));
                break;*/
        }
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

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==1000){
            //일단 다운로드테스트

        }
    }*/
}
