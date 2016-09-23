package com.shenshanlaoyuan.mobilesafe.activities;


import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shenshanlaoyuan.mobilesafe.R;
import com.shenshanlaoyuan.mobilesafe.domain.ContantBean;
import com.shenshanlaoyuan.mobilesafe.engine.ReadContantsEngine;
import com.shenshanlaoyuan.mobilesafe.utils.MyConstants;


/**
 * @author hp
 * 显示所有好友信息的界面
 */
public class FriendsActivity extends BaseFriendsCallSmsActivity {

	@Override
	public List<ContantBean> getDatas() {
		// TODO Auto-generated method stub
		return ReadContantsEngine.readContants(getApplicationContext());
	}
	
}
