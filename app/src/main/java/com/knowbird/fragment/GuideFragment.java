package com.knowbird.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.knowbird.BaseFragment;
import com.knowbird.R;

public class GuideFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void initView(@NonNull View view) {
        TextView tvHint = view.findViewById(R.id.tv_guide_hint);
        tvHint.setText("图鉴功能开发中...");
    }
}
