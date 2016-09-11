package com.shenshanlaoyuan.mobilesafe.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 黑名单数据库
 * 
 * @author hp
 * 
 */
public class BlackDB extends SQLiteOpenHelper {

	/**
	 * 初始版本信息
	 * 
	 * @param context
	 */
	public BlackDB(Context context) {
		super(context, "black.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// 只初始一次
		// 创建表
		db.execSQL("create table blacktb(_id integer primary key autoincrement,phone text,mode integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// 版本号发生变化，执行此方法
		// 清空数据
		db.execSQL("drop table blacktb");
		onCreate(db);
	}

}
