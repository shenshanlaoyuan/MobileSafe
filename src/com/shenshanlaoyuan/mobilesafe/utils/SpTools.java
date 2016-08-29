package com.shenshanlaoyuan.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * SharedPreferences保存数据工具类
 * @author hp
 *
 */
public class SpTools {
	public static void putString(Context context,String key, String value){
		SharedPreferences sp =context.getSharedPreferences(MyConstants.SPFILE,context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();//保存数据
	}
	public static String getString(Context context,String key, String value){
		SharedPreferences sp =context.getSharedPreferences(MyConstants.SPFILE,context.MODE_PRIVATE);
		return sp.getString(key, value);//读取数据
	}
	public static void putBoolean(Context context,String key, boolean value){
		SharedPreferences sp =context.getSharedPreferences(MyConstants.SPFILE,context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();//保存数据
	}
	public static boolean getBoolean(Context context,String key, boolean value){
		SharedPreferences sp =context.getSharedPreferences(MyConstants.SPFILE,context.MODE_PRIVATE);
		return sp.getBoolean(key, value);//读取数据
	}
}
