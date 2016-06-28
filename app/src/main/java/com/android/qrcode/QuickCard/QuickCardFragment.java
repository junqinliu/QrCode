package com.android.qrcode.QuickCard;

import android.os.Bundle;

import com.android.base.BaseFragment;
import com.android.qrcode.R;
import com.android.utils.SquareImageView;
import com.android.utils.Utils;

import butterknife.Bind;

/**
 * Created by liujunqin on 2016/6/12.
 */
public class QuickCardFragment extends BaseFragment {

    @Bind(R.id.binaryCode)
    SquareImageView binaryCode;


    public QuickCardFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "快捷房卡");
        bundle.putInt("icon", R.drawable.selector_main_tab_search_icon);
        this.setArguments(bundle);
    }
    @Override
    public int setContentView() {
        return R.layout.find_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        binaryCode.setImageBitmap(Utils.createQRImage(getActivity(), "test", 500, 500));

    }

    @Override
    public void setListener() {

    }

}
