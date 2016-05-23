package com.myapplication.Utils;



import com.myapplication.Cookie.CookiesManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ifane on 2016/5/13 0013.
 */
public class OKHttpUtils {

    private static CookiesManager cookiesManager=new CookiesManager();
    private static OkHttpClient client=new OkHttpClient.Builder()
            .cookieJar(cookiesManager)
            .build();
    public static void getCookiesManager() {
         cookiesManager.delAll();
    }

    private static String parameter="http://newjwglxt.jiea.cn/jiaowu_system/";
    private static String CheckCode="http://newjwglxt.jiea.cn/jiaowu_system/other/CheckCode.aspx?datetime=az";
    private static String loginurl="http://newjwglxt.jiea.cn/jiaowu_system/login.aspx";

    public static String getLoginurl() {return loginurl;}

    public static String getCheckCode() {
        return CheckCode;
    }

    public static String getParameter() {
        return parameter;
    }

    public static OkHttpClient getClient() {
        return client;
    }
    //登录需要的Request
    public static Request getRequest(String url, RequestBody requestBody){
        Request request=new Request.Builder()
                .url(url)
                .addHeader("HOST","newjwglxt.jiea.cn")
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addHeader("Connection","keep-alive")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36")
                .addHeader("Origin","http://newjwglxt.jiea.cn")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Cache-Control","max-age=0")
                .addHeader("Referer","http://newjwglxt.jiea.cn/jiaowu_system/")
                .addHeader("Upgrade-Insecure-Requests","1")
                .post(requestBody)
                .build();
        return request;
    }
    //获取验证码，抓取页面数据需要的Request
    public static Request getRequest(String url){
        Request request=new Request.Builder()
                .url(url)
                .addHeader("HOST","newjwglxt.jiea.cn")
                .build();
        return request;
    }
}
