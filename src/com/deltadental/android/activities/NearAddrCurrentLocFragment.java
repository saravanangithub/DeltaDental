package com.deltadental.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.deltadental.android.R;
import com.deltadental.android.adapters.ProviderListAdapter;
import com.deltadental.android.commons.Closure;
import com.deltadental.android.exception.LocationException;
import com.deltadental.android.models.DeltaDentalResponse;
import com.deltadental.android.services.DeltaDentalService;
import com.deltadental.android.services.LocationService;
import com.deltadental.android.utils.KDialog;
import com.parse.ParseGeoPoint;

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
						KDialog.showLoadingDialog(getSherlockActivity(),"Processing");    
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
