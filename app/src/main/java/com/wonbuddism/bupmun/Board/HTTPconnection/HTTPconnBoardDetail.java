package com.wonbuddism.bupmun.Board.HTTPconnection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.Board.BoardDetailActivity;
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

public class HTTPconnBoardDetail extends AsyncTask<Void,Void,Void>{


    private Activity activity;
    private String TAG = "HTTPconnBoardDetail";
    private String http_conection_url = "http://115.91.201.9/board/file/down";
    private String http_otp ="otp";
    private String http_boardno ="boardno";
    private String http_writeno ="writeno";
    private String responseResult;
    private String OTP;
    private String BOARDNO;
    private String WRITENO;


    public HTTPconnBoardDetail(Activity activity,String boardno, String writeno) {
        this.activity = activity;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.BOARDNO = boardno;
        this.WRITENO = writeno;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP + "&" +http_boardno +"="+BOARDNO + "&" +http_writeno +"="+WRITENO;

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
        JSONArray resultData = null;
        BoardDetail boardDetail = new BoardDetail();


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
            Toast.makeText(activity, "연결에 실패하였습니다", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상
            try {
                boardDetail = getBoardDetail(resultData.getJSONObject(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(activity, BoardDetailActivity.class);
            intent.putExtra("boardDetail",boardDetail);
            activity.startActivityForResult(intent,1000);
            // 로딩페이지 Activity Stack에서 제거
           // Toast.makeText(activity,"성공",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {

            Toast.makeText(activity, "연결에 실패하였습니다", Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity, "연결에 실패하였습니다", Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }

    }

    private ArrayList<BoardDetail> getBoardDetatilList(JSONArray jList){
        int count = jList.length();
        ArrayList<BoardDetail> list = new ArrayList<>();
        BoardDetail info = null;

        for(int i=0; i<count;i++){
            try {
                info = getBoardDetail((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private BoardDetail getBoardDetail(JSONObject jInfo){
        String BOARDNO = ""; //게시판번호
        String WRITENO = ""; //글번호
        String TITLE = ""; //제목
        String READCNT = ""; //조회수
        String REPLYDEPTH = ""; //답글개수
        String CONTENT = ""; //내용
        String USERID = ""; //작성자아이디
        String WRITETIME = ""; //작성일시

        try{
            BOARDNO = jInfo.getString("BOARDNO");
            WRITENO = jInfo.getString("WRITENO");
            TITLE = jInfo.getString("TITLE");
            READCNT = jInfo.getString("READCNT");
            REPLYDEPTH = jInfo.getString("REPLYDEPTH");
            CONTENT = jInfo.optString("CONTENT","").equals("null")?"":jInfo.optString("CONTENT","");
            USERID = jInfo.getString("USERID");
            WRITETIME = jInfo.getString("WRITETIME");
        }catch (JSONException e){
            e.printStackTrace();
        }
        BoardDetail info=new BoardDetail(BOARDNO,WRITENO,TITLE,READCNT,REPLYDEPTH,CONTENT,USERID,WRITETIME);
        return info;
    }
}
