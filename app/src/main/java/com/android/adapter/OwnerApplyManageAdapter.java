package com.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.mylibrary.model.MessageInfoBean;
import com.android.mylibrary.model.OwnerApplyManageBean;
import com.android.qrcode.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by liujunqin on 2016/5/12.
 */
public class OwnerApplyManageAdapter extends BaseAdapter {

    Context context;
    List<OwnerApplyManageBean> list;

    public OwnerApplyManageAdapter(Context context, List<OwnerApplyManageBean> list) {
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
            convertView = View.inflate(context, R.layout.item_owner_apply_manage, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.apply_user_name.setText("用户"+list.get(i).getUserName()+"请求认证");
        holder.house_num.setText("所在楼栋："+list.get(i).getHouseNum());
        holder.submit_time.setText("时间："+list.get(i).getApplyTime());


        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.apply_user_name)
        TextView apply_user_name;
        @Bind(R.id.house_num)
        TextView house_num;
        @Bind(R.id.submit_time)
        TextView submit_time;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
