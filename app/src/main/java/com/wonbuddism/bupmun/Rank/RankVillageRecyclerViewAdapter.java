package com.wonbuddism.bupmun.Rank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonbuddism.bupmun.DataVo.RankVillageInfo;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Common.FooterLoaderAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by csc-pc on 2016. 2. 23..
 */
public class RankVillageRecyclerViewAdapter extends FooterLoaderAdapter<RankVillageInfo> {

    private List<RankVillageInfo> mValues;

    public RankVillageRecyclerViewAdapter(Context context,ArrayList<RankVillageInfo> items) {
        super(context);
        mValues = items;
    }

    @Override
    public long getYourItemId(int position) {
        return Long.parseLong(mItems.get(position).getNo());
    }


    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        return new viewHolder(mInflater.inflate(R.layout.item_rank_village_recyclerview, parent, false));
    }

    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof viewHolder) {
            viewHolder viewHolder = (viewHolder)holder;
            viewHolder.no.setText(mValues.get(position).getNo()+".");
            viewHolder.name.setText(mValues.get(position).getVil_name());
            viewHolder.cnt.setText("("+mValues.get(position).getVil_user_count()+"명)");
            viewHolder.manager.setText(mValues.get(position).getVil_manager());
        }
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_rank_village_recyclerview_no) TextView no;
        @Bind(R.id.item_rank_village_recycleview_manager) TextView manager;
        @Bind(R.id.item_rank_village_recycleview_name) TextView name;
        @Bind(R.id.item_rank_village_recycleview_cnt) TextView cnt;


        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public RankVillageInfo getValueAt(int position) {
        return mValues.get(position);
    }

}
