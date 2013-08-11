package com.deltadental.android.activities;

import com.actionbarsherlock.app.SherlockActivity;
import com.deltadental.android.R; 
import com.deltadental.android.R.layout;
import com.deltadental.android.R.menu;

import com.deltadental.android.commons.Closure;
import com.deltadental.android.models.DeltaDentalResponse;
import com.deltadental.android.services.DeltaDentalService;
import com.deltadental.android.services.LocationService;
import com.deltadental.android.utils.KDialog;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeltaDentalActivity extends SherlockActivity {
	  
	  private EditText edtTxtUserName;
	  private EditText edtTxtpassword;
	  private Button btnLogin;
	  private DeltaDentalService deltaDentalService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delta_dental);
		
		edtTxtUserName=(EditText)findViewById(R.id.edtTxtMemberIdLogin);
		edtTxtpassword=(EditText)findViewById(R.id.edtTxtPasswordLogin);
		btnLogin=(Button)findViewById(R.id.btnLogin);
		deltaDentalService=DeltaDentalService.getInstance(this,getIntent());
		LocationService.getInstance(this); 
		btnLogin.setOnClickListener(new OnClickListener() 
		{ 
		  
			@Override
		  public void onClick(View v) 
		  {
			
		    KDialog.showLoadingDialog(DeltaDentalActivity.this,"Signing in..");     
		    deltaDentalService.signIn(edtTxtUserName.getText().toString(), edtTxtpassword.getText().toString(),
		    		                  new OnLoginSuccess(),new OnLoginFailure());
		    
				
		  }
		});
		
		/*Button btnLogin=(Button)findViewById(R.id.login);
		
		/btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(DeltaDentalActivity.this,com.deltadental.android.activities.LoginActivity.class); 
				startActivity(intent); 
				
			}
		});*/ 
		
		Button btnRegister=(Button)findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(DeltaDentalActivity.this,com.deltadental.android.activities.RegisterActivity.class);  
				startActivity(intent); 
			}
		}); 
	} 
	
	private class OnLoginSuccess extends Closure<DeltaDentalResponse>
	{
		    @Override
	  public void invoke(DeltaDentalResponse response)
	  {
		    	
		   KDialog.hideLoadingDialog(DeltaDentalActivity.this);
		   Intent intent=new Intent(DeltaDentalActivity.this,com.deltadental.android.activities.DeltaDentalHomeActivity.class);
	
		   intent.putExtra("userName", response.getUserName());     
		   
		   startActivity(intent);  
		      
	  }
	}
		
	private class OnLoginFailure extends Closure<DeltaDentalResponse>
	{
		    @Override
	  public void invoke(DeltaDentalResponse response)
	  {
		    	
	    KDialog.hideLoadingDialog(DeltaDentalActivity.this);
		KDialog.showMessage(DeltaDentalActivity.this, response.getError());      
		      
	  }
	} 
 
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delta_dental, menu);
		return true;
	}
*/
}
