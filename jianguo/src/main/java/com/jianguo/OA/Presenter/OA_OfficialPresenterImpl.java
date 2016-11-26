package com.jianguo.OA.Presenter;

import com.jianguo.OA.Model.OA_OfficialModel;
import com.jianguo.OA.Model.OA_OfficialModelImpl;
import com.jianguo.OA.View.OA_Common_View;

/**
 * Created by ifane on 2016/8/27 0027.
 */
public class OA_OfficialPresenterImpl implements OA_OfficialPresemter {

    private final OA_Common_View oa_officialView;
    private final OA_OfficialModel oa_officialModel;

    public OA_OfficialPresenterImpl(OA_Common_View view) {
        oa_officialView = view;
        oa_officialModel = new OA_OfficialModelImpl();
    }

    @Override
    public void loadOfficial() {
        oa_officialModel.getOfficial(oa_officialView);
    }
}
