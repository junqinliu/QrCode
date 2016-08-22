package com.android.qrcode.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.OwnerListBean;
import com.android.mylibrary.model.SortModel;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.android.utils.TextUtil;
import com.android.utils.ValidateUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import butterknife.Bind;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by liujunqin on 2016/6/14.
 */
public class AddOwnerActivity extends BaseAppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.radio_man)
    RadioButton radio_man;
    @Bind(R.id.radio_woman)
    RadioButton radio_woman;

    @Bind(R.id.add_img)
    ImageView add_img;
    @Bind(R.id.name_txt)
    EditText name_txt;
    @Bind(R.id.phone_num_txt)
    EditText phone_num_txt;

    @Bind(R.id.button3)
    Button button3;

    private int sex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_owner);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.add_owner_str);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
    //    add_img.setImageResource(R.mipmap.submit);
      //  add_img.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

        add_img.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.radio_man) {
                    sex = 1;
                   // Toast.makeText(AddOwnerActivity.this, "男", Toast.LENGTH_LONG).show();
                } else {
                    sex = 2;
                  //  Toast.makeText(AddOwnerActivity.this, "女", Toast.LENGTH_LONG).show();
                }
            }
        });


        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        button3.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            //提交按钮
            case R.id.button3:

                if(TextUtil.isEmpty(name_txt.getText().toString())||TextUtil.isEmpty(phone_num_txt.getText().toString())){

                    showToast("请完善信息");
                    return;
                }

                if(!ValidateUtil.isMobile(phone_num_txt.getText().toString())){
                    showToast("请填写正确的手机号");
                    return;
                }

                createManage();
                break;

            default:
                break;

        }
    }


    private void createManage() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", phone_num_txt.getText().toString().trim());
            jsonObject.put("name", name_txt.getText().toString().trim());
            jsonObject.put("sex", sex);
            jsonObject.put("buildid",getIntent().getStringExtra("buildid"));
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

        HttpUtil.post(AddOwnerActivity.this, Constants.HOST + Constants.AddOwner, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(AddOwnerActivity.this)) {

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
                                //TODO 返回true 却没有添加至数据库中。
                                OwnerListBean sortModel = new OwnerListBean();
                                sortModel.setName(name_txt.getText().toString());
                                sortModel.setPhone(phone_num_txt.getText().toString());
                                sortModel.setSex(sex);
                               // sortModel.setHouseid(houseid);

                                Intent intent = new Intent();
                                intent.putExtra("OwnerListBean", sortModel);
                                setResult(2, intent);
                                finish();


                             /*   OwnerListBean sortModel = new OwnerListBean();
                                sortModel.setName(name_txt.getText().toString());
                                sortModel.setPhone(phone_num_txt.getText().toString());
                                sortModel.setSex(1);

                                Intent  intent = new Intent();
                                intent.putExtra("OwnerListBean",sortModel);
                                setResult(2, intent);
                                finish();*/


                                showToast("添加成功");
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
