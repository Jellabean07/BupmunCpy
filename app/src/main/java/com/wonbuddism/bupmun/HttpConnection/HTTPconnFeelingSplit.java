package com.wonbuddism.bupmun.HttpConnection;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.FeelingMemo;
import com.wonbuddism.bupmun.Common.PrefUserInfoManager;
import com.wonbuddism.bupmun.Dialog.WritingFeelingDialogAdapter;
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

public class HTTPconnFeelingSplit extends AsyncTask<Void,Void,Void>{
    private Activity activity;
    private String TAG = "HTTPconnFeelingSplit";
    private String http_conection_url = "memo/detail";
    private String http_otp ="otp";
    private String http_index = "index";
    private String http_page_no = "page_no";
    private String responseResult;
    private String OTP;
    private String INDEX;
    private String PAGE_NO;
    private String http_host;
    private WritingFeelingDialogAdapter adapter;


    public HTTPconnFeelingSplit(Activity activity, WritingFeelingDialogAdapter adapter, String index, String page_no) {
        this.activity = activity;
        this.adapter = adapter;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.INDEX = index;
        this.PAGE_NO = page_no;
        this.http_host = this.activity.getResources().getString(R.string.host_name);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP + "&" + http_index+"="+INDEX
                + "&" + http_page_no+"="+PAGE_NO ;

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
        ArrayList<FeelingMemo> feelingMemos;

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
            Toast.makeText(activity, "감각감상 불러오기에 실패하였습니다", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상
            feelingMemos = getFeelingMemoList(resultData);

            for(FeelingMemo memo : feelingMemos)
            {
                adapter.add(memo);
            }

            adapter.notifyDataSetChanged();

            // 로딩페이지 Activity Stack에서 제거
          //  Toast.makeText(activity,"성공",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {

            Toast.makeText(activity,"감각감상 불러오기에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity,"감각감상 불러오기에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        } else if (responseCode.contains("03")) {
            Toast.makeText(activity,"로그인이 만료되었습니다",Toast.LENGTH_SHORT).show();
            // 03 : 로그인 만료
            new HttpConnLogout(activity).execute();

        }

    }

    private ArrayList<FeelingMemo> getFeelingMemoList(JSONArray jList){
        int count = jList.length();
        ArrayList<FeelingMemo> list = new ArrayList<>();
        FeelingMemo info = null;

        for(int i=0; i<count;i++){
            try {
                info = getFeelingMemo((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public String replaceChinese(String words){
        String regex = "\\((.*?)\\)";
        return words.replaceAll(regex, "");
    }

    private FeelingMemo getFeelingMemo(JSONObject jInfo){
        String BUPMUNINDEX = this.INDEX; //법문인덱스키,
        String TITLE = ""; //법문타이틀,
        String SHOART_TITLE = ""; //법문
        String MEMO_SEQ = ""; //메모인덱스번호,
        String MEMO_CONTENTS = ""; //메모내용,
        String REGIST_DATE = ""; //메모등록일

        try{
            TITLE = replaceChinese(jInfo.getString("TITLE"));
            SHOART_TITLE = replaceChinese(jInfo.getString("SHORT_TITLE"));
            MEMO_SEQ = jInfo.getString("MEMO_SEQ");
            MEMO_CONTENTS = jInfo.getString("MEMO_CONTENTS");
            REGIST_DATE = jInfo.getString("REGIST_DATE");
        }catch (JSONException e){
            e.printStackTrace();
        }
        FeelingMemo info=new FeelingMemo(BUPMUNINDEX,TITLE,SHOART_TITLE,MEMO_SEQ,MEMO_CONTENTS,REGIST_DATE);
        return info;
    }
}
