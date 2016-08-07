package com.android.qrcode.Setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.UserInfoBean;
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
import cz.msebera.android.httpclient.entity.StringEntity;

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


                if(TextUtil.isEmpty(user_name_edit.getText().toString())){

                    showToast("请填写用户名");
                    return;
                }

                modifyUserName();
                break;

            default:
                break;

        }
    }

    /**
     * 修改用户名
     */
    private void modifyUserName(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",user_name_edit.getText().toString());
           // jsonObject.put("sex","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonObject.toString(),"utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpUtil.put(UserNameResetActivity.this, Constants.HOST + Constants.ModifyUserName, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(UserNameResetActivity.this)) {

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

                                showToast("用户名修改成功");
                                UserInfoBean   userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(UserNameResetActivity.this).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
                                userInfoBean.setName(user_name_edit.getText().toString());
                                String  userInfoBeanStr = JSON.toJSONString(userInfoBean);
                                SharedPreferenceUtil.getInstance(UserNameResetActivity.this).putData("UserInfo", userInfoBeanStr);
                                finish();
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
