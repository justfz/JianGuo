package com.jianguo.OA.Presenter;

import com.jianguo.OA.Model.OA_Official_DetailModel;
import com.jianguo.OA.Model.OA_Official_DetailModelImpl;
import com.jianguo.OA.View.OA_Official_DetailView;
import com.jianguo.OA.View.OfficialActivity;

import java.io.File;

/**
 * Created by ifane on 2016/8/27 0027.
 */
public class OA_Official_DetailPresenterImpl implements OA_Official_DetailPresenter {

    private final OA_Official_DetailView oa_official_detailView;
    private final OA_Official_DetailModel oa_official_detailModel;

    public OA_Official_DetailPresenterImpl(OA_Official_DetailView view) {
        oa_official_detailView = view;
        oa_official_detailModel = new OA_Official_DetailModelImpl();
    }

    @Override
    public void loadPDF(File file,String herf) {
        oa_official_detailModel.getPDF(oa_official_detailView,file,herf);
    }
}
