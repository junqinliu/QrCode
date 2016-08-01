package com.android.qrcode.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.adapter.HouseAdapter;
import com.android.adapter.MainHouseAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.BuildBean;
import com.android.mylibrary.model.CardInfoBean;
import com.android.mylibrary.model.UserInfoBean;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.android.utils.SharedPreferenceUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cz.msebera.android.httpclient.Header;

/**
 * Created by liujunqin on 2016/6/13.
 */
public class HouseManageListActivity extends BaseAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.houseSwipeRefresh)
    SwipeRefreshLayout houseSwipeRefresh;

    @Bind(R.id.houseListView)
    ListView houseListView;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    MainHouseAdapter houseAdapter;
    private List<BuildBean> buildBeanList = new ArrayList<>();
    private List<BuildBean> buildBeanListTemp = new ArrayList<>();
    private boolean loadingMore = false;
    int pageNumber = 0;
    int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_list);

    }

    @Override
    public void initView() {

        toolBar.setTitle("");
        toolbar_title.setText(R.string.owner_manage_str);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);

    }

    @Override
    public void initData() {


        houseAdapter = new MainHouseAdapter(this, buildBeanList);
        houseListView.setAdapter(houseAdapter);
        getBuildList();

    }

    @Override
    public void setListener() {

        houseSwipeRefresh.setOnRefreshListener(this);
        houseListView.setOnScrollListener(this);
        houseListView.setOnItemClickListener(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

    }

    @Override
    public void onRefresh() {

        //TODO request data from server
        pageNumber = 0;
        buildBeanList.clear();
        getBuildList();
        houseSwipeRefresh.setRefreshing(false);

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 倒数第二个item为当前屏最后可见时，加载更多
        if ((firstVisibleItem + visibleItemCount + 1 >= totalItemCount) && loadingMore) {
          //  loadingMore = true;
            //TODO 加载数据
            getBuildList();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(HouseManageListActivity.this,OwnerManageListActivity.class);
        intent.putExtra("buildid",buildBeanList.get(i).getBuildid());
        startActivity(intent);
    }

    /**
     * 获取楼栋列表
     */
    private void getBuildList() {

        UserInfoBean userInfoBean = JSON.parseObject(SharedPreferenceUtil.getInstance(this).getSharedPreferences().getString("UserInfo", ""), UserInfoBean.class);
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
                if (!NetUtil.checkNetInfo(HouseManageListActivity.this)) {

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
                                buildBeanListTemp.clear();
                                JSONObject gg = new JSONObject(jsonObject.getString("data"));

                                buildBeanListTemp = JSON.parseArray(gg.getJSONArray("items").toString(), BuildBean.class);
                                if(buildBeanListTemp != null && buildBeanListTemp.size() > 0) {

                                    buildBeanList.addAll(buildBeanListTemp);
                                    houseAdapter.notifyDataSetChanged();
                                    if(buildBeanListTemp.size() == 10){
                                        loadingMore = true;
                                    }else{
                                        loadingMore = false;
                                    }

                                }else{

                                    showToast("该小区目前还没有维护楼栋");
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

}
