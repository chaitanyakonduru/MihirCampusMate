package com.mms.mcm.activities;

import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.mms.mcm.R;
import com.mms.mcm.custom.Constants;
import com.mms.mcm.custom.Utils;
import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.model.CampusCalendarResponse;
import com.mms.mcm.model.NotificationResponse;
import com.mms.mcm.model.Projects;
import com.mms.mcm.network.NetworkCallback;
import com.mms.mcm.network.Parser;
import com.mms.mcm.network.SoapServiceManager;

public class NotificationsActivity extends TabActivity implements OnTabChangeListener,OnItemClickListener{
	
	protected static final String TAG = null;
	private TabHost tabHost;
	private SoapServiceManager manager;
	private List<Projects> prjctsList;
	
	private AuthenticateResponse authenticateResponse;
	private ListView notificationListView;
	private ListView campusCalendarListView;
	private TextView studentName;
	private TextView campusName;
	private ImageView logo;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_layout_notifications);
		initializeViews();
		MihirApp app=(MihirApp) getApplication();
		authenticateResponse=app.getCurUserInfo();
		Utils.setActionBar(campusName, studentName, authenticateResponse, null);
		manager=SoapServiceManager.getInstance(NotificationsActivity.this);
		tabHost=getTabHost();
		setupTab("Campus Calendar",R.id.campus_calendar);
		setupTab("Notifications",R.id.notification);
		tabHost.setCurrentTab(0);
		if(tabHost.getCurrentTab()==0)
		{
			showDialog(Constants.PROGRESSDIALOG);
			manager.sendGetCampusCalenderDetailsRequest(authenticateResponse.getCampus_ID(), authenticateResponse.getStudent_ID(), campuscalendarCallBack);
		}
		tabHost.setOnTabChangedListener(NotificationsActivity.this);
		
	}

	private void setupTab(String string, int id) {

		TabSpec spec=tabHost.newTabSpec(string).setContent(id).setIndicator(makeTab(string));
		tabHost.addTab(spec);
	}

		private View makeTab(String tag) {
			int bgId;
			if ("Billing Info".equalsIgnoreCase(tag)) {
				bgId = R.drawable.left_tab_indicator;
			} else {
				bgId = R.drawable.right_tab_indicator;
			}

			View tabView = LayoutInflater.from(NotificationsActivity.this).inflate(
					R.layout.tab_indicator, null);
			tabView.setBackgroundResource(bgId);
			TextView tabText = (TextView) tabView.findViewById(R.id.text);
			tabText.setText(tag);
			return tabView;
		}
	
	private void initializeViews()  {
		notificationListView=(ListView) findViewById(R.id.notifications_listview);
		campusCalendarListView=(ListView) findViewById(R.id.campus_calendar_listview);
		notificationListView.setOnItemClickListener(NotificationsActivity.this);
		campusCalendarListView.setOnItemClickListener(NotificationsActivity.this);
		studentName=(TextView)findViewById(R.id.action_bar_tv_patient_name);
		campusName = (TextView) findViewById(R.id.action_tv_hospital_name);
		logo = (ImageView) findViewById(R.id.school_logo);
		
	}

	public void onTabChanged(String arg0) {
		
		if(tabHost.getCurrentTab()==0)
		{
			showDialog(Constants.PROGRESSDIALOG);
			manager.sendGetCampusCalenderDetailsRequest(authenticateResponse.getCampus_ID(), authenticateResponse.getStudent_ID(), campuscalendarCallBack);
		}
		else if(tabHost.getCurrentTab()==1)
		{
			showDialog(Constants.PROGRESSDIALOG);
			manager.sendGetNotificationDetailsRequest(authenticateResponse.getStudent_ID(), callback);
		}
	}
	
	/*@Override
	public void onTabChanged(String tabId) {
		if (tabHost.getCurrentTab() == 0) {
			showDialog(Constants.PROGRESSDIALOG);
			manager.sendPrescriptionsRequest(curPatient.getPatient_HistoryId(),
					prescriptioncallback);
		}
		if (tabHost.getCurrentTab() == 1) {
			showDialog(Constants.PROGRESSDIALOG);
			manager.sendTestsnProceduresRequest(curPatient
					.getPatient_HistoryId(), testscallback);
		}

	}*/
	
	private NetworkCallback<Object> callback=new NetworkCallback<Object>() {
		
		
		private List<Projects> prjctsList;

		public void onSuccess(Object object) {
			removeDialog(Constants.PROGRESSDIALOG);
			try
			{
				Log.v(TAG, object.toString());
				NotificationResponse response=Parser.parseNotificationReponse((SoapObject)object);
				if(response.getErrorMessage().equals(""))
				{
					prjctsList=response.getNotificationList();
					if(prjctsList!=null &&prjctsList.size()>0)
					{
						notificationListView.setAdapter(new ArrayAdapter<Projects>(NotificationsActivity.this,R.layout.layout_dietcarelisttext,prjctsList));
						notificationListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								
								Intent intent=new Intent(NotificationsActivity.this,ProjectDetailsActivity.class);
//								Utils.showToast(""+arg2, NotificationsActivity.this);
								intent.putExtra("myObject", prjctsList.get(arg2));
								startActivity(intent);
								
							}
							
						});
					}
				}
				else
				{
					Utils.showDialog(response.getErrorMessage(), NotificationsActivity.this, false);
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
	
private NetworkCallback<Object> campuscalendarCallBack=new NetworkCallback<Object>() {
		
		
	

		public void onSuccess(Object object) {
			removeDialog(Constants.PROGRESSDIALOG);
			try
			{
				Log.v(TAG, object.toString());
				
				CampusCalendarResponse response=Parser.parseCampusCalenderReponse((SoapObject)object);
				
				if(response.getErrorMessage().equals(""))
				{
					prjctsList=response.getProjectsList();
					if(prjctsList!=null &&prjctsList.size()>0)
					{
						campusCalendarListView.setAdapter(new ArrayAdapter<Projects>(NotificationsActivity.this,android.R.layout.simple_list_item_1,prjctsList));
						campusCalendarListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								
								Intent intent=new Intent(NotificationsActivity.this,ProjectDetailsActivity.class);
//								Utils.showToast(""+arg2, NotificationsActivity.this);
								intent.putExtra("myObject", prjctsList.get(arg2));
								startActivity(intent);
								
							}
							
						});
					}
				}
				else
				{
				Utils.showDialog(response.getErrorMessage(), NotificationsActivity.this, false);
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

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case Constants.PROGRESSDIALOG:
			final ProgressDialog progressDialog = new ProgressDialog(
					NotificationsActivity.this);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		Intent intent=new Intent(NotificationsActivity.this,ProjectDetailsActivity.class);
//		Utils.showToast(""+arg2, NotificationsActivity.this);
		intent.putExtra("myObject", prjctsList.get(arg2));
		startActivity(intent);
		
	}
	

}
