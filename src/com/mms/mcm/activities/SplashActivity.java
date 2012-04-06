package com.mms.mcm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mms.mcm.R;

public class SplashActivity extends Activity {
	protected static final String TAG = "SplashScreen";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread t = new Thread() {
			public void run() {
				super.run();
				try {
					sleep(5000);
				}
				catch(InterruptedException ie)
				{
					Log.v(TAG, ie.getMessage());
				}
				catch(Exception e)
				{
					Log.v(TAG, e.getMessage());
				}
				
				finally
				{
					
					startActivity(new Intent(SplashActivity.this,LoginActivity.class));
					finish();
				}
			}

		};
		t.start();

	}


}