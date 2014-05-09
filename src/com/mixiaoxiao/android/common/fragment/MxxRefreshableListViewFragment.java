package com.mixiaoxiao.android.common.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mixiaoxiao.android.util.MxxSystemBarTintUtil;
import com.mixiaoxiao.android.view.MxxRefreshableListView;
import com.mixiaoxiao.pearlshome.R;

public class MxxRefreshableListViewFragment extends Fragment{
	
	protected MxxRefreshableListView mListView;
//	protected Context mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mListView = (MxxRefreshableListView) inflater.inflate(R.layout.mxx_base_refreshable_listview, null);
		MxxSystemBarTintUtil.insertPadding(mListView, getActivity());
		return mListView;
	}
}
