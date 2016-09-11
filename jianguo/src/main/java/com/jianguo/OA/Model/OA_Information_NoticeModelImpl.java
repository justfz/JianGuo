package com.jianguo.OA.Model;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.OA.OAUtils;
import com.jianguo.OA.View.OA_Information_NoticeView;
import com.jianguo.beans.Information_noticeBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ifane on 2016/8/27 0027.
 */
public class OA_Information_NoticeModelImpl implements OA_Information_notice_Model {
    @Override
    public void getInformation_Notice(final OA_Information_NoticeView oa_information_noticeView, String url) {
        Request request=new Request.Builder()
                .url(url)
                .build();
        OKHttpUtils.getClient_oa().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();
                Information_noticeBean information_noticeBean = new Information_noticeBean();

                information_noticeBean.setInformation_Notice_title(
                        OAUtils.readingInformation_Notice_title(content));
                information_noticeBean.setInformation_Notice_time(
                        OAUtils.readingInformation_Notice_time(content));
                information_noticeBean.setInformation_Notice_content(
                        OAUtils.readingInformation_Notice_content(content)
                );
                oa_information_noticeView.setInformation_Notice(information_noticeBean);
            }
        });
    }
}
