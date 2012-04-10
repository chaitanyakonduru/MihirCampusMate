package com.mms.mcm.network;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mms.mcm.custom.Constants;

public final class SoapConn {

	private static final String TAG = "SoapConnection";
	private static final String URL = "http://mihircampusmate.elasticbeanstalk.com/services/MihirCampusMateSrv?wsdl";
	private static HttpTransportSE httpTransportSE;

	public static void callWebService(String actionName, SoapObject request,
			Handler mihirHandler) {
		try {
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			envelope.dotNet = false;
			envelope.bodyOut = request;
			envelope.encodingStyle = SoapSerializationEnvelope.XSD;
			 int timeout = 60000;
			httpTransportSE = new HttpTransportSE(URL,
			 timeout);
			httpTransportSE.debug = true;
			Log.v("request", request.toString());
			httpTransportSE.call(actionName, envelope);
			Object object = envelope.bodyIn;
			
			if (object != null) {
				Log.v(TAG, "response::::" + object.toString());
				Message.obtain(mihirHandler, Constants.SUCCESS, object)
						.sendToTarget();
			} else {
				Message.obtain(mihirHandler, Constants.FAILURE, object)
						.sendToTarget();
			}

		} catch (Exception e) {
			
			Message.obtain(mihirHandler, Constants.FAILURE, e.getMessage())
					.sendToTarget();
			e.printStackTrace();
		}
		finally
		{
		httpTransportSE.reset();
		}
	}

}
