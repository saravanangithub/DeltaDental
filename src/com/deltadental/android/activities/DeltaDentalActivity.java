package com.deltadental.android.activities;

import org.json.JSONException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.deltadental.android.R;
import com.deltadental.android.commons.Closure;
import com.deltadental.android.exception.LocationException;
import com.deltadental.android.models.DeltaDentalResponse;
import com.deltadental.android.models.Weather;
import com.deltadental.android.services.DeltaDentalService;
import com.deltadental.android.services.LocationService;
import com.deltadental.android.services.WeatherHttpClient;
import com.deltadental.android.utils.JSONWeatherParser;
import com.deltadental.android.utils.KDialog;
import com.parse.ParseGeoPoint;

public class DeltaDentalActivity extends SherlockActivity {

	private EditText edtTxtUserName;
	private EditText edtTxtpassword;
	private Button btnLogin;
	private DeltaDentalService deltaDentalService;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delta_dental);

		edtTxtUserName = (EditText) findViewById(R.id.edtTxtMemberIdLogin);
		edtTxtpassword = (EditText) findViewById(R.id.edtTxtPasswordLogin);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		deltaDentalService = DeltaDentalService.getInstance(this, getIntent());
		intent = new Intent(
				this,
				com.deltadental.android.activities.DeltaDentalHomeActivity.class);
		LocationService.getInstance(this);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				KDialog.showLoadingDialog(DeltaDentalActivity.this,
						"Signing in..");
				deltaDentalService.signIn(edtTxtUserName.getText().toString(),
						edtTxtpassword.getText().toString(),
						new OnLoginSuccess(), new OnLoginFailure());

			}
		});

		/*
		 * Button btnLogin=(Button)findViewById(R.id.login);
		 * 
		 * /btnLogin.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent=new
		 * Intent(DeltaDentalActivity.this,com.deltadental
		 * .android.activities.LoginActivity.class); startActivity(intent);
		 * 
		 * } });
		 */

		Button btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						DeltaDentalActivity.this,
						com.deltadental.android.activities.RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	private class OnLoginSuccess extends Closure<DeltaDentalResponse> {
		@Override
		public void invoke(DeltaDentalResponse response) {
			LocationService locationService = LocationService
					.getInstance(DeltaDentalActivity.this);
			ParseGeoPoint parseGeoPoint = null;
			try {
				parseGeoPoint = locationService.getLocation();
			} catch (LocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String latitude = parseGeoPoint.getLatitude() + "";
			String longitude = parseGeoPoint.getLongitude() + "";
			JSONWeatherTask task = new JSONWeatherTask();
			task.execute(new String[] { latitude, longitude });
			intent.putExtra("userName", response.getUserName());
			SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("UserName", response.getUserName());
			editor.commit();
			/*
			 * KDialog.hideLoadingDialog(DeltaDentalActivity.this); Intent
			 * intent=new
			 * Intent(DeltaDentalActivity.this,com.deltadental.android
			 * .activities.DeltaDentalHomeActivity.class);
			 * 
			 * intent.putExtra("userName", response.getUserName());
			 * 
			 * startActivity(intent);
			 */

		}
	}

	private class OnLoginFailure extends Closure<DeltaDentalResponse> {
		@Override
		public void invoke(DeltaDentalResponse response) {

			KDialog.hideLoadingDialog(DeltaDentalActivity.this);
			KDialog.showMessage(DeltaDentalActivity.this, response.getError());

		}
	}

	private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

		@Override
		protected Weather doInBackground(String... params) {
			Weather weather = new Weather();
			try {

				String data = ((new WeatherHttpClient()).getWeatherData(
						params[0], params[1]));

				// if(data==null){
				// Toast.makeText(MainActivity.this,"data"+data,Toast.LENGTH_LONG).show();
				// }
				try {
					weather = JSONWeatherParser.getWeather(data);

					// Let's retrieve the icon
					weather.iconData = ((new WeatherHttpClient())
							.getImage(weather.currentCondition.getIcon()));

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return weather;

		}

		@Override
		protected void onPostExecute(Weather weather) {
			super.onPostExecute(weather);

			KDialog.hideLoadingDialog(DeltaDentalActivity.this);
			// Intent intent=new
			// Intent(DeltaDentalActivity.this,com.deltadental.android.activities.DeltaDentalHomeActivity.class);

			SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("city", weather.location.getCity() + ","
					+ weather.location.getCountry());
			editor.putString("condition",
					weather.currentCondition.getCondition() + "("
							+ weather.currentCondition.getDescr() + ")");
			editor.putString("temparature",
					"" + Math.round((weather.temperature.getTemp() - 275.15))
							+ "°C");
			editor.putString("humidity",
					"" + weather.currentCondition.getHumidity() + "%");
			editor.putString("pressure",
					"" + weather.currentCondition.getPressure() + " hPa");
			editor.putString("windspeed", "" + weather.wind.getSpeed() + " mps");
			editor.putString("windDegree", "" + weather.wind.getDeg() + "°");
			editor.putString("image",new String(Base64.encode(weather.iconData,1)));
			editor.putBoolean("oneKmDistance", false);
			editor.putBoolean("twoKmDistance", true);   
			//String string=new String(Base64.encode(weather.iconData,1)); 
			//byte[] restoredBytes = Base64.decode(string.getBytes(),1); 
			//Toast.makeText(DeltaDentalActivity.this, weather.iconData.length+" : "+restoredBytes.length, Toast.LENGTH_SHORT).show();
			editor.commit();
			intent.putExtra("city", weather.location.getCity() + ","
					+ weather.location.getCountry());
			intent.putExtra("condition",
					weather.currentCondition.getCondition() + "("
							+ weather.currentCondition.getDescr() + ")");
			intent.putExtra("temparature",
					"" + Math.round((weather.temperature.getTemp() - 275.15))
							+ "°C");
			intent.putExtra("humidity",
					"" + weather.currentCondition.getHumidity() + "%");
			intent.putExtra("pressure",
					"" + weather.currentCondition.getPressure() + " hPa");
			intent.putExtra("windspeed", "" + weather.wind.getSpeed() + " mps");
			intent.putExtra("windDegree", "" + weather.wind.getDeg() + "°");
			intent.putExtra("image", weather.iconData);
			startActivity(intent);

			/*
			 * if (weather.iconData != null && weather.iconData.length > 0) {
			 * Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0,
			 * weather.iconData.length); imgView.setImageBitmap(img); }
			 * 
			 * cityText.setText(weather.location.getCity() + "," +
			 * weather.location.getCountry());
			 * condDescr.setText(weather.currentCondition.getCondition() + "(" +
			 * weather.currentCondition.getDescr() + ")"); temp.setText("" +
			 * Math.round((weather.temperature.getTemp() - 275.15)) + "°C");
			 * hum.setText("" + weather.currentCondition.getHumidity() + "%");
			 * press.setText("" + weather.currentCondition.getPressure() +
			 * " hPa"); windSpeed.setText("" + weather.wind.getSpeed() +
			 * " mps"); windDeg.setText("" + weather.wind.getDeg() + "°");
			 */
		}

		/*
		 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate
		 * the menu; this adds items to the action bar if it is present.
		 * getMenuInflater().inflate(R.menu.delta_dental, menu); return true; }
		 */
	}
}
