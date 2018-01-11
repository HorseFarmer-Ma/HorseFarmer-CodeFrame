package com.meizu.code.frame.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meizu.code.frame.R;
import com.meizu.code.frame.base.annotations.RequirePresenter;
import com.meizu.code.frame.base.mvp.BeamDataView;

import java.util.List;

/**
 * 测试View类
 *
 * Created by mxm on 12/11/17.
 */
@RequirePresenter(TestPresenter.class)
public class TestView extends BeamDataView<TestPresenter, List<String>> {

    private TextView mTextView;

    @Override
    protected View onCreateView(ViewGroup parent, LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_test, parent, false);
    }

    @Override
    public void setData(List<String> data) {
        super.setData(data);
        if (mTextView == null) {
            mTextView = (TextView) findViewById(R.id.text);
        }
        mTextView.setText(data.toString());
    }

    @Override
    public void setExtraData(Object data, Enum loadTypeParmas) {
        super.setExtraData(data, loadTypeParmas);

        if (mTextView == null) {
            mTextView = (TextView) findViewById(R.id.text);
        }
        mTextView.setText(data.toString());
    }
}
