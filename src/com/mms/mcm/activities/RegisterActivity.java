package com.mms.mcm.activities;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mms.mcm.R;
import com.mms.mcm.custom.Constants;
import com.mms.mcm.custom.Utils;
import com.mms.mcm.network.NetworkCallback;
import com.mms.mcm.network.Parser;
import com.mms.mcm.network.SoapServiceManager;

public class RegisterActivity extends Activity implements OnClickListener {

	private static final String TAG = "RegisterActivity";
	private String from;
	private EditText mmsId;
	private Button button;
	private TextView headerText;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_forgotpwd_signup);
		initializeViews();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			from = bundle.getString("from");
			if ("register".equals(from)) {
				headerText.setText("REGISTER");
				button.setText("Register");
			} else if ("forgotpwd".equals(from)) {
				headerText.setText("FORGOT PASSWORD");
				button.setText("Send");
			}

		} else {
			return;
		}
	}

	private void initializeViews() {
		mmsId = (EditText) findViewById(R.id.signup_edittext_mmsid);
		button = (Button) findViewById(R.id.signup_btn_register);
		button.setOnClickListener(RegisterActivity.this);
		headerText = (TextView) findViewById(R.id.signup_headertext);
		findViewById(R.id.mms_ad_image).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.mihirmobile.com/")));
			}
		});
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.signup_btn_register:
			String userid = mmsId.getText().toString();
			SoapServiceManager serviceManager = SoapServiceManager
					.getInstance(RegisterActivity.this);
			if (TextUtils.isEmpty(userid)) {
				Utils.showDialog(Constants.FIELDSREQUIRED,
						RegisterActivity.this, false);
			} else {
				showDialog(Constants.PROGRESSDIALOG);
				if (from.equalsIgnoreCase("register")) {
					serviceManager.sendregisterRequest(userid, callback);
				} else if (from.equalsIgnoreCase("forgotpwd")) {
					serviceManager.sendforgotPasswordRequest(userid, callback);
				}
 
			}

			break;
		default:
			break;
		}
	}

	NetworkCallback<Object> callback = new NetworkCallback<Object>() {

		public void onSuccess(Object object) {
			removeDialog(Constants.PROGRESSDIALOG);
			try
			{
			String msg =Parser.parseRegister((SoapObject) object);
			Utils.showDialog(msg, RegisterActivity.this, false);
			}
			catch(ClassCastException cce)
			{
				Utils.showToast("Unable to process your request", RegisterActivity.this);
				Log.v(TAG, cce.getMessage());
			}
			catch(Exception e)
			{
				Log.v(TAG, e.getMessage());
			}
		}

		public void onFailure(String errorMessge) {
			removeDialog(Constants.PROGRESSDIALOG);
			Log.v(TAG, errorMessge);
		}
	};

	NetworkCallback<Object> adcallback = new NetworkCallback<Object>() {

		public void onSuccess(Object object) {
		}

		public void onFailure(String errorMessge) {
			Log.v(TAG, errorMessge);
		}
	};

	protected Dialog onCreateDialog(int id) {
		switch (id) {

		case Constants.PROGRESSDIALOG:
			final ProgressDialog progressDialog = new ProgressDialog(
					RegisterActivity.this);
			progressDialog.setMessage("Please Wait....");
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
		}

		return super.onCreateDialog(id);
	}

}
