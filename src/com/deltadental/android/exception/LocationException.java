package com.deltadental.android.exception;

public class LocationException extends Exception 
{
  public enum LocationError
  {
    GETTING_LOCATION,
    CANNOT_GET_LOCATION,
    LOCATION_SETTINGS_OFF
  }
  private LocationError _error;


  public LocationError getError()
  {
    return _error;
  }


  public void setError(LocationError error)
  {
    _error = error;
  }
}
