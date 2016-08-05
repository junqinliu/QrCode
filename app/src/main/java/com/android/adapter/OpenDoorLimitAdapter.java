package com.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.android.mylibrary.model.OpenDoorLimit;
import com.android.qrcode.R;
import com.android.utils.OnAddDeleteListener;

import java.util.HashMap;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujunqin on 2016/5/12.
 */
public class OpenDoorLimitAdapter extends BaseAdapter {

    Context context;
    List<OpenDoorLimit> list;
    private static HashMap<Integer, Boolean> isSelectedMap = new HashMap<Integer, Boolean>();
    OnAddDeleteListener onAddDeleteListener;

    public OpenDoorLimitAdapter(Context context, List<OpenDoorLimit> list,OnAddDeleteListener onAddDeleteListener) {
        this.context = context;
        this.list = list;
        this.onAddDeleteListener = onAddDeleteListener;
        for (int i = 0; i < list.size(); i++) {
            getIsSelected().put(i, false);
        }
    }
    // 初始化isSelected的数据
    public void initDate() {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).isOpen()){

                getIsSelected().put(i, true);

            }else{

                getIsSelected().put(i, false);
            }
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
            convertView = View.inflate(context, R.layout.item_open_door, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.open_door_desc.setText(list.get(i).getBuildname());

        final int tempPosition = i;

        holder.toggle_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                    getIsSelected().put(tempPosition, true);
                    onAddDeleteListener.onAdd(list.get(tempPosition).getBuildid());
                   // notifyDataSetChanged();

                } else {
                    getIsSelected().put(tempPosition, false);
                    onAddDeleteListener.onDelete(list.get(tempPosition).getBuildid());
                    //notifyDataSetChanged();
                }

            }
        });


        holder.toggle_btn.setChecked(getIsSelected().get(i));
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.open_door_desc)
        TextView open_door_desc;
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
