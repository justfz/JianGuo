package com.jianguo.Cookie;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.jianguo.DB.JianGuoDB;
import com.jianguo.beans.NotifyBean;

import java.util.Map;

/**
 * Created by J。 on 2016/4/19.
 */
public class MyApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //PushAgent mPushAgent = PushAgent.getInstance(context);
        //mPushAgent.enable();
        //UmengMessageHandler handler = new UmengMessageHandler() {
        //    @Override
        //    public void dealWithCustomMessage(final Context context, final UMessage uMessage) {
        //        new Handler(getMainLooper()).post(new Runnable() {
        //            @Override
        //            public void run() {
        //                final Map<String, String> extra = uMessage.extra;
        //                NotifyBean notifyBean = new NotifyBean();
        //                notifyBean.setTitle(extra.get("title"));
        //                notifyBean.setContent(uMessage.custom);
        //                notifyBean.setHerf(extra.get("herf"));
        //                notifyBean.setTime(extra.get("time"));
        //                notifyBean.setType(Integer.parseInt(extra.get("type")));
        //                notifyBean.setIsLook(0);
        //                JianGuoDB instance = JianGuoDB.getInstance(context);
        //                instance.addNotify(notifyBean);
        //                Toast.makeText(context,extra.get("title") , Toast.LENGTH_LONG).show();
        //            }
        //        });
        //    }
        //};
        //mPushAgent.setMessageHandler(handler);
    }

    /**
     * 静态方法以供全局调用Application的context
     *
     * @return Application的Context对象
     */
    public static Context getContext() {
        return context;
    }
}
