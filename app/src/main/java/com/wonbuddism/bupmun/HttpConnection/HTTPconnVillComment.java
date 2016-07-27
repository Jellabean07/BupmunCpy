package com.wonbuddism.bupmun.HttpConnection;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.VillageComments;
import com.wonbuddism.bupmun.Common.PrefUserInfoManager;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Vil.VillageRecycleViewAdapter;

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

public class HTTPconnVillComment extends AsyncTask<Void,Void,Void> {
    private Activity activity;
    private String TAG = "HTTPconnVillComment";
    private String http_conection_url = "comment";
    private String http_otp ="otp";
    private String http_vil_id = "vil_id";
    private String http_page_no = "page_no";
    private String responseResult;
    private String OTP;
    private String VIL_ID;
    private String PAGE_NO;
    private String http_host;
    private VillageRecycleViewAdapter adapter;
    private ArrayList<VillageComments> villageCommentses;


    public HTTPconnVillComment(Activity activity,VillageRecycleViewAdapter adapter, String vil_id, String page_no) {
        this.adapter = adapter;
        this.activity = activity;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.VIL_ID = vil_id;
        this.PAGE_NO = page_no;
        this.http_host = this.activity.getResources().getString(R.string.host_name);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP +"&"+http_vil_id +"="+VIL_ID +"&"+http_page_no +"="+PAGE_NO;


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
       // ArrayList<VillageComments> villageCommentses;

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
          //  Toast.makeText(activity, "실패00", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상
            this.villageCommentses = getVillageCommentsList(resultData);
            for(VillageComments comments : villageCommentses)
            {
                adapter.add(comments);
            }
            adapter.showLoading(false);
            adapter.notifyDataSetChanged();

            // 로딩페이지 Activity Stack에서 제거
          //  Toast.makeText(activity,"성공",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {

           // Toast.makeText(activity,"실패01",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
          //  Toast.makeText(activity,"실패02",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }else if (responseCode.contains("03")) {
            Toast.makeText(activity, "로그인이 만료되었습니다", Toast.LENGTH_SHORT).show();
            // 03 : 로그인 만료
            new HttpConnLogout(activity).execute();

        }

    }

    private ArrayList<VillageComments> getVillageCommentsList(JSONArray jList){
        int count = jList.length();
        ArrayList<VillageComments> list = new ArrayList<>();
        VillageComments info = null;

        for(int i=0; i<count;i++){
            try {
                info = getVillageComments((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private VillageComments getVillageComments(JSONObject jInfo){
        String COMMENT_NO = ""; //글번호
        String USERID = ""; //사경아이디
        String COMMENT_NAME = ""; //글쓴이 이름
        String COMMENT_CONTENTS = ""; //내용
        String COMMENT_DATE = ""; //글쓴날짜

        try{
            COMMENT_NO = jInfo.getString("COMMENT_NO");
            USERID = jInfo.getString("USERID");
            COMMENT_NAME = jInfo.getString("COMMENT_NAME");
            COMMENT_CONTENTS = jInfo.getString("COMMENT_CONTENTS");
            COMMENT_DATE = jInfo.getString("COMMENT_DATE");
        }catch (JSONException e){
            e.printStackTrace();
        }
        VillageComments info=new VillageComments(COMMENT_NO,USERID,COMMENT_NAME,COMMENT_CONTENTS,COMMENT_DATE);
        return info;
    }
}
