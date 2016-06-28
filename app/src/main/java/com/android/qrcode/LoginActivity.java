package com.android.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.adapter.MainActivityAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.qrcode.Account.AccountFragmet;
import com.android.qrcode.Card.CardFragmet;
import com.android.qrcode.Manage.ManageFragmet;
import com.android.qrcode.QuickCard.QuickCardFragment;
import com.android.qrcode.Setting.SettingFragmet;
import com.android.utils.Page;
import com.android.utils.Utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

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

            RequestParams params = new RequestParams("http://192.168.0.13:8080/upload");
            params.addBodyParameter("wd", "xUtils");
            params.addBodyParameter("wd1", "xUtils");
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
