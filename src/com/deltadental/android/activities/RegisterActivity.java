package com.deltadental.android.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener; 
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.deltadental.android.R;
import com.deltadental.android.commons.Closure;
import com.deltadental.android.models.DeltaDentalResponse;
import com.deltadental.android.models.MemPersonalInfo;
import com.deltadental.android.services.DeltaDentalService;
import com.deltadental.android.utils.DateTimeUtils;
import com.deltadental.android.utils.KDialog;

public class RegisterActivity extends BaseActivity  
{

  private ImageButton btnDate;
  private TextView txtVwDate;
  public static final int Date_dialog_id = 1;
	// date and time
  private int mYear;
  private int mMonth;
  private int mDay;
  private Button btnRegister;
  private EditText edtTxtMemberId;
  private EditText edtTxtPassword;
  private EditText edtTxtConPassword;
  private EditText edtTxtSsn;

  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_register);

	btnRegister = (Button) findViewById(R.id.btnRegister);
	btnDate = (ImageButton) findViewById(R.id.btnDate);
	txtVwDate = (TextView) findViewById(R.id.txtVwDate);
	edtTxtMemberId = (EditText) findViewById(R.id.edtTxtMemberId);
	edtTxtPassword = (EditText) findViewById(R.id.edtTxtPassword);
	edtTxtConPassword = (EditText) findViewById(R.id.edtTxtConPassword);
	edtTxtSsn = (EditText) findViewById(R.id.edtTxtSsn);

	btnRegister.setOnClickListener(registerListener);
	btnDate.setOnClickListener(new OnClickListener() 
	{

	  @Override
	  public void onClick(View v) 
	  {
	    DatePickerDialog DPD = new DatePickerDialog(RegisterActivity.this, mDateSetListener,
	    		                                   mYear, mMonth,mDay);
		DPD.show();
	  }
	});

	final Calendar c = Calendar.getInstance();
	mYear = c.get(Calendar.YEAR);
	mMonth = c.get(Calendar.MONTH);
	mDay = c.get(Calendar.DAY_OF_MONTH);

	updateDisplay();

  }

  protected void onPrepareDialog(int id, Dialog dialog) 
  {
		// TODO Auto-generated method stub
    super.onPrepareDialog(id, dialog);

	((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);

  }

  private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() 
  {

    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) 
    {
	  mYear = year;
	  mMonth = monthOfYear;
	  mDay = dayOfMonth;
	  updateDisplay();
	}
  };

  private void updateDisplay()
  {
		
    txtVwDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear));
  }

  private OnClickListener registerListener = new OnClickListener() 
  {

		@Override
    public void onClick(View v) 
	{
			// TODO Auto-generated method stub
	String memberId = edtTxtMemberId.getText().toString();
	String password = edtTxtPassword.getText().toString();
	String conPassword = edtTxtConPassword.getText().toString();
	String ssn=edtTxtSsn.getText().toString();
	Calendar dob=DateTimeUtils.parseStr("MM-dd-yyyy",txtVwDate.getText().toString());
			
	MemPersonalInfo memPersonalInfo=new MemPersonalInfo();
	memPersonalInfo.setMemberId(memberId);
	memPersonalInfo.setSsn(ssn);
	memPersonalInfo.setDob(dob); 
			
	DeltaDentalService deltaDentalService=DeltaDentalService.getInstance(RegisterActivity.this,getIntent());
	
	KDialog.showLoadingDialog(RegisterActivity.this, "Registering");
	deltaDentalService.signUp(memPersonalInfo, password,new OnRegistrationSuccess(),new OnRegistrationFailure());			
			
			
	}
  };

	public void validate() {

	}

  private class OnRegistrationSuccess extends Closure<DeltaDentalResponse>
  {
	    @Override
    public void invoke(DeltaDentalResponse response)
	{
	  KDialog.hideLoadingDialog(RegisterActivity.this);
	  KDialog.showMessage(RegisterActivity.this, response.getMessage());
	        
	      
	}
  }
	
  private class OnRegistrationFailure extends Closure<DeltaDentalResponse>
  {
	    @Override
    public void invoke(DeltaDentalResponse response)
    {
	  KDialog.hideLoadingDialog(RegisterActivity.this);
	  KDialog.showMessage(RegisterActivity.this, response.getError());      
	      
	}
  }
   
}
