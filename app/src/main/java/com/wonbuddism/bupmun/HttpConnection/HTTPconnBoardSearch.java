package com.wonbuddism.bupmun.HttpConnection;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.wonbuddism.bupmun.Board.BoardArticleListViewAdapter;
import com.wonbuddism.bupmun.DataVo.BoardArticle;
import com.wonbuddism.bupmun.Common.PrefUserInfoManager;
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

public class HTTPconnBoardSearch extends AsyncTask<Void,Void,Void>{
    private Activity activity;
    private String TAG = "HTTPconnBoardMainAll";
    private String http_conection_url = "board/search";
    private String http_otp ="otp";
    private String http_boardno = "boardno";
    private String http_keyword = "keyword";
    private String http_category = "category";
    private String http_page_no = "page_no";
    private String responseResult;
    private String OTP;
    private String BOARD_NO;
    private String PAGE_NO;
    private String KEWORD;
    private String CATEGORY;
    private BoardArticleListViewAdapter adapter;
    private TextView total;
    private String http_host;

    /*00 : 제목
    01 : 내용
    02 : 제목+내용
    03 : 작성자*/

    public HTTPconnBoardSearch(Activity activity,BoardArticleListViewAdapter adapter,
                               TextView total, String boardno,String keyword, String page_no, String category) {
        this.activity = activity;
        this.adapter = adapter;
        this.total = total;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.BOARD_NO = boardno;
        this.KEWORD = keyword;
        this.CATEGORY = category;
        this.PAGE_NO = page_no;
        this.adapter.addFooter();
        this.http_host = this.activity.getResources().getString(R.string.host_name);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP + "&" + http_boardno +"="+BOARD_NO
                + "&" + http_page_no +"="+PAGE_NO + "&" + http_keyword +"="+KEWORD
                + "&" + http_category +"="+CATEGORY;


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
        JSONArray resultData1 = null;
        JSONArray resultData2 = null;
        ArrayList<BoardArticle> boardArticles;
        adapter.removeFooter();
        if (responseResult != null) {
            try {
                JSONObject jObj = new JSONObject(responseResult);
                responseCode = jObj.getString("code");
                resultData1 = jObj.getJSONArray("resultData1");
                resultData2 = jObj.getJSONArray("resultData2");

            } catch (JSONException e) {
                Log.e(TAG, "JSON error :: " + e);
            }
        } else {
            responseCode = null;
        }
        Log.d(TAG, "response code :: " + responseCode);

        if (responseCode == null) {
            Toast.makeText(activity, "서버연결에 실패하였습니다", Toast.LENGTH_SHORT).show();
            total.setText("0");

        } else if (responseCode.contains("00")) {
            //  00 : 정상
            try {
                String total_cnt = resultData1.getJSONObject(0).optString("TOTAL_CNT");
                total.setText(total_cnt);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            boardArticles = getBoardArticleList(resultData2);

            for(BoardArticle article : boardArticles)
            {
                adapter.add(article);
            }

            adapter.notifyDataSetChanged();

            if(total.getText().toString().equals("0")){
                Toast.makeText(activity,"검색결과가 없습니다",Toast.LENGTH_SHORT).show();
            }


            // 로딩페이지 Activity Stack에서 제거
            //Toast.makeText(activity,"성공",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {
            total.setText("0");
            Toast.makeText(activity,"불러오기에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            total.setText("0");
            Toast.makeText(activity,"불러오기에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        } else if (responseCode.contains("03")) {
            total.setText("0");
            Toast.makeText(activity,"검색결과가 없습니다",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        } else if (responseCode.contains("03")) {
            Toast.makeText(activity,"로그인이 만료되었습니다",Toast.LENGTH_SHORT).show();
            // 03 : 로그인 만료
            new HttpConnLogout(activity).execute();

        }
    }

    private ArrayList<BoardArticle> getBoardArticleList(JSONArray jList){
        if(jList.isNull(0)){
            Log.e("resultData2","null");
        }
        int count = jList.length();
        ArrayList<BoardArticle> list = new ArrayList<>();
        BoardArticle info = null;

        for(int i=0; i<count;i++){
            try {
                info = getBoardArticle((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private BoardArticle getBoardArticle(JSONObject jInfo){
        String BOARDNO = ""; //게시판번호
        String WRITENO = ""; //글번호
        String TITLE = ""; //제목
        String READCNT = ""; //조회수
        String PARENTWRITENO = ""; //부모 글번호
        String FOUNDERWRITENO = ""; //묶음 글번호
        String REPLYDEPTH = ""; //답글순서
        String REPLYORDER = ""; //답글순서
        String USERID = ""; //작성자아이디
        String WRITETIME = ""; //작성일시

        try{
            BOARDNO = jInfo.getString("BOARDNO");
            WRITENO = jInfo.getString("WRITENO");
            TITLE = jInfo.getString("TITLE");
            READCNT = jInfo.getString("READCNT");
            PARENTWRITENO = jInfo.getString("PARENTWRITENO");
            FOUNDERWRITENO = jInfo.getString("FOUNDERWRITENO");
            REPLYDEPTH = jInfo.getString("REPLYDEPTH");
            REPLYORDER = jInfo.getString("REPLYORDER");
            USERID = jInfo.getString("USERID");
            WRITETIME = jInfo.getString("WRITETIME");
        }catch (JSONException e){
            e.printStackTrace();
        }
        BoardArticle info=new BoardArticle(BOARDNO,WRITENO,TITLE,READCNT,PARENTWRITENO,FOUNDERWRITENO,REPLYDEPTH,REPLYORDER,USERID,WRITETIME,false);
        return info;
    }
}
