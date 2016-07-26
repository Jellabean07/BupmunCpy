package com.wonbuddism.bupmun.MyFeeling;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonbuddism.bupmun.DataVo.FeelingMemo;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Common.FooterLoaderAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FeelingRecycleViewAdapter extends FooterLoaderAdapter<FeelingMemo> {


    private ArrayList<FeelingMemo> mValues;
    private Context context;
    private Activity activity;
    private int no;
    private android.support.v4.app.Fragment fragment;

    public FeelingRecycleViewAdapter(android.support.v4.app.Fragment fragment, Context context,Activity activity,ArrayList<FeelingMemo> items) {
        super(context);
        this.fragment = fragment;
        this.context = context;
        this.activity = activity;
        mValues = items;
        no = 1;
    }

    @Override
    public long getYourItemId(int position) {
        return Long.parseLong(mItems.get(position).getMemo_seq());
    }

    @Override
    public void setItems(List<FeelingMemo> items) {
        super.setItems(items);
    }


 /*   @Override
    public int getItemCount() {
        return super.getItemCount();
    }*/

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        return new feelingMemoViewHolder(mInflater.inflate(R.layout.item_feeling_content_recycleview, parent, false));
    }


    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof feelingMemoViewHolder) {
            final int pos = position;
            feelingMemoViewHolder viewHolder = (feelingMemoViewHolder)holder;
            /*if(((pos+1)/10)==0){
                viewHolder.memo_seq.setText("0"+(pos+1));
            }else{
                viewHolder.memo_seq.setText((pos+1)+"");
            }*/

            viewHolder.memo_seq.setText((pos+1)+"");
           // viewHolder.memo_seq.setText(mValues.get(position).getMemo_seq());

            viewHolder.memo_contents.setText(mValues.get(position).getMemo_contents());
            viewHolder.bupmuntitle.setText(mValues.get(position).getTitle());
            viewHolder.bupmunindex.setText(mValues.get(position).getShort_title());
            viewHolder.regist_date.setText(mValues.get(position).getRegist_date().substring(0, 10));

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity,FeelingDetailActivity.class);
                    intent.putExtra("feelingMemo",mValues.get(pos));
                    fragment.startActivityForResult(intent,1000);
                }
            });
        }
    }

    public class feelingMemoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_feeling_content_no_textview) TextView memo_seq;
        @Bind(R.id.item_feeling_content_content_textview) TextView memo_contents;
        @Bind(R.id.item_feeling_content_category_textview) TextView bupmuntitle;
        @Bind(R.id.item_feeling_content_shortTitle_textview) TextView bupmunindex;
        @Bind(R.id.item_feeling_content_date_textview) TextView regist_date;


        public feelingMemoViewHolder(View itemView) {
            super(itemView);
            try {
                ButterKnife.bind(this, itemView);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    public void deleteDialog(int positon){
        final int pos =positon;
        AlertDialog.Builder d = new AlertDialog.Builder(activity);
        d.setTitle("덧글 삭제");
        d.setMessage("해당 덧글을 삭제 하시겠습니까?");
        // d.setIcon(R.drawable.warnning_off);

        d.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // listener.DeleteItem(mValues.get(pos));
            }
        });

        d.setNegativeButton("아니요",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        d.show();
    }

   /* public void setOnDeleteListener(BoardCommDeleteListener listener) {
        this.listener = listener;
    }*/

    public ArrayList<FeelingMemo> getFeelingMemos(){
        return mValues;
    }

    public void setFeelingMemos(ArrayList<FeelingMemo> feelingMemos){
        mValues = feelingMemos;
    }
    public FeelingMemo getValueAt(int position) {
        return mValues.get(position);
    }
    // 외부에서 아이템 추가 요청 시 사용
    public void add(FeelingMemo _msg) {
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
