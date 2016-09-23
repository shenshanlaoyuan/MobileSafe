package com.shenshanlaoyuan.mobilesafe.activities;


import java.util.List;

import com.shenshanlaoyuan.mobilesafe.domain.ContantBean;
import com.shenshanlaoyuan.mobilesafe.engine.ReadContantsEngine;


/**
 * @author hp
 * 显示所有好友信息的界面
 */
public class SmsLogActivity extends BaseFriendsCallSmsActivity {

	@Override
	public List<ContantBean> getDatas() {
		// TODO Auto-generated method stub
		return ReadContantsEngine.readSmsLog(getApplicationContext());
	}
	
}
