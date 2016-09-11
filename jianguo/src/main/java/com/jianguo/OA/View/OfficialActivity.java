package com.jianguo.OA.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FileUtils;
import com.jianguo.OA.Presenter.OA_Official_DetailPresenter;
import com.jianguo.OA.Presenter.OA_Official_DetailPresenterImpl;
import com.jianguo.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OfficialActivity extends AppCompatActivity implements OA_Official_DetailView{

    private PDFView pdfView;
    private File file;
    private OA_Official_DetailPresenter oa_official_detailPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String herf=getIntent().getStringExtra("herf");
        pdfView = (PDFView) findViewById(R.id.pdfView);
        file = new File(getFilesDir().getPath(),"tmp.pdf");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
        oa_official_detailPresenter = new OA_Official_DetailPresenterImpl(this);
        oa_official_detailPresenter.loadPDF(file,herf);
    }

    @Override
    public void setPDF(File file) {
        progressDialog.dismiss();
        pdfView.fromFile(file).load();
    }
}
