package com.android.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.android.utils.TextUtil;

import butterknife.ButterKnife;

/**
 * Created by liujunqin on 2016/5/25.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements CommonInterface{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);


    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initView();
        initData();
        setListener();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);

    }


    public void showToast(String text) {
        if (TextUtil.isEmpty(text)) {
            return;
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
