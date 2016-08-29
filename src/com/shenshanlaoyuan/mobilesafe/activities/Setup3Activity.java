package com.shenshanlaoyuan.mobilesafe.activities;

import com.shenshanlaoyuan.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;

public class Setup3Activity extends BaseSetupActivity {

	@Override
	public void initView() {
		setContentView(R.layout.activity_setup3);
		
	}

	@Override
	public void nextActivity() {
		// TODO Auto-generated method stub
		startActivity(Setup4Activity.class);
	}

	@Override
	public void prevActivity() {
		// TODO Auto-generated method stub
		startActivity(Setup2Activity.class);
	}

	

}