package com.shenshanlaoyuan.mobilesafe.dao;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shenshanlaoyuan.mobilesafe.db.LockedDB;
import com.shenshanlaoyuan.mobilesafe.domain.LockedTable;


/**
 * 程序锁的数据存取层
 * 
 * @author Administrator
 * 
 */
public class LockedDao {
	private LockedDB lockdb;

	public LockedDao(Context context) {
		lockdb = new LockedDB(context);
	}
	
	/**
	 * @param packName
	 *    app 的包名
	 * @return
	 *    是否是加锁的
	 */
	public boolean isLocked(String packName){
		SQLiteDatabase database = lockdb.getReadableDatabase();
		Cursor cursor = database.rawQuery("select 1 from " + LockedTable.TABLENAME + " where " + LockedTable.PACKNAME + "=?",
				new String[]{packName});
		if (cursor.moveToNext()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @param packName
	 *      要加锁的app的包名
	 */
	public void add(String packName){
		SQLiteDatabase database = lockdb.getWritableDatabase();
		//表中列的封装
		ContentValues values = new ContentValues();
		values.put(LockedTable.PACKNAME, packName);
		
		//往表中添加一条记录
		database.insert(LockedTable.TABLENAME, null, values );
		
		database.close();//关闭数据库
	}
	
	/**
	 * @return
	 *    获取所有加锁的app包名
	 */
	public List<String> getAllLockedDatas(){
		
		List<String> lockedNames = new ArrayList<String>();
		SQLiteDatabase database = lockdb.getReadableDatabase();
		//取出所有的包名
		Cursor cursor = database.rawQuery("select " + LockedTable.PACKNAME + " from " + LockedTable.TABLENAME, null);
		while (cursor.moveToNext()) {
			//取出包名
			lockedNames.add(cursor.getString(0));
		}
		
		return lockedNames;
	}
	
	/**
	 * @param packName
	 *    要解除加锁的app包名
	 */
	public void remove(String packName){
		SQLiteDatabase database = lockdb.getWritableDatabase();
		//表中删除一条数据
		database.delete(LockedTable.TABLENAME, LockedTable.PACKNAME +"=?", new String[]{packName});
		
		database.close();//关闭数据库
	}
}
