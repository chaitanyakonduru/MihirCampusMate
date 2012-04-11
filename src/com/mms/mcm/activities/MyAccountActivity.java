package com.mms.mcm.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.mms.mcm.R;
import com.mms.mcm.custom.Utils;
import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.network.NetworkCallback;

public class MyAccountActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {

	private static final String TAG = null;
	private String[] days_hours = { "Days", "Hour", "Never" };
	private Spinner spinner;
	private Spinner number_spinner;
	private AuthenticateResponse authenticateResponse;
	private TextView studentName;
	private TextView campusName;
	private ImageView logo;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_myaccount);
		MihirApp app = (MihirApp) getApplication();
		authenticateResponse= app.getCurUserInfo();
		
		initializeViews();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, days_hours);
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		
		
		
//		Utils.setActionBar(hospitalName, patientName, curPatient, schoolLogo);
		spinner.setSelection(2);
	}

	private void initializeViews() {

		findViewById(R.id.myaccount_btn_changepwd).setOnClickListener(
				MyAccountActivity.this);
		studentName=(TextView)findViewById(R.id.action_bar_tv_patient_name);
		campusName = (TextView) findViewById(R.id.action_tv_hospital_name);
		logo = (ImageView) findViewById(R.id.school_logo);
		Utils.setActionBar(campusName, studentName, authenticateResponse, null);
		findViewById(R.id.mms_ad_image).setOnClickListener(this);
		spinner = (Spinner) findViewById(R.id.myaccount_spinner_logouttime);
		number_spinner = (Spinner) findViewById(R.id.numbers_spinner);
		spinner.setOnItemSelectedListener(this);
		number_spinner.setOnItemSelectedListener(this);
		((TextView) findViewById(R.id.myaccount_tv_username))
		.setText(getUserName());
		((TextView)findViewById(R.id.credits_achieved)).setText(authenticateResponse.getCredits_Acheived());
		((TextView)findViewById(R.id.myacc_cgpa)).setText(authenticateResponse.getcGPA());
		((TextView)findViewById(R.id.myacc_feedue)).setText(authenticateResponse.getFee_Due());
		((TextView)findViewById(R.id.myacc_duetime)).setText(authenticateResponse.getFee_DueDate());
	}

	private String getUserName() {
		String username = "";
			username = authenticateResponse.getStudent_Name();
		return username;
	}

	final NetworkCallback<Object> adcallback = new NetworkCallback<Object>() {

		public void onSuccess(Object object) {

		}

		public void onFailure(String errorMessge) {

		}
	};

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myaccount_btn_changepwd:
			startActivity(new Intent(MyAccountActivity.this,
					ChangePwdActivity.class));
			break;
		case R.id.mms_ad_image:
			startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("http://www.mihirmobile.com/")));
			break;
		default:
			break;
		}

	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long id) {

		if (arg0.getItemAtPosition(arg2).toString().equals("Days")) {
			number_spinner.setVisibility(View.VISIBLE);
			String days[] = new String[10];
			for (int i = 0; i < 10; i++) {
				days[i] = String.valueOf(i + 1);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					MyAccountActivity.this,
					android.R.layout.simple_spinner_item, days);
			adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			number_spinner.setAdapter(adapter);
			
		}

		else if (arg0.getItemAtPosition(arg2).toString().equals("Hour")) {
			number_spinner.setVisibility(View.VISIBLE);
			String days[] = new String[23];
			for (int i = 0; i < 23; i++) {
				days[i] = String.valueOf(i + 1);
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					MyAccountActivity.this,
					android.R.layout.simple_spinner_item, days);
			adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			number_spinner.setAdapter(adapter);
			Log.v(TAG, "Hours is selected");

		} else if (arg0.getItemAtPosition(arg2).toString().equals("Never")) {
			number_spinner.setVisibility(View.GONE);
			Log.v(TAG, "Never Selected");
		}
		editPreferences();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v(TAG, "On Pause");
		
	}

	private void editPreferences() {
		Long logoutTime = 0L;
		if (spinner.getSelectedItem().toString().equalsIgnoreCase("Days")) {
			logoutTime = System.currentTimeMillis()
					+ Long.valueOf(number_spinner.getSelectedItem().toString())
					* 24 * 60 * 60 * 1000;
		} else if (spinner.getSelectedItem().toString().equalsIgnoreCase(
				"Hours")) {
			if (number_spinner.getSelectedItem() != null) {
				logoutTime = System.currentTimeMillis()
						+ Long.valueOf(number_spinner.getSelectedItem()
								.toString()) * 60 * 60 * 1000;
			}
		} else if (spinner.getSelectedItem().toString().equalsIgnoreCase(
				"Never")) {
			logoutTime = System.currentTimeMillis()+14*24*60*60*1000;
		}

		LoginActivity.mMyPrefs = this.getSharedPreferences("userPrefs",
				MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = LoginActivity.mMyPrefs.edit();
		prefsEditor.putLong(LoginActivity.LOGOUTTIME, logoutTime);
		prefsEditor.commit();
	}

}
