package com.android.qrcode.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.base.BaseAppCompatActivity;
import com.android.qrcode.R;
import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/15.
 */
public class TenementManageActivity extends BaseAppCompatActivity implements View.OnClickListener{

    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.device_reapair_layout)
    RelativeLayout device_reapair_layout;
    @Bind(R.id.complain_layout)
    RelativeLayout complain_layout;
    @Bind(R.id.message_push_layout)
    RelativeLayout message_push_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenement_manage);

    }
    @Override
    public void initView() {

        toolBar.setTitle("");
        toolbar_title.setText(R.string.tenement_manage_str);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

        device_reapair_layout.setOnClickListener(this);
        complain_layout.setOnClickListener(this);
        message_push_layout.setOnClickListener(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
           //设备报修
            case R.id.device_reapair_layout:

                startActivity(new Intent(this,DeviceRepairActivity.class));
                break;
            //业主投诉
            case R.id.complain_layout:

                startActivity(new Intent(this,OwnerComplainActivity.class));
                break;
            //信息公告推送
            case R.id.message_push_layout:

                startActivity(new Intent(this,MessagePushActivity.class));
                break;
            default:
                break;
        }

    }
}
