package com.wonbuddism.bupmun.HttpConnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.VillageStatsMember;
import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;
import com.wonbuddism.bupmun.Village.VillageStatsRecyclerViewAdapter;

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

public class HTTPconnVillStatsMember extends AsyncTask<Void,Void,Void>{

    private Activity activity;
    private String TAG = "HTTPconnVillStatsMember";
    private String http_conection_url = "http://115.91.201.9/vilage/statistics/member";
    private String http_otp ="otp";
    private String http_vil_id ="vil_id";
    private String http_page_no = "page_no";
    private String responseResult;
    private String OTP;
    private String VIL_ID;
    private String PAGE_NO;
    private VillageStatsRecyclerViewAdapter adapter;

    public HTTPconnVillStatsMember(Activity activity, VillageStatsRecyclerViewAdapter adapter, String vil_id, String page_no) {
        this.activity = activity;
        this.adapter = adapter;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.VIL_ID = vil_id;
        this.PAGE_NO = page_no;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP +"&"+http_vil_id +"="+VIL_ID +"&"+http_page_no +"="+PAGE_NO;

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
        ArrayList<VillageStatsMember> villageStatsMembers;
        adapter.showLoading(false);
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
            Toast.makeText(activity, "인터넷 연결실패", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상
            villageStatsMembers = getVillageStatsMemberList(resultData);
            for(VillageStatsMember member : villageStatsMembers)
            {
                adapter.add(member);
            }

            adapter.notifyDataSetChanged();

            // 로딩페이지 Activity Stack에서 제거
          //  Toast.makeText(activity,"성공",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {
            Toast.makeText(activity, "인터넷 연결실패", Toast.LENGTH_SHORT).show();
           // Toast.makeText(activity,"실패01",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity, "인터넷 연결실패", Toast.LENGTH_SHORT).show();
          //  Toast.makeText(activity,"실패02",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }

    }

    private ArrayList<VillageStatsMember> getVillageStatsMemberList(JSONArray jList){
        int count = jList.length();
        ArrayList<VillageStatsMember> list = new ArrayList<>();
        VillageStatsMember info = null;

        for(int i=0; i<count;i++){
            try {
                info = getVillageStatsMember((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private VillageStatsMember getVillageStatsMember(JSONObject jInfo){
        String NO = ""; //동네안 순위
        String USERID = ""; //유저 아이디
        String NAME = ""; //유저 이름
        String TODAY_CNT = ""; // 오늘 단락수
        String MONTH_CNT = ""; //최근 30일 단락수
        String TOTAL_CNT = ""; //누적 단락수

        try{
            NO = jInfo.getString("NO");
            USERID = jInfo.getString("USERID");
            NAME = jInfo.getString("NAME");

            TODAY_CNT = jInfo.optString("VIL_CNT","0");
            MONTH_CNT = jInfo.optString("MONTH_CNT","0");
            TOTAL_CNT = jInfo.optString("TOTAL_CNT","0");
        }catch (JSONException e){
            e.printStackTrace();
        }
        VillageStatsMember info=new VillageStatsMember(NO,USERID,NAME,TODAY_CNT,MONTH_CNT,TOTAL_CNT);
        return info;
    }
}
