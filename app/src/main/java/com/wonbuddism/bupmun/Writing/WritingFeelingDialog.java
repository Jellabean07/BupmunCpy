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
