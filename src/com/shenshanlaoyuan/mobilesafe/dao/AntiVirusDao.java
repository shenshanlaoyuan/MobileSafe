package com.shenshanlaoyuan.mobilesafe.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 病毒业务类
 * 
 * @author hp
 * 
 */
public class AntiVirusDao {

	/**
	 * 添加病毒
	 * 
	 * @param md5
	 *            病毒文件的md5值
	 * @param desc
	 *            病毒的描述信息
	 */
	public static void addVirus(String md5, String desc) {
		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				"/data/data/com.shenshanlaoyuan.mobilesafe/files/antivirus.db",
				null, SQLiteDatabase.OPEN_READWRITE);
		ContentValues values = new ContentValues();
		values.put("md5", md5);
		values.put("type", 6);
		values.put("name", "Android.Hack.CarrierIQ.a");
		values.put("desc", desc);
		database.insert("datable", null, values);
		database.close();
	}

	/**
	 * 判断文件是否是病毒
	 * @param md5
	 * 		病毒文件的md5值
	 * @return
	 */
	public static boolean isVirus(String md5) {
		boolean res = false;
		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				"/data/data/com.shenshanlaoyuan.mobilesafe/files/antivirus.db",
				null, SQLiteDatabase.OPEN_READWRITE);
		Cursor cursor = database.rawQuery("select 1 from datable where md5=?", new String[]{md5});
		if (cursor.moveToNext()) {
			res = true;
		}
		database.close();
		cursor.close();
		return res;

	}
}
