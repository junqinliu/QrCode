package com.android.qrcode.SubManage.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.base.BaseAppCompatActivity;
import com.android.mylibrary.model.OwnerListBean;
import com.android.qrcode.R;
import com.android.utils.TextUtil;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/14.
 */
public class SubAddCommunityActivity extends BaseAppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;



    @Bind(R.id.add_img)
    ImageView add_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_community_activity);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.add_community_str);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        add_img.setImageResource(R.mipmap.submit);
        add_img.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {

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

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            //提交按钮
            case R.id.add_img:


                break;

            default:
                break;

        }
    }

}
