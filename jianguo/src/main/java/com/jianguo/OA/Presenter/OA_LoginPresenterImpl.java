package com.jianguo.OA.Presenter;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.OA.Model.OA_LoginModel;
import com.jianguo.OA.Model.OA_LoginModelImpl;
import com.jianguo.OA.View.OA_LoginView;
import com.jianguo.common.Common;

/**
 * Created by ifane on 2016/8/26 0026.
 */
public class OA_LoginPresenterImpl implements OA_LoginPresenter {

    private final OA_LoginView oaLoginView;
    private final OA_LoginModel oa_loginModel;

    public OA_LoginPresenterImpl(OA_LoginView oa_loginView) {
        oaLoginView = oa_loginView;
        oa_loginModel = new OA_LoginModelImpl();
    }

    @Override
    public void loadParameterView() {
        oa_loginModel.getParameter(oaLoginView, Common.OA_CHECKCODE);
    }

    @Override
    public void Loginevent(String str1, String str2, String str3) {
        oa_loginModel.LoginPost(str1,str2,str3,oaLoginView);
    }
}
