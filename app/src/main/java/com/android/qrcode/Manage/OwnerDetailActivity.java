package com.android.qrcode.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.base.BaseAppCompatActivity;
import com.android.mylibrary.model.OwnerListBean;
import com.android.mylibrary.model.SortModel;
import com.android.qrcode.R;
import com.android.qrcode.SubManage.Manage.SubAddOwnerActivity;
import com.android.qrcode.SubManage.Manage.SubOwnerEditActivity;
import com.android.utils.TextUtil;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/14.
 */
public class OwnerDetailActivity extends BaseAppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;

    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.phone_num)
    TextView phone_num;
    @Bind(R.id.username)
    TextView username;
    @Bind(R.id.sex)
    TextView sex;

    @Bind(R.id.open_door_layout)
    RelativeLayout open_door_layout;

    private SortModel sortModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_owner_detail);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.add_owner_detail_str);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        add_img.setImageResource(R.mipmap.submit);

        if("1".equals(getIntent().getStringExtra("Flag"))){
            add_img.setVisibility(View.GONE);
        }else{
           add_img.setVisibility(View.GONE);
        }

    }

    @Override
    public void initData() {


        sortModel =(SortModel) getIntent().getSerializableExtra("Model");
        name.setText(sortModel.getName());
        phone_num.setText("手机号码：   "+sortModel.getPhoneNum());

        username.setText("姓名：   "+sortModel.getPhoneNum());

        if("0".equals(sortModel.getSex())){

            sex.setText("性   别：   "+"男");
        }else{

            sex.setText("性   别：   "+"女");
        }

        if("1".equals(getIntent().getStringExtra("Flag"))){
            open_door_layout.setVisibility(View.VISIBLE);
        }else{
            open_door_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void setListener() {

        add_img.setOnClickListener(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        open_door_layout.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.add_img:
                Intent intent = new Intent(new Intent(this, SubOwnerEditActivity.class));
                intent.putExtra("houseid", sortModel.getUserid());
                startActivity(intent);

                break;
            //开门权限
            case R.id.open_door_layout:

                Intent intentlimt = new Intent(OwnerDetailActivity.this,OpenDoorLimitActivity.class);
                intentlimt.putExtra("sourceuserid",sortModel.getUserid()+"");
                startActivity(intentlimt);

                break;

            default:
                break;

        }
    }

}
