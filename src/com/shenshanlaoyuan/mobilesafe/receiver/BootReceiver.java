package com.shenshanlaoyuan.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.shenshanlaoyuan.mobilesafe.activities.Setup4Activity;
import com.shenshanlaoyuan.mobilesafe.service.LostFindService;
import com.shenshanlaoyuan.mobilesafe.utils.MyConstants;
import com.shenshanlaoyuan.mobilesafe.utils.SpTools;

/**
 * 开机启动的广播接收者
 * 
 * @author hp
 * 
 */
public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// 手机启动完成，检测SIM卡是否变化
		// 取出原来保存的sim卡信息
		String oldSim = SpTools.getString(context, MyConstants.SIM, "");

		// 取出当前手机的SIM卡信息
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String simSerialNumber = tm.getSimSerialNumber();
		
		// 判断是否变化 
		if (!oldSim.equals(simSerialNumber)) {
			// 取出安全号码
			String safeNumber = SpTools.getString(context,
					MyConstants.SAFENUMBER, "");
			// 发送短信给安全号码
			SmsManager sm = SmsManager.getDefault();
			
			sm.sendTextMessage(safeNumber, "", "SIM卡变化了,手机已被盗", null, null);
		}
		
		//自动开启防盗服务
		if (SpTools.getBoolean(context, MyConstants.LOSTFIND, false)) {
			Intent service = new Intent(context, LostFindService.class);
			context.startService(service);
		} 
	}

}
