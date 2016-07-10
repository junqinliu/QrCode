package com.android.application;


import android.app.Application;

import com.android.exception.CrashHandler;


import cn.smssdk.SMSSDK;

public class AppContext extends Application {
	
	private static AppContext appContext;
	
	/**
	 *
	 * 将在Application中注册未捕获异常处理器。
	 */
	@Override
	public void onCreate() {
		super.onCreate();
	
		CrashHandler crashHandler = CrashHandler.getInstance();
		// 注册crashHandler
		crashHandler.init(getApplicationContext());

		//xutils3初始化
		//x.Ext.init(this);
	//	x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能

		//SMMSDK初始化
		SMSSDK.initSDK(this, "146d57ebbef52", "8a6b993fb9b85a0998a51729374ea4c1");
		
	}
	
	
	public static AppContext getInstance() {
		return appContext;
	}

}
