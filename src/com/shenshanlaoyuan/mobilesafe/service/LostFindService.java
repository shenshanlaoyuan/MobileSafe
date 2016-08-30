package com.shenshanlaoyuan.mobilesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;

public class LostFindService extends Service {

	private SmsReceiver smsReceiver;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private class SmsReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			//实现短信拦截功能
			Bundle extras = intent.getExtras();
		    // int i = 3 
			
			 
			Object datas[] = (Object[]) extras.get("pdus");
			for (Object data:datas){
				SmsMessage sm = SmsMessage.createFromPdu((byte[]) data);
				System.out.println(sm.getMessageBody() + ":" + sm.getOriginatingAddress());
				
			}
		}
		
	}

	@Override
	public void onCreate() {
		
		//短信广播接收者
		smsReceiver = new SmsReceiver();
		IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(Integer.MAX_VALUE);//设置优先级
		//注册短信监听
		registerReceiver(smsReceiver, filter);
				
		super.onCreate();
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//取消注册短信的监听广播
		unregisterReceiver(smsReceiver);
		super.onDestroy();
	}
}
