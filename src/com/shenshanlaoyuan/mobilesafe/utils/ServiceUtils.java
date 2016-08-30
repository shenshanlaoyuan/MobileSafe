package com.shenshanlaoyuan.mobilesafe.utils;

import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

/**
 * 判断服务的状态
 * @author hp
 *
 */
public class ServiceUtils {

	/**
	 * 
	 * @param context
	 * @param serviceName
	 * 			service完整的名字
	 * @return
	 * 			该servcie是否在运行
	 */			
	public static boolean isServiceRunning(Context context,String serviceName){
		boolean isRunning = false;
		//判断运行中的服务状态，ActivityManager
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		//获取android手机中运行的所有服务
		List<RunningServiceInfo> runningServices = am.getRunningServices(50);
		
		for (RunningServiceInfo runningServiceInfo : runningServices) {
			if(runningServiceInfo.service.getClassName().equals(serviceName)){
				
				isRunning = true ;
				break;
			}
		}
		return isRunning;
	}
	
}
