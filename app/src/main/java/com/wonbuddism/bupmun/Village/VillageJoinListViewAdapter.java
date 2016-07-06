package com.wonbuddism.bupmun.Village;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.ProgressWaitDaialog;
import com.wonbuddism.bupmun.DataVo.VillageMainInfo;

import java.util.ArrayList;
import java.util.List;

public class VillageJoinListViewAdapter extends BaseAdapter{
    private Context context;
    private Activity activity;
    private ArrayList<VillageMainInfo> mList;
    private Dialog dialog;


    public VillageJoinListViewAdapter(Activity activity,Dialog dialog, ArrayList<VillageMainInfo> list) {
        this.activity = activity;
        this.dialog = dialog;
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

        TextView name = null;

        CustomHolder    holder  = null;

        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_dialog_village_join, parent, false);

            name = (TextView) convertView.findViewById(R.id.item_dialog_village_join_name_textview);

            holder = new CustomHolder();
            holder.name =  name;

            convertView.setTag(holder);
        }else {
            holder   = (CustomHolder) convertView.getTag();
            name    = holder.name;

        }

        name.setText(mList.get(position).getVil_name());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                final Dialog dialogWait = new ProgressWaitDaialog(activity);
                dialogWait.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogWait.dismiss();
                        Intent intent = new Intent(activity, VillageMainActivity.class);
                        intent.putExtra("villageMainInfo", mList.get(position));

                        activity.startActivity(intent);

                        /*메인이아니면 종료함
                        * 복잡해서..정리가필요하다..*/
                        ActivityManager am = (ActivityManager)activity.getSystemService(Context.ACTIVITY_SERVICE);
                        List<ActivityManager.RunningTaskInfo> Info = am.getRunningTasks(1);
                        ComponentName topActivity = Info.get(0).topActivity;
                        String topactivityname = topActivity.getClassName();

                        Log.e("현재액티비티", topactivityname);
                        if(!topactivityname.equals("com.wonbuddism.bupmun.Main.MainActivity")){
                                activity.finish();
                        }
                        //activity.finish();
                    }
                }, 1500);

            }
        });

        return convertView;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(VillageMainInfo _msg) {
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
    }
}
