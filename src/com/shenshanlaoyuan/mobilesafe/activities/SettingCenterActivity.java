package com.shenshanlaoyuan.mobilesafe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.utils.MyConstants;
import com.shenshanlaoyuan.mobilesafe.utils.SpTools;
import com.shenshanlaoyuan.mobilesafe.view.SettingCenterItemView;

public class SettingCenterActivity extends Activity {
	private SettingCenterItemView sciv_autoupdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();//初始化界面
		initEvent();//初始化事件
		initDate();//初始化数据
	}

	private void initDate() {
		// TODO Auto-generated method stub
		//设置自动更新复选框初始值
		sciv_autoupdate.setChecked(SpTools.getBoolean(getApplicationContext(), MyConstants.AUTOUPDATE, false));
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		sciv_autoupdate.setItemClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sciv_autoupdate.setChecked(!sciv_autoupdate.isChecked());
				//记录复选框的状态
				SpTools.putBoolean(getApplicationContext(), MyConstants.AUTOUPDATE, sciv_autoupdate.isChecked());
			}
		});
		
	}

	private void initView() {
		setContentView(R.layout.activity_settingcenter);
		sciv_autoupdate = (SettingCenterItemView) findViewById(R.id.sciv_setting_center_autoupdate);
		
	}
}
