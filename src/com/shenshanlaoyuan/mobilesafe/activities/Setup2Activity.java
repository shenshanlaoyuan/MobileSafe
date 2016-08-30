package com.shenshanlaoyuan.mobilesafe.activities;

import android.content.Context;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.style.TtsSpan.TelephoneBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.utils.MyConstants;
import com.shenshanlaoyuan.mobilesafe.utils.SpTools;

public class Setup2Activity extends BaseSetupActivity {

	private Button bt_bind;
	private ImageView iv_isbind;

	@Override
	public void initView() {
		setContentView(R.layout.activity_setup2);
		bt_bind = (Button) findViewById(R.id.bt_setup2_bindsim);
		iv_isbind = (ImageView) findViewById(R.id.iv_setup2_isbind);
	}

	/**
	 * 添加自己的事件
	 */
	@Override
	public void initEvent() {
		super.initEvent();
		bt_bind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(SpTools.getString(getApplicationContext(), MyConstants.SIM, ""))) {
					//绑定sim卡
					TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					String simSerialNumber = manager.getSimSerialNumber();
					SpTools.putString(getApplicationContext(), MyConstants.SIM, simSerialNumber);
					
					//切换绑定的图标
					iv_isbind.setImageResource(R.drawable.lock);
				} else {
					//解绑sim卡
					SpTools.putString(getApplicationContext(), MyConstants.SIM, "");
					
					//切换解绑绑的图标
					iv_isbind.setImageResource(R.drawable.unlock);
				}
				
				
			}
		});
		
	}
	/**
	 * 初始化数据
	 */
	@Override
	public void initDate() {
		if (TextUtils.isEmpty(SpTools.getString(getApplicationContext(), MyConstants.SIM, ""))) {
			//切换解绑绑的图标
			iv_isbind.setImageResource(R.drawable.unlock);
		}else{
			//切换绑定的图标
			iv_isbind.setImageResource(R.drawable.lock);
		}
		super.initDate();
	}
	@Override
	public void next(View v) {
		if (TextUtils.isEmpty(SpTools.getString(getApplicationContext(), MyConstants.SIM, ""))) {
			Toast.makeText(getApplicationContext(), "请绑定SIM卡", 1).show();
			return;
		}
		super.next(v);
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