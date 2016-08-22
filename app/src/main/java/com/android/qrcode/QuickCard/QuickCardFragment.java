package com.android.qrcode.QuickCard;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.base.BaseFragment;
import com.android.constant.Constants;
import com.android.mylibrary.model.RoomCardBean;
import com.android.mylibrary.model.UserInfoBean;
import com.android.qrcode.Card.CardQrCodeActivity;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.ImageOpera;
import com.android.utils.NetUtil;
import com.android.utils.SharedPreferenceUtil;
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
import cz.msebera.android.httpclient.Header;

/**
 * Created by liujunqin on 2016/6/12.
 */
public class QuickCardFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.binaryCode)
    SquareImageView binaryCode;
    @Bind(R.id.build_name_quick)
    TextView build_name_quick;
    @Bind(R.id.time_count_txt)
     TextView time_count_txt;

    List<RoomCardBean> roomCardBeanListTemp = new ArrayList<>();
    RoomCardBean roomCardBean;
    int pageNumber = 0;
    int pageSize = 10;
    String phone;

    TimeCount time;


    public QuickCardFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "");
        bundle.putInt("icon", 0);
        this.setArguments(bundle);
    }

    @Override
    public int setContentView() {
        return R.layout.find_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {


        Bundle bundle = getArguments();
        phone = bundle.getString("phone");

        time = new TimeCount(30000, 1000);//构造CountDownTimer对象

    }

    @Override
    public void setListener() {

        binaryCode.setOnClickListener(this);
    }


    /**
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){



            //实时获取最新的选中快捷房卡
            if(!"".equals(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("RoomCardBean", ""))){

                roomCardBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("RoomCardBean", ""), RoomCardBean.class);
                getQrCode(roomCardBean.getBuildid(),roomCardBean.getName());
            }else{

                //这边调接口是避免用户进入首页 没有去配置微卡 直接点击快捷房卡出现没有默认快捷房卡的问题

                UserInfoBean userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
                String houseid = "";

                if (userInfoBean != null) {

                    houseid = userInfoBean.getHouseid();
                    if(TextUtil.isEmpty(houseid)){

                        getUserInfo();
                    }else{

                        getCardList();
                    }

                }

            }

        }else{

        }

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            //点击二维码实时获取最新的二维码
            case R.id.binaryCode:


                if(!TextUtil.isEmpty(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""))){

                    UserInfoBean userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
                    if(!"PASS".equals(userInfoBean.getAduitstatus())){
                        showToast("还未通过审核");
                        return;
                    }

                }


                //实时获取最新的选中快捷房卡
                if(!"".equals(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("RoomCardBean", ""))){

                    roomCardBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("RoomCardBean", ""), RoomCardBean.class);
                    getQrCode(roomCardBean.getBuildid(),roomCardBean.getName());
                }else{
                //表示没有在房卡列表配置过快捷房卡 那就是取得默认的第一张房卡作为快捷房卡
                    if(roomCardBeanListTemp.size()>0){

                        getQrCode(roomCardBeanListTemp.get(0).getBuildid(),roomCardBeanListTemp.get(0).getName());
                    }
                }

                break;
            default:
                break;

        }
    }



    /**
     * 获取微卡列表
     */
    private void getCardList() {

        UserInfoBean userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
        String houseid = "";

        if (userInfoBean != null) {

            houseid = userInfoBean.getHouseid();
        }
        RequestParams params = new RequestParams();
        params.put("pageSize", pageSize);
        params.put("pageNumber", pageNumber);

        HttpUtil.get(Constants.HOST + Constants.build + "/" + houseid, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(getActivity())) {

                    showToast("当前网络不可用,请检查网络");
                    return;

                }
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (responseBody != null) {
                    try {
                        String str = new String(responseBody);
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject != null) {

                            if (jsonObject.getBoolean("success")) {

                                roomCardBeanListTemp.clear();
                                JSONObject gg = new JSONObject(jsonObject.getString("data"));
                                roomCardBeanListTemp = JSON.parseArray(gg.getJSONArray("items").toString(), RoomCardBean.class);
                                if (roomCardBeanListTemp != null && roomCardBeanListTemp.size() > 0) {

                                    getQrCode(roomCardBeanListTemp.get(0).getBuildid(),roomCardBeanListTemp.get(0).getName());

                                } else {


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
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody != null) {

                    binaryCode.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.default_qrcode));

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
     * 获取楼栋二维码
     */
    private void getQrCode(final String buildid, final String buildname){

        RequestParams param  = new RequestParams();


        HttpUtil.get(Constants.HOST + Constants.GetQrCode + "/" + buildid + "/card",param , new AsyncHttpResponseHandler() {
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

                                build_name_quick.setText(buildname);
                                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                                binaryCode.setImageBitmap(Utils.createQRImage(getActivity(), jsonObject1.getString("secret"), 500, 500));
                                String  localUrl = ImageOpera.savePicToSdcard(Utils.createQRImage(getActivity(), jsonObject1.getString("secret"), 500, 500), getOutputMediaFile(), "MicroCode.png");
                                time.start();

                                //给按钮添加音效
                                try{

                                    VoiceUtil.getInstance(getActivity()).startVoice();

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

                        binaryCode.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.default_qrcode));
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



    private void getUserInfo(){

        RequestParams params = new RequestParams();


        HttpUtil.get(Constants.HOST + Constants.getUserInfo,params,new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                if(!NetUtil.checkNetInfo(getActivity())){

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

                            if(jsonObject.getBoolean("success")){


                                UserInfoBean userInfoBean = JSON.parseObject(jsonObject.getJSONObject("data").toString(),UserInfoBean.class);
                                if(!TextUtil.isEmpty(phone)){

                                    userInfoBean.setPhone(phone);
                                }
                                String  userInfoBeanStr = JSON.toJSONString(userInfoBean);
                                SharedPreferenceUtil.getInstance(getActivity()).putData("UserInfo", userInfoBeanStr);

                                //配置请求接口全局token 和 userid
                                if (userInfoBean != null) {

                                    HttpUtil.getClient().addHeader("Token", userInfoBean.getToken());
                                    HttpUtil.getClient().addHeader("Userid", userInfoBean.getUserid());

                                }

                                /**
                                 * 这里调用获取用户信息接口主要是要获取houseid值，因为刚进来默认来到快捷房卡时 houseid是null
                                 */
                                getCardList();



                            }else{

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

                if(responseBody != null){
                    try {
                        String str1 = new String(responseBody);
                        JSONObject jsonObject1 = new JSONObject(str1);
                        showToast(jsonObject1.getString("msg"));

                    }catch (JSONException e){
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

            if(binaryCode != null && time_count_txt != null ) {
                binaryCode.setClickable(true);
                time_count_txt.setText("扫描二维码开门(" + "0" + ")");
                binaryCode.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.default_qrcode));
            }
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示

            if(binaryCode != null && time_count_txt != null ){

                time_count_txt.setText("扫描二维码开门("+ --millisUntilFinished / 1000 + ")");
                binaryCode.setClickable(false);
            }
        }
    }




    /**
     * 将图片保存到本地文件中
     */
    private String getOutputMediaFile() {

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            //Toast.makeText(getContext(),"SD卡可用",Toast.LENGTH_SHORT).show();
        }


        //get the mobile Pictures directory   /storage/emulated/0/Pictures/IMAGE_20160315_134742.jpg
        File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if(!picDir.exists()){
            picDir.mkdir();
        }

        String str = picDir.getPath() + File.separator;
        return str;
    }

}
