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

import com.mo.adapter.RecommentNewsAdapter;
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
import android.widget.TextView;
import android.widget.Toast;

public class RecommentNewsView extends Activity {

	private CustomListView lv_recommentNews;// 自定义ListView
	private static final String TAG = "FindGroupFragment";
	
	private static final int LOAD_DATA_FINISH = 10;// 上拉刷新
	private static final int REFRESH_DATA_FINISH = 11;// 下拉刷新
	//页码索引
	private int PageIndex=1;
	//加载数据条数
	private int dataSize=5;
	private RecommentNewsAdapter rNewsAdapter;

	private List<News> newsData;
	private ArrayList<News> newsList;
	private TextView tv_back, tv_mark;
	private LinearLayout ll_loadingView;
	private XmlPulltoParser xpb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.push_right_in, R.anim.hold);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recomment_news);
		tv_back=(TextView) findViewById(R.id.tv_back);
		lv_recommentNews = (CustomListView) findViewById(R.id.lv_GroupList);
		xpb = new XmlPulltoParser();
		ll_loadingView = (LinearLayout) findViewById(R.id.loading);
		ll_loadingView.setVisibility(View.VISIBLE);
		newsList = new ArrayList<News>();
		loadData(-1);

		lv_recommentNews.setAdapter(rNewsAdapter);
		
		lv_recommentNews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				lv_NewsToContent(view, position);
				}

			private void lv_NewsToContent(View view, int position) {
				// TODO Auto-generated method stub

				TextView tv_newsID2=(TextView) view.findViewById(R.id.tv_newsID);
				String string=tv_newsID2.getText().toString();
				
				Intent newsContent_intent=new Intent(RecommentNewsView.this, NewsContentActivity.class);
				 Bundle bundle = new Bundle();
				  //字符、字符串、布尔、字节数组、浮点数等等，都可以传
				  bundle.putString("NEWSID", string);
				  newsContent_intent.putExtras(bundle);
				  startActivity(newsContent_intent);
			
			
			}
		});
		lv_recommentNews.setOnRefreshListener(new CustomListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO 下拉刷新
				Log.e(TAG, "onRefresh");
				loadData(0);
			}

		});
		lv_recommentNews.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

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
					
					if (NetUtil.isNetOk(RecommentNewsView.this)) {
						try {
							URL url = new URL(
									"http://wcf.open.cnblogs.com/news/recommend/paged/"+PageIndex+"/7");
							URLConnection connection5 = url.openConnection();
							InputStream inputStream = connection5.getInputStream();
							newsList.clear();
							newsData = XmlPulltoParser.ParseHotNews(inputStream);

							/*
							for (News hotnew : newsData) {
								System.out.println("---" + hotnew.getSummary());
								System.out.println(hotnew.getId());
								System.out.println("---" + hotnew.getTitle());
							}*/
							newsList.addAll(newsData);
							
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
					
					if (NetUtil.isNetOk(RecommentNewsView.this)) {
						try {
							URL url = new URL(
									"http://wcf.open.cnblogs.com/news/recommend/paged/"+PageIndex+"/5");
							URLConnection connection5 = url.openConnection();
							InputStream inputStream = connection5.getInputStream();

							newsData = XmlPulltoParser.ParseHotNews(inputStream);
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
					msg.obj = newsData;
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
				rNewsAdapter = new RecommentNewsAdapter(RecommentNewsView.this,
						newsList);
				lv_recommentNews.setAdapter(rNewsAdapter);
				if (newsList.size() != 0) {
					ll_loadingView.setVisibility(View.GONE);
				}
				break;
			case REFRESH_DATA_FINISH:
				
				rNewsAdapter = new RecommentNewsAdapter(RecommentNewsView.this,
						newsList);
				lv_recommentNews.setAdapter(rNewsAdapter);
				if (newsList.size() != 0) {
					ll_loadingView.setVisibility(View.GONE);
				}
				if (rNewsAdapter != null) {
					rNewsAdapter.notifyDataSetChanged();
				}
				lv_recommentNews.onRefreshComplete(); // 下拉刷新完成
				break;
			case LOAD_DATA_FINISH:
				Log.d("mo", "LOAD_DATA_FINISH");
				if (rNewsAdapter != null) {
					newsList.addAll(newsData);
					rNewsAdapter.notifyDataSetChanged();
				}
				lv_recommentNews.onLoadMoreComplete(); // 加载更多完成
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
