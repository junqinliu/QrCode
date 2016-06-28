package com.android.qrcode.Manage;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.base.BaseAppCompatActivity;
import com.android.qrcode.R;
import com.android.utils.SquareImageView;
import com.android.utils.Utils;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/14.
 */
public class CardQrCodeCertificatActivity extends BaseAppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.add_img)
    ImageView add_img;
    @Bind(R.id.binaryCode)
    SquareImageView binaryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_card);

    }

    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.card_certification);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        add_img.setImageResource(R.mipmap.submit);
        add_img.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {

        binaryCode.setImageBitmap(Utils.createQRImage(this, "test", 500, 500));

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

            case 1:


                break;

            default:
                break;

        }
    }

}
