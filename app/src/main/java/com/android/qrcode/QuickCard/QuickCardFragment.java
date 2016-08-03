package com.android.qrcode.QuickCard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.base.BaseFragment;
import com.android.constant.Constants;
import com.android.mylibrary.model.RoomCardBean;
import com.android.mylibrary.model.UserInfoBean;
import com.android.qrcode.Card.CardQrCodeActivity;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.android.utils.SharedPreferenceUtil;
import com.android.utils.SquareImageView;
import com.android.utils.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

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

    List<RoomCardBean> roomCardBeanListTemp = new ArrayList<>();
    RoomCardBean roomCardBean;
    int pageNumber = 0;
    int pageSize = 10;


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

        //这边调接口是避免用户进入首页 没有去配置微卡 直接点击快捷房卡出现没有默认快捷房卡的问题
        getCardList();

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
                getQrCode(roomCardBean.getBuildid(),roomCardBean.getBuildname());
            }

        }else{

        }

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            //点击二维码实时获取最新的二维码
            case R.id.binaryCode:

                //实时获取最新的选中快捷房卡
                if(!"".equals(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("RoomCardBean", ""))){

                    roomCardBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("RoomCardBean", ""), RoomCardBean.class);
                    getQrCode(roomCardBean.getBuildid(),roomCardBean.getBuildname());
                }else{
                //表示没有在房卡列表配置过快捷房卡 那就是取得默认的第一张房卡作为快捷房卡
                    if(roomCardBeanListTemp.size()>0){

                        getQrCode(roomCardBeanListTemp.get(0).getBuildid(),roomCardBeanListTemp.get(0).getBuildname());
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
        String sourceuserid = "";

        if (userInfoBean != null) {

            sourceuserid = userInfoBean.getUserid();
        }

        RequestParams params = new RequestParams();
        params.put("pageSize", pageSize);
        params.put("pageNumber", pageNumber);

        HttpUtil.get(Constants.HOST + Constants.CardList + "/" + sourceuserid, params, new AsyncHttpResponseHandler() {
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

                                    getQrCode(roomCardBeanListTemp.get(0).getBuildid(),roomCardBeanListTemp.get(0).getBuildname());

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
                                binaryCode.setImageBitmap(Utils.createQRImage(getActivity(), jsonObject.getString("data"), 500, 500));

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
