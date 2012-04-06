package com.mms.mcm.model;

import java.util.List;

public class Semester {

	String semesterName;
	List<Course> courseList;
	public String getSemesterName() {
		return semesterName;
	}
	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}
	public List<Course> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}
	
	
	
	
	
}
