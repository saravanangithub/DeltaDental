package com.deltadental.android.models;

public class DeltaDentalResponse 
{
  private String error;
  private String message;
  

  
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
    
}
