package com.deltadental.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deltadental.android.R;
import com.deltadental.android.services.DeltaDentalService;
import com.deltadental.android.views.PieChart;

 
public class RecentVisitActivity extends BaseActivity {

	private DeltaDentalService deltaDentalService;
	private TextView txtVwPatientName;
	private TextView txtVwDentistName;
	private TextView txtVwDateOfService;
	private TextView txtVwServiceReceived;
	private TextView txtVwCoveredCost;
	private TextView txtVwYourCost; 
	private TextView txtVwClaimedAmount;
	private TextView txtVwRemainingAmount;
	
	
	float values[] = { 700, 400, 100, 500, 600 };
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent_visit);
		
		txtVwPatientName=(TextView)findViewById(R.id.txtVwPatientName);
		txtVwDentistName=(TextView)findViewById(R.id.txtVwDentistName);
		txtVwDateOfService=(TextView)findViewById(R.id.txtVwDateOfService);
		txtVwServiceReceived=(TextView)findViewById(R.id.txtVwServiceReceived);
		txtVwCoveredCost=(TextView)findViewById(R.id.txtVwCoveredCost);
		txtVwYourCost=(TextView)findViewById(R.id.txtVwYourCost);
		txtVwClaimedAmount=(TextView)findViewById(R.id.txtVwClaimedAmount);
		txtVwRemainingAmount=(TextView)findViewById(R.id.txtVwRemainingAmount);
		float summaryLevel[]=new float[2];
		
		Intent intent=getIntent();
		
		summaryLevel[0]=intent.getIntExtra("claimedAmount", 100);
		summaryLevel[1]=intent.getIntExtra("totalAmount", 900)-summaryLevel[0]; 
		
		
		txtVwPatientName.setText(intent.getStringExtra("patientName"));
		txtVwDentistName.setText(intent.getStringExtra("dentistName"));
		txtVwDateOfService.setText(intent.getStringExtra("dateOfVisit"));
		txtVwServiceReceived.setText(intent.getStringExtra("serviceReceived"));
		txtVwCoveredCost.setText(intent.getStringExtra("coveredCost"));
		txtVwYourCost.setText(intent.getStringExtra("yourCost"));
		txtVwClaimedAmount.setText("  Claimed Amount :"+summaryLevel[0]+" $"); 
		txtVwRemainingAmount.setText("  Remaining Amount :"+summaryLevel[1]+" $"); 
		LinearLayout lv1 = (LinearLayout)findViewById(R.id.linearLayout);   

		values = calculateData(summaryLevel); 
		PieChart pieChart = new PieChart(this, values);
		lv1.addView(pieChart); 
		
		
	}

	 
	private float[] calculateData(float[] data) {
		float total = 0;
		for (int i = 0; i < data.length; i++) {
			total += data[i];
		}
		for (int i = 0; i < data.length; i++) {
			data[i] = 360 * (data[i] / total); 
		} 
		return data;
	}


}
