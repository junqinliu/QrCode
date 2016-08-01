package com.android.qrcode.Manage;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.android.adapter.DeviceRepairAdapter;
import com.android.adapter.OwnerApplyManageAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.BuildBean;
import com.android.mylibrary.model.MessageInfoBean;
import com.android.mylibrary.model.OwnerApplyManageBean;
import com.android.qrcode.R;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
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
 * Created by liujunqin on 2016/6/13.
 */
public class OwnerApplyManageActivity extends BaseAppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener{

    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.houseSwipeRefresh)
    SwipeRefreshLayout houseSwipeRefresh;

    @Bind(R.id.houseListView)
    ListView houseListView;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    OwnerApplyManageAdapter ownerApplyManageAdapter;
    private List<OwnerApplyManageBean> ownerApplyManageBeanList = new ArrayList<>();
    private List<OwnerApplyManageBean> ownerApplyManageBeanListTemp = new ArrayList<>();
    private boolean loadingMore = false;
    int pageNumber = 0;
    int pageSize = 10;
    String audituserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_list);

    }
    @Override
    public void initView() {

        toolBar.setTitle("");
        toolbar_title.setText(R.string.apply_manage);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);

    }

    @Override
    public void initData() {


        ownerApplyManageAdapter = new OwnerApplyManageAdapter(this, ownerApplyManageBeanList);
        houseListView.setAdapter(ownerApplyManageAdapter);
        getList();
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
        ownerApplyManageBeanList.clear();
        getList();
        houseSwipeRefresh.setRefreshing(false);

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 倒数第二个item为当前屏最后可见时，加载更多
        if ((firstVisibleItem + visibleItemCount + 1 >= totalItemCount) && loadingMore) {
           // loadingMore = true;
            //TODO 加载数据
            getList();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        audituserid = ownerApplyManageBeanList.get(i).getUserid();
        new AlertDialog.Builder(OwnerApplyManageActivity.this, AlertDialog.THEME_HOLO_LIGHT).setTitle("提示")
                .setMessage("是否认证？")
                .setNegativeButton("拒绝", refuseListener)
                .setPositiveButton("确定", agreeListener).create().show();

    }



    /**
     * 同意回调接口
     */
    android.content.DialogInterface.OnClickListener agreeListener = new android.content.DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

            rootAction("PASS");

        }
    };

    /**
     * 拒绝回调接口
     */
    android.content.DialogInterface.OnClickListener refuseListener = new android.content.DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

            rootAction("REFUSE");

        }
    };

    /**
     * 获取待审核列表
     */
    private void getList(){

        RequestParams params = new RequestParams();
        params.put("pageSize", pageSize);
        params.put("pageNumber", pageNumber);

        HttpUtil.get(Constants.HOST + Constants.isROOT, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(OwnerApplyManageActivity.this)) {

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
                                ownerApplyManageBeanListTemp.clear();
                                JSONObject gg = new JSONObject(jsonObject.getString("data"));

                                ownerApplyManageBeanListTemp = JSON.parseArray(gg.getJSONArray("items").toString(), OwnerApplyManageBean.class);
                                if (ownerApplyManageBeanListTemp != null && ownerApplyManageBeanListTemp.size() > 0) {

                                    ownerApplyManageBeanList.addAll(ownerApplyManageBeanListTemp);
                                    ownerApplyManageAdapter.notifyDataSetChanged();
                                    if (ownerApplyManageBeanList.size() == 10) {
                                        loadingMore = true;
                                    } else {
                                        loadingMore = false;
                                    }

                                } else {

                                    showToast("目前还没有审核列表");
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
     * 通过审核
     */

   private void rootAction(String auditstatus){

       JSONObject jsonObject = new JSONObject();
       try {
           jsonObject.put("audituserid",audituserid);
           jsonObject.put("auditstatus",auditstatus);
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

       HttpUtil.post(OwnerApplyManageActivity.this, Constants.HOST + Constants.ROOT, entity, "application/json", new AsyncHttpResponseHandler() {
           @Override
           public void onStart() {
               super.onStart();
               if (!NetUtil.checkNetInfo(OwnerApplyManageActivity.this)) {

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

                               showToast("处理成功");
                               finish();
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
