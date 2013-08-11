package com.deltadental.android.models;

import java.util.List;

import com.parse.ParseObject;

public class DeltaDentalResponse 
{
  private String error;
  private String message; 
  private List<ParseObject> providerAddressList;
  private String userName;
  private ParseObject claimInfo;
  private int claimedAmount;
  private int totalAmount;
  
   
  
  
  public int getClaimedAmount() {
	return claimedAmount;
}

public void setClaimedAmount(int claimedAmount) {
	this.claimedAmount = claimedAmount;
}

public int getTotalAmount() {
	return totalAmount;
}

public void setTotalAmount(int totalAmount) {
	this.totalAmount = totalAmount;
}

public ParseObject getClaimInfo() {
	return claimInfo;
}

public void setClaimInfo(ParseObject claimInfo) {
	this.claimInfo = claimInfo;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getMessage() 
  {
	return message;
  }

  public void setMessage(String message) 
  {
	this.message = message; 
  }

  public String getError() 
  {
	return error;
  }

  public void setError(String error) 
  {
	this.error = error;
  }

public List<ParseObject> getProviderAddressList() {
	return providerAddressList;
}

public void setProviderAddressList(List<ParseObject> providerAddressList) {
	this.providerAddressList = providerAddressList; 
}
    
  
}
