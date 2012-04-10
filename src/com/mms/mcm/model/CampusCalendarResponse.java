package com.mms.mcm.model;

import java.util.List;

public class CampusCalendarResponse {
	
	private String errorMessage;
	private List<Projects> projectsList;
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<Projects> getProjectsList() {
		return projectsList;
	}
	public void setProjectsList(List<Projects> projectsList) {
		this.projectsList = projectsList;
	}
	
	
	

}
