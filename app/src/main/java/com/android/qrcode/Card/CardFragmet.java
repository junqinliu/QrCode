package com.android.qrcode.Card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.adapter.CardAdapter;
import com.android.adapter.MemberAdapter;
import com.android.base.BaseFragment;
import com.android.mylibrary.model.CardInfoBean;
import com.android.mylibrary.model.MemberBean;
import com.android.qrcode.Manage.HouseManageActivity;
import com.android.qrcode.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/5/31.
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

    private List<CardInfoBean> carinfoBeansList = new ArrayList<>();
    private boolean loadingMore = false;


    @Override
    public int setContentView() {
        return R.layout.card_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        int i = 0;
        do {

            carinfoBeansList.add(new CardInfoBean("1002","http://avatar.csdn.net/1/1/E/1_fengyuzhengfan.jpg"
            ));
            i++;

        } while (i < 3);

        cardAdapter = new CardAdapter(getActivity(), carinfoBeansList);
        cardListView.setAdapter(cardAdapter);
    }

    @Override
    public void setListener() {
        cardSwipeRefresh.setOnRefreshListener(this);
        cardListView.setOnScrollListener(this);
        cardListView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        startActivity(new Intent(getActivity(),CardQrCodeActivity.class));
    }

    @Override
    public void onRefresh() {
        //TODO request data from server
        cardSwipeRefresh.setRefreshing(false);
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

}
