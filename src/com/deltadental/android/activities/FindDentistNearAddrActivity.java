package com.deltadental.android.activities;


import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;

import com.deltadental.android.R;
 
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
