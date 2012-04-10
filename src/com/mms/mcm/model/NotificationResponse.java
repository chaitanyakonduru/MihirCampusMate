package com.mms.mcm.model;

import java.util.List;

public class NotificationResponse {
	
	private String errorMessage;
	private List<Projects> notificationList;

public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<Projects> getNotificationList() {
		return notificationList;
	}
	public void setNotificationList(List<Projects> notificationList) {
		this.notificationList = notificationList;
	}
	

}
