package com.deltadental.android.activities;


import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.deltadental.android.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.content.Context;
 
public class FindDentistNearAddrActivity extends BaseActivity {
    // Declare Tab Variable
	
	 private FragmentTabHost mTabHost;
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	 // Set the view from main_fragment.xml
	       
	         super.onCreate(savedInstanceState);
	         setContentView(R.layout.activity_find_dentist_near_addr);  
	        
	  
	        // Locate android.R.id.tabhost in main_fragment.xml
	      mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
	 
	        // Create the tabs in main_fragment.xml
	        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);
	 
	        // Create Tab1 with a custom image in res folder
	       mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Current Location"),
	                NearAddrCurrentLocFragment.class, null);
	 
	        // Create Tab2
	        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Near Address"),
	                NearAddrNearAddrFragment.class, null);  
	 
	       
	    }
	  
 
}
