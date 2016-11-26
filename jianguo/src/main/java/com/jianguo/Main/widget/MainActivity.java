package com.jianguo.Main.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianguo.Cookie.MyApplication;
import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.DB.JianGuoDB;
import com.jianguo.Main.presenter.MainPresenter;
import com.jianguo.Main.presenter.MainPresenterImpl;
import com.jianguo.Main.view.MainView;
import com.jianguo.Notify.NotifyActivity;
import com.jianguo.OA.OAUtils;
import com.jianguo.OA.View.OAFragment;
import com.jianguo.OA.View.OA_LoginFragment;
import com.jianguo.R;
import com.jianguo.common.URLActivity;
import com.jianguo.jiaowu.IsLogin;
import com.jianguo.jiaowu.JWUtils;
import com.jianguo.jiaowu.widget.JWFragment;
import com.jianguo.jiaowu.widget.LoginFragment;
import com.jianguo.news.widget.NewsListFragment;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView, IsLogin {

    private Toolbar toolbar;
    private MainPresenter mainPresenter;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView student_name;
    private TextView student_id;
    private SharedPreferences config;
    private ImageView toolbar_notify;
    private NewsListFragment newsListFragment;
    private JWFragment jwFragment;
    private OAFragment oaFragment;
    private AboutFragment aboutFragment;
    private LoginFragment loginFragment;
    private OA_LoginFragment oa_loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.startbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        student_id = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_id);
        student_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
        config = getSharedPreferences("config", MODE_PRIVATE);
        if (config.getBoolean("isLogin", false)) {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_quit).setVisible(true);
            student_id.setText(config.getString("student_id", "对不起"));
            student_name.setText(config.getString("student_name", "尚未登陆"));
        } else {
            navigationView.getMenu().findItem(R.id.nav_quit).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        }
        toolbar_notify = (ImageView) findViewById(R.id.toolbar_notify);
        toolbar_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApplication.getContext(), NotifyActivity.class));
            }
        });
        mainPresenter = new MainPresenterImpl(this);
        //PushAgent.getInstance(getApplicationContext()).onAppStart();
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_NewsList));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        item.setCheckable(true);
        navigationView.setCheckedItem(item.getItemId());
        mainPresenter.switchNavigation(item.getItemId());
        drawer.closeDrawer(GravityCompat.START);
        navigationView.postInvalidate();
        return true;
    }

    @Override
    public void switchNews() {
            newsListFragment = new NewsListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, newsListFragment).commit();
        toolbar.setTitle("资讯");
    }

    @Override
    public void switchJiaowu() {
        JianGuoDB instance = JianGuoDB.getInstance(this);
        if (instance.isScheduleExist() && instance.TestisExist() && instance.ScoreSpinnerisExist()) {
                jwFragment = new JWFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, jwFragment).commit();
            toolbar.setTitle("教务系统");
        } else {
            JWUtils.getUserName(this);
        }
    }
    @Override
    public void switchOA() {
        oa_loginFragment = new OA_LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, oa_loginFragment).commit();
        toolbar.setTitle("办公自动化");
    }

    @Override
    public void switchAbout() {
            aboutFragment = new AboutFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, aboutFragment).commit();
        toolbar.setTitle("关于");
    }

    @Override
    public void switchLogin() {
            loginFragment = new LoginFragment(navigationView,this);
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, loginFragment).commit();
        toolbar.setTitle("登录");
    }

    @Override
    public void switchQuit() {
        /*
        注销要做的几件事
        1. 清空数据库 教务系统有关的数据
        2. 登录item设为true，注销item设为false；
        3. 清除Cookie
         */
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在注销...");
        progressDialog.show();
        final JianGuoDB instance = JianGuoDB.getInstance(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                instance.clean();
                navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_quit).setVisible(false);
                getSharedPreferences("config", MODE_PRIVATE).edit().putBoolean("isLogin", false).commit();
                getSharedPreferences("config", Context.MODE_PRIVATE).edit().putString("student_id", "对不起").commit();
                getSharedPreferences("config", Context.MODE_PRIVATE).edit().putString("student_name", "尚未登陆").commit();
                student_name.setText("对不起");
                student_id.setText("尚未登陆");
                OKHttpUtils.clearCookie();
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_login));
                progressDialog.dismiss();
            }
        }, 2000);
    }

    @Override
    public void switchcommunity() {
        Intent intent = new Intent(this, URLActivity.class);
        intent.putExtra("type", 0);
        intent.putExtra("herf", "http://bbs.zfane.cn");
        intent.putExtra("title", "坚果社区");
        startActivity(intent);
    }

    @Override
    public void loginSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                jwFragment = new JWFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, jwFragment).commit();
                toolbar.setTitle("教务系统");
            }
        });
    }

    @Override
    public void loginfail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainPresenter.switchNavigation(R.id.nav_login);
                toolbar.setTitle("登录");
            }
        });
    }

    @Override
    public void setToolbarTitle(String title){
        toolbar.setTitle(title);
    }
}
