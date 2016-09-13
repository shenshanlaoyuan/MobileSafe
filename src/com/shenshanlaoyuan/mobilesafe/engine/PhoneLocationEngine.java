package com.shenshanlaoyuan.mobilesafe.engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 手机归属地业务封装类
 * 
 * @author hp
 * 
 */
public class PhoneLocationEngine {

	/**
	 * 归属地查询
	 * 
	 * @param phoneNumber
	 *            查询的号码
	 * @param context
	 * @return 归属地
	 */
	public static String Query(String phoneNumber, Context context) {
		String location = phoneNumber;

		//
		/**
		 * 判断phoneNumber 1， 手机号 2， 固定电话 4008517517 3， 服务号码 110 120 95559 010 -
		 * 99999999 0391-3456654 0755-8888888
		 */
		// 如果是手机号
		// 正则表达式
		Pattern p = Pattern.compile("1{1}[3857]{1}[0-9]{9}");
		Matcher m = p.matcher(phoneNumber);
		boolean b = m.matches();
		if (b) {
			// 是手机号
			location = mobileQuery(phoneNumber, context);
		} else if (phoneNumber.length() >= 11) {
			// 固定号码
			// 如果是固定号码
			location = phoneQuery(phoneNumber, context);
		} else {

			// 如果是服务号码
			location = serviceNumberQuery(phoneNumber);
		}
		return location;

	}
	/**
	 * 查询服务号码 如：110匪警
	 * 
	 * @param phoneNumber
	 * @return
	 */
	private static String serviceNumberQuery(String phoneNumber) {
		String res = phoneNumber;
		if (phoneNumber.equals("110")) {
			res = "匪警";
		} else if (phoneNumber.equals("10086")) {
			res = "中国移动";
		}
		return res;
	}

	/**
	 * 固定电话归属地查询
	 * 
	 * @param phoneNumber
	 *            查询的号码
	 * @param context
	 * @return 固定电话归属地
	 */
	private static String phoneQuery(String phoneNumber, Context context) {
		String res = phoneNumber;
		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				"/data/data/com.shenshanlaoyuan.mobilesafe/files/address.db", null,
				SQLiteDatabase.OPEN_READONLY);
		String quHao = "";
		// 2位区号 3位区号
		if (phoneNumber.charAt(1) == '1' || phoneNumber.charAt(1) == '2') {
			// 2位区号
			quHao = phoneNumber.substring(1, 3);
		} else {
			// 3位区号
			quHao = phoneNumber.substring(1, 4);
		}

		Cursor cursor = database.rawQuery(
				"select location from data2 where area=?",
				new String[] { quHao });
		if (cursor.moveToNext()) {
			res = cursor.getString(0);
		}
		return res;
	}

	/**
	 * 手机归属地查询
	 * 
	 * @param phoneNumber
	 *            查询的号码
	 * @param context
	 * @return 手机号码归属地
	 */
	public static String mobileQuery(String phoneNumber, Context context) {
		String res = phoneNumber;

		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				"/data/data/com.shenshanlaoyuan.mobilesafe/files/address.db",
				null, SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = database
				.rawQuery(
						"select location from data2 where id = (select outKey from data1 where id=?)",
						new String[] { phoneNumber.substring(0, 7) });

		if (cursor.moveToNext()) {
			res = cursor.getString(0);
		}

		return res;
	}
}
