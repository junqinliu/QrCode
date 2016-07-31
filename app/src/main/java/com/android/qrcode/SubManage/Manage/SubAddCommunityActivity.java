package com.android.qrcode.SubManage.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.application.AppContext;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.AddressBean;
import com.android.mylibrary.model.OwnerListBean;
import com.android.qrcode.Address.ProviceActivity;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * 添加小区
 * Created by liujunqin on 2016/6/14.
 */
public class SubAddCommunityActivity extends BaseAppCompatActivity implements View.OnClickListener {


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
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.comfirm)
    Button comfirm;

    private AppContext myApplicaton;

    private AddressBean addressBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_community_activity);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.add_community_str);
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
                startActivity(new Intent(SubAddCommunityActivity.this, ProviceActivity.class));
            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //提交按钮
            case R.id.add_img:


                break;  //提交按钮
            case R.id.comfirm:
                
                createData();
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

    private void createData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", village.getText().toString().trim());
            jsonObject.put("areacode", addressBean.getAreaCode());
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

        HttpUtil.post(SubAddCommunityActivity.this, Constants.HOST + Constants.add_cell,entity,"application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(SubAddCommunityActivity.this)) {

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
                                showToast("添加成功");
                                finish();
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
