package com.shenshanlaoyuan.mobilesafe.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shenshanlaoyuan.mobilesafe.R;

public abstract class BaseSetupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}
	
	/**
	 * 下一个页面的事件处理
	 * @param v
	 */
	public void next(View v) {
		// 1,完成界面的切换
		nextActivity();
		// 2,动画的播放
		nextAnimation();
	}
	
	/**
	 * 下一个界面显示的动画
	 */
	private void nextAnimation() {
		//第一个参数进来的动画，第二个参数是出去的动画
		overridePendingTransition(R.anim.next_in, R.anim.next_out);
	}

	/**
	 * 上一个页面的事件处理
	 * @param v
	 */
	public void prev(View v) {
		// 1,完成界面的切换
		prevActivity();
		// 2,动画的播放
		prevAnimation();// 界面之间企划的动画

	}

	/**
	 * 上一个界面进来的动画
	 */
	private void prevAnimation() {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.prev_in, R.anim.prev_out);
	}


	public void startActivity(Class type) {
		Intent next = new Intent(this, type);
		startActivity(next);
		finish();// 关闭自己
	}

	public abstract void initView();

	public abstract void nextActivity();

	public abstract void prevActivity();
}
