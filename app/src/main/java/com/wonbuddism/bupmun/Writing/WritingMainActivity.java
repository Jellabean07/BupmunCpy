package com.wonbuddism.bupmun.Writing;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.wonbuddism.bupmun.Database.BUPMUN_TYPING_INDEX;
import com.wonbuddism.bupmun.Database.DbAdapter;
import com.wonbuddism.bupmun.Database.TYPING_HIST;
import com.wonbuddism.bupmun.HttpConnection.HttpConnWritingValue;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.AlertDialogYN;
import com.wonbuddism.bupmun.Utility.NavigationDrawerMenu;
import com.wonbuddism.bupmun.Utility.PrefUserInfoManager;
import com.wonbuddism.bupmun.Writing.HTTPconnection.HTTPconnSyncUp;
import com.wonbuddism.bupmun.Writing.HTTPconnection.HttpParamBupmun;
import com.wonbuddism.bupmun.Writing.HTTPconnection.HttpResultBupmun;
import com.wonbuddism.bupmun.Writing.TypingProcess.HttpConnWritingListener;
import com.wonbuddism.bupmun.Writing.TypingProcess.TextSplitManager;
import com.wonbuddism.bupmun.Writing.TypingProcess.TypingDbManager;
import com.wonbuddism.bupmun.Writing.TypingProcess.TypingFinishListener;
import com.wonbuddism.bupmun.Writing.TypingProcess.TypingSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WritingMainActivity extends AppCompatActivity implements View.OnClickListener, TypingFinishListener, HttpConnWritingListener {

    private DrawerLayout mDrawerLayout;
    private TextView fullTitle;
    private TextView MainTitle;
    private TextView textLine;
    private EditText typingLine;
    private ImageView nextbtn;
    private ImageView beforebtn;
    private ListView typingList;
    private InputMethodManager imm;

    private TypingSupport typingSupport;
    private TextSplitManager splitManager;

    private DbAdapter dbAdapter;
    private BUPMUN_TYPING_INDEX bupmunitem;
    private TypingDbManager typingDbManager;


    private ArrayList<String> content;
    private ArrayList<String> words;
    private String bupmunindex;
    private int paragraph;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private int direction = 1; //1 오른쪽 -1 왼쪽

    private HttpResultBupmun hrb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_main);

        setBupmun();
       // setData();
       // DataReceiver();
       // setLayout();
       // DataProcess();
       // setTitle();
    }

    private void setBupmun() {
        HttpConnWritingValue hcwv = new HttpConnWritingValue(this);
        hcwv.setOnListener(this);
        hcwv.execute();
    }

    private void setData() {
        dbAdapter = new DbAdapter(this);
        typingDbManager = new TypingDbManager(this);
        pref = getSharedPreferences("BUPMUN_TYPING_INDEX", MODE_PRIVATE);
        editor = pref.edit();
    }


    public void setTitle(){
        MainTitle.setText(bupmunitem.getTITLE1());
        fullTitle.setText(bupmunitem.getTITLE1() + " " + bupmunitem.getTITLE2() + " " +
                bupmunitem.getTITLE3() + " " + bupmunitem.getTITLE4());
        textLine.setText(bupmunitem.getSHORTTITLE());
    }

    private void setLayout(){
        nextbtn = (ImageView)findViewById(R.id.writing_imageview_next);
        nextbtn.setOnClickListener(this);
        beforebtn = (ImageView)findViewById(R.id.writing_imageview_before);
        beforebtn.setOnClickListener(this);

        MainTitle = (TextView)findViewById(R.id.writing_maintitle_textview);
        fullTitle =  (TextView)findViewById(R.id.writing_textview_fulltitle);
        textLine = (TextView)findViewById(R.id.writing_content_original_textview);
        typingLine = (EditText)findViewById(R.id.writing_edittext_typing);
        typingList = (ListView)findViewById(R.id.writing_content_list_listview);

        typingSupport = new TypingSupport(this, textLine,typingLine, typingList);
        typingSupport.setOnFinishListener(this);

        imm = (InputMethodManager)getSystemService(WritingMainActivity.this.INPUT_METHOD_SERVICE); //키보드 컨트롤

        mDrawerLayout = (DrawerLayout) findViewById(R.id.writing_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.writing_nav_view);
        if (navigationView != null) {
            new NavigationDrawerMenu(this,mDrawerLayout).setupDrawerContent(navigationView);
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.writing_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public void DataReceiver(){
        Intent intent= getIntent();
        boolean receiver = false;
        if(intent.hasExtra("MESSAGE_RECEIVER")){
            receiver = intent.getBooleanExtra("MESSAGE_RECEIVER", false);
        }

        if(receiver){
            bupmunitem = (BUPMUN_TYPING_INDEX)getIntent().getSerializableExtra("bupmunItem");
            bupmunindex = bupmunitem.getBUPMUNINDEX();
        }else {
            bupmunitem = typingDbManager.getReceiveBupmun();
            bupmunindex = bupmunitem.getBUPMUNINDEX();
        }
        LogTest();
        //tempLogTest();
    }



    public void DataImport(){

        paragraph = hrb.getPARAGRAPH_NO();
        Log.e("paragraph", paragraph + "");
        for(int i=0; i<paragraph;i++){
            words = splitManager.TextSplit(content.get(i));
            for(int j = 0; j<words.size();j++){
                typingSupport.AddAdpaterData(words.get(j));
            }
        }
        typingSupport.AdapterDataSetChanged();

        Log.e("DataImport", "햇음");
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
        DataImport();

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

    public void NextParagraph(){
        words = splitManager.TextSplit(content.get(paragraph));
        typingSupport.setReplaceContent(words);
    }


    private void MoveContent(int NO) {
        paragraph = 0;
        content = new ArrayList<>();

        bupmunitem = typingDbManager.getMoveContent(NO);
        Log.e("NO",NO+"");
        Log.e("bupmunitem",bupmunitem.toString());
        content.add(bupmunitem.getSHORTTITLE());
        for(String s : bupmunitem.getCONTENTS_KOR().split("\r")){
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

        setTitle();
        editor.putString("bupmunindex", bupmunitem.getBUPMUNINDEX());
        editor.commit();
    }

    public void TypingLogWrite(){  //단락저장

        int paragraph_no = paragraph; //	문단번호	SMALLINT
        String bupmunindex = this.hrb.getBUPMUNINDEX();
        int tasu = content.get(paragraph).length(); //	타수	SMALLIN

        HttpParamBupmun hpb = new HttpParamBupmun(paragraph_no,bupmunindex,tasu);
        new HTTPconnSyncUp(this,hpb).execute();
        Log.e("TypingLogWrite", "햇음");
       // new LogRegistManager(this).LogRegist(bupmunitem,paragraph,content);
    }




   /* private void TypingTempExport(){
        int TYPING_CNT = 1; //	사경횟수	SMALLINT
        String TYPING_ID = new PrefUserInfoManager(WritingMainActivity.this).getUserInfo().getTYPING_ID(); //	사경아이디	CHARACTER
        String BUPMUNINDEX = bupmunitem.getBUPMUNINDEX(); //	법문인덱스키	VARCHAR
        int PARAGRAPH_NO = paragraph; //	문단번호	SMALLINT
        String CHNS_YN = "M"; //	한자포함여부	CHARACTER
        String REGIST_TIME =  new SimpleDateFormat("HHmmss").format(new Date(System.currentTimeMillis())) ; //	사경시간	CHARACTER
        String RMRK = typingSupport.getTempLog();
        TYPING_HIST_LOCAL tempLog = new TYPING_HIST_LOCAL(TYPING_CNT,TYPING_ID,BUPMUNINDEX,PARAGRAPH_NO,CHNS_YN,REGIST_TIME,RMRK);
        typingDbManager.Export_TYPING_HIST_LOCAL(tempLog);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Dialog dialog;

        switch (item.getItemId()) {
            case android.R.id.home:
                imm.hideSoftInputFromWindow(typingLine.getWindowToken(), 0);
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_writing_main_error:
                dialog = new WritingErrorDialog(WritingMainActivity.this,this,bupmunindex);
                dialog.show();
                break;
            case R.id.menu_writing_main_feeling:
                dialog = new WritingFeelingDialog(WritingMainActivity.this,this,
                        bupmunitem.getSHORTTITLE(),bupmunitem.getBUPMUNINDEX());
                dialog.show();

                break;
            case R.id.menu_writing_main_load:
                dialog = new WritingImportDialog(this);
                dialog.show();
                break;
            case R.id.menu_writing_main_save:
                imm.hideSoftInputFromWindow(typingLine.getWindowToken(), 0);
                boolean answer = new AlertDialogYN(WritingMainActivity.this).showExportDialog();
                if(answer){
                    //TypingTempExport();
                }
                break;
            default:

                break;
        }

        //로그인 상태체크
        /*if(new PrefUserInfoManager(WritingMainActivity.this).getLoginState()){

        }else{
            new AlertDialogYN(WritingMainActivity.this).showLoginDialog();
        }*/
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
                MoveContent(bupmunitem.getNO()+1);
                break;
            case R.id.writing_imageview_before:
                direction = -1;
                MoveContent(bupmunitem.getNO()-1);
                break;
        }
    }

    /*public void tempLogTest(){
        TempLogDbAdapter.open();
        ArrayList<TypingTempLog> list = TempLogDbAdapter.getTempLogs();
        for(TypingTempLog t : list){
            Log.e("tempLogTest",t.toString());
        }
        TempLogDbAdapter.close();
    }*/

    public void LogTest(){
        dbAdapter.open();
        List<TYPING_HIST> list = dbAdapter.getTypingItem(bupmunitem.getBUPMUNINDEX());
        for(TYPING_HIST t : list){
            Log.e("TypingLog",t.toString());
        }
        dbAdapter.close();
    }

    //커스텀 리스너
    @Override
    public void TypingFinish() {

        if(words.get(0).equals("\r")){
            switch (direction){
                case 1:
                    MoveContent(bupmunitem.getNO() + 1);
                    break;
                case -1:
                    MoveContent(bupmunitem.getNO()-1);
                    break;
            }
            Toast.makeText(WritingMainActivity.this,"다음 법문",Toast.LENGTH_SHORT).show();
        }else{
            TypingLogWrite();
            paragraph = paragraph + 1;
            if(paragraph <content.size()){
                NextParagraph();
                //typingDbManager.Delete_TYPING_HIST_LOCAL(bupmunitem.getBUPMUNINDEX(), paragraph - 1);
            }else{
                MoveContent(bupmunitem.getNO()+1);
                Toast.makeText(WritingMainActivity.this,"다음 법문",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void WritingListener(HttpResultBupmun hrb) {
        setLayout();
        MainTitle.setText(hrb.getTITLE1());
        fullTitle.setText(hrb.getTITLE1() + " " + hrb.getTITLE2() + " " +
                hrb.getTITLE3() + " " + hrb.getTITLE4());
        textLine.setText(hrb.getSHORTTITLE());

        this.hrb = hrb;
        this.bupmunindex = hrb.getBUPMUNINDEX();

        DataProcess();
    }
}
