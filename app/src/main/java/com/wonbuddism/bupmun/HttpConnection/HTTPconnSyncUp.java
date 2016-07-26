package com.wonbuddism.bupmun.HttpConnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.HttpParamBupmun;
import com.wonbuddism.bupmun.Common.PrefUserInfoManager;
import com.wonbuddism.bupmun.Dialog.ProgressWaitDaialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPconnSyncUp extends AsyncTask<Void,Void,Void> {
    private Activity activity;
    private String TAG = "HTTPconnSyncUp";
    private String http_conection_url = "http://115.91.201.9/sync/select";
    private String http_otp ="otp";
    private String http_paragraph_no = "paragraph_no";
    private String http_tasu = "tasu";
    private String http_index = "index";
    private String responseResult;
    private String OTP;
    private String PARAGRAPH_NO;
    private String TASU;
    private String INDEX;
    private ProgressWaitDaialog daialog;
   /* paragraph_no (varchar)
    tasu (varchar)
    index (varchar)*/


    public HTTPconnSyncUp(Activity activity, HttpParamBupmun hpb) {
        this.activity = activity;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.PARAGRAPH_NO = String.valueOf(hpb.getPARAGRAPH_NO());
        this.TASU = String.valueOf(hpb.getTASU());
        this.INDEX = hpb.getBupmunindex();
        this.daialog = new ProgressWaitDaialog(this.activity);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        daialog.show();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP+"&"+http_paragraph_no +"="+PARAGRAPH_NO
                +"&"+http_tasu +"="+TASU +"&"+http_index +"="+INDEX;

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
        String resultData = "";
        daialog.dismiss();

        if (responseResult != null) {
            try {
                JSONObject jObj = new JSONObject(responseResult);
                responseCode = jObj.getString("code");
                resultData = jObj.getString("resultData");
            } catch (JSONException e) {
                Log.e(TAG, "JSON error :: " + e);
            }
        } else {
            responseCode = null;
        }
        Log.d(TAG, "response code :: " + responseCode);

        if (responseCode == null) {
            Toast.makeText(activity, "사경작성에 실패하였습니다", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상

         // Toast.makeText(activity,"사경성공",Toast.LENGTH_SHORT).show();
            // resultdata; -> 성공여부
            //Toast.makeText(activity,"오류신고가 정상적으로 처리 되었습니다",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {

            Toast.makeText(activity,"사경작성에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity,"사경작성에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }else if (responseCode.contains("03")) {
            Toast.makeText(activity,"로그인이 만료되었습니다",Toast.LENGTH_SHORT).show();
            // 03 : 로그인 만료
            new HttpConnLogout(activity).execute();

        }else{
            Toast.makeText(activity,"사경작성에 실패하였습니다",Toast.LENGTH_SHORT).show();
        }

    }
}
