package com.android.qrcode.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.base.BaseFragment;
import com.android.mylibrary.model.UserInfoBean;
import com.android.qrcode.R;
import com.android.utils.SharedPreferenceUtil;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/5/31.
 */
public class AccountFragmet extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.log_activity)
    RelativeLayout log_activity;
    @Bind(R.id.user_name)
    TextView user_name;
    @Bind(R.id.user_phone)
    TextView user_phone;
    @Bind(R.id.post_message)
    TextView post_message;




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


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){

            UserInfoBean userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);

            user_name.setText(userInfoBean.getName());
            user_phone.setText("手机号码     "+userInfoBean.getPhone());
            post_message.setText("管辖小区    " + userInfoBean.getHousename());

        }else{


        }

    }

}
