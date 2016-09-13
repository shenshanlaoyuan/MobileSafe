package com.shenshanlaoyuan.mobilesafe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.engine.PhoneLocationEngine;

/**
 * 手机归属地查询界面
 * 
 * @author hp
 * 
 */
public class PhoneLocationActivity extends Activity {

	private EditText et_phone;
	private Button bt_query;
	private TextView tv_location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initEvent();
	}

	private void initEvent() {
		
		//给EditText加文件变化事件
		et_phone.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				//改变后
				locationQuery();
				
			}
		});
		
		//设置查询按钮的监听
		// TODO Auto-generated method stub
		bt_query.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				locationQuery();
			}
		});
	}

	/**
	 * 归属地查询事件封装
	 */
	protected void locationQuery() {
		// TODO Auto-generated method stub
		String phone = et_phone.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) {
			//Toast.makeText(getApplicationContext(), "电话号码不能为空", 1).show();
			//抖动的效果
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
	        et_phone.startAnimation(shake);
		}
		//归属地查询
		String location = PhoneLocationEngine.Query(phone, getApplicationContext());
		//显示归属地
		tv_location.setText(location);
	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_phonelocation);
		// 输入号码
		et_phone = (EditText) findViewById(R.id.et_phonelocation_number);
		// 查询的按钮
		bt_query = (Button) findViewById(R.id.bt_phonelocation_query);

		// 显示号码归属地
		tv_location = (TextView) findViewById(R.id.tv_phonelocation_address);
	}
}
