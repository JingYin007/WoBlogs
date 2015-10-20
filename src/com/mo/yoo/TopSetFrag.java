package com.mo.yoo;

import net.micode.compass.CompassActivity;

import com.mo.view.BookmarkActivity;
import com.mo.view.SearchView;
import com.mo.woBlogs.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TopSetFrag extends Fragment  implements OnClickListener{
	  private View rootView;
	  private AlertDialog.Builder builder_exit;
	  private Button btn_mark,btn_search,btn_compass,btn_exit;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{

			if(rootView==null){  
	            rootView=inflater.inflate(R.layout.sup_set, null);  
	        }  
			//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
	        ViewGroup parent = (ViewGroup) rootView.getParent();  
	        if (parent != null) {  
	            parent.removeView(rootView);  
	        }   
	        return rootView;  
			//	return inflater.inflate(R.layout.tab_news, container, false);
		}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		builder_exit=new AlertDialog.Builder(getActivity());
		btn_mark=(Button) getActivity().findViewById(R.id.btn_mark);
		btn_search=(Button) getActivity().findViewById(R.id.btn_search);
		btn_compass=(Button) getActivity().findViewById(R.id.btn_outline);
		btn_exit=(Button) getActivity().findViewById(R.id.btn_exit);
		
		btn_mark.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		btn_compass.setOnClickListener(this);
		btn_exit.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_mark:
			Intent to_mark=new Intent(getActivity(), BookmarkActivity.class);
			startActivity(to_mark);
			break;
		case R.id.btn_search:
			Intent to_search=new Intent(getActivity(), SearchView.class);
			startActivity(to_search);
			break;
		case R.id.btn_outline:
			Intent to_compass=new Intent(getActivity(), CompassActivity.class);
			startActivity(to_compass);
			break;
		case R.id.btn_exit:
			builder_exit.setIcon(R.drawable.yoo);
			builder_exit.setTitle("Miracles happen every day!");
			builder_exit.setMessage("确定要退出程序吗?");
			builder_exit.setPositiveButton("确定", 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							getActivity().finish();
							//android.os.Process.killProcess(android.os.Process.myPid());
							System.exit(0);
						}
			});
			builder_exit.setNegativeButton("取消", 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
			}).create().show();
		
			break;
		default:
			break;
		}
	}
}
