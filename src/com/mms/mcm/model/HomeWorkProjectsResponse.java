package com.mms.mcm.model;

import java.util.List;

public class HomeWorkProjectsResponse {
	
	private String errorMsg;
	private String studentName;
	private String campus_Id;
	private List<Projects> projectsList;
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCampus_Id() {
		return campus_Id;
	}
	public void setCampus_Id(String campusId) {
		campus_Id = campusId;
	}
	public List<Projects> getProjectsList() {
		return projectsList;
	}
	public void setProjectsList(List<Projects> projectsList) {
		this.projectsList = projectsList;
	}
	
	

}
