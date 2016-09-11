package com.jianguo.OA.Presenter;

import com.jianguo.OA.Model.OA_Information_NoticeModelImpl;
import com.jianguo.OA.Model.OA_Information_notice_Model;
import com.jianguo.OA.View.Notice_InformationActivity;
import com.jianguo.OA.View.OA_Information_NoticeView;

/**
 * Created by ifane on 2016/8/27 0027.
 */
public class OA_Information_NoticePresenterImpl implements OA_Information_NoticePresenter {

    private final OA_Information_notice_Model oa_information_notice_model;
    private final OA_Information_NoticeView oa_information_noticeView;

    public OA_Information_NoticePresenterImpl(OA_Information_NoticeView view) {
        oa_information_noticeView = view;
        oa_information_notice_model = new OA_Information_NoticeModelImpl();
    }

    @Override
    public void loadInformation_Notice(String herf) {
        oa_information_notice_model.getInformation_Notice(oa_information_noticeView,herf);
    }
}
