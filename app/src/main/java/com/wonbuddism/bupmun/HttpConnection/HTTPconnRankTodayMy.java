package com.wonbuddism.bupmun.HttpConnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.RankMyInfo;
import com.wonbuddism.bupmun.Common.PrefUserInfoManager;
import com.wonbuddism.bupmun.R;

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

public class HTTPconnRankTodayMy extends AsyncTask<Void,Void,Void> {
    private Activity activity;
    private String TAG = "HTTPconnRankTodayMy";
    private String http_conection_url = "rank/today";
    private String http_otp ="otp";
    private String http_page_no ="page_no";
    private String responseResult;
    private String OTP;
    private String PAGE_NO;
    private String http_host;
    private TextView msg;



    public HTTPconnRankTodayMy(Activity activity,TextView msg) {
        this.activity = activity;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.PAGE_NO = "0";
        this.msg = msg;
        this.http_host = this.activity.getResources().getString(R.string.host_name);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP +"&"+http_page_no+"="+PAGE_NO;

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
        JSONArray resultData1 = null;
        RankMyInfo rankMyInfo = null;

        if (responseResult != null) {
            try {
                JSONObject jObj = new JSONObject(responseResult);
                responseCode = jObj.getString("code");
                resultData1 = jObj.getJSONArray("resultData1");

            } catch (JSONException e) {
                Log.e(TAG, "JSON error :: " + e);
            }
        } else {
            responseCode = null;
        }
        Log.d(TAG, "response code :: " + responseCode);

        if (responseCode == null) {
            Toast.makeText(activity, "본인 랭킹을 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상

            for(RankMyInfo s : getList(resultData1)){
                rankMyInfo = s;
            }

            msg.setText(rankMyInfo.getName()+"님의 전체 랭킹은 전체"+rankMyInfo.getToday_rank()+"명 중에 "+rankMyInfo.getToday_total_cnt()+"위 입니다.");

        } else if (responseCode.contains("01")) {

            Toast.makeText(activity,"본인 랭킹을 불러오는데 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity,"본인 랭킹을 불러오는데 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }else if (responseCode.contains("03")) {
            Toast.makeText(activity,"로그인이 만료되었습니다",Toast.LENGTH_SHORT).show();
            // 03 : 로그인 만료
            new HttpConnLogout(activity).execute();

        }else{
            Toast.makeText(activity,"본인 랭킹을 불러오는데 실패하였습니다",Toast.LENGTH_SHORT).show();
        }

    }
    private ArrayList<RankMyInfo> getList(JSONArray jList){
        int count = 0;
        if(jList !=null){
            count = jList.length();
        }

        ArrayList<RankMyInfo> list = new ArrayList<RankMyInfo>();
        RankMyInfo info = null;

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

    private RankMyInfo getObject(JSONObject jInfo){
        String name = new PrefUserInfoManager(activity).getUserInfo().getNAME();
        String today_rank = "0";
        String today_total_cnt = "0";

        if(jInfo !=null) {
            try {
                name = jInfo.getString("NAME");
                today_rank = jInfo.getString("RN");
                today_total_cnt = jInfo.getString("TODYAY_TOTAL_CNT");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        RankMyInfo info=new RankMyInfo(name,today_rank,today_total_cnt);
        return info;
    }

}
