package com.android.qrcode.SubManage.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.base.BaseFragment;
import com.android.mylibrary.model.UserInfoBean;
import com.android.qrcode.Account.LogActivity;
import com.android.qrcode.R;
import com.android.utils.SharedPreferenceUtil;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/5/31.
 */
public class SubAccountFragmet extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.sub_set_activity)
    RelativeLayout sub_set_activity;
    @Bind(R.id.user_name_txt)
    TextView user_name_txt;
    @Bind(R.id.user_phone_txt)
    TextView user_phone_txt;

    private UserInfoBean userInfoBean = new UserInfoBean();

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

     /*   userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
        if(userInfoBean != null){

            user_name_txt.setText(userInfoBean.getName());
            user_phone_txt.setText("手机号码      " + userInfoBean.getPhone());

        }
*/
    }

    @Override
    public void onResume() {
        super.onResume();
        userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
        if(userInfoBean != null){

            user_name_txt.setText(userInfoBean.getName());
            user_phone_txt.setText("手机号码      " + userInfoBean.getPhone());

        }

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){

            userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
            if(userInfoBean != null){

                user_name_txt.setText(userInfoBean.getName());
                user_phone_txt.setText("手机号码      " + userInfoBean.getPhone());

            }
        }

    }
}
