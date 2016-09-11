package com.jianguo.OA.View;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jianguo.OA.Presenter.OA_Information_NoticePresenter;
import com.jianguo.OA.Presenter.OA_Information_NoticePresenterImpl;
import com.jianguo.R;
import com.jianguo.beans.Information_noticeBean;

public class Notice_InformationActivity extends AppCompatActivity implements OA_Information_NoticeView{

    private OA_Information_NoticePresenter oa_information_noticePresenter;
    private TextView notice_information_title;
    private TextView information_notice_time;
    private WebView notice_information_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice__information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String herf = getIntent().getStringExtra("herf");
        notice_information_title = (TextView) findViewById(R.id.notice_information_title);
        information_notice_time= (TextView) findViewById(R.id.information_notice_time);
        notice_information_webview= (WebView) findViewById(R.id.notice_information_webview);
        oa_information_noticePresenter = new OA_Information_NoticePresenterImpl(this);
        notice_information_webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        notice_information_webview.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        notice_information_webview.setHorizontalScrollBarEnabled(false);
        notice_information_webview.requestFocus();
        oa_information_noticePresenter.loadInformation_Notice(herf);
    }

    @Override
    public void setInformation_Notice(final Information_noticeBean information_noticeBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notice_information_title.setText(information_noticeBean.getInformation_Notice_title());
                information_notice_time.setText(information_noticeBean.getInformation_Notice_time());
                notice_information_webview.loadDataWithBaseURL(
                        null,
                        information_noticeBean.getInformation_Notice_content()
                        ,"text/html", "utf-8", null);

            }
        });
    }
}
