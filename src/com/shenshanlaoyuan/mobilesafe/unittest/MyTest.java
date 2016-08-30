package com.shenshanlaoyuan.mobilesafe.unittest;

import android.test.AndroidTestCase;

import com.shenshanlaoyuan.mobilesafe.engine.ReadContantsEngine;
import com.shenshanlaoyuan.mobilesafe.utils.ServiceUtils;

public class MyTest extends AndroidTestCase {
 
	public void testReadContants(){
		ReadContantsEngine.readContants(getContext());//获取虚拟的上下文
	}
	public void testIsServiceRunning(){
		ServiceUtils.isServiceRunning(getContext(), "");
	}
}
