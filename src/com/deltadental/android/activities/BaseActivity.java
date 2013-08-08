package com.deltadental.android.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;  
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.deltadental.android.R;
import com.deltadental.android.adapters.MenuListAdapter;
 
public class BaseActivity extends SherlockFragmentActivity {
 
    // Declare Variable
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    MenuListAdapter mMenuAdapter; 
    String[] title; 
    String[] subtitle;
    int[] icon;
    
    //Fragment fragment1 = new Fragment1();
  //  Fragment fragment2 = new Fragment2();
  
   protected LinearLayout fullLayout;
   protected FrameLayout actContent;
 
   @Override
   public void setContentView(final int layoutResID) {
       fullLayout= (LinearLayout) getLayoutInflater().inflate(R.layout.drawer_main, null); // Your base layout here
       actContent= (FrameLayout) fullLayout.findViewById(R.id.content_frame);
       getLayoutInflater().inflate(layoutResID, actContent, true); // Setting the content of layout your provided to the act_content frame
       super.setContentView(fullLayout); 
       // here you can get your drawer buttons and define how they should behave and what must they do, so you won't be needing to repeat it in every activity class
       
       // Generate title
       title = new String[] { "Home", "Find Dentist Near Me","Find Dentist Near Address",
    		                  "Benefits & Elgiblity","Check Claims","Local Dental Services"};

       // Generate subtitle
       //subtitle = new String[] { "Subtitle Fragment 1", "Subtitle Fragment 2"};

       // Generate icon
      // icon = new int[] { R.drawable.action_about, R.drawable.action_settings};

       // Locate DrawerLayout in drawer_main.xml
       mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

       // Locate ListView in drawer_main.xml
       mDrawerList = (ListView) findViewById(R.id.left_drawer);

       // Set a custom shadow that overlays the main content when the drawer
       // opens
       mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
               GravityCompat.START);

       // Pass results to MenuListAdapter Class
       mMenuAdapter = new MenuListAdapter(this, title); 

       // Set the MenuListAdapter to the ListView
       mDrawerList.setAdapter(mMenuAdapter);

       // Capture button clicks on side menu
       mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

       // Enable ActionBar app icon to behave as action to toggle nav drawer
       getSupportActionBar().setHomeButtonEnabled(true);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // ActionBarDrawerToggle ties together the the proper interactions
       // between the sliding drawer and the action bar app icon
       mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
               R.drawable.ic_drawer, R.string.drawer_open,
               R.string.drawer_close) {

           public void onDrawerClosed(View view) {
               // TODO Auto-generated method stub
               super.onDrawerClosed(view);
           } 

           public void onDrawerOpened(View drawerView) {
               // TODO Auto-generated method stub
               super.onDrawerOpened(drawerView);
           }
       };

       mDrawerLayout.setDrawerListener(mDrawerToggle);
 
   }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.drawer_main);
  
       
       /* if (savedInstanceState == null) {
            selectItem(0);
        }*/
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.base, menu);
        return true;
    } 
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        if (item.getItemId() == android.R.id.home) {
 
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
 
        return super.onOptionsItemSelected(item);
    }
 
    // The click listener for ListView in the navigation drawer
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            selectItem(position);
        }
    }
 
    private void selectItem(int position) {
 
      /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      Toast.makeText(this,"cddddd",Toast.LENGTH_LONG).show();
        // Locate Position
        switch (position) {
        case 0:
            ft.replace(R.id.content_frame, findDentistActivity);
            break;
      
        } 
        ft.commit(); */
    	
    	 Intent intent=null;
    	if(position==0)
    	{
    	  intent=new Intent(BaseActivity.this,com.deltadental.android.activities.DeltaDentalHomeActivity.class);
    	    
    	}
    	else if(position==1)  
    	{

   		  intent=new Intent(BaseActivity.this,com.deltadental.android.activities.FindDentistNearMeActivity.class);
   	      
    	}
    	else
    	{
    		intent=new Intent(BaseActivity.this,com.deltadental.android.activities.FindDentistNearAddrActivity.class);
    	   
    	}
      
    	BaseActivity.this.startActivity(intent);
        mDrawerList.setItemChecked(position, true);
        // Close drawer 
        mDrawerLayout.closeDrawer(mDrawerList);
    	
    }
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}