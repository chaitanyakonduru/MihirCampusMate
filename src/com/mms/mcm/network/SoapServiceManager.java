package com.mms.mcm.network;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import android.content.Context;

public final class SoapServiceManager {

	private final static String NAMESPACE ="http://mihirmobile.com/MIHIRCampusMateSrv/";

	private static final String SOAP_ACTION_REGISTRATION = "http://mihirmobile.com/MIHIRCampusMateSrv/Registration";
	private static final String SOAP_ACTION_FORGOTPASSWORD = "http://mihirmobile.com/MIHIRCampusMateSrv/ForgotPassword";
	private static final String SOAP_ACTION_CHANGEPASSWORD = "http://mihirmobile.com/MIHIRCampusMateSrv/ResetPassword";
	private static final String SOAP_ACTION_AUTHENTICATE = "http://mihirmobile.com/MIHIRCampusMateSrv/Authenticate";
	private static final String SOAP_ACTION_GETGRADES = "http://mihirmobile.com/MIHIRCampusMateSrv/GetGrades";
	private static final String SOAP_ACTION_GETHOMEWORKPROJECTS = "http://mihirmobile.com/MIHIRCampusMateSrv/GetHomeWorkProjects";
	private static final String SOAP_ACTION_GETNOTIFICATIONS= "http://mihirmobile.com/MIHIRCampusMateSrv/GetNotifications";
	private static final String SOAP_ACTION_GETCAMPUSCALENDER = "http://mihirmobile.com/MIHIRCampusMateSrv/GetCampusCalender";
	private static final String SOAP_ACTION_MAKECOMPLAINTS = "http://mihirmobile.com/MIHIRCampusMateSrv/MakeComplaints";
	private static final String SOAP_ACTION_BOOKSONSEARCH="http://mihirmobile.com/MIHIRCampusMateSrv/BooksOnSearch";
	private static final String SOAP_ACTION_BOOKSPOSSESSION="http://mihirmobile.com/MIHIRCampusMateSrv/BooksOnPossession";
	private static final String SOAP_ACTION_BOOKS_ON_HOLD="http://mihirmobile.com/MIHIRCampusMateSrv/BooksOnHold";
	
	private static SoapServiceManager serviceManager;
	private static ExecutorService executorService;

	public static SoapServiceManager getInstance(Context context) {
		return serviceManager == null ? serviceManager = new SoapServiceManager(
				context)
				: serviceManager;
	}
	public SoapServiceManager(Context context) {
		executorService = Executors.newCachedThreadPool(new NamedThreadFactory(
				"mihir"));
	}

	public void sendGetGradesRequest(String student_Id,NetworkCallback<Object> callback)
	{
		String methodName = "GetGrades";
		final String actionName = SOAP_ACTION_GETGRADES;
		final SoapObject requestObj = new SoapObject(NAMESPACE, methodName);

		
		PropertyInfo info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Student_Id");
		info.setValue(student_Id);
		requestObj.addProperty(info);
		final MihirHandler handler = new MihirHandler(callback);

		executorService.execute(new Runnable() {

			public void run() {
				SoapConn.callWebService(actionName, requestObj, handler);
			}
		});
		

	}
	
	
	public void sendGetHomeWorkProjectsRequest(String student_Id,
			NetworkCallback<Object> callback) {
		String methodName = "GetHomeWorkProjects";
		final String actionName = SOAP_ACTION_GETHOMEWORKPROJECTS;
		final SoapObject requestObj = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Student_Id");
		info.setValue(student_Id);
		requestObj.addProperty(info);
		final MihirHandler handler = new MihirHandler(callback);

		executorService.execute(new Runnable() {

			public void run() {
				SoapConn.callWebService(actionName, requestObj, handler);
			}
		});

	}

	public  void sendGetNotificationDetailsRequest(String student_Id,
			NetworkCallback<Object> callback) {
		String methodName = "GetNotifications";
		final String actionName = SOAP_ACTION_GETNOTIFICATIONS;
		final SoapObject requestObj = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Student_Id");
		info.setValue(student_Id);
		requestObj.addProperty(info);
		final MihirHandler handler = new MihirHandler(callback);
		executorService.execute(new Runnable() {

			public void run() {
				SoapConn.callWebService(actionName, requestObj, handler);
			}
		});

	}

	public void sendGetCampusCalenderDetailsRequest(String campus_Id,String student_Id,
			NetworkCallback<Object> callback) {
		String methodName = "GetCampusCalender";
		final String actionName = SOAP_ACTION_GETCAMPUSCALENDER;
		final SoapObject requestObj = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Campus_Id");
		info.setValue(campus_Id);
		requestObj.addProperty(info);
		
		
		info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Studnet_ID");
		info.setValue(student_Id);
		requestObj.addProperty(info);
		
		final MihirHandler handler = new MihirHandler(callback);
		executorService.execute(new Runnable() {

			public void run() {
				SoapConn.callWebService(actionName, requestObj, handler);
			}
		});

	}

