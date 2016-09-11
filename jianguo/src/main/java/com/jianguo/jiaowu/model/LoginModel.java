package com.jianguo.jiaowu.model;

import com.jianguo.jiaowu.presenter.LoginPresenter;
import com.jianguo.jiaowu.view.LoginView;

/**
 * Created by ifane on 2016/5/31 0031.
 */
public interface LoginModel {
    void loadCheckCode(String url, LoginView loginView);
    void getParameter(LoginView mloginView);
    void loginpost(String arg1, String arg2, String arg3, LoginView loginView);
}
