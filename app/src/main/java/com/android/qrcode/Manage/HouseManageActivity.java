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

import com.android.adapter.CardManageAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.mylibrary.model.CardInfoBean;
import com.android.qrcode.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/13.
 */
public class HouseManageActivity extends BaseAppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener{

    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.houseSwipeRefresh)
    SwipeRefreshLayout houseSwipeRefresh;

    @Bind(R.id.houseListView)
    ListView houseListView;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    CardManageAdapter cardManageAdapter;
    private List<CardInfoBean> carinfoBeansList = new ArrayList<>();
    private boolean loadingMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_list);

    }
    @Override
    public void initView() {

        toolBar.setTitle("");
        toolbar_title.setText(R.string.house_manage_str);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);

    }

    @Override
    public void initData() {

        int i = 0;
        do {

            carinfoBeansList.add(new CardInfoBean(i+"号楼房卡","http://avatar.csdn.net/1/1/E/1_fengyuzhengfan.jpg"
            ));
            i++;

        } while (i < 3);

        cardManageAdapter = new CardManageAdapter(this, carinfoBeansList);
        houseListView.setAdapter(cardManageAdapter);

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

       // startActivity(new Intent(this,CardQrCodeCertificatActivity.class));

    }
}
