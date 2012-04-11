package com.mms.mcm.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mms.mcm.R;
import com.mms.mcm.custom.BookSearchAdapter;
import com.mms.mcm.custom.Utils;
import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.model.Books;

public class BookSearchActivity extends Activity implements OnItemClickListener{
	
	private static List<Books> booksList;
	private static String isbn;
	private ListView searchListView;
	private TextView studentName;
	private TextView campusName;
	private ImageView logo;
	private AuthenticateResponse authenticateResponse;
	static String book_id;
	
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_book_search);
		initializeViews();
		MihirApp app=(MihirApp) getApplication();
		authenticateResponse=app.getCurUserInfo();
		Utils.setActionBar(campusName, studentName, authenticateResponse, null);
		searchListView.setAdapter(new BookSearchAdapter(BookSearchActivity.this,-1, booksList,"search"));
		searchListView.setOnItemClickListener(BookSearchActivity.this);
	}
	
	private void initializeViews() {

		searchListView=(ListView) findViewById(R.id.books_search_listview);
		((TextView)findViewById(R.id.isbn_no)).setText("ISBN No  :"+isbn);
		((TextView)findViewById(R.id.book_id)).setText("Book Id   :"+book_id);
		studentName=(TextView)findViewById(R.id.action_bar_tv_patient_name);
		campusName = (TextView) findViewById(R.id.action_tv_hospital_name);
		logo = (ImageView) findViewById(R.id.school_logo);
		
	}

	public static List<Books> setBooksList(List<Books> booksList,String isbn,String book_id)
	{
		BookSearchActivity.booksList=booksList;
		BookSearchActivity.isbn=isbn;
		BookSearchActivity.book_id=book_id;
		return BookSearchActivity.booksList;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
