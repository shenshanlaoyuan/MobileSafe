package com.shenshanlaoyuan.mobilesafe.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.utils.Md5Utils;
import com.shenshanlaoyuan.mobilesafe.utils.MyConstants;
import com.shenshanlaoyuan.mobilesafe.utils.SpTools;

/**
 * 主界面
 * 
 * @author hp
 * 
 */
public class HomeActivity extends Activity {

	private int icons[] = { R.drawable.safe, R.drawable.callmsgsafe,
			R.drawable.app, R.drawable.taskmanager, R.drawable.netmanager,
			R.drawable.trojan, R.drawable.sysoptimize, R.drawable.atools,
			R.drawable.settings };

	private String names[] = { "手机防盗", "通讯卫士", "软件管家", "进程管理", "流量统计", "病毒查杀",
			"缓存清理", "高级工具", "设置中心" };

	private MyAdapter adapter;// gridview的适配器
	private GridView gv_menus;// 主界面的按钮

	private AlertDialog dialog;// 对话框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();// 初始化界面
		initDate();// 初始化数据
		initEvent();// 初始化事件
	}

	/**
	 * 初始化组件的事件
	 */
	private void initEvent() {
		gv_menus.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 判断点击位置
				switch (position) {
				case 4://流量统计
				{
					Intent intent = new Intent(HomeActivity.this,
							ConnectivityActivity.class);
					startActivity(intent);
				}
					break;
					
				case 3://进程管理
				{
					Intent intent = new Intent(HomeActivity.this,
							TastManagerActivity.class);
					startActivity(intent);
				}
					break;
				
				case 2://软件管家
					
				{
					Intent intent = new Intent(HomeActivity.this,
							AppManagerActivity.class);
					startActivity(intent);
				}
					break;
				case 7:// 高级工具
				{
					Intent intent = new Intent(HomeActivity.this,
							AToolActivity.class);
					startActivity(intent);
				}
					break;

				case 1:// 通讯卫士
				{
					Intent intent = new Intent(HomeActivity.this,
							TelSmsSafeActivity.class);
					startActivity(intent);
				}
					break;

				case 8:// 设置中心
					Intent centerIntent = new Intent(HomeActivity.this,
							SettingCenterActivity.class);
					startActivity(centerIntent);
					break;
				case 0:// 手机防盗
						// 是否设置过密码，没有设置密码，弹出设置密码的对话框，如果设置密码，登陆的对话框
					if (TextUtils.isEmpty(SpTools.getString(
							getApplicationContext(), MyConstants.PASSWORD, ""))) {
						// 设置密码对话框
						showSettingPassDialog();
					} else {
						// 直接输入密码对话框
						showEnterPassDialog();
					}

					break;

				default:
					break;
				}
			}
		});
	}

	/**
	 * 登录密码输入对话框
	 */
	protected void showEnterPassDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = View.inflate(getApplicationContext(),
				R.layout.dialog_enter_password, null);

		final EditText et_passone = (EditText) view
				.findViewById(R.id.et_dialog_enter_password_passone);

		Button bt_setpass = (Button) view
				.findViewById(R.id.bt_dialog_enter_password_login);
		Button bt_cancel = (Button) view
				.findViewById(R.id.bt_dialog_enter_password_cancel);

		builder.setView(view);

		bt_setpass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 输入的密码
				String passone = et_passone.getText().toString().trim();

				if (TextUtils.isEmpty(passone)) {
					Toast.makeText(getApplicationContext(), "密码不能为空", 0).show();
					return;
				} else {

					// 密码MD5加密
					passone = Md5Utils.md5(passone);
					// 读取sp中的密码进行判断
					if (passone.equals(SpTools.getString(
							getApplicationContext(), MyConstants.PASSWORD,
							passone))) {
						// 密码相同
						Intent intent = new Intent(HomeActivity.this,
								LostFindActivity.class);
						startActivity(intent);
					} else {
						// 密码不正确
						Toast.makeText(getApplicationContext(), "密码输入错误", 0)
								.show();
						return;
					}
					dialog.dismiss();
				}
			}
		});
		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();// 关闭对话框
			}
		});

		dialog = builder.create();
		dialog.show();
	}

	/**
	 * 显示设置密码对话框
	 */
	protected void showSettingPassDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = View.inflate(getApplicationContext(),
				R.layout.dialog_setting_password, null);

		final EditText et_passone = (EditText) view
				.findViewById(R.id.et_dialog_setting_password_passone);
		final EditText et_passtwo = (EditText) view
				.findViewById(R.id.et_dialog_setting_password_passtwo);
		Button bt_setpass = (Button) view
				.findViewById(R.id.bt_dialog_setting_password_setpass);
		Button bt_cancel = (Button) view
				.findViewById(R.id.bt_dialog_setting_password_cancel);

		builder.setView(view);

		bt_setpass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 设置密码
				String passone = et_passone.getText().toString().trim();
				String passtwo = et_passtwo.getText().toString().trim();
				if (TextUtils.isEmpty(passone) || TextUtils.isEmpty(passtwo)) {
					Toast.makeText(getApplicationContext(), "密码不能为空", 0).show();
					return;
				} else if (!passone.equals(passtwo)) {
					Toast.makeText(getApplicationContext(), "密码不一致", 0).show();
					return;
				} else {
					// 保存数据
					// 密码MD5加密
					passone = Md5Utils.md5(passone);
					SpTools.putString(getApplicationContext(),
							MyConstants.PASSWORD, passone);
					Toast.makeText(getApplicationContext(), "保存成功", 0).show();
					dialog.dismiss();
				}
			}
		});
		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();// 关闭对话框
			}
		});

		dialog = builder.create();
		dialog.show();
	}

	/**
	 * 数据的初始化
	 */
	private void initDate() {
		adapter = new MyAdapter();
		gv_menus.setAdapter(adapter);// 设置gridview适配器数据
	}

	/**
	 * 组件的初始化
	 */
	private void initView() {
		setContentView(R.layout.activity_home);
		gv_menus = (GridView) findViewById(R.id.gv_home_menus);
	}

	@Override
	protected void onResume() {
		// 通知Gridview重新取数据
		adapter.notifyDataSetChanged();
		super.onResume();
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return icons.length;// 图标的个数
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(),
					R.layout.item_home_gridview, null);
			// 获取组件
			ImageView imageView = (ImageView) view
					.findViewById(R.id.iv_item_home_gv_icon);
			TextView textView = (TextView) view
					.findViewById(R.id.tv_item_home_gv_name);
			// 设置数据
			imageView.setImageResource(icons[position]);

			textView.setText(names[position]);

			if (position == 0) {
				// 判断是否存在新的手机防盗名
				if (!TextUtils.isEmpty(SpTools.getString(
						getApplicationContext(), MyConstants.LOSTFINDNAME, ""))) {
					textView.setText(SpTools.getString(getApplicationContext(),
							MyConstants.LOSTFINDNAME, ""));
				}
			}
			return view;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}
}
