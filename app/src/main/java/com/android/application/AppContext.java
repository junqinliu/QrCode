package com.android.application;


import android.app.Application;
import android.graphics.Bitmap;

import com.android.exception.CrashHandler;
import com.android.mylibrary.model.AddressBean;


import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.SMSSDK;

public class AppContext extends Application {

    private static AppContext appContext;

    AddressBean addressBean;

    List<Bitmap> alb = new ArrayList<>();

    /**
     * 将在Application中注册未捕获异常处理器。
     */
    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());

        //SMMSDK初始化
        SMSSDK.initSDK(this, "164e213c26efa", "9b52eb8ddf2420df1c2176b79db60ec1");
        //社会化分享初始化
        ShareSDK.initSDK(this);

    }


    public static AppContext getInstance() {
        return appContext;
    }

    public AddressBean getAddressBean() {
        return addressBean;
    }

    public void setAddressBean(AddressBean addressBean) {
        this.addressBean = addressBean;
    }

    public List<Bitmap> getAlb() {
        return alb;
    }

    public void setAlb(List<Bitmap> alb) {
        this.alb = alb;
    }
}
