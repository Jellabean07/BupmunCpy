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
import android.widget.TextView;

import com.wonbuddism.bupmun.DataVo.RankMyInfo;
import com.wonbuddism.bupmun.DataVo.RankUserInfo;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnRankToday;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnRankTodayMy;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Rank.RankTodayRecyclerViewAdapter;
import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;
import com.wonbuddism.bupmun.Utility.RecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;


public class RankTodayFragment extends Fragment{


    @Bind(R.id.rank_today_recyclerview) RecyclerView mRecyclerView;
    @Bind(R.id.rank_today_title_msg) TextView msgTextview;

    private View view;
    private Context context;
    private Activity activity;
    private TextView msg;
    private RankMyInfo rankMyInfo;
    private boolean mLockListView;

    private RankTodayRecyclerViewAdapter adapter;
    private ArrayList<RankUserInfo> rankUserInfos;
    private Handler mHandler;
    private int page_no;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rank_today, container, false);
        setMyRankData();
        mHandler = new Handler();
        page_no = 0;
        RecyclerView rv = (RecyclerView)view.findViewById(R.id.rank_today_recyclerview);
        setupRecyclerView(rv);


        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
        this.activity = activity;
    }

    private void setMyRankData(){
        rankMyInfo = new RankMyInfo(new PrefUserInfoManager(activity).getUserInfo().getNAME(),"0","0");
        msg = (TextView)view.findViewById(R.id.rank_today_title_msg);
        msg.setText("");
        //msg.setText(rankMyInfo.getName() + "님의 전체 랭킹은 전체" + rankMyInfo.getToday_rank() + "명 중에 " + rankMyInfo.getToday_total_cnt() + "위 입니다.");
        new HTTPconnRankTodayMy(activity,msg).execute();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
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
        rankUserInfos = new ArrayList<>();
        adapter = new RankTodayRecyclerViewAdapter(activity, rankUserInfos);
        adapter.setHasStableIds(true);
        //rankUserInfos = getSortList();
        adapter.setItems(rankUserInfos);
    }

    private void loadMoreData() {

        adapter.showLoading(true);
        adapter.notifyDataSetChanged();

        // Load data after delay
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new HTTPconnRankToday(activity,adapter,rankUserInfos,(page_no++)+"").execute();
            }
        }, 1500);

    }

    private ArrayList<RankUserInfo> getSortList() {
        ArrayList<RankUserInfo> list = new ArrayList<>();
        list.add(new RankUserInfo("3","전용수","jys"));
        list.add(new RankUserInfo("1","김정훈","kjh"));
        list.add(new RankUserInfo("10","신민아","sma"));
        list.add(new RankUserInfo("4","노세현","nsh"));
        list.add(new RankUserInfo("11","이병헌","lbh"));
        list.add(new RankUserInfo("8","문채원","mcw"));
        list.add(new RankUserInfo("12","이대길","ldg"));
        list.add(new RankUserInfo("14","한지민","hjm"));
        list.add(new RankUserInfo("7","전도현","jdh"));
        list.add(new RankUserInfo("13","김범수","kbs"));
        list.add(new RankUserInfo("5","최석찬","csc"));
        list.add(new RankUserInfo("6","임원희","lwh"));
        list.add(new RankUserInfo("2","김지운","kjw"));
        list.add(new RankUserInfo("9","김우빈","kwb"));


        Collections.sort(list, new NoAscCompare());


        return list;
    }

    static class NoAscCompare implements Comparator<RankUserInfo> {

        /**
         * 오름차순(ASC)
         */
        @Override
        public int compare(RankUserInfo arg0, RankUserInfo arg1) {
            return Integer.parseInt(arg0.getNo()) < Integer.parseInt(arg1.getNo()) ? -1 : Integer.parseInt(arg0.getNo()) > Integer.parseInt(arg1.getNo()) ? 1:0;
        }

    }
}

