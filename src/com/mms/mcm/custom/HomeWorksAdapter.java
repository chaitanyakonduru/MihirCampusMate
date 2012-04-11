package com.mms.mcm.custom;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mms.mcm.R;
import com.mms.mcm.model.Projects;

public class HomeWorksAdapter extends ArrayAdapter<Projects> {
	
	private static final String TAG = "Home_worksAdapter";
	private List<Projects> items;
	private LayoutInflater inflater;

	public HomeWorksAdapter(Context context, int textViewResourceId,
			List<Projects> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return items.size();
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.home_work_projects_list_layout,
					null);
			
			holder = new ViewHolder();
			holder.subjectCode = (TextView) convertView
					.findViewById(R.id.projects_subject_code);
			holder.subjectname = (TextView) convertView
					.findViewById(R.id.projects_subname);
			holder.date=(TextView)convertView.findViewById(R.id.projects_due_date);
			convertView.setTag(holder);
		} else {
			
			holder = (ViewHolder) convertView.getTag();
		}

		Projects projects=getItem(position);
		holder.subjectCode.setText(projects.getCourse_Code());
		holder.subjectname.setText(projects.getCourse_Name());
		holder.date.setText("Due Date :"+projects.getNotifications_DueTime());
		return convertView;
	}
	
	@Override
	public Projects getItem(int position) {
	
		return items.get(position);
	}

	private static class ViewHolder {
		private TextView date;
		private TextView subjectname;
		private TextView subjectCode;
	}
	

	

}
