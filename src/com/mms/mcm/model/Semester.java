package com.mms.mcm.model;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

public class Semester {

	String semesterName="";
	String gpa="";
	List<Course> courseList;

	
	public String getGpa() {
		return gpa;
	}

	public void setGpa(String gpa) {
		this.gpa = gpa;
	}
	
	
//	Semister_List=anyType{Semister_Name=FALL-2012; 
//    Course_List=anyType{	
//		Course_Code=COMP101A; 
//		Course_Name=Computer Fundamentals; 
//		Course_Grade=A; }; 
//    Course_List=anyType{
//		Course_Code=SOC101;
//		Course_Name=US Political System;
//		Course_Grade=B; }; }; 
	public Semester(SoapObject soapObject){
		courseList=new ArrayList<Course>();
		semesterName = soapObject.getPropertyAsString("Semister_Name");
		gpa=soapObject.getPropertyAsString("Semister_GPA");
		
		for (int i = 2; i < soapObject.getPropertyCount(); i++) {
			SoapObject courseObject = (SoapObject) soapObject.getProperty(i);
			courseList.add(new Course(courseObject));
		}
	}
	
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
