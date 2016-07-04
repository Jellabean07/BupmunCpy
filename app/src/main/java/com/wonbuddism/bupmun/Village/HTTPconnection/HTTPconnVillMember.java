package com.wonbuddism.bupmun.Village.HTTPconnection;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;
import com.wonbuddism.bupmun.Village.VillageMemberDialog;
import com.wonbuddism.bupmun.Village.VillageMemberDialogAdapter;

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

public class HTTPconnVillMember extends AsyncTask<Void,Void,Void>{
    private Activity activity;
    private String TAG = "HTTPconnVillMember";
    private String http_conection_url = "http://115.91.201.9/vilage/member";
    private String http_otp ="otp";
    private String http_vil_id = "vil_id";
    private String http_page_no = "page_no";
    private String responseResult;
    private String OTP;
    private String VIL_ID;
    private String PAGE_NO;
    private ArrayList<VillageMember> villageMembers;
    private VillageMemberDialogAdapter adapter;


    public HTTPconnVillMember(Activity activity,VillageMemberDialogAdapter adapter, String vil_id, String page_no) {
        this.activity = activity;
        this.adapter = adapter;
        this.OTP= new PrefUserInfoManager(this.activity).getOTP();
        this.VIL_ID = vil_id;
        this.PAGE_NO = page_no;
        this.adapter.addFooter();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        String postData =  http_otp +"="+OTP +"&"+http_vil_id +"="+VIL_ID +"&"+http_page_no +"="+PAGE_NO;

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
       // ArrayList<VillageMember> villageMembers;
        VillageMember villageMember;
        adapter.removeFooter();
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
            Toast.makeText(activity, "불러오기에 실패하였습니다", Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("00")) {
            //  00 : 정상
            this.villageMembers = getVillageMemberList(resultData);


            for(VillageMember member : villageMembers)
            {
                adapter.add(member);
            }

            adapter.notifyDataSetChanged();
            // 로딩페이지 Activity Stack에서 제거
            //Toast.makeText(activity,"성공",Toast.LENGTH_SHORT).show();

        } else if (responseCode.contains("01")) {

            Toast.makeText(activity,"불러오기에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 01 : Method 오류


        } else if (responseCode.contains("02")) {
            Toast.makeText(activity,"불러오기에 실패하였습니다",Toast.LENGTH_SHORT).show();
            // 02 : 필수항목누락

        }

    }


    private ArrayList<VillageMember> getVillageMemberList(JSONArray jList){
        int count = jList.length();
        ArrayList<VillageMember> list = new ArrayList<>();
        VillageMember info = null;

        for(int i=0; i<count;i++){
            try {
                info = getVillageMember((JSONObject) jList.get(i));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private VillageMember getVillageMember(JSONObject jInfo){
        String USERID = ""; //아이디
        String NAME = ""; //속명
        String VIL_JOIN_DATE = ""; //동네가입일

        try{
            USERID = jInfo.getString("USERID");
            NAME = jInfo.getString("NAME");
            VIL_JOIN_DATE = jInfo.getString("VIL_JOIN_DATE");
        }catch (JSONException e){
            e.printStackTrace();
        }
        VillageMember info=new VillageMember(USERID,NAME,VIL_JOIN_DATE);
        return info;
    }
}
