package com.shenshanlaoyuan.mobilesafe.activities;

import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.service.LostFindService;
import com.shenshanlaoyuan.mobilesafe.utils.MyConstants;
import com.shenshanlaoyuan.mobilesafe.utils.ServiceUtils;
import com.shenshanlaoyuan.mobilesafe.utils.SpTools;

public class Setup4Activity extends BaseSetupActivity {

	private CheckBox cb_isprotected;

	/**
	 * 子类需要覆盖此方法，来完成界面的显示
	 */
	@Override
	public void initView() {
		setContentView(R.layout.activity_setup4);
		cb_isprotected = (CheckBox) findViewById(R.id.cb_setup4_isprotected);
		
	}

	/**
	 * 初始化数据
	 */
	@Override
	public void initDate() {
		if (ServiceUtils.isServiceRunning(getApplicationContext(), "com.shenshanlaoyuan.mobilesafe.service.LostFindService")){
			cb_isprotected.setChecked(true);
		} else {
			cb_isprotected.setChecked(false);
		}
		super.initDate();
	}
	/**
	 * 初始化事件 
	 */
	@Override
	public void initEvent() {
		
		cb_isprotected.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//如果选择打钩，开启防盗保护,防盗保护是一个服务
				if (isChecked) {
					System.out.println("check true");
					
					Intent service = new Intent(Setup4Activity.this, LostFindService.class);
					startService(service);
				} else {
					System.out.println("check false");
					//关闭服务
					Intent service = new Intent(Setup4Activity.this, LostFindService.class);
					stopService(service);
				}
			}
		});
		
		super.initEvent();
	}
	@Override
	public void nextActivity() {
		// TODO Auto-generated method stub
		SpTools.putBoolean(getApplicationContext(), MyConstants.ISSETUP, true);
		startActivity(LostFindActivity.class);
	}

	@Override
	public void prevActivity() {
		// TODO Auto-generated method stub
		startActivity(Setup3Activity.class);
	}

	

}
