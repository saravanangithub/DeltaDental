package com.deltadental.android.activities;

import com.actionbarsherlock.app.SherlockFragment;
import com.deltadental.android.R;
import com.deltadental.android.R.layout;
import com.deltadental.android.R.menu;
import com.deltadental.android.adapters.ProviderListAdapter;
import com.deltadental.android.commons.Closure;
import com.deltadental.android.exception.LocationException;
import com.deltadental.android.models.DeltaDentalResponse;
import com.deltadental.android.services.DeltaDentalService;
import com.deltadental.android.services.LocationService;
import com.deltadental.android.utils.KDialog;
import com.parse.ParseGeoPoint;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;

public class NearMeCurrentLocFragment extends SherlockFragment  {

	private Button btnNearMeCurrentLoc;
	private LocationService locationService;
	private DeltaDentalService deltaDentalService;
	private RadioGroup radGroupNearMeCurrentLoc;
	private ListView listVwProviders;
			
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_near_me_current_loc, container, false);
        btnNearMeCurrentLoc=(Button)rootView.findViewById(R.id.btnNearMeCurrentLoc);
        listVwProviders=(ListView)rootView.findViewById(R.id.listVwNearMeCurrentLoc);
        radGroupNearMeCurrentLoc=(RadioGroup)rootView.findViewById( R.id.radGroupNearMeCurrentLoc );
        locationService = LocationService.getInstance(getSherlockActivity());
        deltaDentalService=DeltaDentalService.getInstance(getSherlockActivity(), new Intent());
        btnNearMeCurrentLoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  try {
						ParseGeoPoint lastKnownGeoPoint=locationService.getLocation();
						int distance=0;
						if(lastKnownGeoPoint!=null)
						{
							int id = radGroupNearMeCurrentLoc.getCheckedRadioButtonId();
							switch(id)
							{
							case R.id.radio1NearMeCurrentLoc:
								distance=5;
								break;
							case R.id.radio2NearMeCurrentLoc:
								distance=10;
								break;
							
							case R.id.radio4NearMeCurrentLoc:
								distance=20; 
								break;  
							}
							 KDialog.showLoadingDialog(getSherlockActivity(),"Processing");  
							 deltaDentalService.getProvidersByNearMeCurrentLoc(getSherlockActivity(), lastKnownGeoPoint, distance, new GetProviderSuccess(), new GetProviderFailure());
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
			KDialog.hideLoadingDialog(getSherlockActivity()); 
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
