package com.jianguo.jiaowu.model;

import android.content.Context;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.DB.JianGuoDB;
import com.jianguo.beans.ScheduleBean;
import com.jianguo.common.Common;
import com.jianguo.jiaowu.JWUtils;
import com.jianguo.jiaowu.view.ScheduleView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ifane on 2016/5/29 0029.
 */
public class ScheduleModelImpl implements ScheduleModel {

    @Override
    public void loadSchedule(final ScheduleView scheduleView, Context context, final String week_id) {
        OkHttpClient client = OKHttpUtils.getClient();
        Request request=new Request.Builder()
                //.url("http://newjwglxt.jiea.cn/jiaowu_system/JWXS/pkgl/XsKB_List.aspx")
                .url(Common.SCHEDULE_URL)
                .build();
        //判断数据库里面是否有
        final JianGuoDB instance = JianGuoDB.getInstance(context);
        if (instance.isScheduleExist()){
            //从数据库里读数据,得到指定星期的所有课程
            loadfromDB(instance,scheduleView,week_id);
        }else{
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String content=response.body().string();
                    //从网站读数据到数据库,再从数据库取数据
                    Element kb = Jsoup.parse(content).getElementById("kb");
                    if (kb.text()==""){
                        System.out.println(Jsoup.parse(content).getElementById("Label1").text());
                    }else {
                        JWUtils.readingSchedule(content,instance);
                        loadfromDB(instance,scheduleView,week_id);
                    }
                }
            });
        }
    }
    public void loadfromDB(JianGuoDB instance,ScheduleView scheduleView,String week_id){
        List<ScheduleBean> list = instance.loadWeek(week_id);
        scheduleView.addSchedule(list);
    }
}
