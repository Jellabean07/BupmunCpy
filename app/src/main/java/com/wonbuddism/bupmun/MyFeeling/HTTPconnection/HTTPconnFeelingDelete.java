package com.wonbuddism.bupmun.MyFeeling.HTTPconnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HTTPconnFeelingDelete extends AsyncTask<Void,Void,Void> {

    private Activity activity;
    private String TAG = "HTTPconnFeelingDelete";
    private String http_conection_url = "http://115.91.201.9/memo/remove";
    private String http_otp ="otp";
    private String http_index = "index";
    private String http_memo_seq = "memo_seq";
    private String responseResult;
    private String OTP;
    private String INDEX;
    private String MEMO_SEQ;


    public HTTPconnFeelingDelete(Activity activity,String index, String memo_seq) {
        this.activity = activity;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.INDEX = index;
        this.MEMO_SEQ = memo_seq;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP + "&"+ http_index +"="+ INDEX
                + "&"+http_memo_seq+"="+MEMO_SEQ;

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
            Toast.makeText(activity, "감각감상 삭제에 실패하였습니다", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상

            // resultdata; -> 성공여부
            Toast.makeText(activity,"감각감상이 삭제 되었습니다",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {

            Toast.makeText(activity,"감각감상 삭제에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity,"감각감상 삭제에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }

    }
}
