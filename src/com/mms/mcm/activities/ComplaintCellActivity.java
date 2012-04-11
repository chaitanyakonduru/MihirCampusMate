package com.mms.mcm.activities;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mms.mcm.R;
import com.mms.mcm.custom.Constants;
import com.mms.mcm.custom.Utils;
import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.network.NetworkCallback;
import com.mms.mcm.network.Parser;
import com.mms.mcm.network.SoapServiceManager;

public class ComplaintCellActivity extends Activity implements OnClickListener {

	private static final String TAG = null;
	private LinearLayout complaintNamesLayout;
	private EditText description;
	private  Integer i = 1;
	private AuthenticateResponse authenticateResponse;
	private ImageView addButton;
	private TextView campusEmergencyNo;
	private TextView campusPoliceNo;
	private TextView localPoliceNo;
	private TextView studentName;
	private TextView campusName;
	private ImageView logo;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_complaint_cell);
		MihirApp app=(MihirApp) getApplication();
		authenticateResponse=app.getCurUserInfo();
		initializeViews();
		Utils.setActionBar(campusName, studentName, authenticateResponse, null);
	}

	private void initializeViews() {

		addButton=(ImageView)findViewById(R.id.action_bar_image_add);
		addButton.setVisibility(View.VISIBLE);
		addButton.setOnClickListener(ComplaintCellActivity.this);
		complaintNamesLayout = (LinearLayout) findViewById(R.id.layout_linearlayout_campus_cell_names);
		description=(EditText) findViewById(R.id.complaint_description);
		campusEmergencyNo=(TextView) findViewById(R.id.campus_emergency_no);
		campusPoliceNo=(TextView) findViewById(R.id.campus_police_no);
		localPoliceNo=(TextView) findViewById(R.id.local_police_no);
		
		campusPoliceNo.setText(authenticateResponse.getCampus_Police_number());
		localPoliceNo.setText(authenticateResponse.getCampus_Local_number());
		campusEmergencyNo.setText(authenticateResponse.getCampus_Emmergency_Number());
		
		campusPoliceNo.setOnClickListener(this);
		localPoliceNo.setOnClickListener(this);
		campusEmergencyNo.setOnClickListener(this);
		studentName=(TextView)findViewById(R.id.action_bar_tv_patient_name);
		campusName = (TextView) findViewById(R.id.action_tv_hospital_name);
		logo = (ImageView) findViewById(R.id.school_logo);

		
		findViewById(R.id.complaint_cell_btn_send).setOnClickListener(ComplaintCellActivity.this);
	}
	

	final NetworkCallback<Object> callback=new NetworkCallback<Object>() {
		
		public void onSuccess(Object object) {
			removeDialog(Constants.PROGRESSDIALOG);
			try
			{
				String msg=Parser.parseComplaintCellResponse((SoapObject)object);
				Utils.showDialog(msg, ComplaintCellActivity.this, false);
			}
			catch(ClassCastException cce)
			{
				Utils.showToast("Unable to process your request", ComplaintCellActivity.this);
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
	

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.action_bar_image_add:
			
			if(complaintNamesLayout.getChildCount()<5)
			{
			addView();
			}
			else
			{
				Utils.showToast("You can not add more than 5 names", ComplaintCellActivity.this);
			}
			break;
		case R.id.layout_complaint_cell_image_removename:
			View v1=complaintNamesLayout.findViewWithTag(v.getTag());
			complaintNamesLayout.removeView(v1);
			break;
		case R.id.complaint_cell_btn_send:
			showDialog(Constants.PROGRESSDIALOG);
			submitComplaint();
			break;
		case R.id.campus_emergency_no:
			makecall(campusEmergencyNo.getText().toString());
			break;
		case R.id.campus_police_no:
			makecall(campusPoliceNo.getText().toString());
			break;
		case R.id.local_police_no:
			makecall(localPoliceNo.getText().toString());
			break;
		default:
			break;
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case Constants.PROGRESSDIALOG:
			final ProgressDialog progressDialog = new ProgressDialog(
					ComplaintCellActivity.this);
			progressDialog.setMessage("Please wait....");
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
	

	private void makecall(String string) {
		startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+string)));	
	}

	private void submitComplaint() {
		boolean b=true;
		int count=complaintNamesLayout.getChildCount();
		List<String> names=new ArrayList<String>();
		for (int i=0;i<count;i++) {
			View v=complaintNamesLayout.getChildAt(i);
			EditText et=(EditText) v.findViewById(R.id.layout_complaint_cell_edittext_name);
			if(et.getText().toString().trim().equals(""))
			{
				b=false;
				et.setError("Please give the name");
				
			}
			else
			{
			names.add(et.getText().toString());
			}
			
		}
		if(description.getText().toString().trim().equals(""))
		{
			b=false;
			description.setError("Please give the description");
		}
		
		if(b)
		{
		SoapServiceManager manager=SoapServiceManager.getInstance(ComplaintCellActivity.this);
		manager.sendComplaintCellDetailsRequest(authenticateResponse.getStudent_ID(), names, description.getText().toString(), callback);
		}
	}

	private void addView() {
		View view = LayoutInflater.from(ComplaintCellActivity.this)
				.inflate(R.layout.complaint_cell_name_layout, null);
		EditText name=(EditText) view.findViewById(R.id.layout_complaint_cell_edittext_name);
		ImageView removeNameBtn = (ImageView) view
				.findViewById(R.id.layout_complaint_cell_image_removename);
		removeNameBtn.setTag(i);
		removeNameBtn.setOnClickListener(ComplaintCellActivity.this);
		view.setTag(i);
		i++;
		complaintNamesLayout.addView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Send");
		menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "Add Name");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			submitComplaint();
			return true;
		} else if (item.getItemId() == 2) {
			addView();
			return true;

		} else {
			return false;
		}

	}

}
