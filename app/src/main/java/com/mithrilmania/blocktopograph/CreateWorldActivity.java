package com.mithrilmania.blocktopograph;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mithrilmania.blocktopograph.databinding.ActivityCreateWorldBinding;


public final class CreateWorldActivity extends AppCompatActivity {

    private ActivityCreateWorldBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_world);
    }

    public void onClickPositiveButton(View view) {
    }
}
