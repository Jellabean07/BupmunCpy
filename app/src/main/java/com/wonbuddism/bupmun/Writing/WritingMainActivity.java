package com.wonbuddism.bupmun.Writing;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wonbuddism.bupmun.Dialog.WritingErrorDialog;
import com.wonbuddism.bupmun.Dialog.WritingSCDialog;
import com.wonbuddism.bupmun.HttpConnection.HttpConnWritingNext;
import com.wonbuddism.bupmun.HttpConnection.HttpConnWritingPrevious;
import com.wonbuddism.bupmun.HttpConnection.HttpConnWritingValue;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Dialog.AlertDialogYN;
import com.wonbuddism.bupmun.Common.NavigationDrawerMenu;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnSyncUp;
import com.wonbuddism.bupmun.DataVo.HttpParamBupmun;
import com.wonbuddism.bupmun.DataVo.HttpResultBupmun;
import com.wonbuddism.bupmun.Listener.HttpConnWritingListener;
import com.wonbuddism.bupmun.Listener.TypingFinishListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WritingMainActivity extends AppCompatActivity implements View.OnClickListener, TypingFinishListener, HttpConnWritingListener, DialogInterface.OnDismissListener {

    @Bind(R.id.writing_drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.writing_textview_fulltitle) TextView fullTitle;
    @Bind(R.id.writing_maintitle_textview) TextView MainTitle;
    @Bind(R.id.writing_content_original_textview) TextView textLine;
    @Bind(R.id.writing_edittext_typing) EditText typingLine;
    @Bind(R.id.writing_imageview_next) ImageView nextbtn;
    @Bind(R.id.writing_imageview_before) ImageView beforebtn;
    @Bind(R.id.include_writing_glyphs_imageview) ImageView scbtn;
    @Bind(R.id.writing_content_list_listview) ListView typingList;
    @Bind(R.id.writing_nav_view) NavigationView navigationView;
    @Bind(R.id.writing_toolbar) Toolbar toolbar;

    private InputMethodManager imm;
    private TypingSupport typingSupport;
    private TextSplitManager splitManager;

    private String TAG = "WritingMainActivity";

    private ArrayList<String> content;
    private ArrayList<String> words;
    private int paragraph;
    private HttpResultBupmun hrb;


    private int direction = 1; //1 오른쪽 -1 왼쪽
    private boolean flag = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_main);
        setLayout();
        setBupmun();

    }

    private void setLayout(){
        ButterKnife.bind(this);

        nextbtn.setOnClickListener(this);
        beforebtn.setOnClickListener(this);
        scbtn.setOnClickListener(this);

        typingSupport = new TypingSupport(this, textLine,typingLine, typingList);
        typingSupport.setOnFinishListener(this);

        imm = (InputMethodManager)getSystemService(WritingMainActivity.this.INPUT_METHOD_SERVICE); //키보드 컨트롤

        if (navigationView != null) {
            new NavigationDrawerMenu(this,mDrawerLayout).setupDrawerContent(navigationView);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setBupmun() {
        Intent intent = getIntent();
        if(intent.hasExtra("httpResultBupmun")){
            hrb = (HttpResultBupmun)intent.getSerializableExtra("httpResultBupmun");
            setResultBupmun();
        }else {
            HttpConnWritingValue hcwv = new HttpConnWritingValue(this);
            hcwv.setOnListener(this);
            hcwv.execute();
        }
    }


    public void setTitle(){
        MainTitle.setText(hrb.getTITLE1());
        fullTitle.setText(hrb.getTITLE1() + " " + hrb.getTITLE2() + " " +
                hrb.getTITLE3() + " " + hrb.getTITLE4());
        textLine.setText(hrb.getSHORTTITLE());
    }



    public void DataImport(){
        paragraph = hrb.getPARAGRAPH_NO();
        Log.e(TAG, "paragraph : " + paragraph);
        for(int i=0; i<paragraph;i++){
            words = splitManager.TextSplit(content.get(i));
            for(int j = 0; j<words.size();j++){
                typingSupport.AddAdpaterData(words.get(j));
            }
        }
        typingSupport.AdapterDataSetChanged();

        Log.e(TAG, "DataImport : Do it");
    }


    public void UpLoad(){  //단락저장
        int paragraph_no = paragraph + 1;
        String bupmunindex = this.hrb.getBUPMUNINDEX();
        int tasu = content.get(paragraph).length();

        HttpParamBupmun hpb = new HttpParamBupmun(paragraph_no,bupmunindex,tasu);
        new HTTPconnSyncUp(this,hpb).execute();
        Log.e(TAG, "UpLoad : 사경시도");
    }

    public void NextParagraph(){
        words = splitManager.TextSplit(content.get(paragraph));
        typingSupport.setReplaceContent(words);
    }

    private void MoveBupmun() {
        paragraph = 0;
        content = new ArrayList<>();

        content.add(hrb.getSHORTTITLE());
        for(String s : hrb.getCONTENTS_KOR().split("\r")){
            content.add(s.trim());
        }

        typingSupport.AdapterClearDataSetChanged();

        DataImport();

        if(paragraph<content.size()){
            words = splitManager.TextSplit(content.get(paragraph));
            Log.e("다음문장 스플릿","스플릿");
        }else{
            words = new ArrayList<>();
            words.add("\r");
            Log.e("다음문장 노스플릿", "노스플릿");
        }

        typingSupport.setReplaceContent(words, true);
    }


    public void DataProcess(){
        paragraph = 0;
        words = new ArrayList<>();
        content = new ArrayList<>();
        splitManager=new TextSplitManager(this, textLine);

        content.add(hrb.getSHORTTITLE());
        for(String s : hrb.getCONTENTS_KOR().split("\r")){
            content.add(s.trim());
        }

        Log.e("content_size", content.size() + "");
        DataImport(); //이전 사경내용 불러오기

        if(paragraph<content.size()){
            words = splitManager.TextSplit(content.get(paragraph));
            Log.e("시작 스플릿","스플릿");
        }else{
            words = new ArrayList<>();
            words.add("\r");
            Log.e("시작 노 스플릿", "노 스플릿");
        }

        typingSupport.Warcher(words);
    }
    private void NextBupmun(){
        HttpConnWritingNext hcwn = new HttpConnWritingNext(this,hrb.getBUPMUNINDEX(),hrb.getBUPMUNSORT());
        hcwn.setOnListener(this);
        hcwn.execute();
    }

    private void PreviousBupmun(){
        HttpConnWritingPrevious hcwp = new HttpConnWritingPrevious(this,hrb.getBUPMUNINDEX(),hrb.getBUPMUNSORT());
        hcwp.setOnListener(this);
        hcwp.execute();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Dialog dialog;
        switch (item.getItemId()) {
            case android.R.id.home:
                imm.hideSoftInputFromWindow(typingLine.getWindowToken(), 0);
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_writing_main_error:
                dialog = new WritingErrorDialog(WritingMainActivity.this,this,hrb.getBUPMUNINDEX());
                dialog.show();
                break;
            case R.id.menu_writing_main_feeling:
                dialog = new WritingFeelingDialog(WritingMainActivity.this,this,
                        hrb.getSHORTTITLE(),hrb.getBUPMUNINDEX());
                dialog.show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_writing_main, menu);
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //빽(취소)키가 눌렸을때 종료여부를 묻는 다이얼로그 띄움
        if((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerLayout.closeDrawers();
            }else{
                new AlertDialogYN(WritingMainActivity.this).showExitDialog();

                return true;
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.writing_imageview_next:
                direction = 1;
                NextBupmun();
                break;
            case R.id.writing_imageview_before:
                direction = -1;
                PreviousBupmun();
                break;
            case R.id.include_writing_glyphs_imageview:
                Dialog dialog = new WritingSCDialog(WritingMainActivity.this);
                dialog.setOnDismissListener(this);
                dialog.show();
                break;
        }
    }




    //커스텀 리스너
    @Override
    public void TypingFinish() {
        if(words.get(0).equals("\r")){
            switch (direction){
                case 1:
                    NextBupmun();
                    break;
                case -1:
                    PreviousBupmun();
                    break;
            }
            Toast.makeText(WritingMainActivity.this,"다음 법문",Toast.LENGTH_SHORT).show();
        }else{
            UpLoad();
            paragraph = paragraph + 1;
            if(paragraph <content.size()){
                NextParagraph();
            }else{
                NextBupmun();
                Toast.makeText(WritingMainActivity.this,"다음 법문",Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void WritingListener(HttpResultBupmun hrb) {
        this.hrb = hrb;
        setResultBupmun();

    }

    private void setResultBupmun(){
        MainTitle.setText(hrb.getTITLE1());
        fullTitle.setText(hrb.getTITLE1() + " " + hrb.getTITLE2() + " " +
                hrb.getTITLE3() + " " + hrb.getTITLE4());
        textLine.setText(hrb.getSHORTTITLE());

        if(flag){
            DataProcess();
            flag = false;
        }else{
            MoveBupmun();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        WritingSCDialog scDialog = (WritingSCDialog) dialog ;
        String name = typingLine.getText() + scDialog.getString() ;
        typingLine.setText( name ) ;
    }
}
