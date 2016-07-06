package com.wonbuddism.bupmun.Board;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonbuddism.bupmun.DataVo.BoardComment;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.FooterLoaderAdapter;
import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;
import com.wonbuddism.bupmun.Utility.ProgressWaitDaialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BoardCommentRecyclerViewAdapter extends FooterLoaderAdapter<BoardComment>{
    private ArrayList<BoardComment> mValues;
    private Context context;
    private Activity activity;
    private BoardCommDeleteListener listener;

    public BoardCommentRecyclerViewAdapter(Context context,Activity activity,ArrayList<BoardComment> items) {
        super(context);
        this.context = context;
        this.activity = activity;
        mValues = items;

    }

    @Override
    public long getYourItemId(int position) {
        return Long.parseLong(mItems.get(position).getSeqno());
    }


    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        return new boardCommentViewHolder(mInflater.inflate(R.layout.item_board_detail_comment_recycleview, parent, false));
    }


    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof boardCommentViewHolder) {
            final int pos = position;
            boardCommentViewHolder viewHolder = (boardCommentViewHolder)holder;
            viewHolder.name.setText(mValues.get(position).getUsername());
            viewHolder.id.setText("["+mValues.get(position).getUserid()+"]");
            viewHolder.date.setText(mValues.get(position).getWritetime());
            viewHolder.content.setText(mValues.get(position).getContent());


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mValues.get(pos).getUserid().equals(new PrefUserInfoManager(activity).getUserInfo().getUSERID())) {
                        deleteDialog(pos);
                    }
                }
            });



            if(today_chk(position)){
                viewHolder.newnew.setVisibility(View.VISIBLE);
            }else {
                viewHolder.newnew.setVisibility(View.INVISIBLE);
            }
        }
    }

    public class boardCommentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_board_detail_comment_name_textview) TextView name;
        @Bind(R.id.item_board_detail_comment_id_textview) TextView id;
        @Bind(R.id.item_board_detail_comment_date_textview) TextView date;
        @Bind(R.id.item_board_detail_comment_content_textview) TextView content;
        @Bind(R.id.item_board_detail_comment_new_imageview) ImageView newnew;

        public boardCommentViewHolder(View itemView) {
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
                final Dialog dialogWait = new ProgressWaitDaialog(activity);
                dialogWait.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogWait.dismiss();
                        listener.DeleteItem(mValues.get(pos));
                    }
                },1500);

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

    public void setOnDeleteListener(BoardCommDeleteListener listener) {
        this.listener = listener;
    }

    public ArrayList<BoardComment> getBoardComments(){
        return mValues;
    }

    public BoardComment getValueAt(int position) {
        return mValues.get(position);
    }
    // 외부에서 아이템 추가 요청 시 사용
    public void add(BoardComment _msg) {
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
        return mValues.get(position).getWritetime().substring(0,10).equals(date);
    }


}
