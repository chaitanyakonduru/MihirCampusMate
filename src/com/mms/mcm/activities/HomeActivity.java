package com.mms.mcm.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mms.mcm.R;
import com.mms.mcm.custom.Utils;
import com.mms.mcm.model.AuthenticateResponse;

public class HomeActivity extends Activity implements OnClickListener {
	private static final String TAG = "Home Activity";
	private TextView campusName;
	private ImageView logo;
	private Button singout;
	private MihirApp app;
	private AuthenticateResponse curUser;
	private TextView studentName;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home);
		app = (MihirApp) getApplication();
		curUser = app.getCurUserInfo();
		initViews();
		Utils.setActionBar(campusName, null, curUser, logo);
		
		try {
			Log.v(TAG, curUser.getStudent_ID());
		} 
		catch (Exception e) {
			Log.v(TAG, "Something wrong");
		}
	}

	private void initViews() {

		findViewById(R.id.home_tv_grades).setOnClickListener(
				HomeActivity.this);
		findViewById(R.id.home_tv_homework_projects).setOnClickListener(
				HomeActivity.this);
		findViewById(R.id.home_tv_library).setOnClickListener(
				HomeActivity.this);
		findViewById(R.id.home_tv_notifications).setOnClickListener(
				HomeActivity.this);
		findViewById(R.id.home_tv_myaccount).setOnClickListener(
				HomeActivity.this);
		findViewById(R.id.home_tv_complaint_cell).setOnClickListener(
				HomeActivity.this);
		findViewById(R.id.mms_ad_image).setOnClickListener(this);
		singout = (Button) findViewById(R.id.action_bar_signout);
		singout.setVisibility(View.VISIBLE);
		singout.setOnClickListener(HomeActivity.this);
		studentName=(TextView)findViewById(R.id.action_bar_tv_patient_name);
		campusName = (TextView) findViewById(R.id.action_tv_hospital_name);
		logo = (ImageView) findViewById(R.id.school_logo);
	}

	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.home_tv_grades:

			startActivity(new Intent(HomeActivity.this,
					GradesActivity.class));
			break;
		case R.id.home_tv_homework_projects:
			startActivity(new Intent(HomeActivity.this, HomeWorkProjectsActivity.class));
			break;
		case R.id.home_tv_notifications:

			startActivity(new Intent(HomeActivity.this,
					NotificationsActivity.class));
			break;
		case R.id.home_tv_library:

			startActivity(new Intent(HomeActivity.this, LibraryActivity.class));
			break;
		case R.id.home_tv_myaccount:

			startActivity(new Intent(HomeActivity.this, MyAccountActivity.class));
			break;
		case R.id.home_tv_complaint_cell:
			startActivity(new Intent(HomeActivity.this,ComplaintCellActivity.class));
			break;
		case R.id.action_bar_signout:
			clearPreferences();
			app.setCurUserInfo(null);
			app.setIsloggedin(false);
			/*Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);*/
			finish();
			break;
		case R.id.mms_ad_image:
			startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("http://www.mihirmobile.com/")));
			break;
		default:
			break;
		}

	}

	private void clearPreferences() {
		LoginActivity.mMyPrefs = this.getSharedPreferences("userPrefs",
				MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = LoginActivity.mMyPrefs.edit();
		prefsEditor.putLong(LoginActivity.LOGOUTTIME, 0L);
		prefsEditor.putString("Response", "");
		prefsEditor.commit();
	}

	protected void onRestart() {
		Log.v(TAG, "OnREstart");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.v(TAG, "OnResume");
		super.onResume();
	}

	protected void onStart() {
		Log.v(TAG, "On Start");
		super.onStart();
	}

	@Override
	protected void onPause() {
		Log.v(TAG, "On Pause");
		super.onPause();
	}

}