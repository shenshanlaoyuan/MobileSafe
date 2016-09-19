package com.shenshanlaoyuan.mobilesafe.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建程序锁的数据库 ,就一张表，该表就一个列 packname
 * @author Administrator
 *
 */
public class LockedDB extends SQLiteOpenHelper {

	public LockedDB(Context context) {
		super(context, "locked.db", null, 2);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建加锁的表
		db.execSQL("create table locked(_id integer primary key autoincrement,packname text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table locked");
		onCreate(db);
	}

}
