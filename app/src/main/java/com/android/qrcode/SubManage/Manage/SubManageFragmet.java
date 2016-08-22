package com.android.qrcode.SubManage.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import com.android.base.BaseFragment;
import com.android.qrcode.Manage.OwnerApplyManageActivity;
import com.android.qrcode.R;
import butterknife.Bind;

/**
 * Created by liujunqin on 2016/5/31.
 */
public class SubManageFragmet extends BaseFragment implements View.OnClickListener{



    @Bind(R.id.owner_manage_layout)
    RelativeLayout owner_manage_layout;
    @Bind(R.id.tenement_manage_layout)
    RelativeLayout tenement_manage_layout;
    @Bind(R.id.owner_apply_manage_layout)
    RelativeLayout owner_apply_manage_layout;
    @Bind(R.id.house_manage_layout)
    RelativeLayout house_manage_layout;

    public SubManageFragmet() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "管理");
        bundle.putInt("icon", R.drawable.selector_main_tab_home_icon);
        this.setArguments(bundle);
    }
    @Override
    public int setContentView() {
        return R.layout.sub_manage_fragment;
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
        owner_apply_manage_layout.setOnClickListener(this);
        house_manage_layout.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            //小区管理员管理
            case R.id.owner_manage_layout:

                startActivity(new Intent(getActivity(),SubHouseManageListActivity.class));
                break;
            //小区管理
            case R.id.tenement_manage_layout:
                startActivity(new Intent(getActivity(),SubCommunityManageActivity.class));
                break;
            //认证管理
            case R.id.owner_apply_manage_layout:
                startActivity(new Intent(getActivity(),SubOwnerApplyManageActivity.class));
                break;
            //数据统计
            case R.id.house_manage_layout:

                showToast("该功能模块正在开发");
              //  startActivity(new Intent(getActivity(),HouseManageActivity.class));
                break;
            default:
                break;

        }
    }
}
