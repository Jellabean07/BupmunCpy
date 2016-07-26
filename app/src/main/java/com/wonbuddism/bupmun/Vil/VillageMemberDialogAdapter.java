package com.wonbuddism.bupmun.Vil;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Common.ListViewFooterManager;
import com.wonbuddism.bupmun.DataVo.VillageMember;

import java.util.ArrayList;

/**
 * Created by user on 2016-01-25.
 */
public class VillageMemberDialogAdapter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private ArrayList<VillageMember> mList;
    private ListViewFooterManager footerManager;

    public VillageMemberDialogAdapter(Activity activity,ArrayList<VillageMember> list, ListView lv) {
        this.activity = activity;
        this.mList = list;
        footerManager = new ListViewFooterManager(activity,lv);
    }

    public void removeFooter(){
        footerManager.removeFooter();
    }

    public void addFooter(){
        footerManager.addFooter();
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

        TextView name = null;
        TextView id    = null;
        TextView date = null;
        CustomHolder    holder  = null;

        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_dialog_village_member, parent, false);

            name = (TextView) convertView.findViewById(R.id.item_dialog_village_member_name_textview);
            id = (TextView) convertView.findViewById(R.id.item_dialog_village_member_id_textview);
            date =  (TextView)convertView.findViewById(R.id.item_dialog_village_member_join_textview);
            holder = new CustomHolder();
            holder.name =  name;
            holder.id = id;
            holder.date = date;
            convertView.setTag(holder);
        }else {
            holder   = (CustomHolder) convertView.getTag();
            name    = holder.name;
            id      = holder.id;
            date     = holder.date;
        }

        name.setText(mList.get(position).getName());
        id.setText("("+mList.get(position).getUserid()+")");
        date.setText(mList.get(position).getVil_join_date().substring(0,10));


        return convertView;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(VillageMember _msg) {
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
        TextView name;
        TextView id;
        TextView date;
    }
}
