package com.mo.util;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;
/**
 * 自定义Application
 * 用于请求队列获取
 * @author Administrator
 *
 */
public class MyApplication extends Application {

	public static RequestQueue queue;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		queue=Volley.newRequestQueue(getApplicationContext());
				
	}
	public static RequestQueue getHttpQueues(){
		return queue;
	}
}
