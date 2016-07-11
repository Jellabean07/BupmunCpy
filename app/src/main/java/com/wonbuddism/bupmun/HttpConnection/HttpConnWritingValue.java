package com.wonbuddism.bupmun.HttpConnection;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;
import com.wonbuddism.bupmun.Writing.HTTPconnection.HttpResultBupmun;
import com.wonbuddism.bupmun.Writing.TypingProcess.HttpConnWritingListener;

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

/**
 * Created by csc-pc on 2016. 7. 6..
 */
public class HttpConnWritingValue extends AsyncTask<Void,Void,Void> {

    private Activity activity;
    private String http_conection_url = "http://115.91.201.9/meditation";
    private String http_login_otp = "otp";
    private String responseResult;
    private String OTP;
    private String TAG = "HttpConnWritingValue";
    private HttpConnWritingListener listener;

    public HttpConnWritingValue(Activity activity) {
        this.activity = activity;
        this.OTP = new PrefUserInfoManager(this.activity).getOTP();
        Log.e("OTP", this.OTP);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData = http_login_otp + "=" + OTP;

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

                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLconn.getInputStream(), "UTF-8"));
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
        Log.e(TAG, "response : " + responseResult);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String responseCode = "";
        JSONObject resultData = null;
        JSONArray resulDataList = null;

        if (responseResult != null) {
            try {
                JSONObject jObj = new JSONObject(responseResult);
                responseCode = jObj.getString("code");
                //resultData = jObj.getJSONObject("resultData");
                resulDataList = jObj.getJSONArray("resultData");

            } catch (JSONException e) {
                Log.e(TAG, "JSON error :: " + e);
            }
        } else {
            responseCode = null;
        }
        Log.d(TAG, "response code :: " + responseCode);

        if (responseCode == null) {

            Log.e(TAG,"실패0");
        } else if (responseCode.contains("00")) {
            //  00 : 정상
            Log.e(TAG,"성공");
            HttpResultBupmun httpResult = null;
            for(HttpResultBupmun htb : getList(resulDataList)){
               httpResult = htb;
            }
            listener.WritingListener(httpResult);
            //HttpResultBupmun httpResult = getObject(resultData);
            Log.e(TAG,httpResult.toString());

        } else if (responseCode.contains("01")) {

            Log.e(TAG,"실패1");
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Log.e(TAG,"실패2");
            // 02 : 필수항목누락

        }

    }

    private ArrayList<HttpResultBupmun> getList(JSONArray jList){
        int count = jList.length();
        ArrayList<HttpResultBupmun> list = new ArrayList<>();
        HttpResultBupmun info = null;

        for(int i=0; i<count;i++){
            try {
                info = getObject((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }


    private HttpResultBupmun getObject(JSONObject jInfo){
        int TYPING_CNT = 0;  // int
        String TYPING_ID = ""; //0000014246
        String BUPMUNINDEX = ""; //jungjun020102
        int PARAGRAPH_NO = 0; // int
        int TASU = 0; // int
        String REGIST_DATE = ""; //20160707
        String REGIST_TIME = ""; //032021
        String TITLE1 = ""; //정전(正典)
        String TITLE2 = ""; //제2 교의편(敎義編)
        String TITLE3 = ""; //제1장 일원상(一圓相)
        String TITLE4 = ""; //제2절 일원상의 신앙(一圓相-信仰)
        String BUPMUNSORT = ""; //01020102
        String CONTENTS = "";
        String SHORTTITLE = ""; //일원상의 신앙
        int PARAGRAPH_CNT = 0; //int
        String CONTENTS_KOR = "";

        TYPING_CNT = jInfo.optInt("TYPING_CNT",0);
        TYPING_ID = jInfo.optString("TYPING_ID","");
        BUPMUNINDEX = jInfo.optString("BUPMUNINDEX","");
        PARAGRAPH_NO = jInfo.optInt("PARAGRAPH_NO", 0);
        TASU = jInfo.optInt("TASU", 0);
        REGIST_DATE = jInfo.optString("REGIST_DATE","0");
        REGIST_TIME = jInfo.optString("REGIST_TIME","0");
        TITLE1 = jInfo.optString("TITLE1","0");
        TITLE2 = jInfo.optString("TITLE2","0");
        TITLE3 = jInfo.optString("TITLE3","0");
        TITLE4 = jInfo.optString("TITLE4","0");
        BUPMUNSORT = jInfo.optString("BUPMUNSORT","0");
        CONTENTS = jInfo.optString("CONTENTS","0");
        SHORTTITLE = jInfo.optString("SHORTTITLE","0");
        PARAGRAPH_CNT = jInfo.optInt("PARAGRAPH_CNT", 0);
        CONTENTS_KOR = jInfo.optString("CONTENTS_KOR","0");


        HttpResultBupmun info=new HttpResultBupmun(TYPING_CNT,TYPING_ID,BUPMUNINDEX,PARAGRAPH_NO,
                TASU,REGIST_DATE,REGIST_TIME,TITLE1,TITLE2,TITLE3,TITLE4,BUPMUNSORT,CONTENTS,
                SHORTTITLE,PARAGRAPH_CNT,CONTENTS_KOR);
        return info;
    }

    public void setOnListener(HttpConnWritingListener listener) {
        this.listener = listener;
    }
}