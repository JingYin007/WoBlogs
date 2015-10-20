package com.mo.adapter;

import java.util.ArrayList;

import javax.crypto.spec.IvParameterSpec;

import com.mo.bean.Blogs;
import com.mo.db.Col_BlogsDao;
import com.mo.util.AsynImageLoader;
import com.mo.view.BookmarkActivity;
import com.mo.yoo.MainActivity;
import com.mo.woBlogs.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookmarkAdapter extends BaseAdapter{
	
	private Context context;
	LayoutInflater inflater;
	AsynImageLoader loader;
	ArrayList<Blogs> blogs;
	Col_BlogsDao collectionDao;
	AlertDialog.Builder builder;
	public BookmarkAdapter(Context context,ArrayList<Blogs> Blogs) {
		// TODO Auto-generated constructor stub
		loader=new AsynImageLoader();
		blogs=new ArrayList<Blogs>();
		this.context=context;
		this.blogs=Blogs;
		inflater = LayoutInflater.from(context);
		collectionDao = new Col_BlogsDao(context);
		builder = new AlertDialog.Builder(context);
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
		ViewHolder vholder;
		final Blogs entity = blogs.get(position);
		if (convertView==null) {
			convertView=inflater.inflate(R.layout.item_col_blogs, null);
			vholder=new ViewHolder();
			vholder.tv_mark2=(TextView) convertView.findViewById(R.id.tv_mark2);
			vholder.tv_mark1=(TextView) convertView.findViewById(R.id.tv_mark1);
			vholder.iv_mark=(ImageView) convertView.findViewById(R.id.iv_mark);
			vholder.iv_icon=(ImageView) convertView.findViewById(R.id.iv_icon);
			convertView.setTag(vholder);
		}else {
			vholder=(ViewHolder) convertView.getTag();
			
		}
		vholder.tv_mark2.setText(blogs.get(position).getTitle());
		vholder.tv_mark1.setText(blogs.get(position).getAuthorName());
		switch (position%5) {
		case 0:
			vholder.iv_icon.setImageResource(R.drawable.icon1);
			break;
		case 1:
			vholder.iv_icon.setImageResource(R.drawable.icon2);
			break;
		case 2:
			vholder.iv_icon.setImageResource(R.drawable.icon3);
			break;
		case 3:
			vholder.iv_icon.setImageResource(R.drawable.icon4);
			break;
		case 4:
			vholder.iv_icon.setImageResource(R.drawable.icon5);
			break;

		default:
			break;
		}
		vholder.iv_mark.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				builder.setIcon(R.drawable.top6);
				builder.setTitle("Miracles happen every day!");
				builder.setMessage("确定要取消收藏吗?");
				builder.setPositiveButton("确定", 
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								collectionDao.deleteByUrl(entity.getId());
						       	BookmarkActivity.blogList.remove(position);
								Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show();
								notifyDataSetChanged();
							}
				});
				builder.setNegativeButton("取消", 
						new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
				}).create().show();	
			}
		});
		return convertView;
	}
	class ViewHolder{
		TextView tv_mark1,
		tv_mark2;
		ImageView iv_icon,iv_mark;
	}

}