	public  void sendComplaintCellDetailsRequest(String student_ID,List<String> studentNames,String complaintDescription,
			NetworkCallback<Object> callback) {
		String names[]={"","","","",""};
		for(int i=0;i<studentNames.size();i++)
		{
			names[i]=studentNames.get(i);
		}
		String methodName = "MakeComplaints";
		final String actionName = SOAP_ACTION_MAKECOMPLAINTS;
		final SoapObject requestObj = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Student_ID");
		info.setValue(student_ID);
		requestObj.addProperty(info);
		
		info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Student_Name1");
		info.setValue(names[0]);
		requestObj.addProperty(info);
		
		info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Student_Name2");
		info.setValue(names[1]);
		requestObj.addProperty(info);
		
		info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Student_Name3");
		info.setValue(names[2]);
		requestObj.addProperty(info);
		
		info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Student_Name4");
		info.setValue(names[3]);
		requestObj.addProperty(info);
		
		info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Student_Name5");
		info.setValue(names[4]);
		requestObj.addProperty(info);
		
		info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Complaint_Description");
		info.setValue(complaintDescription);
		requestObj.addProperty(info);
		final MihirHandler handler = new MihirHandler(callback);
		executorService.execute(new Runnable() {

			public void run() {
				SoapConn.callWebService(actionName, requestObj, handler);
			}
		});

	}
	
	

	public  void sendBooksOnSearchRequest(String book_MMS_ID,String book_IBSN_NO,
			NetworkCallback<Object> callback) {

		String methodName = "BooksOnSearch";
		final String actionName = SOAP_ACTION_BOOKSONSEARCH;
		final SoapObject requestObj = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Book_MMS_ID");
		info.setValue(book_MMS_ID);
		requestObj.addProperty(info);
		
		info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Book_IBSN_NO");
		info.setValue(book_IBSN_NO);
		requestObj.addProperty(info);
		
		final MihirHandler handler = new MihirHandler(callback);
		executorService.execute(new Runnable() {
			public void run() {
				SoapConn.callWebService(actionName, requestObj, handler);
			}
		});

	}

	public void sendBooksOnHoldRequest(int student_ID,
			NetworkCallback<Object> callback) {
		String methodName = "BooksOnHold";
		final String actionName = SOAP_ACTION_BOOKS_ON_HOLD;
		final SoapObject requestObj = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Student_ID");
		info.setValue(student_ID);
		requestObj.addProperty(info);
		final MihirHandler handler = new MihirHandler(callback);
		executorService.execute(new Runnable() {
			public void run() {
				SoapConn.callWebService(actionName, requestObj, handler);
			}
		});

	}
	
	public void sendBooksOnPossessionRequest(int student_ID,
			NetworkCallback<Object> callback) {
		String methodName = "BooksOnPossession";
		final String actionName = SOAP_ACTION_BOOKSPOSSESSION;
		final SoapObject requestObj = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.setNamespace(NAMESPACE);
		info.setName("Student_ID");
		info.setValue(student_ID);
		requestObj.addProperty(info);
		final MihirHandler handler = new MihirHandler(callback);
		executorService.execute(new Runnable() {
			public void run() {
				SoapConn.callWebService(actionName, requestObj, handler);
			}
		});

	}
	
	
	public void sendregisterRequest(String mmsId,
			NetworkCallback<Object> callback) {

		String methodName = "Registration";
		final String actionName = SOAP_ACTION_REGISTRATION;
		final SoapObject request = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "MMS_ID";
		info.setValue(mmsId);
		request.addProperty(info);
		final MihirHandler handler = new MihirHandler(callback);
		executorService.execute(new Runnable() {
			public void run() {

				SoapConn.callWebService(actionName, request, handler);
			}
		});
	}

	public  void sendforgotPasswordRequest(String mmsId,
			NetworkCallback<Object> callback) {
		String methodName = "ForgotPassword";
		final String actionName = SOAP_ACTION_FORGOTPASSWORD;
		final SoapObject request = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "MMS_ID";
		info.setValue(mmsId);
		request.addProperty(info);
		final MihirHandler handler = new MihirHandler(callback);
		executorService.execute(new Runnable() {
			public void run() {
				SoapConn.callWebService(actionName, request, handler);
			}
		});

	}

	public void sendchangePasswordRequest(String mmsId, String oldPwd,
			String newPwd, NetworkCallback<Object> callback) {
		String methodName = "ResetPassword";
		final String actionName = SOAP_ACTION_CHANGEPASSWORD;
		final SoapObject request = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "MMS_ID";
		info.setValue(mmsId);
		request.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "Old_Password";
		info.setValue(oldPwd);
		request.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "New_Password";
		info.setValue(newPwd);
		request.addProperty(info);
		final MihirHandler handler = new MihirHandler(callback);

		executorService.execute(new Runnable() {
			public void run() {
				SoapConn.callWebService(actionName, request, handler);
			}
		});
	}

	public void sendAuthenticateRequest(String mmsId, String password,
			String deviceId, NetworkCallback<Object> callback) {

		String methodName = "Authentication";

		final String actionName = SOAP_ACTION_AUTHENTICATE;
		final SoapObject requestObject = new SoapObject(NAMESPACE, methodName);

		PropertyInfo info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "MMS_ID";
		info.setValue(mmsId);
		requestObject.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "User_Password";
		info.setValue(password);
		requestObject.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "Device_ID";
		info.setValue(deviceId);
		requestObject.addProperty(info);

		info = new PropertyInfo();
		info.namespace = NAMESPACE;
		info.name = "Device_OS";
		info.setValue(2);
		requestObject.addProperty(info);

		final MihirHandler mihirHandler = new MihirHandler(callback);

		executorService.execute(new Runnable() {

			public void run() {
				SoapConn
						.callWebService(actionName, requestObject, mihirHandler);
			}
		});
	}

}
