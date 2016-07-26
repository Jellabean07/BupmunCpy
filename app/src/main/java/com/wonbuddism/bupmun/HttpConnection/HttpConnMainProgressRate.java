package com.wonbuddism.bupmun.HttpConnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.HttpResultProgressRate;
import com.wonbuddism.bupmun.Dialog.ProgressWaitDaialog;
import com.wonbuddism.bupmun.Listener.MainProgressRateListener;
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

/**
 * Created by csc-pc on 2016. 7. 26..
 */
public class HttpConnMainProgressRate extends AsyncTask<Void,Void,Void> {

    private Activity activity;
    private String http_conection_url = "http://115.91.201.9/meditation/exp";
    private String http_login_otp = "otp";
    private String responseResult;
    private String OTP;
    private String TAG = "HttpConnMainProgressRate";

    private MainProgressRateListener listener;
    private ProgressWaitDaialog daialog;

    public HttpConnMainProgressRate(Activity activity) {
        this.activity = activity;
        this.OTP = new PrefUserInfoManager(this.activity).getOTP();
        Log.e("OTP", this.OTP);
        this.daialog = new ProgressWaitDaialog(this.activity);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        daialog.show();
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
        JSONArray resultData = null;
        this.daialog.dismiss();
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
            Log.e(TAG,"성공");
            HttpResultProgressRate httpResult = null;
            for(HttpResultProgressRate hr : getList(resultData)){
                httpResult = hr;
            }
            this.listener.progressRate(httpResult);
            Log.e(TAG,httpResult.toString());

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

    private ArrayList<HttpResultProgressRate> getList(JSONArray jList){
        int count = jList.length();
        ArrayList<HttpResultProgressRate> list = new ArrayList<>();
        HttpResultProgressRate info = null;

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

    private HttpResultProgressRate getObject(JSONObject jInfo){
        int EXP = 0;
        int TOTALEXP = 0;

        EXP = jInfo.optInt("EXP",0);
        TOTALEXP = jInfo.optInt("TOTALEXP", 0);

        HttpResultProgressRate info=new HttpResultProgressRate(EXP,TOTALEXP);
        return info;
    }

    public void setProgressRateListener(MainProgressRateListener listener){
        this.listener = listener;
    }

}
