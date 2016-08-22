package com.android.qrcode.Manage;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.android.utils.SquareImageView;
import com.android.utils.Utils;
import com.android.utils.VoiceUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

/**
 * Created by liujunqin on 2016/6/14.
 */
public class CardQrCodeCertificatActivity extends BaseAppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;
    @Bind(R.id.binaryCode)
    SquareImageView binaryCode;
    @Bind(R.id.build_name_txt)
    TextView build_name_txt;
    @Bind(R.id.time_count_txt)
    TextView time_count_txt;

    String secret;
    String buildid;
    String buildname;
    TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_card);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.card_certification);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        add_img.setImageResource(R.mipmap.submit);
        add_img.setVisibility(View.VISIBLE);
        time = new TimeCount(30000, 1000);//构造CountDownTimer对象
    }

    @Override
    public void initData() {

        secret = getIntent().getStringExtra("secret");
        buildid = getIntent().getStringExtra("buildid");
        buildname = getIntent().getStringExtra("buildname");
        build_name_txt.setText(buildname);
        binaryCode.setImageBitmap(Utils.createQRImage(this, secret, 500, 500));
        time.start();

    }

    @Override
    public void setListener() {

        add_img.setOnClickListener(this);
        binaryCode.setOnClickListener(this);
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

            //配置按钮实时获取最新的授权码
            case R.id.add_img:

                getAuthorizateQrCode(buildid);

                break;
            //点击二维码实时获取最新的授权码
            case R.id.binaryCode:

                getAuthorizateQrCode(buildid);

                break;
            default:
                break;

        }
    }


    /**
     * 获取楼栋授权码
     */
    private void getAuthorizateQrCode(final String buildid) {


        ByteArrayEntity entity = null;


        HttpUtil.post(CardQrCodeCertificatActivity.this, Constants.HOST + Constants.AuthorizateQrCode + "/" + buildid, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(CardQrCodeCertificatActivity.this)) {

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

                                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                                binaryCode.setImageBitmap(Utils.createQRImage(CardQrCodeCertificatActivity.this, jsonObject1.getString("secret"), 500, 500));
                                time.start();

                                //给按钮添加音效
                                try{

                                    VoiceUtil.getInstance(CardQrCodeCertificatActivity.this).startVoice();

                                }catch (Exception e){

                                    e.printStackTrace();
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

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发

            if(binaryCode != null && time_count_txt != null && add_img != null) {
                binaryCode.setClickable(true);
                add_img.setClickable(true);
                time_count_txt.setText("扫描二维码授权(" + "0" + ")");
                binaryCode.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.default_qrcode));
            }
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示

            if(binaryCode != null && time_count_txt != null && add_img != null ){

                time_count_txt.setText("扫描二维码授权("+ --millisUntilFinished / 1000 + ")");
                binaryCode.setClickable(false);
                add_img.setClickable(false);
            }
        }
    }




}
