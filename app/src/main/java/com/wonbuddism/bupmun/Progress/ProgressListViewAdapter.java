package com.wonbuddism.bupmun.Progress;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonbuddism.bupmun.DataVo.HttpParamProgress;
import com.wonbuddism.bupmun.DataVo.HttpResultProgress;
import com.wonbuddism.bupmun.HttpConnection.HttpConnProgressTitle;
import com.wonbuddism.bupmun.Listener.ProgressNextListener;
import com.wonbuddism.bupmun.R;

import java.util.ArrayList;

/**
 * Created by csc-pc on 2016. 7. 25..
 */
public class ProgressListViewAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HttpResultProgress> mList;
    private HttpParamProgress httpParam;
    private String TAG = "ProgressListViewAdapter";
    private ProgressNextListener listener;


    public ProgressListViewAdapter(Activity activity, ArrayList<HttpResultProgress> mList) {
        this.activity = activity;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text    = null;
        LinearLayout lL = null;
        CustomHolder holder  = null;
        final int pos = position;

        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_progress_detail, parent, false);
            text = (TextView) convertView.findViewById(R.id.item_progress_detail_textview);
            lL = (LinearLayout)convertView.findViewById(R.id.item_progress_detail_linearlayout);
            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.m_TextView = text;
            holder.mLinearLayout = lL;
            convertView.setTag(holder);
        }else{
            holder  = (CustomHolder) convertView.getTag();
            text    = holder.m_TextView;
            lL = holder.mLinearLayout;

        }
        text.setText(mList.get(pos).getTitle());
        String complete = mList.get(pos).getComplete();
        if (complete.equals("NONE")) {
            text.setTextColor(Color.parseColor("#EC008C"));
            Log.e(TAG, "COMPLETE : NONE");
        } else if(complete.equals("ING")){
            text.setTextColor(Color.parseColor("#00AEFF"));
            Log.e(TAG, "COMPLETE : ING");
        } else {
            text.setTextColor(Color.parseColor("#9A9A9A"));
            Log.e(TAG, "COMPLETE : COMPLETE");
        }

        lL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpParam.getTitle().add(mList.get(pos).getTitle());
                httpParam.setNdepth(mList.get(pos).getNdepth());
                int title_no = Integer.parseInt(httpParam.getTitle_no()) + 1;
                httpParam.setTitle_no(String.valueOf(title_no));
                Log.e(TAG, httpParam.toString());

                if (mList.get(pos).getNdepth().equals("T")) {
                    Log.e(TAG, "depth : T");
                    listener.nextProgress(httpParam);
                } else {
                    Log.e(TAG, "depth : F");
                    new HttpConnProgressTitle(activity,httpParam).execute();
                }

            }
        });





        return convertView;
    }


    public void setNextProgressListenter(ProgressNextListener listener){
        this.listener = listener;
    }

    public void setParameter(HttpParamProgress httpParam){
        this.httpParam = httpParam;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(HttpResultProgress _msg) {
        mList.add(_msg);
    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        mList.remove(_position);
    }


    //외부에서 아이템 초기화 요청
    public void clear(){
        mList.clear();
    }

    private class CustomHolder {
        TextView    m_TextView;
        LinearLayout mLinearLayout;
    }
}
