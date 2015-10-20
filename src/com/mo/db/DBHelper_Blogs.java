package com.mo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper_Blogs extends SQLiteOpenHelper {

	public static final String DB_NAME = "Blogsdb";
	public static final String DB_BLOGS_TABLE="Blogs";
	public static final String DB_COLLECTION_TABLE="Col_Blogs";
	public static int version = 2;

	public DBHelper_Blogs(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, factory, version);
		// TODO Auto-generated constructor stub
	}

	

	

	@Override
	public void onCreate(SQLiteDatabase db) {
		

		String sql = "create table "+DB_BLOGS_TABLE +"(" +
				"id integer primary key autoincrement," +
				"blogid integer," +
				"title varchar," +
				"authorName varchar," +
				"published varchar," +
				"comments integer," +
				"content varchar)";
		db.execSQL(sql);
		String sql2 = "create table "+DB_COLLECTION_TABLE+"(" +
				"id integer primary key autoincrement," +
				"blogid integer," +
				"title varchar," +
				"authorName varchar," +
				"published varchar," +
				"comments integer," +
				"content varchar)";
		db.execSQL(sql2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exit Note_File");
		onCreate(db);
	}

}
