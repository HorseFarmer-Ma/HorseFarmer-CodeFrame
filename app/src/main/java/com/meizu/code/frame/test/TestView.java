package com.meizu.code.frame.test;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meizu.code.frame.R;
import com.meizu.code.frame.base.annotations.RequirePresenter;
import com.meizu.code.frame.base.frame.mvp.BeamDataView;

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
        View rootView = inflater.inflate(R.layout.activity_test, parent, false);
        mTextView = rootView.findViewById(R.id.btn_text);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), TestActicity.class));
            }
        });
        return rootView;
    }

    @Override
    protected void onActivityCreate() {
        super.onActivityCreate();
    }

    @Override
    public void setData(List<String> data) {
        super.setData(data);
        mTextView.setText(data.toString());
    }

    @Override
    public void setExtraData(Object data, Enum loadTypeParmas) {
        super.setExtraData(data, loadTypeParmas);
        mTextView.setText(data.toString());
    }
}
