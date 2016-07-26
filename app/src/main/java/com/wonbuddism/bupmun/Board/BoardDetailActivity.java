package com.wonbuddism.bupmun.Board;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.BoardComment;
import com.wonbuddism.bupmun.DataVo.BoardDetail;
import com.wonbuddism.bupmun.HttpConnection.HttpConnBoardCommDelete;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnBoardCommList;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnBoardCommRegist;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnBoardDelete;
import com.wonbuddism.bupmun.Listener.BoardCommDeleteListener;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Common.CustomLinearLayoutManager;
import com.wonbuddism.bupmun.Common.PrefUserInfoManager;
import com.wonbuddism.bupmun.Dialog.ProgressWaitDaialog;
import com.wonbuddism.bupmun.Common.RecyclerViewScrollListener;

import java.util.ArrayList;

public class BoardDetailActivity extends AppCompatActivity implements View.OnClickListener, BoardCommDeleteListener {

    private TextView actionbarTitle;
    private TextView title;
    private TextView id;
    private TextView date;
    private TextView hit;
    private ImageView uploadfile_image;
    private TextView uploadfile;
    private TextView content;

    private LinearLayout manangeLine;
    private TextView deleteArticle;
    private TextView modifyArticle;

    private NestedScrollView nsv;

    private TextView comment_count;
    private RecyclerView comments;
    private Button comment_regist;
    private EditText comment_content;

