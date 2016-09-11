package com.jianguo.Notify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jianguo.R;

/**
 * Created by ifane on 2016/6/24 0024.
 */
public class NotifyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        toolbar = (Toolbar) findViewById(R.id.notify_toolbar);
        toolbar.setTitle("消息中心");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.notify_framelayout,new NotifyFragment()).commit();
    }
}
