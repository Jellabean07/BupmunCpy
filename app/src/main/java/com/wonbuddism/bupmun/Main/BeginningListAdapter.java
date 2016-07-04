package com.wonbuddism.bupmun.Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wonbuddism.bupmun.R;

import java.util.ArrayList;

/**
 * Created by user on 2015-12-22.
 */
public class BeginningListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<String> mList;

    public BeginningListAdapter(Context context,ArrayList<String> list) {
        this.context = context;
        this.mList = list;
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
        CustomHolder    holder  = null;

        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_beginning_list, parent, false);
            text = (TextView) convertView.findViewById(R.id.list_item_textview);
            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.m_TextView = text;
            convertView.setTag(holder);
        }else{
            holder  = (CustomHolder) convertView.getTag();
            text    = holder.m_TextView;

        }
        text.setText(mList.get(position));

        return convertView;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(String _msg) {
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
    }
}
