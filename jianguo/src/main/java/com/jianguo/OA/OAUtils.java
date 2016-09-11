package com.jianguo.OA;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.beans.NewsBean;
import com.jianguo.common.Common;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by ifane on 2016/8/26 0026.
 */
public class OAUtils {
    public static String content;
    void getParameterFromUrl(){

    }
    public static Bitmap readingCheckCode(ResponseBody responseBody){
        return BitmapFactory.decodeStream(responseBody.byteStream());
    }
    public static boolean isLogin(String content){
        Element id = Jsoup.parse(content).getElementById("main_top_right_bottom");
        if (id!=null){
            return true;
        }
        return false;
    }
    public static List<NewsBean> readingNotice(){
        if (content==null){
            return null;
        }
        List<NewsBean> list=new ArrayList<NewsBean>();
        Elements select = Jsoup.parse(content).getElementById("desk_msg_div").getElementsByClass("desk_content_div").select("ul");
        for (int i=0;i<select.size();i++){
            NewsBean newsBean=new NewsBean();
            newsBean.setNews_time(select.get(i).getElementsByClass("desk_content_right").text());
            newsBean.setNews_title(select.get(i).select("a").attr("title"));
            newsBean.setHref(Common.OA_ZHUYE+select.get(i).select("a").attr("href"));
            list.add(newsBean);
        }
        return list;
    }
    public static List<NewsBean> readingInformation(){
        if (content==null){
            return null;
        }
        List<NewsBean> list=new ArrayList<NewsBean>();
        Elements select = Jsoup.parse(content).getElementById("desk_announce_div").getElementsByClass("desk_content_div").select("ul");
        for (int i=0;i<select.size();i++){
            NewsBean newsBean=new NewsBean();
            newsBean.setNews_time(select.get(i).getElementsByClass("desk_content_right").text());
            newsBean.setNews_title(select.get(i).select("a").attr("title"));
            newsBean.setHref(Common.OA_ZHUYE+select.get(i).select("a").attr("href"));
            list.add(newsBean);
        }
        return  list;
    }
    public static List<NewsBean> readingOfficial() {
        if (content==null)
            return null;
        List<NewsBean> list=new ArrayList<NewsBean>();
        Elements select = Jsoup.parse(content).getElementById("doc_overFlow").select("ul");
        for (int i=0;i<select.size();i++){
            NewsBean newsBean=new NewsBean();
            newsBean.setNews_time(select.get(i).getElementsByClass("desk_content_right").text());
            newsBean.setNews_title(select.get(i).select("a").attr("title"));
            newsBean.setHref(Common.OA_ZHUYE+select.get(i).select("a").attr("href"));
            list.add(newsBean);
        }
        return list;
    }

    public static String readingPDFURL(String content) {
        Elements select = Jsoup.parse(content).select("input");
        String onclick = select.get(4).attr("onclick");
        String[] split = onclick.split("='");
        String[] split1 = split[1].split("'");
        return Common.OA_ZHUYE+split1[0];
    }

    public static String readingInformation_Notice_title(String content) {
        Elements select = Jsoup.parse(content).select("h1");
        return select.text();
    }

    public static String readingInformation_Notice_time(String content) {
        Elements select = Jsoup.parse(content).getElementById("myIDWidth").select("td");
        Node text = select.get(2).childNodes().get(1);

        return text.toString();
    }

    public static String readingInformation_Notice_content(String content) {
        Elements select = Jsoup.parse(content).getElementById("myIDWidth").select("td");
        String web_conten=select.get(3).toString();
        web_conten = web_conten.replace("src=\"","src=\"http://oa.jiea.cn:8003");




                web_conten = web_conten.concat("<style>img{max-width: 100%;height:50vw;}</style>");
        System.out.println(web_conten);
        return Common.HTML_ADAPTER+web_conten;
    }
}
