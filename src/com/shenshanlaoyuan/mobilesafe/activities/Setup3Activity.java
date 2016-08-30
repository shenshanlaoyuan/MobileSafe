package com.shenshanlaoyuan.mobilesafe.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.utils.MyConstants;
import com.shenshanlaoyuan.mobilesafe.utils.SpTools;

public class Setup3Activity extends BaseSetupActivity {

	private EditText et_safeNumber;
	private String safenumber;

	/**
	 * 初始化数据
	 */
	@Override
	public void initDate() {
		et_safeNumber.setText(SpTools.getString(getApplicationContext(), MyConstants.SAFENUMBER, ""));
		super.initDate();
	}
	@Override
	public void initView() {
		setContentView(R.layout.activity_setup3);
		et_safeNumber = (EditText) findViewById(R.id.et_setup3_safenumber);
		
	}
	/**
	 * 从手机联系人里获取安全号码
	 * @param view
	 */
	public void selectSafeNumber(View view){
		//弹出新的Activity来显示所有好友信息
		
		Intent friends = new Intent(this,FriendsActivity.class);
		startActivityForResult(friends,1);//启动显示好友界面
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (data != null) {//用户选择数据来关闭联系人界面,而不是直接点击返回按钮
			//取数据
			String phone = data.getStringExtra(MyConstants.SAFENUMBER);
			//显示安全号码
			et_safeNumber.setText(phone);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**		
	 * 覆盖父类的方法，实现业务
	 */
	@Override
	public void next(View v) {
		safenumber = et_safeNumber.getText().toString().trim();
		//如果安全号码为空，不能进行下一步
		if (TextUtils.isEmpty(safenumber)) {
			
			Toast.makeText(getApplicationContext(), "安全号码不能为空", 1).show();
			return;
		} else {
			//保存安全号码
			SpTools.putString(getApplicationContext(), MyConstants.SAFENUMBER, safenumber);
		}
		super.next(v);
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