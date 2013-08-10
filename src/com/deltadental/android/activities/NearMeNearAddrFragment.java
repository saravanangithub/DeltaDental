package com.deltadental.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.actionbarsherlock.app.SherlockFragment; 
import com.deltadental.android.R;
import com.deltadental.android.adapters.ProviderListAdapter;
import com.deltadental.android.commons.Closure;
import com.deltadental.android.exception.LocationException;
import com.deltadental.android.models.DeltaDentalResponse;
import com.deltadental.android.services.DeltaDentalService;
import com.deltadental.android.services.LocationService;
import com.parse.ParseGeoPoint;

public class NearMeNearAddrFragment extends SherlockFragment   {
   
	private Button btnNearMeNearAddr;
	private LocationService locationService;
	private DeltaDentalService deltaDentalService;
	private RadioGroup radGroupNearMeNearAddr;
	private EditText edtTxtZipCode;
	private ListView listVwProviders;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_near_me_near_addr, container, false);
       
        btnNearMeNearAddr=(Button)rootView.findViewById(R.id.btnNearMeNearAddr);
        listVwProviders=(ListView)rootView.findViewById(R.id.listVwNearMeNearAddr);
        radGroupNearMeNearAddr=(RadioGroup)rootView.findViewById( R.id.radGroupNearMeNearAddr );
        locationService = LocationService.getInstance(getSherlockActivity());
        deltaDentalService=DeltaDentalService.getInstance(getSherlockActivity(), new Intent());
        edtTxtZipCode=(EditText)rootView.findViewById(R.id.edtTxtZipCodeNearMeNearAddr); 
        btnNearMeNearAddr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					ParseGeoPoint parseGeoPoint=locationService.getLocationByZip(getSherlockActivity(),edtTxtZipCode.getText().toString());
					if(parseGeoPoint!=null)
					{
						int id = radGroupNearMeNearAddr.getCheckedRadioButtonId();
						int distance=5;
						switch(id)
						{
						case R.id.radio1NearMeNearAddr:
							distance=5;
							break;
						case R.id.radio2NearMeNearAddr:
							distance=10;
							break;
						
						case R.id.radio4NearMeNearAddr:
							distance=20;  
							break;  
						}
						
						 deltaDentalService.getProvidersByNearMeCurrentLoc(getSherlockActivity(), parseGeoPoint, distance, new GetProviderSuccess(), new GetProviderFailure());
					}
				} catch (LocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
         
        return rootView;
    }
	
	private class GetProviderSuccess extends Closure<DeltaDentalResponse>
	{

		@Override
		public void invoke(DeltaDentalResponse response) {
			// TODO Auto-generated method stub
			ProviderListAdapter providerListAdapter=new ProviderListAdapter(getSherlockActivity(), response.getProviderAddressList());
			listVwProviders.setAdapter(providerListAdapter);  
		}
		
	}
	
	private class GetProviderFailure extends Closure<DeltaDentalResponse>
	{

		@Override
		public void invoke(DeltaDentalResponse response) {
			// TODO Auto-generated method stub 
		    
		}
		
	}
		
	}
