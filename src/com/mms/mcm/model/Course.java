package com.mms.mcm.model;

import org.ksoap2.serialization.SoapObject;

public class Course {
	
	private String courseCode;
	private String courseName;
	private String courseGrade;
	private String course_CGPA;
	
//	 Course_List=anyType{	
//			Course_Code=COMP101A; 
//			Course_Name=Computer Fundamentals; 
//			Course_Grade=A; }; 
	
	public Course(SoapObject object){
		if (object != null) {
			courseCode =object.hasProperty("Course_Code")?object.getPropertyAsString("Course_Code"):"";
			courseName =object.hasProperty("Course_Name")?object.getPropertyAsString("Course_Name"):"";
			courseGrade =object.hasProperty("Course_Grade")?object.getPropertyAsString("Course_Grade"):"";
//			courseCode =object.hasProperty("Course_Code")?object.getPropertyAsString("Course_Code"):"";
		}
	}
	
	
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseGrade() {
		return courseGrade;
	}
	public void setCourseGrade(String courseGrade) {
		this.courseGrade = courseGrade;
	}
	public String getCourse_CGPA() {
		return course_CGPA;
	}
	public void setCourse_CGPA(String courseCGPA) {
		course_CGPA = courseCGPA;
	}
	
	

}
