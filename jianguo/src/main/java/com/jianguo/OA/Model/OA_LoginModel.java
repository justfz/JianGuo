package com.jianguo.OA.Model;

import com.jianguo.OA.View.OA_LoginView;

/**
 * Created by ifane on 2016/8/26 0026.
 */
public interface OA_LoginModel  {
    void getParameter(OA_LoginView view,String url);
    void LoginPost(String str1,String str2,String str3,OA_LoginView oa_loginView);
}
