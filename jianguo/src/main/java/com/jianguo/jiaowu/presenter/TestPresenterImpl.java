package com.jianguo.jiaowu.presenter;

import android.content.Context;

import com.jianguo.jiaowu.model.TestModel;
import com.jianguo.jiaowu.model.TestModelImpl;
import com.jianguo.jiaowu.view.TestView;

/**
 * Created by ifane on 2016/6/3 0003.
 */
public class TestPresenterImpl implements TestPresenter {

    private final TestView mTestView;
    private final TestModel mTestModel;

    public TestPresenterImpl(TestView testView) {
        mTestView = testView;
        mTestModel = new TestModelImpl();
    }

    @Override
    public void loadTestView(Context context) {
        mTestModel.loadTest(mTestView,context);
    }
}
