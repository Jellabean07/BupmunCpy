package com.wonbuddism.bupmun.Dialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wonbuddism.bupmun.DataVo.FeelingMemo;
import com.wonbuddism.bupmun.R;

import java.util.ArrayList;

/**
 * Created by user on 2016-01-21.
 */
public class WritingFeelingDialogAdapter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private ArrayList<FeelingMemo> mList;
    private WritingFeelingDialog dialog;
    public WritingFeelingDialogAdapter(Context context,Activity activity, ArrayList<FeelingMemo> list, WritingFeelingDialog dialog) {
        this.context = context;
        this.activity = activity;
        this.mList = list;
        this.dialog=dialog;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public FeelingMemo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView title = null;
        TextView date = null;
        CustomHolder    holder  = null;

        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_dialog_writing_feeling_list, parent, false);

            title = (TextView) convertView.findViewById(R.id.item_dialog_writing_feeling_list_title_textview);
            date =  (TextView)convertView.findViewById(R.id.item_dialog_writing_feeling_list_date_textview);
            holder = new CustomHolder();
            holder.title =  title;
            holder.date = date;
            convertView.setTag(holder);
        }else {
            holder   = (CustomHolder) convertView.getTag();
            title    = holder.title;
            date     = holder.date;
        }

        title.setText(mList.get(position).getMemo_contents());
        date.setText(mList.get(position).getRegist_date().substring(0,10));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new WritingFeelingContentDialog(context,activity,mList.get(position)).show();

            }
        });


        return convertView;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(FeelingMemo _msg) {
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
        TextView title;
        TextView date;
    }
}
