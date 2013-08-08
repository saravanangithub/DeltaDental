package com.deltadental.android.activities;

import com.deltadental.android.R;
import com.deltadental.android.R.layout;
import com.deltadental.android.R.menu;
import com.deltadental.android.commons.Closure;
import com.deltadental.android.models.DeltaDentalResponse;
import com.deltadental.android.services.DeltaDentalService;
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

import com.parse.Parse;
import com.parse.ParseAnalytics; 

public class LoginActivity extends Activity
{
  private EditText edtTxtUserName;
  private EditText edtTxtpassword;
  private Button btnLogin;
  private DeltaDentalService deltaDentalService;
   
	@Override
  protected void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login);
	
	edtTxtUserName=(EditText)findViewById(R.id.edtTxtMemberIdLogin);
	edtTxtpassword=(EditText)findViewById(R.id.edtTxtPasswordLogin);
	btnLogin=(Button)findViewById(R.id.btnLogin);
	deltaDentalService=DeltaDentalService.getInstance(this,getIntent());
	btnLogin.setOnClickListener(new OnClickListener() 
	{ 
	  
		@Override
	  public void onClick(View v) 
	  {
		Toast.makeText(LoginActivity.this,Thread.currentThread().getName()+"in onclick", Toast.LENGTH_SHORT).show() ;
	    KDialog.showLoadingDialog(LoginActivity.this,"Signing in..");     
	    deltaDentalService.signIn(edtTxtUserName.getText().toString(), edtTxtpassword.getText().toString(),
	    		                  new OnLoginSuccess(),new OnLoginFailure());
	    
			
	  }
	});
			
  }

	private class OnLoginSuccess extends Closure<DeltaDentalResponse>
	{
		    @Override
	  public void invoke(DeltaDentalResponse response)
	  {
		    	Toast.makeText(LoginActivity.this,Thread.currentThread().getName()+"on success", Toast.LENGTH_SHORT);
		   KDialog.hideLoadingDialog(LoginActivity.this);
		   Intent intent=new Intent(LoginActivity.this,com.deltadental.android.activities.DeltaDentalHomeActivity.class);
		   startActivity(intent);
		      
	  }
	}
		
	private class OnLoginFailure extends Closure<DeltaDentalResponse>
	{
		    @Override
	  public void invoke(DeltaDentalResponse response)
	  {
		    	Toast.makeText(LoginActivity.this,Thread.currentThread().getName()+"on failure", Toast.LENGTH_SHORT);
	    KDialog.hideLoadingDialog(LoginActivity.this);
		KDialog.showMessage(LoginActivity.this, response.getError());      
		      
	  }
	}
	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
