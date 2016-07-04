package com.wonbuddism.bupmun.MyFeeling;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonbuddism.bupmun.MyFeeling.HTTPconnection.FeelingMemo;
import com.wonbuddism.bupmun.MyFeeling.HTTPconnection.HTTPconnFeelingAll;
import com.wonbuddism.bupmun.MyFeeling.HTTPconnection.HTTPconnFeelingPart;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.RecyclerViewScrollListener;

import java.util.ArrayList;

public class FeelingConentPartFragment extends Fragment {
    private String title;
    private View view;
    private TextView totalCount;
    private ArrayList<FeelingMemo> feelingMemos;
    private FeelingRecycleViewAdapter adapter;
    private Activity activity;
    private Context context;
    private Handler mHandler;
    private int page_no;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle extra = getArguments();
        title = extra.getString("title");
        view = inflater.inflate(R.layout.fragment_feeling_content_part, container, false);
        setData();
        setLayout();

        return view;
    }

    private String getTitleToCode(String titleCode){
        if(titleCode.equals("정전")){
            return "00";
        }else if(titleCode.equals("대종경")){
            return "01";
        }else if(titleCode.equals("원불교교사")){
            return "02";
        }else if(titleCode.equals("불조요경")){
            return "03";
        }else if(titleCode.equals("예전")){
            return "04";
        }else if(titleCode.equals("정산종사법어")){
            return "05";
        }else if(titleCode.equals("대산종사법어")){
            return "06";
        }
        return  "00";
    }


    private void setData(){
        mHandler = new Handler();
        page_no = 0;
    }
    private void setLayout(){
        totalCount = (TextView)view.findViewById(R.id.feeling_content_count_part_textview);

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.feeling_content_part_recyclerview);
        setupRecyclerView(rv);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        //final CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        //recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        setupAdapter();
        recyclerView.setAdapter(adapter);
        loadMoreData();
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });

        // mLayoutManager = new LinearLayoutManager(this);

    }


    private void setupAdapter() {
        feelingMemos = new ArrayList<>();
       // feelingMemos.add(new FeelingMemo("0","1","2","3","4","500000000000"));
        adapter = new FeelingRecycleViewAdapter(this,context,activity, feelingMemos);
        adapter.setHasStableIds(true);
        //rankUserInfos = getSortList();
        adapter.setItems(feelingMemos);
        //adapter.setOnDeleteListener(this);
    }
    // adding 10 object creating dymically to arraylist and updating recyclerview when ever we reached last item

    private void loadMoreData() {

        adapter.showLoading(true);
        adapter.notifyDataSetChanged();

        // Load data after delay
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
               new HTTPconnFeelingPart(activity, adapter,totalCount, getTitleToCode(title),(page_no++) + "").execute();
               // new HTTPconnFeelingAll(activity, adapter,totalCount,(page_no++) + "").execute();
            }
        }, 1500);
    }
    /*
    private List<FeelingItem> getSortList() {
        List<FeelingItem> list = new ArrayList<>();
        if(title.equals("전체")){
            list.add(new FeelingItem("1","와 좋다","정전","일원상신앙","2015.12.28"));
            list.add(new FeelingItem("2","마음이 편해집니다","정전","일원상신앙","2015.12.28"));
            list.add(new FeelingItem("3","새벽감성","대종경","일원상신앙","2015.12.28"));
            list.add(new FeelingItem("4","난 가끔 눈물을 흘린다","원불교교사","일원상신앙","2015.12.28"));
            list.add(new FeelingItem("5","가끔 눈물을 참을 수 없는 내가 별루다","불조요경","일원상신앙","2015.12.29"));
            list.add(new FeelingItem("6","맘이 아파서","예전","일원상신앙","2015.12.29"));
            list.add(new FeelingItem("7","소리치며 울수 있다는건","정산종사법어","일원상신앙","2015.12.29"));
            list.add(new FeelingItem("7","좋은 거야..","정산종사법어","일원상신앙","2015.12.29"));
            list.add(new FeelingItem("8","뭐 꼭 슬펴야만 우는건아니잖아^^","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("9","난 눈물이 좋다","대종경","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("10","나는 머리가 아닌","대종경","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("11","맘으로 우는 내가 좋다","대산종사법어","일원상신앙","2015.12.30"));
        }else  if(title.equals("정전")){
            list.add(new FeelingItem("1","와 좋다","정전","일원상신앙","2015.12.28"));
            list.add(new FeelingItem("2","마음이 편해집니다","정전","일원상신앙","2015.12.28"));
        }else  if(title.equals("대종경")){
            list.add(new FeelingItem("3","새벽감성","대종경","일원상신앙","2015.12.28"));
            list.add(new FeelingItem("9","난 눈물이 좋다","대종경","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("10","나는 머리가 아닌","대종경","일원상신앙","2015.12.30"));
        }else  if(title.equals("원불교교사")){
            list.add(new FeelingItem("4","난 가끔 눈물을 흘린다","원불교교사","일원상신앙","2015.12.28"));
        }else  if(title.equals("불조요경")){
            list.add(new FeelingItem("5","가끔 눈물을 참을 수 없는 내가 별루다","불조요경","일원상신앙","2015.12.29"));
        }else  if(title.equals("예전")){
            list.add(new FeelingItem("6","맘이 아파서","예전","일원상신앙","2015.12.29"));
        }else  if(title.equals("정산종사법어")){
            list.add(new FeelingItem("7","좋은 거야..","정산종사법어","일원상신앙","2015.12.29"));
        }else  if(title.equals("대산종사법어")){
            list.add(new FeelingItem("8","뭐 꼭 슬펴야만 우는건아니잖아^^","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("11","맘으로 우는 내가 좋다","대산종사법어","일원상신앙","2015.12.30"));
        }

        Collections.sort(list, new NoAscCompare());

        totalCount.setText(list.size()+"");
        return list;
    }

    static class NoAscCompare implements Comparator<FeelingItem> {

        *//**
     * 오름차순(ASC)
     *//*
        @Override
        public int compare(FeelingItem arg0, FeelingItem arg1) {
            return Integer.parseInt(arg0.getNo()) < Integer.parseInt(arg1.getNo()) ? -1 : Integer.parseInt(arg0.getNo()) > Integer.parseInt(arg1.getNo()) ? 1:0;
        }

    }*/

    private void refreshData(){
        page_no = 0;
        feelingMemos = new ArrayList<>();
        adapter.clear();
        adapter.notifyDataSetChanged();
        loadMoreData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000){
            refreshData();
        }
    }
}
