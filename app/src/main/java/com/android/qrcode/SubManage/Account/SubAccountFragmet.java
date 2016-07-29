package com.android.qrcode.SubManage.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.base.BaseFragment;
import com.android.qrcode.Account.LogActivity;
import com.android.qrcode.R;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/5/31.
 */
public class SubAccountFragmet extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.sub_set_activity)
    RelativeLayout sub_set_activity;
//
    public SubAccountFragmet() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "用户");
        bundle.putInt("icon", R.drawable.selector_main_tab_person_icon);
        this.setArguments(bundle);
    }
    @Override
    public int setContentView() {
        return R.layout.sub_fragment_person;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {


    }

    @Override
    public void setListener() {
        sub_set_activity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

           //设置
            case R.id.sub_set_activity:

                startActivity( new Intent(getActivity(),SubSettingActivity.class));
                break;
            default:
                break;
        }
    }

}
