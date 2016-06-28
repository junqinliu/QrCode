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

import com.android.adapter.DeviceRepairAdapter;
import com.android.adapter.HouseAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.mylibrary.model.CardInfoBean;
import com.android.mylibrary.model.MessageInfoBean;
import com.android.qrcode.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/13.
 */
public class DeviceRepairActivity extends BaseAppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener{

    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.houseSwipeRefresh)
    SwipeRefreshLayout houseSwipeRefresh;

    @Bind(R.id.houseListView)
    ListView houseListView;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    DeviceRepairAdapter deviceRepairAdapter;
    private List<MessageInfoBean> messageInfoBeanList = new ArrayList<>();
    private boolean loadingMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_list);

    }
    @Override
    public void initView() {

        toolBar.setTitle("");
        toolbar_title.setText(R.string.device_repair);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);

    }

    @Override
    public void initData() {

        int i = 0;
        do {

            messageInfoBeanList.add(new MessageInfoBean("刘俊","15522503900","2016/06/15","小区1号楼门禁损坏，请找人来维修"
            ));
            i++;

        } while (i < 3);

        deviceRepairAdapter = new DeviceRepairAdapter(this, messageInfoBeanList);
        houseListView.setAdapter(deviceRepairAdapter);

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
        houseSwipeRefresh.setRefreshing(false);

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 倒数第二个item为当前屏最后可见时，加载更多
        if ((firstVisibleItem + visibleItemCount + 1 >= totalItemCount) && !loadingMore) {
            loadingMore = true;
            //TODO 加载数据
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(this,OwnerManageListActivity.class));
    }
}
