package com.shenshanlaoyuan.mobilesafe.activities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.domain.UrlBean;

public class SplashActivity extends ActionBarActivity {

	private static final int LOADMAIN = 1;// 加载主界面
	private static final int SHOWUPDATEDIALOG = 2;// 弹出更新对话框
	protected static final int ERROR = 3;// 错误
	private RelativeLayout rl_root;// 界面的根布局组件
	private TextView tv_versionName; // 显示版本名的组件
	private UrlBean parseJson;// url信息封装bean
	private int versionCode;// 版本号
	private String versionName;// 版本名称
	private long startTimeMillis;// 开始访问网络的时间
	private ProgressBar pb_download_progress;//下载新版本的进度条

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 初始化界面
		initView();
		// 初始化动画
		initAnimation();
		// 初始化数据
		initDate();

		// 检查服务器的版本
		checkVersion();
	}

	/**
	 * 初始化数据
	 */
	private void initDate() {
		// 获取自己的版本信息
		PackageManager manager = getPackageManager();
		try {
			PackageInfo packageInfo = manager.getPackageInfo(getPackageName(),
					0);
			// 当前版本号
			versionCode = packageInfo.versionCode;
			// 当前版本名称
			versionName = packageInfo.versionName;
			// 设置TextView
			tv_versionName.setText(versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 访问服务器获取最新的版本信息
	 */
	private void checkVersion() {

		// 访问服务器，获取url数据
		// 耗时操作放子线程中执行
		new Thread(new Runnable() {

			private HttpURLConnection conn;
			private BufferedReader bfr;

			@Override
			public void run() {
				// TODO Auto-generated method stub

				int errorCode = -1;// 正常，没有错误
				try {

					startTimeMillis = System.currentTimeMillis();
					URL url = new URL("http://10.0.2.2:8080/version.json");
					conn = (HttpURLConnection) url.openConnection();
					// 设置读取超时时间
					conn.setReadTimeout(5000);
					// 设置网络连接超时时间
					conn.setConnectTimeout(5000);
					// 设置请求方式
					conn.setRequestMethod("GET");

					// 获取相应的结果
					int code = conn.getResponseCode();
					if (code == 200) {
						// 获取字节流
						InputStream is = conn.getInputStream();
						bfr = new BufferedReader(new InputStreamReader(is));
						// 读取一行信息
						String line = bfr.readLine();
						// json字符串数据的封装
						StringBuilder json = new StringBuilder();
						while (line != null) {
							json.append(line);
							line = bfr.readLine();
						}

						// 解析josn数据
						parseJson = parseJson(json);

					} else {
						errorCode = 404;
					}

				} catch (MalformedURLException e) {
					errorCode = 4002;
					e.printStackTrace();

				} catch (IOException e) {
					errorCode = 4001;
					e.printStackTrace();

				} catch (JSONException e) {
					errorCode = 4003;
					e.printStackTrace();
				} finally {

					Message message = Message.obtain();
					if (errorCode == -1) {
						// 是否有新版本
						message.what = isNewVersion(parseJson);
					} else {
						message.what = ERROR;
						message.arg1 = errorCode;
					}
					//保证Splash界面停留不少于3秒
					long endTimeMillis = System.currentTimeMillis();// 结束时间
					if (endTimeMillis - startTimeMillis < 3000) {
						// 设置休眠时间，保证至少睡3秒
						SystemClock
								.sleep(3000 - (endTimeMillis - startTimeMillis));
					}
					handler.sendMessage(message);
					try {

						// 关闭连接资源
						if (bfr == null || conn == null) {
							return;
						}
						bfr.close();
						conn.disconnect();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}

		}).start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 处理消息
			switch (msg.what) {
			case LOADMAIN: // 加载主界面

				loadMain();
				break;
			case ERROR:// 有异常
				switch (msg.arg1) {
				case 404:// 资源找不到
					Toast.makeText(getApplicationContext(), "404资源找不到", 0)
							.show();
					break;
				case 4001:// 找不到网络
					Toast.makeText(getApplicationContext(), "4001没有网络", 0)
							.show();
					break;
				case 4003:// json格式错误
					Toast.makeText(getApplicationContext(), "4003json格式错误", 0)
							.show();
					break;
				default:
					break;
				}
				loadMain();

				break;
			case SHOWUPDATEDIALOG:// 显示更新版本的对话框
				showUpdateDialog();
				break;
			default:
				break;
			}

		}

	};
	

	/**
	 * 加载主界面
	 */
	private void loadMain() {
		Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 显示是否更新的对话框
	 */
	private void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提醒")
				.setMessage("是否更新新版本？新版本更新内容如下" + parseJson.getDesc())
				.setPositiveButton("更新", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						System.out.println("更新apk");
						// 下载新的APK
						downLoadNewApk();
					}

				}).setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 进入主界面
						loadMain();
					}
				}).setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// 后退键取消的处理，进入主界面
						loadMain();
					}
				});
		builder.show();// 显示对话框

	};

	/**
	 * 新版本的下载安装
	 */
	private void downLoadNewApk() {

		// 使用xUtils下载
		HttpUtils utils = new HttpUtils();
		System.out.println(parseJson.getUrl());

		File file = new File("/mnt/sdcard/xx.apk");
		file.delete();// 删除文件

		utils.download(parseJson.getUrl(), "/mnt/sdcard/xx.apk",
				new RequestCallBack<File>() {
			
					

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						pb_download_progress.setVisibility(View.VISIBLE);//设置进度的显示
						pb_download_progress.setMax((int) total);//设置进度条的最大值
						pb_download_progress.setProgress((int) current);//设置当前进度
						super.onLoading(total, current, isUploading);
						
					}

					@Override
					public void onSuccess(ResponseInfo<File> arg0) {
						// 下载成功
						Toast.makeText(getApplicationContext(), "下载新版本成功", 1)
								.show();
						installApk();
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// 下载失败
						Toast.makeText(getApplicationContext(), "下载新版本失败", 1)
								.show();
					}
				});
	}

	/**
	 * 安装Apk
	 */
	private void installApk() {
		/*
		 * <intent-filter> <action android:name="android.intent.action.VIEW" />
		 * <category android:name="android.intent.category.DEFAULT" /> <data
		 * android:scheme="content" /> <data android:scheme="file" /> <data
		 * android:mimeType="application/vnd.android.package-archive" />
		 * </intent-filter>
		 */
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		Uri data = Uri.fromFile(new File("/mnt/sdcard/xx.apk"));
		String type = "application/vnd.android.package-archive";
		intent.setDataAndType(data, type);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// 如果用户取消更新的安装，直接进入主界面
		loadMain();
		super.onActivityResult(arg0, arg1, arg2);
	}

	/**
	 * 在子线程中进行
	 * 
	 * @param parseJson
	 */
	private int isNewVersion(UrlBean parseJson) {

		int serverCode = parseJson.getVersionCode();// 获取服务器版本

		if (serverCode == versionCode) {// 版本相同，进入主界面

			return LOADMAIN;
		} else {

			return SHOWUPDATEDIALOG;

		}
	}

	/**
	 * 
	 * @param json
	 *            url的json数据
	 * @return 信息封装对象
	 * @throws JSONException
	 */
	private UrlBean parseJson(StringBuilder json) throws JSONException {
		UrlBean bean = new UrlBean();
		JSONObject jsonObj;

		jsonObj = new JSONObject(json + "");
		int versionCode = jsonObj.getInt("version");
		String url = jsonObj.getString("url");
		String desc = jsonObj.getString("desc");
		// 封装数据
		bean.setVersionCode(versionCode);
		bean.setUrl(url);
		bean.setDesc(desc);

		return bean;
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

		// 创建动画集
		AnimationSet set = new AnimationSet(true);
		set.addAnimation(alpha);
		set.addAnimation(rotate);
		set.addAnimation(scale);

		// 显示动画
		rl_root.setAnimation(set);

	}

	/**
	 * 初始化界面
	 */
	private void initView() {

		setContentView(R.layout.activity_splash);
		rl_root = (RelativeLayout) findViewById(R.id.rl_splash_root);
		tv_versionName = (TextView) findViewById(R.id.tv_splash_version_name);
		pb_download_progress = (ProgressBar) findViewById(R.id.pb_splash_download_progress);

	}

}
