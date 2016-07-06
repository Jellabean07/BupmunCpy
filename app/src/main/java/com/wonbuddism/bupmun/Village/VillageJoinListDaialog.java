package com.wonbuddism.bupmun.Village;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.DataVo.VillageMainInfo;

import java.util.ArrayList;

public class VillageJoinListDaialog extends Dialog implements View.OnClickListener{
    private TextView cancel;
    private ListView vil_lv;
    private VillageJoinListViewAdapter adapter;
    private Activity activity;
    private ArrayList<VillageMainInfo> villageMainInfos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.dialog_village_join);

        setLayout();

    }


    public VillageJoinListDaialog(Activity activity, ArrayList<VillageMainInfo> villageMainInfos) {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        this.activity = activity;
        this.villageMainInfos = villageMainInfos;
        //this.context = context;

    }


    private void setLayout() {
        cancel = (TextView)findViewById(R.id.dialog_village_join_close_textview);
        cancel.setOnClickListener(this);

        adapter = new VillageJoinListViewAdapter(activity, this,villageMainInfos);
        vil_lv = (ListView)findViewById(R.id.dialog_village_join_listview);
        vil_lv.setAdapter(adapter);

    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dialog_village_join_close_textview:
                dismiss();
                break;
        }
    }

}
