package com.wonbuddism.bupmun.Board;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wonbuddism.bupmun.Board.HTTPconnection.BoardArticle;
import com.wonbuddism.bupmun.Board.HTTPconnection.HTTPconnBoardSearch;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.ListViewFooterManager;
import com.wonbuddism.bupmun.Utility.LoadingProgressManager;
import com.wonbuddism.bupmun.Utility.ProgressWaitDaialog;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BoardSearchActivity extends AppCompatActivity implements View.OnClickListener,AbsListView.OnScrollListener{

    private NiceSpinner category; //모듈 사용
    private NiceSpinner type;

    private EditText keyword;
    private TextView totalCount;
    private ListView boardSerchList;
    private ImageView goTop;
    private LinearLayout search_btn;

    private LayoutInflater mInflater;
    private LinearLayout footer;
    private ListViewFooterManager footerManager;
    private BoardArticleListViewAdapter adapter;
    private ArrayList<BoardArticle> boardArticles;
    private boolean mLockListView;
    private int page_no;
    private int size;

    private String typeData;
    private String categoryData;

    private boolean searchFlag = false;
    private Dialog progressWait;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_search);

        setData();
        setLayout();
    }

    private void setData(){
        this.mLockListView = false;  // 원래 true
        this.page_no=0;
        this.size = -1;
        this.boardArticles = new ArrayList<>();
        progressWait = new ProgressWaitDaialog(this);
    }

    public void setLayout(){
        keyword = (EditText)findViewById(R.id.board_search_bars_keyword_edittext);
        goTop = (ImageView)findViewById(R.id.board_search_body_title_top_imageview);
        goTop.setOnClickListener(this);
        search_btn = (LinearLayout)findViewById(R.id.board_search_bars_keyword_btn_layout);
        search_btn.setOnClickListener(this);

        totalCount = (TextView)findViewById(R.id.board_search_body_title_totalvalue_textview);
        totalCount.setText(boardArticles.size() + "");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.board_search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        category = (NiceSpinner)findViewById(R.id.board_search_bars_keyword_sub_category_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("질문", "의견", "기타", "동네홍보"));
        category.attachDataSource(dataset);

        type = (NiceSpinner)findViewById(R.id.board_search_bars_keyword_sub_type_spinner);
        dataset = new LinkedList<>(Arrays.asList("제목", "내용", "제목+내용", "작성자"));
        type.attachDataSource(dataset);


        boardSerchList = (ListView)findViewById(R.id.board_search_body_content_listview);
        adapter= new BoardArticleListViewAdapter(this,boardArticles,boardSerchList);
        boardSerchList.setOnScrollListener(this);
        boardSerchList.setAdapter(adapter);
        boardSerchList.setSelection(0);

       // footerManager = new ListViewFooterManager(this,boardSerchList);

    }


    private void StartSearch(){
        if(keyword.getText().toString().equals("")){
            Toast.makeText(this,"검색어를 입력해주세요",Toast.LENGTH_SHORT).show();
        }else{
            //footerManager.addFooter();
           // adapter.addFooter();
            if(searchFlag){
                setData();
            }else{
                searchFlag = true;
            }
            adapter.clear();
            addItems();
        }



    }

    private String getTypeData(){
        switch (type.getSelectedIndex()){
            case 0:
                return "00";
            case 1:
                return "01";
            case 2:
                return "02";
            case 3:
                return "03";
            default:
                return "00";
        }
    }

    private String getCategoryData(){
        switch (category.getSelectedIndex()){
            case 0:
                return "2800";
            case 1:
                return "2800";
            case 2:
                return "2800";
            case 3:
                return "2800";
            default:
                return "2800";
        }
    }

    private void addItems()
    {

        typeData = getTypeData();
        categoryData = getCategoryData();
        // 아이템을 추가하는 동안 중복 요청을 방지하기 위해 락을 걸어둡니다.
        mLockListView = true;

        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                if(size<adapter.getCount()){
                    new HTTPconnBoardSearch(BoardSearchActivity.this,adapter,totalCount,categoryData,keyword.getText().toString(),(page_no++)+"",typeData).execute();
                   /* if(totalCount.getText().toString().equals("0")){
                        footerManager.removeFooter();
                    }*/
                }else{
                 // footerManager.removeFooter();
                }
                size = adapter.getCount();

                /*new HTTPconnBoardSearch(BoardSearchActivity.this,adapter,totalCount,
                        categoryData,keyword.getText().toString(),(page_no++)+"",typeData).execute();*/
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

            if(searchFlag){

                addItems();

            }

        }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.board_search_body_title_top_imageview:
                boardSerchList.setSelectionFromTop(0, 0);
                break;
            case R.id.board_search_bars_keyword_btn_layout:
                final Dialog dialog = new ProgressWaitDaialog(this);
                dialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        StartSearch();
                    }
                },1500);


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000){
            setResult(1002);
           finish();
        }
    }
}
