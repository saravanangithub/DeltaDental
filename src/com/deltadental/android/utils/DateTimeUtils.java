package com.deltadental.android.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils 
{

 public static Calendar parseStr(String format,String dateStr)
 {
   Date date=null;
   Calendar calendar=null; 
   try 
   { 
     date = (Date)new SimpleDateFormat(format, Locale.ENGLISH).parse(dateStr);
     calendar=Calendar.getInstance();
     calendar.setTime(date);
   }
   catch(Exception ex)
   {
     System.out.println("Errrror");
   }
   
   return calendar;
 }
 
 public static Calendar changeFormat(String format,Calendar calendar)
 {
	 Date date=null;
	   
	   try 
	   { 
	     String strDate= new SimpleDateFormat(format, Locale.ENGLISH).format(calendar.getTime());
	     date= new SimpleDateFormat(format, Locale.ENGLISH).parse(strDate);
	     
	     calendar.setTime(date);
	     
	   }
	   catch(Exception ex)
	   {
	     System.out.println("Errrror");
	   }
	   
	   return calendar;
 }
}
