package com.mms.mcm.activities;

import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.mms.mcm.R;
import com.mms.mcm.custom.Constants;
import com.mms.mcm.custom.HomeWorksAdapter;
import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.model.HomeWorkProjectsResponse;
import com.mms.mcm.model.Projects;
import com.mms.mcm.network.NetworkCallback;
import com.mms.mcm.network.Parser;
import com.mms.mcm.network.SoapServiceManager;

public class HomeWorkProjectsActivity extends Activity{
	
	private AuthenticateResponse authenticateResponse;
	protected String TAG;
	
	private ListView listview;
	protected List<Projects> prjctsList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home_work_projects);
		initializeViews();
		MihirApp app=(MihirApp) getApplication();
		authenticateResponse=app.getCurUserInfo();
		SoapServiceManager manager=SoapServiceManager.getInstance(HomeWorkProjectsActivity.this);
		showDialog(Constants.PROGRESSDIALOG);
		manager.sendGetHomeWorkProjectsRequest(authenticateResponse.getStudent_ID(), callback);
	}

	private void initializeViews() {
		listview=(ListView)findViewById(R.id.home_work_projects_listview);
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case Constants.PROGRESSDIALOG:
			final ProgressDialog progressDialog = new ProgressDialog(
					HomeWorkProjectsActivity.this);
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
	
	private NetworkCallback<Object> callback=new NetworkCallback<Object>() {
		
		public void onSuccess(Object object) {
			removeDialog(Constants.PROGRESSDIALOG);
			try
			{
				Log.v(TAG, object.toString());
				HomeWorkProjectsResponse response=Parser.parseHomeWorkProjectsResponse((SoapObject)object);
				if(response.getErrorMsg().equals(""))
				{
					prjctsList=response.getProjectsList();
					if(prjctsList!=null &&prjctsList.size()>0)
					{
						listview.setAdapter(new HomeWorksAdapter(HomeWorkProjectsActivity.this,-1,prjctsList));
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void onFailure(String errorMessge) {
			removeDialog(Constants.PROGRESSDIALOG);
			Log.v(TAG, errorMessge);
		}
	};

}
