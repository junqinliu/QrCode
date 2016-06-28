package com.android.qrcode.Manage;

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
public class MessagePushActivity extends BaseAppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;
    @Bind(R.id.msg_title)
    EditText msg_title;
    @Bind(R.id.msg_content)
    EditText msg_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_push);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.message_push);
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
