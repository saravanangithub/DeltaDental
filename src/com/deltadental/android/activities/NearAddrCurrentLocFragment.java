package com.deltadental.android.activities;

import java.util.List;

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
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ActionBar;
import android.content.Intent;

public class NearAddrCurrentLocFragment extends SherlockFragment  {
	
	private Button btnNearAddrCurrentLoc;
	private LocationService locationService;
	private DeltaDentalService deltaDentalService;
	private ListView listVwProviders;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_near_addr_current_loc, container, false);
        
        btnNearAddrCurrentLoc = (Button) rootView.findViewById(R.id.btnNearAddrCurrentLoc);
        listVwProviders=(ListView)rootView.findViewById(R.id.listVwNearAddrCurrentLoc);
        locationService=LocationService.getInstance(getSherlockActivity());
        deltaDentalService=DeltaDentalService.getInstance(getSherlockActivity(),new Intent());
        btnNearAddrCurrentLoc.setOnClickListener(new OnClickListener(){
             @Override
             public void onClick(View v) {
            	 try {
					ParseGeoPoint parseGeoPoint=locationService.getLocation();
					if(parseGeoPoint!=null)
					{
					deltaDentalService.getProvidersByNearAddrCurrentLocation(getSherlockActivity(),parseGeoPoint,new  GetProviderSuccess(), new GetProviderFailure());
					}
					
				} catch (LocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                Toast.makeText(getActivity(), "asdasd", Toast.LENGTH_SHORT).show();     
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
