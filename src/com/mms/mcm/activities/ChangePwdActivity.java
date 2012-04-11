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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mms.mcm.R;
import com.mms.mcm.custom.Constants;
import com.mms.mcm.custom.Utils;
import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.network.NetworkCallback;
import com.mms.mcm.network.Parser;
import com.mms.mcm.network.SoapServiceManager;

public class ChangePwdActivity extends Activity implements OnClickListener{
	
	protected static final String TAG = "ChangePasswordActivity";
	private EditText mmsId;
	private EditText oldPwd;
	private EditText newPwd;
	private EditText confPwd;
	
	private AuthenticateResponse authenticateResponse;
	private TextView studentName;
	private TextView campusName;
	private ImageView logo;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.layout_change_password);
		initializeViews();
		MihirApp app=(MihirApp) getApplication();
		authenticateResponse=app.getCurUserInfo();
		Utils.setActionBar(campusName, studentName, authenticateResponse, null);
		
	}
	private  void initializeViews() {
		mmsId=(EditText)findViewById(R.id.changepwd_edittext_mmsid);
		oldPwd=(EditText)findViewById(R.id.change_pwd_edittext_oldpwd);
		newPwd=(EditText)findViewById(R.id.change_pwd_edittext_newpwd);
		confPwd=(EditText)findViewById(R.id.change_pwd_edittext_confpwd);
		findViewById(R.id.change_pwd_btn_change).setOnClickListener(ChangePwdActivity.this);
		findViewById(R.id.mms_ad_image).setOnClickListener(this);
		studentName=(TextView)findViewById(R.id.action_bar_tv_patient_name);
		campusName = (TextView) findViewById(R.id.action_tv_hospital_name);
		logo = (ImageView) findViewById(R.id.school_logo);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_pwd_btn_change:

			SoapServiceManager manager=SoapServiceManager.getInstance(ChangePwdActivity.this);
			if(isEmpty(mmsId)||isEmpty(oldPwd)||isEmpty(newPwd)||isEmpty(confPwd))
			{
				Utils.showDialog(Constants.FIELDSREQUIRED,ChangePwdActivity.this,false);
			}
			else if(!TextUtils.equals(newPwd.getText().toString().trim(), confPwd.getText().toString().trim()))
			{
				Utils.showDialog(Constants.FIELDSNOTMATCH, ChangePwdActivity.this, false);
			}
			else if(TextUtils.equals(oldPwd.getText().toString().trim(), newPwd.getText().toString().trim()))
			{
				Utils.showDialog("New Password should not be OldPassword", ChangePwdActivity.this, false);
			}
			else
			{	showDialog(Constants.PROGRESSDIALOG);
				manager.sendchangePasswordRequest(mmsId.getText().toString(), oldPwd.getText().toString(), newPwd.getText().toString(), callback);
			}
			break;
		case R.id.mms_ad_image:
			startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.mihirmobile.com/")));
		break;
		default:
			break;
		}
	}
	
	
	final NetworkCallback<Object> callback=new NetworkCallback<Object>() {
		
		public void onSuccess(Object object) {
			removeDialog(Constants.PROGRESSDIALOG);
			try
			{
				String msg=Parser.parseChangePwd((SoapObject)object);
				Utils.showDialog(msg, ChangePwdActivity.this, false);
			}
			catch(ClassCastException cce)
			{
				Utils.showToast("Unable to process your request", ChangePwdActivity.this);
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
	

	NetworkCallback<Object> adcallback=new NetworkCallback<Object>() {
		
		public void onSuccess(Object object) {
			
		}
		public void onFailure(String errorMessge) {
			Log.v(TAG, errorMessge);
		}
	};
	
	
	
	private Boolean isEmpty(EditText editText)
	{
		return TextUtils.isEmpty(editText.getText().toString());
	}
	
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Constants.PROGRESSDIALOG:
				final ProgressDialog progressDialog=new ProgressDialog(ChangePwdActivity.this);
				progressDialog.setMessage("Please Wait....");
				progressDialog.show();
				progressDialog.setOnCancelListener(new OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						if(!progressDialog.isShowing())
						{
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
	
	
	

}
