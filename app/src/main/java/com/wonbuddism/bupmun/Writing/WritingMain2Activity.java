package com.wonbuddism.bupmun.Writing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wonbuddism.bupmun.Database.DbAdapter2;
import com.wonbuddism.bupmun.Database.ScriptureDTO;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.NavigationDrawerMenu;
import com.wonbuddism.bupmun.Writing.TypingProcess.TypingSupport;

import java.util.ArrayList;

public class WritingMain2Activity extends AppCompatActivity implements View.OnClickListener {

    private int idx=0;
    private String originalData;
    private String typingData;
    private TextView fullTitle;
    private TextView mainTitle;
    private TextView originalLine;
    private EditText typingLine;
    private ImageView nextbtn;
    private ImageView beforebtn;

    private ScriptureDTO scripture;
    private ListView writingScripture;
    private WritingContentAdapter adapter;
    private ArrayList<String> words;
    private SharedPreferences pref;
    SharedPreferences.Editor editor;
    private DbAdapter2 dbAdapter;
    private String bupmubindex;
    private TypingSupport support;

    private DrawerLayout mDrawerLayout;
    private InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_main);
   /*     support = new TypingSupport(this);
        scripture =  support.getScripture();*/
        ReceiverCheck();
        LayoutView();
        dataProc();
    }

    private void LayoutView(){
        nextbtn = (ImageView)findViewById(R.id.writing_imageview_next);
        nextbtn.setOnClickListener(this);
        beforebtn = (ImageView)findViewById(R.id.writing_imageview_before);
        beforebtn.setOnClickListener(this);

        writingScripture = (ListView)findViewById(R.id.writing_content_list_listview);
        adapter = new WritingContentAdapter(getApplicationContext());
        writingScripture.setAdapter(adapter);
        mainTitle = (TextView)findViewById(R.id.writing_maintitle_textview);
        mainTitle.setText(replaceChinese(scripture.getTITLE1()));
        fullTitle =  (TextView)findViewById(R.id.writing_textview_fulltitle);

        fullTitle.setText(replaceChinese(scripture.getTITLE1() + " " + scripture.getTITLE2() + " " +
                scripture.getTITLE3() + " " + scripture.getTITLE4()));

        originalLine = (TextView)findViewById(R.id.writing_content_original_textview);
        originalLine.setText(scripture.getSHORTTITLE());

        typingLine = (EditText)findViewById(R.id.writing_edittext_typing);
        typingLine.addTextChangedListener(watcher);
        typingLine.setFocusable(true);
        typingLine.requestFocus();

        imm = (InputMethodManager)getSystemService(WritingMain2Activity.this.INPUT_METHOD_SERVICE); //키보드 컨트롤
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


    public void ReceiverCheck(){

        pref=getSharedPreferences("Db", MODE_PRIVATE);
        editor = pref.edit();

        this.dbAdapter = new DbAdapter2(WritingMain2Activity.this);
        dbAdapter.open();


        Intent intent= getIntent();
        boolean receiver = false;
        if(intent.hasExtra("MESSAGE_RECEIVER")){
            receiver = intent.getBooleanExtra("MESSAGE_RECEIVER", false);
        }
        if(receiver){
            scripture = (ScriptureDTO)getIntent().getSerializableExtra("scripture");
            bupmubindex=scripture.getBUPMUNINDEX();
        }else {
            bupmubindex = pref.getString("bupmunindex", "");
            if(bupmubindex.equals("")){
                scripture = dbAdapter.getItem("jungjun000100");
            }else {
                scripture = dbAdapter.getItem(bupmubindex);
            }
        }
        editor.putString("bupmubindex", bupmubindex);
        editor.commit();

        dbAdapter.close();
    }

    //해당 텍스트뷰에 들어가는 문자열의 길이를 구함
    public float TextWidth(String str, int start, int length){
        return originalLine.getPaint().measureText(str,start,length);
    }

    public float TextWidth(String str){
        return originalLine.getPaint().measureText(str);
    }

    public String replaceChinese(String words){
        String regex = "\\((.*?)\\)";
        return words.replaceAll(regex, "");
    }

    public void dataProc(){
        String regex = "\\((.*?)\\)";
        String subtitle = scripture.getSHORTTITLE();
        String contents = scripture.getCONTENTS().replaceAll(regex,""); //한자 자르기
        words = new ArrayList<>();

        int screenWidth = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getWidth(); // 플랫폼 가로사이즈를 구함
        Log.e("screenWidth", screenWidth + "");

        //문자를 자른다
        //words.add(subtitle);
        for(String s : contents.split("\r")){
            String word = s.trim();

            //Textiwdth (문장의 텍스트뷰에서의 크기가 스크린 가로길이를 넘어가면 잘라준다
            if(TextWidth(word)>screenWidth){
                int maxLine = 0;
                //문장의 인덱스를 0 부터 조사해서 스크린가로길이를 넘어가는 순간의 인덱스를 구한다. ( MaxLine 으로 넣어서 문장을  MaxLine 만큼 자른다)
                for(int i=0; i<word.length(); i++){
                    //TextWidth(문장,인덱스,카운트)
                    if(TextWidth(word.substring(0,i+1),0,word.substring(0,i+1).length())>(screenWidth-150)){
                        Log.e("Textwidth", TextWidth(word.substring(0,i+1),0,word.substring(0,i+1).length()) + "");
                        maxLine=i;
                        break;
                    }
                }
                // 문장을 MaxLine 만큼 자르는부분
                for(int i =0; i<word.length(); i+= maxLine){
                    if((i+ maxLine)>word.length()){
                        words.add(word.substring(i,word.length()));
                        Log.e("word", word.substring(i,word.length()));
                    }else{
                        words.add(word.substring(i,i+maxLine));
                        Log.e("word2", word.substring(i,i+ maxLine));
                    }
                }
            }else{
                words.add(word);
                Log.e("contents", word);
                // Toast.makeText(this, word,Toast.LENGTH_SHORT ).show();
            }

        }
    }
    public void nextPage(){
        int Index = Integer.parseInt(scripture.getIDX())+1;
        ScriptureDTO dto=scripture;
        dbAdapter.open();
        int maxSize = dbAdapter.getCount();
        while(true){
            if(Index>maxSize){
                break;
            }
            scripture = dbAdapter.getItem(String.valueOf(Index),true);
            Log.e("getBUPMUN_CONFIRM",scripture.getBUPMUN_CONFIRM());
            if(scripture.getBUPMUN_CONFIRM().equals("0")){
                break;
            }
            scripture=dto;
            Index++;
        }
        dbAdapter.close();

        editor.putString("bupmunindex", scripture.getBUPMUNINDEX());
        editor.commit();

        //리셋
        dataProc();

        idx=0;
        adapter.clear();
        adapter.notifyDataSetChanged();
        //contentList.setAdapter(adapter);
        mainTitle.setText(scripture.getTITLE1());
        originalLine.setText(scripture.getSHORTTITLE());
        fullTitle.setText(replaceChinese(scripture.getTITLE1() + " " + scripture.getTITLE2() + " " +
                scripture.getTITLE3() + " " + scripture.getTITLE4()));
        typingLine.setText("");
        typingLine.setFocusable(true);
    }

    public void beforePage(){
        int Index = Integer.parseInt(scripture.getIDX())-1;
        ScriptureDTO dto=scripture;
        dbAdapter.open();
        while(true){
            if(Index<=0){
                break;
            }

            scripture = dbAdapter.getItem(String.valueOf(Index),true);
            Log.e("getBUPMUN_CONFIRM",scripture.getBUPMUN_CONFIRM());
            if(scripture.getBUPMUN_CONFIRM().equals("0")) {

                break;
            }
            scripture=dto;
            Index--;
        }
        dbAdapter.close();

        editor.putString("bupmunindex", scripture.getBUPMUNINDEX());
        editor.commit();

        //리셋
        dataProc();

        idx=0;
        adapter.clear();
        adapter.notifyDataSetChanged();
        //contentList.setAdapter(adapter);
        mainTitle.setText(scripture.getTITLE1());
        fullTitle.setText(replaceChinese(scripture.getTITLE1() + " " + scripture.getTITLE2() + " " +
                scripture.getTITLE3() + " " + scripture.getTITLE4()));
        originalLine.setText(scripture.getSHORTTITLE());
        typingLine.setText("");
        typingLine.setFocusable(true);
    }

    private void nextWords() { //다이얼로그 하나 띄우기
        int Index = Integer.parseInt(scripture.getIDX())+1;

        Log.e("index", Index + "");
        dbAdapter.open();
        dbAdapter.updateScripture(scripture.getBUPMUNINDEX(), scripture.getCONTENTS(), "1"); // 데이터 저장


        while(true){
            scripture = dbAdapter.getItem(String.valueOf(Index),true);
            Log.e("getBUPMUN_CONFIRM",scripture.getBUPMUN_CONFIRM());
            if(scripture.getBUPMUN_CONFIRM().equals("0")){
                break;
            }
            Index++;
        }

        Log.e("index2", String.valueOf(Index));
        /*do{
            scripture = dbAdapter.getItem(String.valueOf(Index++),true); // 다음 법문 불러오기
        }while(!scripture.getBUPMUN_CONFIRM().equals(0)); // 해당법문 내용이 0이면 아직 작성되지않는 법문, 아니라면 작성된법문이라 다음법문으로 간다*/


        dbAdapter.close();

        editor.putString("bupmunindex", scripture.getBUPMUNINDEX());
        editor.commit();

        //리셋
        dataProc();

        idx=0;
        adapter.clear();
        adapter.notifyDataSetChanged();
        //contentList.setAdapter(adapter);
        mainTitle.setText(scripture.getSHORTTITLE());
        originalLine.setText(scripture.getSHORTTITLE());
        typingLine.setText("");
        typingLine.setFocusable(true);
    }

    public boolean isEndTiping(String originalData, String typingData){
        if(typingData.equals(originalData)) return true;
        return false;
    }




    //문자 틀렸는지 맞았는지 확인
    private SpannableStringBuilder Writing(String originalData, String typingData){
        String orignal[] = originalData.split(" ");
        String typing[] = typingData.split(" ");
        ArrayList<String> Original = new ArrayList<>();
        ArrayList<String> Typing = new ArrayList<>();

        for(String o : orignal){
            Original.add(o);
        }

        for(String t : typing){
            Typing.add(t);
        }


        int pos = 0;
        int wordsColor = 0;
        int size = 0;

        //타이핑한 글자가 원래글자 길이를 넘어갔을때 예외처리리
        if(Typing.size()>Original.size()){
            size = Original.size();
        }else{
            size = Typing.size();
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        for(int i=0; i<size; i++){
            if(Typing.get(i).equals(Original.get(i))){
                wordsColor = Color.parseColor("#26c3b8");
            }else{
                wordsColor =  Color.parseColor("#fe316b");
            }
            SpannableString ss;

            ss = new SpannableString(Original.get(i)+" ");
            pos =Original.get(i).length()+1;

            ss.setSpan(new ForegroundColorSpan(wordsColor), 0, pos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(ss);
        }
        SpannableStringBuilder builders = new SpannableStringBuilder();
        builders.append(builder);

        for(int i = Typing.size(); i<Original.size(); i++){
            if(Original.size() > (i + 1)){
                builders.append(Original.get(i) + " ");
            }else{
                builders.append(Original.get(i));
            }
        }

        return builders;
    }

    //문자 입력 감지
    TextWatcher watcher = new TextWatcher() {
        //텍스트변경후
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //Toast.makeText(TestText.this,"텍스트변경후"+(i++),Toast.LENGTH_SHORT).show();
        }

        //문자길이 늘어날떄마다
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //originalData = words.get(idx);
            originalData = originalLine.getText().toString().trim();
            typingData = typingLine.getText().toString();

            //본문과 입력한 문장이 일치하면 다은문장으로 넘어가고 아니라면 계속 타이핑 검사한다
            if(isEndTiping(originalData,typingData)){
                adapter.add(originalData);
                adapter.notifyDataSetChanged();
                writingScripture.setSelectionFromTop(writingScripture.getCount(), 0);

                //마지막 문장일경우 다음 법문으로 넘어간다
                if(idx<words.size()){
                    originalLine.setText(words.get(idx++).trim());
                    typingLine.setText("");
                }else{
                    originalLine.setText("");
                    nextWords();
                    Log.e("idx",scripture.getIDX()+" : ok");
                }

                // Toast.makeText(getApplicationContext(), "다음 문장", Toast.LENGTH_SHORT).show();
            }else{
                Log.e("originalData",originalData);
                Log.e("typing", typingData);
//                originalLine.setText(Writing(originalData,typingData));
                originalLine.setText(Writing(originalData,typingData));
            }

        }
        //텍스트 변경 할때마다
        @Override
        public void afterTextChanged(Editable s) {
            //Toast.makeText(TestText.this,"텍스트변경할떄마다"+(i++),Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Dialog dialog;

        switch (item.getItemId()) {
            case android.R.id.home:
                imm.hideSoftInputFromWindow(typingLine.getWindowToken(), 0);
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_writing_main_error:
                dialog = new WritingErrorDialog(WritingMain2Activity.this,this,bupmubindex);
                dialog.show();
                break;
            case R.id.menu_writing_main_feeling:
                dialog = new WritingFeelingDialog(WritingMain2Activity.this,this,scripture.getSHORTTITLE(),scripture.getBUPMUNINDEX());
                dialog.show();
                break;
            case R.id.menu_writing_main_load:
                dialog = new WritingImportDialog(this);
                dialog.show();
                break;
            case R.id.menu_writing_main_save:
                imm.hideSoftInputFromWindow(typingLine.getWindowToken(), 0);
                AlertDialog.Builder d = new AlertDialog.Builder(WritingMain2Activity.this);
                d.setTitle("임시저장");
                d.setMessage("정말 저장 하시겠습니꺄?");
                // d.setIcon(R.drawable.warnning_off);

                d.setPositiveButton("예",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(WritingMain2Activity.this,"저장 되었습니다",Toast.LENGTH_SHORT).show();
                        //WritingMain2Activity.this.finish();
                    }
                });

                d.setNegativeButton("아니요",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
                d.show();
                break;
            default:
                return false;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
                AlertDialog.Builder d = new AlertDialog.Builder(WritingMain2Activity.this);
                d.setTitle("법문쓰기 종료");
                d.setMessage("정말 종료 하시겠습니꺄?");
                // d.setIcon(R.drawable.warnning_off);

                d.setPositiveButton("예",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        WritingMain2Activity.this.finish();
                    }
                });

                d.setNegativeButton("아니요",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
                d.show();

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
                nextPage();
                break;
            case R.id.writing_imageview_before:
                beforePage();
                break;
        }
    }
}
