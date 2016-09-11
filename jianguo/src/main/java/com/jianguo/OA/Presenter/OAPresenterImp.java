package com.jianguo.OA.Presenter;

import com.jianguo.OA.Model.OAModel;
import com.jianguo.OA.Model.OAModelImp;
import com.jianguo.OA.View.OAView;

/**
 * Created by ifane on 2016/8/26 0026.
 */
public class OAPresenterImp implements OAPresenter {

    private final OAView oaView;
    private OAModel oaModel;
    public OAPresenterImp(OAView view) {
        oaView = view;
        oaModel=new OAModelImp();
    }

    @Override
    public void loadNews() {

    }
}
