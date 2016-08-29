package com.shenshanlaoyuan.mobilesafe.activities;

import com.shenshanlaoyuan.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;

public class Setup4Activity extends BaseSetupActivity {

	@Override
	public void initView() {
		setContentView(R.layout.activity_setup4);
		
	}

	@Override
	public void nextActivity() {
		// TODO Auto-generated method stub
		startActivity(LostFindActivity.class);
	}

	@Override
	public void prevActivity() {
		// TODO Auto-generated method stub
		startActivity(Setup3Activity.class);
	}

	

}
