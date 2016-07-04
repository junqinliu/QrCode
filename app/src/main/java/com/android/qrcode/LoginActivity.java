package com.android.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.Login;
import com.google.gson.Gson;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

import butterknife.Bind;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener{



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

      switch (view.getId()){

        //登录按钮
        case R.id.btn_login:
            Login login = new Login();
            login.setUserCode("liujq");
            login.setUserPhone("15522503900");
            login.setPhoneType("HM 1SW");
            login.setUserSim("89860110421307064473");
            login.setUserPin("865624021665253");
            Gson gson = new Gson();
            String jsonstr = gson.toJson(login);
            RequestParams params = new RequestParams(Constants.HOST+Constants.Login);
            params.addBodyParameter("loginRequest", jsonstr);
         //   params.addBodyParameter("wd1", "xUtils");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFinished() {
                   // Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                }
            });


            Intent intent1;
            if("1".equals(ed_account.getText().toString())){

                 intent1 = new Intent(LoginActivity.this, MainActivity.class);
            }else{

                intent1 = new Intent(LoginActivity.this, SubMainActivity.class);
            }

          startActivity(intent1);

          break;

        //注册按钮
       case  R.id.tv_resiger:

           /*RegisterPage registerPage = new RegisterPage();
           registerPage.setRegisterCallback(new EventHandler() {
               public void afterEvent(int event, int result, Object data) {
                  // 解析注册结果
                   if (result == SMSSDK.RESULT_COMPLETE) {
                       @SuppressWarnings("unchecked")
                       HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                       String country = (String) phoneMap.get("country");
                       String phone = (String) phoneMap.get("phone");

                      // 提交用户信息
                       //registerUser(country, phone);
                   }
               }
           });
           registerPage.show(LoginActivity.this);*/
          Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
          startActivity(intent2);

          break;

        //忘记密码
        case  R.id.tv_forgetpassword:

          Intent intent3 = new Intent(LoginActivity.this, PwdForgetActivity.class);
          startActivity(intent3);

          break;
        default:
          break;


      }
    }
}
