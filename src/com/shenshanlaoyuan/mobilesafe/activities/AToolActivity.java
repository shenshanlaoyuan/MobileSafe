package com.shenshanlaoyuan.mobilesafe.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shenshanlaoyuan.mobilesafe.R;
/**
 * 高级工具界面
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

	public void phoneQuery(View v){
		Intent intent = new Intent(this,PhoneLocationActivity.class);
		startActivity(intent);
	}
	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_atool);
	}
}
