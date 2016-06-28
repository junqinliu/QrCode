package com.android.qrcode.Account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.adapter.LogAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.mylibrary.model.LogBean;
import com.android.qrcode.R;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/13.
 */
public class LogActivity extends BaseAppCompatActivity implements View.OnClickListener ,
        SwipeRefreshLayout.OnRefreshListener,AbsListView.OnScrollListener{

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;

    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    /*是不是在加载更多的状态中*/
    private boolean loadingMore = false;

    List<LogBean> list;

    LogAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
    }

    @Override
    public void initView() {

        toolBar.setTitle("");
        toolbar_title.setText(R.string.log_title);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        add_img.setImageResource(R.mipmap.submit);
      //  add_img.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {

        list = new ArrayList<>();
        adapter = new LogAdapter(this,getData());
        listView.setAdapter(adapter);
    }

    @Override
    public void setListener() {

        swipeRefresh.setOnRefreshListener(this);
        listView.setOnScrollListener(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onRefresh() {
       // adapter.reAddList(getData());
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
// 倒数第二个item为当前屏最后可见时，加载更多
        if ((firstVisibleItem + visibleItemCount + 1 >= totalItemCount) && !loadingMore) {
            loadingMore = true;
            //TODO 加载数据
          //  adapter.addAll(getData());
            loadingMore = false;
        }
    }

    private List<LogBean> getData() {
        List<LogBean> list = new ArrayList<>();
        list.add(new LogBean("小名1","2016-4-3 19:20:32"));
        list.add(new LogBean("小名2","2016-4-3 19:20:32"));
        list.add(new LogBean("小名3","2016-4-3 19:20:32"));
        list.add(new LogBean("小名4","2016-4-3 19:20:32"));
        list.add(new LogBean("小名5","2016-4-3 19:20:32"));
        list.add(new LogBean("小名6","2016-4-3 19:20:32"));

        return list;
    }

}
