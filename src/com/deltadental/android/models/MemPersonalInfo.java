package com.deltadental.android.models;

import java.util.Calendar;

public class MemPersonalInfo
{
  private String memberId;
  private String ssn;
  private Calendar dob;
  
  public String getMemberId() 
  {
	return memberId;
  }

  public void setMemberId(String memberId) 
  {
	this.memberId = memberId;
  }

  public String getSsn() 
  {
	return ssn;
  }

  public void setSsn(String ssn) 
  {
	this.ssn = ssn;
  }

  public Calendar getDob() 
  {
	return dob;
  }

  public void setDob(Calendar dob) 
  {
	this.dob = dob;
  }

  public boolean equals(Object o)
  {
	if((o instanceof MemPersonalInfo))
	{
	  MemPersonalInfo memPersonalInfo=(MemPersonalInfo)o;
	  
	  if(memberId.equals(memPersonalInfo.getMemberId()))
	    if(ssn.equals(memPersonalInfo.getSsn()))
	      if(dob.equals(memPersonalInfo.getDob()))
			  return true;
	  
	}
	 
	return false ; 
  }
}
