package com.mo.view;

import com.mo.yoo.MainActivity;
import com.mo.woBlogs.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends Activity{
	AnimationDrawable ad;
	MarqueTextView tv_version;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		final Window window = getWindow();// 获取当前的窗体对象
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 隐藏了状态栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏了标题栏
		setContentView(R.layout.welcome_view);
		tv_version=(MarqueTextView) findViewById(R.id.tv_version);
		ImageView imageView = (ImageView) findViewById(R.id.iv_toMain);// 查找到对应的ImageView
		imageView.setImageResource(R.drawable.yoo_head);// 注意drawable中的图片的name必须是小写字母
		Animation a=AnimationUtils.loadAnimation(this,R.anim.ha);
		imageView.startAnimation(a);
		a.setFillAfter(true);
		welcomeUI();
	}

	private void welcomeUI()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					Thread.sleep(3000);
					Message message = new Message();
					welHandler.sendMessage(message);// 具体消息中包含什么东西并不重要，因为接收的函数不需要该参数
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	Handler welHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			welcomeFunction();
		}

	};

	public void welcomeFunction()
	{
		Intent intent = new Intent();
		intent.setClass(WelcomeActivity.this, MainActivity.class);
		startActivity(intent);
		WelcomeActivity.this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
