package com.ty.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.mo.adapter.BlogsAdapter;
import com.mo.adapter.PersonBlogsAdapter;
import com.mo.bean.Blogger;
import com.mo.bean.Blogs;
import com.mo.parse.XmlPulltoParser;
import com.mo.util.BitmapCache;
import com.mo.util.NetUtil;
import com.mo.view.BlogsContentActivity;
import com.mo.view.MarqueTextView;
import com.mo.woBlogs.R;
import com.ty.pull.to.refresh.PullToRefreshView;

public class PullToRefreshActivity extends Activity {

	public static final int REFRESH_DELAY = 2000;

	private PullToRefreshView mPullToRefreshView;
	private Blogger blogger_data;
	private String blogger_title;
	private String blogger_id;
	private String blogger_blogapp;
	private String blogger_avatar;
	private String blogger_postcount;
	private ListView lv_person_blog;
	private ImageView iv_blogger_avatar;
	private TextView tv_title;
	private MarqueTextView tv_id;
	private RequestQueue mQueue;
	private ImageLoader mImageLoader;
	private ImageListener listener;
	private XmlPulltoParser xpb;
	private ArrayList<Blogs> blogsData;
	private ArrayList<Blogs> blogsList;
	private PersonBlogsAdapter pBlogsAdapter;
	private LinearLayout ll_loading,ll_addMore;
	private int pageIndex;
	final static int LOAD_FIRST = -1;
	final static int LOAD_MORE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_blogs_view);
		initView();
		initData(-1);
		mPullToRefreshView
				.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
					@Override
					public void onRefresh() {
						mPullToRefreshView.postDelayed(new Runnable() {
							@Override
							public void run() {
								/*
								 * Toast.makeText(PullToRefreshActivity.this,
								 * "Success", Toast.LENGTH_SHORT).show();
								 */
								initData(-1);
								mPullToRefreshView.setRefreshing(false);
							}
						}, REFRESH_DELAY);
					}
				});
		ll_addMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initData(1);
				 Toast.makeText(PullToRefreshActivity.this,
						  "addMore", Toast.LENGTH_SHORT).show();
			}
		});
		lv_person_blog.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				lv_BlogsToCentent(view, position, blogsList);
			}

			private void lv_BlogsToCentent(View view, int position,
					ArrayList<Blogs> blogsData) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable("BlogBean", blogsData.get(position));
				intent.putExtras(bundle);
				intent.setClass(getApplicationContext(),
						BlogsContentActivity.class);
				startActivity(intent);
			}
		});

	}

	private void initView() {
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
		lv_person_blog = (ListView) findViewById(R.id.list_view);
		
		lv_person_blog.addFooterView(LayoutInflater.from(this).inflate(
				R.layout.list_footer_more2, null));
		
		ll_addMore = (LinearLayout) findViewById(R.id.ll_addMore);
		iv_blogger_avatar = (ImageView) findViewById(R.id.iv_blogger_avatar);
		tv_title = (TextView) findViewById(R.id.tv_blogger_title);
		tv_id = (MarqueTextView) findViewById(R.id.tv_blogger_id);
		mQueue = Volley.newRequestQueue(this);
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
		xpb = new XmlPulltoParser();
		ll_loading = (LinearLayout) findViewById(R.id.loading);
		ll_loading.setVisibility(View.VISIBLE);
		blogsList = new ArrayList<Blogs>();
		blogsData = new ArrayList<Blogs>();
		blogger_data = (Blogger) getIntent()
				.getSerializableExtra("BloggerBean");
		blogger_avatar = blogger_data.getAvatar();
		blogger_blogapp = blogger_data.getBlogapp();
		blogger_id = blogger_data.getId();
		blogger_postcount = blogger_data.getPostcount();
		blogger_title = blogger_data.getTitle();

		tv_id.setText(blogger_id);
		tv_title.setText(blogger_title);

		listener = ImageLoader.getImageListener(iv_blogger_avatar,
				R.drawable.sup4, R.drawable.sup4);
		// 根据博主头像存在与否进行 图片加载判断
		if (blogger_avatar == null || blogger_avatar.equals("")) {
			iv_blogger_avatar.setImageResource(R.drawable.sup4);
			;
		} else {
			mImageLoader.get(blogger_avatar, listener);
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOAD_FIRST:
				pBlogsAdapter = new PersonBlogsAdapter(
						PullToRefreshActivity.this, blogsList);
				lv_person_blog.setAdapter(pBlogsAdapter);
				if (blogsList.size() != 0) {
					ll_loading.setVisibility(View.GONE);
				}
				break;
			case LOAD_MORE:
				if (pBlogsAdapter != null) {
					blogsList.addAll(blogsData);
					pBlogsAdapter.notifyDataSetChanged();
				}
				break;
			default:
				break;
			}
		};
	};

	private void initData(final int TagLoading) {
		// TODO Auto-generated method stub
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (NetUtil.isNetOk(PullToRefreshActivity.this)) {
					try {
						if (TagLoading == -1) {
							pageIndex = 1;
							URL url = new URL(
									"http://wcf.open.cnblogs.com/blog/u/"
											+ blogger_blogapp + "/posts/"
											+ pageIndex + "/7");
							URLConnection connection5 = url.openConnection();
							InputStream inputStream = connection5
									.getInputStream();

							blogsData = xpb.ParseBlogs(inputStream);
							

							blogsList.addAll(blogsData);
							Message msg = handler.obtainMessage();
							msg.obj = blogsData;
							msg.what = LOAD_FIRST;
							handler.sendMessage(msg);
						} else {
							pageIndex++;
							URL url = new URL(
									"http://wcf.open.cnblogs.com/blog/u/"
											+ blogger_blogapp + "/posts/"
											+ pageIndex + "/5");
							URLConnection connection5 = url.openConnection();
							InputStream inputStream = connection5
									.getInputStream();

							blogsData = xpb.ParseBlogs(inputStream);
							handler.sendEmptyMessage(LOAD_MORE);
						}
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
			}
		}.start();
	}
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.hold,R.anim.push_right_out);
			}
	return false;
}
}
