package com.android.qrcode;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.base.BaseAppCompatActivity;

import butterknife.Bind;

public class RegisterActivity extends BaseAppCompatActivity implements View.OnClickListener{



    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }


    @Override
    public void initView() {

        toolBar.setTitle("");
        toolbar_title.setText(R.string.register_title);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

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
       /* case R.id.btn_login:

          Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
          startActivity(intent);
          finish();
          break;*/
        default:
          break;


      }
    }
}
