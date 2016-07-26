package com.wonbuddism.bupmun.Board;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wonbuddism.bupmun.DataVo.BoardArticle;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnBoardDetail;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Common.ListViewFooterManager;
import com.wonbuddism.bupmun.Dialog.ProgressWaitDaialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 2015-12-30.
 */
public class BoardArticleListViewAdapter extends BaseAdapter{
    private Context context;
    private Activity activity;
    private ArrayList<BoardArticle> mList;
    private ListView lv;
    private ListViewFooterManager footerManager;

    public BoardArticleListViewAdapter(Activity activity,ArrayList<BoardArticle> mList, ListView lv) {
        this.context = context;
        this.activity = activity;
        this.mList = mList;
        this.lv = lv;
        footerManager = new ListViewFooterManager(activity,this.lv);

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

        TextView title           = null;
        TextView id              = null;
        TextView date            = null;
        TextView hit             = null;
        TextView index           = null;
        TextView category        = null;
        TextView comment_count   = null;
        LinearLayout commentBox  = null;
        ImageView new_img         = null;
        CustomHolder    holder   = null;

        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_board_main_content_listview, parent, false);
            title = (TextView) convertView.findViewById(R.id.item_board_main_content_info_title_textview);
            id = (TextView) convertView.findViewById(R.id.item_board_main_content_info_detail_id_textview);
            date = (TextView) convertView.findViewById(R.id.item_board_main_content_info_detail_date_textview);
            hit = (TextView) convertView.findViewById(R.id.item_board_main_content_info_detail_hit_textview);
            index = (TextView) convertView.findViewById(R.id.item_board_main_content_info_category_index_textview);
            category = (TextView) convertView.findViewById(R.id.item_board_main_content_info_category_title_textview);
            comment_count = (TextView) convertView.findViewById(R.id.item_board_main_content_count_textview);
            commentBox = (LinearLayout)convertView.findViewById(R.id.item_board_main_content_count_main);
            new_img = (ImageView)convertView.findViewById(R.id.item_board_main_content_info_title_new_imageview);

            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.title_TextView = title;
            holder.id_TextView = id;
            holder.date_TextView = date;
            holder.hit_TextView = hit;
            holder.index_TextView = index;
            holder.category_TextView = category;
            holder.coment_count_TextView = comment_count;
            holder.commentBox_LinearLayout = commentBox;
            holder.new_img_ImageView = new_img;
            convertView.setTag(holder);
        }else{
            holder  = (CustomHolder) convertView.getTag();
            title   = holder.title_TextView;
            id   = holder.id_TextView;
            date   = holder.date_TextView;
            hit   = holder.hit_TextView;
            index   = holder.index_TextView;
            category   = holder.category_TextView;
            comment_count   = holder.coment_count_TextView;
            commentBox = holder.commentBox_LinearLayout;
            new_img = holder.new_img_ImageView;
        }
        title.setText(mList.get(position).getTitle());
        id.setText(mList.get(position).getUserid());
        date.setText(mList.get(position).getWritetime().substring(0, 10)); //년월일 까지 출력
        hit.setText(mList.get(position).getReadcnt());
        index.setText(mList.get(position).getWriteno());

        switch (Integer.parseInt(mList.get(position).getBoardno())){
            case 2802:
                category.setText("질문");
                break;
            case 2801:
                category.setText("의견");
                break;
            case 2800:
                category.setText("기타");
                break;
            case 2803:
                category.setText("동네방네홍보");
                break;
            case 2804:
                category.setText("공지");
                break;
            default:
                category.setText("기타");
                break;
        }

        if(Integer.parseInt(mList.get(position).getReplydepth())<1){
            commentBox.setVisibility(convertView.GONE);
        }else {
            comment_count.setText(mList.get(position).getReplydepth());
        }

        if(!dateCompare(mList.get(position).getWritetime())){ //최신글 new 표시
            new_img.setVisibility(convertView.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new ProgressWaitDaialog(activity);
                dialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        new HTTPconnBoardDetail(activity, mList.get(position).getBoardno(), mList.get(position).getWriteno()).execute();
                    }
                }, 1500);

            }
        });
        return convertView;
    }

    private boolean dateCompare(String item_date){
        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = dateFormat.parse(item_date);
            date2 = dateFormat.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = date1.getTime() - date2.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if(diffDays>=1){
            return true;
        }else{
            return false;
        }
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(BoardArticle msg) {
        mList.add(msg);
    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int position) {
        mList.remove(position);
    }

    //외부에서 아이템 초기화 요청
    public void clear(){
        mList.clear();
    }
    public void refresh(ArrayList<BoardArticle> list){
        this.mList = list;
    }


    private class CustomHolder {
        TextView    title_TextView;
        TextView    id_TextView;
        TextView    date_TextView;
        TextView    hit_TextView;
        TextView    index_TextView;
        TextView    category_TextView;
        TextView    coment_count_TextView;
        LinearLayout commentBox_LinearLayout;
        ImageView new_img_ImageView;
    }
}
