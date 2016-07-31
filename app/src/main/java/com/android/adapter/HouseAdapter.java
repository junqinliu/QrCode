package com.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mylibrary.model.CardInfoBean;
import com.android.qrcode.R;
import com.android.qrcode.SubManage.Manage.SubOwnerManageListActivity;
import com.android.utils.OndeleteListener;
import com.android.utils.SwipeLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by liujunqin on 2016/5/12.
 */
public class HouseAdapter extends BaseAdapter {

    Context context;
    List<CardInfoBean> list;

    OndeleteListener listener;

    public HouseAdapter(Context context, List<CardInfoBean> list,OndeleteListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
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
            convertView = View.inflate(context, R.layout.item_card, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Glide.with(context).load(list.get(i).getMemberPhoto()).into(holder.messagePic);
        holder.messagePic.setImageResource(R.mipmap.owner_manage);
        holder.messageTitle.setText(list.get(i).getName());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDelete(list.get(i).getHouseid());
            }
        });

        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubOwnerManageListActivity.class);
                intent.putExtra("houseid", list.get(i).getHouseid());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.messagePic)
        CircleImageView messagePic;
        @Bind(R.id.delete_button)
        RelativeLayout deleteButton;
        @Bind(R.id.delete_layout)
        LinearLayout deleteLayout;
        @Bind(R.id.messageTitle)
        TextView messageTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
