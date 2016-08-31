package com.shenshanlaoyuan.mobilesafe.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.utils.MyConstants;
import com.shenshanlaoyuan.mobilesafe.utils.SpTools;

public class LostFindActivity extends Activity {
	private AlertDialog dialog;
	private View popupView;
	private PopupWindow pw;
	private ScaleAnimation sa;
	private RelativeLayout rl_root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 如果第一次访问该界面，要先进入设置向导界面
		if (SpTools.getBoolean(getApplicationContext(), MyConstants.ISSETUP,
				false)) {
			// 进入过设置向导界面
			initView();
			initPopupView();// 初始化修改名字的界面
			initPopupWindow();// 初始化弹出窗体

		} else {
			// 要进入设置向导界面
			Intent intent = new Intent(this, Setup1Activity.class);
			startActivity(intent);
			finish();// 关闭自己
		}

	}

	/**
	 * 
	 */
	private void initPopupWindow() {

		// 弹出窗体
		pw = new PopupWindow(popupView, -2, -2);
		pw.setFocusable(true);//获取焦点

		// 窗体显示的动画
		sa = new ScaleAnimation(1, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(1000);

	}

	/**
	 * 重新进入设置向导界面
	 * 
	 * @param view
	 */
	public void enterSetup(View view) {
		Intent intent = new Intent(this, Setup1Activity.class);
		startActivity(intent);
		finish();

	}

	private void initView() {
		setContentView(R.layout.activity_lostfind);
		rl_root = (RelativeLayout) findViewById(R.id.rl_lostfind_root);
	}

	/**
	 * 创建菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//getMenuInflater().inflate(R.menu.splash, menu);

		return true;
	}

	/**
	 * 处理菜单事件
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.mn_modify_name:
			Toast.makeText(getApplicationContext(), "修改菜单名", 1).show();
			// 弹出对话框，让用户输入新的手机防盗名
			showModifyNameDialog();
			break;
		case R.id.mn_test_menu:
			Toast.makeText(getApplicationContext(), "测试", 1).show();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	/**
	 * 修改手机防盗名的对话框
	 */
	private void showModifyNameDialog() {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);

		dialog = ab.create();
		dialog.show();// 显示对话框
	}

	private void initPopupView() {
		popupView = View.inflate(getApplicationContext(),
				R.layout.dialog_modify_name, null);
		// 处理界面和事件
		final EditText et_name = (EditText) popupView
				.findViewById(R.id.et_dialog_lostfind_modify_name);
		Button bt_modify = (Button) popupView
				.findViewById(R.id.bt_dialog_lostfind_modify);
		Button bt_cancel = (Button) popupView
				.findViewById(R.id.bt_dialog_lostfind_cancel);

		// 处理按钮事件
		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pw.dismiss();
			}
		});

		bt_modify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获取修改的名字
				String name = et_name.getText().toString().trim();
				if (TextUtils.isEmpty(name)) {
					Toast.makeText(getApplicationContext(), "名字不能为空", 1).show();
					return;
				}
				// 保存新名字到sp中
				SpTools.putString(getApplicationContext(),
						MyConstants.LOSTFINDNAME, name);
				pw.dismiss();
				Toast.makeText(getApplicationContext(), "修改防盗名成功", 1).show();
			}
		});

	}

	// 处理menu键的事件
	@SuppressWarnings("deprecation")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (pw != null && pw.isShowing()) {
				pw.dismiss();
			} else {

				// 设置弹出窗体的背景
				pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				popupView.startAnimation(sa);
				// 设置弹出窗体显示的位置
				int height = getWindowManager().getDefaultDisplay().getHeight();
				int width = getWindowManager().getDefaultDisplay().getWidth();

				pw.showAtLocation(rl_root, Gravity.LEFT | Gravity.TOP, width / 4,
						height / 4);

			}
		}// end public boolean onKeyDown(int keyCode, KeyEvent event) {

		/*
		 * if (keyCode == KeyEvent.KEYCODE_MENU) { if (isShowMenu) { // 显示菜单
		 * ll_bottom_menu.setVisibility(View.VISIBLE); } else { // 不显示菜单
		 * ll_bottom_menu.setVisibility(View.GONE); } isShowMenu = !isShowMenu;
		 * }
		 */
		return super.onKeyDown(keyCode, event);
	}
}
