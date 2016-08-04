package com.android.qrcode.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.application.AppContext;
import com.android.application.ExitApplication;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.AddressBean;
import com.android.mylibrary.model.UserInfoBean;
import com.android.qrcode.Address.ProviceActivity;
import com.android.qrcode.MainActivity;
import com.android.qrcode.Manage.CommunityActivity;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.android.utils.SharedPreferenceUtil;
import com.android.utils.TextUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * 申请审核
 * Created by liujunqin on 2016/6/14.
 */
public class ApplyActivity extends BaseAppCompatActivity implements View.OnClickListener {


    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;


    @Bind(R.id.add_img)
    ImageView add_img;
    @Bind(R.id.search_text)
    EditText searchText;
    @Bind(R.id.search_button)
    ImageView searchButton;
    @Bind(R.id.provice)
    EditText provice;
    @Bind(R.id.city)
    EditText city;
    @Bind(R.id.zone)
    EditText zone;
    @Bind(R.id.village)
    EditText village;
    @Bind(R.id.user_phone)
    EditText user_phone;
    @Bind(R.id.user_name)
    EditText user_name;
    @Bind(R.id.comfirm)
    Button comfirm;

    @Bind(R.id.xiaoqu)
    LinearLayout xiaoqu;

    private AppContext myApplicaton;

    private AddressBean addressBean;

    private String houseid;

    private String flag = "";//用来标识是哪个入口进来


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_apply_activity);
        ExitApplication.getInstance().addAllActivity(this);
    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.add_limit_str);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
       // add_img.setImageResource(R.mipmap.submit);
        //add_img.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {

        flag = getIntent().getStringExtra("flag");

        if("register".equals(flag)){

            UserInfoBean userInfoBean = null;
            if(!TextUtil.isEmpty(SharedPreferenceUtil.getInstance(this).getSharedPreferences().getString("UserInfo", ""))){

                userInfoBean  = JSON.parseObject(SharedPreferenceUtil.getInstance(this).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
            }

            //配置请求接口全局token 和 userid
            if (userInfoBean != null) {

                HttpUtil.getClient().addHeader("Token", userInfoBean.getToken());
                HttpUtil.getClient().addHeader("Userid", userInfoBean.getUserid());

            }
        }

    }

    @Override
    public void setListener() {

       // add_img.setOnClickListener(this);

        comfirm.setOnClickListener(this);


        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        provice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApplyActivity.this, ProviceActivity.class));
            }
        });

       // xiaoqu.setOnClickListener(this);
        village.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //提交按钮
            case R.id.add_img:


                break;

            //获取小区列表
            case R.id.village:

                if(TextUtil.isEmpty(provice.getText().toString())){

                    showToast("请先选省份");
                    return;
                }

                Intent intent = new Intent(this,CommunityActivity.class);
                intent.putExtra("areacode",addressBean.getAreaCode());

                startActivityForResult(intent,100);
                break;

            //提交按钮
            case R.id.comfirm:

                if(TextUtil.isEmpty(houseid)){
                    showToast("请选择小区");
                    return;
                }

                if(TextUtil.isEmpty(user_phone.getText().toString())){

                    showToast("请输入手机号码");
                    return;
                }
                if(TextUtil.isEmpty(user_name.getText().toString())){
                    showToast("请输入名字");
                    return;
                }
                applyLimit();
                break;

            default:
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myApplicaton = (AppContext) getApplication();
        addressBean = myApplicaton.getAddressBean();
        if (addressBean != null) {
            provice.setText(addressBean.getProvinceName());
            city.setText(addressBean.getCityName());
            zone.setText(addressBean.getAreaName());

        }
    }

    @Override
    protected void onDestroy() {
        addressBean = null;
        myApplicaton.setAddressBean(addressBean);

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){

            if(resultCode == 200){

                village.setText(data.getStringExtra("housename"));
                houseid = data.getStringExtra("houseid");
            }
        }

    }

    private void applyLimit() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("houseid",houseid);
            jsonObject.put("name",user_name.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpUtil.post(ApplyActivity.this, Constants.HOST + Constants.Apply,entity,"application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(ApplyActivity.this)) {

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
                                showToast("提交成功");

                                if("register".equals(flag)){
                                   //从注册界面进来
                                    Intent intent = new Intent(ApplyActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    ExitApplication.getInstance().exitAll();
                                    finish();
                                }else{
                                    //进入首页后进来的
                                    if(!TextUtil.isEmpty(SharedPreferenceUtil.getInstance(ApplyActivity.this).getSharedPreferences().getString("UserInfo", ""))){

                                        UserInfoBean   userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(ApplyActivity.this).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
                                        userInfoBean.setAduitstatus("AUDITING");
                                        String  userInfoBeanStr = JSON.toJSONString(userInfoBean);
                                        SharedPreferenceUtil.getInstance(ApplyActivity.this).putData("UserInfo", userInfoBeanStr);
                                    }

                                    finish();
                                }
                            } else {
                                showToast("请求接口失败，请联系管理员");
                            }
                        }
                    } catch (Exception e) {
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


        });
    }
}
