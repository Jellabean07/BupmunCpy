package com.wonbuddism.bupmun.Rank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.DataVo.RankUserInfo;
import com.wonbuddism.bupmun.Utility.FooterLoaderAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class RankTodayRecyclerViewAdapter extends FooterLoaderAdapter<RankUserInfo> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<RankUserInfo> mValues;

    public RankTodayRecyclerViewAdapter(Context context,List<RankUserInfo> items) {
        super(context);
        mValues = items;
    }

    @Override
    public long getYourItemId(int position) {
        return Long.parseLong(mItems.get(position).getNo());
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        return new viewHolder(mInflater.inflate(R.layout.item_rank_today_recyclerview, parent, false));
    }

    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof viewHolder) {
            viewHolder viewHolder = (viewHolder)holder;
            viewHolder.no.setText(mValues.get(position).getNo()+".");
            viewHolder.name.setText(mValues.get(position).getName());
            viewHolder.id.setText("("+mValues.get(position).getId()+")");
        }
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_rank_today_recyclerview_no) TextView no;
        @Bind(R.id.item_rank_today_recyclerview_name) TextView name;
        @Bind(R.id.item_rank_today_recyclerview_id) TextView id;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /*public static class ViewHolder extends RecyclerView.ViewHolder {
        public RankUserInfo mBoundString;

        public final View mView;
        public final TextView no;
        public final TextView name;
        public final TextView id;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            no = (TextView) view.findViewById(R.id.item_rank_today_recyclerview_no);
            name = (TextView) view.findViewById(R.id.item_rank_today_recyclerview_name);
            id = (TextView) view.findViewById(R.id.item_rank_today_recyclerview_id);


        }
    }

    public RankUserInfo getValueAt(int position) {
        return mValues.get(position);
    }

    public RankTodayRecyclerViewAdapter(Context context, List<RankUserInfo> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rank_today_recyclerview, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mBoundString = mValues.get(position);
        holder.no.setText(mValues.get(position).getNo()+".");
        holder.name.setText(mValues.get(position).getName());
        holder.id.setText("("+mValues.get(position).getId()+")");
    }*/


}