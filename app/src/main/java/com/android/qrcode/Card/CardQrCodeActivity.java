package com.android.qrcode.Card;

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
import com.android.utils.SquareImageView;
import com.android.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/13.
 */
public class CardQrCodeActivity extends BaseAppCompatActivity implements View.OnClickListener
      {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;

    @Bind(R.id.toolbar)
    Toolbar toolBar;
      @Bind(R.id.binaryCode)
      SquareImageView binaryCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_qr_code);
    }

    @Override
    public void initView() {

        toolBar.setTitle("");
        toolbar_title.setText(R.string.house_card);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        add_img.setImageResource(R.mipmap.submit);
      //  add_img.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {
        binaryCode.setImageBitmap(Utils.createQRImage(CardQrCodeActivity.this, "test", 500, 500));
    }

    @Override
    public void setListener() {


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


}
