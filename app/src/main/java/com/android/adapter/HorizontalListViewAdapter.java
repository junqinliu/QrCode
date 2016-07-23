package com.android.adapter;


import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.qrcode.R;

public class HorizontalListViewAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Bitmap> alb;
	public HorizontalListViewAdapter(Context context,ArrayList<Bitmap> alb) {
		this.context = context;
		this.alb = alb;
	}
	@Override
	public int getCount() {
		return alb.size();
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
		mViewHould.mImageView.setImageBitmap(alb.get(position));
		if(position == alb.size()-1){
			mViewHould.delete_pic.setVisibility(View.GONE);
		}else{
			mViewHould.delete_pic.setVisibility(View.VISIBLE);
		}
		
		
		return convertView;
	}
	
	private class ViewHould{
		
		private ImageView mImageView;
		private ImageView delete_pic;
		
	}
}