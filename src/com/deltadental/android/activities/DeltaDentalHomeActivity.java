package com.deltadental.android.activities;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast; 
import com.deltadental.android.R;
import com.parse.LocationCallback;
import com.parse.ParseGeoPoint;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

public class DeltaDentalHomeActivity extends BaseActivity {

	private static final long TIME_OUT = 7000; 
	ParseGeoPoint lastKnownGeopoint;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		
		super.onCreate(savedInstanceState); 
		  //PushService.subscribe(this, "user_" + ParseUser.getCurrentUser().getObjectId(), this);
		  /*  ParseInstallation currentInstallation = ParseInstallation.getCurrentInstallation();
		    Toast.makeText(getApplicationContext(), ParseUser.getCurrentUser()+"",Toast.LENGTH_SHORT).show();
		    currentInstallation.put("user", ParseUser.getCurrentUser());
		    currentInstallation.saveInBackground();  */ 
		setContentView(R.layout.activity_delta_dental_home);
		 ParseGeoPoint.getCurrentLocationInBackground(TIME_OUT, new OnGetLocationCallback()); 
	}
	 private class OnGetLocationCallback extends LocationCallback
	  {
	   

		@Override
		public void done(ParseGeoPoint parseGeoPoint, com.parse.ParseException e) {
			// TODO Auto-generated method stub
			if(e == null || e.getMessage().isEmpty()) 
		      {
				Geocoder geocoder=new Geocoder(getBaseContext(),Locale.getDefault());
		    	  List<Address> addresses=null;
		    	  lastKnownGeopoint = parseGeoPoint;
		        try {
					addresses=geocoder.getFromLocation(lastKnownGeopoint.getLatitude(),lastKnownGeopoint.getLongitude(), 5);
				} catch (IOException e1) { 
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}     
			    Toast.makeText(DeltaDentalHomeActivity.this,addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
			    Toast.makeText(DeltaDentalHomeActivity.this,addresses.get(0).getAddressLine(1), Toast.LENGTH_SHORT).show();
			    Toast.makeText(DeltaDentalHomeActivity.this,addresses.get(0).getAddressLine(2), Toast.LENGTH_SHORT).show();
		      }
			else 
			{
				e.printStackTrace();
				Toast.makeText(DeltaDentalHomeActivity.this, e.getMessage()+parseGeoPoint+ " hhhhjjjjjjj", Toast.LENGTH_SHORT).show();
			}
		}
	  }
	
}
