package com.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mylibrary.model.BuildBean;
import com.android.qrcode.R;
import com.android.utils.CommonInterface;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by liujunqin on 2016/5/12.
 */
public class CardBlockManageAdapter extends BaseAdapter {

    Context context;
    List<BuildBean> list;

    public CardBlockManageAdapter(Context context, List<BuildBean> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_card_temp_block, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.messageTitle.setText(list.get(i).getName());




        return convertView;
    }

    static class ViewHolder {


        @Bind(R.id.messageTitle)
        TextView messageTitle;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



}
