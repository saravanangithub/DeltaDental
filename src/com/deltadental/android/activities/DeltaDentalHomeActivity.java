package com.deltadental.android.activities;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.deltadental.android.R;
import com.deltadental.android.adapters.ProviderListAdapter;
import com.deltadental.android.commons.Closure;
import com.deltadental.android.exception.LocationException;
import com.deltadental.android.models.DeltaDentalResponse;
import com.deltadental.android.services.DeltaDentalService;
import com.deltadental.android.services.LocationService;
import com.parse.LocationCallback;
import com.parse.ParseGeoPoint;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;

public class DeltaDentalHomeActivity extends BaseActivity {

	
	
	private LocationService locationService;
	private DeltaDentalService deltaDentalService;
	private TextView txtVwWelcome;
	private Button btnRecentActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
 
		setContentView(R.layout.activity_delta_dental_home);
		
		txtVwWelcome=(TextView)findViewById(R.id.welcome);
		 txtVwWelcome.setText("Welcome "+ getIntent().getStringExtra("userName"));
		btnRecentActivity=(Button)findViewById(R.id.btnRecentActivity);
		 
		   
		locationService=LocationService.getInstance(this);
		deltaDentalService=DeltaDentalService.getInstance(this,getIntent());
		try {
			locationService.getLocation();
		} catch (LocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		
		btnRecentActivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deltaDentalService.getLastVist(new GetLastVisitSuccess(), new GetLastVisitFailure());
			}
		});
		
	}

	private class GetLastVisitSuccess extends Closure<DeltaDentalResponse>
	{

		@Override
		public void invoke(DeltaDentalResponse response) {
			// TODO Auto-generated method stub
		    Intent intent=new Intent(DeltaDentalHomeActivity.this,RecentVisitActivity.class);
		    ParseObject lastClaim=response.getClaimInfo();
		    intent.putExtra("patientName",lastClaim.getString("PatientName") );
		    intent.putExtra("dentistName",lastClaim.getString("DentistName") ); 
		    intent.putExtra("coveredCost",lastClaim.getInt("CoveredCost")+"" );
		    intent.putExtra("yourCost",lastClaim.getInt("YourCost")+"" );
		    intent.putExtra("serviceReceived",lastClaim.getString("ServiceReceived") );
		    intent.putExtra("dateOfVisit",lastClaim.getDate("DateOfVisit").toLocaleString() );  
		    intent.putExtra("claimedAmount",response.getClaimedAmount());
		    intent.putExtra("totalAmount", response.getTotalAmount());  
		   DeltaDentalHomeActivity.this.startActivity(intent);	  	 
		} 
		
	}
	
	private class GetLastVisitFailure extends Closure<DeltaDentalResponse> 
	{

		@Override
		public void invoke(DeltaDentalResponse response) {
			// TODO Auto-genlerated method stub
		    
		}
		
	}

	
}
