package com.shenshanlaoyuan.mobilesafe.activities;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.utils.MyConstants;
import com.shenshanlaoyuan.mobilesafe.utils.SpTools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LostFindActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//如果第一次访问该界面，要先进入设置向导界面
		if (SpTools.getBoolean(getApplicationContext(), MyConstants.ISSETUP, false)) {
			//进入过设置向导界面
			initView();
		} else {
			//要进入设置向导界面
			Intent intent= new Intent(this,Setup1Activity.class);
			startActivity(intent);
			finish();//关闭自己
		}
	
	}

	private void initView() {
		setContentView(R.layout.activity_lostfind);
	}
}
