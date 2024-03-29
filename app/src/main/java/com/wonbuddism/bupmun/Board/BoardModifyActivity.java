package com.wonbuddism.bupmun.Board;

import android.app.Dialog;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wonbuddism.bupmun.DataVo.BoardDetail;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnBoardModify;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Dialog.ProgressWaitDaialog;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BoardModifyActivity extends AppCompatActivity {

    private EditText title;
    private EditText contents;
    private NiceSpinner category; //모듈 사용
    private ImageView imageUpload;
    private DrawerLayout mDrawerLayout;
    private String categoryData;
    private BoardDetail boardDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_modify);
        getData();
        setLayout();
    }

    private void getData(){
     boardDetail = (BoardDetail)getIntent().getSerializableExtra("boardDetail");
    }

    private void setLayout(){
        title = (EditText)findViewById(R.id.board_modify_title_edittext);
        contents = (EditText)findViewById(R.id.board_modify_content_edittext);
        imageUpload = (ImageView)findViewById(R.id.board_modify_upload_imageview);

        title.setText(boardDetail.getTitle());
        contents.setText(boardDetail.getContent());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.board_modify_drawer_layout); //필요없음

        final Toolbar toolbar = (Toolbar) findViewById(R.id.board_modify_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        category = (NiceSpinner)findViewById(R.id.board_modify_category_spinner);

        List<String> dataset;
        dataset = new LinkedList<>(Arrays.asList("질문", "의견", "기타", "동네홍보"));


        category.attachDataSource(dataset);
        categoryData = getCategoryData();

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

    private void modify(){

        if(title.getText().toString().trim().equals("")){
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(contents.getText().toString().trim().equals("")){
            Toast.makeText(this,"내용을 입력해주세요",Toast.LENGTH_SHORT).show();
        }else{
            new HTTPconnBoardModify(this,categoryData,boardDetail.getWriteno(),
                    title.getText().toString(),contents.getText().toString()).execute();
            setResult(1000);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board_detail_write, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.board_write_action_submit:
                final Dialog dialog = new ProgressWaitDaialog(this);
                dialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        modify();
                    }
                }, 1500);

                break;
            default:
                return false;

        }
        return super.onOptionsItemSelected(item);
    }

}
