package com.android.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.mylibrary.model.AdBean;
import com.android.qrcode.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AdEditPublicAdapter extends BaseAdapter{
	private Context context;
	private List<AdBean> list = new ArrayList<>();
	public AdEditPublicAdapter(Context context, List<AdBean> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list==null?0:list.size();
	}
	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHould mViewHould = null;
		if (convertView==null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.edittsukkomi_grid_item, null);
			mViewHould=new ViewHould();
			mViewHould.mImageView = (ImageView) convertView.findViewById(R.id.edittsukkomi_grid_item_image);
			mViewHould.delete_pic = (ImageView) convertView.findViewById(R.id.delete_pic);
			convertView.setTag(mViewHould);
		}else {
			mViewHould=(ViewHould) convertView.getTag();
		}
		//mViewHould.mImageView.setImageBitmap(alb.get(position));
		Glide.with(context).load(list.get(position).getPicurl()).into(mViewHould.mImageView);

		/*if(position == list.size()-1){
			mViewHould.delete_pic.setVisibility(View.GONE);
		}else{*/
			mViewHould.delete_pic.setVisibility(View.VISIBLE);
	/*	}*/

		
		return convertView;
	}
	
	private class ViewHould{
		
		private ImageView mImageView;
		private ImageView delete_pic;
		
	}
}