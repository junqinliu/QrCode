package com.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.mylibrary.model.CardInfoBean;
import com.android.mylibrary.model.MessageInfoBean;
import com.android.qrcode.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by liujunqin on 2016/5/12.
 */
public class DeviceRepairAdapter extends BaseAdapter {

    Context context;
    List<MessageInfoBean> list;

    public DeviceRepairAdapter(Context context, List<MessageInfoBean> list) {
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
            convertView = View.inflate(context, R.layout.item_device_repair, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.messge_detail.setText(list.get(i).getMessage());
        holder.name.setText(list.get(i).getName());
        holder.submit_time.setText("时间："+list.get(i).getTime());
        holder.link_num.setText("业主电话："+list.get(i).getPhoneNum());

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.messge_detail)
        TextView messge_detail;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.submit_time)
        TextView submit_time;
        @Bind(R.id.link_num)
        TextView link_num;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
