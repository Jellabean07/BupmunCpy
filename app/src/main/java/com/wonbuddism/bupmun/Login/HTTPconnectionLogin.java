package com.wonbuddism.bupmun.Login;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wonbuddism.bupmun.Main.BeginningActivity;
import com.wonbuddism.bupmun.Main.MainActivity;
import com.wonbuddism.bupmun.Utility.PrefUserInfo;
import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;
import com.wonbuddism.bupmun.Utility.ProgressWaitDaialog;
import com.wonbuddism.bupmun.Writing.HTTPconnection.HTTPconnSyncDown;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HTTPconnectionLogin extends AsyncTask<Void,Void,Void>{
    private Activity activity;
    private String TAG = "HTTPconnectionLogin";
    private String http_conection_url = "http://115.91.201.9/login";
    private String http_login_userid="userid";
    private String http_login_pass="userpass";
    private String http_version="VERSION";
    private String http_os="OS";
    private String http_model="MODEL";
    private String responseResult;
    private String userid;
    private String userpass;
    private String version;
    private String model;
    private String OS;
    private Dialog dialog;


    public HTTPconnectionLogin(Activity activity, String userid, String userpass) {
        this.activity = activity;

        this.dialog  = new ProgressWaitDaialog(activity);
        this.dialog.show();
        this.userid = userid;
        this.userpass = userpass;
        this.version = Build.VERSION.RELEASE;
        this.OS = "Android";
        this.model = Build.MODEL;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_login_userid+"="+userid+"&"+http_login_pass+"="+userpass
                +"&"+ http_version+"="+version+"&"+ http_model+"="+model +"&"+ http_os+"="+OS;

        try {
            URL url = new URL(http_conection_url);
            HttpURLConnection httpURLconn = (HttpURLConnection) url.openConnection();
            httpURLconn.setRequestMethod("POST");
            httpURLconn.setUseCaches(false);
            httpURLconn.setDoInput(true);
            httpURLconn.setDoOutput(true);

            httpURLconn.addRequestProperty("Accept", "application/json");
            httpURLconn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLconn.connect();



            OutputStream outputStream = httpURLconn.getOutputStream();
            outputStream.write(postData.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = httpURLconn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLconn.getInputStream(),"UTF-8"));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                responseResult = response.toString();
            }
        } catch (IOException e) {
            Log.e(TAG, "Http Connection Error :: " + e);
        }
        Log.d(TAG, "response : " + responseResult);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        String responseCode = "";
        String otp = "";
        JSONObject resultData = null;
        dialog.dismiss();

        if (responseResult != null) {
            try {

                JSONObject jObj = new JSONObject(responseResult);
                responseCode = jObj.getString("code");
                otp = jObj.getString("otp");
                resultData = jObj.getJSONObject("resultData");

            } catch (JSONException e) {
                Log.e(TAG, "JSON error :: " + e);
            }
        } else {
            responseCode = null;
        }
        Log.d(TAG, "response code :: " + responseCode);

        if (responseCode == null) {
            Toast.makeText(activity,"로그인에 실패 하였습니다",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상

            PrefUserInfo userInfo =  getUserInfo(resultData);

            new PrefUserInfoManager(activity).LogIn(userInfo);
            new PrefUserInfoManager(activity).setOTP(otp);
            activity.startActivity(new Intent(activity, MainActivity.class)); // 로딩이 끝난후 이동할 Activity
            activity.setResult(1000);
            activity.finish(); // 로딩페이지 Activity Stack에서 제거

            //new HTTPconnSyncDown(activity,"0").execute();
            Toast.makeText(activity,"로그인 되었습니다",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {

            Toast.makeText(activity,"로그인에 실패 하였습니다",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity,"로그인에 실패 하였습니다",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }

    }

    private PrefUserInfo getUserInfo(JSONObject jInfo){
        String USERID = "";	//아이디
        String TYPING_ID = "";	 //사경아이디
        String BDNM = "";	//법명
        String NAME = "";	//속명
        String USER_LEVEL = ""; //	사용자 등급

        try{
            USERID = jInfo.getString("USERID");
            TYPING_ID = jInfo.optString("TYPING_ID","");
            BDNM = jInfo.getString("BDNM");
            NAME = jInfo.getString("NAME");
            USER_LEVEL = jInfo.getString("USER_LEVEL");
        }catch (JSONException e){
            e.printStackTrace();
        }
        PrefUserInfo info=new PrefUserInfo(USERID,TYPING_ID,BDNM,NAME,USER_LEVEL);
        Log.e("PrefUserInfo",info.toString());
        return info;
    }

}