    private InputMethodManager imm;
    private BoardCommentRecyclerViewAdapter adapter;
    private BoardDetail boardDetail;
    private ArrayList<BoardComment> boardComments;
    private int page_no;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        setData();
        setLayout();
    }

    private String getCategoryName(String boardno){
        switch (Integer.parseInt(boardno)){
            case 2802:
                return "질문";
            case 2801:
                return "의견";
            case 2800:
                return "기타";
            case 2803:
                return "동네홍보";
            case 2804:
                return "공지";
            default:
                return "기타";
        }
    }

    public void setData(){
        Intent intent = getIntent();
        boardDetail = (BoardDetail)intent.getSerializableExtra("boardDetail");

        boardComments = new ArrayList<>();
        mHandler = new Handler();
        page_no = 0;
    }

    public void setLayout(){
        manangeLine = (LinearLayout)findViewById(R.id.board_detail_middle_manage);
        deleteArticle = (TextView)findViewById(R.id.board_detail_middle_manage_delete_textview);
        modifyArticle =  (TextView)findViewById(R.id.board_detail_middle_manage_modify_textview);

        Log.e("userid",boardDetail.getUserid());
        Log.e("userid",new PrefUserInfoManager(this).getUserInfo().getUSERID());

        if (boardDetail.getUserid().equals(new PrefUserInfoManager(this).getUserInfo().getUSERID())) {
            manangeLine.setVisibility(View.VISIBLE);

        }else{
            manangeLine.setVisibility(View.GONE);
        }
        deleteArticle.setOnClickListener(this);
        modifyArticle.setOnClickListener(this);

        /*글내용*/
        actionbarTitle = (TextView)findViewById(R.id.board_detail_toolbar_title);
        title = (TextView)findViewById(R.id.board_detail_top_title_textview);
        id = (TextView)findViewById(R.id.board_detail_top_id_textview);
        date = (TextView)findViewById(R.id.board_detail_top_sub_date_textview);
        hit = (TextView)findViewById(R.id.board_detail_top_sub_hit_textview);
        content = (TextView)findViewById(R.id.board_detail_middle_content_textview);

        uploadfile_image = (ImageView)findViewById(R.id.board_detail_top_file_image_imageview);
        uploadfile = (TextView)findViewById(R.id.board_detail_top_upload_file_textview);
        uploadfile.setVisibility(View.GONE); // -> 일단 안보이게 처리

        actionbarTitle.setText(getCategoryName(boardDetail.getBoardno()));
        title.setText(boardDetail.getTitle());
        id.setText(boardDetail.getUserid());
        date.setText(boardDetail.getWritetime()+"");
        hit.setText(boardDetail.getReadcnt()+"");

        Spanned sp = Html.fromHtml(boardDetail.getContent());
        content.setText(sp);
        content.setMovementMethod(LinkMovementMethod.getInstance());

        //content.setText(getResources().getString(R.string.info_content2_title4));

        /*덧글*/
        comment_regist = (Button)findViewById(R.id.board_detail_comment_top_lower_button);
        comment_regist.setOnClickListener(this);
        comment_content = (EditText)findViewById(R.id.board_detail_comment_top_lower_edittext);
        comment_count =(TextView)findViewById(R.id.board_detail_comment_top_upper_count_textview);
        comment_count.setText(boardDetail.getReplydepth());


       /* comments = (RecyclerView)findViewById(R.id.board_detail_recycler_view);
        comments.setHasFixedSize(true);
        comments.setNestedScrollingEnabled(false);
        setupRecyclerView(comments);*/

        comments = (RecyclerView)findViewById(R.id.board_detail_recycler_view); //동네 내 랭킹 목록
        setupRecyclerView(comments);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.board_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /* nsv = (NestedScrollView)findViewById(R.id.board_detail_nestedscrollview);
        nsv.smoothScrollTo(0, 0);*/

        imm = (InputMethodManager)getSystemService(BoardDetailActivity.this.INPUT_METHOD_SERVICE); //키보드 컨트롤
    }

    private void setupRecyclerView(final RecyclerView recyclerView) {
        final CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapter();
        recyclerView.setAdapter(adapter);
        loadMoreData();
        recyclerView.setNestedScrollingEnabled(false);
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
        boardComments = new ArrayList<>();
        /**/
        adapter = new BoardCommentRecyclerViewAdapter(BoardDetailActivity.this,this, boardComments);
        adapter.setHasStableIds(true);
        adapter.setItems(boardComments);
        adapter.setOnDeleteListener(this);
    }


    private void loadMoreData() {

        adapter.showLoading(true);
        adapter.notifyDataSetChanged();

        // Load data after delay
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new HTTPconnBoardCommList(BoardDetailActivity.this, adapter, comment_count,
                        boardDetail.getBoardno(), boardDetail.getWriteno(), (page_no++) + "").execute();
            }
        }, 1500);

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
        int id = v.getId();
        switch (id){
            case R.id.board_detail_comment_top_lower_button:
                final Dialog dialogWait = new ProgressWaitDaialog(BoardDetailActivity.this);
                dialogWait.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogWait.dismiss();
                        regist();
                    }
                }, 1500);

                break;
            case R.id.board_detail_middle_manage_delete_textview:
                delete();
                break;
            case R.id.board_detail_middle_manage_modify_textview:
                Intent intent = new Intent(BoardDetailActivity.this, BoardModifyActivity.class);
                intent.putExtra("boardDetail",boardDetail);
                startActivityForResult(intent,1000);
                break;
        }
    }

    private void refreshAdapter(){
        loadMoreData();
        boardComments = new ArrayList<>();
        page_no = 0;
        adapter.clear();
        adapter.notifyDataSetChanged();
    }

    private void regist(){
        if(comment_content.getText().toString().trim().equals("")){
            Toast.makeText(this, "덧글 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(comment_content.getText().toString().trim().length()>=150){
            Toast.makeText(this, "글자수는 150자를 넘어갈 수 없습니다", Toast.LENGTH_SHORT).show();
        }else {
            new HTTPconnBoardCommRegist(this,boardDetail.getBoardno(),boardDetail.getWriteno(),
                    comment_content.getText().toString().trim()).execute();
            refreshAdapter();
            comment_content.setText("");
            imm.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);


        }
    }
    private void delete(){
        AlertDialog.Builder d = new AlertDialog.Builder(this);
        d.setTitle("게시글 삭제");
        d.setMessage("해당 게시글을 삭제 하시겠습니까?");
        // d.setIcon(R.drawable.warnning_off);

        d.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Dialog dialogWait = new ProgressWaitDaialog(BoardDetailActivity.this);
                dialogWait.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogWait.dismiss();
                        new HTTPconnBoardDelete(BoardDetailActivity.this, boardDetail.getBoardno(), boardDetail.getWriteno()).execute();
                        setResult(1000);
                        finish();
                    }
                }, 1500);
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


    @Override
    public void DeleteItem(BoardComment comment) {
        new HttpConnBoardCommDelete(this,boardDetail.getBoardno(),boardDetail.getWriteno(),comment.getSeqno()).execute();
        refreshAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000){
            setResult(1000);
            finish();
        }
    }
}
