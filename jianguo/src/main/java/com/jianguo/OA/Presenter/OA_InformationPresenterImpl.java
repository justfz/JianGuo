package com.jianguo.OA.Presenter;

import com.jianguo.OA.Model.OA_InformationModelImpl;
import com.jianguo.OA.View.OA_Common_View;

/**
 * Created by ifane on 2016/8/27 0027.
 */
public class OA_InformationPresenterImpl implements OA_InformationPresenter {

    private final OA_Common_View oa_informationView;
    private final OA_InformationModelImpl oa_informationModel;

    public OA_InformationPresenterImpl(OA_Common_View view) {
        oa_informationView = view;
        oa_informationModel = new OA_InformationModelImpl();
    }
    public void loadInformation(){
        oa_informationModel.getInformation(oa_informationView);
    }
}
