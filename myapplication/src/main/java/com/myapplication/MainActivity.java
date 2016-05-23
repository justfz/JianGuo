package com.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.myapplication.UI.LoginActivity;
import com.myapplication.UI.News_Adapter;
import com.myapplication.UI.News_Content_Activity;
import com.myapplication.UI.ScheduleActivity;
import com.myapplication.UI.ScoreActivity;
import com.myapplication.UI.TestActivity;
import com.myapplication.Utils.OKHttpUtils;
import com.myapplication.bean.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lv_news;
    private List<News> data = new ArrayList<News>();;
    private SharedPreferences user;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("坚果资讯");
        user = getSharedPreferences("jianguo_user", MODE_PRIVATE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*
        判断是否登录
         */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        AdjustLogin();
        // Button button= (Button) findViewById(R.id.hello);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            }
//        });

        //initdate();
        //爬取学校首页数据

        lv_news = (ListView) findViewById(R.id.lv_news);
        getNews();
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news=data.get(position);
                String href=news.getHref();
                Intent intent=new Intent(MainActivity.this, News_Content_Activity.class);
                Bundle bundle=new Bundle();
                bundle.putString("news_content",href);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /*
    * 判断是否登录过
    * */
    private void AdjustLogin() {
        View view =navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        SubMenu subMenu = menu.getItem(4).getSubMenu();
        TextView nav_num = (TextView) view.findViewById(R.id.nav_name);
        TextView nav_xh = (TextView) view.findViewById(R.id.nav_xh);
        SharedPreferences user = getSharedPreferences("jianguo_user", MODE_PRIVATE);
        if (user.getBoolean("login",false)){
            //登录成功过,开始读取数据
            String num = user.getString("num", "对不起");
            String xh = user.getString("xh", "您尚未登录");
            nav_num.setText(num);
            nav_xh.setText(xh);
           subMenu.getItem(0).setVisible(false);
            subMenu.getItem(1).setVisible(true);
        }else{
            //没有登录
            nav_num.setText("对不起");
            nav_xh.setText("您尚未登录");
            subMenu.getItem(1).setVisible(false);
            subMenu.getItem(0).setVisible(true);
        }
    }

    private void getNews() {
        OkHttpClient client = OKHttpUtils.getClient();
        Request request=new Request.Builder()
                .url("http://www.jiea.cn/")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("errp",e.toString());
                System.out.println("11111");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content=response.body().string();
                System.out.println(content);
                Elements select = Jsoup.parse(content).getElementById("xxkc_11").select("li");
                for (int i=0;i<select.size();i++){
                    News news=new News();
                    //得到标题
                    news.setTitle(select.get(i).select("a").text());
                    //System.out.println(news.getTitle());
                    //得到网址
                    news.setHref("http://www.jiea.cn/"+select.get(i).select("a").attr("href"));
                    //System.out.println(news.getHref());
                    //得到日期
                    news.setDate(select.get(i).select("em").text());
                    //System.out.println(news.getDate());
                    data.add(news);
                }
                //抓取通告
                Elements select1 = Jsoup.parse(content).select("ul.nlist.w96").get(3).select("li");
                for (int i=0;i<select1.size();i++){
                    News news=new News();
                    news.setTitle(select1.get(i).select("a").text());
                    news.setDate(select1.get(i).select("em").text());
                    news.setHref(select1.get(i).select("a").attr("href"));
                    data.add(news);
                }
                System.out.println(select1.text());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        News_Adapter news_adapter=new News_Adapter(MainActivity.this, R.layout.adapter_item, data);
                        lv_news.setAdapter(news_adapter);
                    }
                });

            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_score) {
            if (user.getBoolean("login",false)) {
                startActivity(new Intent(MainActivity.this, ScoreActivity.class));
            }else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }
        } else if (id == R.id.nav_Course) {
            if (user.getBoolean("login",false)) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        } else if (id == R.id.nav_Schedule) {
            if (user.getBoolean("login",false)) {
                startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
            }else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        } else if (id == R.id.nav_test) {
            if (user.getBoolean("login",false)) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        } else if (id == R.id.nav_login) {

            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        } else if (id == R.id.nav_quit) {
            OKHttpUtils.getCookiesManager();
            user.edit().clear().commit();
            AdjustLogin();
        } else if (id == R.id.nav_about){

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
