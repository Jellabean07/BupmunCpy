package com.wonbuddism.bupmun.Village.HTTPconnection;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPconnVillCommentModify extends AsyncTask<Void,Void,Void> {
    private Activity activity;
    private String TAG = "HTTPconnVillCommentModify";
    private String http_conection_url = "http://sakyoung.mybluemix.net/vilage/comment/modify";
    private String http_login_otp="otp";
    private String http_msg = "msg";
    private String http_comment_no = "comment_no";
    private String responseResult;
    private String OTP;
    private String MSG;
    private String COMMENT_NO;


    public HTTPconnVillCommentModify(Activity activity, String msg, String comment_no) {
        this.activity = activity;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.MSG = msg;
        this.COMMENT_NO = comment_no;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_login_otp+"="+OTP + "&"+http_msg+"="+MSG +"&"+http_comment_no+"="+COMMENT_NO;

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

        if (responseResult != null) {
            try {
                JSONObject jObj = new JSONObject(responseResult);
                responseCode = jObj.getString("code");

            } catch (JSONException e) {
                Log.e(TAG, "JSON error :: " + e);
            }
        } else {
            responseCode = null;
        }
        Log.d(TAG, "response code :: " + responseCode);

        if (responseCode == null) {
            Toast.makeText(activity, "실패00", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상

            Toast.makeText(activity,"성공",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {

            Toast.makeText(activity,"실패01",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity,"실패02",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }

    }
}
