package com.mo.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.mo.adapter.CommentAdapter;
import com.mo.bean.Blogs;
import com.mo.bean.Comment;
import com.mo.parse.XmlPulltoParser;
import com.mo.woBlogs.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BlogsCommentActivity extends Activity{

		private TextView tv_newsCommentTitle,tv_back;
		private ListView lv_Comment;
		private List<Comment> Comment;  
		private Blogs blogData;
		private Handler handler;
		private ArrayList<Comment> Comment2;
		private String blogId;
		private String blogTitle;
		private CommentAdapter adapter ;
		private Button btn_next,btn_pre;
		private int pageIndex=1;
		private int  Commentcount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.push_right_in,R.anim.hold);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comment_view);
		
		tv_newsCommentTitle=(TextView) findViewById(R.id.tv_newsCommentTitle);
		tv_back=(TextView) findViewById(R.id.tv_back);
		lv_Comment=(ListView) findViewById(R.id.lv_newsComment);
		btn_next=(Button)findViewById(R.id.btn_next);
		btn_pre=(Button)findViewById(R.id.btn_pre);
		blogData = (Blogs) getIntent().getSerializableExtra("mark_blogs");
		
		blogId=blogData.getId();
		blogTitle=blogData.getTitle();
		Commentcount=blogData.getComments();
		
		tv_newsCommentTitle.setText(blogTitle);
		
		loadComment(blogId);
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				adapter = new CommentAdapter(BlogsCommentActivity.this, Comment2);
				lv_Comment.setAdapter(adapter);
			 	
			}
		};
		btn_pre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(pageIndex>1){ 
					if(com.mo.util.NetUtil.isNetOk(BlogsCommentActivity.this)){
						pageIndex--;
						loadComment(blogId);
					}else{
						Toast.makeText(getApplicationContext(), "请检查您的网络状态", Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(getApplicationContext(), "已是第一页", Toast.LENGTH_SHORT).show();
				}
			}
		});
		btn_next.setOnClickListener(new OnClickListener() {
			int pageCount= Commentcount/8;
			@Override
			public void onClick(View v) {
					if(pageIndex<pageCount){ 
					if(com.mo.util.NetUtil.isNetOk(BlogsCommentActivity.this)){
						pageIndex++;
						loadComment(blogId);
					}else{
						Toast.makeText(getApplicationContext(), "请检查您的网络状态", Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(getApplicationContext(), "已是最后一页", Toast.LENGTH_SHORT).show();			
				}
			}
		});
		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.hold,R.anim.push_right_out);
			}
		});
	}
	private void loadComment(final String id) {
		new Thread(){
			public void run() {
				try {
					URL url = new URL("http://wcf.open.cnblogs.com/blog/post/"+id+"/comments/"+pageIndex+"/8");
					URLConnection connection = url.openConnection();  
					System.out.println("准备解析");  
					InputStream inputStream = connection.getInputStream();  
					
					Comment = XmlPulltoParser.ParseComment(inputStream);
					Comment2=new ArrayList<Comment>();
						
					Comment2.addAll(Comment); 
					        Message msg = handler.obtainMessage();
							msg.obj = Comment;
							handler.sendMessage(msg);
							
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					Log.d("node","无法解析");  
					e.printStackTrace();
				} catch (Throwable e) {
					e.printStackTrace();
				}  
			};
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
