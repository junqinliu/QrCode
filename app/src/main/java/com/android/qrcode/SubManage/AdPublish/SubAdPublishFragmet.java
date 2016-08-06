package com.android.qrcode.SubManage.AdPublish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.android.base.BaseFragment;
import com.android.constant.Constants;
import com.android.mylibrary.model.AdBean;
import com.android.mylibrary.model.BannerItemBean;
import com.android.qrcode.R;
import com.android.qrcode.Setting.UserNameResetActivity;
import com.android.qrcode.SubManage.Account.SubSettingActivity;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.android.utils.SimpleImageBanner;
import com.flyco.banner.anim.select.RotateEnter;
import com.flyco.banner.anim.unselect.NoAnimExist;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/06/21.
 */
public class SubAdPublishFragmet extends BaseFragment implements View.OnClickListener {

    SimpleImageBanner advert;

    public SubAdPublishFragmet() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "");
        bundle.putInt("icon", 0);
        this.setArguments(bundle);
    }
    List<AdBean> adBeanList = new ArrayList<>();
   /* public static String[] titles = new String[]{
            "伪装者:胡歌演绎'痞子特工'",
            "无心法师:生死离别!月牙遭虐杀",
            "花千骨:尊上沦为花千骨",
            "综艺饭:胖轩偷看夏天洗澡掀波澜",
            "碟中谍4:阿汤哥高塔命悬一线,超越不可能",
    };
    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160323071011277.jpg",//伪装者:胡歌演绎"痞子特工"
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144158380433341332.jpg",//无心法师:生死离别!月牙遭虐杀
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160286644953923.jpg",//花千骨:尊上沦为花千骨
            "http://photocdn.sohu.com/tvmobilemvms/20150902/144115156939164801.jpg",//综艺饭:胖轩偷看夏天洗澡掀波澜
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144159406950245847.jpg",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
    };*/

    @Bind(R.id.ad_edit_img)
    ImageView ad_edit_img;

    @Override
    public int setContentView() {
        return R.layout.fragment_ad;
    }

    @Override
    public void initView() {

        advert = (SimpleImageBanner) getView().findViewById(R.id.advert);

    }

    @Override
    public void initData() {

      /*  advert.setSelectAnimClass(RotateEnter.class)
                .setUnselectAnimClass(NoAnimExist.class)
                .setSource(adBeanList)
                .startScroll();*/
        //获取图片路径
        getAdList();
    }

    @Override
    public void setListener() {
        ad_edit_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            //广告编辑
            case R.id.ad_edit_img:

                startActivity( new Intent(getActivity(),AdEditActivity.class));
                break;

            default:
                break;
        }
    }


   /* public static ArrayList<BannerItemBean> getList() {
        ArrayList<BannerItemBean> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            BannerItemBean item = new BannerItemBean();
            item.imgUrl = urls[i];
            item.title = titles[i];
            list.add(item);
        }

        return list;
    }
*/


    /**
     * 获取广告列表的方法
     */

    private void getAdList() {

        RequestParams params = new RequestParams();
        HttpUtil.get(Constants.HOST + Constants.AdList, params, new AsyncHttpResponseHandler() {
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
                                JSONObject gg = new JSONObject(jsonObject.getString("data"));
                                adBeanList = JSON.parseArray(gg.getJSONArray("items").toString(), AdBean.class);
                                if (adBeanList != null && adBeanList.size() > 0) {

                                    advert.setSelectAnimClass(RotateEnter.class)
                                            .setUnselectAnimClass(NoAnimExist.class)
                                            .setSource(adBeanList)
                                            .startScroll();
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
