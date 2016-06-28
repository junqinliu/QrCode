package com.android.qrcode;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始启动页
 * 
 * @author Liujq
 * 
 */
public class HomeActivity extends Activity implements OnPageChangeListener {

	private static final int GO_HOME = 1000;
	// private static final int GO_GUIDE = 1001;
	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	/**
	 * 底部小点图片
	 */
	private ImageView[] dots;
	/**
	 * 记录当前选中位置
	 */
	private int currentIndex;
	/**
	 * 立即体验按钮
	 */
	private ImageView guideGoHome;

	/**
	 * 是否首次登陆
	 */
	private boolean isFirstIn = false;
	/**
	 * 延时时间
	 */
	private static final long SPLASH_DELAY_MILLIS = 1500;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 关闭通知栏的提示
		NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// 关闭通知栏
		if (mManager != null) {
			mManager.cancel(0);
		}
		// 使用SharedPreferences来记录程序的使用次数
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		// 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (!isFirstIn) {
			setContentView(R.layout.activity_splash);
			// 使用Handler的postDelayed方法，1.5秒后执行跳转到MainActivity
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
		} else {
			
			initGuideViews();
			initDots();
		}
	}

	/**
	 * 引导页初始化
	 */
	private void initGuideViews() {
		// 加载引导页布局
		setContentView(R.layout.activity_guide);
		guideGoHome = (ImageView) findViewById(R.id.guide_gohome);
		LayoutInflater inflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		// 初始化引导图片列表
		views.add(inflater.inflate(R.layout.viewpager_guide, null));
		views.add(inflater.inflate(R.layout.viewpager_guide, null));
		views.add(inflater.inflate(R.layout.viewpager_guide, null));
		// 初始化Adapter
		vpAdapter = new ViewPagerAdapter(views, this);
		vp = (ViewPager) findViewById(R.id.guide_viewpager);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);
		guideGoHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goHome();
				setGuided();
			}
		});
	}
	/**
	 * 初始化下面的小点
	 */
	private void initDots() {
		LinearLayout layDots = (LinearLayout) findViewById(R.id.guide_dots);
		dots = new ImageView[views.size()];
		// 循环取得小点图片
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) layDots.getChildAt(i);
			dots[i].setEnabled(false);// 都设为灰色
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(true);// 设置为白色，即选中状态
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}
		dots[position].setEnabled(true);
		dots[currentIndex].setEnabled(false);
		currentIndex = position;
	}

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	// 当当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int pos) {
		// 设置底部小点选中状态
		if (pos == views.size() - 1) {
			guideGoHome.setVisibility(View.VISIBLE);
		} else {
			guideGoHome.setVisibility(View.INVISIBLE);
		}
		setCurrentDot(pos);
	}

	/**
	 * Handler:跳转到不同界面
	 */
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * 调到主页
	 */
	private void goHome() {
		
		//Intent intent = new Intent(HomeActivity.this, MainActivity.class);
		Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
	/**
	 * method desc：设置已经引导过了，下次启动不用再次引导
	 */
	private void setGuided() {
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		// 存入数据
		editor.putBoolean("isFirstIn", false);
		// 提交修改
		editor.commit();
	}
	/**
	 * 宣导页 适配器
	 * @author LJB
	 */
	class ViewPagerAdapter extends PagerAdapter {

		// 界面列表
		private List<View> views;
		private Activity activity;
		public static final String SHAREDPREFERENCES_NAME = "first_pref";

		public ViewPagerAdapter(List<View> views, Activity activity) {
			this.views = views;
			this.activity = activity;
		}

		// 销毁arg1位置的界面
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		// 获得当前界面数
		@Override
		public int getCount() {
			if (views != null) {
				return views.size();
			}
			return 0;
		}

		// 初始化arg1位置的界面
		@Override
		public Object instantiateItem(View view, int pos) {
			((ViewPager) view).addView(views.get(pos), 0);
			ImageView ImgView=((ImageView)view.findViewById(R.id.viewpager_image));
			if (pos==0) {
				ImgView.setImageResource(R.mipmap.img_guide1);
			}else if (pos==1) {
				ImgView.setImageResource(R.mipmap.img_guide2);
			}else if (pos==2) {
				ImgView.setImageResource(R.mipmap.img_guide3);
			}
			return views.get(pos);
		}

	

		// 判断是否由对象生成界面
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return (arg0 == arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

	}
}