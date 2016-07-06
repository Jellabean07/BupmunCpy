package com.wonbuddism.bupmun.Rank;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonbuddism.bupmun.DataVo.RankVillageInfo;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnRankVillage;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.RecyclerViewScrollListener;

import java.util.ArrayList;

public class RankVillageFragemnet extends Fragment{

    private View view;
    private Context context;
    private Activity activity;

    private RankVillageRecyclerViewAdapter adapter;
    private ArrayList<RankVillageInfo> rankVillageInfos;
    private Handler mHandler;
    private int page_no;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rank_village, container, false);
        mHandler = new Handler();
        page_no = 0;

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.rank_village_recyclerview);
        setupRecyclerView(rv);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
        this.activity = activity;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        setupAdapter();
        recyclerView.setAdapter(adapter);
        loadMoreData();
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
    }

    private void setupAdapter() {
        rankVillageInfos = new ArrayList<>();
        adapter = new RankVillageRecyclerViewAdapter(activity, rankVillageInfos);
        adapter.setHasStableIds(true);
        //rankUserInfos = getSortList();
        adapter.setItems(rankVillageInfos);
    }

    private void loadMoreData() {

        adapter.showLoading(true);
        adapter.notifyDataSetChanged();

        // Load data after delay
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new HTTPconnRankVillage(activity, adapter, rankVillageInfos, (page_no++) + "").execute();
            }
        }, 1500);

    }

}
