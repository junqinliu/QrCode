package com.android.qrcode.Setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.SortModel;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
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
public class PwdResetActivity extends BaseAppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;

    @Bind(R.id.old_pwd_edt)
    EditText old_pwd_edt;
    @Bind(R.id.new_pwd_edt)
    EditText new_pwd_edt;
    @Bind(R.id.comfirm_pwd_edt)
    EditText comfirm_pwd_edt;
    @Bind(R.id.modify_btn)
    Button modify_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warranty);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.psw_reset);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        add_img.setImageResource(R.mipmap.submit);
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

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            //保存
            case R.id.modify_btn:

                if(TextUtil.isEmpty(old_pwd_edt.getText().toString())){

                    showToast("请输入原始密码");
                    return;
                }
                if(TextUtil.isEmpty(new_pwd_edt.getText().toString())){

                    showToast("请输入新密码");
                    return;
                }
                if(TextUtil.isEmpty(comfirm_pwd_edt.getText().toString())){

                    showToast("请输入确认密码");
                    return;
                }

                if(!new_pwd_edt.getText().toString().equals(comfirm_pwd_edt.getText().toString())){

                    showToast("新密码和确认密码不一样");
                    return;
                }

                if(new_pwd_edt.getText().toString().length() < 6){

                    showToast("密码至少为6位数");

                    return;
                }

                submit();
                break;

            default:
                break;

        }
    }


    /**
     * 修改密码提交
     */
    private void submit(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password",old_pwd_edt.getText().toString());
            jsonObject.put("newpassword",new_pwd_edt.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonObject.toString(),"utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpUtil.put(PwdResetActivity.this, Constants.HOST + Constants.ModifyPwd, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(PwdResetActivity.this)) {

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

                                showToast("密码修改成功");
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
