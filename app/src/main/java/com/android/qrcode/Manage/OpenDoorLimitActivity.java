package com.android.qrcode.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.adapter.OpenDoorLimitAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.OpenDoorLimit;
import com.android.mylibrary.model.RoomCardBean;
import com.android.mylibrary.model.SortModel;
import com.android.mylibrary.model.UserInfoBean;
import com.android.qrcode.MainActivity;
import com.android.qrcode.R;
import com.android.qrcode.SubMainActivity;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.android.utils.OnAddDeleteListener;
import com.android.utils.SharedPreferenceUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by liujunqin on 2016/7/23.
 */
public class OpenDoorLimitActivity extends BaseAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        AbsListView.OnScrollListener, View.OnClickListener, OnAddDeleteListener {


    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;

    @Bind(R.id.openSwipeRefresh)
    SwipeRefreshLayout openSwipeRefresh;

    @Bind(R.id.open_door_listview)
    ListView open_door_listview;

    @Bind(R.id.submit_btn)
    Button submit_btn;

    OpenDoorLimitAdapter openDoorLimitAdapter;
    List<OpenDoorLimit> openDoorLimitList = new ArrayList<>();
    List<OpenDoorLimit> openDoorLimitListTemp = new ArrayList<>();

    private boolean loadingMore = false;
    int pageNumber = 0;
    int pageSize = 10;

    String sourceuserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_door_limit);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.add_owner_open_str);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        // add_img.setImageResource(R.mipmap.submit);
        //add_img.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {

        sourceuserid = getIntent().getStringExtra("sourceuserid");
        openDoorLimitAdapter = new OpenDoorLimitAdapter(this, openDoorLimitList,this);
        open_door_listview.setAdapter(openDoorLimitAdapter);
        getOpenLimitList();
    }

    @Override
    public void setListener() {

        add_img.setOnClickListener(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        submit_btn.setOnClickListener(this);
        openSwipeRefresh.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        pageNumber = 0;
        openDoorLimitList.clear();
        getOpenLimitList();
        openSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 倒数第二个item为当前屏最后可见时，加载更多
        if ((firstVisibleItem + visibleItemCount + 1 >= totalItemCount) && loadingMore) {
            //TODO 加载数据
            getOpenLimitList();
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.submit_btn:

                finish();

                break;

            default:
                break;

        }
    }


    /**
     * 获取微卡列表
     */
    private void getOpenLimitList() {

        RequestParams params = new RequestParams();
        params.put("pageSize", pageSize);
        params.put("pageNumber", pageNumber);
        params.put("sourceuserid ", sourceuserid);

        HttpUtil.get(Constants.HOST + Constants.CardList + "/" + sourceuserid, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(OpenDoorLimitActivity.this)) {

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
                                openDoorLimitListTemp.clear();
                                JSONObject gg = new JSONObject(jsonObject.getString("data"));

                                openDoorLimitListTemp = JSON.parseArray(gg.getJSONArray("items").toString(), OpenDoorLimit.class);
                                if (openDoorLimitListTemp != null && openDoorLimitListTemp.size() > 0) {

                                    openDoorLimitList.addAll(openDoorLimitListTemp);
                                    openDoorLimitAdapter.initDate();
                                    openDoorLimitAdapter.notifyDataSetChanged();
                                    if (openDoorLimitListTemp.size() == 10) {
                                        loadingMore = true;
                                    } else {
                                        loadingMore = false;
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
     * 添加开门权限
     *
     * @param buildid
     */
    @Override
    public void onAdd(String buildid) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", sourceuserid);
            jsonObject.put("buildid", buildid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpUtil.post(OpenDoorLimitActivity.this, Constants.HOST + Constants.AddLimit, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(OpenDoorLimitActivity.this)) {

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


                            } else {


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
     * 删除开门权限
     *
     * @param buildid
     */
    @Override
    public void onDelete(String buildid) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", sourceuserid);
            jsonObject.put("buildid", buildid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        HttpUtil.delete(OpenDoorLimitActivity.this, Constants.HOST + Constants.DeleteLimit, entity, "application/json", new

                        AsyncHttpResponseHandler() {
                            @Override
                            public void onStart() {
                                super.onStart();
                                if (!NetUtil.checkNetInfo(OpenDoorLimitActivity.this)) {

                                    showToast("当前网络不可用,请检查网络");
                                    return;
                                }

                            }

                            @Override
                            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers,
                                                  byte[] responseBody) {

                                if (responseBody != null) {
                                    try {
                                        String str = new String(responseBody);
                                        JSONObject jsonObject = new JSONObject(str);
                                        if (jsonObject != null) {

                                            if (jsonObject.getBoolean("success")) {


                                            } else {


                                            }

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers,
                                                  byte[] responseBody, Throwable error) {

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
                        }

        );

    }


}
