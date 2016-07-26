package com.wonbuddism.bupmun.Progress;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonbuddism.bupmun.DataVo.HttpParamProgress;
import com.wonbuddism.bupmun.DataVo.HttpResultProgress;
import com.wonbuddism.bupmun.HttpConnection.HttpConnProgressTitle;
import com.wonbuddism.bupmun.R;

import java.util.ArrayList;

/**
 * Created by csc-pc on 2016. 7. 25..
 */
public class ProgressRecyclerViewAdapter extends RecyclerView.Adapter<ProgressRecyclerViewAdapter.ProgressViewHolder> {


    ArrayList<HttpResultProgress> cotnents;
    HttpParamProgress httpParam;
    Activity activity;
    String TAG ="ProgressRecyclerViewAdapter";
    ProgressRecyclerViewAdapter(Activity activity, ArrayList<HttpResultProgress> cotnents) {
        this.cotnents = cotnents;
        this.activity = activity;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public ProgressViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_progress_main_recyclerview, viewGroup, false);
        return new ProgressViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ProgressViewHolder holder, final int position) {

        String complete = cotnents.get(position).getComplete();
        holder.cotnent.setText(cotnents.get(position).getTitle());
        if (complete.equals("NONE")) {
            holder.cotnent.setTextColor(Color.parseColor("#EC008C"));
            Log.e(TAG, "COMPLETE : NONE");
        } else if(complete.equals("ING")){
            holder.cotnent.setTextColor(Color.parseColor("#00AEFF"));
            Log.e(TAG, "COMPLETE : ING");
        } else {
            holder.cotnent.setTextColor(Color.parseColor("#9A9A9A"));
            Log.e(TAG, "COMPLETE : NOTNONE");
        }

        holder.lL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Log.e(TAG, "depth : T");
                //httpParam.getTitle().add(cotnents.get(position).getTitle());
                httpParam.setNdepth(cotnents.get(position).getNdepth());
                httpParam.setTitle_no("1");


                HttpParamProgress param = new HttpParamProgress(httpParam.getIndex(),httpParam.getTitle_no(),httpParam.getNdepth());
                param.getTitle().add(cotnents.get(position).getTitle());
                Log.e(TAG, httpParam.toString());
                Log.e(TAG, param.toString());
                if (cotnents.get(position).getNdepth().equals("T")) {

                    intent = new Intent(activity,ProgressDetailActivity.class);
                    intent.putExtra("httpParam",param);
                    activity.startActivityForResult(intent, 1000);
                } else {
                    Log.e(TAG,"depth : F");
                    new HttpConnProgressTitle(activity,param).execute();

                }

            }
        });

    }


    @Override
    public int getItemCount() {
        /*return cards.size();*/
        return cotnents.size();
    }

    public final static class ProgressViewHolder extends RecyclerView.ViewHolder {
        TextView cotnent;
        LinearLayout lL;


        ProgressViewHolder(View itemView) {
            super(itemView);
            cotnent = (TextView) itemView.findViewById(R.id.item_progress_main_content_textview);
            lL = (LinearLayout) itemView.findViewById(R.id.item_progress_main_content_linearlayout);
        }
    }

    public void setParameter(HttpParamProgress httpParam){
        this.httpParam = httpParam;
    }
    public ArrayList<HttpResultProgress> getProgressItem() {
        return cotnents;
    }

    public HttpResultProgress getValueAt(int position) {
        return cotnents.get(position);
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(HttpResultProgress _msg) {
        cotnents.add(_msg);
    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        cotnents.remove(_position);
    }

    //외부에서 아이템 초기화 요청
    public void clear() {
        cotnents.clear();
    }
}
