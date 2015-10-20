package com.mo.view;

import java.util.ArrayList;

import com.mo.adapter.BookmarkAdapter;
import com.mo.bean.Blogs;
import com.mo.db.Col_BlogsDao;
import com.mo.woBlogs.R;

import android.app.Activity;
import android.app.DownloadManager.Request;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class BookmarkActivity extends Activity{

	private TextView tv_back;;
	private ListView lv_mark;
	public static ArrayList<Blogs> blogList;
	private Col_BlogsDao collectionDao;
	public static BookmarkAdapter bookmarkAdapter;
	private Handler handler;
	private Blogs outBlog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.push_right_in,R.anim.hold);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.bookmark_view);
	    tv_back=(TextView) findViewById(R.id.tv_back);
	    lv_mark=(ListView) findViewById(R.id.lv_mark);
	    outBlog = new Blogs();
	    collectionDao = new Col_BlogsDao(this);
	   	
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				blogList = collectionDao.getDetail();
				Message msg = handler.obtainMessage();
				msg.obj = blogList;
				msg.what = 1;
				handler.sendMessage(msg);
				}
		}.start();
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 1:
					bookmarkAdapter = new BookmarkAdapter(BookmarkActivity.this,blogList);
					lv_mark.setAdapter(bookmarkAdapter);
					break;
				default:
					break;
				}
			}
		};
		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.hold,R.anim.push_right_out);
			}
		});
	    
	    lv_mark.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				outBlog = blogList.get(position);
				RedirectOutLine(outBlog);
			}
		});
	}
	private void RedirectOutLine(Blogs dataOut) {
		    Intent intent = new Intent();
			Bundle bundle_col = new Bundle();
			bundle_col.putSerializable("BlogBean", dataOut);
			intent.putExtras(bundle_col);
			
			intent.setClass(BookmarkActivity.this, BlogsContentActivity.class);
			startActivity(intent);
	}
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.hold,R.anim.push_right_out);
			}
	return false;
}
}
