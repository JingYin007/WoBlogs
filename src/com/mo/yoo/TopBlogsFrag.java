package com.mo.yoo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.mo.view.BlogsContentActivity;
import com.mo.view.BookmarkActivity;
import com.mo.view.HomeBlogsView;
import com.mo.view.NewsContentActivity;
import com.mo.view.RecommentNewsView;
import com.mo.adapter.BlogsAdapter;
import com.mo.adapter.NewsAdapter;
import com.mo.bean.Blogs;
import com.mo.bean.News;
import com.mo.db.Col_BlogsDao;
import com.mo.parse.XmlPulltoParser;
import com.mo.util.NetUtil;
import com.mo.woBlogs.R;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TopBlogsFrag extends Fragment
{
	  private View rootView;
	  
	  private ArrayList<Blogs> blogs;
		 private ArrayList<Blogs> tendayBlogs2;
		 private ListView lv_topBlogs;
		 private BlogsAdapter adapter5;
		 private LinearLayout ll_loadingView;
		 private XmlPulltoParser xpb;
		 private TextView tv_home_blogs;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		if(rootView==null){  
            rootView=inflater.inflate(R.layout.sup_blogs, null);  
        }  
		//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
        ViewGroup parent = (ViewGroup) rootView.getParent();  
        if (parent != null) {  
            parent.removeView(rootView);  
        }   
        return rootView;  
		//	return inflater.inflate(R.layout.tab_news, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);  
		
		lv_topBlogs=(ListView) getActivity().findViewById(R.id.lv_tendayblogs);
		ll_loadingView=(LinearLayout) getActivity().findViewById(R.id.loading);
		ll_loadingView.setVisibility(View.VISIBLE);
		tv_home_blogs=(TextView) getActivity().findViewById(R.id.tv_home_blogs);
		xpb=new XmlPulltoParser();
		blogs=new ArrayList<Blogs>();
		InitData();
	
		lv_topBlogs.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				lv_BlogsToCentent(view,position,tendayBlogs2);
			}

			private void lv_BlogsToCentent(View view, int position,ArrayList<Blogs> blog) {
	
				
				
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable("BlogBean", blog.get(position));
				intent.putExtras(bundle);
				
				intent.setClass(getActivity(), BlogsContentActivity.class);
				
				startActivity(intent);
			}
		});
		tv_home_blogs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent home_blog_intent=new Intent(getActivity(), HomeBlogsView.class);
				startActivity(home_blog_intent);
			}
		});
		
	};
	
	
	private void InitData() {
		// TODO Auto-generated method stub
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (NetUtil.isNetOk(getActivity())) {
					try {
						URL url = new URL("http://wcf.open.cnblogs.com/blog/TenDaysTopDiggPosts/10");
						URLConnection connection5 = url.openConnection();  
						InputStream inputStream = connection5.getInputStream();  
						
						blogs = xpb.ParseBlogs(inputStream);  
						tendayBlogs2=new ArrayList<Blogs>();
						
							tendayBlogs2.addAll(blogs);
						       	Message msg = handler.obtainMessage();
								msg.obj = blogs;
								msg.what=2;
								handler.sendMessage(msg);
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
	
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 2:
				adapter5 = new BlogsAdapter(getActivity(), tendayBlogs2);
	            lv_topBlogs.setAdapter(adapter5);  
	            
	            ll_loadingView.setVisibility(View.GONE);
//	            if (tendayBlogs2.size()!=0) {
//	            	ll_loadingView.setVisibility(View.GONE);
//				}
				break;
			default:
				break;
			}		
		};			
	};
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}
	

}
