package com.jianguo.OA.Presenter;

import com.jianguo.OA.View.OA_Official_DetailView;
import com.jianguo.OA.View.OfficialActivity;

import java.io.File;

/**
 * Created by ifane on 2016/8/27 0027.
 */
public interface OA_Official_DetailPresenter {
    void loadPDF(File file,String herf);
}
