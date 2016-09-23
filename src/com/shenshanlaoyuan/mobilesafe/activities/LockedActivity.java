package com.shenshanlaoyuan.mobilesafe.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.fragment.LockedFragment;
import com.shenshanlaoyuan.mobilesafe.fragment.UnlockedFragment;

/**
 * 程序锁界面
 * 
 * @author hp
 * 
 */
public class LockedActivity extends FragmentActivity {

	private TextView tv_locked;
	private TextView tv_unlock;
	private FrameLayout fl_locked;
	private Fragment lockedFragment;
	private Fragment unlockFragment;
	private FragmentManager fm;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		super.onCreate(arg0);
		initView();
		initData();
		initEvent();
	}

	private void initEvent() {
		OnClickListener listener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				FragmentTransaction transaction = fm.beginTransaction();
				
				
				
				if (v.getId()==R.id.tv_lockedactivity_locked) {
					transaction.replace(R.id.fl_lockedactivity_content, lockedFragment);
					tv_locked.setBackgroundResource(R.drawable.tab_right_pressed);//按下
					tv_unlock.setBackgroundResource(R.drawable.tab_left_default);//默认 不按下
					
				} else {
					transaction.replace(R.id.fl_lockedactivity_content, unlockFragment);
					tv_locked.setBackgroundResource(R.drawable.tab_right_default);//不按下
					tv_unlock.setBackgroundResource(R.drawable.tab_left_pressed);//按下
				}
				
				transaction.commit();
			}
			
		};
		
		tv_locked.setOnClickListener(listener);
		tv_unlock.setOnClickListener(listener);
		
	}

	private void initData() {
		// fragment的管理器
		fm = getSupportFragmentManager();
		// 1，获取事物
		FragmentTransaction transaction = fm.beginTransaction();
		// 2,替换,默认显示未加锁的界面,把未加锁的fragment替换掉framelayout
		transaction.replace(R.id.fl_lockedactivity_content, unlockFragment);
		// 3,提交事物
		transaction.commit();
	}

	private void initView() {
		setContentView(R.layout.activity_lock);

		tv_locked = (TextView) findViewById(R.id.tv_lockedactivity_locked);
		tv_unlock = (TextView) findViewById(R.id.tv_lockedactivity_unlock);
		fl_locked = (FrameLayout) findViewById(R.id.fl_lockedactivity_content);
		lockedFragment = new LockedFragment();
		unlockFragment = new UnlockedFragment();
		

	}

}
