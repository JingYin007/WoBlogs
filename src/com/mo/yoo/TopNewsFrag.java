package com.mo.yoo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.mo.view.NewsContentActivity;
import com.mo.view.RecommentNewsView;
import com.mo.adapter.NewsAdapter;
import com.mo.bean.News;
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

public class TopNewsFrag extends Fragment
{
	  private List<News> hotnews;
	  private ArrayList<News> hotNews2;
	  private ListView lv_hotNews;
	  private TextView tv_recomment_news;
	  private LinearLayout ll_loadingView;
	  private View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		if(rootView==null){  
            rootView=inflater.inflate(R.layout.sup_news, null);  
        }  
		//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
        ViewGroup parent = (ViewGroup) rootView.getParent();  
        if (parent != null) {  
            parent.removeView(rootView);  
        }   
        return rootView;  
		//	return inflater.inflate(R.layout.tab_news, container, false);
	}
	
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				NewsAdapter	adapter1 = new NewsAdapter(getActivity(),hotNews2);
				lv_hotNews.setAdapter(adapter1);
				ll_loadingView.setVisibility(View.GONE);
	
				break;
			default:
				break;
			}		
		};			
	};
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);  
		
		lv_hotNews =  (ListView) getActivity().findViewById(R.id.lv_hotnews);  
		tv_recomment_news=(TextView) getActivity().findViewById(R.id.tv_recomment_news);
        ll_loadingView=(LinearLayout) getActivity().findViewById(R.id.loading);
        ll_loadingView.setVisibility(View.VISIBLE);
       
    	new Thread(){
			@Override
			public void run() {
				
				if (NetUtil.isNetOk(getActivity())) {
					try {
						URL url = new URL("http://wcf.open.cnblogs.com/news/hot/10");
						URLConnection connection = url.openConnection();  
							
						InputStream inputStream = connection.getInputStream();  
						hotnews=XmlPulltoParser.ParseHotNews(inputStream);
						
						
						hotNews2=new ArrayList<News>();
						        for (News hotnew : hotnews) {  
						        	System.out.println("---"+hotnew.getSummary());   
						            System.out.println(hotnew.getId());
						            System.out.println("---"+hotnew.getTitle());	
						        }
						        hotNews2.addAll(hotnews);
						        Message msg = handler.obtainMessage();
								msg.obj = hotnews;
								msg.what = 1;
								handler.sendMessage(msg);
								
						        
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						Log.d("node","无法解析");  
						e.printStackTrace();
					} catch (Throwable e) {
						e.printStackTrace();
					} 
				}
			}
		}.start();
		//暂时禁止功能
		tv_recomment_news.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent top_blog_intent=new Intent(getActivity(), RecommentNewsView.class);
				startActivity(top_blog_intent);
				}
		});
		
		lv_hotNews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				lv_NewsToContent(view, position);
			}

			private void lv_NewsToContent(View view, int position) {
				TextView tv_newsID2=(TextView) view.findViewById(R.id.tv_newsID);
				String string=tv_newsID2.getText().toString();
				
				Intent newsContent_intent=new Intent(getActivity(), NewsContentActivity.class);
				 Bundle bundle = new Bundle();
				  //字符、字符串、布尔、字节数组、浮点数等等，都可以传
				  bundle.putString("NEWSID", string);
				  newsContent_intent.putExtras(bundle);
				  startActivity(newsContent_intent);
			
			}
		});
	};
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}
	

}
