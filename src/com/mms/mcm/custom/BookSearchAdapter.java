package com.mms.mcm.custom;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mms.mcm.R;
import com.mms.mcm.model.Books;

public class BookSearchAdapter extends ArrayAdapter<Books> {

	private static final String TAG = "BookSearchAdapter";
	private Context context;
	private List<Books> booksList;
	private String isFrom;
	
	public BookSearchAdapter(Context context, int textViewResourceId,
			List<Books> objects,String isFrom) {
		super(context, textViewResourceId, objects);
		this.context=context;
		this.booksList=objects;
		this.isFrom=isFrom;
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public int getCount() {
		return booksList.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.books_in_possession_list_layout,
					null);
			holder = new ViewHolder();
			holder.refno = (TextView) convertView
					.findViewById(R.id.ref_no);
			holder.book_status = (TextView) convertView
					.findViewById(R.id.book_status);
			holder.holdcount0 = (TextView) convertView
			.findViewById(R.id.hold_count0);
			holder.holdcount1 = (TextView) convertView
			.findViewById(R.id.hold_count1);
			holder.return_date=(TextView)convertView.findViewById(R.id.return_date);
			holder.title=(TextView)convertView.findViewById(R.id.book_title);
			convertView.setTag(holder);
		} else {
			
			holder = (ViewHolder) convertView.getTag();
		}

		Books book=booksList.get(position);
		
		
		
		
		if(isFrom.equalsIgnoreCase("search"))
		{
		holder.holdcount0.setVisibility(View.GONE);
		holder.title.setVisibility(View.GONE);
		
		}
		else if(isFrom.equalsIgnoreCase("booksOnHold"))
		{
			holder.return_date.setVisibility(View.GONE);
			holder.book_status.setVisibility(View.GONE);
			holder.holdcount1.setVisibility(View.GONE);
		}
		else if(isFrom.equalsIgnoreCase("booksOnPossession"))
		{
			holder.book_status.setVisibility(View.GONE);
			holder.holdcount1.setVisibility(View.GONE);
			holder.holdcount0.setVisibility(View.VISIBLE);
		}
		
		holder.refno.setText("Ref No:"+book.getBook_REF_Number());
		holder.book_status.setText(book.getBook_Status());
		holder.holdcount0.setText(book.getBook_Hold_Count());
		holder.title.setText(book.getBook_Title());
		holder.return_date.setText(book.getBook_Return_Date());
		holder.holdcount1.setText(book.getBook_Hold_Count());
		
		return convertView;
	}
	

	private static class ViewHolder {
		private TextView refno;
		private TextView book_status;
		private TextView return_date;
		private TextView holdcount0;
		private TextView holdcount1;
		private TextView title;
		private Button button;
	}
	

}
