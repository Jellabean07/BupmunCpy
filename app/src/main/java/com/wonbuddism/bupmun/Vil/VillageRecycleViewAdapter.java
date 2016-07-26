package com.wonbuddism.bupmun.Vil;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wonbuddism.bupmun.Listener.VillageDeleteListener;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Common.FooterLoaderAdapter;
import com.wonbuddism.bupmun.Common.PrefUserInfoManager;
import com.wonbuddism.bupmun.DataVo.VillageComments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VillageRecycleViewAdapter extends FooterLoaderAdapter<VillageComments> {


    private ArrayList<VillageComments> mValues;
    private Context context;
    private Activity activity;
    private VillageDeleteListener listener;

    public VillageRecycleViewAdapter(Context context,Activity activity,ArrayList<VillageComments> items) {
        super(context);
        this.context = context;
        this.activity = activity;
        mValues = items;
    }

    @Override
    public long getYourItemId(int position) {
        if(mItems.get(position)==null){
            return 0;
        }else{
            return Long.parseLong(mItems.get(position).getComment_no());
        }
    }

  /*  @Override
    public int getItemCount() {
        return mValues.size();
    }*/

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        return new villCommentViewHolder(mInflater.inflate(R.layout.item_village_recycleview, parent, false));
    }

    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof villCommentViewHolder) {
            final int pos = position;
            villCommentViewHolder viewHolder = (villCommentViewHolder)holder;
            viewHolder.name.setText(mValues.get(position).getComment_name());
            viewHolder.id.setText("["+mValues.get(position).getUserid()+"]");
            viewHolder.date.setText(mValues.get(position).getComment_date());
            viewHolder.content.setText(mValues.get(position).getComment_contents());

            if(mValues.get(position).getUserid().equals(new PrefUserInfoManager(activity).getUserInfo().getUSERID())){
                viewHolder.more.setVisibility(View.VISIBLE);
            }else{
                viewHolder.more.setVisibility(View.INVISIBLE);
                //viewHolder.more.setVisibility(View.INVISIBLE);
            }

            viewHolder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context = v.getContext();
                    listener.DeleteItem(mValues.get(pos));
                    Toast.makeText(context, "덧글이 삭제 되었습니다", Toast.LENGTH_SHORT).show();
                }
            });

            if(today_chk(position)){
                viewHolder.newnew.setVisibility(View.VISIBLE);
            }else {
                viewHolder.newnew.setVisibility(View.INVISIBLE);
            }
        }
    }

    public class villCommentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_village_recycleview_name_textview) TextView name;
        @Bind(R.id.item_village_recycleview_id_textview) TextView id;
        @Bind(R.id.item_village_recycleview_date_textview) TextView date;
        @Bind(R.id.item_village_recycleview_content_textview) TextView content;
        @Bind(R.id.item_village_recycleview_more_imageview) ImageView more;
        @Bind(R.id.item_village_recycleview_new_imageview) ImageView newnew;

        public villCommentViewHolder(View itemView) {
            super(itemView);
            try {
                ButterKnife.bind(this, itemView);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }



    public void setOnDeleteListener(VillageDeleteListener listener) {
        this.listener = listener;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(VillageComments _msg) {
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

    private boolean today_chk(int position){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        return mValues.get(position).getComment_date().substring(0,10).equals(date);
    }
}
