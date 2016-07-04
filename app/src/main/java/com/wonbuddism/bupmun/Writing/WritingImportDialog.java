package com.wonbuddism.bupmun.Writing;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Utility.SwipeDismissListViewTouchListener;

import java.util.ArrayList;

public class WritingImportDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView cancel;
    private ListView imports;
    private WritingImportDialogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.dialog_writing_import);

        setLayout();
    }

    private void setLayout() {
        cancel = (TextView)findViewById(R.id.dialog_writing_import_close_textview);
        cancel.setOnClickListener(this);

        adapter = new WritingImportDialogAdapter(context, getSortList());
        imports = (ListView)findViewById(R.id.dialog_writing_import_listview);
        imports.setAdapter(adapter);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(imports,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                              //  Toast.makeText(context,"터치 : "+position,Toast.LENGTH_SHORT).show();
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {

                                    Toast.makeText(context,"삭제 : "+position,Toast.LENGTH_SHORT).show();
                                    adapter.remove(position);
                                    //adapter.remove(adapter.getItem(position));
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
        imports.setOnTouchListener(touchListener);
        imports.setOnScrollListener(touchListener.makeScrollListener());
        imports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,"불러오기 : "+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public WritingImportDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;

    }

    private ArrayList<WritingImportItem> getSortList() {
        ArrayList<WritingImportItem> list = new ArrayList<>();

        list.add(new WritingImportItem("정전","일원상 진리",1,2));
        list.add(new WritingImportItem("정전","일원상 신앙",1,3));
        list.add(new WritingImportItem("정전","일원상 수행",1,1));
        list.add(new WritingImportItem("정전","일원상 서원문",1,4));
        list.add(new WritingImportItem("원불교 교사","기념관·영모전·정산 종사 성탑 봉건",1,1));

        return list;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dialog_writing_import_close_textview:
                dismiss();
                break;
        }
    }
}
