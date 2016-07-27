package com.wonbuddism.bupmun.HttpConnection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.VillageStats;
import com.wonbuddism.bupmun.Common.PrefUserInfoManager;
import com.wonbuddism.bupmun.Dialog.ProgressWaitDaialog;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Vil.VillageStatsActivity;

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

public class HTTPconnVillStats extends AsyncTask<Void,Void,Void>{

    private Activity activity;
    private String TAG = "HTTPconnVillStats";
    private String http_conection_url = "vilage/statistics";
    private String http_otp ="otp";
    private String http_vil_id ="vil_id";
    private String responseResult;
    private String OTP;
    private String VIL_ID;
    private String http_host;
    private ProgressWaitDaialog daialog;



    public HTTPconnVillStats(Activity activity,String vil_id) {
        this.activity = activity;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.VIL_ID = vil_id;
        this.daialog = new ProgressWaitDaialog(this.activity);
        this.http_host = this.activity.getResources().getString(R.string.host_name);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        daialog.show();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP + "&" +http_vil_id +"="+VIL_ID;

        try {
            URL url = new URL(http_host+http_conection_url);
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
        VillageStats villageStats = new VillageStats();
        daialog.dismiss();
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
            Toast.makeText(activity, "통계 데이터를 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상
            for(VillageStats s : getList(resultData) ){
                villageStats = s;
            }

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

        }else if (responseCode.contains("03")) {
            Toast.makeText(activity,"로그인이 만료되었습니다",Toast.LENGTH_SHORT).show();
            // 03 : 로그인 만료
            new HttpConnLogout(activity).execute();

        }

    }

    private ArrayList<VillageStats> getList(JSONArray jList){
        int count = 0;
        if(jList !=null){
            count = jList.length();
        }

        ArrayList<VillageStats> list = new ArrayList<VillageStats>();
        VillageStats info = null;

        /** Taking each country, parses and adds to list object */
        for(int i=0; i<count;i++){
            try {
                /** Call getCountry with country JSON object to parse the country */
                info = getObject((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private VillageStats getObject(JSONObject jInfo){
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
            VIL_TODAY_CNT = String.valueOf(jInfo.getInt("VIL_TODAY_CNT"));
            VIL_MONTH_CNT = String.valueOf(jInfo.getInt("VIL_MONTH_CNT"));
            VIL_TOTAL_CNT = String.valueOf(jInfo.getInt("VIL_TOTAL_CNT"));
            VIL_CNT = String.valueOf(jInfo.getInt("VIL_CNT"));
            VIL_RANK = jInfo.getString("VIL_RANK");
            MEMBER_TODAY_CNT = String.valueOf(jInfo.getInt("MEMBER_TODAY_CNT"));
            MEMBER_MONTH_CNT = String.valueOf(jInfo.getInt("MEMBER_MONTH_CNT"));
            MEMBER_TOTAL_CNT = String.valueOf(jInfo.getInt("MEMBER_TOTAL_CNT"));
            MEMBER_CNT = String.valueOf(jInfo.getInt("MEMBER_CNT"));
            MEMBER_RANK = jInfo.getString("MEMBER_RANK");
        }catch (JSONException e){
            e.printStackTrace();
        }
        VillageStats info=new VillageStats(VIL_ID,VIL_TODAY_CNT,VIL_MONTH_CNT,VIL_TOTAL_CNT,VIL_CNT,VIL_RANK,MEMBER_TODAY_CNT,MEMBER_MONTH_CNT,MEMBER_TOTAL_CNT,MEMBER_CNT,MEMBER_RANK);
        return info;
    }
}
