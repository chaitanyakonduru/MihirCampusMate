package com.mms.mcm.activities;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_complaint_cell);
		initializeViews();
		MihirApp app=(MihirApp) getApplication();
		authenticateResponse=app.getCurUserInfo();

	}

	private void initializeViews() {

		addButton=(ImageView)findViewById(R.id.action_bar_image_add);
		addButton.setVisibility(View.VISIBLE);
		complaintNamesLayout = (LinearLayout) findViewById(R.id.layout_linearlayout_campus_cell_names);
		description=(EditText) findViewById(R.id.complaint_description);
		
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
			addView();
			break;
		case R.id.layout_complaint_cell_image_removename:
			View v1=complaintNamesLayout.findViewWithTag(v.getTag());
			complaintNamesLayout.removeView(v1);
			break;
		case R.id.complaint_cell_btn_send:
			showDialog(Constants.PROGRESSDIALOG);
			submitComplaint();
			break;
		default:
			break;
		}
	}

	private void submitComplaint() {
		int count=complaintNamesLayout.getChildCount();
		List<String> names=new ArrayList<String>();
		for (int i=0;i<count;i++) {
			View v=complaintNamesLayout.getChildAt(i);
			EditText et=(EditText) v.findViewById(R.id.layout_complaint_cell_edittext_name);
			names.add(et.getText().toString());
//			Log.v(TAG, et.getText().toString());
		}
		SoapServiceManager manager=SoapServiceManager.getInstance(ComplaintCellActivity.this);
		manager.sendComplaintCellDetailsRequest(authenticateResponse.getStudent_ID(), names, description.getText().toString(), callback);
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
