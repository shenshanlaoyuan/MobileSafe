package com.shenshanlaoyuan.mobilesafe.service;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;

public class LostFindService extends Service {

	private SmsReceiver smsReceiver;
	private boolean isPlay;// false 音乐播放的标记

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private class SmsReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			// 实现短信拦截功能
			Bundle extras = intent.getExtras();
			// int i = 3

			Object datas[] = (Object[]) extras.get("pdus");
			for (Object data : datas) {
				SmsMessage sm = SmsMessage.createFromPdu((byte[]) data);

				// 获取设备管理器
				DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(context.DEVICE_POLICY_SERVICE);

				String messageBody = sm.getMessageBody();
				if (messageBody.equals("#*gps*#")) {
					// 获取去定位信息
					// 耗时的定位,把定位的功能放到服务中执行
					Intent service = new Intent(context, LocationService.class);
					startService(service);// 启动定位的服务
					abortBroadcast();// 终止广播
				} else if (messageBody.equals("#*lockscreen*#")) {
					// 一键锁屏

					// 设置锁屏密码
					dpm.resetPassword("123", 0);
					dpm.lockNow();
				} else if (messageBody.equals("#*wipedata*#")) {
					// 远程清除数据
					dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
					abortBroadcast();// 终止广播

				} else if (messageBody.equals("#*music*#")) {
					// 只播放一次
					abortBroadcast();
					if (isPlay) {
						return;
					}

					// 远程报警
					// 播放音乐
					MediaPlayer mp = MediaPlayer.create(
							getApplicationContext(),
							com.shenshanlaoyuan.mobilesafe.R.raw.qqqg);
					// 设置左右声道声音为最大值
					mp.setVolume(1, 1);
					mp.start();// 开始播放
					mp.setOnCompletionListener(new OnCompletionListener() {
						
						@Override
						public void onCompletion(MediaPlayer mp) {
							//音乐播放完毕，触发此方法
							isPlay = false;
						}
					});
					isPlay = true;
				}

			}
		}

	}

	@Override
	public void onCreate() {

		// 短信广播接收者
		smsReceiver = new SmsReceiver();
		IntentFilter filter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(Integer.MAX_VALUE);// 设置优先级
		// 注册短信监听
		registerReceiver(smsReceiver, filter);

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		// 取消注册短信的监听广播
		unregisterReceiver(smsReceiver);
		super.onDestroy();
	}
}
