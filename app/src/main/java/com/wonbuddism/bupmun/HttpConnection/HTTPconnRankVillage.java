package com.wonbuddism.bupmun.HttpConnection;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.RankMyInfo;
import com.wonbuddism.bupmun.DataVo.RankVillageInfo;
import com.wonbuddism.bupmun.Rank.RankVillageRecyclerViewAdapter;
import com.wonbuddism.bupmun.Common.PrefUserInfoManager;

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

public class HTTPconnRankVillage extends AsyncTask<Void,Void,Void>{
    private Activity activity;
    private String TAG = "HTTPconnectionRank";
    private String http_conection_url = "http://115.91.201.9/rank/vilage";
    private String http_otp ="otp";
    private String http_page_no ="page_no";
    private String responseResult;
    private String OTP;
    private String PAGE_NO;
    private RankVillageRecyclerViewAdapter adapter;
    private ArrayList<RankVillageInfo> rankVillageInfos;

    public HTTPconnRankVillage(Activity activity,RankVillageRecyclerViewAdapter adapter,ArrayList<RankVillageInfo> rankVillageInfos, String page_no) {
        this.activity = activity;
        this.adapter = adapter;
        this.rankVillageInfos = rankVillageInfos;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.PAGE_NO = page_no;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP +"&"+http_page_no+"="+PAGE_NO;

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
        ArrayList<RankVillageInfo> rankVillageInfos;
        RankMyInfo rankMyInfo;

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
            Toast.makeText(activity, "랭킹을 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상
            rankVillageInfos = getRankVillageInfoList(resultData);
            this.rankVillageInfos.addAll(rankVillageInfos);
            adapter.setItems(this.rankVillageInfos); // No need of this
            adapter.showLoading(false);
            adapter.notifyDataSetChanged();

             // 로딩페이지 Activity Stack에서 제거
            //Toast.makeText(activity,"성공",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {

            Toast.makeText(activity,"랭킹을 불러오는데 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity,"랭킹을 불러오는데 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }else if (responseCode.contains("03")) {
            Toast.makeText(activity,"로그인이 만료되었습니다",Toast.LENGTH_SHORT).show();
            // 03 : 로그인 만료
            new HttpConnLogout(activity).execute();

        }else{
            Toast.makeText(activity,"랭킹을 불러오는데 실패하였습니다",Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<RankVillageInfo> getRankVillageInfoList(JSONArray jList){
        int count = jList.length();
        ArrayList<RankVillageInfo> list = new ArrayList<>();
        RankVillageInfo info = null;

        /** Taking each country, parses and adds to list object */
        for(int i=0; i<count;i++){
            try {
                /** Call getCountry with country JSON object to parse the country */
                info = getRankVillageInfo((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private RankVillageInfo getRankVillageInfo(JSONObject jInfo){
        String no = ""; // 순위
        String vil_manager = ""; //동네운영자이름
        String vil_name= ""; //동네이름
        String vil_user_count = ""; //동네회원수

        try{
            no = jInfo.getString("NO");
            vil_manager = jInfo.getString("VIL_MANAGER");
            vil_name = jInfo.getString("VIL_NAME");
            vil_user_count = jInfo.getString("VIL_USER_COUNT");
        }catch (JSONException e){
            e.printStackTrace();
        }
        RankVillageInfo info=new RankVillageInfo(no,vil_manager,vil_name,vil_user_count);
        return info;
    }
}
