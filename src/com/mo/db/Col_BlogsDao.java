package com.mo.db;

import java.util.ArrayList;

import com.mo.bean.Blogs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Col_BlogsDao {
	private DBHelper_Blogs dbHelper;

	public Col_BlogsDao(Context context) {
		dbHelper = new DBHelper_Blogs(context, DBHelper_Blogs.DB_NAME, null, 1);
	}

	public void addDetail(Blogs outLineList) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues values = new ContentValues();

		values.put("blogid", outLineList.getId());
		values.put("title", outLineList.getTitle());
		values.put("published", outLineList.getUpdated());
		values.put("comments", outLineList.getComments());
		values.put("authorName", outLineList.getAuthorName());
		values.put("content", outLineList.getContent());
		db.insert(DBHelper_Blogs.DB_COLLECTION_TABLE, null, values);
		db.close();
	}

	/**
	 * 查询数据库是否有url= url的那条记录
	 * 
	 * @param url
	 * @return
	 */
	public boolean queryByUrl(int id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String blogid = Integer.toString(id);
		
		//dbHelper.onUpgrade(db, 1, 2);
		Cursor cursor = db.query(DBHelper_Blogs.DB_COLLECTION_TABLE,
				new String[] { "blogid" }, "blogid = ?",
				new String[] { blogid }, null, null, null);
		if (cursor != null && cursor.moveToNext()) {
			cursor.close();
			return true;
		}
		db.close();
		return false;
	}

	public ArrayList<Blogs> getDetail() {
		ArrayList<Blogs> blog = new ArrayList<Blogs>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		//dbHelper.onUpgrade(db, 1, 2);
		
		Cursor cursor = db.query(DBHelper_Blogs.DB_COLLECTION_TABLE, null, null, null,
				null, null, null);
		while (cursor.moveToNext()) {
			Blogs data = new Blogs();
			data.setId(cursor.getString(cursor.getColumnIndexOrThrow("blogid")));
			data.setComments(cursor.getInt(cursor
					.getColumnIndexOrThrow("comments")));
			data.setTitle(cursor.getString(cursor
					.getColumnIndexOrThrow("title")));
			data.setPublished(cursor.getString(cursor
					.getColumnIndexOrThrow("published")));
			data.setAuthorName(cursor.getString(cursor
					.getColumnIndexOrThrow("authorName")));
			data.setContent(cursor.getString(cursor
					.getColumnIndexOrThrow("content")));
			
			blog.add(data);
		}
		cursor.close();
		db.close();
		return blog;
	}

	public void deleteByUrl(String id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		db.delete(DBHelper_Blogs.DB_COLLECTION_TABLE, "blogid = ?",
				new String[] { id });
		db.close();
	}

}
