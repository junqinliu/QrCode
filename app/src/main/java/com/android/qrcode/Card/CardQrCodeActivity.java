package com.android.qrcode.Card;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.adapter.LogAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.LogBean;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.ImageOpera;
import com.android.utils.NetUtil;
import com.android.utils.SquareImageView;
import com.android.utils.TextUtil;
import com.android.utils.Utils;
import com.android.utils.VoiceUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

/**
 * Created by liujunqin on 2016/6/13.
 */
public class CardQrCodeActivity extends BaseAppCompatActivity implements View.OnClickListener {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;

    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.binaryCode)
    SquareImageView binaryCode;

    @Bind(R.id.build_name)
    TextView build_name;

    String secret;
    String buildname;
    String buildid;
    String localUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_qr_code);
    }

    @Override
    public void initView() {

        toolBar.setTitle("");
        toolbar_title.setText(R.string.house_card);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        //add_img.setImageResource(R.mipmap.submit);
        add_img.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {

        secret = getIntent().getStringExtra("secret");
        buildname = getIntent().getStringExtra("buildname");
        buildid = getIntent().getStringExtra("buildid");
        build_name.setText(buildname);
        binaryCode.setImageBitmap(Utils.createQRImage(CardQrCodeActivity.this, secret, 500, 500));
        localUrl = ImageOpera.savePicToSdcard(Utils.createQRImage(CardQrCodeActivity.this, secret, 500, 500), getOutputMediaFile(), "MicroCode.png");
    }

    @Override
    public void setListener() {


        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


        binaryCode.setOnClickListener(this);
        add_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){

           //获取二维码
           case R.id.binaryCode:

               //给按钮添加音效
               try{

                   VoiceUtil.getInstance(CardQrCodeActivity.this).startVoice();

               }catch (Exception e){

                   e.printStackTrace();
               }

               getQrCode(buildid);

               break;
           //分享二维码
           case R.id.add_img:
               OnekeyShare oks = new OnekeyShare();
               //关闭sso授权
               oks.disableSSOWhenAuthorize();

               // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
               //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
               // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
               oks.setTitle("微卡");
               // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
               // oks.setTitleUrl("http://sharesdk.cn");
               // text是分享文本，所有平台都需要这个字段
               oks.setText("微卡");

               //oks.setTitleUrl("http://mob.com");

             //  oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");

               // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
               if(!TextUtil.isEmpty(localUrl)){

                   oks.setImagePath(localUrl);//确保SDcard下面存在此张图片
               }
               // url仅在微信（包括好友和朋友圈）中使用
               //oks.setUrl("http://sharesdk.cn");
               // comment是我对这条分享的评论，仅在人人网和QQ空间使用
               //   oks.setComment("我是测试评论文本");
               // site是分享此内容的网站名称，仅在QQ空间使用
               // oks.setSite(getString(R.string.app_name));
               // siteUrl是分享此内容的网站地址，仅在QQ空间使用
               // oks.setSiteUrl("http://sharesdk.cn");

               // 启动分享GUI
               oks.show(this);

               break;

           default:
               break;
       }
    }



    /**
     * 获取楼栋二维码
     */
    private void getQrCode(String buildid){

        RequestParams param  = new RequestParams();
        HttpUtil.get(Constants.HOST + Constants.GetQrCode + "/" + buildid + "/card", param, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(CardQrCodeActivity.this)) {

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
                                binaryCode.setImageBitmap(Utils.createQRImage(CardQrCodeActivity.this, jsonObject1.getString("secret"), 500, 500));
                                localUrl = ImageOpera.savePicToSdcard(Utils.createQRImage(CardQrCodeActivity.this, jsonObject1.getString("secret"), 500, 500), getOutputMediaFile(), "MicroCode.png");

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


    /**
     * 将图片保存到本地文件中
     */
    private String getOutputMediaFile() {

        //get the mobile Pictures directory   /storage/emulated/0/Pictures/IMAGE_20160315_134742.jpg
        File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String str = picDir.getPath() + File.separator;
        return str;
    }

}
