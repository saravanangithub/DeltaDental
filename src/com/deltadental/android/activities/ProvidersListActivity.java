package com.deltadental.android.activities;

import com.deltadental.android.R;
import com.deltadental.android.R.layout;
import com.deltadental.android.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ProvidersListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_providers_list);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.providers_list, menu);
		return true;
	}

}
