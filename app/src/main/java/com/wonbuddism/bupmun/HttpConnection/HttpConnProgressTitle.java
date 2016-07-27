package com.wonbuddism.bupmun.HttpConnection;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.HttpParamProgress;
import com.wonbuddism.bupmun.DataVo.HttpResultProgress;
import com.wonbuddism.bupmun.Listener.ProgressReponseListener;
import com.wonbuddism.bupmun.Common.PrefUserInfoManager;
import com.wonbuddism.bupmun.Dialog.ProgressWaitDaialog;
import com.wonbuddism.bupmun.DataVo.HttpResultBupmun;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Writing.WritingMainActivity;

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

public class HttpConnProgressTitle extends AsyncTask<Void,Void,Void> {

    private Activity activity;
    private String http_conection_url = "meditation/titlelist";
    private String http_login_otp = "otp";
    private String http_index = "index" ;
    private String http_title_no = "title_no";
    private String http_ndepth = "ndepth";
    private String http_title = "title";


    private String responseResult;
    private String OTP;
    private String TAG = "HttpConnProgressTitle";
    private String http_host;

    private HttpParamProgress httpParam;
    private ProgressWaitDaialog daialog;
    private ProgressReponseListener listener;

    public HttpConnProgressTitle(Activity activity, HttpParamProgress httpParam) {
        this.activity = activity;
        this.OTP = new PrefUserInfoManager(this.activity).getOTP();
        this.httpParam = httpParam;
        Log.e("OTP", this.OTP);
        this.daialog = new ProgressWaitDaialog(this.activity);
        this.http_host = this.activity.getResources().getString(R.string.host_name);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.daialog.show();
    }


    @Override
    protected Void doInBackground(Void... params) {

        String postData = http_login_otp + "=" + OTP + "&"
                + http_index + "=" + httpParam.getIndex() + "&"
                + http_title_no + "=" + httpParam.getTitle_no() + "&"
                + http_ndepth + "=" + httpParam.getNdepth();

        if(Integer.parseInt(httpParam.getTitle_no())>0){
            for(int i = 0; i<httpParam.getTitle().size(); i++){
                postData += "&" + http_title + "[" + i + "]=" + httpParam.getTitle().get(i);
            }
        }

        Log.e(TAG, postData);

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
        this.daialog.dismiss();
        String responseCode = "";
        JSONArray resultData = null;


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

            Log.e(TAG,"실패0");
        } else if (responseCode.contains("00")) {
            //  00 : 정상
            Log.e(TAG, "성공");

            if(httpParam.getNdepth().equals("T")){
                ArrayList<HttpResultProgress> httpResult = getList(resultData);
                listener.HttpResponse(httpResult);
            }else{
                HttpResultBupmun httpResultBupmun = new HttpResultBupmun();
                for(HttpResultBupmun htb : getBupmunList(resultData)) {
                    httpResultBupmun = htb;
                }
                Log.e(TAG,"httpResultBupmun ? " + httpResultBupmun.toString());
                Intent intent = new Intent(activity, WritingMainActivity.class);
                intent.putExtra("httpResultBupmun",httpResultBupmun);
                activity.startActivity(intent);
                activity.setResult(1000);
                activity.finish();
            }


        } else if (responseCode.contains("01")) {

            Log.e(TAG,"실패1");
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Log.e(TAG,"실패2");
            // 02 : 필수항목누락

        }else if (responseCode.contains("03")) {
            Toast.makeText(activity, "로그인이 만료되었습니다", Toast.LENGTH_SHORT).show();
            // 03 : 로그인 만료
            new HttpConnLogout(activity).execute();

        }

    }

    private ArrayList<HttpResultProgress> getList(JSONArray jList){
        int count = jList.length();
        ArrayList<HttpResultProgress> list = new ArrayList<>();
        HttpResultProgress info = null;

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


    private HttpResultProgress getObject(JSONObject jInfo){
        String TITLE = "";
        String NDEPTH = "";
        String COMPLETE = "";

        TITLE = jInfo.optString("TITLE","");
        NDEPTH = jInfo.optString("NDEPTH","");
        COMPLETE = jInfo.optString("COMPLETE", "");


        HttpResultProgress info=new HttpResultProgress(TITLE,NDEPTH,COMPLETE);
        return info;
    }


    private ArrayList<HttpResultBupmun> getBupmunList(JSONArray jList){
        int count = jList.length();
        ArrayList<HttpResultBupmun> list = new ArrayList<>();
        HttpResultBupmun info = null;

        for(int i=0; i<count;i++){
            try {
                info = getBupmunObject((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }


    private HttpResultBupmun getBupmunObject(JSONObject jInfo){
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
        REGIST_DATE = jInfo.optString("REGIST_DATE","");
        REGIST_TIME = jInfo.optString("REGIST_TIME","");
        TITLE1 = replaceChinese(jInfo.optString("TITLE1", ""));
        TITLE2 = replaceChinese(jInfo.optString("TITLE2", ""));
        TITLE3 = replaceChinese(jInfo.optString("TITLE3", ""));
        TITLE4 = replaceChinese(jInfo.optString("TITLE4",""));
        BUPMUNSORT = jInfo.optString("BUPMUNSORT","");
        CONTENTS = jInfo.optString("CONTENTS","");
        SHORTTITLE = jInfo.optString("SHORTTITLE","");
        PARAGRAPH_CNT = jInfo.optInt("PARAGRAPH_CNT", 0);
        CONTENTS_KOR = replaceChinese(jInfo.optString("CONTENTS_KOR",""));

        if(TITLE3 ==null){
            TITLE3 = "";
        }

        if(TITLE4 == null){
            TITLE4 = "";
        }

        HttpResultBupmun info=new HttpResultBupmun(TYPING_CNT,TYPING_ID,BUPMUNINDEX,PARAGRAPH_NO,
                TASU,REGIST_DATE,REGIST_TIME,TITLE1,TITLE2,TITLE3,TITLE4,BUPMUNSORT,CONTENTS,
                SHORTTITLE,PARAGRAPH_CNT,CONTENTS_KOR);
        return info;
    }

    public String replaceChinese(String words){
        String regex = "\\((.*?)\\)";
        return words.replaceAll(regex, "");
    }



    public void setOnResponseListener(ProgressReponseListener listener) {
        this.listener = listener;
    }


}