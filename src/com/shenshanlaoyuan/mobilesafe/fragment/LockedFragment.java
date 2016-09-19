package com.shenshanlaoyuan.mobilesafe.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LockedFragment extends Fragment {

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		TextView tv = new TextView(getActivity());
		
		tv.setText("已加锁的fragment");
		tv.setGravity(Gravity.CENTER);
		return tv;
	}
	
}
