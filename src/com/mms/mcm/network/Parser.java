package com.mms.mcm.network;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.model.BookSearchResponse;
import com.mms.mcm.model.BooksOnHoldResponse;
import com.mms.mcm.model.BooksOnPossessionResponse;
import com.mms.mcm.model.Course;
import com.mms.mcm.model.GradesResponse;
import com.mms.mcm.model.HomeWorkProjectsResponse;
import com.mms.mcm.model.Semester;

public class Parser {

	public static final AuthenticateResponse parseAuthenticateResponse(
			SoapObject object) {
		AuthenticateResponse authenticateResponse = AuthenticateResponse
				.getInstance();

		authenticateResponse.setAuthenticateMSG(object
				.hasProperty("AuthenticateMSG") ? object
				.getPropertyAsString("AuthenticateMSG") : "");
		authenticateResponse
				.setUserType(object.hasProperty("User_Type") ?Integer.parseInt(object
						.getPropertyAsString("User_Type")) : 1000);
		authenticateResponse
				.setStudent_ID(object.hasProperty("Student_ID") ? object
						.getPropertyAsString("Student_ID") : "");
		authenticateResponse
				.setCampus_ID(object.hasProperty("Campus_ID") ? object
						.getPropertyAsString("Campus_ID") : "");
		authenticateResponse
				.setStudent_Name(object.hasProperty("Student_Name") ? object
						.getPropertyAsString("Student_Name") : "");
		authenticateResponse.setCampus_Registration_Number(object
				.hasProperty("Campus_Registration_Number") ? object
				.getPropertyAsString("Campus_Registration_Number") : "");
		authenticateResponse.setCampus_ID(object
				.hasProperty("Campus_Student_Id") ? object
				.getPropertyAsString("Campus_Student_Id") : "");
		authenticateResponse.setFee_Due(object.hasProperty("Fee_Due") ? object
				.getPropertyAsString("Fee_Due") : "");
		authenticateResponse
				.setFee_DueDate(object.hasProperty("Fee_DueDate") ? object
						.getPropertyAsString("Fee_DueDate") : "");
		authenticateResponse.setCredits_Acheived(object
				.hasProperty("Credits_Acheived") ? object
				.getPropertyAsString("Credits_Acheived") : "");
		authenticateResponse.setcGPA(object.hasProperty("CGPA") ? object
				.getPropertyAsString("CGPA") : "");
		authenticateResponse.setStudent_Campus_Number(object
				.hasProperty("Student_Campus_Number") ? object
				.getPropertyAsString("Student_Campus_Number") : "");
		authenticateResponse.setStudent_Doctor_Number(object
				.hasProperty("Student_Doctor_Number") ? object
				.getPropertyAsString("Student_Doctor_Number") : "");
		authenticateResponse.setCampus_Police_number(object
				.hasProperty("Campus_Police_number") ? object
				.getPropertyAsString("Campus_Police_number") : "");
		authenticateResponse.setCampus_Local_number(object
				.hasProperty("Campus_Local_number") ? object
				.getPropertyAsString("Campus_Local_number") : "");
		authenticateResponse.setCampus_Emmergency_Number(object
				.hasProperty("Campus_Emmergency_Number") ? object
				.getPropertyAsString("Campus_Emmergency_Number") : "");

		return authenticateResponse;
	}

	public static final String parseRegister(SoapObject object) {
		String message = "";
		if (object.hasProperty("Registration_MSG")) {
			message = object.getPropertyAsString("Registration_MSG");
		} else if (object.hasProperty("Forgot_Password_MSG")) {
			message = object.getPropertyAsString("Forgot_Password_MSG");
		}
		return message;
	}

	public static final String parseChangePwd(SoapObject object) {
		String message = "";
		if (object.hasProperty("Reset_Password_MSG")) {
			message = object.getPropertyAsString("Reset_Password_MSG");
		}

		return message;
	}

