package com.jianguo.jiaowu.presenter;
import com.jianguo.common.Common;
import com.jianguo.jiaowu.model.LoginModel;
import com.jianguo.jiaowu.model.LoginModelImpl;
import com.jianguo.jiaowu.view.LoginView;

/**
 * Created by ifane on 2016/5/31 0031.
 */
public class LoginPresenterImpl implements LoginPresenter {
    private LoginView mloginView;
    private LoginModel mloginModel;
    public LoginPresenterImpl(LoginView loginView) {
        mloginView=loginView;
        mloginModel=new LoginModelImpl();
    }
    public void loadCheckCodeView(){
        mloginModel.loadCheckCode(Common.CHECK_CODE,mloginView);
    }
    public void getParameterView(){
        mloginModel.getParameter(mloginView);
    }
    @Override
    public void loadParameterView() {
        this.getParameterView();
        //this.loadCheckCodeView();
    }
    @Override
    public void loginevent(String arg1, String arg2, String arg3) {
        mloginModel.loginpost(arg1,arg2,arg3,mloginView);
    }
}
