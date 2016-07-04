package com.wonbuddism.bupmun.Writing.HTTPconnection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.Database.Typing.TYPING_HIST;
import com.wonbuddism.bupmun.Main.MainActivity;
import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;
import com.wonbuddism.bupmun.Writing.TypingProcess.TypingDbManager;

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


public class HTTPconnSyncDown extends AsyncTask<Void,Void,Void> {

    private Activity activity;
    private String TAG = "HTTPconnSyncDown";
    private String http_conection_url = "http://115.91.201.9/sync/new";
    private String http_otp ="otp";
    private String http_page_no = "page_no";
    private String responseResult;
    private String OTP;
    private String PAGE_NO;
    private TypingDbManager dbManager;
   /* paragraph_no (varchar)
    tasu (varchar)
    index (varchar)*/



    public HTTPconnSyncDown(Activity activity, String page_no) {
        this.activity = activity;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.PAGE_NO = page_no;
        this.dbManager = new TypingDbManager(activity);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP+"&"+http_page_no +"="+PAGE_NO;

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
        JSONArray resultData = null;
        ArrayList<TYPING_HIST> typing_hists;
        if (responseResult != null) {
            try {
                JSONObject jObj = new JSONObject(responseResult);
                responseCode = jObj.getString("code");
                resultData = jObj.getJSONArray("resultData");
            } catch (JSONException e) {
                Log.e(TAG, "JSON error :: " + e);
            }
        } else {
            responseCode = null;
        }
        Log.d(TAG, "response code :: " + responseCode);

        if (responseCode == null) {
            Toast.makeText(activity, "통신 동기화에 실패하였습니다", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상
            typing_hists=getTyping_hists(resultData);
            /*db작업*/
            dbManager.setTYPING_HIST_SyncDown(typing_hists);
            activity.startActivity(new Intent(activity, MainActivity.class)); // 로딩이 끝난후 이동할 Activity
            activity.setResult(1000);
            activity.finish(); // 로딩페이지 Activity Stack에서 제거
            // resultdata; -> 성공여부
            //Toast.makeText(activity,"오류신고가 정상적으로 처리 되었습니다",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {

            Toast.makeText(activity,"통신 동기화에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity,"통신 동기화에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }else{
            Toast.makeText(activity,"통신 동기화에 실패하였습니다",Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<TYPING_HIST> getTyping_hists(JSONArray jList){
        int count = jList.length();
        ArrayList<TYPING_HIST> list = new ArrayList<>();
        TYPING_HIST info = null;

        for(int i=0; i<count;i++){
            try {
                info = getTyping_hist((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private TYPING_HIST getTyping_hist(JSONObject jInfo){
        int typing_cnt = 0; //	사경횟수	SMALLINT
        String typing_id = ""; //	사경아이디	CHARACTER
        String bupmunindex = ""; //	법문인덱스키	VARCHAR
        int paragraph_no= 0; //	문단번호	SMALLINT
        String chns_yn= ""; //	한자포함여부	CHARACTER
        int tasu= 0; //	타수	SMALLINT
        String regist_date= ""; //	사경일	CHARACTER
        String regist_time= ""; //	사경시간	CHARACTER

        try{
            typing_cnt = jInfo.optInt("TYPING_CNT", 0);
            typing_id = jInfo.optString("TYPING_ID","");
            bupmunindex = jInfo.getString("BUPMUNINDEX");
            paragraph_no = jInfo.optInt("PARAGRAPH_NO");
            chns_yn = jInfo.getString("CHNS_YN");
            tasu = jInfo.optInt("TASU",0);
            regist_date = jInfo.getString("REGIST_DATE");
            regist_time = jInfo.getString("REGIST_TIME");

        }catch (JSONException e){
            e.printStackTrace();
        }
        TYPING_HIST info=new TYPING_HIST(typing_cnt,typing_id,bupmunindex,
                paragraph_no,chns_yn,tasu,regist_date,regist_time);
        return info;
    }
}
