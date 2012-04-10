package com.mms.mcm.model;

public class AuthenticateResponse {

	private static AuthenticateResponse authenticateResponse;

	String authenticateMSG ;
	int userType;
	String student_ID;
	String campus_ID;
	String student_Name;
	String campusName;
	String campusShortName;
	String campus_Registration_Number;
	String campus_Student_Id;
	String logo_URL;
	String fee_Due;
	String fee_DueDate ;
	String credits_Acheived;
	String cGPA ;
	String student_Campus_Number;
	String campusPoliceNumber;
	String campusLocalNumber;
	String campusEmergencyNumber;
	String student_Doctor_Number = "";
	String campus_Police_number = "";
	String campus_Local_number = "";
	String campus_Emmergency_Number = "";

	public String getCampusShortName() {
		return campusShortName;
	}

	public void setCampusShortName(String campusShortName) {
		this.campusShortName = campusShortName;
	}

	public String getCampusPoliceNumber() {
		return campusPoliceNumber;
	}

	public void setCampusPoliceNumber(String campusPoliceNumber) {
		this.campusPoliceNumber = campusPoliceNumber;
	}

	public String getCampusLocalNumber() {
		return campusLocalNumber;
	}

	public void setCampusLocalNumber(String campusLocalNumber) {
		this.campusLocalNumber = campusLocalNumber;
	}

	public String getCampusEmergencyNumber() {
		return campusEmergencyNumber;
	}

	public void setCampusEmergencyNumber(String campusEmergencyNumber) {
		this.campusEmergencyNumber = campusEmergencyNumber;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}

	public String getLogo_URL() {
		return logo_URL;
	}

	public void setLogo_URL(String logoURL) {
		logo_URL = logoURL;
	}

	

	

	public String getAuthenticateMSG() {
		return authenticateMSG;
	}

	public void setAuthenticateMSG(String authenticateMSG) {
		this.authenticateMSG = authenticateMSG;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getStudent_ID() {
		return student_ID;
	}

	public void setStudent_ID(String studentID) {
		student_ID = studentID;
	}

	public String getCampus_ID() {
		return campus_ID;
	}

	public void setCampus_ID(String campusID) {
		campus_ID = campusID;
	}

	public String getStudent_Name() {
		return student_Name;
	}

	public void setStudent_Name(String studentName) {
		student_Name = studentName;
	}

	public String getCampus_Registration_Number() {
		return campus_Registration_Number;
	}

	public void setCampus_Registration_Number(String campusRegistrationNumber) {
		campus_Registration_Number = campusRegistrationNumber;
	}

	public String getCampus_Student_Id() {
		return campus_Student_Id;
	}

	public void setCampus_Student_Id(String campusStudentId) {
		campus_Student_Id = campusStudentId;
	}

	public String getFee_Due() {
		return fee_Due;
	}

	public void setFee_Due(String feeDue) {
		fee_Due = feeDue;
	}

	public String getFee_DueDate() {
		return fee_DueDate;
	}

	public void setFee_DueDate(String feeDueDate) {
		fee_DueDate = feeDueDate;
	}

	public String getCredits_Acheived() {
		return credits_Acheived;
	}

	public void setCredits_Acheived(String creditsAcheived) {
		credits_Acheived = creditsAcheived;
	}

	public String getcGPA() {
		return cGPA;
	}

	public void setcGPA(String cGPA) {
		this.cGPA = cGPA;
	}

	public String getStudent_Campus_Number() {
		return student_Campus_Number;
	}

	public void setStudent_Campus_Number(String studentCampusNumber) {
		student_Campus_Number = studentCampusNumber;
	}

	public String getStudent_Doctor_Number() {
		return student_Doctor_Number;
	}

	public void setStudent_Doctor_Number(String studentDoctorNumber) {
		student_Doctor_Number = studentDoctorNumber;
	}

	public String getCampus_Police_number() {
		return campus_Police_number;
	}

	public void setCampus_Police_number(String campusPoliceNumber) {
		campus_Police_number = campusPoliceNumber;
	}

	public String getCampus_Local_number() {
		return campus_Local_number;
	}

	public void setCampus_Local_number(String campusLocalNumber) {
		campus_Local_number = campusLocalNumber;
	}

	public String getCampus_Emmergency_Number() {
		return campus_Emmergency_Number;
	}

	public void setCampus_Emmergency_Number(String campusEmmergencyNumber) {
		campus_Emmergency_Number = campusEmmergencyNumber;
	}

	public static AuthenticateResponse getInstance() {
		return authenticateResponse == null ? new AuthenticateResponse()
				: authenticateResponse;
	}
}
