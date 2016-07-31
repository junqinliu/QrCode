package com.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
public class BlockManageAdapter extends BaseAdapter {

    Context context;
    List<BuildBean> list;
    CommonInterface lister;


    public BlockManageAdapter(Context context, List<BuildBean> list,CommonInterface lister) {
        this.context = context;
        this.list = list;
        this.lister = lister;

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
            convertView = View.inflate(context, R.layout.item_card_temp, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Glide.with(context).load(list.get(i).getMemberPhoto()).into(holder.messagePic);
        holder.messagePic.setVisibility(View.GONE);
        holder.messageTitle.setText(list.get(i).getName());


            holder.delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lister.delete(list.get(i).getBuildid());

                }
            });

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.messagePic)
        CircleImageView messagePic;
        @Bind(R.id.messageTitle)
        TextView messageTitle;
        @Bind(R.id.delete_button)
        RelativeLayout delete_button;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



}
