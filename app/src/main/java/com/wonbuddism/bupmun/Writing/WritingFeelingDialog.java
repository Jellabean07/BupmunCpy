package com.wonbuddism.bupmun.Writing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.wonbuddism.bupmun.DataVo.FeelingMemo;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnFeelingSplit;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Common.ListViewFooterManager;

import java.util.ArrayList;

public class WritingFeelingDialog extends Dialog implements View.OnClickListener, AbsListView.OnScrollListener{

    private TextView regist;
    private TextView cancel;
    private ListView feelings;
    private WritingFeelingDialogAdapter adapter;
    private Context context;
    private Activity activity;
    private String shortTitle;
    private String bupmunindex;
    private FeelingMemo feelingMemo;

    private ArrayList<FeelingMemo> feelingMemos;
    private boolean mLockListView;
    private int page_no;
    private int size;
    private ListViewFooterManager footerManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.dialog_writing_feeling);
        setData();
        setLayout();

    }

    public WritingFeelingDialog(Context context,Activity activity, String shortTitle, String bupmunindex) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.activity = activity;
        this.shortTitle = shortTitle;
        this.bupmunindex = bupmunindex;

    }

    private void setData(){
        this.mLockListView = false;  // 원래 true
        this.page_no=0;
        this.size = -1;
        feelingMemos = new ArrayList<>();
    }

    private void setLayout() {
        regist = (TextView)findViewById(R.id.dialog_writing_feeling_regist_textview);
        regist.setOnClickListener(this);
        cancel = (TextView)findViewById(R.id.dialog_writing_feeling_cancel_textview);
        cancel.setOnClickListener(this);

        adapter = new WritingFeelingDialogAdapter(context,activity,feelingMemos,this);
        feelings = (ListView)findViewById(R.id.dialog_writing_feeling_listview);
        feelings.setAdapter(adapter);
        feelings.setOnScrollListener(this);
        feelings.setSelection(0);
        footerManager = new ListViewFooterManager(activity,feelings);
        footerManager.addFooter();
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
                    Log.e("bupmunindex", bupmunindex);
                    Log.e("page_no", page_no+"");
                    new HTTPconnFeelingSplit(activity,adapter,bupmunindex,(page_no++)+"").execute();
                }else{
                    footerManager.removeFooter();
                }

                size = adapter.getCount();

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
            addItems();
        }
    }
    /*private ArrayList<FeelingItem> getSortList() {
        ArrayList<FeelingItem> list = new ArrayList<>();

        if(shortTitle.equals("일원상 신앙")){
            list.add(new FeelingItem("1","와 좋다","정전","일원상신앙","2015.12.28"));
            list.add(new FeelingItem("2","마음이 편해집니다","정전","일원상신앙","2015.12.28"));
        }else  if(shortTitle.equals("개교의 동기")){
            list.add(new FeelingItem("1","새벽감성","대종경","일원상신앙","2015.12.28"));
            list.add(new FeelingItem("2","난 눈물이 좋다","대종경","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("3","나는 머리가 아닌","대종경","일원상신앙","2015.12.30"));
        }else  if(shortTitle.equals("교법의 총설")){
            list.add(new FeelingItem("1","난 가끔 눈물을 흘린다","원불교교사","일원상신앙","2015.12.28"));
        }else  if(shortTitle.equals("일원상의 진리")){
            list.add(new FeelingItem("1","가끔 눈물을 참을 수 없는 내가 별루다","불조요경","일원상신앙","2015.12.29"));
        }else  if(shortTitle.equals("일원상의 수행")){
            list.add(new FeelingItem("1","맘이 아파서","예전","일원상신앙","2015.12.29"));
        }else  if(shortTitle.equals("일원상의 법어")){
            list.add(new FeelingItem("1","좋은 거야..","정산종사법어","일원상신앙","2015.12.29"));
        }else  if(shortTitle.equals("일원상 서원문")) {
            list.add(new FeelingItem("1","뭐 꼭 슬펴야만 우는건아니잖아^^","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("2","맘으로 우는 내가 좋다","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("3","뭐 꼭 슬펴야만 우는건아니잖아^^","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("4","맘으로 우는 내가 좋다","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("5","뭐 꼭 슬펴야만 우는건아니잖아^^","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("6","맘으로 우는 내가 좋다","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("7","뭐 꼭 슬펴야만 우는건아니잖아^^","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("8","맘으로 우는 내가 좋다","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("9","뭐 꼭 슬펴야만 우는건아니잖아^^","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("10","맘으로 우는 내가 좋다","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("11","뭐 꼭 슬펴야만 우는건아니잖아^^","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("12","맘으로 우는 내가 좋다","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("13","뭐 꼭 슬펴야만 우는건아니잖아^^","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("14","맘으로 우는 내가 좋다","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("15","뭐 꼭 슬펴야만 우는건아니잖아^^","대산종사법어","일원상신앙","2015.12.30"));
            list.add(new FeelingItem("16","맘으로 우는 내가 좋다","대산종사법어","일원상신앙","2015.12.30"));

        }

        Collections.sort(list, new NoAscCompare());


        return list;
    }


    static class NoAscCompare implements Comparator<FeelingItem> {
        @Override
        public int compare(FeelingItem arg0, FeelingItem arg1) {
            //오름차순
            return Integer.parseInt(arg0.getNo()) < Integer.parseInt(arg1.getNo()) ? -1 : Integer.parseInt(arg0.getNo()) > Integer.parseInt(arg1.getNo()) ? 1:0;
        }
    }*/

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id) {
            case R.id.dialog_writing_feeling_regist_textview:
                dismiss();
                Dialog dialog = new WritingFeelingRegistDialog(context,activity, shortTitle,bupmunindex);
                dialog.show();
                break;
            case R.id.dialog_writing_feeling_cancel_textview:
                dismiss();
                break;
        }
    }


}
