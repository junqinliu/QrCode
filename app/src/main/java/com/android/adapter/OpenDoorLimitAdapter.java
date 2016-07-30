package com.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.mylibrary.model.LogBean;
import com.android.mylibrary.model.OpenDoorLimit;
import com.android.qrcode.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujunqin on 2016/5/12.
 */
public class OpenDoorLimitAdapter extends BaseAdapter {

    Context context;
    List<OpenDoorLimit> list;

    public OpenDoorLimitAdapter(Context context, List<OpenDoorLimit> list) {
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
            convertView = View.inflate(context, R.layout.item_open_door, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.open_door_desc.setText(list.get(i).getDoorDesc());
      //  holder.time.setText(list.get(i).getTime());
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.open_door_desc)
        TextView open_door_desc;
        @Bind(R.id.open_door_cb)
        CheckBox open_door_cb;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
