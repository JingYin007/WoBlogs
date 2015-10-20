package com.mo.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.mo.bean.NewsContent;
import com.mo.parse.XmlPulltoParser;
import com.mo.woBlogs.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsContentActivity extends Activity{

	private WebView wv_NewsContent;
	private MarqueTextView tv_Title;
	private TextView tv_SorceName,tv_published,tv_CommentCount,tv_id,tv_back;
	private List<NewsContent> newsContent;  
	private Handler handler;
	private ArrayList<NewsContent> newsContents2;
	public String NEWSid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.push_right_in,R.anim.hold);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_content);
		init();
		
		 /*获取Intent中的Bundle对象*/
		//  Bundle bundle = this.getIntent().getExtras();
        /*获取Bundle中的数据，注意类型和key*/
        final String NEWSid =getIntent().getStringExtra("NEWSID");

		new Thread(){
			public void run() {

				try {
					URL url = new URL("http://wcf.open.cnblogs.com/news/item/"+NEWSid);
				
					URLConnection connection = url.openConnection();  
					InputStream inputStream = connection.getInputStream();  
					newsContent = XmlPulltoParser.ParseNewsContent(inputStream);  
					newsContents2=new ArrayList<NewsContent>();
						
					newsContents2.addAll(newsContent); 
					        Message msg = handler.obtainMessage();
							msg.obj = newsContent;
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
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
			tv_id.setText(NEWSid);
			tv_Title.setText(newsContent.get(0).getTitle());
			tv_published.setText("发表时间："+newsContent.get(0).getSubmitDate());
			tv_SorceName.setText("FROM："+newsContent.get(0).getSourceName());
			tv_CommentCount.setText(String.valueOf(newsContent.get(0).getCommentCount()));
	       //设置适配属性
			wv_NewsContent.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			wv_NewsContent.loadDataWithBaseURL(null,newsContent.get(0).getContent(),"text/html", "utf-8",null) ;
				
			}
		};		
		   //设置WebView属性，能够执行Javascript脚本 
	    WebSettings settings = wv_NewsContent.getSettings();
        settings.setSupportZoom(true);   //支持缩放
        settings.setBuiltInZoomControls(true); //启用内置缩放装置
        settings.setJavaScriptEnabled(true); //启用JS脚本
	
	
		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.hold,R.anim.push_right_out);
				
			}
		});
		
		tv_CommentCount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				 String NewsID2=NEWSid;
				 String NewsTitle2=tv_Title.getText().toString();
				 String Comment2=tv_CommentCount.getText().toString();
				 Bundle bundle = new Bundle();
				  //字符、字符串、布尔、字节数组、浮点数等等，都可以传
				 bundle.putString("NewsID2", NewsID2);
				 bundle.putString("NewsTitle2", NewsTitle2);
				 bundle.putString("Comment2", Comment2);
				 Intent comment_intent=new Intent(NewsContentActivity.this, NewsCommentActivity.class);
					
				 comment_intent.putExtras(bundle);
				
				startActivity(comment_intent);
			}
		});
	}
	private void init(){
		tv_id=(TextView) findViewById(R.id.tv_id_News);
		tv_Title=(MarqueTextView) findViewById(R.id.tv_newTitle);
		tv_SorceName=(TextView) findViewById(R.id.tv_sourceName);
		tv_published=(TextView) findViewById(R.id.tv_published);
		tv_CommentCount=(TextView) findViewById(R.id.tv_Comments);
		tv_back=(TextView) findViewById(R.id.tv_back);
		wv_NewsContent=(WebView) findViewById(R.id.wv_Content);
		
		
	}
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.hold,R.anim.push_right_out);
			}
	return false;
}
}
