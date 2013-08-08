package com.deltadental.android.services;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.deltadental.android.commons.Closure;
import com.deltadental.android.models.DeltaDentalResponse;
import com.deltadental.android.models.MemPersonalInfo;
import com.deltadental.android.utils.DateTimeUtils;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
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
		  successClosure.invoke(deltaDentalResponse);
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
  
  public static DeltaDentalService getInstance(Context context,Intent intent)
  {
    if(instance == null)
    {
      instance = new DeltaDentalService(context,intent);
    }
     
    return instance;
   
  }

}
