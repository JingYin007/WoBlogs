package com.mo.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.mo.adapter.BlogsAdapter;
import com.mo.adapter.RecommentNewsAdapter;
import com.mo.bean.Blogs;
import com.mo.bean.News;
import com.mo.db.Col_BlogsDao;
import com.mo.parse.XmlPulltoParser;
import com.mo.util.NetUtil;
import com.mo.woBlogs.R;
import com.mo.yoo.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeBlogsView extends Activity {

	private CustomListView lv_homeBlogs;// 自定义ListView
	private static final String TAG = "FindGroupFragment";
	
	private static final int LOAD_DATA_FINISH = 10;// 上拉刷新
	private static final int REFRESH_DATA_FINISH = 11;// 下拉刷新
	//页码索引
	private int PageIndex=1;
	//加载数据条数
	private int dataSize=5;
	private BlogsAdapter homeBlogsAdapter;

	 private ArrayList<Blogs> blogs;
	 private ArrayList<Blogs> dataBlogs;
	 private BlogsAdapter adapter5;
	 private Col_BlogsDao collectionDao;
	 private TextView tv_back,tv_mark;
	 private LinearLayout ll_loadingView;
	 private XmlPulltoParser xpb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.push_right_in, R.anim.hold);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_blogs);
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_mark = (TextView) findViewById(R.id.tv_mark);
		ll_loadingView=(LinearLayout) findViewById(R.id.loading);
		ll_loadingView.setVisibility(View.VISIBLE);
		lv_homeBlogs = (CustomListView) findViewById(R.id.lv_homeBlogs);
		collectionDao=new Col_BlogsDao(this);
		xpb=new XmlPulltoParser();
		blogs=new ArrayList<Blogs>();
		loadData(-1);

		lv_homeBlogs.setAdapter(homeBlogsAdapter);
		
		lv_homeBlogs.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//很有意思的情况，点击的item 调取下一item的信息
				lv_BlogsToCentent(view,position-1,blogs);
			}

			private void lv_BlogsToCentent(View view, int position,ArrayList<Blogs> blog) {
	
				
				
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable("BlogBean", blog.get(position));
				intent.putExtras(bundle);
				
				intent.setClass(HomeBlogsView.this, BlogsContentActivity.class);
				startActivity(intent);
			}
		});
		lv_homeBlogs.setOnRefreshListener(new CustomListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO 下拉刷新
				Log.e(TAG, "onRefresh");
				loadData(0);
			}

		});
		lv_homeBlogs.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				// TODO 加载更多
				Log.e(TAG, "onLoad");
				loadData(1);
			}
		});
		// //关闭下拉刷新
		// GroupList.setCanRefresh(!GroupList.isCanRefresh());
		// //关闭上拉刷新
		// GroupList.setCanLoadMore(!GroupList.isCanLoadMore());
		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.hold,R.anim.push_right_out);
				
			}
		});
	}

	/*
	 * 上下拉刷新加载数据方法
	 */
	public void loadData(final int updateTag) {
		new Thread() {
			@Override
			public void run() {
				switch (updateTag) {
				case -1:
				case  0:// 这里是刷新数据
					PageIndex =1;
					if (NetUtil.isNetOk(HomeBlogsView.this)) {
						try {
							URL url = new URL(
									"http://wcf.open.cnblogs.com/blog/sitehome/paged/"+PageIndex+"/7");
							URLConnection connection5 = url.openConnection();
							InputStream inputStream = connection5.getInputStream();
							blogs.clear();
							dataBlogs = xpb.ParseBlogs(inputStream); 

							
							for (Blogs hotnew : dataBlogs) {
								System.out.println("---" + hotnew.getSummary());
								System.out.println(hotnew.getId());
								System.out.println("---" + hotnew.getTitle());
							}
							blogs.addAll(dataBlogs);
							
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;

				case 1:
					// 这里是上拉刷新
					PageIndex ++;
					
					if (NetUtil.isNetOk(HomeBlogsView.this)) {
						try {
							URL url = new URL(
									"http://wcf.open.cnblogs.com/blog/sitehome/paged/"+PageIndex+"/5");
							URLConnection connection5 = url.openConnection();
							InputStream inputStream = connection5.getInputStream();

							dataBlogs = xpb.ParseBlogs(inputStream); 
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				}

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (updateTag == 0) {// 下拉刷新
					// 通知Handler
					handler.sendEmptyMessage(REFRESH_DATA_FINISH);
				} else if (updateTag == 1) {// 上拉刷新
					// 通知Handler
					handler.sendEmptyMessage(LOAD_DATA_FINISH);
				}else if (updateTag==-1) {
					Message msg = handler.obtainMessage();
					msg.obj = dataBlogs;
					msg.what = 2;
					handler.sendMessage(msg);
				}
			}
		}.start();
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 2:
				homeBlogsAdapter = new BlogsAdapter(HomeBlogsView.this,
						blogs);
				lv_homeBlogs.setAdapter(homeBlogsAdapter);
				if (blogs.size() != 0) {
					ll_loadingView.setVisibility(View.GONE);
				}
				break;
			case REFRESH_DATA_FINISH:
				
				homeBlogsAdapter = new BlogsAdapter(HomeBlogsView.this,
						blogs);
				lv_homeBlogs.setAdapter(homeBlogsAdapter);
				if (blogs.size() != 0) {
					ll_loadingView.setVisibility(View.GONE);
				}
				if (homeBlogsAdapter != null) {
					homeBlogsAdapter.notifyDataSetChanged();
				}
				lv_homeBlogs.onRefreshComplete(); // 下拉刷新完成
				break;
			case LOAD_DATA_FINISH:
				Log.d("mo", "LOAD_DATA_FINISH");
				if (homeBlogsAdapter != null) {
					blogs.addAll(dataBlogs);
					homeBlogsAdapter.notifyDataSetChanged();
				}
				lv_homeBlogs.onLoadMoreComplete(); // 加载更多完成
				break;
			default:
				break;
			}
		}

	};
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.hold,R.anim.push_right_out);
			}
	return false;
}
}
