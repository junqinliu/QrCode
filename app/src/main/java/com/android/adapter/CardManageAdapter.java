package com.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.mylibrary.model.CardInfoBean;
import com.android.qrcode.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by liujunqin on 2016/5/12.
 */
public class CardManageAdapter extends BaseAdapter {

    Context context;
    List<CardInfoBean> list;

    public CardManageAdapter(Context context, List<CardInfoBean> list) {
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_card, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Glide.with(context).load(list.get(i).getMemberPhoto()).into(holder.messagePic);
        holder.messagePic.setVisibility(View.GONE);
        holder.messageTitle.setText(list.get(i).getCardNum());

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.messagePic)
        CircleImageView messagePic;
        @Bind(R.id.messageTitle)
        TextView messageTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
