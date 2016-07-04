package com.wonbuddism.bupmun.Writing.TypingProcess;


import android.app.Activity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TextSplitManager {

    private Activity activity;
    private TextView textLine;

    public TextSplitManager(Activity activity, TextView textLine) {
        this.activity = activity;
        this.textLine = textLine;

    }

    //해당 텍스트뷰에 들어가는 문자열의 길이를 구함
    private float TextWidth(String str, int start, int length){
        return textLine.getPaint().measureText(str, start, length);
    }

    private float TextWidth(String str){
        return textLine.getPaint().measureText(str);
    }

    private String replaceChinese(String words){
        String regex = "\\((.*?)\\)";
        return words.replaceAll(regex, "");
    }

    public ArrayList<String> TextSplit(String content){
        ArrayList<String> words = new ArrayList<>();
        String word = content;
        int screenWidth = ((WindowManager) activity.getSystemService(activity.WINDOW_SERVICE)).getDefaultDisplay().getWidth(); // 플랫폼 가로사이즈를 구함

        //Textiwdth (문장의 텍스트뷰에서의 크기가 스크린 가로길이를 넘어가면 잘라준다
        if(TextWidth(word)>screenWidth){
            int maxLine = 0;
            //문장의 인덱스를 0 부터 조사해서 스크린가로길이를 넘어가는 순간의 인덱스를 구한다. ( MaxLine 으로 넣어서 문장을  MaxLine 만큼 자른다)
            for(int i=0; i<word.length(); i++){
                //TextWidth(문장,인덱스,카운트)
                if(TextWidth(word.substring(0,i+1),0,word.substring(0,i+1).length())>(screenWidth-150)){
                    maxLine=i;
                    break;
                }
            }
            // 문장을 MaxLine 만큼 자르는부분
            for(int i =0; i<word.length(); i+= maxLine){
                if((i+ maxLine)>word.length()){
                    words.add(word.substring(i,word.length()));
                }else{
                    words.add(word.substring(i,i+maxLine));
                }
            }
        }else{
            words.add(word);
        }


        return words;
    }
}
