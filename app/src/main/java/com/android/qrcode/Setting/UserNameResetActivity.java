package com.android.qrcode.Setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.BaseAppCompatActivity;
import com.android.qrcode.R;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/14.
 */
public class UserNameResetActivity extends BaseAppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;

    @Bind(R.id.user_name_edit)
    EditText user_name_edit;
    @Bind(R.id.user_name_submit)
    Button user_name_submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.user_name_reset);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        //add_img.setImageResource(R.mipmap.submit);
       // add_img.setVisibility(View.VISIBLE);
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

        user_name_submit.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.user_name_submit:


                break;

            default:
                break;

        }
    }

}
