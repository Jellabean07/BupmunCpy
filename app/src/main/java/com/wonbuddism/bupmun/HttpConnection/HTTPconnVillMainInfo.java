package com.wonbuddism.bupmun.HttpConnection;


import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.VillageMainInfo;
import com.wonbuddism.bupmun.Common.PrefUserInfoManager;
import com.wonbuddism.bupmun.Dialog.VillageJoinListDaialog;
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

public class HTTPconnVillMainInfo extends AsyncTask<Void,Void,Void>{

    private Activity activity;
    private String http_conection_url = "vilage";
    private String http_login_otp="otp";
    private String responseResult;
    private String OTP;
    private String TAG = "HTTPconnVillMainInfo";
    private Dialog dialog;
    private String http_host;

    public HTTPconnVillMainInfo(Activity activity, Dialog dialog) {
        this.activity = activity;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.dialog = dialog;
        Log.e("OTP",this.OTP);
        this.http_host = this.activity.getResources().getString(R.string.host_name);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_login_otp+"="+OTP;

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
        VillageMainInfo villageMainInfo;
        ArrayList<VillageMainInfo> villageMainInfos;
        this.dialog.dismiss();
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
            Toast.makeText(activity, "동네방네 목록을 가져오지 못했습니다", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상
            villageMainInfos = getVillageCommentsList(resultData);
            Dialog dialogGO = new VillageJoinListDaialog(activity,villageMainInfos);
            dialogGO.show();
            //activity.finish();

            /*
            villageMainInfo = villageMainInfos.get(0);
            Log.e("villageMainInfo",villageMainInfo.toString());
                    Intent intent = new Intent(activity, VillageMainActivity.class);
            intent.putExtra("villageMainInfo", villageMainInfo);
            activity.startActivity(intent);*/

            // 로딩페이지 Activity Stack에서 제거
          //  Toast.makeText(activity,"성공",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {

            Toast.makeText(activity,"동네방네 목록을 가져오지 못했습니다",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity,"동네방네 목록을 가져오지 못했습니다",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }else if (responseCode.contains("03")) {
            Toast.makeText(activity,"로그인이 만료되었습니다",Toast.LENGTH_SHORT).show();
            // 03 : 로그인 만료
            new HttpConnLogout(activity).execute();

        }

    }

    private ArrayList<VillageMainInfo> getVillageCommentsList(JSONArray jList){
        int count = jList.length();
        ArrayList<VillageMainInfo> list = new ArrayList<>();
        VillageMainInfo info = null;

        for(int i=0; i<count;i++){
            try {
                info = getVillageMainInfo((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }


    private VillageMainInfo getVillageMainInfo(JSONObject jInfo){
        String vil_manager = ""; //	   동네운영자이름
        String vil_name= ""; //	   동네이름
        String vil_intro= ""; //	   동네소개
        String vil_open_date= ""; //	   동네개설일
        String vil_join_date= ""; //	   동네가입일
        String vil_user_count= ""; //   동네회원수
        String vil_rank= ""; //
        String vil_id = "";

        try{
            vil_manager = jInfo.getString("VIL_MANAGER");
            vil_name = jInfo.getString("VIL_NAME");
            vil_intro = jInfo.getString("VIL_INTRO");
            vil_open_date = jInfo.getString("VIL_OPEN_DATE").substring(0, 10);
            vil_join_date = jInfo.getString("VIL_JOIN_DATE").substring(0,10);
            vil_user_count = jInfo.getString("VIL_USER_COUNT");
            vil_rank = jInfo.getString("VIL_RANK");
            vil_id = jInfo.getString("VIL_ID");
        }catch (JSONException e){
            e.printStackTrace();
        }
        VillageMainInfo info=new VillageMainInfo(vil_manager,vil_name,vil_intro,
                vil_open_date,vil_join_date,vil_user_count,
                vil_rank,vil_id);
        return info;
    }
}
