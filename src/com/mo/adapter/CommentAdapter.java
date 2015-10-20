package com.mo.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.mo.bean.Comment;
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

public class CommentAdapter extends BaseAdapter{

	private Context context;
	LayoutInflater inflater;
	
	ArrayList<Comment> comments=new ArrayList<Comment>();
	

	public CommentAdapter(Context context,ArrayList<Comment> comments) {
		this.context=context;
		this.comments=comments;
		inflater = LayoutInflater.from(context);
	}
	
	
	@Override
	public int getCount() {
		return comments.size();
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
		ViewHolder hodler;
		if (convertView==null) {
			
			convertView=inflater.inflate(R.layout.item_comment, null);
			hodler=new ViewHolder();
			hodler.tv_COMMENTCONTENT=(TextView) convertView.findViewById(R.id.tv_COMMENTCONTENT);
			hodler.tv_NAME=(TextView) convertView.findViewById(R.id.tv_NAME);
			hodler.tv_TIME=(TextView) convertView.findViewById(R.id.tv_TIME);
			hodler.iv_icon=(ImageView) convertView.findViewById(R.id.iv_icon);
			convertView.setTag(hodler);
		}else {
			hodler=(ViewHolder) convertView.getTag();
			
		}
		String Pub_Date = comments.get(position).getPublished().substring(0,10);
		String Pub_Time = comments.get(position).getPublished().substring(11,19);
		
		hodler.tv_COMMENTCONTENT.setText("\t\t\t"+comments.get(position).getContent());
		hodler.tv_NAME.setText(comments.get(position).getName());
		hodler.tv_TIME.setText("\t\t"+Pub_Date+"\t\t"+Pub_Time);
		switch (position%5) {
		case 0:
			hodler.iv_icon.setImageResource(R.drawable.sup5);
			break;
		case 1:
			hodler.iv_icon.setImageResource(R.drawable.sup6);
			break;
		case 2:
			hodler.iv_icon.setImageResource(R.drawable.sup7);
			break;
		case 3:
			hodler.iv_icon.setImageResource(R.drawable.sup8);
			break;
		case 4:
			hodler.iv_icon.setImageResource(R.drawable.sup9);
			break;
		default:
			break;
		}
		return convertView;
	}
	class ViewHolder{
		
		TextView tv_COMMENTCONTENT,tv_NAME,tv_TIME;
		ImageView iv_icon;
	}

}
