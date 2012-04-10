package com.mms.mcm.network;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.mms.mcm.custom.Constants;
import com.mms.mcm.model.AuthenticateResponse;
import com.mms.mcm.model.BookSearchResponse;
import com.mms.mcm.model.BooksOnHoldResponse;
import com.mms.mcm.model.BooksOnPossessionResponse;
import com.mms.mcm.model.CampusCalendarResponse;
import com.mms.mcm.model.GradesResponse;
import com.mms.mcm.model.HomeWorkProjectsResponse;
import com.mms.mcm.model.NotificationResponse;
import com.mms.mcm.model.Projects;
import com.mms.mcm.model.Semester;

public class Parser {

	public static final AuthenticateResponse parseAuthenticateResponse(
			SoapObject object) {
		AuthenticateResponse authenticateResponse =new AuthenticateResponse(); 

		authenticateResponse.setAuthenticateMSG(object
				.hasProperty("AuthenticateMSG") ? object
				.getPropertyAsString("AuthenticateMSG") : "");

		authenticateResponse.setUserType(authenticateResponse
				.getAuthenticateMSG().trim().equals("") ? Constants.STUDENT
				: 1000);

		// authenticateResponse
		// .setUserType(object.hasProperty("User_Type") ?Integer.parseInt(object
		// .getPropertyAsString("User_Type")) : 1000);
		authenticateResponse
				.setStudent_ID(object.hasProperty("Student_ID") ? object
						.getPropertyAsString("Student_ID") : "");
		authenticateResponse.setCampusName(object.hasProperty("Campus_Name") ? object
						.getPropertyAsString("Campus_Name") : "");
		authenticateResponse.setCampus_ID(object.hasProperty("Campus_ID") ? object
						.getPropertyAsString("Campus_ID") : "");
		
		authenticateResponse
				.setStudent_Name(object.hasProperty("Student_Name") ? object
						.getPropertyAsString("Student_Name") : "");
		authenticateResponse.setCampus_Registration_Number(object
				.hasProperty("Campus_Registration_Number") ? object
				.getPropertyAsString("Campus_Registration_Number") : "");
		authenticateResponse.setCampus_Student_Id(object
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
	
	public static final NotificationResponse parseNotificationReponse(SoapObject object)
	{
		NotificationResponse notificationResponse=new NotificationResponse();
		notificationResponse.setErrorMessage(object.hasProperty("Notification_MSG")?object.getPropertyAsString("Notification_MSG"):"");
		if(object.hasProperty("NotificationList"))
		{
			
			SoapObject notificationListSoapObject=(SoapObject) object.getProperty("NotificationList");
			List<Projects> notificationList=getList(notificationListSoapObject);
			notificationResponse.setNotificationList(notificationList);
		}
		
		return notificationResponse;
	}
	
	public static final CampusCalendarResponse parseCampusCalenderReponse(SoapObject object)
	{
		CampusCalendarResponse response=new CampusCalendarResponse();
		response.setErrorMessage(object.hasProperty("Campus_Calender_MSG")?object.getPropertyAsString("Campus_Calender_MSG"):"");
		if(object.hasProperty("CalenderList"))
		{
			
			SoapObject campusCalendarListSoapObject=(SoapObject) object.getProperty("CalenderList");
			List<Projects> notificationList=getList(campusCalendarListSoapObject);
			response.setProjectsList(notificationList);
		}
		return response;
	}
	private static List<Projects> getList(SoapObject notificationListSoapObject) {
		Projects projects;
		List<Projects> projectsList=new ArrayList<Projects>();
		for (int i = 0; i < notificationListSoapObject.getPropertyCount(); i++) {
			projects = new Projects();
			SoapObject notificationSoapObject = (SoapObject) notificationListSoapObject
					.getProperty(i);
			projects.setCourse_Code(notificationSoapObject
					.hasProperty("Course_Code") ? notificationSoapObject
					.getPropertyAsString("Course_Code") : "");
			projects.setCourse_Name(notificationSoapObject
					.hasProperty("Course_Name") ? notificationSoapObject
					.getPropertyAsString("Course_Name") : "");
			projects
					.setNotification_Id(notificationSoapObject
							.hasProperty("Notification_Id") ? notificationSoapObject
							.getPropertyAsString("Notification_Id")
							: "");
			projects
					.setNotifications_Title(notificationSoapObject
							.hasProperty("Notifications_Title") ? notificationSoapObject
							.getPropertyAsString("Notifications_Title")
							: "");
			projects
					.setNotifications_DueTime(notificationSoapObject
							.hasProperty("Notifications_DueTime") ? notificationSoapObject
							.getPropertyAsString("Notifications_DueTime")
							: "");
			projects
					.setNotifications_Details(notificationSoapObject
							.hasProperty("Notifications_Details") ? notificationSoapObject
							.getPropertyAsString("Notifications_Details")
							: "");
			projects
					.setNotifications_Comments(notificationSoapObject
							.hasProperty("Notifications_Comments") ? notificationSoapObject
							.getPropertyAsString("Notifications_Comments")
							: "");
			projects
					.setNotifications_Type(notificationSoapObject
							.hasProperty("Notifications_Type") ? notificationSoapObject
							.getPropertyAsString("Notifications_Type")
							: "");
			projects.setCourse_Grade(notificationSoapObject
					.hasProperty("Course_Grade") ? notificationSoapObject
					.getPropertyAsString("Course_Grade") : "");
			projects.setSemister_Name(notificationSoapObject
					.hasProperty("Semister_Name") ? notificationSoapObject
					.getPropertyAsString("Semister_Name") : "");
			projectsList.add(projects);

		}
		return projectsList;
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
	
	public static final String parseComplaintCellResponse(SoapObject object) {
		String message = "";
		if (object.hasProperty("Complaint_Message")) {
			message = object.getPropertyAsString("Complaint_Message");
		}

		return message;
	}
	

	public static final GradesResponse parseGradesResponse(SoapObject object) {

		GradesResponse gradesResponse = new GradesResponse();
		List<Semester> semesterList = null;

		gradesResponse.setErrorMsg(object.hasProperty("getGradeMSG") ? object
				.getPropertyAsString("getGradeMSG") : "");
		gradesResponse.setCampus_ID(object.hasProperty("Campus_ID") ? object
				.getPropertyAsString("Campus_ID") : "");
		gradesResponse
				.setStudent_Name(object.hasProperty("Student_Name") ? object
						.getPropertyAsString("Student_Name") : "");

		if (gradesResponse.getErrorMsg().equals("")) {
			semesterList = new ArrayList<Semester>();

			for (int i = 1; i < object.getPropertyCount(); i++) {
				SoapObject soapObject = (SoapObject) object.getProperty(i);
				semesterList.add(new Semester(soapObject));
			}

			gradesResponse.setSemesters(semesterList);
		}
		return gradesResponse;

	}

	public static final HomeWorkProjectsResponse parseHomeWorkProjectsResponse(
			SoapObject object) {
		HomeWorkProjectsResponse homeWorkProjectsResponse = new HomeWorkProjectsResponse();
		List<Projects> home_work_prjctslist;
		Projects projects;

		homeWorkProjectsResponse.setErrorMsg(object
				.hasProperty("HomeWork_Projects_MSG") ? object
				.getPropertyAsString("HomeWork_Projects_MSG") : "");
		homeWorkProjectsResponse.setCampus_Id(object
				.hasProperty("Student_Name") ? object
				.getPropertyAsString("Student_Name") : "");
		homeWorkProjectsResponse
				.setStudentName(object.hasProperty("Campus_ID") ? object
						.getPropertyAsString("Campus_ID") : "");

		if (object.hasProperty("Projects_List")) {
			home_work_prjctslist = new ArrayList<Projects>();
			SoapObject projectsListSoapObject = (SoapObject) object
					.getProperty("Projects_List");

			homeWorkProjectsResponse.setProjectsList(getList(projectsListSoapObject));

		}

		return homeWorkProjectsResponse;
	}

	public static final String parseComplaintResponse(SoapObject object) {
		return object.hasProperty("Complaint_Message") ? object
				.getPropertyAsString("Complaint_Message") : "";
	}
	

	public static final BookSearchResponse parseBookSearchResponse(
			SoapObject object)

	{
		BookSearchResponse bookSearchResponse = new BookSearchResponse();
		return bookSearchResponse;
	}

	public static final BooksOnHoldResponse parseBooksonHoldResponse(
			SoapObject object) {
		BooksOnHoldResponse booksOnHoldResponse = new BooksOnHoldResponse();

		return booksOnHoldResponse;

	}

	public static final BooksOnPossessionResponse parseBooksonPossessionResponse(
			SoapObject object) {
		BooksOnPossessionResponse booksOnPossessionResponse = new BooksOnPossessionResponse();

		return booksOnPossessionResponse;

	}

	public static final String parseRemoveBooksResponse(SoapObject object) {
		return object.hasProperty("Book_Remove_MSG") ? object
				.getPropertyAsString("Book_Remove_MSG") : "";
	}

}
