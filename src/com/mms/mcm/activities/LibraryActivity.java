package com.mms.mcm.activities;

import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mms.mcm.R;
import com.mms.mcm.custom.Constants;
import com.mms.mcm.custom.Utils;
import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.model.BookSearchResponse;
import com.mms.mcm.model.Books;
import com.mms.mcm.network.NetworkCallback;
import com.mms.mcm.network.Parser;
import com.mms.mcm.network.SoapServiceManager;

public class LibraryActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	protected static final String TAG = "Library Activity";
	private ListView librarylistview;
	private String[] library_items = { "Books on Hold", "Books In Possession" };
	private EditText isbnNo;
	private EditText book_id;
	private TextView studentName;
	private TextView campusName;
	private ImageView logo;
	private AuthenticateResponse authenticateResponse;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_library);
		initializeViews();
		
		MihirApp app = (MihirApp) getApplication();
		authenticateResponse=app.getCurUserInfo();
		Utils.setActionBar(campusName, studentName, authenticateResponse, null);
		librarylistview = (ListView) findViewById(R.id.list);
		librarylistview.setAdapter(new ArrayAdapter<String>(
				LibraryActivity.this, R.layout.layout_dietcarelisttext,
				library_items));
		librarylistview.setOnItemClickListener(LibraryActivity.this);
	}

	private void initializeViews() {
		isbnNo = (EditText) findViewById(R.id.isbnno);
		book_id = (EditText) findViewById(R.id.mms_book_id);
		findViewById(R.id.book_search).setOnClickListener(LibraryActivity.this);
		studentName=(TextView)findViewById(R.id.action_bar_tv_patient_name);
		campusName = (TextView) findViewById(R.id.action_tv_hospital_name);
		logo = (ImageView) findViewById(R.id.school_logo);

	}

	protected void onResume() {

		super.onResume();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.book_search:
			if (isbnNo.getText().toString().trim().equals("")
					|| book_id.getText().toString().trim().equals("")) {
				Utils.showDialog(Constants.FIELDSREQUIRED,
						LibraryActivity.this, false);
			} else {
				searchBooks();
			}
			break;

		default:
			break;
		}

	}

	private void searchBooks() {
		showDialog(Constants.PROGRESSDIALOG);
		SoapServiceManager manager = SoapServiceManager
				.getInstance(LibraryActivity.this);
		manager.sendBooksOnSearchRequest(book_id.getText().toString().trim(),
				isbnNo.getText().toString().trim(), callback);
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
						BookSearchActivity.setBooksList(booksList, isbnNo
								.getText().toString(), book_id.getText()
								.toString());
						startActivity(new Intent(LibraryActivity.this,
								BookSearchActivity.class));
					}
				} else {
					Utils.showDialog(response.getErrorMsg(),
							LibraryActivity.this, true);
				}

			} catch (ClassCastException cce) {
				Utils.showToast("Unable to process your request",
						LibraryActivity.this);
				Log.v(TAG, cce.getMessage());

			}

		}

		public void onFailure(String errorMessge) {
			removeDialog(Constants.PROGRESSDIALOG);
			Utils.showToast("Unable to process your request",
					LibraryActivity.this);
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case Constants.PROGRESSDIALOG:
			final ProgressDialog progressDialog = new ProgressDialog(
					LibraryActivity.this);
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

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//To hide the KeyBoard when Clicked on Item
		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
         imm.hideSoftInputFromWindow(librarylistview.getApplicationWindowToken(), 0);
         
		if(arg2==0)
		{
			startActivity(new Intent(LibraryActivity.this,BooksOnHoldActivity.class));
		}
		else if(arg2==1)
		{
			startActivity(new Intent(LibraryActivity.this,BooksOnPossessionActivity.class));
		}
	}

}
