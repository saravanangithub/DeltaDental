package com.deltadental.android.models;

import java.util.List;

import com.parse.ParseObject;

public class DeltaDentalResponse 
{
  private String error;
  private String message; 
  private List<ParseObject> providerAddressList;
 
  
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
