package com.puhuibao.customview_voice;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.puhuibao.customview_voice.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityMainBinding mMainBinding;
    private int count = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mMainBinding.setBtnClick(this);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){

            case R.id.btn_add:

                mMainBinding.customView.setCurrentTime(count++);
                break;

            case R.id.btn_delete:

                mMainBinding.customView.setCurrentTime(count--);
                break;
        }
        mMainBinding.customView.invalidate();
    }
}
