package com.shenshanlaoyuan.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.engine.PhoneLocationEngine;
import com.shenshanlaoyuan.mobilesafe.utils.MyConstants;
import com.shenshanlaoyuan.mobilesafe.utils.SpTools;

/**
 * 显示来电归属地服务
 * 
 * @author hp
 * 
 */
public class ComingPhoneService extends Service {

	private PhoneStateListener listener;
	private TelephonyManager tm;

	private WindowManager.LayoutParams params;
	private WindowManager wm;
	private View view;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {

		// 初始化窗体管理器
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);

		// 初始化土司的参数
		initToastParams();

		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		listener = new PhoneStateListener() {

			@Override
			public void onCallStateChanged(int state,
					final String incomingNumber) {

				switch (state) {

				case TelephonyManager.CALL_STATE_IDLE:// 挂断的状态，空闲的状态
					// 关闭土司
					closeLocationToast();
					break;
				case TelephonyManager.CALL_STATE_RINGING:// 响铃状态
					// 显示土司
					showLocationToast(incomingNumber);

					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:// 通话的状态

					// 关闭土司
					closeLocationToast();
					break;

				default:
					break;
				}
			}
		};

		// 注册电话的监听
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}

	/**
	 * 初始化土司参数
	 */
	private void initToastParams() {

		// 土司的初始化参数
		params = new WindowManager.LayoutParams();
		;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;

		// 对齐方式左上角
		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
		/* | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE */
		| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.format = PixelFormat.TRANSLUCENT;
		// 初始化土司的位置
		params.x = (int) Float.parseFloat(SpTools.getString(
				getApplicationContext(), MyConstants.TOASTX, "0"));
		params.y = (int) Float.parseFloat(SpTools.getString(
				getApplicationContext(), MyConstants.TOASTY, "0"));
		params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;// 土司天生不相应时间,改变类型
		params.setTitle("Toast");
	}

	/**
	 * 关闭自定义土司
	 */
	protected void closeLocationToast() {
		// 初始先执行一次
		if (view != null) {
			wm.removeView(view);
			view = null;// 删除完 置为null
		}

	}
	
	int bgStyles[] = new int[]{R.drawable.call_locate_blue,R.drawable.call_locate_gray,R.drawable.call_locate_green,R.drawable.call_locate_orange,R.drawable.call_locate_white};

	/**
	 * 显示自定义的土司
	 * 
	 * @param incomingNumber
	 *            来电号码
	 */
	protected void showLocationToast(String incomingNumber) {
		// TODO Auto-generated method stub

		view = View.inflate(getApplicationContext(),
				R.layout.sys_toast, null);
		
		//设置归属地样式
		int index = Integer.parseInt(SpTools.getString(getApplicationContext(), MyConstants.STYLEBGINDEX, "0"));
		view.setBackgroundResource(bgStyles[index]);
		
		TextView tv_location = (TextView) view
				.findViewById(R.id.tv_toast_location);
		tv_location.setText(PhoneLocationEngine.Query(incomingNumber,
				getApplicationContext()));

		// 初始化view的触摸事件
		view.setOnTouchListener(new OnTouchListener() {

			private float startX;
			private float startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				System.out.println(event.getX() + ":" + event.getRawX());
				// 拖动土司
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// 按下
					startX = event.getRawX();
					startY = event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:// 按下移动，拖动
					// 新的 x y坐标
					float moveX = event.getRawX();// 移动后的x坐标
					float moveY = event.getRawY();// 移动后的x坐标

					// dx x方向的位置变化值 dy y方向的位置变化值
					float dx = moveX - startX;
					float dy = moveY - startY;
					// 改变土司的坐标
					params.x += dx;
					params.y += dy;
					// 重新获取新的x y坐标
					startX = moveX;
					startY = moveY;

					// 更新土司的位置
					wm.updateViewLayout(view, params);
					break;
				case MotionEvent.ACTION_UP:// 松开
					// 记录当前土司位置,把x y坐标值保存到sp中
					if (params.x < 0) {
						params.x = 0;
					} else if (params.x + view.getWidth() > wm
							.getDefaultDisplay().getWidth()) {
						params.x = wm.getDefaultDisplay().getWidth()
								- view.getWidth();
					}

					if (params.y < 0) {
						params.y = 0;
					} else if (params.y + view.getHeight() > wm
							.getDefaultDisplay().getHeight()) {
						params.y = wm.getDefaultDisplay().getHeight()
								- view.getHeight();
					}
					SpTools.putString(getApplicationContext(),
							MyConstants.TOASTX, params.x + "");
					SpTools.putString(getApplicationContext(),
							MyConstants.TOASTY, params.y + "");

				default:
					break;
				}
				return false;
			}
		});

		wm.addView(view, params);
	}

	@Override
	public void onDestroy() {

		// 注册电话的监听
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		super.onDestroy();
	}

}
