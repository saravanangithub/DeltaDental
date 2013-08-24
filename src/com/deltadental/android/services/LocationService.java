package com.deltadental.android.services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.deltadental.android.exception.LocationException;
import com.parse.LocationCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class LocationService
{
  private static final int TIME_OUT = 7000; 
  private static LocationService _instance;
  private ParseGeoPoint _lastKnownGeopoint;
  //private List<LocationServiceListener> _listeners;
  private Context context;

  private LocationService(){}

  private LocationService(Context context)
  {
    ParseGeoPoint.getCurrentLocationInBackground(TIME_OUT, new OnGetLocationCallback());
    this.context = context;
  }


  public static LocationService getInstance(Context context)
  {
    if(_instance == null)
    {
      _instance =  new LocationService(context);
      return _instance;
    }
    else
    {
      return _instance;
    }
  }

  public ParseGeoPoint getLocation() throws LocationException
  {
    ParseGeoPoint.getCurrentLocationInBackground(TIME_OUT, new OnGetLocationCallback());

    if(_lastKnownGeopoint == null)
    {
      LocationException exception =  new LocationException();
      exception.setError(LocationException.LocationError.GETTING_LOCATION); 
      throw exception;
    }

    return _lastKnownGeopoint;
  } 
  
  public ParseGeoPoint getLocationByZip(final Context context,String zipCode) throws LocationException
  {
	  Geocoder geocoder=new Geocoder(context,Locale.getDefault());
	  try {
		List<Address> addresses=geocoder.getFromLocationName(zipCode, 10);
		if(addresses!=null)
		{
		ParseGeoPoint parseGeoPoint=new ParseGeoPoint(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
		   return parseGeoPoint; 
		}
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    ParseGeoPoint.getCurrentLocationInBackground(TIME_OUT, new OnGetLocationCallback());

    if(_lastKnownGeopoint == null)
    {
      LocationException exception =  new LocationException();
      exception.setError(LocationException.LocationError.GETTING_LOCATION); 
      throw exception;
    }

    return _lastKnownGeopoint;
  } 


  private class OnGetLocationCallback extends LocationCallback
  {
    @Override
    public void done(ParseGeoPoint parseGeoPoint, ParseException e)
    {
      if(e == null || e.getMessage().isEmpty())
      {
    	  Geocoder geocoder=new Geocoder(context,Locale.getDefault());
    	  List<Address> addresses=null;
        _lastKnownGeopoint = parseGeoPoint;
        try {
			addresses=geocoder.getFromLocation(_lastKnownGeopoint.getLatitude(),_lastKnownGeopoint.getLongitude(), 5);
		} catch (IOException e1) { 
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}      
	    //Toast.makeText(context,addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
	   // Toast.makeText(context,addresses.get(0).getAddressLine(1), Toast.LENGTH_SHORT).show();
	    //Toast.makeText(context,addresses.get(0).getAddressLine(2), Toast.LENGTH_SHORT).show();
	    //Log.d("jjj","02201"+ addresses.get(0).getAddressLine(0) + "  "+ addresses.get(0).getLatitude()+" "+addresses.get(0).getLongitude());
	    
        //Toast.makeText(context,"kdjkw", Toast.LENGTH_SHORT).show(); 
      }
    }
  }


    
  
}