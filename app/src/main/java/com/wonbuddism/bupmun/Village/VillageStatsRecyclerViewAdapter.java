package com.wonbuddism.bupmun.Village;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.FooterLoaderAdapter;
import com.wonbuddism.bupmun.DataVo.VillageStatsMember;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VillageStatsRecyclerViewAdapter extends FooterLoaderAdapter<VillageStatsMember> {

    private ArrayList<VillageStatsMember> mValues;
    private Context context;

    public VillageStatsRecyclerViewAdapter(Context context,ArrayList<VillageStatsMember> items) {
        super(context);
        this.context = context;
        mValues = items;
    }

    @Override
    public long getYourItemId(int position) {
        if(mItems.get(position)==null){
            return 0;
        }else{
            return Long.parseLong(mItems.get(position).getNo());
        }
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        return new villStatsMemeberViewHolder(mInflater.inflate(R.layout.item_village_stats_recyclerview, parent, false));
    }

    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof villStatsMemeberViewHolder) {
            villStatsMemeberViewHolder viewHolder = (villStatsMemeberViewHolder)holder;

            viewHolder.no.setText(mValues.get(position).getNo()+"");
            viewHolder.name.setText(mValues.get(position).getName());
            viewHolder.id.setText("("+mValues.get(position).getUserid()+")");
            viewHolder.today.setText(mValues.get(position).getToday_cnt());
            viewHolder.month.setText(mValues.get(position).getMonth_cnt());
            viewHolder.cumulation.setText(mValues.get(position).getTotal_cnt());

        }
    }

    public class villStatsMemeberViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_village_stats_no_textview) TextView no;
        @Bind(R.id.item_village_stats_name_textview) TextView name;
        @Bind(R.id.item_village_stats_id_textview) TextView id;
        @Bind(R.id.item_village_stats_today_textview) TextView today;
        @Bind(R.id.item_village_stats_month_textview) TextView month;
        @Bind(R.id.item_village_stats_cumulation_textview) TextView cumulation;

        public villStatsMemeberViewHolder(View itemView) {
            super(itemView);
            try {
                ButterKnife.bind(this, itemView);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    public VillageStatsMember getValueAt(int position) {
        return mValues.get(position);
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(VillageStatsMember _msg) {
        mValues.add(_msg);
    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        mValues.remove(_position);
    }

    //외부에서 아이템 초기화 요청
    public void clear(){
        mValues.clear();
    }
}