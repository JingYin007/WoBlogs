package com.mo.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.mo.bean.News;
import com.mo.util.BitmapCache;
import com.mo.woBlogs.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsAdapter extends BaseAdapter{

	private Context context;
	LayoutInflater inflater;
	private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private ImageListener listener;
	ArrayList<News> hotNews=new ArrayList<News>();
	

	public NewsAdapter(Context context,ArrayList<News> hotNews) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.hotNews=hotNews;
		inflater = LayoutInflater.from(context);
		mQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hotNews.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder hodler;
		if (convertView==null) {
			
			//inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.item_news, null);
			hodler=new ViewHolder();
			hodler.tv_Title=(TextView) convertView.findViewById(R.id.tv_newTitle);
			
			hodler.iv_icon=(ImageView) convertView.findViewById(R.id.iv_icon);
			hodler.tv_published=(TextView) convertView.findViewById(R.id.tv_published);
			hodler.tv_sourceName=(TextView) convertView.findViewById(R.id.tv_sourceName);
			hodler.tv_diggs=(TextView) convertView.findViewById(R.id.tv_diggs);
			hodler.tv_views=(TextView) convertView.findViewById(R.id.tv_views);
			hodler.tv_comments=(TextView) convertView.findViewById(R.id.tv_comments);
			hodler.tv_summary=(TextView) convertView.findViewById(R.id.tv_summary);
			hodler.tv_newsID=(TextView) convertView.findViewById(R.id.tv_newsID);
			
			convertView.setTag(hodler);
		}else {
			hodler=(ViewHolder) convertView.getTag();
		}
		
		 // 利用Volley加载图片
		  
	       
		String Pub_Date = hotNews.get(position).getPublished().substring(0,10);
		String Pub_Time = hotNews.get(position).getPublished().substring(10,18);
		
		hodler.tv_Title.setText(hotNews.get(position).getTitle());
		hodler.tv_summary.setText("       "+hotNews.get(position).getSummary());
		hodler.tv_published.setText("时间："+Pub_Date+" "+Pub_Time);
		hodler.tv_sourceName.setText("来源："+hotNews.get(position).getSourceName());
		hodler.tv_diggs.setText(String.valueOf(hotNews.get(position).getDiggs()));
		hodler.tv_comments.setText(String.valueOf(hotNews.get(position).getComments()));
		hodler.tv_views.setText(String.valueOf(hotNews.get(position).getViews()));
		hodler.tv_newsID.setText(String.valueOf(hotNews.get(position).getId()));
		
		String topicIcon=hotNews.get(position).getTopicIcon();
		 listener = ImageLoader.getImageListener(hodler.iv_icon, R.drawable.icon1,  R.drawable.icon1);
		 //根据博主头像存在与否进行 图片加载判断
		 if (topicIcon==null ||topicIcon.equals("")) {
				hodler.iv_icon.setImageResource(R.drawable.icon1);;
			}else {
				mImageLoader.get(topicIcon, listener);
			}
		return convertView;
	}
	class ViewHolder{
		ImageView iv_icon;
		TextView tv_Title,tv_published,
			tv_sourceName,tv_summary,tv_views,tv_comments,tv_diggs,tv_updated,tv_newsID;
	}

}
