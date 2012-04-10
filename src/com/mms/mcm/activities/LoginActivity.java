package com.mms.mcm.activities;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mms.mcm.R;
import com.mms.mcm.custom.Constants;
import com.mms.mcm.custom.Utils;
import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.network.NetworkCallback;
import com.mms.mcm.network.Parser;
import com.mms.mcm.network.SoapServiceManager;

public class LoginActivity extends Activity implements OnClickListener {

	private static final String TAG = "Login Activity";
	public static final String USERNAME = "username";
	public static final String LOGOUTTIME = "logouttime";
	public static final String USERDATA = "userdata";
	public static SharedPreferences mMyPrefs;
	private EditText mUserName;
	private EditText mPassword;
	private CheckBox mrememberMe;
	private AuthenticateResponse authenticate;
	private Boolean rememberme = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		initializeViews();

		/*mMyPrefs = this.getSharedPreferences("userPrefs", MODE_WORLD_WRITEABLE);
//		mUserName.setText(mMyPrefs.getString(USERNAME, ""));

		Long logoutTime = mMyPrefs.getLong(LOGOUTTIME, 0L);
		if (logoutTime > System.currentTimeMillis()) {
		ObjectInputStream ois=null;
		FileInputStream fileInputStream=null;
		try {
			
			fileInputStream=openFileInput("persistentData.txt");
			ois=new ObjectInputStream(fileInputStream);
			Object object=ois.readObject();
			Log.v(TAG, "+++++++Persistent Response++++++++");
			
			if(object instanceof AuthenticateResponse)
			{
				AuthenticateResponse authenticateResponse=(AuthenticateResponse) object;
				processResponseResult(authenticateResponse);
			}
			
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		} catch (StreamCorruptedException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		catch(NullPointerException npe)
		{
			Log.v(TAG, "Null Pointer Exception Occured");
		}
		finally
		{
			try
			{
				if(fileInputStream!=null)
				{
					fileInputStream.close();
					fileInputStream=null;
				}
				if(ois!=null)
				{
					ois.close();
					ois=null;
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		}*/
		
		
/*		if (logoutTime > System.currentTimeMillis()) {
			response = mMyPrefs.getString("Response", "");
			if (response.equalsIgnoreCase("")) {
			} else {
				Log.v(TAG, "Persistant Response:::" + response);
				SoapObject object = new SoapObject(
						"http://mihirmobile.com/MIHIRHealthSrv/", mMyPrefs
								.getString("Response", ""));
				Log.v(TAG, object.hasProperty("MMS_Staff_Response") + "");

				try {
					authenticate = Comm.parseAuthenticate(object);
					Log.v(TAG, authenticate.getmUserType() + "");
					processResponseResult(authenticate);
				} catch (ClassCastException cce) {
					Log.v(TAG, cce.getMessage());
				} catch (Exception e) {
					Log.v(TAG, e.getMessage());
				}
			}
		}*/

	}

	private void initializeViews() {

		mUserName = (EditText) findViewById(R.id.login_edittext_username);
		mPassword = (EditText) findViewById(R.id.login_edittext_password);
		mrememberMe = (CheckBox) findViewById(R.id.login_checkbox_rememberme);

		findViewById(R.id.login_btn_login).setOnClickListener(
				LoginActivity.this);
		findViewById(R.id.login_btn_forgotpwd).setOnClickListener(
				LoginActivity.this);
		findViewById(R.id.login_btn_register).setOnClickListener(
				LoginActivity.this);
		findViewById(R.id.mms_ad_image).setOnClickListener(this);
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.login_btn_login:
			SoapServiceManager serviceManager = SoapServiceManager
					.getInstance(LoginActivity.this);
			String userName = mUserName.getText().toString().trim();
			String password = mPassword.getText().toString().trim();
			if (mrememberMe.isChecked()) {
				rememberme = true;
			} else {
				rememberme = false;
			}
			if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {

				Utils.showDialog(Constants.FIELDSREQUIRED, LoginActivity.this,
						false);
			}

			else {
				showDialog(Constants.PROGRESSDIALOG);
				String deviceId = getDeiviceID();
				serviceManager.sendAuthenticateRequest(userName, password,
						deviceId, callback);
			}
			break;

		case R.id.login_btn_forgotpwd:
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			intent.putExtra("from", "forgotpwd");
			startActivity(intent);
			break;

		case R.id.login_btn_register:
			intent = new Intent(LoginActivity.this, RegisterActivity.class);
			intent.putExtra("from", "register");
			startActivity(intent);
			break;
		case R.id.mms_ad_image:
			startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("http://www.mihirmobile.com/")));
			break;
		default:

			break;
		}

	}

	private String getDeiviceID() {

		TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String deviceId = manager.getDeviceId();
		return deviceId != null ? deviceId : "";
	}

	final NetworkCallback<Object> callback = new NetworkCallback<Object>() {
		public void onSuccess(Object responseObj) {
			removeDialog(Constants.PROGRESSDIALOG);
			FileOutputStream fileOutputStream = null;
			ObjectOutputStream objectOutputStream = null;
			try {

				SoapObject responceObject = (SoapObject) responseObj;
				authenticate = Parser.parseAuthenticateResponse(responceObject);
				/*fileOutputStream=openFileOutput("persistentData.txt", MODE_PRIVATE);
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(authenticate);
				fileOutputStream.flush();*/
				
				int userType = authenticate.getUserType();
				if (userType != Constants.INVALIDUSER) {
					rememberMe();
				}
				processResponseResult(authenticate);
			} catch (ClassCastException cce) {
				Utils.showToast("Unable to process your request",
						LoginActivity.this);
				Log.v(TAG, cce.getMessage());
			 
			} 
			/*finally {
				try {
					if (objectOutputStream != null) {
						objectOutputStream.close();
						objectOutputStream = null;
					}
					if(fileOutputStream!=null)
					{
						fileOutputStream.close();
						fileOutputStream=null;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}*/

		}

		public void onFailure(String errorMessge) {
			removeDialog(Constants.PROGRESSDIALOG);
			Utils.showToast("Unable to process your request",
					LoginActivity.this);
		}
	};

	NetworkCallback<Object> adcallback = new NetworkCallback<Object>() {

		public void onSuccess(Object object) {

		}

		public void onFailure(String errorMessge) {
			Log.v(TAG, errorMessge);
		}
	};
	private MihirApp app;
	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case Constants.PROGRESSDIALOG:
			final ProgressDialog progressDialog = new ProgressDialog(
					LoginActivity.this);
			progressDialog.setMessage("Signing in....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (!progressDialog.isShowing()) {
						progressDialog.show();
					}
				}
			});
			return progressDialog;
		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	private void rememberMe() {
		mMyPrefs = this.getSharedPreferences("userPrefs", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = mMyPrefs.edit();
		if (rememberme) {
			prefsEditor.putString(LoginActivity.USERNAME, mUserName.getText()
					.toString());
		}
		prefsEditor.putLong(LoginActivity.LOGOUTTIME, (System
				.currentTimeMillis() + (14 * 24 * 60 * 60 * 1000)));
		prefsEditor.commit();
	}

	private void processResponseResult(AuthenticateResponse authenticate) {
		
		app = (MihirApp) getApplication();
		int userType = authenticate.getUserType();
		if (authenticate.getAuthenticateMSG().equalsIgnoreCase("")) {
			switch (userType) {
			
			case Constants.STUDENT:
				
				app.setIsloggedin(true);
				app.setCurUserInfo(authenticate);
				startActivity(new Intent(LoginActivity.this, HomeActivity.class));
				
				break;
			
			}
		} else {
			Utils.showDialog(authenticate.getAuthenticateMSG(), LoginActivity.this,
					false);
			app.setIsloggedin(false);
		}
	}

	
	protected void onRestart() {
	
		super.onRestart();
		Log.v(TAG, "+++++++ Restart+++++++++");
		/*if(app.isIsloggedin())
		{
			finish();
		}*/
	}
	
	protected void onResume() {
		super.onResume();
		
		Log.v(TAG, "+++++++ Resume+++++++++");
	}
	
	
	protected void onStart() {
		super.onStart();
		Log.v(TAG, "+++++++== On Start+++++++");
	}

	protected void onPause() {
		super.onPause();
		Log.v(TAG, "+++++++== On Pause+++++++");
	}
}