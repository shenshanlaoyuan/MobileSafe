package com.shenshanlaoyuan.mobilesafe.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shenshanlaoyuan.mobilesafe.db.BlackDB;
import com.shenshanlaoyuan.mobilesafe.domain.BlackBean;
import com.shenshanlaoyuan.mobilesafe.domain.BlackTable;

/**
 * 黑名单数据业务封装类
 * 
 * @author hp
 */
public class BlackDao {
	
	private BlackDB blackDB;

	public BlackDao(Context context) {
		this.blackDB = new BlackDB(context);
	}

	/**
	 * 删除黑名单号码
	 * 
	 * @param phone
	 *            要删除的黑名单号码
	 */
	public void delete(String phone) {
		SQLiteDatabase db = this.blackDB.getWritableDatabase();
		// 根据黑名单号码，来删除黑名单数据
		db.delete(BlackTable.BLACKTABLE, BlackTable.PHONE + "=?",
				new String[] { phone });
		db.close();
	}

	/**
	 * @param phone
	 *            修改的黑名单号码
	 * @param mode
	 *            修改的新的拦截模式
	 */
	public void update(String phone, int mode) {
		SQLiteDatabase db = this.blackDB.getWritableDatabase();

		ContentValues values = new ContentValues();// 修改新的属性值
		// 设置新的拦截模式
		values.put(BlackTable.MODE, mode);

		// 根据号码更新新的模式
		db.update(BlackTable.BLACKTABLE, values, BlackTable.PHONE + "=?",
				new String[] { phone });

		db.close();// 关闭数据库

	}

	/**
	 * 
	 * @return 返回所有的黑名单数据
	 */
	public List<BlackBean> getAllDatas() {
		List<BlackBean> datas = new ArrayList<BlackBean>();
		SQLiteDatabase db = blackDB.getReadableDatabase();
		// 获取blacktb的所有数据游标
		Cursor cursor = db.rawQuery("select " + BlackTable.PHONE + ","
				+ BlackTable.MODE + " from " + BlackTable.BLACKTABLE, null);
		while (cursor.moveToNext()) {
			// 有数据，数据封装
			BlackBean bean = new BlackBean();

			// 封装黑名单号码
			bean.setPhone(cursor.getString(0));

			// 封装拦截模式
			bean.setMode(cursor.getInt(1));

			// 添加数据到集合中
			datas.add(bean);

		}

		cursor.close();
		db.close();

		return datas;
	}

	/**
	 * 添加黑名单号码
	 * 
	 * @param bean
	 *            黑名单信息的封装bean
	 */
	public void add(BlackBean bean) {
		add(bean.getPhone(), bean.getMode());
	}

	/**
	 * 添加黑名单
	 * 
	 * @param phone
	 *            黑名单号码
	 * @param mode
	 *            拦截模式
	 */
	public void add(String phone, int mode) {
		// 获取黑名单数据库
		SQLiteDatabase db = blackDB.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(BlackTable.PHONE, phone);
		values.put(BlackTable.MODE, mode);
		// 往黑名单表中插入一条记录
		db.insert(BlackTable.BLACKTABLE, null, values);

		// 关闭数据库
		db.close();
	}
}
