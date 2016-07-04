package com.wonbuddism.bupmun.Utility;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wonbuddism.bupmun.R;

public class ListViewFooterManager {

    private Activity activity;
    private LayoutInflater mInflater;
    private LinearLayout footer;
    private ListView lv;
    public ListViewFooterManager(Activity activity, ListView lv) {
        this.activity = activity;
        mInflater = (LayoutInflater) this.activity.getSystemService(this.activity.LAYOUT_INFLATER_SERVICE);
        footer = (LinearLayout) mInflater.inflate(R.layout.footer, null);
        this.lv = lv;
    }

    public void addFooter(){
        lv.addFooterView(footer);
    }

    public void removeFooter(){
        lv.removeFooterView(footer);
    }
}
