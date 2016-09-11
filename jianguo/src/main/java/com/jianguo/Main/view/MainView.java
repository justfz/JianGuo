package com.jianguo.Main.view;

import android.support.v4.app.FragmentTransaction;

/**
 * Created by ifane on 2016/5/25 0025.
 */
public interface MainView {
    void switchNews();
    void switchJiaowu();
    void switchOA();
    void switchAbout();
    void switchLogin();
    void switchQuit();
    void switchcommunity();

    //    public void hideFragment(FragmentTransaction fragmentTransaction) {
    //        if (newsListFragment != null)
    //            fragmentTransaction.hide(newsListFragment);
    //        if (jwFragment != null)
    //            fragmentTransaction.hide(jwFragment);
    //        if (oaFragment != null)
    //            fragmentTransaction.hide(oaFragment);
    //        if (aboutFragment != null)
    //            fragmentTransaction.hide(aboutFragment);
    //        if (loginFragment!=null)
    //            fragmentTransaction.hide(loginFragment);
    //        if (oa_loginFragment!=null)
    //            fragmentTransaction.hide(oa_loginFragment);
    //    }
    void setToolbarTitle(String title);
}
