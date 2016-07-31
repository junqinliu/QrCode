package com.android.qrcode.Manage;

import android.annotation.SuppressLint;
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
import android.widget.Toast;
import com.android.adapter.SortAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.mylibrary.model.OwnerListBean;
import com.android.mylibrary.model.SortModel;
import com.android.qrcode.R;
import com.android.utils.CharacterParser;
import com.android.utils.ClearEditText;
import com.android.utils.PinyinComparator;
import com.android.utils.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/13.
 */
public class OwnerManageListActivity extends BaseAppCompatActivity implements View.OnClickListener{


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contact);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.owner_manage_str);
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
        OwnerListBean.add(new OwnerListBean("李海","15522503900",0));
        OwnerListBean.add(new OwnerListBean("啊龙","15522503900",1));
        OwnerListBean.add(new OwnerListBean("何绍","15522503900",0));
        OwnerListBean.add(new OwnerListBean("志伟","15522503900",0));
        OwnerListBean.add(new OwnerListBean("晓桃","15522503900",0));

        SourceDateList = filledData(OwnerListBean);
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);

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

                Intent intent = new Intent(OwnerManageListActivity.this,OwnerDetailActivity.class);
                intent.putExtra("Model",(SortModel) adapter.getItem(position));
                startActivity(intent);

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


    /**
     * 为ListView填充数据
     *
     * @param OwnerListBean
     * @return
     */
    private List<SortModel> filledData( List<OwnerListBean> OwnerListBean) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < OwnerListBean.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(OwnerListBean.get(i).getName());
            sortModel.setPhoneNum(OwnerListBean.get(i).getPhone());
            sortModel.setSex(OwnerListBean.get(i).getSex()+ "");
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(OwnerListBean.get(i).getName());
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

        switch (view.getId()){

            case R.id.add_img:

                Intent intent = new Intent(OwnerManageListActivity.this,AddOwnerActivity.class);
                startActivityForResult(intent,1);

                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){

            if (resultCode == 2){

                OwnerListBean ownerListBean = (OwnerListBean) data.getSerializableExtra("OwnerListBean");
                OwnerListBean.add(ownerListBean);
                SourceDateList =  filledData(OwnerListBean);
                Collections.sort(SourceDateList, pinyinComparator);
                adapter.updateListView(SourceDateList);
            }
        }
    }
}
