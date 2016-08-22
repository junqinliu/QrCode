package com.android.qrcode.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.android.base.BaseFragment;
import com.android.mylibrary.model.UserInfoBean;
import com.android.qrcode.R;
import com.android.utils.SharedPreferenceUtil;
import com.android.utils.TextUtil;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/5/31.
 */
public class  ManageFragmet extends BaseFragment implements View.OnClickListener{



    @Bind(R.id.owner_manage_layout)
    RelativeLayout owner_manage_layout;
    @Bind(R.id.tenement_manage_layout)
    RelativeLayout tenement_manage_layout;
    @Bind(R.id.car_manage_layout)
    RelativeLayout car_manage_layout;
    @Bind(R.id.owner_apply_manage_layout)
    RelativeLayout owner_apply_manage_layout;
    @Bind(R.id.house_manage_layout)
    RelativeLayout house_manage_layout;

    public ManageFragmet() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "管理");
        bundle.putInt("icon", R.drawable.selector_main_tab_home_icon);
        this.setArguments(bundle);
    }
    @Override
    public int setContentView() {
        return R.layout.manage_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {



    }

    @Override
    public void setListener() {
        owner_manage_layout.setOnClickListener(this);
        tenement_manage_layout.setOnClickListener(this);
        car_manage_layout.setOnClickListener(this);
        owner_apply_manage_layout.setOnClickListener(this);
        house_manage_layout.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            //业主管理
            case R.id.owner_manage_layout:

                if(!TextUtil.isEmpty(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""))){

                    UserInfoBean userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
                    if(!"Pass".equals(userInfoBean.getAduitstatus())){
                        showToast("还未通过审核");
                        return;
                    }

                }
                startActivity(new Intent(getActivity(),HouseManageListActivity.class));
                break;
            //物业管理
            case R.id.tenement_manage_layout:
                if(!TextUtil.isEmpty(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""))){

                    UserInfoBean userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
                    if(!"Pass".equals(userInfoBean.getAduitstatus())){
                        showToast("还未通过审核");
                        return;
                    }

                }
                startActivity(new Intent(getActivity(),TenementManageActivity.class));
                break;
            //设备配置
            case R.id.car_manage_layout:
                if(!TextUtil.isEmpty(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""))){

                    UserInfoBean userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
                    if(!"Pass".equals(userInfoBean.getAduitstatus())){
                        showToast("还未通过审核");
                        return;
                    }

                }
                startActivity(new Intent(getActivity(),CardManageActivity.class));
                break;

            //楼栋管理
            case R.id.house_manage_layout:
                if(!TextUtil.isEmpty(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""))){

                    UserInfoBean userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
                    if(!"Pass".equals(userInfoBean.getAduitstatus())){
                        showToast("还未通过审核");
                        return;
                    }

                }
                startActivity(new Intent(getActivity(),HouseManageActivity.class));
                break;

            //业主申请认证管理
            case R.id.owner_apply_manage_layout:
                if(!TextUtil.isEmpty(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""))){

                    UserInfoBean userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
                    if(!"Pass".equals(userInfoBean.getAduitstatus())){
                        showToast("还未通过审核");
                        return;
                    }

                }
                startActivity(new Intent(getActivity(),OwnerApplyManageActivity.class));
                break;

            default:
                break;

        }
    }



}
