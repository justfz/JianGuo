package com.jianguo.Main.presenter;

import android.support.v4.app.FragmentTransaction;

import com.jianguo.Main.view.MainView;
import com.jianguo.Main.widget.MainActivity;
import com.jianguo.OA.View.OAFragment;
import com.jianguo.R;

/**
 * Created by ifane on 2016/5/25 0025.
 */
public class MainPresenterImpl implements MainPresenter {
    private MainView mView;
    public MainPresenterImpl(MainView mainView) {
        mView=mainView;
    }

    @Override
    public void switchNavigation(int id) {

        switch (id){
            case R.id.nav_NewsList:
                mView.switchNews();
                break;
            case R.id.nav_jiaowu:
                mView.switchJiaowu();
                break;
            case R.id.nav_oa:
                mView.switchOA();
                break;
            case R.id.nav_about:
                mView.switchAbout();
                break;
            case R.id.nav_login:
                mView.switchLogin();
                break;
            case R.id.nav_quit:
                mView.switchQuit();
                break;
            case R.id.nav_community:
                mView.switchcommunity();
                break;
        }

    }

}
