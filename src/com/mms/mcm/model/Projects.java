package com.mms.mcm.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Projects implements Parcelable{
	
	private String  Semister_Name;
	private String  Course_Code;
	private String  Course_Name;
	private String  Course_Grade;
	private String  Notification_Id;
	private String  Notifications_Type;
	private String   Notifications_Title;
	private String   Notifications_DueTime;
	private String   Notifications_Details;
	private String   Notifications_Comments;
	
	public String getSemister_Name() {
		return Semister_Name;
	}
	public void setSemister_Name(String semisterName) {
		Semister_Name = semisterName;
	}
	public String getCourse_Code() {
		return Course_Code;
	}
	public void setCourse_Code(String courseCode) {
		Course_Code = courseCode;
	}
	public String getCourse_Name() {
		return Course_Name;
	}
	public void setCourse_Name(String courseName) {
		Course_Name = courseName;
	}
	public String getCourse_Grade() {
		return Course_Grade;
	}
	public void setCourse_Grade(String courseGrade) {
		Course_Grade = courseGrade;
	}
	public String getNotification_Id() {
		return Notification_Id;
	}
	public void setNotification_Id(String notificationId) {
		Notification_Id = notificationId;
	}
	public String getNotifications_Type() {
		return Notifications_Type;
	}
	public void setNotifications_Type(String notificationsType) {
		Notifications_Type = notificationsType;
	}
	public String getNotifications_Title() {
		return Notifications_Title;
	}
	public void setNotifications_Title(String notificationsTitle) {
		Notifications_Title = notificationsTitle;
	}
	public String getNotifications_DueTime() {
		return Notifications_DueTime;
	}
	public void setNotifications_DueTime(String notificationsDueTime) {
		Notifications_DueTime = notificationsDueTime;
	}
	public String getNotifications_Details() {
		return Notifications_Details;
	}
	public void setNotifications_Details(String notificationsDetails) {
		Notifications_Details = notificationsDetails;
	}
	public String getNotifications_Comments() {
		return Notifications_Comments;
	}
	public void setNotifications_Comments(String notificationsComments) {
		Notifications_Comments = notificationsComments;
	}
	
	
	@Override
	public String toString() {
		return Notifications_Title;
	}

	public Projects()
	{
		
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {

		List<String> ll = new ArrayList<String>();
		ll.add(this.Course_Code);
		ll.add(this.Course_Grade);
		ll.add(this.Course_Name);
		ll.add(this.Notification_Id);
		ll.add(this.Notifications_Comments);
		ll.add(this.Notifications_Details);
		ll.add(this.Notifications_DueTime);
		ll.add(this.Notifications_Title);
		ll.add(this.Notifications_Type);
		ll.add(this.Semister_Name);
		dest.writeStringList(ll);

	}

	public Projects(Parcel parcel) {
		List<String> list1 = new ArrayList<String>();
		parcel.readStringList(list1);
		this.Course_Code = list1.get(0);
		this.Course_Grade = list1.get(1);
		this.Course_Name = list1.get(2);
		this.Notification_Id = list1.get(3);
		this.Notifications_Comments = list1.get(4);
		this.Notifications_Details= list1.get(5);
		this.Notifications_DueTime= list1.get(6);
		this.Notifications_Title= list1.get(7);
		this.Notifications_Type= list1.get(8);
		this.Semister_Name= list1.get(9);
	}

	public static final Creator<Projects> CREATOR = new Creator<Projects>() {

		public Projects createFromParcel(Parcel source) {
			return new Projects(source);
		}

		public Projects[] newArray(int size) {
			return new Projects[size];
		}
	};

	public static Creator<Projects> getCreator() {
		return CREATOR;
	}
	public int describeContents() {
		return 0;
	}

}
