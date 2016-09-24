package com.shenshanlaoyuan.mobilesafe.activities;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.domain.AppBean;
import com.shenshanlaoyuan.mobilesafe.engine.AppManagerEngine;


/**
 * 缓存管理界面
 * 
 * @author hp
 * 
 */
public class CacheActivity extends Activity {
	protected static final int SCANNING = 1;
	protected static final int FINISH = 2;
	private ProgressBar pb_loading;
	private LinearLayout ll_mess;

	// 存放缓存信息的容器
	private List<CacheInfo> cacheDatas = new ArrayList<CacheActivity.CacheInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		initView();// 初始化缓存的界面

		scanAllapp();// 变量所有的app，是否有缓存信息
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SCANNING:// 正在扫描
				// 显示进度条
				pb_loading.setVisibility(View.VISIBLE);

				// 隐藏LinearLayout组件
				ll_mess.setVisibility(View.GONE);

				// 隐藏没有数据的组件
				tv_nodata.setVisibility(View.GONE);

				tv_scan_name.setVisibility(View.VISIBLE);
				tv_scan_name.setText("正在扫描:" + msg.obj);
;				
				break;

			case FINISH:// 扫描完成
				// 显示进度条
				pb_loading.setVisibility(View.GONE);
				tv_scan_name.setVisibility(View.GONE);

				//判断是否有缓存
				if (cacheDatas.size() == 0) {
					//没有缓存
					// 隐藏LinearLayout组件
					ll_mess.setVisibility(View.GONE);
					//显示没有缓存数据
					tv_nodata.setVisibility(View.VISIBLE);
					
				} else {
					//有缓存信息
					// 隐藏LinearLayout组件
					ll_mess.setVisibility(View.VISIBLE);
					//显示没有缓存数据
					tv_nodata.setVisibility(View.GONE);
					//取缓存信息，添加到LinearLayout中
					for (CacheInfo info: cacheDatas) {
						//取每条缓存信息
						//显示信息
						View view = View.inflate(getApplicationContext(), R.layout.item_cache_linearlayout_item, null);
						ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_cache_linearlayout_item_icon);
						iv_icon.setImageDrawable(info.icon);//显示图标
						TextView tv_title = (TextView) view.findViewById(R.id.tv_cache_linearlayout_item_title);
						tv_title.setText(info.name);//显示app名字
						TextView tv_cachesize = (TextView) view.findViewById(R.id.tv_cache_linearlayout_cachesize);
						tv_cachesize.setText(info.cacheSize);//显示缓存大小
						
						ll_mess.addView(view, 0);//添加缓存信息的显示
					}
					
				}
							
				break;
			default:
				break;
			}
		};
	};
	private TextView tv_nodata;
	private GetCacheInfo getcacheInfo;
	private PackageManager pm;
	private TextView tv_scan_name;

	private void scanAllapp() {
		new Thread() {
			public void run() {
				// 搜索所有app 查询缓存信息
				// 发送一个显示进度的消息
				handler.obtainMessage(SCANNING).sendToTarget();

				// 遍历所有安装的app ，看是否有缓存
				List<AppBean> allApks = AppManagerEngine
						.getAllApks(getApplicationContext());

				for (AppBean appBean : allApks) {
					// 检查是否有缓存
					//包传递给aidl对象，看是否有缓存
					Message msg = handler.obtainMessage(SCANNING);
					msg.obj = appBean.getPackName();//把包名传递给handler的scanning状态
					handler.sendMessage(msg);
					getAppCacheSize(appBean.getPackName());
					SystemClock.sleep(100);
				}
				
				//扫描完毕
				//发送消息
				handler.obtainMessage(FINISH).sendToTarget();
			};
		}.start();
	}

	private void initView() {
		setContentView(R.layout.activity_cache);

		// 获取缓存信息的进度
		pb_loading = (ProgressBar) findViewById(R.id.pb_cache_scaning_cache);

		// 显示缓存信息的容器
		ll_mess = (LinearLayout) findViewById(R.id.ll_cache_mess);

		// 没有缓存信息的显示
		tv_nodata = (TextView) findViewById(R.id.tv_cache_nodata);
		
		//扫描过程中显示的app名字
		tv_scan_name = (TextView) findViewById(R.id.tv_cache_scanning_appname);

		// 获取缓存的远程aidl对象
		getcacheInfo = new GetCacheInfo();

		// 包管理器
		pm = getPackageManager();
	}

	/**
	 * 清理所有缓存
	 * 
	 * @param v
	 */
	public void clearAll(View v) {
		System.out.println("clear all....");
		Class clazz = pm.getClass();// 获取PackageManager的类类型
		try {
			Method method = clazz.getDeclaredMethod("deleteApplicationCacheFiles",
					String.class, IPackageDataObserver.class);
			// 把包名传递给回调对象
			//getcacheInfo.packName = packageName;
			method.invoke(pm, getPackageName(), new ClearCache());// 结果回调在IPackageStatsObserver的对象中
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	private void getAppCacheSize(String packageName) {

		Class clazz = pm.getClass();// 获取PackageManager的类类型
		try {
			Method method = clazz.getDeclaredMethod("getPackageSizeInfo",
					String.class, IPackageStatsObserver.class);
			// 把包名传递给回调对象
			getcacheInfo.packName = packageName;
			method.invoke(pm, packageName, getcacheInfo);// 结果回调在IPackageStatsObserver的对象中
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 缓存信息的封装类
	 * 
	 * @author Administrator
	 * 
	 */
	private class CacheInfo {
		Drawable icon;
		String name;
		String cacheSize;// 格式化后字符串
	}
	
	/**
	 * 清理缓存
	 * @author Administrator
	 *
	 */
	private class ClearCache extends IPackageDataObserver.Stub{

		@Override
		public void onRemoveCompleted(final String packageName, boolean succeeded)
				throws RemoteException {
			//子线回调结果显示
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), "清理完毕" + packageName, 1).show();
				}
			});
		}
		
	}

	/**
	 * 获取缓存大小
	 * @author Administrator
	 *
	 */
	private class GetCacheInfo extends IPackageStatsObserver.Stub {
		String packName;

		/*
		 * (non-Javadoc) 回调结果
		 * 
		 * @see
		 * android.content.pm.IPackageStatsObserver#onGetStatsCompleted(android
		 * .content.pm.PackageStats, boolean)
		 */
		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			// 判断是否有缓存
			if (pStats.cacheSize > 0) {
				// 有缓存
				// 把app和对应的缓存信息记录(放到容器中)
				// 图标 名字 缓存大小
				CacheInfo info = new CacheInfo();
				try {
					PackageInfo packageInfo = pm.getPackageInfo(packName, 0);
					//设置app的图标
					info.icon = packageInfo.applicationInfo.loadIcon(pm);
					
					//设置app的名字
					info.name = packageInfo.applicationInfo.loadLabel(pm) + "";
					
					//设置缓存的大小 （格式化字符串）
					info.cacheSize = Formatter.formatFileSize(getApplicationContext(), pStats.cacheSize);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 添加一条缓存信息
				cacheDatas.add(info);
			}
		}

	}
}