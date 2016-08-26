package com.shenshanlaoyuan.mobilesafe.activities;

import com.shenshanlaoyuan.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;
/**
 * 主界面
 * @author hp
 *
 */
public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();//初始化界面
	}

	private void initView() {
		setContentView(R.layout.activity_home);
	}
}
