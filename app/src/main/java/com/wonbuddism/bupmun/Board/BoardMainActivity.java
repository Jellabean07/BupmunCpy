package com.wonbuddism.bupmun.Board;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wonbuddism.bupmun.DataVo.BoardArticle;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnBoardMainAll;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.ListViewFooterManager;
import com.wonbuddism.bupmun.Utility.NavigationDrawerMenu;

import java.util.ArrayList;

public class BoardMainActivity extends AppCompatActivity implements View.OnClickListener, AbsListView.OnScrollListener{

    private DrawerLayout mDrawerLayout;
    private TextView totalCount;
    private ListView boardList;
    private ImageView goTop;

    private BoardArticleListViewAdapter adapter;
    private ArrayList<BoardArticle> boardArticles;
    private boolean mLockListView;
    private int page_no;
    private int size;

    private ListViewFooterManager footerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_main);
        setData();
        setLayout();
    }

    private void refreshData(){
         this.mLockListView = false;  // 원래 true
        this.page_no=0;
        this.size = -1;
        this.boardArticles = new ArrayList<>();
        adapter.clear();
        adapter.notifyDataSetChanged();
        //adapter.removeFooter();
        //adapter.addFooter();
       /* footerManager.removeFooter();
        footerManager.addFooter();*/
        addItems();
    }

    private void setData(){
        this.mLockListView = false;  // 원래 true
        this.page_no=0;
        this.size = -1;
        this.boardArticles = new ArrayList<>();
    }

    private void setLayout(){
        goTop = (ImageView)findViewById(R.id.board_main_title_top_imageview);
        goTop.setOnClickListener(this);
        totalCount = (TextView)findViewById(R.id.board_main_title_totalvalue_textview);
        totalCount.setText(boardArticles.size() + "");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.board_drawer_layout);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.board_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.board_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardMainActivity.this,BoardRegistActivity.class);
                startActivityForResult(intent,1001);
                /*  1000내용
                    1001쓰기
                    1002검색  */
                /*Snackbar.make(view, "게시판 글 쓰기", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.board_nav_view);
        if (navigationView != null) {
            new NavigationDrawerMenu(this,mDrawerLayout).setupDrawerContent(navigationView);
        }


        boardList = (ListView)findViewById(R.id.board_main_content_listview);
        adapter= new BoardArticleListViewAdapter(this,boardArticles,boardList);
        boardList.setOnScrollListener(this);
        boardList.setAdapter(adapter);
        boardList.setSelection(0);
        /*footerManager = new ListViewFooterManager(BoardMainActivity.this,boardList);
        footerManager.addFooter();*/
        //adapter.addFooter();
        addItems();
    }

    private void addItems()
    {
        // 아이템을 추가하는 동안 중복 요청을 방지하기 위해 락을 걸어둡니다.
        mLockListView = true;

        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                if(size<adapter.getCount()){
                    new HTTPconnBoardMainAll(BoardMainActivity.this,adapter,totalCount,(page_no++)+"").execute();
                }else{
                  // footerManager.removeFooter();
                }

                size = adapter.getCount();
                //new HTTPconnBoardMainAll(BoardMainActivity.this,adapter,totalCount,(page_no++)+"").execute();
                mLockListView = false;
            }
        };

        // 속도의 딜레이를 구현하기 위한 꼼수
        Handler handler = new Handler();
        handler.postDelayed(run, 1000);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        // 현재 가장 처음에 보이는 셀번호와 보여지는 셀번호를 더한값이
        // 전체의 숫자와 동일해지면 가장 아래로 스크롤 되었다고 가정합니다.
        int count = totalItemCount - visibleItemCount;

        if(firstVisibleItem >= count && totalItemCount != 0
                && mLockListView == false)
        {
           // adapter.addFooter();
            addItems();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_search:
               Intent intent = new Intent(BoardMainActivity.this,BoardSearchActivity.class);
                startActivityForResult(intent,1002);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawers();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.board_main_title_top_imageview:
                boardList.setSelectionFromTop(0, 0);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         /*
                1000 내용
                1001 작성
                1002 검색
        */
        switch (resultCode){
            case 1000:
                refreshData();
                break;
            case 1001:
                refreshData();
                break;
            case 1002:
                refreshData();
                break;

        }
    }
}
