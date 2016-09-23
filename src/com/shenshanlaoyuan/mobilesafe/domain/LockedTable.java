package com.shenshanlaoyuan.mobilesafe.domain;


/**
 * 程序锁数据库 加锁的表结构
 * @author hp
 *
 */
public interface LockedTable {
	String TABLENAME = "locked";//程序锁的表名
	String PACKNAME = "packname";//程序锁表的列名
}
