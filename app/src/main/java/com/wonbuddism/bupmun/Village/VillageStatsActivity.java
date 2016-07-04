package com.wonbuddism.bupmun.Village;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.wonbuddism.bupmun.Utility.CustomLinearLayoutManager;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.RecyclerViewScrollListener;
import com.wonbuddism.bupmun.Village.HTTPconnection.HTTPconnVillStats;
import com.wonbuddism.bupmun.Village.HTTPconnection.HTTPconnVillStatsMember;
import com.wonbuddism.bupmun.Village.HTTPconnection.VillageStats;
import com.wonbuddism.bupmun.Village.HTTPconnection.VillageStatsMember;

import java.util.ArrayList;
import java.util.List;

public class VillageStatsActivity extends AppCompatActivity {

    private TextView villageToday;
    private TextView villageMonth;
    private TextView villageCumulation;
    private TextView villageCurrentRank;
    private TextView villageMaxRank;

    private TextView peopleToday;
    private TextView peopleMonth;
    private TextView peopleCumulation;
    private TextView peopleCurrentRank;
    private TextView peopleMaxRank;

    private RecyclerView vStatsRecyclerView;
    private VillageStats villageStats;
    private ArrayList<VillageStatsMember> villageStatsMembers;
    private VillageStatsRecyclerViewAdapter adapter;
    private Handler mHandler;
    private int page_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_stats);

        setData();
        setLayout();

    }

    private void setData() {
        Intent intent = getIntent();
        villageStats = (VillageStats)intent.getSerializableExtra("villageStats");
        mHandler = new Handler();
        page_no = 0;
    }

    private void setLayout() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.village_stats_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        villageToday  = (TextView)findViewById(R.id.village_stats_vill_today_textview); // 동네 오늘 단락수
        villageMonth = (TextView)findViewById(R.id.village_stats_vill_month_textview); // 동네 최근30일 단락수
        villageCumulation = (TextView)findViewById(R.id.village_stats_vill_cumulation_textview); //동네 누적 단락수
        villageCurrentRank = (TextView)findViewById(R.id.village_stats_vill_current_rank_textview); //동네 랭크 몇위
        villageMaxRank =  (TextView)findViewById(R.id.village_stats_vill_max_rank_textview); // 총 동네 수

        peopleToday = (TextView)findViewById(R.id.village_stats_people_today_textview); //개인 오늘 단락수
        peopleMonth = (TextView)findViewById(R.id.village_stats_people_month_textview); //개인 최근30일 단락수
        peopleCumulation = (TextView)findViewById(R.id.village_stats_people_cumulation_textview); //개인 누적단락수
        peopleCurrentRank = (TextView)findViewById(R.id.village_stats_people_current_rank_textview); //개인 랭크 몇위
        peopleMaxRank =  (TextView)findViewById(R.id.village_stats_people_max_rank_textview); //동네 가입회원수


        villageToday.setText(villageStats.getVil_today_cnt());
        villageMonth.setText(villageStats.getVil_month_cnt());
        villageCumulation.setText(villageStats.getVil_total_cnt());
        villageCurrentRank.setText(villageStats.getVil_rank());
        villageMaxRank.setText(villageStats.getVil_cnt());

        peopleToday.setText(villageStats.getMember_today_cnt());
        peopleMonth.setText(villageStats.getMember_month_cnt());
        peopleCumulation.setText(villageStats.getMember_total_cnt());
        peopleCurrentRank.setText(villageStats.getMember_rank());
        peopleMaxRank.setText(villageStats.getMember_cnt());

        vStatsRecyclerView = (RecyclerView)findViewById(R.id.village_stats_recyclerview); //동네 내 랭킹 목록
        final CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        setupRecyclerView(vStatsRecyclerView);
        vStatsRecyclerView.setHasFixedSize(true);
        vStatsRecyclerView.setLayoutManager(layoutManager);
        vStatsRecyclerView.setNestedScrollingEnabled(false);
        vStatsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItemPos = layoutManager.findLastVisibleItemPosition();

                if ((visibleItemCount + lastVisibleItemPos) >= totalItemCount) {
                    Log.i("LOG", "Last Item Reached!");
                }
            }
        });

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        //final CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        //recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    }

    private void setupAdapter() {

        villageStatsMembers = new ArrayList<>();
        adapter = new VillageStatsRecyclerViewAdapter(VillageStatsActivity.this, villageStatsMembers);
        adapter.setHasStableIds(true);
        //rankUserInfos = getSortList();
        adapter.setItems(villageStatsMembers);
    }
    // adding 10 object creating dymically to arraylist and updating recyclerview when ever we reached last item

    private void loadMoreData() {

        adapter.showLoading(true);
        adapter.notifyDataSetChanged();

        // Load data after delay
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new HTTPconnVillStatsMember(VillageStatsActivity.this, adapter, villageStats.getVil_id(), (page_no++) + "").execute();
            }
        }, 1500);

    }

    private List<VillageStatsItem> getSortList() {
        List<VillageStatsItem> list = new ArrayList<>();
        list.add(new VillageStatsItem(1,"이지은","lje12",18,75,565));
        list.add(new VillageStatsItem(2, "신민아", "sma73",15,60,250));
        list.add(new VillageStatsItem(3, "민효린", "mhr", 14,20,150));

        return  list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
