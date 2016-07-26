package com.wonbuddism.bupmun.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Vil.VillageMemberDialogAdapter;
import com.wonbuddism.bupmun.Common.ListViewFooterManager;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnVillMember;
import com.wonbuddism.bupmun.DataVo.VillageMember;

import java.util.ArrayList;

public class VillageMemberDialog extends Dialog implements View.OnClickListener , AbsListView.OnScrollListener{
    private TextView cancel;
    private ListView members;
    private VillageMemberDialogAdapter adapter;
    private Activity activity;
    private ArrayList<VillageMember> villageMembers;
    private ArrayList<VillageMember> mRowList;
    private boolean mLockListView;
    private String vil_id;
    private int page_no;
    private int size;
    private ListViewFooterManager footer;
  /*  private LayoutInflater mInflater;
    private LinearLayout footer;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.dialog_village_member);
        setLayout();

    }

    public VillageMemberDialog(Activity activity, String vil_id) {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        this.activity = activity;
        //this.context = context;
        this.mLockListView = false;  // 원래 true
        this.mRowList = new ArrayList<>();
        this.vil_id= vil_id;
        this.page_no=0;
        this.size = -1;
        this.villageMembers = new ArrayList<>();


    }


    private void setLayout() {
        cancel = (TextView)findViewById(R.id.dialog_village_member_close_textview);
        cancel.setOnClickListener(this);


        members = (ListView)findViewById(R.id.dialog_village_member_listview);
        adapter = new VillageMemberDialogAdapter(activity,villageMembers,members);
        members.setOnScrollListener(this);
        members.setAdapter(adapter);

        //adapter.addFooter();
        addItems();
        /*footer = new ListViewFooterManager(activity,members);
        footer.addFooter();*/

    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dialog_village_member_close_textview:
                dismiss();
                break;
        }
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
                    new HTTPconnVillMember(activity,adapter,vil_id,(page_no++)+"").execute();
                }else{
                  // footer.removeFooter();
                }

                size = adapter.getCount();

                mLockListView = false;
            }
        };

        // 속도의 딜레이를 구현하기 위한 꼼수
        Handler handler = new Handler();
        handler.postDelayed(run, 1000);
    }

}
