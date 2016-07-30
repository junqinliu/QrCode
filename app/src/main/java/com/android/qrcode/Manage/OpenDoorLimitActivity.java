package com.android.qrcode.Manage;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.adapter.OpenDoorLimitAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.mylibrary.model.OpenDoorLimit;
import com.android.mylibrary.model.SortModel;
import com.android.qrcode.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/7/23.
 */
public class OpenDoorLimitActivity extends BaseAppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;
@Bind(R.id.open_door_listview)
    ListView open_door_listview;
    OpenDoorLimitAdapter openDoorLimitAdapter;
    List<OpenDoorLimit> openDoorLimitList = new ArrayList<>();

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
        add_img.setImageResource(R.mipmap.submit);
        add_img.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        openDoorLimitList.add(new OpenDoorLimit("1号门","00001"));
        openDoorLimitList.add(new OpenDoorLimit("2号门","00002"));
        openDoorLimitList.add(new OpenDoorLimit("3号门","00003"));
        openDoorLimitList.add(new OpenDoorLimit("4号门","00004"));
        openDoorLimitAdapter = new OpenDoorLimitAdapter(this,openDoorLimitList);
        open_door_listview.setAdapter(openDoorLimitAdapter);
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


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){


          /*  case R.id.owner_apply_manage_layout:


                break;*/

            default:
                break;

        }
    }

}
