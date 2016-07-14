package com.android.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.Login;
import com.android.utils.HttpUtil;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener {


    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.tv_resiger)
    TextView tv_resiger;
    @Bind(R.id.tv_forgetpassword)
    TextView tv_forgetpassword;

    @Bind(R.id.ed_account)
    EditText ed_account;

    @Bind(R.id.ed_password)
    EditText ed_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    @Override
    public void initView() {


    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

        btn_login.setOnClickListener(this);
        tv_resiger.setOnClickListener(this);
        tv_forgetpassword.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            //登录按钮
            case R.id.btn_login:

                RequestParams params = new RequestParams();
                params.put("phone", "15522503900");
                params.put("password", "liujq");

                HttpUtil.post("http://120.76.202.60:8080/microcard/v0.1/user/login", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();


                    }


                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                        if (responseBody != null) {
                            try {
                                String str = new String(responseBody);
                                JSONObject jsonObject = new JSONObject(str);
                                if (jsonObject != null) {
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                        if (responseBody != null) {


                            String str = new String(responseBody);
                            System.out.print(str);
                        }
                    }


                    @Override
                    public void onFinish() {
                        super.onFinish();
                        //closeLoadDialog();
                    }


                });


                Intent intent1;
                if ("1".equals(ed_account.getText().toString())) {

                    intent1 = new Intent(LoginActivity.this, MainActivity.class);
                } else {

                    intent1 = new Intent(LoginActivity.this, SubMainActivity.class);
                }

                startActivity(intent1);

                break;

            //注册按钮
            case R.id.tv_resiger:


                Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent2);

                break;

            //忘记密码
            case R.id.tv_forgetpassword:

                Intent intent3 = new Intent(LoginActivity.this, PwdForgetActivity.class);
                startActivity(intent3);

                break;
            default:
                break;


        }
    }
}
