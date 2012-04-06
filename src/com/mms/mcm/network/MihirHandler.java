package com.mms.mcm.network;

import android.os.Handler;
import android.os.Message;

import com.mms.mcm.custom.Constants;

public class MihirHandler extends Handler{
	
	private NetworkCallback<Object> callback;
	public MihirHandler(NetworkCallback<Object> callback)
	{
		this.callback=callback;
	}
	
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		if(msg.what==Constants.SUCCESS)
		{
			Object object=msg.obj;
			callback.onSuccess(object);
		}
		else if(msg.what==Constants.FAILURE)
		{
			Object object=msg.obj;
			callback.onFailure(object.toString());
		}
	}
}

