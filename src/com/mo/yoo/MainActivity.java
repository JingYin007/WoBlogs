package com.mo.yoo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.mo.woBlogs.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener,
		OnPageChangeListener
{
	private ViewPager mViewPager;
	private ArrayList<Fragment> mTabs;
	private FragmentPagerAdapter mAdapter;
	Intent intent_key;
	boolean isExit;
	private List<ChangeColor> mTabIndicators = new ArrayList<ChangeColor>();
	AlertDialog.Builder builder;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	//	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setOverflowButtonAlways();
		//去掉标题的ActionBar 
		//getActionBar().setDisplayShowHomeEnabled(false);
		initView();
		
		initDatas();
		mViewPager.setAdapter(mAdapter);
		initEvent();

	}
	/**
	 * 初始化所有事
	 */
	private void initEvent()
	{
		mViewPager.setOnPageChangeListener(this);
	}

	private void initDatas()
	{
		mTabs = new ArrayList<Fragment>();
		//
		TopNewsFrag tab1 = new TopNewsFrag();
		TopBlogsFrag tab2 = new TopBlogsFrag();
		TopSetFrag tab3 = new TopSetFrag();
		
		mTabs.add(tab1);
		mTabs.add(tab2);
		mTabs.add(tab3);
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int position)
			{
				return mTabs.get(position);
			}
		};
	}

	private void initView()
	{
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		ChangeColor one = (ChangeColor) findViewById(R.id.id_indicator_one);
		mTabIndicators.add(one);
		ChangeColor two = (ChangeColor) findViewById(R.id.id_indicator_two);
		mTabIndicators.add(two);
		ChangeColor three = (ChangeColor) findViewById(R.id.id_indicator_three);
		mTabIndicators.add(three);

		builder = new AlertDialog.Builder(getApplicationContext());
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);

		one.setIconAlpha(1.0f);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setOverflowButtonAlways()
	{
		try
		{
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKey = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKey.setAccessible(true);
			menuKey.setBoolean(config, false);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 设置menu显示icon
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu)
	{

		if (featureId == Window.FEATURE_ACTION_BAR && menu != null)
		{
			if (menu.getClass().getSimpleName().equals("MenuBuilder"))
			{
				try
				{
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public void onClick(View v)
	{
		clickTab(v);

	}

	/**
	 * 点击Tab按钮
	 * 
	 * @param v
	 */
	private void clickTab(View v)
	{
		resetOtherTabs();

		switch (v.getId())
		{
		case R.id.id_indicator_one:
			mTabIndicators.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
		
			break;
		case R.id.id_indicator_two:
			mTabIndicators.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			mTabIndicators.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		}
	}

	/**
	 * 重置其他的TabIndicator的颜
	 */
	private void resetOtherTabs()
	{
		for (int i = 0; i < mTabIndicators.size(); i++)
		{
			mTabIndicators.get(i).setIconAlpha(0);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels)
	{
		if (positionOffset > 0)
		{
			ChangeColor left = mTabIndicators.get(position);
			ChangeColor right = mTabIndicators.get(position + 1);
			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageSelected(int position)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrollStateChanged(int state)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			// TODO Auto-generated method stub
			builder.setIcon(R.drawable.yoo);
			builder.setTitle("Miracles happen every day!");
			builder.setMessage("确定要退出程序吗?");
			builder.setPositiveButton("确定", 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//MusicActivity.isExit=true;
							MainActivity.this.finish();
						}
			});
			builder.setNegativeButton("取消", 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
			}).create().show();
		}else if (keyCode==KeyEvent.KEYCODE_BACK) {
			
			if (isExit) {
				MainActivity.this.finish();
			} else {
				Toast.makeText(getApplicationContext(), "再按一下，就会退出！", Toast.LENGTH_SHORT).show();
				isExit= true;
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						isExit=false;
					}
				}, 3000);
			}
		}
		return false;
	}
}
