package com.android.qrcode.SubManage.Manage;

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
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.CardInfoBean;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.android.utils.OndeleteListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/13.
 */
public class SubHouseManageListActivity extends BaseAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener,OndeleteListener {

    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.houseSwipeRefresh)
    SwipeRefreshLayout houseSwipeRefresh;

    @Bind(R.id.houseListView)
    ListView houseListView;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    HouseAdapter houseAdapter;

    private int pageSize = 20;

    private int pageNumber = 0;

    private List<CardInfoBean> carinfoBeansList = new ArrayList<CardInfoBean>();
    private List<CardInfoBean> carinfoBeansListTemp = new ArrayList<CardInfoBean>();
    private boolean loadingMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_list);

    }

    @Override
    public void initView() {

        toolBar.setTitle("");
        toolbar_title.setText(R.string.sub_community_str);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);

    }

    @Override
    public void initData() {

        houseAdapter = new HouseAdapter(this, carinfoBeansList,this);
        houseListView.setAdapter(houseAdapter);

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
        loadingMore = false;//刷新后，就可以加载更多了
        houseSwipeRefresh.setRefreshing(true);
        pageNumber = 0;
        requestData();

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 倒数第二个item为当前屏最后可见时，加载更多
        if ((firstVisibleItem + visibleItemCount + 1 >= totalItemCount) && !loadingMore) {
            loadingMore = true;
            requestData();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, SubOwnerManageListActivity.class);
        intent.putExtra("houseid",carinfoBeansList.get(i).getHouseid());
        startActivity(intent);
    }

    private void requestData() {
        HttpUtil.get(Constants.HOST + Constants.cell_list, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(SubHouseManageListActivity.this)) {

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
                                JSONObject response = jsonObject.getJSONObject("data");
                                if (response != null && response.getJSONArray("items") != null) {
                                    carinfoBeansListTemp = JSON.parseArray(response.getJSONArray("items").toString(), CardInfoBean.class);
                                    if (loadingMore) {
                                        if (pageNumber == 0) {
                                            carinfoBeansList.clear();
                                        }
                                        carinfoBeansList.addAll(carinfoBeansListTemp);
                                        houseAdapter.notifyDataSetChanged();
                                        if (carinfoBeansListTemp.size() == 20) {
                                            //后台还有数据，否则不改变loadingMore的状态，一直是加载中的状态，那么就不会加载更多了
                                            loadingMore = false;
                                            pageNumber++;
                                        }
                                    } else {
                                        carinfoBeansList.clear();
                                        carinfoBeansList.addAll(carinfoBeansListTemp);
                                        houseAdapter.notifyDataSetChanged();
                                        pageNumber++;
                                    }
                                }
                            } else {
                                showToast("请求接口失败，请联系管理员");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                houseSwipeRefresh.setRefreshing(false);
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
                houseSwipeRefresh.setRefreshing(false);
            }


        });
    }


    @Override
    public void onDelete(int houseid) {
        deleteMember(houseid);
    }
    /**
     * 调用删除成员接口
     */
    private void deleteMember(int houseid) {
        HttpUtil.delete(Constants.HOST + Constants.delete_cell + "/" + houseid, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(SubHouseManageListActivity.this)) {

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
                                requestData();
                                showToast("删除成功");
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
