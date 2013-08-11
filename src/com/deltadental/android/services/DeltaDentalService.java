package com.deltadental.android.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.deltadental.android.commons.Closure;
import com.deltadental.android.models.DeltaDentalResponse;
import com.deltadental.android.models.MemPersonalInfo;
import com.deltadental.android.utils.DateTimeUtils;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class DeltaDentalService 
{
  private static DeltaDentalService instance;
  private Context context;
  private DeltaDentalResponse deltaDentalResponse=new DeltaDentalResponse();
	
  private DeltaDentalService(Context context,Intent intent)
  {
    this.context=context;
    Parse.initialize(context, "wFC3QAOgnFovCbBd5iltFDPERUjsBDgqBCGjCl7i",
			"tJVTkTsjYvBvrN2i9Q33h1uXc14TFNKCKUmacEbK");
	ParseAnalytics.trackAppOpened(intent); 
  }
  public void signIn(final String userName,final String password,final Closure<DeltaDentalResponse> successClosure,final Closure<DeltaDentalResponse> errorClosure)
  {
    ParseUser.logInInBackground(userName, password, new LogInCallback() 
    {
      public void done(ParseUser user, ParseException e) 
      {
	    if (user != null) 
	    {
		  deltaDentalResponse.setMessage("Login Success"); 
		  
		  ParseUser currentUser = ParseUser.getCurrentUser();
			 
			Toast.makeText(context,
					"current user" + currentUser.getString("username"),
					Toast.LENGTH_SHORT).show();
			ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("MemPersonalInfo");
			parseQuery.whereEqualTo("MemberId",Integer.parseInt(currentUser.getString("username") ));
			 parseQuery.findInBackground(new FindCallback<ParseObject>() {

					@Override
					public void done(List<ParseObject> providersList, ParseException ex) {
						// TODO Auto-generated method stub
						if(ex == null)
						{
						  ParseObject parseObject=providersList.get(0);
						 String userName=parseObject.getString("FirstName")+" "+parseObject.getString("LastName");
						 
						 Toast.makeText(context,"thread"+Thread.currentThread().getName(), Toast.LENGTH_SHORT).show(); 
						 DeltaDentalResponse deltaDentalResponse=new DeltaDentalResponse();
						 deltaDentalResponse.setUserName(userName); 
						 
							Toast.makeText(context,
									"user name" + userName, 
									Toast.LENGTH_SHORT).show();
						 successClosure.invoke(deltaDentalResponse);
						}
					}  });
		 // successClosure.invoke(deltaDentalResponse);
		  /*ParseQuery<ParseObject> query = ParseQuery.getQuery("MemPersonalInfo");
		  query.whereEqualTo("MemeberId", Integer.parseInt(userName));
		  query.findInBackground(new FindCallback<ParseObject>()
		  {
		    public void done(List<ParseObject> membersList,ParseException e)
		    {
		      ParseObject member=membersList.get(0);
		      
		    }
		  });*/
		  
		}
	    else 
		{
	      deltaDentalResponse.setError("Login Failed"); 
	      errorClosure.invoke(deltaDentalResponse);
		}
	 }
   });
 } 
  
  public void signUp(final MemPersonalInfo memPersonalInfo,final String password,final Closure<DeltaDentalResponse> successClosure,final Closure<DeltaDentalResponse> errorClosure)
  {
	  
    ParseQuery<ParseObject> query = ParseQuery.getQuery("MemPersonalInfo");
    query.whereEqualTo("MemberId", Integer.parseInt(memPersonalInfo.getMemberId()));
   
    query.getFirstInBackground(new GetCallback<ParseObject>() 
    {
	  @SuppressWarnings("deprecation")
	  public void done(ParseObject parseObject, ParseException e) 
	  {
		
	    if (e == null) 
	    {
	      MemPersonalInfo memPersonalInfoDup=new MemPersonalInfo();
	      memPersonalInfoDup.setMemberId(parseObject.getInt("MemberId")+"");
	      Calendar dob=Calendar.getInstance();
	      dob.setTime(parseObject.getDate("DOB"));
	      dob=DateTimeUtils.changeFormat("MM-dd-yyyy", dob);
	      memPersonalInfoDup.setDob(dob);
	      memPersonalInfoDup.setSsn(parseObject.getString("SSN"));
	      
	      if(memPersonalInfoDup.equals(memPersonalInfo)) 
	      {
	        ParseUser parseUser = new ParseUser();
			parseUser.setUsername(parseObject.getInt("MemberId")+ "");
			parseUser.setPassword(password);
			parseUser.put("PersonalInfo", parseObject);
			parseUser.signUpInBackground(new SignUpCallback() 
			{
			  public void done(ParseException e) 
			  {
			    if (e == null) 
			    {
				  deltaDentalResponse.setMessage("Registered Successfully");
				  successClosure.invoke(deltaDentalResponse);
				}  
			    else 
			    {
			      //Toast.makeText(context, "registered already", Toast.LENGTH_SHORT).show();
			      deltaDentalResponse.setMessage("Member Registered Already"); 
				  successClosure.invoke(deltaDentalResponse);		
				}
			  }
			});
	               
	      } 
	      else
	      { //Toast.makeText(context, "object not equals", Toast.LENGTH_SHORT).show();   
	        deltaDentalResponse.setError("Invalid Member details");
	        errorClosure.invoke(deltaDentalResponse);
	      }
	    }
	    else 
	    {
	      //Toast.makeText(context, "Member Id not exist", Toast.LENGTH_SHORT).show();
	      deltaDentalResponse.setError("Invalid Member details");
		  errorClosure.invoke(deltaDentalResponse); 
	    }
	  }
    });
    
   
    
  }
  
  public void getProvidersByNearMeCurrentLoc(final Context context,ParseGeoPoint lastKownGeoPoint,final int distance,final Closure<DeltaDentalResponse> successClosure,final Closure<DeltaDentalResponse> errorClosure)
  {
	  ParseQuery<ParseObject> query = ParseQuery.getQuery("Provider");
	  query.whereWithinMiles("GeoPoint", lastKownGeoPoint,distance);
	  
	  query.setLimit(10); 
	  query.findInBackground(new FindCallback<ParseObject>() {

		@Override
		public void done(List<ParseObject> providersList, ParseException ex) {
			// TODO Auto-generated method stub
			if(ex == null)
			{
			  DeltaDentalResponse deltaDentalResponse=new DeltaDentalResponse();
			  deltaDentalResponse.setProviderAddressList(providersList);
			  for(ParseObject parseObject:providersList)
			  {
			      			 
			  Toast.makeText(context, parseObject.getString("FirstName"), Toast.LENGTH_SHORT).show();
			  }
			  successClosure.invoke(deltaDentalResponse);
			  }
		}  });
  }
  
  public void getProvidersByNearAddrNearAddr(final Context context,ParseGeoPoint lastKownGeoPoint,final Closure<DeltaDentalResponse> successClosure,final Closure<DeltaDentalResponse> errorClosure)
  {
	  ParseQuery<ParseObject> query = ParseQuery.getQuery("Provider");
	  query.whereWithinMiles("GeoPoint", lastKownGeoPoint, 2);
	  
	  query.setLimit(10); 
	  query.findInBackground(new FindCallback<ParseObject>() {

		@Override
		public void done(List<ParseObject> providersList, ParseException ex) {
			// TODO Auto-generated method stub
			if(ex == null)
			{
			  ParseObject parseObject=providersList.get(0); 
			  DeltaDentalResponse deltaDentalResponse=new DeltaDentalResponse();
			  deltaDentalResponse.setProviderAddressList(providersList);
			  successClosure.invoke(deltaDentalResponse);
			 
			  Toast.makeText(context, parseObject.getString("FirstName"), Toast.LENGTH_SHORT).show();
			}
		}  });
  }
  
  public void getProvidersByNearAddrCurrentLocation(final Context context,ParseGeoPoint lastKownGeoPoint,final Closure<DeltaDentalResponse> successClosure,final Closure<DeltaDentalResponse> errorClosure)
  {
	  ParseQuery<ParseObject> query = ParseQuery.getQuery("Provider");
	  query.whereWithinMiles("GeoPoint", lastKownGeoPoint, 2);
	 
	  query.setLimit(10); 
	  query.findInBackground(new FindCallback<ParseObject>() {

		@Override
		public void done(List<ParseObject> providersList, ParseException ex) {
			// TODO Auto-generated method stub
			if(ex == null)
			{
			  ParseObject parseObject=providersList.get(0); 
			 
			  Toast.makeText(context, parseObject.getString("FirstName"), Toast.LENGTH_SHORT).show();
			  
			  DeltaDentalResponse deltaDentalResponse=new DeltaDentalResponse();
			  deltaDentalResponse.setProviderAddressList(providersList);
			  successClosure.invoke(deltaDentalResponse);
			  
			}
		}  });
  }
  
  
  public void getLastVist(final Closure<DeltaDentalResponse> successClosure,final Closure<DeltaDentalResponse> errorClosure)
  {
	  ParseQuery<ParseObject> query=ParseQuery.getQuery("Claims");
	  query.whereEqualTo("MemberId","96457");
	  
	
	  query.findInBackground(new FindCallback<ParseObject>() {

		@Override
		public void done(List<ParseObject> claimsList, ParseException ex) {
			// TODO Auto-generated method stub
			if(ex==null) {
				ArrayList<Date> dates=new ArrayList<Date>();  
				int claimedAmount=0;
				for(ParseObject claim:claimsList)
				{
					claimedAmount+=claim.getInt("CoveredCost");  
					dates.add(claim.getDate("DateOfVisit"));
				}
				Date date=Collections.max(dates); 							
				ParseObject latestClaim=null;
				Date dupDate=null;
				for(ParseObject claim:claimsList)
				{
					dupDate=claim.getDate("DateOfVisit");
					if(dupDate.equals(date))
					{
						latestClaim=claim; 
						break;
					}
					//dates=new ArrayList<Date>(); 
					//dates.add(claim.getDate("DateOfVisit")); 
				}
				final DeltaDentalResponse deltaDentalResponse=new DeltaDentalResponse();
				deltaDentalResponse.setClaimInfo(latestClaim);
				deltaDentalResponse.setClaimedAmount(claimedAmount);
				 ParseQuery<ParseObject> benefitsQuery=ParseQuery.getQuery("Benefits");
				  benefitsQuery.whereEqualTo("MemberID","96457"); 
				  benefitsQuery.findInBackground(new FindCallback<ParseObject>() {
 
					@Override
					public void done(List<ParseObject> parseObject, ParseException ex) {
						// TODO Auto-generated method stub
						//Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
						deltaDentalResponse.setTotalAmount(parseObject.get(0).getInt("AllowedAmount")); 
						successClosure.invoke(deltaDentalResponse);   
					}
				});
				  
				
				int index=dates.indexOf(date);   
				//ParseObject parseObject=claimsList.get(index); 
				//ParseObject lastClaim=claimsList.get(0);
				//Toast.makeText(context, "last claim"+date, Toast.LENGTH_SHORT).show(); 
				//Toast.makeText(context, index +"  "+latestClaim.getString("ServiceReceived"), Toast.LENGTH_SHORT).show(); 
			}
				
				
		}
	});
  }
  public static DeltaDentalService getInstance(Context context,Intent intent)
  {
    if(instance == null)
    {
      instance = new DeltaDentalService(context,intent);
    }
     
    return instance;
   
  }

}
