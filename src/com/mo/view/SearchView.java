package com.mo.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.mo.bean.Blogger;
import com.mo.adapter.SearchAdapter;
import com.mo.parse.XmlPulltoParser;
import com.mo.woBlogs.R;
import com.ty.view.PullToRefreshActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SearchView extends Activity {

	private EditText et_search;
	private Button btn_search;
	private ListView lv_search;
	private LinearLayout ll_loading;
	private SearchAdapter searchAdapter;
	private XmlPulltoParser xpb;
	ArrayList<Blogger> blogger2;
	List<Blogger> bloggers;
	private TextView tv_back;
	private String sss;
	private Handler handler;
	private InputMethodManager imm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.push_right_in,R.anim.hold);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_view);
		
		initView();
		initData();
	}
	private void initData() {
		// TODO Auto-generated method stub
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_loading.setVisibility(View.VISIBLE);
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
				new Thread(){
					public void run() {

						try {
							
							sss=et_search.getText().toString();
							URL url = new URL("http://wcf.open.cnblogs.com/blog/bloggers/search?t="+sss);
							URLConnection connection = url.openConnection();  
							System.out.println("准备解析");  
							InputStream inputStream = connection.getInputStream();  
							//ParserSearch parserSearch=new ParserSearch();
							
							bloggers = xpb.ParseSearch(inputStream);  
							blogger2=new ArrayList<Blogger>();
								
							blogger2.addAll(bloggers); 
							        Message msg = handler.obtainMessage();
									msg.obj = bloggers;
									msg.what=11;
									handler.sendMessage(msg);
									
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							Log.d("node","无法解析");  
							e.printStackTrace();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
					};
				}.start();
				handler=new Handler(){
					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						switch (msg.what) {
						case 11:
							searchAdapter = new SearchAdapter(SearchView.this, blogger2);
				            lv_search.setAdapter(searchAdapter);
							ll_loading.setVisibility(View.GONE);
							break;
						default:
							break;
						}
						
					 	
					}
				};
			}
		});
		
		lv_search.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent_to_person = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable("BloggerBean", blogger2.get(position));
				intent_to_person.putExtras(bundle);
				intent_to_person.setClass(getApplicationContext(), PullToRefreshActivity.class);
				startActivity(intent_to_person);
			}
		});
	}
	private void initView() {
		// TODO Auto-generated method stub
		btn_search=(Button) findViewById(R.id.btn_search);
		et_search=(EditText) findViewById(R.id.et_search);
		lv_search=(ListView) findViewById(R.id.lv_search);
		tv_back = (TextView) findViewById(R.id.tv_back);
		xpb = new XmlPulltoParser();
		blogger2=new ArrayList<Blogger>();
		bloggers=new ArrayList<Blogger>();
		ll_loading=(LinearLayout) findViewById(R.id.loading);
		//软键盘显示状况 设置
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
		
		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.hold,R.anim.push_right_out);
				
			}
		});
	}
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.hold,R.anim.push_right_out);
			}
	return false;
}
}