	public static final GradesResponse parseGradesResponse(SoapObject object) {
		GradesResponse gradesResponse = new GradesResponse();
		Semester semester = null;
		List<Semester> semesterList = null;
		List<Course> courseList = null;
		Course course = null;
		gradesResponse.setErrorMsg(object.hasProperty("getGradeMSG") ? object
				.getPropertyAsString("getGradeMSG") : "");
		gradesResponse.setCampus_ID(object.hasProperty("Student_Name") ? object
				.getPropertyAsString("Student_Name") : "");
		gradesResponse.setStudent_Name(object.hasProperty("Campus_ID") ? object
				.getPropertyAsString("Campus_ID") : "");

		if (object.hasProperty("Semister_List")) {
			SoapObject semesterListSoapObj = (SoapObject) object
					.getProperty("Semister_List");
			semesterList = new ArrayList<Semester>();
			for (int i = 0; i < semesterListSoapObj.getPropertyCount(); i++) {
				semester = new Semester();
				SoapObject semesterSoapObj = (SoapObject) semesterListSoapObj
						.getProperty(i);
				semester.setSemesterName(semesterSoapObj
						.getPropertyAsString("Semister_Name"));
				courseList = new ArrayList<Course>();
				if (semesterSoapObj.hasProperty("Course_List")) {
					SoapObject courseListSoapObj = (SoapObject) semesterSoapObj
							.getProperty("Course_List");
					for (int j = 0; j < courseListSoapObj.getPropertyCount(); j++) {
						
						SoapObject courseSoapObj=(SoapObject) courseListSoapObj.getProperty(j);
						course = new Course();
						course.setCourseName(courseSoapObj
								.hasProperty("Course_Code") ? courseSoapObj
								.getPropertyAsString("Course_Code") : "");
						course.setCourseName(courseSoapObj
								.hasProperty("Course_Name") ? courseSoapObj
								.getPropertyAsString("Course_Name") : "");
						course.setCourseName(courseSoapObj
								.hasProperty("Course_Grade") ? courseSoapObj
								.getPropertyAsString("Course_Grade") : "");
						course.setCourseName(courseSoapObj
								.hasProperty("Course_CGPA") ? courseSoapObj
								.getPropertyAsString("Course_CGPA") : "");
						courseList.add(course);
					}
				}

				semester.setCourseList(courseList);
				semesterList.add(semester);

			}
			gradesResponse.setSemesters(semesterList);
		}
		return gradesResponse;

	}
	
	public static final HomeWorkProjectsResponse parseHomeWorkProjectsResponse(SoapObject object)
	{
		HomeWorkProjectsResponse homeWorkProjectsResponse=new HomeWorkProjectsResponse();
		
		homeWorkProjectsResponse.setErrorMsg(object.hasProperty("HomeWork_Projects_MSG")?object.getPropertyAsString("HomeWork_Projects_MSG"):"");
		homeWorkProjectsResponse.setCampus_Id(object.hasProperty("Student_Name")?object.getPropertyAsString("Student_Name"):"");
		homeWorkProjectsResponse.setStudentName(object.hasProperty("Campus_ID")?object.getPropertyAsString("Campus_ID"):"");
		
		if(object.hasProperty("Projects_List"))
		{
			SoapObject soapObject=(SoapObject) object.getProperty("Notification_List_Type");
			
		}
		
		return homeWorkProjectsResponse;
	}
	
	
	public static final String parseComplaintResponse(SoapObject object)
	{
		return object.hasProperty("Complaint_Message")?object.getPropertyAsString("Complaint_Message"):"";
	}
	
	
	public static final BookSearchResponse parseBookSearchResponse(SoapObject object)
	
	{
		BookSearchResponse bookSearchResponse=new BookSearchResponse();
		return bookSearchResponse;
	}
	
	public static final BooksOnHoldResponse parseBooksonHoldResponse(SoapObject object)
	{
		BooksOnHoldResponse booksOnHoldResponse=new BooksOnHoldResponse();
		
		return booksOnHoldResponse;
	
	}
	
	public static final BooksOnPossessionResponse parseBooksonPossessionResponse(SoapObject object)
	{
		BooksOnPossessionResponse booksOnPossessionResponse=new BooksOnPossessionResponse();
		
		return booksOnPossessionResponse;
	
	}
	
	public static final String parseRemoveBooksResponse(SoapObject object)
	{
		return object.hasProperty("Book_Remove_MSG")?object.getPropertyAsString("Book_Remove_MSG"):"";
	}
	
	
	
	
	
	

}
