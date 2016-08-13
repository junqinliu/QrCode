package com.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSON;
import com.android.mylibrary.model.CardInfoBean;
import com.android.mylibrary.model.MemberBean;
import com.android.mylibrary.model.RoomCardBean;
import com.android.qrcode.R;
import com.android.utils.SharedPreferenceUtil;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by liujunqin on 2016/5/12.
 */
public class CardAdapter extends BaseAdapter {

    Context context;
    List<RoomCardBean> list;
    private static HashMap<Integer, Boolean> isSelectedMap = new HashMap<Integer, Boolean>();

    public CardAdapter(Context context, List<RoomCardBean> list) {
        super();
        this.context = context;
        this.list = list;

        for (int i = 0; i < list.size(); i++) {
            getIsSelected().put(i, false);
        }
    }
    // 初始化isSelected的数据
    public void initDate() {
        for (int i = 0; i < list.size(); i++) {
            getIsSelected().put(i, false);
        }
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
            convertView = View.inflate(context, R.layout.item_card_fragment, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Glide.with(context).load(list.get(i).getMemberPhoto()).into(holder.messagePic);
        holder.messageTitle.setText(list.get(i).getName());

        final int tempPosition = i;

        holder.toggle_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                    getIsSelected().put(tempPosition, true);

                    for (int j = 0; j < list.size(); j++) {

                        if (j == tempPosition) {

                            Toast.makeText(context,"您设置快捷房卡"+list.get(j).getName(),Toast.LENGTH_SHORT).show();
                            String  roomCardBeanstr = JSON.toJSONString(list.get(j));
                            SharedPreferenceUtil.getInstance(context).putData("RoomCardBean", roomCardBeanstr);

                        } else {

                            getIsSelected().put(j, false);
                        }
                    }

                    notifyDataSetChanged();

                } else {
                    getIsSelected().put(tempPosition, true);
                    notifyDataSetChanged();
                }

            }
        });

       holder.toggle_btn.setChecked(getIsSelected().get(i));


        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.messagePic)
        CircleImageView messagePic;
        @Bind(R.id.messageTitle)
        TextView messageTitle;
        @Bind(R.id.toggle_btn)
        CheckBox toggle_btn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelectedMap;
    }

    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        isSelectedMap = isSelected;
    }
}
