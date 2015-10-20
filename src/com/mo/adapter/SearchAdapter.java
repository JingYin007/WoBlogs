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
import com.mo.bean.Blogger;
import com.mo.bean.Blogs;
import com.mo.util.BitmapCache;
import com.mo.woBlogs.R;
import com.ty.view.PullToRefreshActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchAdapter extends BaseAdapter{
	
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Blogger> bloggers=new ArrayList<Blogger>();
	
	private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private ImageListener listener;
	public SearchAdapter(Context context,ArrayList<Blogger> Blogger) {
		
		this.context=context;
		this.bloggers=Blogger;
		inflater = LayoutInflater.from(context);
		mQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bloggers.size();
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
			convertView=inflater.inflate(R.layout.item_search, null);
			hodler=new ViewHolder();
			hodler.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
			hodler.iv_avatar=(ImageView) convertView.findViewById(R.id.iv_blogger);
			hodler.tv_updated=(TextView) convertView.findViewById(R.id.tv_updated);
			hodler.tv_postcount=(TextView) convertView.findViewById(R.id.tv_postcount);
			convertView.setTag(hodler);
		}else {
			hodler=(ViewHolder) convertView.getTag();
			
		}
		
		String Pub_Date = bloggers.get(position).getUpdated().substring(0,10);
		String Pub_Time = bloggers.get(position).getUpdated().substring(10,18);
		
		
		hodler.tv_title.setText(bloggers.get(position).getTitle());
		hodler.tv_postcount.setText(bloggers.get(position).getPostcount()+" 篇.");
		hodler.tv_updated.setText(".最新： "+Pub_Date+"\t\t"+Pub_Time);
		
		String imgAvaterUrl=bloggers.get(position).getAvatar();
		 listener = ImageLoader.getImageListener(hodler.iv_avatar, R.drawable.icon3,  R.drawable.icon3);
		 //根据博主头像存在与否进行 图片加载判断
		 if (imgAvaterUrl==null ||imgAvaterUrl.equals("")) {
				hodler.iv_avatar.setImageResource(R.drawable.icon3);;
			}else {
				mImageLoader.get(imgAvaterUrl, listener);
			}
	
		 
		return convertView;
	}

	class ViewHolder{
		
		TextView tv_title,tv_postcount,
						tv_updated;
		ImageView iv_avatar;
	}
}
