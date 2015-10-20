package com.mo.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;






import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.mo.bean.Blogs;
import com.mo.util.BitmapCache;
import com.mo.view.MarqueTextView;
import com.mo.woBlogs.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonBlogsAdapter extends BaseAdapter{
	
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Blogs> blogs=new ArrayList<Blogs>();
	
	private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private ImageListener listener;
	public PersonBlogsAdapter(Context context,ArrayList<Blogs> Blogs) {
		
		this.context=context;
		this.blogs=Blogs;
		inflater = LayoutInflater.from(context);
		mQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return blogs.size();
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
			convertView=inflater.inflate(R.layout.item_blogger_blogs, null);
			hodler=new ViewHolder();
			hodler.tv_Title=(TextView) convertView.findViewById(R.id.tv_newTitle);
			hodler.tv_diggs=(TextView) convertView.findViewById(R.id.tv_diggs);
			hodler.tv_views=(TextView) convertView.findViewById(R.id.tv_views);
			hodler.tv_comments=(TextView) convertView.findViewById(R.id.tv_comments);
			hodler.tv_summary=(TextView) convertView.findViewById(R.id.tv_summary);
			hodler.tv_updated=(TextView) convertView.findViewById(R.id.tv_updated);
			convertView.setTag(hodler);
		}else {
			hodler=(ViewHolder) convertView.getTag();
			
		}
		
		String Pub_Date = blogs.get(position).getPublished().substring(0,10);
		String Pub_Time = blogs.get(position).getPublished().substring(11,19);
		
		hodler.tv_Title.setText(blogs.get(position).getTitle());
		hodler.tv_summary.setText("     "+blogs.get(position).getSummary());
		hodler.tv_diggs.setText(String.valueOf(blogs.get(position).getDiggs()));
		hodler.tv_comments.setText(String.valueOf(blogs.get(position).getComments()));
		hodler.tv_views.setText(String.valueOf(blogs.get(position).getViews()));
		hodler.tv_updated.setText(" ʱ�䣺"+Pub_Date+"\t\t"+Pub_Time);
		
		
		return convertView;
	}

	class ViewHolder{
		TextView tv_Title,
						tv_summary,tv_views,tv_comments,tv_diggs,
						tv_updated;
	}
}
