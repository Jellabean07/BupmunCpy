package com.wonbuddism.bupmun.Village.HTTPconnection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;
import com.wonbuddism.bupmun.Village.VillageStatsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPconnVillStats extends AsyncTask<Void,Void,Void>{

    private Activity activity;
    private String TAG = "HTTPconnVillStats";
    private String http_conection_url = "http://115.91.201.9/vilage/statistics";
    private String http_otp ="otp";
    private String http_vil_id ="vil_id";
    private String responseResult;
    private String OTP;
    private String VIL_ID;


    public HTTPconnVillStats(Activity activity,String vil_id) {
        this.activity = activity;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.VIL_ID = vil_id;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP + "&" +http_vil_id +"="+VIL_ID;

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
        JSONObject resultData = null;
        VillageStats villageStats;

        if (responseResult != null) {
            try {
                JSONObject jObj = new JSONObject(responseResult);
                responseCode = jObj.getString("code");
                resultData = jObj.getJSONObject("resultData");

            } catch (JSONException e) {
                Log.e(TAG, "JSON error :: " + e);
            }
        } else {
            responseCode = null;
        }
        Log.d(TAG, "response code :: " + responseCode);

        if (responseCode == null) {
            Toast.makeText(activity, "통계 데이터를 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상
            villageStats = getVillageStats(resultData);
            Log.e("villageStats",villageStats.toString());
            Intent intent = new Intent(activity, VillageStatsActivity.class);
            intent.putExtra("villageStats", villageStats);
            activity.startActivity(intent);

            // 로딩페이지 Activity Stack에서 제거
            // Toast.makeText(activity,"성공",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {

            Toast.makeText(activity, "통계 데이터를 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity, "통계 데이터를 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }

    }

    private VillageStats getVillageStats(JSONObject jInfo){
        String VIL_TODAY_CNT = ""; //오늘 단락수
        String VIL_MONTH_CNT = ""; //최근 30일 단락수
        String VIL_TOTAL_CNT = ""; //누적 단락수
        String VIL_CNT = ""; //동네 전체 개수
        String VIL_RANK = ""; //동네 전체순위
        String MEMBER_TODAY_CNT = ""; //오늘 단락수
        String MEMBER_MONTH_CNT = ""; //최근 30일 단락수
        String MEMBER_TOTAL_CNT = ""; //누적 단락수
        String MEMBER_CNT = ""; //동네 회원수
        String MEMBER_RANK = ""; //동네에서 내 순위

        try{
            VIL_TODAY_CNT = jInfo.getString("VIL_TODAY_CNT");
            VIL_MONTH_CNT = jInfo.getString("VIL_MONTH_CNT");
            VIL_TOTAL_CNT = jInfo.getString("VIL_TOTAL_CNT");
            VIL_CNT = jInfo.getString("VIL_CNT");
            VIL_RANK = jInfo.getString("VIL_RANK");
            MEMBER_TODAY_CNT = jInfo.getString("MEMBER_TODAY_CNT");
            MEMBER_MONTH_CNT = jInfo.getString("MEMBER_MONTH_CNT");
            MEMBER_TOTAL_CNT = jInfo.getString("MEMBER_TOTAL_CNT");
            MEMBER_CNT = jInfo.getString("MEMBER_CNT");
            MEMBER_RANK = jInfo.getString("MEMBER_RANK");
        }catch (JSONException e){
            e.printStackTrace();
        }
        VillageStats info=new VillageStats(VIL_ID,VIL_TODAY_CNT,VIL_MONTH_CNT,VIL_TOTAL_CNT,VIL_CNT,VIL_RANK,MEMBER_TODAY_CNT,MEMBER_MONTH_CNT,MEMBER_TOTAL_CNT,MEMBER_CNT,MEMBER_RANK);
        return info;
    }
}
