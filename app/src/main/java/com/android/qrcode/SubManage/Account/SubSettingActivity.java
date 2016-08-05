package com.android.qrcode.SubManage.Account;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.android.application.ExitApplication;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.OwnerListBean;
import com.android.qrcode.LoginActivity;
import com.android.qrcode.R;
import com.android.qrcode.Setting.IdeaFeedBackActivity;
import com.android.qrcode.Setting.PwdResetActivity;
import com.android.qrcode.Setting.UserNameResetActivity;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.android.utils.SharedPreferenceUtil;
import com.android.utils.TextUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

/**
 * Created by liujunqin on 2016/6/14.
 */
public class SubSettingActivity extends BaseAppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;
    @Bind(R.id.pwd_reset_layout)
    RelativeLayout pwd_reset_layout;
    @Bind(R.id. user_name_reset_layout)
    RelativeLayout  user_name_reset_layout;
    @Bind(R.id.feed_back_layout)
    RelativeLayout feed_back_layout;
    @Bind(R.id.link_people_layout)
    RelativeLayout link_people_layout;
    @Bind(R.id.about_layout)
    RelativeLayout about_layout;
    @Bind(R.id.login_out_layout)
    RelativeLayout login_out_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_setting_fragment);
        ExitApplication.getInstance().addAllActivity(this);
    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.sub_add_tenement_str);
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
        pwd_reset_layout.setOnClickListener(this);
        user_name_reset_layout.setOnClickListener(this);
        feed_back_layout.setOnClickListener(this);
        link_people_layout.setOnClickListener(this);
        about_layout.setOnClickListener(this);
        login_out_layout.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            //密码修改
            case R.id.pwd_reset_layout:

                startActivity( new Intent(SubSettingActivity.this,PwdResetActivity.class));
                break;
            //用户名修改
            case R.id.user_name_reset_layout:

                startActivity( new Intent(SubSettingActivity.this,UserNameResetActivity.class));
                break;
            //意见反馈
            case R.id.feed_back_layout:

                startActivity( new Intent(SubSettingActivity.this,IdeaFeedBackActivity.class));
                break;
            //联系客服
            case R.id.link_people_layout:
                break;
            //关于
            case R.id.about_layout:
                break;
            //退出登录
            case R.id.login_out_layout:

                new AlertDialog.Builder(SubSettingActivity.this, AlertDialog.THEME_HOLO_LIGHT).setTitle("提示")
                        .setMessage("是否退出登录？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", dialogListener).create().show();
                break;
            default:
                break;

        }
    }

    android.content.DialogInterface.OnClickListener dialogListener = new android.content.DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

            //调用退出登录接口
            Logout();

        }
    };


    /**
     * 注销方法
     */
    private void Logout(){


        ByteArrayEntity entity = null;
        HttpUtil.post(SubSettingActivity.this, Constants.HOST + Constants.LoginOut, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(SubSettingActivity.this)) {

                    showToast("当前网络不可用,请检查网络");
                    return;
                }
            }


            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                if (responseBody != null) {
                    try {
                        String str = new String(responseBody);
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject != null) {

                            if (jsonObject.getBoolean("success")) {

                                showToast("退出登录成功");
                                //跳转到登录接口 并且把本地文件的数据清除掉
                                Intent intent = new Intent(SubSettingActivity.this, LoginActivity.class);
                                startActivity(intent);
                                SharedPreferenceUtil.getInstance(SubSettingActivity.this).deleteData();
                                ExitApplication.getInstance().exitAll();

                            } else {

                                showToast("请求接口失败，请联系管理员");
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody != null) {
                    try {
                        String str1 = new String(responseBody);
                        JSONObject jsonObject1 = new JSONObject(str1);
                        showToast(jsonObject1.getString("msg"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }


            @Override
            public void onFinish() {
                super.onFinish();

            }


        });




    }

}
