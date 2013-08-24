package com.deltadental.android.activities;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.deltadental.android.R;
import com.deltadental.android.adapters.ProviderListAdapter;
import com.deltadental.android.commons.Closure;
import com.deltadental.android.exception.LocationException;
import com.deltadental.android.models.DeltaDentalResponse;
import com.deltadental.android.services.DeltaDentalService;
import com.deltadental.android.services.LocationService;
import com.deltadental.android.utils.KDialog;
import com.parse.LocationCallback;
import com.parse.ParseGeoPoint;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;

public class DeltaDentalHomeActivity extends BaseActivity {

	private LocationService locationService;
	private DeltaDentalService deltaDentalService;
	private TextView txtVwWelcome;
	private Button btnRecentActivity;
	private TextView cityText;
	private TextView condDescr;
	private TextView temp;
	private TextView press;
	private TextView windSpeed;
	private TextView windDeg;
	private ImageView imgView;

	private TextView hum;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_delta_dental_home);

		txtVwWelcome = (TextView) findViewById(R.id.welcome);
		txtVwWelcome.setText("Welcome "
				+ getIntent().getStringExtra("userName"));
		btnRecentActivity = (Button) findViewById(R.id.btnRecentActivity);

		cityText = (TextView) findViewById(R.id.cityText);
		condDescr = (TextView) findViewById(R.id.condDescr);
		temp = (TextView) findViewById(R.id.temp);
		hum = (TextView) findViewById(R.id.hum);
		press = (TextView) findViewById(R.id.press);
		windSpeed = (TextView) findViewById(R.id.windSpeed);
		windDeg = (TextView) findViewById(R.id.windDeg);
		cityText.setText(getIntent().getStringExtra("city"));
		condDescr.setText(getIntent().getStringExtra("condition"));
		temp.setText(getIntent().getStringExtra("temparature"));
		hum.setText(getIntent().getStringExtra("humidity"));
		press.setText(getIntent().getStringExtra("pressure"));
		windSpeed.setText(getIntent().getStringExtra("windspeed"));
		windDeg.setText(getIntent().getStringExtra("windDegree"));
		byte[] image = getIntent().getByteArrayExtra("image");
		imgView = (ImageView) findViewById(R.id.condIcon); 
		if (image == null) {
			Toast.makeText(this, "image is null ", Toast.LENGTH_LONG).show();
		}
		if (image.length == 0) {
			Toast.makeText(this, "image lenght 0", Toast.LENGTH_LONG).show();
		}

		if (image != null && image.length > 0) {
			Toast.makeText(this, "setting image view" + image.length,
					Toast.LENGTH_LONG).show();
			Bitmap img = BitmapFactory.decodeByteArray(image, 0, image.length);
			Toast.makeText(this, "img " + img, Toast.LENGTH_LONG).show();
			imgView.setImageBitmap(img);

		}
		locationService = LocationService.getInstance(this);
		deltaDentalService = DeltaDentalService.getInstance(this, getIntent());
		try {
			locationService.getLocation();
		} catch (LocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		btnRecentActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				KDialog.showLoadingDialog(DeltaDentalHomeActivity.this,
						"Processing");
				deltaDentalService.getLastVist(new GetLastVisitSuccess(),
						new GetLastVisitFailure());
			}
		});
		imgView.invalidate();

	}

	private class GetLastVisitSuccess extends Closure<DeltaDentalResponse> {

		@Override
		public void invoke(DeltaDentalResponse response) {
			// TODO Auto-generated method stub
			KDialog.hideLoadingDialog(DeltaDentalHomeActivity.this);
			Intent intent = new Intent(DeltaDentalHomeActivity.this,
					RecentVisitActivity.class);
			ParseObject lastClaim = response.getClaimInfo();
			intent.putExtra("patientName", lastClaim.getString("PatientName"));
			intent.putExtra("dentistName", lastClaim.getString("DentistName"));
			intent.putExtra("coveredCost", lastClaim.getInt("CoveredCost") + "");
			intent.putExtra("yourCost", lastClaim.getInt("YourCost") + "");
			intent.putExtra("serviceReceived",
					lastClaim.getString("ServiceReceived"));
			intent.putExtra("dateOfVisit", lastClaim.getDate("DateOfVisit")
					.toLocaleString());
			intent.putExtra("claimedAmount", response.getClaimedAmount());
			intent.putExtra("totalAmount", response.getTotalAmount());
			DeltaDentalHomeActivity.this.startActivity(intent);
		}

	}

	private class GetLastVisitFailure extends Closure<DeltaDentalResponse> {

		@Override
		public void invoke(DeltaDentalResponse response) {
			// TODO Auto-genlerated method stub

		}

	}

}
