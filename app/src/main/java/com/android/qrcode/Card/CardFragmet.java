package com.android.qrcode.Card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.adapter.CardAdapter;
import com.android.adapter.MemberAdapter;
import com.android.base.BaseFragment;
import com.android.constant.Constants;
import com.android.mylibrary.model.BuildBean;
import com.android.mylibrary.model.CardInfoBean;
import com.android.mylibrary.model.MemberBean;
import com.android.mylibrary.model.RoomCardBean;
import com.android.mylibrary.model.UserInfoBean;
import com.android.qrcode.Manage.HouseManageActivity;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.android.utils.SharedPreferenceUtil;
import com.android.utils.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

/**
 * Created by liujunqin on 2016/5/31. 房卡界面
 */
public class CardFragmet extends BaseFragment implements  SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener{

    public CardFragmet() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "房卡");
        bundle.putInt("icon", R.drawable.selector_main_tab_message_icon);
        this.setArguments(bundle);
    }

    @Bind(R.id.cardSwipeRefresh)
    SwipeRefreshLayout cardSwipeRefresh;

    @Bind(R.id.cardListView)
    ListView cardListView;
    CardAdapter cardAdapter;

    private List<RoomCardBean> roomCardBeanList = new ArrayList<>();
    private List<RoomCardBean> roomCardBeanListTemp = new ArrayList<>();
    private boolean loadingMore = false;
    int pageNumber = 0;
    int pageSize = 10;
    Map<Integer, Boolean> isSelectedMap = new HashMap<Integer, Boolean>();
    RoomCardBean roomCardBean;

    @Override
    public int setContentView() {
        return R.layout.card_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        cardAdapter = new CardAdapter(getActivity(), roomCardBeanList);
        cardListView.setAdapter(cardAdapter);

        if(!"".equals(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("RoomCardBean", ""))){

            roomCardBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("RoomCardBean", ""), RoomCardBean.class);
        }

    }

    @Override
    public void setListener() {
        cardSwipeRefresh.setOnRefreshListener(this);
        cardListView.setOnScrollListener(this);
        cardListView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        getQrCode(roomCardBeanList.get(i).getBuildid(),roomCardBeanList.get(i).getName());

    }

    @Override
    public void onRefresh() {
        //TODO request data from server
        pageNumber = 0;
        roomCardBeanList.clear();
        //刷新的时候 重新获取选中的快房卡 不然会一直选中刚进来的那张房卡
        if(!"".equals(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("RoomCardBean", ""))){

            roomCardBean = JSON.parseObject(SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getString("RoomCardBean", ""), RoomCardBean.class);
        }
        getCardList();
        cardSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
      public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 倒数第二个item为当前屏最后可见时，加载更多
        if ((firstVisibleItem + visibleItemCount + 1 >= totalItemCount) && loadingMore) {
            //  loadingMore = true;
            //TODO 加载数据 （重新获取选中的快房卡 不然会一直选中刚进来的那张房卡 ）（还没做）
            getCardList();
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

                                pageNumber = pageNumber + 1;
                                roomCardBeanListTemp.clear();
                                JSONObject gg = new JSONObject(jsonObject.getString("data"));

                                roomCardBeanListTemp = JSON.parseArray(gg.getJSONArray("items").toString(), RoomCardBean.class);
                                if (roomCardBeanListTemp != null && roomCardBeanListTemp.size() > 0) {

                                    roomCardBeanList.addAll(roomCardBeanListTemp);
                                    cardAdapter.initDate();
                                    if(roomCardBean != null){

                                        for(int i = 0;i<roomCardBeanList.size();i++){

                                            if(roomCardBean.getBuildid().equals(roomCardBeanList.get(i).getBuildid())){

                                                //设置上次选中的房卡作为默认快捷房卡
                                                isSelectedMap = cardAdapter.getIsSelected();
                                                isSelectedMap.put(i,true);
                                            }
                                        }
                                    }else{
                                        //初始化设置快捷房卡（以前没有设置过房卡）
                                        isSelectedMap = cardAdapter.getIsSelected();
                                        isSelectedMap.put(0,true);
                                        String  roomCardBeanstr = JSON.toJSONString(roomCardBeanList.get(0));
                                        SharedPreferenceUtil.getInstance(getActivity()).putData("RoomCardBean", roomCardBeanstr);
                                    }
                                    cardAdapter.notifyDataSetChanged();

                                    if (roomCardBeanListTemp.size() == 10) {
                                        loadingMore = true;
                                    } else {
                                        loadingMore = false;
                                    }

                                } else {
                                   if(roomCardBeanList.size() ==0){

                                        showToast("目前还没有房卡");

                                   }
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

                                Intent intent = new Intent(getActivity(), CardQrCodeActivity.class);
                                intent.putExtra("secret", jsonObject.getString("data"));
                                intent.putExtra("buildname", buildname);
                                intent.putExtra("buildid", buildid);
                                startActivity(intent);

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
            }
            pageNumber = 0;
            roomCardBeanList.clear();
            getCardList();
        }else{



        }

    }
}
