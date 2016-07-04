package com.wonbuddism.bupmun.Writing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wonbuddism.bupmun.R;

import java.util.ArrayList;

public class WritingImportDialogAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<WritingImportItem> mList;

    public WritingImportDialogAdapter(Context context,ArrayList<WritingImportItem> list) {

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView title = null;
        TextView shorttitle = null;
        TextView count = null;
        TextView para = null;
        CustomHolder    holder  = null;

        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_dialog_writing_import, parent, false);

            title = (TextView) convertView.findViewById(R.id.item_dialog_writing_import_title_textview);
            shorttitle =  (TextView)convertView.findViewById(R.id.item_dialog_writing_import_shorttitle_textview);
            count = (TextView)convertView.findViewById(R.id.item_dialog_writing_import_count_textview);
            para = (TextView)convertView.findViewById(R.id.item_dialog_writing_import_para_textview);

            holder = new CustomHolder();
            holder.title =  title;
            holder.shorttitle = shorttitle;
            holder.count = count;
            holder.para = para;
            convertView.setTag(holder);
        }else {
            holder   = (CustomHolder) convertView.getTag();
            title    = holder.title;
            shorttitle    = holder.shorttitle;
            count    = holder.count;
            para     = holder.para;
        }

        title.setText(mList.get(position).getTitle());
        shorttitle.setText(mList.get(position).getShorttitle());
        count.setText(mList.get(position).getCount() + "");
        para.setText(mList.get(position).getPara() + "");


        return convertView;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(WritingImportItem _msg) {
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
        TextView shorttitle;
        TextView count;
        TextView para;
    }
}
