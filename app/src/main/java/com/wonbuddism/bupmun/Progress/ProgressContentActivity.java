package com.wonbuddism.bupmun.Progress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wonbuddism.bupmun.Database.BUPMUN_TYPING_INDEX;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Writing.WritingMainActivity;

public class ProgressContentActivity extends AppCompatActivity {
    private  boolean MESSAGE_RECEIVER = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_content);


        BUPMUN_TYPING_INDEX bupmunItem = (BUPMUN_TYPING_INDEX)getIntent().getSerializableExtra("bupmunItem");

        Intent intent = new Intent(ProgressContentActivity.this, WritingMainActivity.class);
        intent.putExtra("bupmunItem", bupmunItem);
        intent.putExtra("MESSAGE_RECEIVER",MESSAGE_RECEIVER);
        startActivity(intent);
        setResult(1000);
        finish();



    }
}
