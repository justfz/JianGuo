package com.jianguo.OA.Model;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.OA.OAUtils;
import com.jianguo.OA.View.OA_Common_View;
import com.jianguo.beans.NewsBean;
import com.jianguo.common.Common;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ifane on 2016/8/27 0027.
 */
public class OA_NoticeModelImpl implements OA_NoticeModel {
    @Override
    public void getNotice(final OA_Common_View oa_noticeView) {
        if (OAUtils.content==null){
            Request request=new Request.Builder()
                    .url(Common.OA_OFFICIAL_URL)
                    .build();
            OKHttpUtils.getClient_oa().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    OAUtils.content = response.body().string();
                    List<NewsBean> list = OAUtils.readingNotice();
                    oa_noticeView.setDataBean(list);
                }
            });
        }else {
            List<NewsBean> list = OAUtils.readingNotice();
            oa_noticeView.setDataBean(list);
        }
    }
}
