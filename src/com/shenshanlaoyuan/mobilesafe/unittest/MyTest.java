package com.shenshanlaoyuan.mobilesafe.unittest;

import java.util.List;

import android.test.AndroidTestCase;

import com.shenshanlaoyuan.mobilesafe.dao.BlackDao;
import com.shenshanlaoyuan.mobilesafe.domain.BlackBean;
import com.shenshanlaoyuan.mobilesafe.domain.BlackTable;
import com.shenshanlaoyuan.mobilesafe.engine.ReadContantsEngine;
import com.shenshanlaoyuan.mobilesafe.utils.ServiceUtils;

public class MyTest extends AndroidTestCase {
	
	public void testDelete(){
		BlackDao dao = new BlackDao(getContext());
		dao.delete("12345670");
		
	}
	public void testUpdate(){
		BlackDao dao = new BlackDao(getContext());
		dao.update("12345670", BlackTable.ALL);
	}
	
	public void testFindAllBlackDatas(){
		BlackDao dao = new BlackDao(getContext());
		//获取所有黑名单数据
		List<BlackBean> datas = dao.getAllDatas();
		System.out.println(datas);
	}
	
 
	public void testAddBlackNumber(){
		BlackDao dao = new BlackDao(getContext());
		for (int i = 0; i < 200;i++){
			dao.add("1234567" + i, BlackTable.SMS);
		}
	}
	
	public void testReadContants(){
		ReadContantsEngine.readContants(getContext());//获取虚拟的上下文
	}
	public void testIsServiceRunning(){
		ServiceUtils.isServiceRunning(getContext(), "");
	}
}
