package com.jianguo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jianguo.Main.widget.MainActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ifane on 2016/6/12 0012.
 */
public class Activity_Guide extends AppCompatActivity {
    private static final int[] mLayoutIds = new int[]{R.layout.guide_one,
            R.layout.guide_two, R.layout.guide_three};
    private static final int[] mImgs=new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    private MyAdapter myAdapter;
    private ViewPager vp;
    private LinearLayout points;
    private View point;
    private int pointWidth;
    private Button enter;
    private Animation animation;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        points = (LinearLayout) findViewById(R.id.points);
        point = findViewById(R.id.point);
        enter = (Button) findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("config",MODE_PRIVATE).edit().putBoolean("isGuide",true).commit();
                startActivity(new Intent(Activity_Guide.this, MainActivity.class));
                finish();
            }
        });
        //PushAgent.getInstance(getApplicationContext()).onAppStart();
        animation = new AlphaAnimation(0,1);
        animation.setDuration(1500);
        myAdapter = new MyAdapter();
        initView();
        vp = (ViewPager) findViewById(R.id.viewpage);
        vp.setAdapter(myAdapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int len = (int) (pointWidth * positionOffset + position * pointWidth);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) point.getLayoutParams();
                params.width=16;
                params.height=16;

                params.leftMargin =len;
                point.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if (position==mLayoutIds.length-1){
                    relativeLayout.setVisibility(View.GONE);
                    enter.setVisibility(View.VISIBLE);
                    enter.setAnimation(animation);
                }else {
                    relativeLayout.setVisibility(View.VISIBLE);
                    enter.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initView() {
        //初始化引导页图片
        View guideView_1 = View.inflate(this,mLayoutIds[0], null);
        ImageView imageView_1= (ImageView) guideView_1.findViewById(R.id.guide_one_2);
        imageView_1.setImageBitmap(compressImg(mImgs[0]));
        myAdapter.addGuide(guideView_1);

        View guideView_2 = View.inflate(this,mLayoutIds[1], null);
        ImageView imageView_2= (ImageView) guideView_2.findViewById(R.id.guide_two_2);
        imageView_2.setImageBitmap(compressImg(mImgs[1]));
        myAdapter.addGuide(guideView_2);

        View guideView_3 = View.inflate(this,mLayoutIds[2], null);
        ImageView imageView_3= (ImageView) guideView_3.findViewById(R.id.guide_three_2);
        imageView_3.setImageBitmap(compressImg(mImgs[2]));
        myAdapter.addGuide(guideView_3);

        //初始化引导页小圆点
        for (int i = 0; i < mLayoutIds.length; i++) {
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16, 16);
            view.setBackgroundResource(R.drawable.shape_point_gray);
            if (i > 0) {
                params.leftMargin = 30;// 设置圆点间隔
            }
            view.setLayoutParams(params);
            points.addView(view);
        }
        points.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                points.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                pointWidth = points.getChildAt(1).getLeft() - points.getChildAt(0).getLeft();
            }
        });
    }
    private Bitmap compressImg(int layoutId){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=2;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), layoutId, options);
        return bitmap;
    }
    public class MyAdapter extends PagerAdapter {
        private List<View> guide = new ArrayList<View>();

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(guide.get(position));
            return guide.get(position);
        }

        @Override
        public int getCount() {
            return guide.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public void addGuide(View view) {
            guide.add(view);
        }

    }
}
