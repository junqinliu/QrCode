package com.android.qrcode.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.base.BaseAppCompatActivity;
import com.android.mylibrary.model.OwnerListBean;
import com.android.mylibrary.model.SortModel;
import com.android.qrcode.R;
import com.android.utils.TextUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/14.
 */
public class AddOwnerActivity extends BaseAppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.radio_man)
    RadioButton radio_man;
    @Bind(R.id.radio_woman)
    RadioButton radio_woman;

    @Bind(R.id.add_img)
    ImageView add_img;
    @Bind(R.id.name_txt)
    EditText name_txt;
    @Bind(R.id.phone_num_txt)
    EditText phone_num_txt;
    private String sex = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_owner);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.add_owner_str);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        add_img.setImageResource(R.mipmap.submit);
        add_img.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

        add_img.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.radio_man) {
                    sex = "0";
                    Toast.makeText(AddOwnerActivity.this, "男", Toast.LENGTH_LONG).show();
                } else {
                    sex = "1";
                    Toast.makeText(AddOwnerActivity.this, "女", Toast.LENGTH_LONG).show();
                }
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
    public void onClick(View view) {

        switch (view.getId()){
            //提交按钮
            case R.id.add_img:

                if(TextUtil.isEmpty(name_txt.getText().toString())||TextUtil.isEmpty(phone_num_txt.getText().toString())){

                    showToast("请完善信息");
                    return;
                }
                OwnerListBean sortModel = new OwnerListBean();
                sortModel.setName(name_txt.getText().toString());
                sortModel.setPhone(phone_num_txt.getText().toString());
                sortModel.setSex(1);

                Intent  intent = new Intent();
                intent.putExtra("OwnerListBean",sortModel);
                setResult(2, intent);
                finish();
                break;

            default:
                break;

        }
    }

}
