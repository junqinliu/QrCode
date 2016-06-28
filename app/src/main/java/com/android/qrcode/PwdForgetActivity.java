package com.android.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.base.BaseAppCompatActivity;

import butterknife.Bind;

public class PwdForgetActivity extends BaseAppCompatActivity implements View.OnClickListener{



    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.btn_Next)
    Button btn_Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

    }


    @Override
    public void initView() {

        toolBar.setTitle("");
        toolbar_title.setText(R.string.forget_pwd_title);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

        btn_Next.setOnClickListener(this);
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

        //注册按钮
        case R.id.btn_Next:

          Intent intent = new Intent(PwdForgetActivity.this, PwdForgetNextActivity.class);
                     startActivity(intent);

          break;

        default:
          break;


      }
    }
}
