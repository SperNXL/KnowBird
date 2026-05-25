package com.knowbird;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.knowbird.utils.ScreenUtils;
import com.knowbird.utils.ToastUtils;

/**
 * Fragment 基类
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        // 设置系统栏高度
//        if (getActivity() != null) {
//            ScreenUtils.setSystemBarHeight(getActivity(), rootView);
//        }
        // 初始化 Toast
        if (getActivity() != null) {
            ToastUtils.init(getActivity().getApplicationContext());
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);
}
