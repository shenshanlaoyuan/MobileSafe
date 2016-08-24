package com.shenshanlaoyuan.mobilesafe.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenshanlaoyuan.mobilesafe.R;

public class SplashActivity extends ActionBarActivity {

	private RelativeLayout rl_root;
	private TextView tv_versionName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 初始化界面
		initView();
		// 初始化动画
		initAnimation();
	}

	/**
	 * 动画显示
	 */
	private void initAnimation() {

		// Alpha动画
		AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
		// 设置动画显示时间
		alpha.setDuration(3000);
		// 界面停留在结束状态
		alpha.setFillAfter(true);

		// 旋转动画
		RotateAnimation rotate = new RotateAnimation(0, 360,
				// 设置瞄点
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// 设置动画显示时间
		rotate.setDuration(3000);
		// 界面停留在结束状态
		rotate.setFillAfter(true);

		// 缩放动画
		ScaleAnimation scale = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// 设置动画显示时间
		scale.setDuration(3000);
		// 界面停留在结束状态
		scale.setFillAfter(true);

		
		//创建动画集
		AnimationSet set = new AnimationSet(true);
		set.addAnimation(alpha);
		set.addAnimation(rotate);
		set.addAnimation(scale);
		
		//显示动画
		rl_root.setAnimation(set);

	}

	/**
	 * 初始化界面
	 */
	private void initView() {

		setContentView(R.layout.activity_splash);
		rl_root = (RelativeLayout) findViewById(R.id.rl_splash_root);
		tv_versionName = (TextView) findViewById(R.id.tv_splash_version_name);

	}

}
