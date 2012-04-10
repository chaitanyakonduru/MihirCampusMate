package com.mms.mcm.model;

public class Projects {
	
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

}
