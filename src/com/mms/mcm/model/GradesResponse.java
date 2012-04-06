package com.mms.mcm.model;

import java.util.List;

public class GradesResponse {

	private String errorMsg;
	private String Student_Name;
	private String Campus_ID;
	private List<Semester> semesters;

	public String getStudent_Name() {
		return Student_Name;
	}

	public void setStudent_Name(String studentName) {
		Student_Name = studentName;
	}

	public String getCampus_ID() {
		return Campus_ID;
	}

	public void setCampus_ID(String campusID) {
		Campus_ID = campusID;
	}

	public List<Semester> getSemesters() {
		return semesters;
	}

	public void setSemesters(List<Semester> semesters) {
		this.semesters = semesters;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
