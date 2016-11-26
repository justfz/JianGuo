package com.jianguo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.jianguo.Main.widget.MainActivity;



/**
 * Created by ifane on 2016/5/28 0028.
 */
public class Activity_Start extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atctivity_start);
        View view = findViewById(R.id.root_start);
        Animation animation=new AlphaAnimation(0.3f,1f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        //PushAgent.getInstance(getApplicationContext()).onAppStart();
        //PushAgent mPushAgent = PushAgent.getInstance(getApplicationContext());
        //mPushAgent.enable();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
                if (config.getBoolean("isGuide",false)){
                    startActivity(new Intent(Activity_Start.this, MainActivity.class));
                }else {
                    startActivity(new Intent(Activity_Start.this,Activity_Guide.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setAnimation(animation);
    }
}
