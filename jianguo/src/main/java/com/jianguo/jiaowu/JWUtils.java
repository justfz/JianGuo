package com.jianguo.jiaowu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentTransaction;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.DB.JianGuoDB;
import com.jianguo.beans.ScheduleBean;
import com.jianguo.beans.ScoreBean;
import com.jianguo.beans.TestBean;
import com.jianguo.common.Common;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by ifane on 2016/5/30 0030.
 */
public class JWUtils {
    //字符串分割获取
    public static String Split(String split){
        return split.split("：")[1];
    }
    /*
    读取课表
     */
    public static void  readingSchedule(String content, JianGuoDB instance){
        Elements tr = Jsoup.parse(content).select("tr");
        for (int i=1;i<tr.size();i++){
            Elements nobr = tr.get(i).select("nobr");
            for (int j=0;j<nobr.size();j++){
                Elements a = nobr.get(j).select("a");
                for (int n=0;n<a.size();n++) {
                    String title = a.get(n).attr("title");
                    ScheduleBean scheduleBean = new ScheduleBean();
                    String[] split = title.split("\n");
                    //得到课名
                    String ScheduleName = Split(split[2]);
                    //得到老师
                    String teacher = Split(split[3]);
                    //得到星期
                    String week_id = String.valueOf(j+1);
                    //得到课
                    String day_id = tr.get(i).select("td").get(0).text();
                    //得到地点
                    String location = Split(split[6]);
                    //周次
                    String Schedule_time = Split(split[5]);
                    scheduleBean.setTeacher(teacher);
                    scheduleBean.setLocation(location);
                    scheduleBean.setSchedule_time(Schedule_time);
                    scheduleBean.setDay_id(day_id);
                    scheduleBean.setWeek_id(week_id);
                    scheduleBean.setScheduleName(ScheduleName);
                    instance.saveSchedule(scheduleBean);
                }
            }
        }

    }
    //抓取验证码
    public static Bitmap readingCheckCode(ResponseBody responseBody){
        return BitmapFactory.decodeStream(responseBody.byteStream());
    }
    //根据能否获取到姓名判断是否登录成功
    public static boolean isLogin(String content){
        Element users = Jsoup.parse(content).getElementById("users");
        if (users!=null){
            return true;
        }else{
            return false;
        }
    }
    //拿到错误原因
    public static String FailParameter(String content){
        Element lerror = Jsoup.parse(content).getElementById("Lerror");
        return lerror.text();
    }
    /*
    判断cookie是否有效，
     */
    public static void getUserName(final IsLogin isLogin)  {
        Request request=new Request.Builder()
                .url(Common.Default)
                .build();
        OKHttpUtils.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (isLogin(response.body().string())){
                    isLogin.loginSuccess();
                }else {
                    isLogin.loginfail();
                }
            }
        });

    }
    public  static void readingScore(String content,JianGuoDB db){
        Element cjxx = Jsoup.parse(content).getElementById("cjxx");
        Elements tr = cjxx.select("tr");
        for (int i=1;i<tr.size();i++){
            ScoreBean scoreBean=new ScoreBean();
            String term = tr.get(i).select("td").get(1).text();
            String Score_name=tr.get(i).select("td").get(3).text();
            String  Score_grade=tr.get(i).select("td").get(4).text();
            scoreBean.setScore_grage(Score_grade);
            scoreBean.setTerm(term);
            scoreBean.setScore_Name(Score_name);
            db.saveScore(scoreBean);
        }
    }
    public static void readingTest(String content,JianGuoDB db){
        Elements tr = Jsoup.parse(content).select("table").get(2).select("tr");
        for (int i=0;i<tr.size();i++){
            Elements td = tr.get(i).select("td");
            String Test_name=tr.get(i).select("td").get(3).text();
            String Test_time=tr.get(i).select("td").get(1).text();
            String Test_location=tr.get(i).select("td").get(4).text();
            String Test_seat=tr.get(i).select("td").get(5).text();
            TestBean testBean=new TestBean();
            testBean.setTest_location(Test_location);
            testBean.setTest_name(Test_name);
            testBean.setTest_seat(Test_seat);
            testBean.setTest_time(Test_time);
            db.saveTest(testBean);
        }
    }
    public static String getStudentName(String content){
        Element users = Jsoup.parse(content).getElementById("users");
        return users.text();
    }

}
