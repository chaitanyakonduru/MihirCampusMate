package com.mms.mcm.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mms.mcm.R;

public class LibraryActivity extends Activity{

	
	private ListView librarylistview;
	private String[] library_items={"Books on Hold","Books on Possession"};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	protected void onResume() {
		setContentView(R.layout.layout_library);
		librarylistview=(ListView)findViewById(R.id.list);
		librarylistview.setAdapter(new ArrayAdapter<String>(LibraryActivity.this, R.layout.layout_dietcarelisttext, library_items));
	
		super.onResume();
	}
}
