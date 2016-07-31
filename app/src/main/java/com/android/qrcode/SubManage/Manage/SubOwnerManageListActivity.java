package com.android.qrcode.SubManage.Manage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.adapter.SortAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.constant.Constants;
import com.android.mylibrary.model.OwnerListBean;
import com.android.mylibrary.model.SortModel;
import com.android.qrcode.Manage.OwnerDetailActivity;
import com.android.qrcode.R;
import com.android.utils.CharacterParser;
import com.android.utils.ClearEditText;
import com.android.utils.HttpUtil;
import com.android.utils.NetUtil;
import com.android.utils.OndeleteListener;
import com.android.utils.PinyinComparator;
import com.android.utils.SideBar;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/13.
 */
public class SubOwnerManageListActivity extends BaseAppCompatActivity implements View.OnClickListener ,OndeleteListener {


    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.sidrbar)
    SideBar sideBar;
    @Bind(R.id.dialog)
    TextView dialog;
    @Bind(R.id.filter_edit)
    ClearEditText mClearEditText;
    @Bind(R.id.sortlist)
    ListView sortListView;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;

    private SortAdapter adapter;
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;
    private List<OwnerListBean> OwnerListBean;

    private PinyinComparator pinyinComparator;

    private int houseid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contact);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.sub_tenement_manage_str);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        add_img.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {

        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(dialog);

        OwnerListBean = new ArrayList<>();

        SourceDateList = filledData(OwnerListBean);
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList,this);
        sortListView.setAdapter(adapter);

        houseid = getIntent().getIntExtra("houseid", 0);

        requestData();

    }

    @Override
    public void setListener() {

        add_img.setOnClickListener(this);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @SuppressLint("NewApi")
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
               /* Toast.makeText(getApplication(),
                        ((SortModel) adapter.getItem(position)).getName(),
                        Toast.LENGTH_SHORT).show();*/

                Intent intent = new Intent(SubOwnerManageListActivity.this, OwnerDetailActivity.class);
                intent.putExtra("Model", (SortModel) adapter.getItem(position));
                startActivity(intent);

            }
        });

        sortListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(SourceDateList.get(position));
                return false;
            }
        });

        mClearEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                mClearEditText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

            }
        });
        // 根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    @Override
    public void onDelete(int userid) {
        for (SortModel model : SourceDateList ) {
            if(model.getUserid() == userid){
                deleteMember(model);
                break;
            }
        }
    }

    // 退出提示框按钮监听
    class DialogListener implements android.content.DialogInterface.OnClickListener {
        private SortModel model;

        DialogListener(SortModel model) {
            this.model = model;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            deleteMember(model);
        }
    }

    /**
     * 调用删除成员接口
     */
    private void deleteMember(final SortModel model) {
        HttpUtil.delete(Constants.HOST + Constants.delete_member + "/" + model.getUserid(), new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(SubOwnerManageListActivity.this)) {

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
                                showToast("已删除"+ model.getName());
                                SourceDateList.remove(model);
                                adapter.notifyDataSetChanged();
//                                requestData();//重新调用接口
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

    /**
     * 删除成员弹出框
     *
     * @param model
     */
    private void showDeleteDialog(SortModel model) {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).setTitle("提示")
                .setMessage("确定删除" + model.getName())
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogListener(model)).create().show();
    }

    /**
     * 为ListView填充数据
     *
     * @param OwnerListBean
     * @return
     */
    private List<SortModel> filledData(List<OwnerListBean> OwnerListBean) {
        List<SortModel> mSortList = new ArrayList<SortModel>();
        SortModel sortModel;
        for (int i = 0; i < OwnerListBean.size(); i++) {
            sortModel = new SortModel();
            sortModel.setUserid(OwnerListBean.get(i).getUserid());
            sortModel.setName(OwnerListBean.get(i).getName());
            sortModel.setPhoneNum(OwnerListBean.get(i).getPhone());
            sortModel.setSex(OwnerListBean.get(i).getSex() + "");
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(OwnerListBean.get(i).getName());
            if("".equals(pinyin)){
                continue;
            }
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.add_img:

                Intent intent = new Intent(SubOwnerManageListActivity.this, SubAddOwnerActivity.class);
                intent.putExtra("intent",houseid);
                startActivityForResult(intent, 1);

                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == 2) {
                requestData();
                OwnerListBean ownerListBean = (OwnerListBean) data.getSerializableExtra("OwnerListBean");
                OwnerListBean.add(ownerListBean);
                SourceDateList = filledData(OwnerListBean);
                Collections.sort(SourceDateList, pinyinComparator);
                adapter.updateListView(SourceDateList);
            }
        }
    }


    private void requestData() {
        RequestParams params = new RequestParams();
        params.put("pageSize", 20);
        params.put("pageNumber", 0);
        HttpUtil.get(Constants.HOST + Constants.managers + "/" + houseid, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if (!NetUtil.checkNetInfo(SubOwnerManageListActivity.this)) {

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
                                    OwnerListBean = JSON.parseArray(response.getJSONArray("items").toString(), OwnerListBean.class);
                                    SourceDateList.clear();
                                    SourceDateList.addAll(filledData(OwnerListBean));
                                    adapter.notifyDataSetChanged();
                                }
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
