package com.shenshanlaoyuan.mobilesafe.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.dao.BlackDao;
import com.shenshanlaoyuan.mobilesafe.domain.BlackBean;
import com.shenshanlaoyuan.mobilesafe.domain.BlackTable;

public class TelSmsSafeActivity extends Activity {
	protected static final int LOADING = 1;
	protected static final int FINISH = 2;
	private ListView lv_safenumbers;
	private Button bt_addSafeNumber;
	private TextView tv_nodata;
	private ProgressBar pb_loading;

	// 存放黑名单数据的容器
	private List<BlackBean> datas = new ArrayList<BlackBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();// 初始化界面
		initDate();// 初始化数据
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOADING:// 正在加载数据
				// 显示加载数据的进度
				pb_loading.setVisibility(View.VISIBLE);

				// 隐藏listview
				lv_safenumbers.setVisibility(View.GONE);

				// 隐藏没有数据
				tv_nodata.setVisibility(View.GONE);
				break;
			case FINISH:// 数据加载完成
				// 判断是否有数据
				// 有数据

				if (datas.size() != 0) {
					// 显示listview
					lv_safenumbers.setVisibility(View.VISIBLE);

					// 隐藏没有数据
					tv_nodata.setVisibility(View.GONE);

					// 隐藏加载数据的进度
					pb_loading.setVisibility(View.GONE);

				} else {

					// 没有数据
					// 隐藏listview
					lv_safenumbers.setVisibility(View.GONE);

					// 显示没有数据
					tv_nodata.setVisibility(View.VISIBLE);

					// 隐藏加载数据的进度
					pb_loading.setVisibility(View.GONE);
				}

				break;
			default:
				break;
			}
		};
	};
	private BlackDao dao;

	private void initDate() {
		// TODO Auto-generated method stub

		new Thread() {
			public void run() {

				// 取数据之前，发个消息显示正在加载数据的进度条
				handler.obtainMessage(LOADING).sendToTarget();
				// 取出数据
				datas = dao.getAllDatas();
				// 取数据完成，发消息通知取数据完成
				handler.obtainMessage(FINISH).sendToTarget();
			};
		}.start();
	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_telsmssafe);
		// 显示安全号码listview
		lv_safenumbers = (ListView) findViewById(R.id.lv_telsms_safenumbers);

		// 添加黑名单数据的按钮
		bt_addSafeNumber = (Button) findViewById(R.id.bt_telsms_addsafenumber);

		// 没有数据显示的文本
		tv_nodata = (TextView) findViewById(R.id.tv_telsms_nodata);

		// 正在加载数据的进度
		pb_loading = (ProgressBar) findViewById(R.id.pb_telsms_loading);
		
		//黑名单业务对象
		dao = new BlackDao(getApplicationContext());
		
		//黑名单的适配器
		MyAdapter adapter = new MyAdapter();
		lv_safenumbers.setAdapter(adapter);
	}
	
	private class ItemView{
		//显示黑名单号码
		TextView tv_phone ;
		
		//显示黑名单号码拦截模式
		TextView tv_mode ;
		
		//删除黑名单数据的 按钮
		ImageView iv_delete;
	}
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return datas.size();
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ItemView itemView = null;
			if (convertView == null) {
				
				convertView = View.inflate(getApplicationContext(), R.layout.item_telsmssafe_listview, null);
				
				itemView = new ItemView();
				
				itemView.tv_phone = (TextView) convertView
						.findViewById(R.id.tv_telsmssafe_listview_item_number);
				
				itemView.tv_mode = (TextView) convertView
						.findViewById(R.id.tv_telsmssafe_listview_item_mode);
				
				itemView.iv_delete = (ImageView) convertView
						.findViewById(R.id.iv_telsmssafe_listview_item_delete);
				
				//设置标记给convertView
				convertView.setTag(itemView);
			} else {
				//存在缓存
				itemView = (ItemView) convertView.getTag();
			}
			//获取当前位置的数据
			BlackBean bean = datas.get(position);
			
			itemView.tv_phone.setText(bean.getPhone());//显示黑名单号码
			
			//设置黑名单的模式
			switch (bean.getMode()) {
			case BlackTable.SMS://短信拦截
				itemView.tv_mode.setText("短信拦截");
				break;
			case BlackTable.TEL://电话拦截
				itemView.tv_mode.setText("电话拦截");
				break;
			case BlackTable.ALL://全部拦截
				itemView.tv_mode.setText("全部拦截");
				break;

			default:
				break;
			}
			
			return convertView;
		}
		
	}
}
