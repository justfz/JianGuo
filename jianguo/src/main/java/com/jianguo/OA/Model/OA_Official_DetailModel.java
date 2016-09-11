package com.jianguo.OA.Model;

import com.jianguo.OA.View.OA_Official_DetailView;

import java.io.File;

/**
 * Created by ifane on 2016/8/27 0027.
 */
public interface OA_Official_DetailModel {
    void getPDF(OA_Official_DetailView oa_official_detailView, File file, String herf) ;
}
