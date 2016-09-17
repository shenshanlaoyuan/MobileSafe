package com.shenshanlaoyuan.mobilesafe.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

/**
 * 短信的备份和还原业务类
 * 
 * @author hp
 * 
 */
public class SmsEngine {

	/**
	 * 短信备份 以XMl方式
	 * 
	 * @param context
	 */
	public static void smsBaikeXml(Context context) {

		// 1,通过内容提供者获取到短信
		Uri uri = Uri.parse("content://sms");
		// 获取电话记录的联系人游标
		final Cursor cursor = context.getContentResolver().query(uri,
				new String[] { "address", "date", "body", "type" }, null, null,
				" _id desc");
		//2,写到文件中
		File file = new File(Environment.getExternalStorageDirectory(), "sms.xml");
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter out = new PrintWriter(fos);
			
			//写根标记
			out.println("<smses count='" + cursor.getCount() + "'>");
			while (cursor.moveToNext()) {
				//取短信
				out.println("<sms>");
				
					//address 封装
					out.println("<address>" + cursor.getString(0) + "</address>");
					//date 封装
					out.println("<date>" + cursor.getString(1) + "</date>");
					//body 封装
					out.println("<body>" + cursor.getString(2) + "</body>");
					//type 封装
					out.println("<type>" + cursor.getString(3) + "</type>");
				
				out.println("</sms>");
				
			}
			
			//写根标记结束标记
			out.println("</smses>");
			out.close();
			cursor.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
