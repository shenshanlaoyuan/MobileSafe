package com.shenshanlaoyuan.mobilesafe.activities;

import com.shenshanlaoyuan.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 主界面
 * 
 * @author hp
 * 
 */
public class HomeActivity extends Activity {

	private int icons[] = { R.drawable.safe, R.drawable.callmsgsafe,
			R.drawable.app, R.drawable.taskmanager, R.drawable.netmanager,
			R.drawable.trojan, R.drawable.sysoptimize, R.drawable.atools,
			R.drawable.settings };

	private String names[] = { "手机防盗", "通讯卫士", "软件管家", "进程管理", "流量统计", "病毒查杀",
			"缓存清理", "高级工具", "设置中心" };

	private MyAdapter adapter;// gridview的适配器
	private GridView gv_menus;// 主界面的按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();// 初始化界面
		initDate();// 初始化数据
	}

	/**
	 * 数据的初始化
	 */
	private void initDate() {
		adapter = new MyAdapter();
		gv_menus.setAdapter(adapter);// 设置gridview适配器数据
	}

	/**
	 * 组件的初始化
	 */
	private void initView() {
		setContentView(R.layout.activity_home);
		gv_menus = (GridView) findViewById(R.id.gv_home_menus);
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return icons.length;// 图标的个数
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(),
					R.layout.item_home_gridview, null);
			// 获取组件
			ImageView imageView = (ImageView) view
					.findViewById(R.id.iv_item_home_gv_icon);
			TextView textView = (TextView) view
					.findViewById(R.id.tv_item_home_gv_name);
			// 设置数据
			imageView.setImageResource(icons[position]);
			textView.setText(names[position]);
			return view;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}
}
