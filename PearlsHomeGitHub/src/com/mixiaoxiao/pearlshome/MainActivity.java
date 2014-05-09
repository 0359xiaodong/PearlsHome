package com.mixiaoxiao.pearlshome;

import com.mixiaoxiao.android.util.MxxTextUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle(MxxTextUtil.getTypefaceSpannableString(this,  getString(R.string.app_name),MxxTextUtil.Roboto_Light, false));
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == R.id.action_about){
			startActivity(new Intent(this, AboutActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}

}
