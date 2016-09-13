package com.shenshanlaoyuan.mobilesafe.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenshanlaoyuan.mobilesafe.R;

public class SettingCenterItemView extends LinearLayout {

	private TextView tv_title;
	private TextView tv_content;
	private CheckBox cb_check;
	private String[] contents;
	private View child;

	public SettingCenterItemView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public SettingCenterItemView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initView();
		initEvent();

		String content = attrs
				.getAttributeValue(
						"http://schemas.android.com/apk/res/com.shenshanlaoyuan.mobilesafe",
						"mcontent");

		String title = attrs
				.getAttributeValue(
						"http://schemas.android.com/apk/res/com.shenshanlaoyuan.mobilesafe",
						"mtitle");
		//System.out.println(content + title);
		tv_title.setText(title);
		contents = content.split("-");
		// 初始化设置未选中的颜色为红色
		tv_content.setTextColor(Color.RED);
		tv_content.setText(contents[0]);

	}

	/**
	 * 设置item中CheckBox的状态
	 * 
	 * @param isChecked
	 */
	public void setChecked(boolean isChecked) {
		cb_check.setChecked(isChecked);
	}

	/**
	 * item中CheckBox的状态
	 * 
	 * @return
	 */
	public boolean isChecked() {
		return cb_check.isChecked();
	}

	/**
	 * item根布局设置点击事件
	 * 
	 * @param listener
	 */
	public void setItemClickListener(OnClickListener listener) {
		child.setOnClickListener(listener);
	}

	/**
	 * 初始化复选框事件
	 */
	private void initEvent() {
		cb_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					// 设置选中的颜色
					tv_content.setTextColor(Color.GREEN);
					tv_content.setText(contents[1]);

				} else {

					tv_content.setTextColor(Color.RED);
					tv_content.setText(contents[0]);
				}
			}
		});

		/*
		 * child.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub cb_check.setChecked(!cb_check.isChecked()); } });
		 */

	}

	/**
	 * 初始化LinearLayout子组件
	 */
	private void initView() {
		child = View.inflate(getContext(), R.layout.item_settingcenter_view,
				null);
		tv_title = (TextView) child
				.findViewById(R.id.tv_settingcenter_autoupdate_title);
		tv_content = (TextView) child
				.findViewById(R.id.tv_settingcenter_autoupdate_content);
		cb_check = (CheckBox) child
				.findViewById(R.id.cb_settingcenter_autoupdate_checked);

		addView(child);

	}

	public SettingCenterItemView(Context context) {
		super(context);
		initView();
		// TODO Auto-generated constructor stub
	}

}
