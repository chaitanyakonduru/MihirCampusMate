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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.mms.mcm.R;
import com.mms.mcm.custom.BookSearchAdapter;
import com.mms.mcm.custom.Constants;
import com.mms.mcm.custom.Utils;
import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.model.BookSearchResponse;
import com.mms.mcm.model.Books;
import com.mms.mcm.network.NetworkCallback;
import com.mms.mcm.network.Parser;
import com.mms.mcm.network.SoapServiceManager;

public class BooksOnHoldActivity extends Activity{
	
	protected static final String TAG = null;
	private AuthenticateResponse authenticateResponse;
	private ListView listview;
	private TextView studentName;
	private TextView campusName;
	private ImageView logo;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_book_hold);
		initializeviews();
		MihirApp app=(MihirApp) getApplication();
		authenticateResponse=app.getCurUserInfo();
		Utils.setActionBar(campusName, studentName, authenticateResponse, null);
		SoapServiceManager manager=SoapServiceManager.getInstance(BooksOnHoldActivity.this);
		showDialog(Constants.PROGRESSDIALOG);
		manager.sendBooksOnHoldRequest(authenticateResponse.getStudent_ID(), callback);
	}
	
	private void initializeviews() {
		listview=(ListView) findViewById(R.id.books_hold_listview);
		studentName=(TextView)findViewById(R.id.action_bar_tv_patient_name);
		campusName = (TextView) findViewById(R.id.action_tv_hospital_name);
		logo = (ImageView) findViewById(R.id.school_logo);
		
	}

	final NetworkCallback<Object> callback = new NetworkCallback<Object>() {

		public void onSuccess(Object responseObj) {
			removeDialog(Constants.PROGRESSDIALOG);
			List<Books> booksList;
			try {

				SoapObject responceObject = (SoapObject) responseObj;
				BookSearchResponse response = Parser
						.parseBookSearchResponse(responceObject);
				if (response.getErrorMsg().trim().equals("")) {
					booksList = response.getBooksList();

					if (booksList != null && booksList.size() > 0) {
						listview.setAdapter(new BookSearchAdapter(BooksOnHoldActivity.this, -1, booksList, "booksOnHold"));
						
					}
				} else {
					Utils.showDialog(response.getErrorMsg(),
							BooksOnHoldActivity.this, true);
				}

			} catch (ClassCastException cce) {
				Utils.showToast("Unable to process your request",
						BooksOnHoldActivity.this);
				Log.v(TAG, cce.getMessage());

			}

		}

		public void onFailure(String errorMessge) {
			removeDialog(Constants.PROGRESSDIALOG);
			Utils.showToast("Unable to process your request",
					BooksOnHoldActivity.this);
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case Constants.PROGRESSDIALOG:
			final ProgressDialog progressDialog = new ProgressDialog(
					BooksOnHoldActivity.this);
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

}
