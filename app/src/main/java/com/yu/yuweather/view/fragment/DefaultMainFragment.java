package com.yu.yuweather.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yu.yuweather.R;
import com.yu.yuweather.view.activity.BaseActivity;

public class DefaultMainFragment extends Fragment {

    private Toolbar tMainToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_main, container, false);
        initView(view);
        initUI();
        initListener();
        return view;
    }

    private void initView(View view) {
        tMainToolbar = (Toolbar) view.findViewById(R.id.t_main_toolbar);
    }

    private void initUI() {
        tMainToolbar.setTitle("");
        tMainToolbar.setNavigationIcon(R.drawable.ic_menu);
        ((BaseActivity) getActivity()).setSupportActionBar(tMainToolbar);
    }

    private void initListener() {
    }
}
