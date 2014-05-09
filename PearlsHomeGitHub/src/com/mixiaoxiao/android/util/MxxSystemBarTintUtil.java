package com.mixiaoxiao.android.util;



import com.mixiaoxiao.pearlshome.R;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

public class MxxSystemBarTintUtil {
	public static void setSystemBarTintColor(Activity activity){
		if(SystemBarTintManager.isKitKat()){
			SystemBarTintManager tintManager = new SystemBarTintManager(activity);  
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintDrawable(new ColorDrawable(activity.getResources().getColor(R.color.mxx_item_theme_color_alpha)));
		}
	}
	
	public static void insertPadding(View rootView, Activity activity){
		insertPadding(rootView, 0, activity);
	}
	
	public static void insertPadding(View rootView,int margin_top_dp, Activity activity){
		SystemBarTintManager tintManager = new SystemBarTintManager(activity);  
		SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();  
		rootView.setPadding(0, config.getPixelInsetTop(true) + MxxUiUtil.dip2px(activity, margin_top_dp), config.getPixelInsetRight(), config.getPixelInsetBottom());
		rootView.requestLayout();
	}
	
}
