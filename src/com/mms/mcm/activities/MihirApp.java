package com.mms.mcm.activities;

import com.mms.mcm.model.AuthenticateResponse;

import android.app.Application;

public final class MihirApp extends Application {
	
	private AuthenticateResponse authenticateResponse;
	private boolean isloggedin=false;
	private static MihirApp app;
	public boolean isIsloggedin() {
		return isloggedin;
	}

	public static MihirApp getInstance()
	{
		return app==null?app=new MihirApp():app;
	}
	
	public void setIsloggedin(boolean isloggedin) {
		
		this.isloggedin = isloggedin;
	}

	public  AuthenticateResponse getCurUserInfo() {
		return authenticateResponse;
	}

	public void setCurUserInfo( AuthenticateResponse response) {
		authenticateResponse= response;
	}

}
