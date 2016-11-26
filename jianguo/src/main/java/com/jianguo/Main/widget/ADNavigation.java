package com.jianguo.Main.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jianguo.R;
import com.jianguo.beans.AdvertisementBean;
import com.jianguo.common.URLActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ifane on 2016/6/16 0016.
 */
public class ADNavigation extends FrameLayout {
    private int count;
    private ImageLoader mImageLoader;
    private List<ImageView> imageViews;
    private Context context;
    private ViewPager vp;
    private boolean isAutoPlay;
    private int currentItem;
    private int delayTime;
    private LinearLayout ll_dot;
    private List<ImageView> iv_dots;
    private Handler handler = new Handler();
    private DisplayImageOptions options;

    public ADNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initImageLoader(context);
        initData();
    }

    public ADNavigation(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ADNavigation(Context context) {
        this(context, null);
    }

    private void initData() {
        imageViews = new ArrayList<ImageView>();
        iv_dots = new ArrayList<ImageView>();
        delayTime = 2000;
    }
    public void setImagesUrl(AdvertisementBean advertisementBean) {
        initLayout();
        initImgFromNet(advertisementBean);
        showTime();
    }
    /*
    从res文件夹内加载图片
     */
//    public void setImagesRes(List imagesRes) {
//        initLayout();
//        initImgFromRes(imagesRes);
//        showTime();
//    }

    private void initLayout() {
        imageViews.clear();
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_advertisement, this, true);
        vp = (ViewPager) view.findViewById(R.id.vp);
        ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
        ll_dot.removeAllViews();
    }

    /*
    从res文件夹内加载图片
     */
//    private void initImgFromRes(final List<AdvertisementBean> imagesRes) {
//        count = imagesRes.size();
//        for (int i = 0; i < count; i++) {
//            ImageView iv_dot = new ImageView(context);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.leftMargin = 5;
//            params.rightMargin = 5;
//            iv_dot.setImageResource(R.drawable.dot_blur);
//            ll_dot.addView(iv_dot, params);
//            iv_dots.add(iv_dot);
//        }
//        iv_dots.get(0).setImageResource(R.drawable.dot_focus);
//
//        for (int i = 0; i <= count + 1; i++) {
//            ImageView iv = new ImageView(context);
//            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//            final AdvertisementBean advertisementBean;
////            iv.setBackgroundResource(R.drawable.loading);
//            if (i == 0) {
//                advertisementBean = imagesRes.get(count - 1);
//                iv.setImageResource(advertisementBean.getmImage());
//            } else if (i == count + 1) {
//                advertisementBean = imagesRes.get(0);
//                iv.setImageResource(advertisementBean.getmImage());
//            } else {
//                advertisementBean = imagesRes.get(i - 1);
//                iv.setImageResource(advertisementBean.getmImage());
//            }
//            iv.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getContext(), UrlActivity.class);
//                    intent.putExtra("type",advertisementBean.getHref());
//                    intent.putExtra("herf",advertisementBean.getHref());
//                    getContext().startActivity(intent);
//                }
//            });
//            imageViews.add(iv);
//        }
//    }

    private void initImgFromNet(final AdvertisementBean advertisementBean) {
        count = advertisementBean.getImg_herf().length;
        for (int i = 0; i < count; i++) {
            ImageView iv_dot = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            iv_dot.setImageResource(R.drawable.dot_blur);
            ll_dot.addView(iv_dot, params);
            iv_dots.add(iv_dot);
        }
        iv_dots.get(0).setImageResource(R.drawable.dot_focus);
        for (int i = 0; i <= count + 1; i++) {
            ImageView iv = new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            final Intent intent = new Intent(getContext(), URLActivity.class);
            if (i == 0) {
                mImageLoader.displayImage(advertisementBean.getImg_herf()[count - 1], iv, options);
                intent.putExtra("herf",advertisementBean.getAd_href()[count-1]);
            } else if (i == count + 1) {
                mImageLoader.displayImage(advertisementBean.getImg_herf()[0], iv, options);
                intent.putExtra("herf",advertisementBean.getAd_href()[0]);
            } else {
                mImageLoader.displayImage(advertisementBean.getImg_herf()[i - 1], iv, options);
                intent.putExtra("herf",advertisementBean.getAd_href()[i-1]);
            }
            intent.putExtra("type",0);
            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContext().startActivity(intent);
                }
            });
            imageViews.add(iv);
        }

    }

    private void showTime() {
        vp.setAdapter(new KannerPagerAdapter());
        vp.setFocusable(true);
        vp.setCurrentItem(1);
        currentItem = 1;
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        startPlay();
    }

    private void startPlay() {
        isAutoPlay = true;
        handler.postDelayed(task, 2000);
    }

    public void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        mImageLoader = ImageLoader.getInstance();
    }

    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            if (isAutoPlay) {
                currentItem = currentItem % (count + 1) + 1;
                if (currentItem == 1) {
                    vp.setCurrentItem(currentItem, false);
                    handler.post(task);
                } else {
                    vp.setCurrentItem(currentItem);
                    handler.postDelayed(task, 3000);
                }
            } else {
                handler.postDelayed(task, 5000);
            }
        }
    };

    class KannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));

            return imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
        }

    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
                case 0:
                    if (vp.getCurrentItem() == 0) {
                        vp.setCurrentItem(count, false);
                    } else if (vp.getCurrentItem() == count + 1) {
                        vp.setCurrentItem(1, false);
                    }
                    currentItem = vp.getCurrentItem();
                    isAutoPlay = true;
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < iv_dots.size(); i++) {
                if (i == arg0 - 1) {
                    iv_dots.get(i).setImageResource(R.drawable.dot_focus);
                } else {
                    iv_dots.get(i).setImageResource(R.drawable.dot_blur);
                }
            }
        }

    }

    public void removeCallbacksAndMessages() {
        handler.removeCallbacksAndMessages(null);
        context = null;
    }
}
