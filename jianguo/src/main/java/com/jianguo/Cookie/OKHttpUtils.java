package com.jianguo.Cookie;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ifane on 2016/5/13 0013.
 */
public class OKHttpUtils {
    private static CookiesManager cookiesManager_jw=new CookiesManager();
    private static CookiesManager cookiesManager_oa=new CookiesManager();
    private static OkHttpClient client_jw=new OkHttpClient.Builder()
            .cookieJar(cookiesManager_jw)
            .build();
    private static OkHttpClient client_oa=new OkHttpClient.Builder()
            .cookieJar(cookiesManager_oa)
            .build();
    public static void clearCookie() {
        cookiesManager_jw.delAll();
    }
    public static void clearCookie_oa(){ cookiesManager_oa.delAll();}
    public static OkHttpClient getClient() {
        return client_jw;
    }
    public static OkHttpClient getClient_oa() {
        return client_oa;
    }


}
