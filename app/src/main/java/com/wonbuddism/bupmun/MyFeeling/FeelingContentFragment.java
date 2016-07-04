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
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.RecyclerViewScrollListener;


import java.util.ArrayList;

/**
 * Created by user on 2016-01-14.
 */
public class FeelingContentFragment extends Fragment{
    private String title;
    private View view;
    private TextView totalCount;
    private ArrayList<FeelingMemo> feelingMemos;
    private FeelingRecycleViewAdapter adapter;
    private Activity activity;
    private Context context;
    private Handler mHandler;
    private int page_no;
    private int size;

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
        view = inflater.inflate(R.layout.fragment_feeling_content, container, false);
        setData();
        setLayout();

        return view;
    }

    private void setData(){
        mHandler = new Handler();
        page_no = 0;
        size = -1;
    }
    private void setLayout(){
        totalCount = (TextView)view.findViewById(R.id.feeling_content_count_textview);

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.feeling_content_recyclerview);
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
                adapter.showLoading(true);
                loadMoreData();
            }
        });

        // mLayoutManager = new LinearLayoutManager(this);

    }


    private void setupAdapter() {
        feelingMemos = new ArrayList<>();
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
                new HTTPconnFeelingAll(activity, adapter,totalCount,(page_no++) + "").execute();


            }
        }, 1500);
    }

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
