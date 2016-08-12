package com.android.qrcode.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.base.BaseFragment;
import com.android.constant.Constants;
import com.android.mylibrary.model.UserInfoBean;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.android.utils.SharedPreferenceUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/5/31.
 */
public class AccountFragmet extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.log_activity)
    RelativeLayout log_activity;
    @Bind(R.id.user_name)
    TextView user_name;
    @Bind(R.id.user_phone)
    TextView user_phone;
    @Bind(R.id.post_message)
    TextView post_message;




    public AccountFragmet() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "用户");
        bundle.putInt("icon", R.drawable.selector_main_tab_person_icon);
        this.setArguments(bundle);
    }
    @Override
    public int setContentView() {
        return R.layout.fragment_person;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {


    }

    @Override
    public void setListener() {
        log_activity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            //往来日志
            case R.id.log_activity:

                startActivity( new Intent(getActivity(),LogActivity.class));
                break;
            default:
                break;
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){

            UserInfoBean userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);

            user_name.setText(userInfoBean.getName());
            user_phone.setText("手机号码     "+userInfoBean.getPhone() == null ? "":userInfoBean.getPhone());
            post_message.setText("管辖小区    " + userInfoBean.getHousename());

        }else{


        }

    }



    private void getUserInfo(){

        RequestParams params = new RequestParams();


        HttpUtil.get(Constants.HOST + Constants.getUserInfo, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                if (!NetUtil.checkNetInfo(getActivity())) {

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


                                UserInfoBean userInfoBean = JSON.parseObject(jsonObject.getJSONObject("data").toString(), UserInfoBean.class);
                              //  userInfoBean.setPhone(phone);
                                String userInfoBeanStr = JSON.toJSONString(userInfoBean);
                                SharedPreferenceUtil.getInstance(getActivity()).putData("UserInfo", userInfoBeanStr);

                                //配置请求接口全局token 和 userid
                                if (userInfoBean != null) {

                                    HttpUtil.getClient().addHeader("Token", userInfoBean.getToken());
                                    HttpUtil.getClient().addHeader("Userid", userInfoBean.getUserid());

                                }



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
