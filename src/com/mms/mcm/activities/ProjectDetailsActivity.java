package com.mms.mcm.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mms.mcm.R;
import com.mms.mcm.custom.Utils;
import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.model.Projects;

public class ProjectDetailsActivity extends Activity implements OnClickListener{

private AuthenticateResponse authenticateResponse;
private Projects project;
private TextView studentName;
private TextView campusName;
private ImageView logo;

protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.layout_projects_details);
	
	MihirApp app=(MihirApp) getApplication();
	
	authenticateResponse=app.getCurUserInfo();
	studentName=(TextView)findViewById(R.id.action_bar_tv_patient_name);
	campusName = (TextView) findViewById(R.id.action_tv_hospital_name);
	logo = (ImageView) findViewById(R.id.school_logo);
	Utils.setActionBar(campusName, studentName, authenticateResponse, null);
	
	findViewById(R.id.sharebutton).setOnClickListener(ProjectDetailsActivity.this);
	
	Bundle bundle=getIntent().getExtras();
	if(bundle!=null&&bundle.containsKey("myObject"))
	{
		project=(Projects) bundle.get("myObject");
		
		((TextView)findViewById(R.id.course_code)).setText(project.getCourse_Code());
		((TextView)findViewById(R.id.course_name)).setText(project.getCourse_Name());
		((TextView)findViewById(R.id.course_grade)).setText(project.getCourse_Grade());
		((TextView)findViewById(R.id.semester_name)).setText(project.getSemister_Name());
		((TextView)findViewById(R.id.notification_type)).setText(project.getNotifications_Type());
		((TextView)findViewById(R.id.notification_title)).setText(project.getNotifications_Title());
		((TextView)findViewById(R.id.notification_due_time)).setText(project.getNotifications_DueTime());
		((TextView)findViewById(R.id.notification_details)).setText(project.getNotifications_Details());
		((TextView)findViewById(R.id.notification_comments)).setText(project.getNotifications_Comments());
		
		
	}
	
}

@Override
public void onClick(View v) {
	
	switch (v.getId()) {
	case R.id.sharebutton:
		composeMailContent();
		break;

	default:
		break;
	}
		
}

private void composeMailContent() {

	String mailText="Student Name"+authenticateResponse.getStudent_Name()+"\n"
	+"Course Code \t"+project.getCourse_Code()+"\n"
	+"Course Name\t"+project.getCourse_Name()+"\n"
	+"Course Grade\t"+project.getCourse_Grade()+"\n"
	+"Semester Name\t"+project.getSemister_Name()+"\n"
	+"Notification Id\t"+project.getNotification_Id()+"\n"
	+"Notification Comments \t"+project.getNotifications_Comments()+"\n"
	+"Notification Details \t"+project.getNotifications_Details()+"\n"
	+"Notification Due Time \t"+project.getNotifications_DueTime()+"\n"
	+"Notification Title \t"+project.getNotifications_Title()+"\n"
	+"Notification Type \t"+project.getNotifications_Type()+"\n";
	String subject=authenticateResponse.getStudent_Name()+"'s Project Details Work";
	Utils.sendMail(mailText, subject, ProjectDetailsActivity.this);
}
}
