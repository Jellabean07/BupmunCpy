package com.wonbuddism.bupmun.Login;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.Database.Typing.TYPING_HIST;
import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;

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

public class HTTPconnTypingSync extends AsyncTask<Void,Void,Void>{
    private Activity activity;
    private String TAG = "HTTPconnTypingSync";
    private String http_conection_url = "http://115.91.201.9/sync/child";
    private String http_otp ="otp";
    private String http_insert = "insert";
    private String responseResult;
    private String OTP;

    public HTTPconnTypingSync(Activity activity) {
        this.activity = activity;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP;

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
        JSONArray resultData = new JSONArray();
        ArrayList<TYPING_HIST> typing_hists= null;

        if (responseResult != null) {
            try {
                JSONObject jObj = new JSONObject(responseResult);
                responseCode = jObj.getString("code");
                resultData = jObj.getJSONArray("resultData");;

            } catch (JSONException e) {
                Log.e(TAG, "JSON error :: " + e);
            }
        } else {
            responseCode = null;
        }
        Log.d(TAG, "response code :: " + responseCode);

        if (responseCode == null) {
            Toast.makeText(activity, "동기화에 실패하였습니다", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상

            typing_hists = getTYPING_HIST(resultData);



        } else if (responseCode.contains("01")) {

            Toast.makeText(activity,"불러오기에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity,"불러오기에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }

    }

    private ArrayList<TYPING_HIST> getTYPING_HIST(JSONArray jList){
        int count = jList.length();
        ArrayList<TYPING_HIST> list = new ArrayList<>();
        TYPING_HIST info = null;

        for(int i=0; i<count;i++){
            try {
                info = getBoardComment((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private TYPING_HIST getBoardComment(JSONObject jInfo){
        String typing_cnt = ""; //	사경횟수	SMALLINT
        String typing_id = ""; //	사경아이디	CHARACTER
        String bupmunindex = ""; //	법문인덱스키	VARCHAR
        String paragraph_no= ""; //	문단번호	SMALLINT
        String chns_yn= ""; //	한자포함여부	CHARACTER
        String tasu= ""; //	타수	SMALLINT
        String regist_date= ""; //	사경일	CHARACTER
        String regist_time= ""; //	사경시간	CHARACTER

        try{
            typing_cnt = jInfo.getString("typing_cnt");
            typing_id = jInfo.optString("typing_id","").equals("null")?"":jInfo.optString("CONTENT","");
            bupmunindex = jInfo.getString("bupmunindex");
            paragraph_no = jInfo.getString("paragraph_no");
            chns_yn = jInfo.getString("chns_yn");
            tasu = jInfo.getString("tasu");
            regist_date = jInfo.getString("regist_date");
            regist_time = jInfo.getString("regist_time");

        }catch (JSONException e){
            e.printStackTrace();
        }
        TYPING_HIST info=new TYPING_HIST(Integer.parseInt(typing_cnt),typing_id,bupmunindex,
                Integer.parseInt(paragraph_no),chns_yn,Integer.parseInt(tasu),regist_date,regist_time);
        return info;
    }
}
