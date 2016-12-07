package com.yu.yuweather.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.yu.yuweather.R;
import com.yu.yuweather.view.fragment.DefaultChooseFragment;

public class ChooseAreaActivity extends BaseActivity {

    private OnChooseAreaActivityBackPressedListener onChooseAreaActivityBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_area);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_choose_area, new DefaultChooseFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (onChooseAreaActivityBackPressedListener != null) {
            onChooseAreaActivityBackPressedListener.setBackPressed();
        }
    }

    public void setOnChooseAreaActivityBackPressedListener(OnChooseAreaActivityBackPressedListener onChooseAreaActivityBackPressedListener) {
        this.onChooseAreaActivityBackPressedListener = onChooseAreaActivityBackPressedListener;
    }

    public interface OnChooseAreaActivityBackPressedListener {
        void setBackPressed();
    }
}
