package com.shenshanlaoyuan.mobilesafe.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
	private List<BlackBean> moreDatas;// 分批加载的容器

	private static final int MOREDATASCOUNTS = 20;// 分批加载的数据个数

	// 存放黑名单数据的容器
	private List<BlackBean> datas = new ArrayList<BlackBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();// 初始化界面
		initDate();// 初始化数据
		initEvent();// 初始化事件
	}

	/**
	 * 给每个组件设置事件
	 */
	private void initEvent() {
		// TODO Auto-generated method stub

		/* 给ListView设置滑动事件 */
		lv_safenumbers.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 状态改变调用
				/*
				 * SCROLL_STATE_FLING:惯性滑动 SCROLL_STATE_IDLE: 滑动停止
				 * SCROLL_STATE_TOUCH_SCROLL: 按住滑动
				 */

				// 监控静止状态SCROLL_STATE_IDLE
				// 当出现SCROLL_STATE_IDLE的状态时候，判断是否显示最后一条数据，如果显示最后一条数据，那就加载更多的数据
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 获取最后显示的数据位置
					int lastVisiblePosition = lv_safenumbers
							.getLastVisiblePosition();

					if (lastVisiblePosition == datas.size() - 1) {
						initDate();
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// 按住滑动触发

			}
		});

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

				if (moreDatas.size() != 0) {
					// 显示listview
					lv_safenumbers.setVisibility(View.VISIBLE);

					// 隐藏没有数据
					tv_nodata.setVisibility(View.GONE);

					// 隐藏加载数据的进度
					pb_loading.setVisibility(View.GONE);

					// 更新数据
					adapter.notifyDataSetChanged();// 通知listview重新去adapter中的数据

				} else {

					if (datas.size() != 0) {// 分批加载数据，没有更多数据
						Toast.makeText(getApplicationContext(), "没有更多数据", 1)
								.show();
						return;
					}

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
	private MyAdapter adapter;
	private AlertDialog dialog;

	private void initDate() {
		// TODO Auto-generated method stub

		new Thread() {

			public void run() {

				// 取数据之前，发个消息显示正在加载数据的进度条
				handler.obtainMessage(LOADING).sendToTarget();
				moreDatas = dao.getMoreDatas(MOREDATASCOUNTS, datas.size());

				datas.addAll(moreDatas);// 把一个容器所有数据加进来

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

		// 黑名单业务对象
		dao = new BlackDao(getApplicationContext());

		adapter = new MyAdapter();
		lv_safenumbers.setAdapter(adapter);
	}

	private class ItemView {
		// 显示黑名单号码
		TextView tv_phone;

		// 显示黑名单号码拦截模式
		TextView tv_mode;

		// 删除黑名单数据的 按钮
		ImageView iv_delete;
	}

	/**
	 * 添加黑名单号码
	 * 
	 * @param view
	 */
	public void addBlackNumber(View v) {
		AlertDialog.Builder ab = new AlertDialog.Builder(
				TelSmsSafeActivity.this);
		View view = View.inflate(getApplicationContext(),
				R.layout.dialog_addblacknumber, null);

		// 黑名单号码编辑框
		final EditText et_blackNumber = (EditText) view
				.findViewById(R.id.et_telsmssafe_blacknumber);

		// 短信拦截复选框
		final CheckBox cb_sms = (CheckBox) view
				.findViewById(R.id.cb_telsmssafe_smsmode);

		// 电话拦截复选框
		final CheckBox cb_phone = (CheckBox) view
				.findViewById(R.id.cb_telsmssafe_phonemode);

		// 添加黑名单号码按钮
		Button bt_add = (Button) view.findViewById(R.id.bt_telsmssafe_add);

		// 取消添加黑名单号码按钮
		Button bt_cancel = (Button) view
				.findViewById(R.id.bt_telsmssafe_cancel);

		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 取消添加黑名单
				dialog.dismiss();
			}
		});

		bt_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 添加黑名单号码

				String phone = et_blackNumber.getText().toString().trim();

				if (TextUtils.isEmpty(phone)) {
					Toast.makeText(getApplicationContext(), "号码不能为空", 1).show();
					return;
				}
				if (!cb_phone.isChecked() && !cb_sms.isChecked()) {
					Toast.makeText(getApplicationContext(), "至少要勾选一个复选框", 1)
							.show();
					return;
				}

				int mode = 0;
				if (cb_phone.isChecked()) {
					mode |= BlackTable.TEL;//设置电话拦截模式
				}

				if (cb_sms.isChecked()) {
					mode |= BlackTable.SMS;//设置短信拦截模式
				}
				BlackBean bean = new BlackBean();
				bean.setPhone(phone);
				bean.setMode(mode);
				
				dao.add(bean);//添加数据到黑名单表中
				
				datas.remove(bean);// 该删除方法要靠equals和hashCode两个方法共同判断数据是否一致
				
				datas.add(0, bean);//添加数据到容器中
				
				//listview 显示第一条数据
				//lv_safenumbers.setSelection(0);
				adapter = new MyAdapter();
				lv_safenumbers.setAdapter(adapter);
				
				dialog.dismiss();
				
			}
		});
		ab.setView(view);

		dialog = ab.create();
		dialog.show();

	}

	private class MyAdapter extends BaseAdapter {

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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ItemView itemView = null;
			if (convertView == null) {

				convertView = View.inflate(getApplicationContext(),
						R.layout.item_telsmssafe_listview, null);

				itemView = new ItemView();

				itemView.tv_phone = (TextView) convertView
						.findViewById(R.id.tv_telsmssafe_listview_item_number);

				itemView.tv_mode = (TextView) convertView
						.findViewById(R.id.tv_telsmssafe_listview_item_mode);

				itemView.iv_delete = (ImageView) convertView
						.findViewById(R.id.iv_telsmssafe_listview_item_delete);

				// 设置标记给convertView
				convertView.setTag(itemView);
			} else {
				// 存在缓存
				itemView = (ItemView) convertView.getTag();
			}
			// 获取当前位置的数据
			final BlackBean bean = datas.get(position);

			itemView.tv_phone.setText(bean.getPhone());// 显示黑名单号码

			// 设置黑名单的模式
			switch (bean.getMode()) {
			case BlackTable.SMS:// 短信拦截
				itemView.tv_mode.setText("短信拦截");
				break;
			case BlackTable.TEL:// 电话拦截
				itemView.tv_mode.setText("电话拦截");
				break;
			case BlackTable.ALL:// 全部拦截
				itemView.tv_mode.setText("全部拦截");
				break;

			default:
				break;
			}
			// 删除一条数据
			itemView.iv_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					AlertDialog.Builder ab = new AlertDialog.Builder(
							TelSmsSafeActivity.this);
					ab.setTitle("注意");
					ab.setMessage("真的要删除吗？");
					ab.setPositiveButton("删除",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									// 从数据库中删除当前
									dao.delete(bean.getPhone());
									// 从容器中删除对应数据
									datas.remove(position);

									// 通知界面更新数据，让用户看到删除数据不存在
									adapter.notifyDataSetChanged();// 只是让listview重新去当前显示位置的数据
								}
							});

					ab.setNegativeButton("取消", null);
					ab.show();
				}
			});

			return convertView;
		}

	}
}
