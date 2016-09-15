package com.shenshanlaoyuan.mobilesafe.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.service.ComingPhoneService;
import com.shenshanlaoyuan.mobilesafe.service.TelSmsBlackService;
import com.shenshanlaoyuan.mobilesafe.utils.MyConstants;
import com.shenshanlaoyuan.mobilesafe.utils.ServiceUtils;
import com.shenshanlaoyuan.mobilesafe.utils.SpTools;
import com.shenshanlaoyuan.mobilesafe.view.SettingCenterItemView;

public class SettingCenterActivity extends Activity {
	private SettingCenterItemView sciv_autoupdate;
	private SettingCenterItemView sciv_blackservice;
	private SettingCenterItemView sciv_phoneLocationService;
	private RelativeLayout rl_style_root;
	private TextView tv_locationStyle_content;
	private ImageView iv_locationSytle_click;

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
		// 设置黑名单拦截复选框初始值
		sciv_blackservice.setChecked(ServiceUtils.isServiceRunning(
				getApplicationContext(),
				"com.shenshanlaoyuan.mobilesafe.service.TelSmsBlackService"));
		// 设置来电归属地复选框初始值
		sciv_phoneLocationService.setChecked(ServiceUtils.isServiceRunning(
				getApplicationContext(),
				"com.shenshanlaoyuan.mobilesafe.service.ComingPhoneService"));
	}

	private String[] styleNames = new String[] { "卫士蓝", "金属灰", "苹果绿", "活力橙",
			"半透明" };

	private AlertDialog dialog;
	
	private void showStyleDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SettingCenterActivity.this);

		builder.setTitle("选择归属地样式");

		builder.setSingleChoiceItems(styleNames, Integer
				.parseInt(SpTools.getString(getApplicationContext(),
						MyConstants.STYLEBGINDEX, "0")),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						SpTools.putString(getApplicationContext(),
								MyConstants.STYLEBGINDEX, which + "");
						tv_locationStyle_content
								.setText(styleNames[which]);
						dialog.dismiss();
					}
				});
		dialog = builder.create();
		dialog.show();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		// 归属地样式条目点击事件
		rl_style_root.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN://按下
					iv_locationSytle_click.setImageResource(R.drawable.jiantou1_pressed);
					break;
				case MotionEvent.ACTION_UP://松开
					iv_locationSytle_click.setImageResource(R.drawable.jiantou1_disable);
					showStyleDialog();
					break;

				default:
					break;
				}

				return false;
			}
		});

		// 箭头的点击事件
		iv_locationSytle_click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showStyleDialog();

			}

			

		});

		// 来电归属地服务开启和关闭
		sciv_phoneLocationService.setItemClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (ServiceUtils
						.isServiceRunning(getApplicationContext(),
								"com.shenshanlaoyuan.mobilesafe.service.ComingPhoneService")) {
					// 关闭服务
					Intent phoneLocationService = new Intent(
							SettingCenterActivity.this,
							ComingPhoneService.class);
					stopService(phoneLocationService);
					sciv_phoneLocationService.setChecked(false);
				} else {
					// 开启服务
					Intent phoneLocationService = new Intent(
							SettingCenterActivity.this,
							ComingPhoneService.class);
					startService(phoneLocationService);
					sciv_phoneLocationService.setChecked(true);
				}
			}
		});

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
				if (ServiceUtils
						.isServiceRunning(getApplicationContext(),
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
		// 获取来电归属地设置的自定义view
		sciv_phoneLocationService = (SettingCenterItemView) findViewById(R.id.sciv_setting_center_phonelocationservice);

		// 归属地样式的根布局
		rl_style_root = (RelativeLayout) findViewById(R.id.rl_settingcenter_locationsytle_root);
		// 归属地样式的名字
		tv_locationStyle_content = (TextView) findViewById(R.id.tv_settingcenter_locationsytle_content);
		// 点击图片按钮来显示样式选择对话框
		iv_locationSytle_click = (ImageView) findViewById(R.id.iv_settingcenter_locationsytle_select);

	}
}
