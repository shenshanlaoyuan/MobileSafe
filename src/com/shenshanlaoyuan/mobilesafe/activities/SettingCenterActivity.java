package com.shenshanlaoyuan.mobilesafe.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.service.TelSmsBlackService;
import com.shenshanlaoyuan.mobilesafe.utils.MyConstants;
import com.shenshanlaoyuan.mobilesafe.utils.ServiceUtils;
import com.shenshanlaoyuan.mobilesafe.utils.SpTools;
import com.shenshanlaoyuan.mobilesafe.view.SettingCenterItemView;

public class SettingCenterActivity extends Activity {
	private SettingCenterItemView sciv_autoupdate;
	private SettingCenterItemView sciv_blackservice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();// 初始化界面
		initEvent();// 初始化事件
		initDate();// 初始化数据
	}

	private void initDate() {
		// TODO Auto-generated method stub
		// 设置自动更新复选框初始值
		sciv_autoupdate.setChecked(SpTools.getBoolean(getApplicationContext(),
				MyConstants.AUTOUPDATE, false));
		//设置黑名单拦截复选框初始值
		sciv_blackservice.setChecked(ServiceUtils.isServiceRunning(
				getApplicationContext(),
				"com.shenshanlaoyuan.mobilesafe.service.TelSmsBlackService"));
	}

	private void initEvent() {
		// TODO Auto-generated method stub

		// 自动更新的事件处理
		sciv_autoupdate.setItemClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sciv_autoupdate.setChecked(!sciv_autoupdate.isChecked());
				// 记录复选框的状态
				SpTools.putBoolean(getApplicationContext(),
						MyConstants.AUTOUPDATE, sciv_autoupdate.isChecked());
			}
		});

		// 黑名单服务启动或关闭
		sciv_blackservice.setItemClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ServiceUtils.isServiceRunning(getApplicationContext(),
						"com.shenshanlaoyuan.mobilesafe.service.TelSmsBlackService")) {
					// 关闭服务
					Intent blackService = new Intent(
							SettingCenterActivity.this,
							TelSmsBlackService.class);
					stopService(blackService);
					sciv_blackservice.setChecked(false);
				} else {
					// 开启服务
					Intent blackService = new Intent(
							SettingCenterActivity.this,
							TelSmsBlackService.class);
					startService(blackService);
					sciv_blackservice.setChecked(true);
				}
			}
		});

	}

	private void initView() {
		setContentView(R.layout.activity_settingcenter);
		// 获取自动更新的自定义view
		sciv_autoupdate = (SettingCenterItemView) findViewById(R.id.sciv_setting_center_autoupdate);
		// 获取黑名单拦截的自定义view
		sciv_blackservice = (SettingCenterItemView) findViewById(R.id.sciv_setting_center_blackservice);

	}
}
