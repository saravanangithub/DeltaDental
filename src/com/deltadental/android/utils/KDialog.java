package com.deltadental.android.utils;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

public class KDialog
{
	private static Dialog _dialog;
	private static ProgressDialog _progressDialog;
	
	public static void showMessage(final Activity activity, final String msg)
	{
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
			    KDialog.AlertDialogBuilder builder = new KDialog.AlertDialogBuilder(activity);
			    builder.setTitle("Error");
			    builder.setMessage(msg);
			    builder.setNegativeButton("OK", null);
			    builder.show();				
			}
		});
	}
	public static void showModalDialog(Activity activity, String title, String msg, String okText, 
			DialogInterface.OnClickListener onOk, String cancelText, DialogInterface.OnClickListener onCancel)
	{
	    KDialog.AlertDialogBuilder builder = new KDialog.AlertDialogBuilder(activity);
	    builder.setTitle(title);
	    builder.setMessage(msg);
	    builder.setPositiveButton(okText, onOk);
	    builder.setNegativeButton(cancelText, onCancel);
	    builder.show();		
	}
	public static void showLoadingDialog(final Activity activity, final String msg)
	{
	  
	  activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
		  _progressDialog = new ProgressDialog(activity);
		  _progressDialog.setMessage(msg);
		  _progressDialog.show();	
			}
	  });
	}
	
	public static void hideLoadingDialog(final Activity activity)
	{
		
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
			  if(_progressDialog != null)
			  {
				  _progressDialog.dismiss();
			  }
			  _progressDialog = null;
			}
		});
	}
	  
	  
	
	public static class AlertDialogBuilder
	{
		private AlertDialog.Builder _builder;
		
		public AlertDialogBuilder(Context context) 
		{
			_builder = new AlertDialog.Builder(context);
		}
		
		
		public AlertDialogBuilder setTitle(String title)
		{
			_builder.setTitle(title);
			return this;
		}
		
		
		public AlertDialogBuilder setMessage(String message)
		{
			_builder.setMessage(message);
			return this;
		}
		
		
		public AlertDialogBuilder setPositiveButton(String text, OnClickListener onClickListener)
		{
			_builder.setPositiveButton(text, onClickListener);
            return this;
		}


        public AlertDialogBuilder setNegativeButton(String text, OnClickListener onClickListener)
        {
            _builder.setNegativeButton(text, onClickListener);
            return this;
        }


        public void show(DialogInterface.OnDismissListener onDismissListener)
        {
            if(_dialog != null && _dialog.isShowing())
            {
                _dialog.dismiss();
            }

            _dialog = _builder.create();
            _dialog.setOnDismissListener(onDismissListener);
            _dialog.show();
        }


        public void show()
        {
           if(_dialog != null && _dialog.isShowing())
           {
               _dialog.dismiss();
           }

            _dialog = _builder.create();
            _dialog.show();
        }
	}
}