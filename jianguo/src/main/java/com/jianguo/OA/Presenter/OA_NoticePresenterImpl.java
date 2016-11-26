package com.jianguo.OA.Presenter;

import com.jianguo.OA.Model.OA_NoticeModel;
import com.jianguo.OA.Model.OA_NoticeModelImpl;
import com.jianguo.OA.View.OA_Common_View;

/**
 * Created by ifane on 2016/8/27 0027.
 */
public class OA_NoticePresenterImpl implements OA_NoticePresenter {

    private final OA_NoticeModel oa_noticeModel;
    private final OA_Common_View oa_noticeView;

    public OA_NoticePresenterImpl(OA_Common_View view) {
        oa_noticeView = view;
        oa_noticeModel = new OA_NoticeModelImpl();
    }

    @Override
    public void loadNotice() {
        oa_noticeModel.getNotice(oa_noticeView);
    }
}
