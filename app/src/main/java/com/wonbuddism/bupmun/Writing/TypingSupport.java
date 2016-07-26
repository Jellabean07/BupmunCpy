package com.wonbuddism.bupmun.Writing;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.wonbuddism.bupmun.Listener.TypingFinishListener;
import com.wonbuddism.bupmun.Writing.WritingContentAdapter;

import java.util.ArrayList;

public class TypingSupport {
    private Activity activity;

    private TextView textLine;
    private EditText editLine;
    private ListView typingList;
    private WritingContentAdapter writingContentAdapter;

    private String textData;
    private String editData;
    private String tempLog = "";
    private int index;

    private ArrayList<String> content;

    private String DifferentColor;
    private String EqualsColor;

    private TypingFinishListener listener;

    public TypingSupport(Activity activity, TextView textLine, EditText editLine, ListView typingList) {
        this.activity = activity;
        this.textLine = textLine;
        this.editLine = editLine;
        this.typingList = typingList;
        this.writingContentAdapter =  new WritingContentAdapter(activity);
        this.typingList.setAdapter(this.writingContentAdapter);
        this.DifferentColor = "#fe316b" ; //default
        this.EqualsColor = "#26c3b8";

    }

   public void Warcher(ArrayList<String> content){
        this.content= content;
        this.index = 0;

        editLine.addTextChangedListener(watcher);
        editLine.setFocusable(true);
        editLine.requestFocus();

    }

    private boolean isEqualsStr(String str1, String str2){
        if(str2.equals(str1)) return true;
        return false;
    }

    public void TypingString(){
        textData = content.get(index).trim();
        editData = editLine.getText().toString();
        setTempLog();
        Log.e("임시저장", getTempLog());
        //본문과 입력한 문장이 일치하면 다은문장으로 넘어가고 아니라면 계속 타이핑 검사한다
        if(isEqualsStr(textData, editData)){
            index = index + 1;
            writingContentAdapter.add(textData);
            writingContentAdapter.notifyDataSetChanged();
            typingList.setSelectionFromTop(typingList.getCount(), 0);
            //마지막 문장일경우 다음 법문으로 넘어간다
            if(index<content.size()){
                textData = content.get(index).trim();
                textLine.setText(textData);
                editLine.setText("");
            }else{
                listener.TypingFinish();
            }

        }else{

            textLine.setText(Typing(textData, editData));
        }
    }


    private SpannableStringBuilder Typing(String textData, String editData){
        int position;
        int wordsColor;
        int wordsSize;

        ArrayList<String> textWords = new ArrayList<>();
        ArrayList<String> editWords = new ArrayList<>();

        for(String s : textData.split(" ")){
            textWords.add(s);
        }

        for(String s :  editData.split(" ")){
            editWords.add(s);
        }

        //타이핑한 글자가 원래글자 길이를 넘어갔을때 예외처리리
        if(editWords.size()>textWords.size()){
            wordsSize = textWords.size();
        }else{
            wordsSize = editWords.size();
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        for(int i=0; i<wordsSize; i++){
            if(editWords.get(i).equals(textWords.get(i))){
                wordsColor = Color.parseColor(EqualsColor);
            }else{
                wordsColor =  Color.parseColor(DifferentColor);
            }
            SpannableString ss;

            ss = new SpannableString(textWords.get(i)+" ");
            position =textWords.get(i).length()+1;

            ss.setSpan(new ForegroundColorSpan(wordsColor), 0, position, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(ss);
        }
        SpannableStringBuilder builders = new SpannableStringBuilder();
        builders.append(builder);

        for(int i = editWords.size(); i<textWords.size(); i++){
            if(textWords.size() > (i + 1)){
                builders.append(textWords.get(i) + " ");
            }else{
                builders.append(textWords.get(i));
            }
        }

        return builders;
    }

     TextWatcher watcher = new TextWatcher() {
        //텍스트변경후
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        //문자길이 늘어날떄마다
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            TypingString();
        }
        //텍스트 변경 할때마다
        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void setOnFinishListener(TypingFinishListener listener) {
        this.listener = listener;
    }

    public void setReplaceContent(ArrayList<String> content) {
        this.tempLog = "";
        this.content = content;
        this.index = 0;
        textLine.setText(this.content.get(this.index).trim());
        editLine.setText("");
        editLine.setFocusable(true);

    }

    public void setReplaceContent(ArrayList<String> content,boolean flag) {
        this.tempLog = "";
        this.content = content;
        this.index = 0;
        textLine.setText(this.content.get(this.index).trim());
        editLine.setText("");
        editLine.setFocusable(true);
      //  writingContentAdapter.clear();
       // writingContentAdapter.notifyDataSetChanged();
    }

    public void setTempLog(){
        tempLog = editLine.getText().toString();
    }

    public String getTempLog(){
        String temp = "";
        for(int i=0; i<index; i++){
            temp += content.get(i);
        }
        temp += tempLog;
        return temp;
    }

    public void AddAdpaterData(String str){
        writingContentAdapter.add(str);

    }

    public void AdapterClearDataSetChanged() {
        writingContentAdapter.clear();
        writingContentAdapter.notifyDataSetChanged();
    }


    public void AdapterDataSetChanged(){
        writingContentAdapter.notifyDataSetChanged();
    }

    public void setDifferentColor(String differentColor) {
        DifferentColor = differentColor;
    }

    public void setEqualsColor(String equalsColor) {
        EqualsColor = equalsColor;
    }


}
