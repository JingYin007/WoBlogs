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

public class BlogsAdapter extends BaseAdapter{
	
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Blogs> blogs=new ArrayList<Blogs>();
	
	private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private ImageListener listener;
	public BlogsAdapter(Context context,ArrayList<Blogs> Blogs) {
		
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
			convertView=inflater.inflate(R.layout.item_blogs, null);
			hodler=new ViewHolder();
			hodler.tv_Title=(TextView) convertView.findViewById(R.id.tv_newTitle);
			hodler.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			hodler.tv_uri=(TextView) convertView.findViewById(R.id.tv_uri);
			hodler.iv_avatar=(ImageView) convertView.findViewById(R.id.iv_avatar);
			hodler.tv_diggs=(TextView) convertView.findViewById(R.id.tv_diggs);
			hodler.tv_views=(TextView) convertView.findViewById(R.id.tv_views);
			hodler.tv_comments=(TextView) convertView.findViewById(R.id.tv_comments);
			hodler.tv_summary=(TextView) convertView.findViewById(R.id.tv_summary);
			hodler.tv_updated=(TextView) convertView.findViewById(R.id.tv_updated);
			hodler.tv_postid=(TextView) convertView.findViewById(R.id.tv_blogID);
			convertView.setTag(hodler);
		}else {
			hodler=(ViewHolder) convertView.getTag();
			
		}
		hodler.iv_avatar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "准备添加收藏！", Toast.LENGTH_SHORT).show();
			}
		});
		
		String Pub_Date = blogs.get(position).getPublished().substring(0,10);
		String Pub_Time = blogs.get(position).getPublished().substring(11,19);
		
		hodler.tv_Title.setText(blogs.get(position).getTitle());
		hodler.tv_summary.setText("     "+blogs.get(position).getSummary());
		
		hodler.tv_name.setText("博主："+blogs.get(position).getAuthorName());
		hodler.tv_uri.setText("主页："+blogs.get(position).getAuthorUri());
		hodler.tv_diggs.setText(String.valueOf(blogs.get(position).getDiggs()));
		hodler.tv_comments.setText(String.valueOf(blogs.get(position).getComments()));
		hodler.tv_views.setText(String.valueOf(blogs.get(position).getViews()));
		hodler.tv_postid.setText(String.valueOf(blogs.get(position).getId()));
		hodler.tv_updated.setText("时间："+Pub_Date+"\t\t"+Pub_Time);
		
		String imgAvaterUrl=blogs.get(position).getAuthorAvater();
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
		
		TextView tv_Title,tv_published,
						tv_sourceName,tv_summary,tv_views,tv_comments,tv_diggs,tv_postid,
						tv_name,tv_uri,tv_updated;
		ImageView iv_avatar;
	}
}
