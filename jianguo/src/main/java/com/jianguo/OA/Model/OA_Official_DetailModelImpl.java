package com.jianguo.OA.Model;

import com.github.barteksc.pdfviewer.util.FileUtils;
import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.OA.OAUtils;
import com.jianguo.OA.View.OA_Official_DetailView;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ifane on 2016/8/27 0027.
 */
public class OA_Official_DetailModelImpl implements OA_Official_DetailModel {
    @Override
    public void getPDF(final OA_Official_DetailView oa_official_detailView, final File file, String herf) {
        final Request request = new Request.Builder()
                .url(herf)
                .build();
        OKHttpUtils.getClient_oa().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String url = OAUtils.readingPDFURL(response.body().string());
                Request request_pdf = new Request.Builder()
                        .url(url)
                        .build();
                OKHttpUtils.getClient_oa().newCall(request_pdf).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        FileUtils.copy(response.body().byteStream(), file);
                        file.createNewFile();
                        oa_official_detailView.setPDF(file);
                    }
                });
            }
        });
    }


}
