package com.android.qrcode.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.base.BaseFragment;
import com.android.qrcode.R;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/5/31.
 */
public class AccountFragmet extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.log_activity)
    RelativeLayout log_activity;

    public AccountFragmet() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "用户");
        bundle.putInt("icon", R.drawable.selector_main_tab_person_icon);
        this.setArguments(bundle);
    }
    @Override
    public int setContentView() {
        return R.layout.fragment_person;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {


    }

    @Override
    public void setListener() {
        log_activity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            //往来日志
            case R.id.log_activity:

                startActivity( new Intent(getActivity(),LogActivity.class));
                break;
            default:
                break;
        }
    }

}
