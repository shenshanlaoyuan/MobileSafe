package com.shenshanlaoyuan.mobilesafe.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.engine.SmsEngine;

/**
 * 高级工具界面
 * 
 * @author hp
 * 
 */
public class AToolActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}

	/**
	 * 归属地查询
	 * 
	 * @param v
	 */
	public void phoneQuery(View v) {
		Intent intent = new Intent(this, PhoneLocationActivity.class);
		startActivity(intent);
	}

	
	/**
	 * 短信备份
	 * @param v
	 */
	public void smsBaike(View v) {
		new Thread() {
			public void run() {
				SmsEngine.smsBaikeXml(getApplicationContext());
			};
		}.start();

	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_atool);
	}
}
