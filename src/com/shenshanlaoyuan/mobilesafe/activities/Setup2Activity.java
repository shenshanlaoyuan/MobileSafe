package com.shenshanlaoyuan.mobilesafe.activities;

import com.shenshanlaoyuan.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;

public class Setup2Activity extends BaseSetupActivity {

	@Override
	public void initView() {
		setContentView(R.layout.activity_setup2);
		
	}

	@Override
	public void nextActivity() {
		startActivity(Setup3Activity.class);
		
	}

	@Override
	public void prevActivity() {
		// TODO Auto-generated method stub
		startActivity(Setup1Activity.class);
	}

	

}